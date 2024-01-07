package com.musicpedia.musicpediaapi.domain.member.service;

import com.musicpedia.musicpediaapi.domain.artist.liked_artist.repository.LikedArtistRepository;
import com.musicpedia.musicpediaapi.domain.member.dto.request.MemberUpdateRequest;
import com.musicpedia.musicpediaapi.domain.member.dto.request.PresignedUrlRequest;
import com.musicpedia.musicpediaapi.domain.member.dto.response.MemberDetail;
import com.musicpedia.musicpediaapi.domain.member.dto.response.PresignedUrlResponse;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.rating.entity.Type;
import com.musicpedia.musicpediaapi.domain.rating.repository.RatingRepository;
import com.musicpedia.musicpediaapi.global.client.aws.S3Client;
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
    private final S3Client s3Client;

    public MemberDetail getMemberDetail(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        return setRatingCntAndGetMemberDetail(member);
    }

    private MemberDetail setRatingCntAndGetMemberDetail(Member member) {
        long albumCnt = ratingRepository.countAllByMemberAndType(member, Type.ALBUM);
        long trackCnt = ratingRepository.countAllByMemberAndType(member, Type.TRACK);
        long artistCnt = likedArtistRepository.countAllByMember(member);

        MemberDetail memberDetail = member.toMemberDetail();
        memberDetail.setRatedAlbumCnt(albumCnt);
        memberDetail.setRatedTrackCnt(trackCnt);
        memberDetail.setFavoriteArtistCnt(artistCnt);

        return memberDetail;
    }

    @Transactional
    public void updateMember(long memberId, MemberUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        member.updateMember(request);
    }

    @Transactional
    public void deleteMember(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

    public PresignedUrlResponse generatePresignedUrl(long memberId, PresignedUrlRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        String category = request.getCategory();
        String fileName = request.getFileName();
        String preSignedUrl = s3Client.getPreSignedUrl(category, fileName);

        return PresignedUrlResponse.builder()
                .presignedUrl(preSignedUrl)
                .build();
    }
}
