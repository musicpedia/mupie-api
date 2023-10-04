package com.musicpedia.musicpediaapi.domain.member.controller;

import com.musicpedia.musicpediaapi.domain.member.dto.MemberInfo;
import com.musicpedia.musicpediaapi.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "member")
@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity<MemberInfo> getMember(HttpServletRequest httpServletRequest) {
        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString());
        return ResponseEntity.ok(memberService.getMemberInfo(memberId));
    }
}
