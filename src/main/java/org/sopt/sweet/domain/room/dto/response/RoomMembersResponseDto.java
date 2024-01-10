package org.sopt.sweet.domain.room.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RoomMembersResponseDto(
        Long myId,
        int gifterNumber,
        List<RoomMemberDto> roomMemberDtoList
) {
    public static RoomMembersResponseDto of(Long myId, int gifterNumber,
                                            List<RoomMemberDto> roomMemberDtoList){
        return RoomMembersResponseDto.builder()
                .myId(myId)
                .gifterNumber(gifterNumber)
                .roomMemberDtoList(roomMemberDtoList)
                .build();
    }
}
