package org.sopt.sweet.domain.gifter.repository;

import org.sopt.sweet.domain.gifter.entity.GifterMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GifterMemberRepository extends JpaRepository<GifterMember, Long> {
}
