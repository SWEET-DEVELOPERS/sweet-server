package org.sopt.sweet.domain.gift.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.sweet.domain.gifter.entity.Gifter;
import org.sopt.sweet.domain.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "gift")
@Entity
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_id")
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int cost;

    private String img;

    @Builder.Default
    private int score = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gifter_id")
    private Gifter gifter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
