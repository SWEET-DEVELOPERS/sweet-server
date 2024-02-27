package org.sopt.sweet.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.sweet.domain.member.constant.SocialType;
import org.sopt.sweet.global.common.BaseTimeEntity;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public void setProfileImg(String profileImage) {
        this.profileImg = profileImage;
    }
}
