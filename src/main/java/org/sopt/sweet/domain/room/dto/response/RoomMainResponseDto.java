package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
@Builder
public record RoomMainResponseDto(
        int gifterNumber,
        String gifteeName,
        String invitationCode,
        LocalDateTime tournamentStartDate,
        List<RoomMyGiftDto> roomMyGiftDtoList,
        List<RoomFriendsGiftDto> roomFriendsGiftDtoList,
        List<HotProductGiftDto> hotProductGiftDtoList
) {
    public static RoomMainResponseDto of(int gifterNumber,
                                         String gifteeName,
                                         String invitationCode,
                                         LocalDateTime tournamentStartDate,
                                         List<RoomMyGiftDto> roomMyGiftDtoList,
                                         List<RoomFriendsGiftDto> roomFriendsGiftDtoList,
                                         List<HotProductGiftDto> hotProductGiftDtoList){
        return RoomMainResponseDto.builder()
                .gifterNumber(gifterNumber)
                .gifteeName(gifteeName)
                .invitationCode(invitationCode)
                .tournamentStartDate(tournamentStartDate)
                .roomMyGiftDtoList(roomMyGiftDtoList)
                .roomFriendsGiftDtoList(roomFriendsGiftDtoList)
                .hotProductGiftDtoList(hotProductGiftDtoList)
                .build();
    }
}
