package org.sopt.sweet.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.sweet.domain.member.constant.SocialType;
import org.sopt.sweet.global.common.BaseTimeEntity;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    private String profileImg;

    private String nickName;

}
