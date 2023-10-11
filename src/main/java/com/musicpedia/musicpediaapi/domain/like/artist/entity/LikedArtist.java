package com.musicpedia.musicpediaapi.domain.like.artist.entity;

import com.musicpedia.musicpediaapi.domain.like.artist.dto.LikedArtistDetail;
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
@Table(name = "liked_artist")
@SQLDelete(sql = "UPDATE LIKED_ARTIST SET deleted=true where id=?")
@Where(clause = "deleted is false")
public class LikedArtist extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "spotify_id", nullable = false)
    private String spotifyId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Builder
    public LikedArtist(
            String name,
            String spotifyId,
            String thumbnail,
            Member member
    ) {
        this.spotifyId = spotifyId;
        this.name = name;
        this.thumbnail = thumbnail;
        this.member = member;
    }

    public void updateMember(Member member) {
        this.member = member;
    }

    public LikedArtistDetail toLikedArtistDetail() {
        return LikedArtistDetail.builder()
                .type("artist")
                .name(this.name)
                .spotifyId(this.spotifyId)
                .thumbnail(this.thumbnail)
                .build();
    }
}
