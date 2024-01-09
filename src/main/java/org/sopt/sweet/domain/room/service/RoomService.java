package org.sopt.sweet.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.domain.room.dto.request.CreateRoomRequestDto;
import org.sopt.sweet.domain.room.dto.request.JoinRoomRequestDto;
import org.sopt.sweet.domain.room.dto.response.CreateRoomResponseDto;
import org.sopt.sweet.domain.room.dto.response.JoinRoomResponseDto;
import org.sopt.sweet.domain.room.dto.response.RoomInviteResponseDto;
import org.sopt.sweet.domain.room.entity.Room;
import org.sopt.sweet.domain.room.entity.RoomMember;
import org.sopt.sweet.domain.room.repository.RoomMemberRepository;
import org.sopt.sweet.domain.room.repository.RoomRepository;
import org.sopt.sweet.global.error.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.sopt.sweet.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final RoomMemberRepository roomMemberRepository;
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private static final int MAX_GIFTER_NUMBER = 8;

    public CreateRoomResponseDto createNewRoom(Long memberId, CreateRoomRequestDto createRoomRequestDto) {
        Member host = findMemberByIdOrThrow(memberId);
        validateName(createRoomRequestDto.gifteeName());
        String invitationCode = generateUniqueInvitationCode();
        Room room = Room.builder()
                .gifteeName(createRoomRequestDto.gifteeName())
                .imageUrl(createRoomRequestDto.imageUrl())
                .deliveryDate(createRoomRequestDto.deliveryDate())
                .tournamentStartDate(createRoomRequestDto.tournamentStartDate())
                .tournamentDuration(createRoomRequestDto.tournamentDuration())
                .invitationCode(invitationCode)
                .host(host)
                .build();
        Long roomId = roomRepository.save(room).getId();
        createRoomMember(room, host);
        return CreateRoomResponseDto.of(roomId, invitationCode);
    }

    private void createRoomMember(Room room, Member member) {
        RoomMember roomMember = RoomMember.builder()
                .room(room)
                .member(member)
                .build();
        roomMemberRepository.save(roomMember);
    }

    @Transactional(readOnly = true)
    public RoomInviteResponseDto getRoomInviteInfo(String invitationCode) {
        Room room = findByInvitationOrThrow(invitationCode);
        return RoomInviteResponseDto.of(
                room.getId(),
                room.getGifterNumber(),
                room.getGifteeName(),
                room.getImageUrl(),
                room.getDeliveryDate(),
                room.getTournamentStartDate(),
                room.getTournamentDuration(),
                room.getInvitationCode());
    }

    public JoinRoomResponseDto findAndJoinRoom(Long memberId, JoinRoomRequestDto joinRoomRequestDto) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByInvitationOrThrow(joinRoomRequestDto.invitationCode());
        joinRoom(member, room);
        return JoinRoomResponseDto.of(room.getId());
    }

    private void joinRoom(Member member, Room room) {
        checkRoomMemberExists(room, member);
        checkMaxParticipants(room);
        checkTournamentStartDate(room);
        createRoomMember(room, member);
        room.setGifterNumber(room.getGifterNumber()+1);
    }

    private void checkMaxParticipants(Room room) {
        if (room.getGifterNumber() >= MAX_GIFTER_NUMBER) {
            throw new ForbiddenException(MEMBER_NUMBER_EXCEEDED);
        }
    }

    private boolean isRoomMemberExists(Room room, Member member) {
        Optional<RoomMember> existingRoomMember = roomMemberRepository.findByRoomAndMember(room, member);
        return existingRoomMember.isPresent();
    }

    private void checkRoomMemberExists(Room room, Member member) {
        if (isRoomMemberExists(room, member)) {
            throw new BusinessException(MEMBER_ALREADY_EXISTS_ROOM);
        }
    }

    public void checkTournamentStartDate(Room room) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (room.getTournamentStartDate().isBefore(currentDateTime)) {
            throw new BusinessException(INVITATION_CLOSED);
        }
    }

    private Room findByInvitationOrThrow(String invitationCode) {
        return roomRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new EntityNotFoundException(ROOM_NOT_FOUND));
    }

    private Member findMemberByIdOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
    }

    private Room findByIdOrThrow(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(ROOM_NOT_FOUND));
    }

    public boolean isInvitationCodeExists(String code) {
        return roomRepository.existsByInvitationCode(code);
    }

    private String generateUniqueInvitationCode() {
        String code;
        do {
            code = generateInvitationCode();
        } while (isInvitationCodeExists(code));
        return code;
    }

    private static String generateInvitationCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            codeBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    private void validateName(String name) {
        if (name.length() > 10) {
            throw new InvalidValueException(NAME_LENGTH_EXCEEDED);
        }
    }


}
