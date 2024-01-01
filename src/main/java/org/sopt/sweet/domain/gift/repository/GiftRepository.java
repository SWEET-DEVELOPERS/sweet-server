package org.sopt.sweet.domain.gift.repository;

import org.sopt.sweet.domain.gift.entity.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftRepository extends JpaRepository<Gift, Long> {
}
