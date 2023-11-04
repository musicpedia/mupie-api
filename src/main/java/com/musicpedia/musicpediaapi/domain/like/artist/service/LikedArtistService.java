package com.musicpedia.musicpediaapi.domain.like.artist.service;

import com.musicpedia.musicpediaapi.domain.like.artist.dto.LikedArtistDetail;
import com.musicpedia.musicpediaapi.domain.like.artist.dto.LikedArtistPage;
import com.musicpedia.musicpediaapi.domain.like.artist.dto.request.LikedArtistCreateRequest;
import com.musicpedia.musicpediaapi.domain.like.artist.entity.LikedArtist;
import com.musicpedia.musicpediaapi.domain.like.artist.repository.LikedArtistRepository;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikedArtistService {
    private final LikedArtistRepository likedArtistRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public LikedArtistDetail likeArtist(long memberId, LikedArtistCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Optional<LikedArtist> foundArtist = likedArtistRepository.findBySpotifyIdAndMember(request.getSpotifyId(), member);

        if (foundArtist.isPresent()) { // 이미 좋아요를 눌렀던 적이 있으면 상태 변경(좋아요 혹은 취소)
            LikedArtist artist = foundArtist.get();
            return artist.toLikedArtistDetail();
        }

        LikedArtist likedArtist = request.toLikedArtist();
        likedArtist.updateMember(member);
        return likedArtistRepository.save(likedArtist).toLikedArtistDetail();
    }

    public boolean isMemberLike(long memberId, String spotifyId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Optional<LikedArtist> likedArtist = likedArtistRepository.findBySpotifyIdAndMember(spotifyId, member);

        return likedArtist.isPresent();
    }

    @Transactional
    public void deleteArtist(long memberId, String spotifyId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Optional<LikedArtist> likedArtist = likedArtistRepository.findBySpotifyIdAndMember(spotifyId, member);
        likedArtist.ifPresent(likedArtistRepository::delete);
    }

    public LikedArtistPage getLikedArtists(long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Page<LikedArtist> likedArtists = likedArtistRepository.findAllByMember(member, pageable);

        return LikedArtistPage.from(likedArtists);
    }
}
