package com.musicpedia.musicpediaapi.domain.rating.service;

import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.rating.dto.AverageScoreDTO;
import com.musicpedia.musicpediaapi.domain.rating.dto.RatingScoreDTO;
import com.musicpedia.musicpediaapi.domain.rating.dto.Score;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

    @Transactional
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

    public Score getScore(long memberId, String spotifyId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));

        String averageScore = getAverageScore(spotifyId);
        String ratingScore = getRatingScore(spotifyId, member);

        return Score.builder()
                .ratingScore(ratingScore)
                .averageScore(averageScore)
                .build();
    }

    public String getRatingScore(String spotifyId, Member member) {
        Optional<Rating> foundRating = ratingRepository.findBySpotifyIdAndMember(spotifyId, member);
        if (foundRating.isPresent()) {
            Rating rating = foundRating.get();
            return rating.getScore();
        }

        return "0";
    }

    public String getAverageScore(String spotifyId) {
        Double calculatedAverageScore = ratingRepository.calculateAverageScoreBySpotifyId(spotifyId);
        if (calculatedAverageScore == null) {
            return "0";
        }

        return calculatedAverageScore.toString();
    }

    public Map<String, Score> getScores(long memberId, List<String> spotifyIds) {
        Map<String, String> averageScores = getAverageScores(spotifyIds);
        Map<String, String> ratingScores = getRatingScores(memberId, spotifyIds);

        return getScores(spotifyIds, averageScores, ratingScores);
    }

    private Map<String, String> getAverageScores(List<String> spotifyIds) {
        Map<String, String> averageScores = new HashMap<>();
        spotifyIds.forEach(spotifyId -> averageScores.put(spotifyId, "0"));

        List<AverageScoreDTO> foundAverageScores = ratingRepository.calculateAverageScoresBySpotifyIds(spotifyIds);
        foundAverageScores.forEach(foundAverageScore -> averageScores.put(foundAverageScore.getSpotifyId(), foundAverageScore.getAverageScore().toString()));

        return averageScores;
    }

    private Map<String, String> getRatingScores(long memberId, List<String> spotifyIds) {
        Map<String, String> ratingScores = new HashMap<>();
        spotifyIds.forEach(spotifyId -> ratingScores.put(spotifyId, "0"));

        List<RatingScoreDTO> foundRatingScores = ratingRepository.calculateRatingScoresBySpotifyIds(memberId, spotifyIds);
        foundRatingScores.forEach(foundRatingScore -> ratingScores.put(foundRatingScore.getSpotifyId(), foundRatingScore.getRatingScore()));

        return ratingScores;
    }

    private Map<String, Score> getScores(List<String> spotifyIds, Map<String, String> averageScores, Map<String, String> ratingScores) {
        Map<String, Score> scores = new HashMap<>();
        spotifyIds.forEach(spotifyId -> scores.put(spotifyId, Score.builder().averageScore(averageScores.get(spotifyId)).ratingScore(ratingScores.get(spotifyId)).build()));

        return scores;
    }

    @Transactional
    public void updateRating(long memberId, RatingUpdateRequest request) {
        String spotifyId = request.getSpotifyId();
        String score = request.getScore();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Rating rating = ratingRepository.findBySpotifyIdAndMember(spotifyId, member)
                .orElseThrow(() -> new NoResultException("해당하는 평가 항목을 찾을 수 없습니다."));
        rating.updateScore(score);

        List<Comment> comments = commentRepository.findAllBySpotifyIdAndMember(spotifyId, member);
        comments.forEach(comment -> comment.updateScore(score));
    }

    @Transactional
    public void deleteRating(long memberId, String spotifyId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Rating rating = ratingRepository.findBySpotifyIdAndMember(spotifyId, member)
                .orElseThrow(() -> new NoResultException("해당하는 평가 항목을 찾을 수 없습니다."));
        ratingRepository.delete(rating);

        List<Comment> comments = commentRepository.findAllBySpotifyIdAndMember(spotifyId, member);
        comments.forEach(comment -> comment.updateScore("0"));
    }

    public RatingPage getRatings(long memberId, Type type, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Page<Rating> ratings = ratingRepository.findAllByMemberAndType(member, type, pageable);

        return RatingPage.from(ratings);
    }
}
