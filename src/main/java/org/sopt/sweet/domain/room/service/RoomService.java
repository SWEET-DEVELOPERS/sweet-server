package org.sopt.sweet.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.gift.entity.Gift;
import org.sopt.sweet.domain.gift.repository.GiftRepository;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.domain.product.entity.Product;
import org.sopt.sweet.domain.product.repository.ProductRepository;
import org.sopt.sweet.domain.room.dto.request.CreateRoomRequestDto;
import org.sopt.sweet.domain.room.dto.request.JoinRoomRequestDto;
import org.sopt.sweet.domain.room.dto.request.RoomImageRequestDto;
import org.sopt.sweet.domain.room.dto.request.RoomNameRequestDto;
import org.sopt.sweet.domain.room.dto.response.*;
import org.sopt.sweet.domain.room.entity.Room;
import org.sopt.sweet.domain.room.entity.RoomMember;
import org.sopt.sweet.domain.room.repository.RoomMemberRepository;
import org.sopt.sweet.domain.room.repository.RoomRepository;
import org.sopt.sweet.global.error.exception.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.sopt.sweet.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final GiftRepository giftRepository;
    private final ProductRepository productRepository;
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
                room.getInvitationCode()
        );
    }

    public JoinRoomResponseDto findAndJoinRoom(Long memberId, JoinRoomRequestDto joinRoomRequestDto) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByInvitationOrThrow(joinRoomRequestDto.invitationCode());
        joinRoom(member, room);
        return JoinRoomResponseDto.of(room.getId());
    }

    @Transactional(readOnly = true)
    public RoomMainResponseDto getRoomMainInfo(Long memberId, Long roomId) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByIdOrThrow(roomId);
        checkRoomMemberNotExists(room, member);
        List<RoomMyGiftDto> roomMyGiftDtoList = buildRoomMyGiftDtoList(member, room);
        List<RoomFriendsGiftDto> roomFriendsGiftDtoList = buildRoomFriendsGiftDtoList(member, room);
        List<HotProductGiftDto> hotProductGiftDtoList = buildHotProductGiftDtoList();
        return RoomMainResponseDto.of(
                room.getGifterNumber(),
                room.getGifteeName(),
                room.getInvitationCode(),
                room.getTournamentStartDate(),
                roomMyGiftDtoList,
                roomFriendsGiftDtoList,
                hotProductGiftDtoList);
    }

    @Transactional(readOnly = true)
    public RoomDetailResponseDto getRoomDetailInfo(Long memberId, Long roomId){
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByIdOrThrow(roomId);
        checkRoomHost(member, room);
        return RoomDetailResponseDto.of(
                roomId,
                room.getImageUrl(),
                room.getGifteeName(),
                room.getGifterNumber(),
                room.getDeliveryDate(),
                room.getTournamentStartDate(),
                room.getTournamentDuration()
        );
    }

    public void modifyRoomThumbnail(Long memberId, Long roomId, RoomImageRequestDto roomImageRequestDto){
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByIdOrThrow(roomId);
        checkRoomHost(member, room);
        room.setImageUrl(roomImageRequestDto.imageUrl());
        roomRepository.save(room);
    }

    public void modifyRoomGifteeName(Long memberId, Long roomId, RoomNameRequestDto roomNameRequestDto){
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByIdOrThrow(roomId);
        checkRoomHost(member, room);
        room.setGifteeName(roomNameRequestDto.gifteeName());
        roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public RoomMembersResponseDto getRoomMembers(Long memberId, Long roomId){
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByIdOrThrow(roomId);
        checkRoomHost(member, room);
        List<RoomMember> roomMembers = roomMemberRepository.findByRoomId(roomId);
        List<RoomMemberDto> roomMemberDtoList = mapToRoomMemberDtoList(roomMembers);
        return RoomMembersResponseDto.of(memberId, room.getGifterNumber(), roomMemberDtoList);
    }

    private List<RoomMemberDto> mapToRoomMemberDtoList(List<RoomMember> roomMembers) {
        return roomMembers.stream()
                .map(this::mapToRoomMemberDto)
                .collect(Collectors.toList());
    }

    private RoomMemberDto mapToRoomMemberDto(RoomMember roomMember) {
        return RoomMemberDto.of(
                roomMember.getMember().getId(),
                roomMember.getMember().getProfileImg(),
                roomMember.getMember().getNickName()
        );
    }

    private void checkRoomHost(Member member, Room room){
        if (!member.equals(room.getHost())) {
            throw new ForbiddenException(ROOM_OWNER_MISMATCH);
        }
    }

    private List<RoomMyGiftDto> buildRoomMyGiftDtoList(Member member, Room room) {
        List<Gift> roomGifts = giftRepository.findByRoomAndMember(room, member);
        return roomGifts.stream()
                .map(gift -> RoomMyGiftDto.of(
                        gift.getId(),
                        gift.getImageUrl(),
                        gift.getName(),
                        gift.getUrl(),
                        gift.getCost()
                ))
                .collect(Collectors.toList());
    }

    private List<RoomFriendsGiftDto> buildRoomFriendsGiftDtoList(Member member, Room room) {
        List<Gift> roomGifts = giftRepository.findLatestGiftsByRoomAndNotMember(room, member, PageRequest.of(0, 5));
        return roomGifts.stream()
                .map(gift -> RoomFriendsGiftDto.of(
                        gift.getId(),
                        gift.getImageUrl(),
                        gift.getName(),
                        gift.getUrl(),
                        gift.getCost(),
                        gift.getMember().getNickName()
                ))
                .collect(Collectors.toList());
    }

    private List<HotProductGiftDto> buildHotProductGiftDtoList() {
        List<Product> randomProducts = productRepository.findRandomProducts(5);
        return randomProducts.stream()
                .map(product -> HotProductGiftDto.of(
                        product.getId(),
                        product.getImageUrl(),
                        product.getName(),
                        product.getUrl(),
                        product.getCost()
                ))
                .collect(Collectors.toList());
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

    private void checkRoomMemberNotExists(Room room, Member member) {
        if (!isRoomMemberExists(room, member)) {
            throw new BusinessException(MEMBER_NOT_IN_ROOM);
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
