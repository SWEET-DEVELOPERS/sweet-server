package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

@Builder
public record RoomDto(
        String gifteeName,
        int gifterNumber

) {
    public static RoomDto of(String gifteeName, int gifterNumber) {
        return RoomDto.builder()
                .gifteeName(gifteeName)
                .gifterNumber(gifterNumber)
                .build();
    }
}
