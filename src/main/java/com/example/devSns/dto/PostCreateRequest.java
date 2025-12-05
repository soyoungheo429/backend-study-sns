package com.example.devSns.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "작성자명은 필수입니다.")
    private String author;

    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    private String content;
}
