package com.musicpedia.musicpediaapi.comment.reply_comment;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentUpdateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentPage;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.comment.comment.service.CommentService;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.request.ReplyCommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.request.ReplyCommentUpdateRequest;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.response.ReplyCommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.dto.response.ReplyCommentPage;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.entity.ReplyComment;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.repository.ReplyCommentRepository;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.service.ReplyCommentService;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReplyCommentServiceTest {
    @Mock
    private ReplyCommentRepository replyCommentRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private ReplyCommentService replyCommentService;

    private Member member;
    private Comment comment;

    @BeforeEach
    public void 회원과_코멘트_초기화() throws NoSuchFieldException, IllegalAccessException {
        Member savedMember = testMemberBuilder();
        Class<?> memberClass = Member.class;
        Field idField = memberClass.getDeclaredField("id");
        idField.setAccessible(true); // private 필드에 접근 가능하게 설정
        idField.set(savedMember, 1L); // ID 값을 Reflection을 사용하여 설정

        Comment savedComment = testCommentBuilder();
        Class<?> commentClass = Comment.class;
        Field commentIdField = commentClass.getDeclaredField("id");
        commentIdField.setAccessible(true); // private 필드에 접근 가능하게 설정
        commentIdField.set(savedComment, 1L); // ID 값을 Reflection을 사용하여 설정

        member = savedMember;
        comment = savedComment;
    }

    @Test
    @DisplayName("[Service] 코멘트 답변 저장 - 성공")
    public void 코멘트_저장_성공() {
        //given
        long memberId = 1L;
        ReplyCommentCreateRequest replyCommentCreateRequest = testCreateRequest();

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(commentRepository.findById(anyLong()))
                .willReturn(Optional.of(comment));
        given(replyCommentRepository.save(any()))
                .willReturn(testReplyCommentBuilder());

        // when
        ReplyCommentDetail replyCommentDetail = replyCommentService.saveReplyComment(memberId, replyCommentCreateRequest);

        // then
        assertThat(replyCommentDetail.getContent()).isEqualTo("저장 테스트: 좋은 감상평이네요.");
    }

    private Member testMemberBuilder() {
        return Member.builder()
                .description("검정치마 좋아합니다.")
                .email("mupie@gmail.com")
                .name("김뮤피")
                .profileImage("this is profile image")
                .oauthInfo(
                        OAuthInfo.builder()
                                .provider(OAuthProvider.GOOGLE)
                                .oid("oauth id")
                                .build()
                )
                .build();
    }

    private ReplyCommentCreateRequest testCreateRequest() {
        ReplyCommentCreateRequest replyCommentCreateRequest = new ReplyCommentCreateRequest();
        replyCommentCreateRequest.setContent("저장 테스트: 좋은 감상평이네요.");
        replyCommentCreateRequest.setSpotifyId("1tfAfSTJHXtmgkzDwBasOp");
        replyCommentCreateRequest.setCommentId(1L);

        return replyCommentCreateRequest;
    }

    private Comment testCommentBuilder() {
        return Comment.builder()
                .content("저장 테스트: 이번 앨범 좋네요")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .score("4.5")
                .build();
    }

    private ReplyComment testReplyCommentBuilder() {
        return ReplyComment.builder()
                .content("저장 테스트: 좋은 감상평이네요.")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .comment(comment)
                .build();
    }

    private Page<ReplyComment> testReplyCommentPageBuilder() {
        ReplyComment replyComment1 = ReplyComment.builder()
                .content("대댓글 1")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .member(member)
                .comment(comment)
                .build();

        ReplyComment replyComment2 = ReplyComment.builder()
                .content("대댓글 2")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .member(member)
                .comment(comment)
                .build();

        return new PageImpl<>(List.of(replyComment1, replyComment2));
    }
}
