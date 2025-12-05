package com.example.devSns.dto;

import com.example.devSns.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String name;
    private int postCount;
    private int commentCount;
    private int totalLikes;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getPosts().size(),
                member.getComments().size(),
                member.getTotalLikesReceived()
        );
    }
}
