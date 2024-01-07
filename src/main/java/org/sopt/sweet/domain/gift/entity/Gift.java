package org.sopt.sweet.domain.gift.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.sweet.domain.room.entity.Room;
import org.sopt.sweet.domain.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "gift")
@Entity
public class Gift {

    private static final int DEFAULT_SCORE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_id")
    private Long id;

    private String url;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int cost;

    private String img;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Gift(String url, String name, int cost, String img, Room room, Member member) {
        this.url = url;
        this.name = name;
        this.cost = cost;
        this.score = DEFAULT_SCORE;
        this.img = img;
        this.room = room;
        this.member = member;
    }
}
