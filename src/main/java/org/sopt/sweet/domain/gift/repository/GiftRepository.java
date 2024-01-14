package org.sopt.sweet.domain.gift.repository;

import org.sopt.sweet.domain.gift.entity.Gift;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.room.entity.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    long countByRoomAndMember(Room room, Member member);

    List<Gift> findByRoomAndMember(Room room, Member member);

    @Query("SELECT g FROM Gift g WHERE g.room = :room AND g.member <> :member ORDER BY g.id DESC")
    List<Gift> findLatestGiftsByRoomAndNotMember(@Param("room") Room room, @Param("member") Member member, Pageable pageable);

    List<Gift> findByRoom(Room room);

    List<Gift> findByRoomOrderByScoreDesc(Room room);

    List<Gift> findByRoomAndMemberNot(Room room, Member member);
}
