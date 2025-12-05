package com.example.devSns.controller;

import com.example.devSns.domain.Post;
import com.example.devSns.dto.PostCreateRequest;
import com.example.devSns.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@Valid @RequestBody PostCreateRequest request) {
        return postService.create(request);
    }
}


