package com.musicpedia.musicpediaapi.domain.rating.entity;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingDetail;
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
@Table(name = "rating")
@SQLDelete(sql = "UPDATE RATING SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class Rating extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "spotify_id", nullable = false)
    private String spotifyId;

    @Column(name = "score")
    private String score;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "deleted")
    private boolean deleted = false;

    @PreRemove
    public void deleteRating() {
        this.deleted = true;
    }

    @Builder
    public Rating(
            Type type,
            String spotifyId,
            String score,
            String name,
            String thumbnail,
            String releaseDate,
            Member member
    ) {
        this.type = type;
        this.spotifyId = spotifyId;
        this.score = score;
        this.name = name;
        this.thumbnail = thumbnail;
        this.releaseDate = releaseDate;
        this.member = member;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public void updateScore(String score) {
        this.score = score;
    }

    public RatingDetail toRatingDetail() {
        return RatingDetail.builder()
                .type(this.type)
                .score(this.score)
                .spotifyId(this.spotifyId)
                .name(this.name)
                .thumbnail(this.thumbnail)
                .releaseDate(this.releaseDate)
                .build();
    }
}
