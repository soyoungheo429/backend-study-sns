package com.example.devSns.service;

import com.example.devSns.domain.Comment;
import com.example.devSns.domain.Post;
import com.example.devSns.dto.CommentCreateRequest;
import com.example.devSns.repository.CommentRepository;
import com.example.devSns.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("댓글 생성 테스트")
    void createComment() {
        // given
        Post post = new Post();
        post.setId(1L);
        post.setAuthor("허소영");
        post.setContent("테스트 게시글");

        CommentCreateRequest request = new CommentCreateRequest();
        request.setAuthor("댓글러");
        request.setContent("좋은 글이네요!");

        Comment comment = Comment.create("댓글러", "좋은 글이네요!", post);

        when(postRepository.findById(1L)).thenReturn(java.util.Optional.of(post));  // mock
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);  // mock

        // when
        Comment savedComment = commentService.create(post.getId(), request);

        // then
        assertThat(savedComment.getAuthor()).isEqualTo("댓글러");
        assertThat(savedComment.getContent()).isEqualTo("좋은 글이네요!");
        assertThat(savedComment.getPost().getId()).isEqualTo(1L);
        verify(postRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("존재하지 않는 게시글에 댓글 생성 시 예외 발생")
    void createCommentWhenPostNotFound() {
        // given
        CommentCreateRequest request = new CommentCreateRequest();
        request.setAuthor("댓글러");
        request.setContent("내용");

        when(postRepository.findById(999L)).thenReturn(java.util.Optional.empty());  // mock

        // when & then
        assertThatThrownBy(() -> commentService.create(999L, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("게시글을 찾을 수 없습니다.");
    }
}

