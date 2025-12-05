package com.example.devSns.controller;

import com.example.devSns.dto.CommentSummaryResponse;
import com.example.devSns.dto.MemberLikesResponse;
import com.example.devSns.dto.MemberResponse;
import com.example.devSns.dto.PostSummaryResponse;
import com.example.devSns.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/search")
    public List<MemberResponse> search(@RequestParam(required = false) String name) {
        return memberService.searchMembers(name);
    }

    @GetMapping("/{memberId}/posts")
    public List<PostSummaryResponse> posts(@PathVariable Long memberId) {
        return memberService.getMemberPosts(memberId);
    }

    @GetMapping("/{memberId}/comments")
    public List<CommentSummaryResponse> comments(@PathVariable Long memberId) {
        return memberService.getMemberComments(memberId);
    }

    @GetMapping("/{memberId}/likes")
    public MemberLikesResponse likes(@PathVariable Long memberId) {
        return memberService.getMemberLikes(memberId);
    }
}