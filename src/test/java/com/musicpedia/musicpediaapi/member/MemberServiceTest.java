package com.musicpedia.musicpediaapi.member;

import com.musicpedia.musicpediaapi.domain.auth.entity.OAuthProvider;
import com.musicpedia.musicpediaapi.domain.artist.liked_artist.repository.LikedArtistRepository;
import com.musicpedia.musicpediaapi.domain.member.dto.request.MemberUpdateRequest;
import com.musicpedia.musicpediaapi.domain.member.dto.response.MemberDetail;
import com.musicpedia.musicpediaapi.domain.member.entity.Member;
import com.musicpedia.musicpediaapi.domain.member.entity.OAuthInfo;
import com.musicpedia.musicpediaapi.domain.member.repository.MemberRepository;
import com.musicpedia.musicpediaapi.domain.member.service.MemberService;
import com.musicpedia.musicpediaapi.domain.rating.repository.RatingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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
        assertThat(memberDetail.getName()).isEqualTo("김뮤피");
    }

    @Test
    @DisplayName("[Service] 회원 이름 수정 - 성공")
    public void 회원_이름_수정_성공() {
        // given
        Member member = testUserBuilder();
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        MemberUpdateRequest request = testNameUpdateRequest();

        // when
        memberService.updateMember(1L, request);

        // then
        assertThat(member.getName()).isEqualTo("곽의준");
    }

    @Test
    @DisplayName("[Service] 회원 프로필 이미지 수정 - 성공")
    public void 회원_프로필_이미지_수정_성공() {
        // given
        Member member = testUserBuilder();
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        MemberUpdateRequest request = testProfileImageUpdateRequest();

        // when
        memberService.updateMember(1L, request);

        // then
        assertThat(member.getProfileImage()).isEqualTo("this is new profile image");
    }

    @Test
    @DisplayName("[Service] 회원 상태 메시지 수정 - 성공")
    public void 회원_상태_메시지_수정_성공() {
        // given
        Member member = testUserBuilder();
        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(member));
        MemberUpdateRequest request = testDescriptionUpdateRequest();

        // when
        memberService.updateMember(1L, request);

        // then
        assertThat(member.getDescription()).isEqualTo("뉴진스 사랑해");
    }

    private static Member testUserBuilder() {
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

    private static MemberUpdateRequest testNameUpdateRequest() {
        MemberUpdateRequest request = new MemberUpdateRequest();
        request.setName("곽의준");
        request.setProfileImage("this is profile image");
        request.setDescription("검정치마 좋아합니다.");

        return request;
    }

    private static MemberUpdateRequest testProfileImageUpdateRequest() {
        MemberUpdateRequest request = new MemberUpdateRequest();
        request.setName("김뮤피");
        request.setProfileImage("this is new profile image");
        request.setDescription("검정치마 좋아합니다.");

        return request;
    }

    private static MemberUpdateRequest testDescriptionUpdateRequest() {
        MemberUpdateRequest request = new MemberUpdateRequest();
        request.setName("김뮤피");
        request.setProfileImage("this is profile image");
        request.setDescription("뉴진스 사랑해");

        return request;
    }
}
