package org.sopt.sweet.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.domain.room.dto.request.CreateRoomRequestDto;
import org.sopt.sweet.domain.room.entity.Room;
import org.sopt.sweet.domain.room.repository.RoomRepository;
import org.sopt.sweet.global.error.exception.EntityNotFoundException;
import org.sopt.sweet.global.error.exception.InvalidValueException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

import static org.sopt.sweet.global.error.ErrorCode.MEMBER_NOT_FOUND;
import static org.sopt.sweet.global.error.ErrorCode.NAME_LENGTH_EXCEEDED;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    public Long createNewRoom(Long userId, CreateRoomRequestDto createRoomRequestDto) {
        Member host = findByIdOrThrow(userId);
        validateName(createRoomRequestDto.gifteeName());
        Room room = Room.builder()
                .gifteeName(createRoomRequestDto.gifteeName())
                .imageUrl(createRoomRequestDto.imageUrl())
                .deliveryDate(createRoomRequestDto.deliveryDate())
                .tournamentStartDate(createRoomRequestDto.tournamentStartDate())
                .tournamentDuration(createRoomRequestDto.tournamentDuration())
                .invitationCode(generateUniqueInvitationCode())
                .host(host)
                .build();
        return roomRepository.save(room).getId();
    }

    private Member findByIdOrThrow(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
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
