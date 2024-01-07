package com.musicpedia.musicpediaapi.domain.comment.reply_comment.entity;

import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
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
@Table(name = "reply_comment")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE reply_comment SET deleted=true where id=?")
public class ReplyComment extends BaseTimeEntity {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;


    @Column(name = "deleted")
    private boolean deleted = false;

    @Builder
    public ReplyComment(
            String content,
            String spotifyId,
            Comment comment,
            Member member
    ) {
        this.content = content;
        this.spotifyId = spotifyId;
        this.comment = comment;
        this.member = member;
    }
}
