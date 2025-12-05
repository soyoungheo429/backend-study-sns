package com.example.devSns.service;

import com.example.devSns.domain.Post;
import com.example.devSns.dto.PostCreateRequest;
import com.example.devSns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public Post create(PostCreateRequest request) {
        Post post = Post.create(
                request.getAuthor(),
                request.getContent()
        );

        return postRepository.save(post);
    }
}
