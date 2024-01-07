package com.musicpedia.musicpediaapi.comment.comment;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.comment.comment.entity.Comment;
import com.musicpedia.musicpediaapi.domain.comment.comment.repository.CommentRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

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
    @DisplayName("[Repository] 코멘트 저장 - 성공")
    public void Repo_코멘트_저장_성공() {
        //given
        Comment comment = Comment.builder()
                .content("저장 테스트:이번 앨범 좋네요")
                .spotifyId("albumspotifyid")
                .member(member)
                .build();
        Comment savedComment = commentRepository.save(comment);

        //when
        Long id = savedComment.getId();
        Comment foundComment = commentRepository.findById(id)
                .orElseThrow(() -> new NoResultException("해당하는 코멘트를 조회할 수 없습니다."));

        //then
        assertThat(foundComment.getContent())
                .isEqualTo("저장 테스트:이번 앨범 좋네요");
    }

    @Test
    @DisplayName("[Repository] 코멘트 조회 - 성공")
    public void Repo_코멘트_조회_페이징_성공() {
        //given
        String spotifyId = "1tfAfSTJHXtmgkzDwBasOp";
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "likeCount");

        //when
        Page<Comment> comments = commentRepository.findAllBySpotifyId(spotifyId, pageable);

        //then
        assertThat(comments.getTotalPages())
                .isEqualTo(comments.getTotalElements()/comments.getSize() + 1);
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
}
