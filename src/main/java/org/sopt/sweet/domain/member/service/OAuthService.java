package org.sopt.sweet.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.constant.SocialType;
import org.sopt.sweet.domain.member.dto.response.KakaoUserInfoResponseDto;
import org.sopt.sweet.domain.member.dto.response.MemberReissueTokenResponseDto;
import org.sopt.sweet.domain.member.dto.response.MemberTokenResponseDto;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.entity.OAuthToken;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.global.config.auth.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
@Transactional
public class OAuthService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    private String issueNewAccessToken(Long memberId) {
        return jwtProvider.getIssueToken(memberId, true);
    }

    private String issueNewRefreshToken(Long memberId) {
        return jwtProvider.getIssueToken(memberId, false);
    }

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${jwt.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    // 카카오 로그인 시 회원 정보 조회
    public KakaoUserInfoResponseDto kakaoCallback(String code, String environment) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        if ("development".equals(environment)) {
            params.add("redirect_uri", redirectUri);
        } else {
            params.add("redirect_uri", "http://localhost:5137/api/oauth/kakao/login");
        }
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        try {
            ResponseEntity<OAuthToken> response = restTemplate.postForEntity(
                    "https://kauth.kakao.com/oauth/token",
                    kakaoTokenRequest,
                    OAuthToken.class
            );

            OAuthToken oauthToken = response.getBody();
            headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + oauthToken.getAccessToken());

            ResponseEntity<String> profileResponse = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );

            JsonElement jsonElement = JsonParser.parseString(profileResponse.getBody());
            JsonElement properties = jsonElement.getAsJsonObject().get("properties");

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String profileImage = properties.getAsJsonObject().get("profile_image").getAsString();
            Long socialId = jsonElement.getAsJsonObject().get("id").getAsLong();

            System.out.println("카카오 토큰 정보 " + oauthToken);
            System.out.println("프로필 정보 - 닉네임: " + nickname + ", 프로필 이미지: " + profileImage);

            KakaoUserInfoResponseDto member = saveMember(socialId, nickname, profileImage);
            return new KakaoUserInfoResponseDto(member.memberId(), socialId, nickname, profileImage);

        } catch (HttpClientErrorException e) {
            System.err.println("Kakao API 요청 실패. 응답 코드: " + e.getRawStatusCode() + ", 응답 내용: " + e.getResponseBodyAsString());
        }

        return null;
    }

    // 카카오 로그인 시 회원 정보 저장
    public KakaoUserInfoResponseDto saveMember(Long socialId, String nickname, String profileImage) {
        Member existMember = memberRepository.findBySocialId(socialId);

        if (existMember == null) {
            Member member = Member.builder()
                    .socialId(socialId)
                    .nickName(nickname)
                    .socialType(SocialType.valueOf("KAKAO"))
                    .profileImg(profileImage)
                    .build();
            memberRepository.save(member);
            sendDiscordNotification(nickname);
            return new KakaoUserInfoResponseDto(member.getId(), socialId, nickname, profileImage);
        }
        return new KakaoUserInfoResponseDto(existMember.getId(), socialId, nickname, profileImage);
    }


    // 카카오 로그인 시 토큰 저장
    public MemberTokenResponseDto saveToken(Long memberId) {
        String refreshToken = null;
        String accessToken = issueNewAccessToken(memberId);
        String redisKey = "RT:" + memberId;
        String storedRefreshToken = redisTemplate.opsForValue().get(redisKey);

        if (storedRefreshToken != null) {
            refreshToken = storedRefreshToken;
        } else {
            refreshToken = issueNewRefreshToken(memberId);
            redisTemplate.opsForValue().set(redisKey, refreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        System.out.println("카카오 로그인 성공 memberId: " + memberId + " accessToken :" + accessToken + " refreshToken: " + refreshToken);
        return new MemberTokenResponseDto(accessToken, refreshToken);
    }


    public void logout(Long memberId) {
        String redisKey = "RT:" + memberId;
        redisTemplate.delete(redisKey);
    }

    public MemberReissueTokenResponseDto reissue(MemberTokenResponseDto memberTokenResponseDto) throws JsonProcessingException {
        Long memberId = Long.valueOf(jwtProvider.decodeJwtPayloadSubject(memberTokenResponseDto.accessToken()));

        String refreshToken = memberTokenResponseDto.refreshToken();
        String redisKey = "RT:" + memberId;
        String storedRefreshToken = redisTemplate.opsForValue().get(redisKey);
        jwtProvider.validateRefreshToken(storedRefreshToken);
        jwtProvider.equalsRefreshToken(refreshToken, storedRefreshToken);

        String newAccessToken = issueNewAccessToken(memberId);
        String newRefreshToken = issueNewRefreshToken(memberId);
        redisTemplate.opsForValue().set(redisKey, newRefreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);

        return MemberReissueTokenResponseDto.of(newAccessToken, newRefreshToken);
    }

    // 회원가입 웹훅
    public String sendDiscordNotification(String nickname) {
        RestTemplate restTemplate = new RestTemplate();
        Long totalMembers = memberRepository.count();

        String message = "\n" + totalMembers + "번째 멤버가 회원가입했습니다.\n" +
                "사용자명: " + nickname;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("content", message);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(discordWebhookUrl, requestEntity, String.class);
        return null;
    }




}






