package org.sopt.sweet.domain.member.dto.response;

import java.util.List;

public record MyPageResponseDto(
        MemberInfoDto memberInfo,
        List<ClosedRoomResponseDto> closedRooms,
        List<ActiveRoomResponseDto> activeRooms) {
}
