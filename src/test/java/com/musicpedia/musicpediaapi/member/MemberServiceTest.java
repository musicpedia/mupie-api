package com.musicpedia.musicpediaapi.member;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.like.artist.repository.LikedArtistRepository;
import com.musicpedia.musicpediaapi.domain.member.dto.MemberDetail;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.member.service.MemberService;
import com.musicpedia.musicpediaapi.domain.rating.repository.RatingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private LikedArtistRepository likedArtistRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("[Service] 아이디로 유저 조회 - 성공")
    public void 아이디로_유저_조회_성공() throws NoSuchFieldException, IllegalAccessException {
        // given
        Member savedMember = testUserBuilder();
        Class<?> memberClass = Member.class;
        Field idField = memberClass.getDeclaredField("id");
        idField.setAccessible(true); // private 필드에 접근 가능하게 설정
        idField.set(savedMember, 1L); // ID 값을 Reflection을 사용하여 설정

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.ofNullable(savedMember));

        // when
        MemberDetail memberDetail = memberService.getMemberDetail(1L);

        // then
        Assertions.assertThat(memberDetail.getName()).isEqualTo("김뮤피");
    }

    private Member testUserBuilder() {
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
