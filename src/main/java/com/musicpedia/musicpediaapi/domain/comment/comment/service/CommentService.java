package com.musicpedia.musicpediaapi.domain.comment.comment.service;

import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentPage;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.rating.service.RatingService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    private final RatingService ratingService;

    @Transactional
    public CommentDetail saveComment(long memberId, CommentCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));

        Comment comment = request.toComment();
        comment.updateMember(member);
        commentRepository.save(comment);

        return comment.toCommentDetail();
    }

    public CommentPage getComments(long memberId, String spotifyId, Pageable pageable) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));

        Page<Comment> comments = commentRepository.findAllBySpotifyId(spotifyId, pageable);

        return CommentPage.from(comments);
    }
}
