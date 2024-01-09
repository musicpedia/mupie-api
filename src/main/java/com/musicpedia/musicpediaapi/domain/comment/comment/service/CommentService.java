package com.musicpedia.musicpediaapi.domain.comment.comment.service;

import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentUpdateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentPage;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
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

    @Transactional
    public void updateComment(long memberId, CommentUpdateRequest request) {
        Long commentId = request.getCommentId();
        String content = request.getContent();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Comment comment = commentRepository.findByIdAndMember(commentId, member)
                .orElseThrow(() -> new NoResultException("해당하는 코멘트를 찾을 수 없습니다."));
        comment.updateContent(content);
    }

    @Transactional
    public void deleteComment(long memberId, long commentId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Comment comment = commentRepository.findByIdAndMember(commentId, member)
                .orElseThrow(() -> new NoResultException("해당하는 코멘트를 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }
}
