package org.sopt.sweet.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.dto.response.ClosedRoomResponseDto;
import org.sopt.sweet.domain.member.dto.response.MemberTokenResponseDto;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.domain.room.entity.Room;
import org.sopt.sweet.domain.room.entity.RoomMember;
import org.sopt.sweet.domain.room.repository.RoomMemberRepository;
import org.sopt.sweet.domain.room.repository.RoomRepository;
import org.sopt.sweet.global.config.auth.jwt.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;

    public MemberTokenResponseDto getToken(Long memberId) {
        String accessToken = issueNewAccessToken(memberId);
        String refreshToken = issueNewRefreshToken(memberId);
        return new MemberTokenResponseDto(accessToken, refreshToken);
    }

    private String issueNewAccessToken(Long memberId) {
        return jwtProvider.getIssueToken(memberId, true);
    }

    private String issueNewRefreshToken(Long memberId) {
        return jwtProvider.getIssueToken(memberId, false);
    }


    public List<ClosedRoomResponseDto> getClosedRoom(Long memberId) {
        List<RoomMember> roomMembers = roomMemberRepository.findByMemberId(memberId);
        System.out.println(memberId);
        System.out.println("/"+ LocalDateTime.now());
        List<ClosedRoomResponseDto> closedRooms = roomMembers.stream()
                .map(RoomMember::getRoom)
                .filter(room -> room.getDeliveryDate().isBefore(LocalDateTime.now()))
                .sorted(Comparator.comparing(Room::getDeliveryDate).reversed())
                .map(this::mapToClosedRoomResponseDto)
                .collect(Collectors.toList());
        return closedRooms;
    }

    private ClosedRoomResponseDto mapToClosedRoomResponseDto(Room room) {
        return new ClosedRoomResponseDto(
                room.getId(),
                room.getImageUrl(),
                room.getGifteeName(),
                room.getGifterNumber()
        );
    }



}