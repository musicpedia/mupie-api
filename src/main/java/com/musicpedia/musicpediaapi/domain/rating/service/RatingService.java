package com.musicpedia.musicpediaapi.domain.rating.service;

import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.exception.MemberNotFoundException;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.rating.dto.request.RatingCreateRequest;
import com.musicpedia.musicpediaapi.domain.rating.dto.response.RatingDetail;
import com.musicpedia.musicpediaapi.domain.rating.entity.Rating;
import com.musicpedia.musicpediaapi.domain.rating.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MemberRepository memberRepository;

    public RatingDetail saveRating(long memberId, RatingCreateRequest request) {
        Rating rating = request.toRating();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당하는 id의 회원을 찾을 수 없습니다."));
        rating.updateMember(member);

        return ratingRepository.save(rating).toRatingDetail();
    }

    public void getRating(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당하는 id의 회원을 찾을 수 없습니다."));
        member.getRatings().forEach(r ->
            System.out.println(r.getSpotifyId())
        );
    }
}
