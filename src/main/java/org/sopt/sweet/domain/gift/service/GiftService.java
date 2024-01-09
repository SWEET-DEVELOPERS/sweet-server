package org.sopt.sweet.domain.gift.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.gift.dto.request.CreateGiftRequestDto;
import org.sopt.sweet.domain.gift.dto.request.MyGiftsRequestDto;
import org.sopt.sweet.domain.gift.dto.response.MyGiftDto;
import org.sopt.sweet.domain.gift.dto.response.MyGiftsResponseDto;
import org.sopt.sweet.domain.gift.entity.Gift;
import org.sopt.sweet.domain.gift.repository.GiftRepository;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.domain.room.entity.Room;
import org.sopt.sweet.domain.room.entity.RoomMember;
import org.sopt.sweet.domain.room.repository.RoomMemberRepository;
import org.sopt.sweet.domain.room.repository.RoomRepository;
import org.sopt.sweet.global.error.exception.BusinessException;
import org.sopt.sweet.global.error.exception.EntityNotFoundException;
import org.sopt.sweet.global.error.exception.ForbiddenException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.sopt.sweet.global.error.ErrorCode.*;

@RequiredArgsConstructor
@Service
@Transactional
public class GiftService {
    private final GiftRepository giftRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private static final int MAX_GIFT_COUNT = 2;

    public void createNewGift(Long memberId, CreateGiftRequestDto createGiftRequestDto) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findRoomByIdOrThrow(createGiftRequestDto.roomId());
        checkRoomMemberNotExists(room, member);
        checkGiftCountNotExceeded(room, member);
        Gift gift = buildGift(member, room, createGiftRequestDto);
        giftRepository.save(gift);
    }

    @Transactional(readOnly = true)
    public MyGiftsResponseDto getMyGift(Long memberId, MyGiftsRequestDto myGiftsRequestDto) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findRoomByIdOrThrow(myGiftsRequestDto.roomId());
        checkRoomMemberNotExists(room, member);
        List<Gift> gifts = giftRepository.findByRoomAndMember(room, member);
        List<MyGiftDto> myGiftsDtoList = mapGiftsToMyGiftDtoList(gifts);
        return new MyGiftsResponseDto(myGiftsDtoList);
    }

    public void deleteMyGift(Long memberId, Long giftId){
        Member member = findMemberByIdOrThrow(memberId);
        Gift gift = findByIdOrThrow(giftId);
        validateMemberGiftOwner(member, gift);
        giftRepository.delete(gift);
    }

    private Gift buildGift(Member member, Room room, CreateGiftRequestDto createGiftRequestDto) {
        return Gift.builder()
                .url(createGiftRequestDto.url())
                .name(createGiftRequestDto.name())
                .cost(createGiftRequestDto.cost())
                .imageUrl(createGiftRequestDto.imageUrl())
                .room(room)
                .member(member)
                .build();
    }

    private List<MyGiftDto> mapGiftsToMyGiftDtoList(List<Gift> gifts) {
        return gifts.stream()
                .map(gift -> MyGiftDto.of(gift.getId(), gift.getImageUrl(), gift.getName(), gift.getCost()))
                .collect(Collectors.toList());
    }

    private void checkGiftCountNotExceeded(Room room, Member member) {
        long giftCount = giftRepository.countByRoomAndMember(room, member);
        if (giftCount >= MAX_GIFT_COUNT) {
            throw new BusinessException(MEMBER_GIFT_COUNT_EXCEEDED);
        }
    }

    private boolean isRoomMemberExists(Room room, Member member) {
        Optional<RoomMember> existingRoomMember = roomMemberRepository.findByRoomAndMember(room, member);
        return existingRoomMember.isPresent();
    }

    private void checkRoomMemberNotExists(Room room, Member member) {
        if (!isRoomMemberExists(room, member)) {
            throw new ForbiddenException(MEMBER_NOT_IN_ROOM);
        }
    }

    private void validateMemberGiftOwner(Member member, Gift gift) {
        if (!gift.getMember().equals(member)) {
            throw new ForbiddenException(MEMBER_NOT_GIFT_OWNER);
        }
    }

    private Member findMemberByIdOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
    }

    private Room findRoomByIdOrThrow(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(ROOM_NOT_FOUND));
    }

    private Gift findByIdOrThrow(Long giftId){
        return giftRepository.findById(giftId)
                .orElseThrow(() -> new EntityNotFoundException(GIFT_NOT_FOUND));
    }
}
