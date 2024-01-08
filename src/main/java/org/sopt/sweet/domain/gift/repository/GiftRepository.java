package org.sopt.sweet.domain.gift.repository;

import org.sopt.sweet.domain.gift.entity.Gift;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    long countByRoomAndMember(Room room, Member member);
}
