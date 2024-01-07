package org.sopt.sweet.domain.room.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.sweet.domain.room.constant.TournamentDuration;
import org.sopt.sweet.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room")
@Entity
public class Room {

    private static final int DEFAULT_NUMBER = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(nullable = false)
    private String gifteeName;

    private String img;

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

}
