package com.example.devSns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLikesResponse {
    private Long memberId;
    private String name;
    private int totalLikes;
}
