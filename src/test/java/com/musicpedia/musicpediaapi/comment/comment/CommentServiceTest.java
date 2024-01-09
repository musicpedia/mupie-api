package com.musicpedia.musicpediaapi.comment.comment;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentPage;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.comment.comment.service.CommentService;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
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

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CommentService commentService;

    private Member member;

    @BeforeEach
    public void 회원_초기화() throws NoSuchFieldException, IllegalAccessException {
        Member savedMember = testMemberBuilder();
        Class<?> memberClass = Member.class;
        Field idField = memberClass.getDeclaredField("id");
        idField.setAccessible(true); // private 필드에 접근 가능하게 설정
        idField.set(savedMember, 1L); // ID 값을 Reflection을 사용하여 설정

        member = savedMember;
    }

    @Test
    @DisplayName("[Service] 코멘트 저장 - 성공")
    public void 코멘트_저장_성공() {
        //given
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(commentRepository.save(any()))
                .willReturn(testCommentBuilder());

        // when
        CommentDetail commentDetail = commentService.saveComment(1L, testCreateRequest());

        // then
        assertThat(commentDetail.getContent()).isEqualTo("저장 테스트: 이번 앨범 좋네요");
    }

    @Test
    @DisplayName("[Service] 코멘트 조회 - 성공")
    public void 코멘트_조회_성공() {
        // given
        long memberId = 1L;
        String spotifyId = "1tfAfSTJHXtmgkzDwBasOp";
        Pageable pageable = Pageable.unpaged(); // For simplicity, you might want to use a real Pageable

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        Page<Comment> comments = testCommentPageBuilder();
        given(commentRepository.findAllBySpotifyId(anyString(), any(Pageable.class))).willReturn(comments);

        // when
        CommentPage commentPage = commentService.getComments(memberId, spotifyId, pageable);

        // Then
        assertThat(commentPage).isNotNull();
        assertThat(commentPage.getTotalCount()).isEqualTo(commentPage.getComments().size());
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

    private CommentCreateRequest testCreateRequest() {
        CommentCreateRequest commentCreateRequest = new CommentCreateRequest();
        commentCreateRequest.setContent("저장 테스트: 이번 앨범 좋네요");
        commentCreateRequest.setSpotifyId("1tfAfSTJHXtmgkzDwBasOp");

        return commentCreateRequest;
    }

    private Comment testCommentBuilder() {
        return Comment.builder()
                .content("저장 테스트: 이번 앨범 좋네요")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .score("4.5")
                .build();
    }

    private CommentDetail testCommentDetailBuilder() {
        return CommentDetail.builder()
                .content("저장 테스트: 이번 앨범 좋네요")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .build();
    }

    private Page<Comment> testCommentPageBuilder() {
        Comment comment1 = Comment.builder()
                .content("댓글 1")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .member(member)
                .score("4.5")
                .build();

        Comment comment2 = Comment.builder()
                .content("댓글 2")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .member(member)
                .score("4.0")
                .build();

        return new PageImpl<>(List.of(comment1, comment2));
    }
}
