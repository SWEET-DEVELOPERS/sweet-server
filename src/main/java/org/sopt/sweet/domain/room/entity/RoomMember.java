package org.sopt.sweet.domain.room.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.global.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RoomMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(columnDefinition = "TINYINT(1) default 0")
    private boolean tournamentParticipation;

    @Column(name = "firstplace_gift_id")
    private Long firstplaceGiftId;

    @Builder
    public RoomMember(Room room, Member member) {
        this.room = room;
        this.member = member;
    }

    public void setTournamentParticipation(boolean tournamentParticipation) {
        this.tournamentParticipation = tournamentParticipation;
    }

}
