package org.sopt.sweet.domain.gifter.repository;

import org.sopt.sweet.domain.gifter.entity.Gifter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GifterRepository extends JpaRepository<Gifter, Long> {
}
