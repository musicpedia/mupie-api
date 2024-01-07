package com.musicpedia.musicpediaapi.domain.comment.comment.service;

import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.rating.service.RatingService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    private final RatingService ratingService;

    public CommentDetail saveComment(Long memberId, CommentCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));

        Comment comment = request.toComment();
        comment.updateMember(member);
        commentRepository.save(comment);

        String ratingScore = ratingService.getRatingScore(request.getSpotifyId(), member);
        boolean isModified = !comment.createdAt.isEqual(comment.updatedAt);
        String createdAt = comment.createdAt.toString();
        long likeCount = 0L;
        CommentDetail.Writer writer = CommentDetail.Writer.builder()
                .id(memberId)
                .name(member.getName())
                .profileImage(member.getProfileImage())
                .score(ratingScore)
                .build();

        return comment.toCommentDetail(writer, likeCount, isModified, createdAt);
    }
}
