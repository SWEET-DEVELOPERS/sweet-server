package org.sopt.sweet.domain.gift.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.gift.dto.request.CreateGiftRequestDto;
import org.sopt.sweet.domain.gift.dto.request.MyGiftsRequestDto;
import org.sopt.sweet.domain.gift.dto.request.TournamentScoreRequestDto;
import org.sopt.sweet.domain.gift.dto.response.*;
import org.sopt.sweet.domain.gift.service.GiftService;
import org.sopt.sweet.global.common.SuccessResponse;
import org.sopt.sweet.global.config.auth.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/gift")
@RestController
public class GiftController implements GiftApi {

    private final GiftService giftService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createNewGift(@UserId Long userId, @RequestBody CreateGiftRequestDto createGiftRequestDto) {
        giftService.createNewGift(userId, createGiftRequestDto);
        return SuccessResponse.created(null);
    }

    @GetMapping("/my/{roomId}")
    public ResponseEntity<SuccessResponse<?>> getMyGift(@UserId Long userId, @PathVariable Long roomId) {
        final MyGiftsResponseDto myGiftsResponseDto = giftService.getMyGift(userId, roomId);
        return SuccessResponse.ok(myGiftsResponseDto);
    }


    @DeleteMapping("/my/{giftId}")
    public ResponseEntity<SuccessResponse<?>> deleteMyGift(@UserId Long userId, @PathVariable Long giftId) {
        giftService.deleteMyGift(userId, giftId);
        return SuccessResponse.ok(null);
    }

    @GetMapping("/tournament/{roomId}")
    public ResponseEntity<SuccessResponse<?>> getTournamentGiftList(@UserId Long userId, @PathVariable Long roomId) {
        List<TournamentListsResponseDto> tournamentGiftList = giftService.getTournamentGiftList(roomId);
        return SuccessResponse.ok(tournamentGiftList);
    }

    @PostMapping("/tournament-score")
    public ResponseEntity<SuccessResponse<?>> evaluateTournamentScore(@UserId Long userId, @RequestBody TournamentScoreRequestDto tournamentScoreRequestDto) {
        giftService.evaluateTournamentScore(tournamentScoreRequestDto);
        return SuccessResponse.created(null);
    }

    @GetMapping("/tournament-info/{roomId}")
    public ResponseEntity<SuccessResponse<?>> getTournamentInfo(@UserId Long userId, @PathVariable Long roomId) {
        final TournamentInfoDto tournamentInfo = giftService.getTournamentInfo(userId, roomId);
        return SuccessResponse.ok(tournamentInfo);
    }

    @GetMapping("/ranking/{roomId}")
    public ResponseEntity<SuccessResponse<?>> getRanking(@UserId Long userId, @PathVariable Long roomId) {
        final List<TournamentRankingResponseDto> ranking = giftService.getTournamentRanking(roomId);
        return SuccessResponse.ok(ranking);
    }

    @GetMapping("/friend/{roomId}")
    public ResponseEntity<SuccessResponse<?>> getFriendGift(@UserId Long userId, @PathVariable Long roomId) {
        TournamentStartDateResponseDto tournamentStartDate = giftService.getTournamentStartDate(roomId);
        final List<FriendGiftDto> friendGiftList = giftService.getFriendGift(userId, roomId);

        Map<String, Object> result = new HashMap<>();
        result.put("tournamentStartDate", tournamentStartDate);
        result.put("friendGiftDto", friendGiftList);
        return SuccessResponse.ok(result);

    }


}
