package com.example.devSns.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {

    @NotBlank(message = "작성자명은 필수입니다.") // validation
    private String author;

    @NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.")
    private String content;
}


