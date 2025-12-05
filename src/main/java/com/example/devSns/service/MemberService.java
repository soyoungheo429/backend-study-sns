package com.example.devSns.service;

import com.example.devSns.domain.Member;
import com.example.devSns.domain.Post;
import com.example.devSns.dto.CommentSummaryResponse;
import com.example.devSns.dto.MemberLikesResponse;
import com.example.devSns.dto.MemberResponse;
import com.example.devSns.dto.PostSummaryResponse;
import com.example.devSns.repository.CommentRepository;
import com.example.devSns.repository.MemberRepository;
import com.example.devSns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<MemberResponse> searchMembers(String name) {
        String keyword = name == null ? "" : name;
        return memberRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(MemberResponse::from)
                .toList();
    }

    public List<PostSummaryResponse> getMemberPosts(Long memberId) {
        validateMemberExists(memberId);
        return postRepository.findAllByMemberId(memberId).stream()
                .map(PostSummaryResponse::from)
                .collect(Collectors.toList());
    }

    public List<CommentSummaryResponse> getMemberComments(Long memberId) {
        validateMemberExists(memberId);
        return commentRepository.findAllByMemberId(memberId).stream()
                .map(CommentSummaryResponse::from)
                .collect(Collectors.toList());
    }

    public MemberLikesResponse getMemberLikes(Long memberId) {
        Member member = validateMemberExists(memberId);
        int likes = postRepository.findAllByMemberId(memberId).stream()
                .mapToInt(Post::getLikes)
                .sum();
        return new MemberLikesResponse(member.getId(), member.getName(), likes);
    }

    private Member validateMemberExists(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없습니다. id=" + memberId));
    }
}
