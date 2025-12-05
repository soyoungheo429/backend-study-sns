package com.example.devSns.service;

import com.example.devSns.domain.Comment;
import com.example.devSns.domain.Member;
import com.example.devSns.domain.Post;
import com.example.devSns.dto.CommentCreateRequest;
import com.example.devSns.repository.CommentRepository;
import com.example.devSns.repository.MemberRepository;
import com.example.devSns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Comment create(Long postId, CommentCreateRequest request) {
        if (!StringUtils.hasText(request.getAuthor())) {
            throw new IllegalArgumentException("작성자명은 필수입니다.");
        }

        if (!StringUtils.hasText(request.getContent())) {
            throw new IllegalArgumentException("댓글 내용은 비어 있을 수 없습니다.");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + postId));

        Member member = memberRepository.findByName(request.getAuthor())
                .orElseGet(() -> memberRepository.save(Member.create(request.getAuthor())));

        Comment comment = Comment.create(
                member,
                request.getContent(),
                post
        );

        return commentRepository.save(comment);
    }
}
