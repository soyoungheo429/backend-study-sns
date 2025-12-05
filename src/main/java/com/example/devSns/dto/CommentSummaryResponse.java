package com.example.devSns.dto;

import com.example.devSns.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentSummaryResponse {
    private Long id;
    private Long postId;
    private String author;
    private String content;
    private LocalDateTime createdAt;

    public static CommentSummaryResponse from(Comment comment) {
        return new CommentSummaryResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getMember() != null ? comment.getMember().getName() : null,
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
