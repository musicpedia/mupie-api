package com.musicpedia.musicpediaapi.domain.member.controller;

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
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @GetMapping()
    public ResponseEntity<String> test(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(httpServletRequest.getAttribute("memberId").toString());
    }
}
