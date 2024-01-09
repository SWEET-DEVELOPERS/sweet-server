package org.sopt.sweet.domain.member.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.constant.SocialType;
import org.sopt.sweet.domain.member.dto.reponse.KakaoUserInfoResponseDto;
import org.sopt.sweet.domain.member.dto.reponse.MemberTokenResponseDto;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.entity.OAuthToken;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.global.config.auth.jwt.JwtProvider;
import org.sopt.sweet.global.error.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;
@RequiredArgsConstructor
@Service
@Transactional
public class OAuthService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    public MemberTokenResponseDto getToken(Long memberId) {
        String accessToken = issueNewAccessToken(memberId);
        String refreshToken = issueNewRefreshToken(memberId);
        return new MemberTokenResponseDto(memberId, accessToken, refreshToken);
    }

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

    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public KakaoUserInfoResponseDto kakaoCallback(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
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

            String accessToken = issueNewAccessToken(socialId);
            String refreshToken = issueNewRefreshToken(socialId);

            saveMember(socialId, nickname, profileImage);

            String redisKey = "RT:" + socialId;
            redisTemplate.opsForValue().set(redisKey, refreshToken,1, TimeUnit.HOURS);

            return new KakaoUserInfoResponseDto(socialId, nickname, profileImage, accessToken, refreshToken);

        } catch (HttpClientErrorException e) {
            System.err.println("Kakao API 요청 실패. 응답 코드: " + e.getRawStatusCode() + ", 응답 내용: " + e.getResponseBodyAsString());
        }

        return null;
    }


    public void saveMember(Long SocialId, String nickname, String profileImage) {
        Member existMember = memberRepository.findBySocialId(SocialId);

        if (existMember == null) {
            Member member = Member.builder()
                    .socialId(SocialId)
                    .nickName(nickname)
                    .socialType(SocialType.valueOf("KAKAO"))
                    .profileImg(profileImage)
                    .build();
            memberRepository.save(member);
        }
    }



    public String refreshAccessToken(String refreshToken) {
        try {
            jwtProvider.validateRefreshToken(refreshToken);
            Long userId = jwtProvider.getSubject(refreshToken);
            return issueNewAccessToken(userId);
        } catch (UnauthorizedException e) {
            throw e;
        }
    }

}
