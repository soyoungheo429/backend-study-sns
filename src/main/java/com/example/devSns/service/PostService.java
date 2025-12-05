package com.example.devSns.service;

import com.example.devSns.domain.Member;
import com.example.devSns.domain.Post;
import com.example.devSns.dto.PostCreateRequest;
import com.example.devSns.repository.MemberRepository;
import com.example.devSns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Post create(PostCreateRequest request) {
        if (!StringUtils.hasText(request.getAuthor())) {
            throw new IllegalArgumentException("작성자명은 필수입니다.");
        }

        if (!StringUtils.hasText(request.getContent())) {
            throw new IllegalArgumentException("내용은 비어 있을 수 없습니다.");
        }

        Member member = memberRepository.findByName(request.getAuthor())
                .orElseGet(() -> memberRepository.save(Member.create(request.getAuthor())));

        Post post = Post.create(
                member,
                request.getContent()
        );

        return postRepository.save(post);
    }
}
