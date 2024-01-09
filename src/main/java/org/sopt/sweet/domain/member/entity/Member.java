package org.sopt.sweet.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sopt.sweet.domain.member.constant.SocialType;
import org.sopt.sweet.global.common.BaseTimeEntity;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PUBLIC) //수정해야함
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private Long socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    private String profileImg;

    private String nickName;

    @Builder
    public Member(Long socialId, String nickName, SocialType socialType, String profileImg) {
        this.socialId = socialId;
        this.nickName = nickName;
        this.socialType = socialType;
        this.profileImg = profileImg;
    }
}
