package com.musicpedia.musicpediaapi.domain.member.service;

import com.musicpedia.musicpediaapi.domain.like.artist.repository.LikedArtistRepository;
import com.musicpedia.musicpediaapi.domain.member.dto.MemberDetail;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.rating.repository.RatingRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RatingRepository ratingRepository;
    private final LikedArtistRepository likedArtistRepository;

    public MemberDetail getMemberDetail(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        return setRatingCntAndgetMemberDetail(member);
    }

    private MemberDetail setRatingCntAndgetMemberDetail(Member member) {
        long albumCnt = ratingRepository.countAllByMemberAndType(member, Type.ALBUM);
        long trackCnt = ratingRepository.countAllByMemberAndType(member, Type.TRACK);
        long artistCnt = likedArtistRepository.countAllByMember(member);

        MemberDetail memberDetail = member.toMemberDetail();
        memberDetail.setRatedAlbumCnt(albumCnt);
        memberDetail.setRatedTrackCnt(trackCnt);
        memberDetail.setFavoriteArtistCnt(artistCnt);

        return memberDetail;
    }
}
