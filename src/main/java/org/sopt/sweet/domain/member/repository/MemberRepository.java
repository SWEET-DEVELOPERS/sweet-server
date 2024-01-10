package org.sopt.sweet.domain.member.repository;

import org.sopt.sweet.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findBySocialId(Long socialId);
}
