package org.sopt.sweet.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.gift.entity.Gift;
import org.sopt.sweet.domain.gift.repository.GiftRepository;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.domain.product.entity.Product;
import org.sopt.sweet.domain.product.repository.ProductRepository;
import org.sopt.sweet.domain.room.dto.request.CreateRoomRequestDto;
import org.sopt.sweet.domain.room.dto.request.RoomImageRequestDto;
import org.sopt.sweet.domain.room.dto.request.RoomNameRequestDto;
import org.sopt.sweet.domain.room.dto.response.*;
import org.sopt.sweet.domain.room.entity.Room;
import org.sopt.sweet.domain.room.entity.RoomMember;
import org.sopt.sweet.domain.room.repository.RoomMemberRepository;
import org.sopt.sweet.domain.room.repository.RoomRepository;
import org.sopt.sweet.global.error.exception.BusinessException;
import org.sopt.sweet.global.error.exception.EntityNotFoundException;
import org.sopt.sweet.global.error.exception.ForbiddenException;
import org.sopt.sweet.global.error.exception.InvalidValueException;
import org.springframework.data.domain.PageRequest;
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
    private static final String DEFAULT_IMAGE_URL = "https://sweet-gift-bucket.s3.ap-northeast-2.amazonaws.com/sweet.png";
    private static final int CODE_LENGTH = 6;
    private static final int MAX_GIFTER_NUMBER = 8;

    public CreateRoomResponseDto createNewRoom(Long memberId, CreateRoomRequestDto createRoomRequestDto) {
        Member host = findMemberByIdOrThrow(memberId);
        validateName(createRoomRequestDto.gifteeName());
        String invitationCode = generateUniqueInvitationCode();
        Room room = Room.builder()
                .gifteeName(createRoomRequestDto.gifteeName())
                .imageUrl((createRoomRequestDto.imageUrl() != null && !createRoomRequestDto.imageUrl().trim().isEmpty()) ? createRoomRequestDto.imageUrl() : DEFAULT_IMAGE_URL)
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

    public JoinRoomResponseDto findAndJoinRoom(Long memberId, String invitationCode) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByInvitationOrThrow(invitationCode);
        if (!isOwner(memberId, room.getId())) {
            joinRoom(member, room);
        }
        return JoinRoomResponseDto.of(room.getId());
    }

    public Boolean isOwner(Long memberId, Long roomId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Room> room = roomRepository.findById(roomId);
        Boolean isOwner = member.get().getId().equals(room.get().getHost().getId());
        return isOwner;
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
    public RoomDetailResponseDto getRoomDetailInfo(Long memberId, Long roomId) {
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

    public void modifyRoomThumbnail(Long memberId, Long roomId, RoomImageRequestDto roomImageRequestDto) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByIdOrThrow(roomId);
        checkRoomHost(member, room);
        room.setImageUrl(roomImageRequestDto.imageUrl());
        roomRepository.save(room);
    }

    public void modifyRoomGifteeName(Long memberId, Long roomId, RoomNameRequestDto roomNameRequestDto) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByIdOrThrow(roomId);
        checkRoomHost(member, room);
        room.setGifteeName(roomNameRequestDto.gifteeName());
        roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public List<RoomMemberDto> getRoomMembers(Long memberId, Long roomId) {
        List<RoomMember> roomMembers = roomMemberRepository.findByRoomId(roomId);
        return mapToRoomMemberDtoList(roomMembers);
    }

    private List<RoomMemberDto> mapToRoomMemberDtoList(List<RoomMember> roomMembers) {
        Long hostId = roomMembers.get(0).getRoom().getHost().getId();
        return roomMembers.stream()
                .filter(roomMember -> !roomMember.getMember().getId().equals(hostId))
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


    @Transactional(readOnly = true)
    public RoomOwnerDetailDto getRoom(Long memberId, Long roomId) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findByIdOrThrow(roomId);
        checkRoomHost(member, room);
        RoomDto roomDto = new RoomDto(room.getGifteeName(), room.getGifterNumber());
        OwnerDto ownerDto = new OwnerDto(room.getHost().getId(), room.getHost().getProfileImg(), room.getHost().getNickName());
        return RoomOwnerDetailDto.of(roomDto, ownerDto);
    }

    public void deleteRoomMember(Long memberId, Long roomId, Long deleteMemberId) {
        Member member = findMemberByIdOrThrow(memberId);
        Member deleteMember = findMemberByIdOrThrow(deleteMemberId);
        Room room = findByIdOrThrow(roomId);
        checkRoomHost(member, room);
        checkDeleteHostSelf(member, deleteMember);
        deleteGiftsByRoomAndMember(room, deleteMember);
        deleteRoomMemberData(room, deleteMember);
        decrementGifterNumber(room);
    }



    private void checkRoomHost(Member member, Room room) {
        if (!member.equals(room.getHost())) {
            throw new ForbiddenException(ROOM_OWNER_MISMATCH);
        }
    }

    private void checkDeleteHostSelf(Member member, Member deleteMember) {
        if (member.equals(deleteMember)) {
            throw new BusinessException(ROOM_OWNER_CANNOT_DELETE_SELF);
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

    private void deleteGiftsByRoomAndMember(Room room, Member deleteMember) {
        List<Gift> giftsToDelete = giftRepository.findByRoomAndMember(room, deleteMember);
        giftRepository.deleteAll(giftsToDelete);
    }

    private void deleteRoomMemberData(Room room, Member deleteMember) {
        RoomMember roomMemberToDelete = roomMemberRepository.findByRoomAndMember(room, deleteMember)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
        roomMemberRepository.delete(roomMemberToDelete);
    }

    private void decrementGifterNumber(Room room) {
        room.setGifterNumber(room.getGifterNumber() - 1);
        roomRepository.save(room);
    }

    private void joinRoom(Member member, Room room) {
        checkRoomMemberExists(room, member);
        checkMaxParticipants(room);
        checkTournamentStartDate(room);
        createRoomMember(room, member);
        room.setGifterNumber(room.getGifterNumber() + 1);
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
