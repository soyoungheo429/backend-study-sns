package com.example.devSns.service;

import com.example.devSns.domain.Member;
import com.example.devSns.domain.Post;
import com.example.devSns.dto.PostCreateRequest;
import com.example.devSns.repository.MemberRepository;
import com.example.devSns.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시글 생성 테스트")
    void createPost() {
        // given
        PostCreateRequest request = new PostCreateRequest("허소영", "테스트 내용");

        Member member = Member.create("허소영");
        when(memberRepository.findByName("허소영")).thenReturn(Optional.of(member));

        Post savedPost = Post.builder()
                .id(1L)
                .member(member)
                .content("테스트 내용")
                .likes(0)
                .build();

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // when
        Post post = postService.create(request);

        // then
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getAuthorName()).isEqualTo("허소영");
        assertThat(post.getContent()).isEqualTo("테스트 내용");
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("작성자명이 없으면 예외 발생")
    void createPostWithoutAuthor() {
        // given
        PostCreateRequest request = new PostCreateRequest("", "내용");

        // when & then
        assertThatThrownBy(() -> postService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("작성자명은 필수입니다.");
    }
}
