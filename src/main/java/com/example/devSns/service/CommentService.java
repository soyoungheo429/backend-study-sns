package com.example.devSns.service;

import com.example.devSns.domain.Comment;
import com.example.devSns.domain.Post;
import com.example.devSns.dto.CommentCreateRequest;
import com.example.devSns.repository.CommentRepository;
import com.example.devSns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment create(Long postId, CommentCreateRequest request) {

        // 댓글이 달릴 Post 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + postId));

        // Comment 엔티티 생성 (Setter 없음)
        Comment comment = Comment.create(
                request.getAuthor(),
                request.getContent(),
                post
        );

        return commentRepository.save(comment);
    }
}
