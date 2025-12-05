package com.example.devSns.service;

import com.example.devSns.domain.Post;
import com.example.devSns.dto.PostCreateRequest;
import com.example.devSns.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    @DisplayName("게시글 생성 테스트")
    void createPost() {
        // given
        PostCreateRequest request = new PostCreateRequest();
        request.setAuthor("허소영");
        request.setContent("테스트 내용");

        Post savedPost = Post.create("허소영", "테스트 내용");

        when(postRepository.save(any(Post.class))).thenReturn(savedPost); // mock

        // when
        Post post = postService.create(request);

        // then
        assertThat(post.getId()).isNotNull();
        assertThat(post.getAuthor()).isEqualTo("강준이");
        assertThat(post.getContent()).isEqualTo("테스트 내용");
        verify(postRepository, times(1)).save(any(Post.class));  // save가 한번 호출됐는지 검증
    }

    @Test
    @DisplayName("작성자명이 없으면 예외 발생")
    void createPostWithoutAuthor() {
        // given
        PostCreateRequest request = new PostCreateRequest();
        request.setAuthor("");
        request.setContent("내용");

        // when & then
        assertThatThrownBy(() -> postService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("작성자명은 필수입니다.");
    }
}

