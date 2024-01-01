package org.sopt.sweet.domain.gifter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.sopt.sweet.domain.gifter.constant.TournamentDuration;
import org.sopt.sweet.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "gifter")
@Entity
public class Gifter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gifter_id")
    private Long id;

    @Column(nullable = false)
    private String gifteeName;

    private String img;

    @Builder.Default
    private int gifterNum = 0;

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
