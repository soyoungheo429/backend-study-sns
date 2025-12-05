package com.example.devSns.dto;

import com.example.devSns.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostSummaryResponse {
    private Long id;
    private String author;
    private String content;
    private int likes;
    private LocalDateTime createdAt;

    public static PostSummaryResponse from(Post post) {
        return new PostSummaryResponse(
                post.getId(),
                post.getAuthorName(),
                post.getContent(),
                post.getLikes(),
                post.getCreatedAt()
        );
    }
}
