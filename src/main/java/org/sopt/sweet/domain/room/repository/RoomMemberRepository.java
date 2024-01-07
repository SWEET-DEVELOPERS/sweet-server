package org.sopt.sweet.domain.room.repository;

import org.sopt.sweet.domain.room.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
}
