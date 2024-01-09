package com.musicpedia.musicpediaapi.domain.comment.reply_comment.service;

import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.request.ReplyCommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.request.ReplyCommentUpdateRequest;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.response.ReplyCommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.response.ReplyCommentPage;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.entity.ReplyComment;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.repository.ReplyCommentRepository;
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
public class ReplyCommentService {
    private final ReplyCommentRepository replyCommentRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReplyCommentDetail saveReplyComment(long memberId, ReplyCommentCreateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));

        ReplyComment replyComment = request.toReplyComment();

        Long commentId = request.getCommentId();
        Comment comment = commentRepository.findByIdAndSpotifyId(commentId, replyComment.getSpotifyId())
                .orElseThrow(() -> new NoResultException("해당하는 id의 코멘트를 찾을 수 없습니다."));
        replyComment.updateComment(comment);
        replyComment.updateMember(member);

        replyCommentRepository.save(replyComment);

        return replyComment.toReplyCommentDetail();
    }

    public ReplyCommentPage getReplyComments(long memberId, long commentId, Pageable pageable) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 코멘트를 찾을 수 없습니다."));

        Page<ReplyComment> replyComments = replyCommentRepository.findAllByComment(comment, pageable);

        return ReplyCommentPage.from(replyComments);
    }

    @Transactional
    public void updateReplyComment(long memberId, ReplyCommentUpdateRequest request) {
        Long replyCommentId = request.getReplyCommentId();
        String content = request.getContent();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        ReplyComment replyComment = replyCommentRepository.findByIdAndMember(replyCommentId, member)
                .orElseThrow(() -> new NoResultException("해당하는 id의 코멘트 답변을 찾을 수 없습니다."));

        replyComment.updateContent(content);
        replyComment.updateModified();
    }

    @Transactional
    public void deleteReplyComment(long memberId, long replyCommentId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("해당하는 id의 회원을 찾을 수 없습니다."));
        ReplyComment replyComment = replyCommentRepository.findByIdAndMember(replyCommentId, member)
                .orElseThrow(() -> new NoResultException("해당하는 id의 코멘트 답변을 찾을 수 없습니다."));

        replyCommentRepository.delete(replyComment);
    }
}