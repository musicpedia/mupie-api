package com.musicpedia.musicpediaapi.comment.comment;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.comment.comment.service.CommentService;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("[Service] 코멘트 저장 - 성공")
    public void 코멘트_저장_성공() {
        //given
        Member member = testMemberBuilder();

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        given(commentRepository.save(any()))
                .willReturn(testCommentBuilder());

        // when
        CommentDetail commentDetail = commentService.saveComment(1L, testCreateRequest());

        // then
        assertThat(commentDetail.getContent()).isEqualTo("저장 테스트: 이번 앨범 좋네요");
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

    private CommentDetail testCommentBuilder() {
        return CommentDetail.builder()
                .content("저장 테스트: 이번 앨범 좋네요")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .build();
    }
}
