package org.sopt.sweet.domain.room.repository;

import org.sopt.sweet.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
