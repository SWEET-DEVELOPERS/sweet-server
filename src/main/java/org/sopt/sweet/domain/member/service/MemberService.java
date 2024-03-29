package org.sopt.sweet.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.dto.response.*;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.domain.room.constant.TournamentDuration;
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
        List<ClosedRoomResponseDto> closedRooms = roomMembers.stream()
                .map(RoomMember::getRoom)
                .filter(room -> {
                    LocalDateTime tournamentEndDate = getTournamentEndDate(room.getTournamentStartDate(), room.getTournamentDuration());
                    return tournamentEndDate != null && tournamentEndDate.isBefore(LocalDateTime.now());
                })
                .sorted(Comparator.comparing(room -> getTournamentEndDate(room.getTournamentStartDate(), room.getTournamentDuration()), Comparator.reverseOrder()))
                .map(room -> mapToClosedRoomResponseDto(room, memberId))
                .collect(Collectors.toList());
        return closedRooms;
    }

    private ClosedRoomResponseDto mapToClosedRoomResponseDto(Room room, Long memberId){
        return new ClosedRoomResponseDto(
                room.getId(),
                room.getImageUrl(),
                room.getGifteeName(),
                room.getGifterNumber(),
                isOwner(memberId, room.getId())
        );
    }


    public List<ActiveRoomResponseDto> getActiveRoom(Long memberId) {
        List<RoomMember> roomMembers = roomMemberRepository.findByMemberId(memberId);
        List<ActiveRoomResponseDto> activeRooms = roomMembers.stream()
                .map(RoomMember::getRoom)
                .filter(room -> {
                    LocalDateTime tournamentEndDate = getTournamentEndDate(room.getTournamentStartDate(), room.getTournamentDuration());
                    return tournamentEndDate != null && tournamentEndDate.isAfter(LocalDateTime.now());
                })
                .sorted(Comparator.comparing(room -> getRoomMemberCreationTime(room, memberId), Comparator.reverseOrder()))
                .map(room -> mapToActiveRoomResponseDto(room, memberId))
                .collect(Collectors.toList());
        return activeRooms;
    }

    private LocalDateTime getTournamentEndDate(LocalDateTime tournamentStartDate, TournamentDuration tournamentDuration) {
        if (tournamentStartDate != null && tournamentDuration != null) {
            return tournamentStartDate.plusHours(tournamentDuration.getHours());
        } else {
            return null;
        }
    }

    private ActiveRoomResponseDto mapToActiveRoomResponseDto(Room room, Long memberId) {
        return new ActiveRoomResponseDto(
                room.getId(),
                room.getImageUrl(),
                room.getGifteeName(),
                room.getGifterNumber(),
                room.getTournamentStartDate(),
                isOwner(memberId, room.getId()),
                getRoomMemberParticipation(room, memberId)

        );
    }

    private boolean getRoomMemberParticipation(Room room, Long memberId) {
        Optional<RoomMember> roomMember = roomMemberRepository.findByMemberIdAndRoom(memberId, room);
        return roomMember.map(RoomMember::isTournamentParticipation).orElse(false);
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

    public List<ClosedRoomResponseDto> getTop2ClosedRooms(Long memberId) {
        List<ClosedRoomResponseDto> closedRooms = getClosedRoom(memberId);
        return closedRooms.size() > 2 ? closedRooms.subList(0, 2) : closedRooms;
    }

    public List<ActiveRoomResponseDto> getTop2ActiveRooms(Long memberId) {
        List<ActiveRoomResponseDto> activeRooms = getActiveRoom(memberId);
        return activeRooms.size() > 2 ? activeRooms.subList(0, 2) : activeRooms;
    }

    public MemberInfoDto getMemberInfo(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return new MemberInfoDto(
                member.get().getNickName(),
                member.get().getProfileImg()
        );
    }

    public ProfileImageResponseDto getProfile(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        return new ProfileImageResponseDto(member.get().getProfileImg());
    }
}