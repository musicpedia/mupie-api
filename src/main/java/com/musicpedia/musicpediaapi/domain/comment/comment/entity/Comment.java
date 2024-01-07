package com.musicpedia.musicpediaapi.domain.comment.comment.entity;


import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE comment SET deleted=true where id=?")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "spotify_id", nullable = false)
    private String spotifyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Builder
    public Comment(
            String content,
            String spotifyId,
            Member member
    ) {
        this.content = content;
        this.spotifyId = spotifyId;
        this.member = member;
    }
}