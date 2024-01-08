package org.sopt.sweet.domain.room.repository;

import org.sopt.sweet.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByInvitationCode(String invitationCode);

    Optional<Room> findByInvitationCode(String invitationCode);
}
