package com.musicpedia.musicpediaapi.domain.comment.reply_comment.entity;

import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.response.ReplyCommentDetail;
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

    @Column(name = "score", nullable = false)
    private String score = "0";

    @Column(name = "is_modified")
    private boolean isModified = false;

    @Column(name = "like_cnt", nullable = false)
    private long likeCount = 0;

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
            String score,
            Comment comment,
            Member member
    ) {
        this.content = content;
        this.spotifyId = spotifyId;
        this.score = score;
        this.comment = comment;
        this.member = member;
    }

    public void updateComment(Comment comment) {
        this.comment = comment;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateScore(String score) {
        this.score = score;
    }

    public ReplyCommentDetail toReplyCommentDetail() {
        ReplyCommentDetail.Writer writer = ReplyCommentDetail.Writer.builder()
                .id(member.getId())
                .name(member.getName())
                .score(score)
                .profileImage(member.getProfileImage())
                .build();

        return ReplyCommentDetail.builder()
                .id(id)
                .content(content)
                .spotifyId(spotifyId)
                .writer(writer)
                .likeCount(likeCount)
                .isModified(isModified)
                .createdAt(createdAt)
                .build();
    }
}
