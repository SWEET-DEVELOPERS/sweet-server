package org.sopt.sweet.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.dto.response.ActiveRoomResponseDto;
import org.sopt.sweet.domain.member.dto.response.ClosedRoomResponseDto;
import org.sopt.sweet.domain.member.dto.response.MemberTokenResponseDto;
import org.sopt.sweet.domain.member.entity.Member;
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
import java.util.Optional;
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


    public List<ActiveRoomResponseDto> getActiveRoom(Long memberId) {
        List<RoomMember> roomMembers = roomMemberRepository.findByMemberId(memberId);
        List<ActiveRoomResponseDto> activeRooms = roomMembers.stream()
                .map(RoomMember::getRoom)
                .filter(room -> room.getDeliveryDate().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(room -> getRoomMemberCreationTime(room, memberId), Comparator.reverseOrder()))
                .map(room -> mapToActiveRoomResponseDto(room, memberId))
                .collect(Collectors.toList());
        return activeRooms;
    }

    private ActiveRoomResponseDto mapToActiveRoomResponseDto(Room room, Long memberId) {
        return new ActiveRoomResponseDto(
                room.getId(),
                room.getImageUrl(),
                room.getGifteeName(),
                room.getGifterNumber(),
                room.getTournamentStartDate(),
                isOwner(memberId, room.getId())
        );
    }
    private LocalDateTime getRoomMemberCreationTime(Room room, Long memberId) {
        Optional<RoomMember> roomMember = roomMemberRepository.findByMemberIdAndRoom(memberId, room);
        return roomMember.map(RoomMember::getCreateDate).orElse(LocalDateTime.MIN);
    }

    public Boolean isOwner(Long memberId, Long roomId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Room> room = roomRepository.findById(roomId);
        Boolean isOwner = member.get().getId().equals(room.get().getHost().getId());
        return isOwner;
    }

}