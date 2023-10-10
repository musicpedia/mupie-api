package com.musicpedia.musicpediaapi.domain.rating.service;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingCreateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingUpdateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingDetail;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingPage;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.rating.repository.RatingRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MemberRepository memberRepository;

    public RatingDetail saveRating(long memberId, RatingCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Optional<Rating> foundRating = ratingRepository.findBySpotifyIdAndMember(request.getSpotifyId(), member);
        if (foundRating.isPresent()) {
            throw new NonUniqueResultException("이미 평가되어있는 항목입니다.");
        }

        Rating rating = request.toRating();
        rating.updateMember(member);

        return ratingRepository.save(rating).toRatingDetail();
    }

    public void updateRating(long memberId, RatingUpdateRequest request) {
        String spotifyId = request.getSpotifyId();
        String score = request.getScore();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Rating rating = ratingRepository.findBySpotifyIdAndMember(spotifyId, member)
                .orElseThrow(() -> new NoResultException("해당하는 평가 항목을 찾을 수 없습니다."));
        rating.updateScore(score);
    }

    public void deleteRating(long memberId, String spotifyId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Rating rating = ratingRepository.findBySpotifyIdAndMember(spotifyId, member)
                .orElseThrow(() -> new NoResultException("해당하는 평가 항목을 찾을 수 없습니다."));
        ratingRepository.delete(rating);
    }

    public RatingPage getRatings(long memberId, Type type, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Page<Rating> ratings = ratingRepository.findAllByMemberAndType(member, type, pageable);

        return RatingPage.from(ratings);
    }
}
