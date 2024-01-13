package org.sopt.sweet.domain.room.repository;

import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.room.entity.Room;
import org.sopt.sweet.domain.room.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    Optional<RoomMember> findByRoomAndMember(Room room, Member member);
    List<RoomMember> findByRoomId(Long roomId);

    int countByRoomIdAndTournamentParticipationIsTrue(Long roomId);
    RoomMember findByRoomIdAndMemberId(Long roomId, Long memberId);

    List<RoomMember> findByMemberId(Long memberId);
}
