package com.musicpedia.musicpediaapi.comment.reply_comment;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentCreateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.request.CommentUpdateRequest;
import com.musicpedia.musicpediaapi.domain.comment.comment.dto.response.CommentDetail;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.entity.ReplyComment;
import com.musicpedia.musicpediaapi.domain.comment.reply_comment.repository.ReplyCommentRepository;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReplyCommentRepositoryTest {
    @Autowired
    ReplyCommentRepository replyCommentRepository;

    @Autowired
    CommentRepository commentRepository;

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
    @DisplayName("[Repository] 코멘트 답변 저장 - 성공")
    public void Repo_코멘트_답변_저장_성공() {
        //given
        ReplyComment replyComment = ReplyComment.builder()
                .content("저장 테스트:좋은 감상평이네요.")
                .spotifyId("albumspotifyid")
                .score("4.0")
                .comment(comment)
                .member(member)
                .build();
        ReplyComment savedReplyComment = replyCommentRepository.save(replyComment);

        //when
        Long id = savedReplyComment.getId();
        ReplyComment foundReplyComment = replyCommentRepository.findById(id)
                .orElseThrow(() -> new NoResultException("해당하는 코멘트를 조회할 수 없습니다."));

        // then
        assertThat(foundReplyComment.getContent())
                .isEqualTo("저장 테스트:좋은 감상평이네요.");
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

    private Comment testCommentBuilder() {
        return Comment.builder()
                .content("저장 테스트: 이번 앨범 좋네요")
                .spotifyId("1tfAfSTJHXtmgkzDwBasOp")
                .score("4.5")
                .build();
    }
}
