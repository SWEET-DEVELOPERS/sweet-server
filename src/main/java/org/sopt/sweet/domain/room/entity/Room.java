package org.sopt.sweet.domain.room.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.sweet.domain.member.entity.Member;
import org.sopt.sweet.domain.room.constant.TournamentDuration;
import org.sopt.sweet.global.common.BaseTimeEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room")
@Entity
public class Room extends BaseTimeEntity {

    private static final int DEFAULT_NUMBER = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(nullable = false)
    private String gifteeName;

    private String imageUrl;

    private int gifterNumber;

    @Column(nullable = false)
    private LocalDateTime deliveryDate;

    @Column(nullable = false)
    private LocalDateTime tournamentStartDate;

    @Column(nullable = false)
    private TournamentDuration tournamentDuration;

    private String invitationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private Member host;

    @Builder
    public Room(String gifteeName, String imageUrl, LocalDateTime deliveryDate, LocalDateTime tournamentStartDate, TournamentDuration tournamentDuration, String invitationCode, Member host) {
        this.gifteeName = gifteeName;
        this.imageUrl = imageUrl;
        this.gifterNumber = DEFAULT_NUMBER;
        this.deliveryDate = deliveryDate;
        this.tournamentStartDate = tournamentStartDate;
        this.tournamentDuration = tournamentDuration;
        this.invitationCode = invitationCode;
        this.host = host;
    }

    public void setGifterNumber(int gifterNumber) {
        this.gifterNumber = gifterNumber;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setGifteeName(String gifteeName) {
        this.gifteeName = gifteeName;
    }

    public  void setTournamentStartDate(LocalDateTime tournamentStartDate) { this.tournamentStartDate = tournamentStartDate;
    }
}
