package org.sopt.sweet.domain.product.service;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.member.repository.MemberRepository;
import org.sopt.sweet.domain.product.dto.response.HotProductDto;
import org.sopt.sweet.domain.product.dto.response.HotProductsResponseDto;
import org.sopt.sweet.domain.product.entity.Product;
import org.sopt.sweet.domain.product.repository.ProductRepository;
import org.sopt.sweet.domain.room.entity.Room;
import org.sopt.sweet.domain.room.repository.RoomRepository;
import org.sopt.sweet.global.error.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.sopt.sweet.global.error.ErrorCode.MEMBER_NOT_FOUND;
import static org.sopt.sweet.global.error.ErrorCode.ROOM_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final RoomRepository roomRepository;

    public HotProductsResponseDto getHotGift(Long memberId, Long roomId) {
        Member member = findMemberByIdOrThrow(memberId);
        Room room = findRoomByIdOrThrow(roomId);
        List<Product> allProducts = productRepository.findAll();
        List<HotProductDto> hotProductDtoList = mapToHotProductDtoList(allProducts);
        return HotProductsResponseDto.of(room.getTournamentStartDate(), hotProductDtoList);
    }

    private Member findMemberByIdOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(MEMBER_NOT_FOUND));
    }

    private Room findRoomByIdOrThrow(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException(ROOM_NOT_FOUND));
    }

    private List<HotProductDto> mapToHotProductDtoList(List<Product> products) {
        return products.stream()
                .map(product -> HotProductDto.of(
                        product.getId(),
                        product.getName(),
                        product.getImageUrl(),
                        product.getUrl(),
                        product.getCost()
                ))
                .collect(Collectors.toList());
    }
}
