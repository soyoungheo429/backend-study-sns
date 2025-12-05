package com.example.devSns.service;

import com.example.devSns.domain.Comment;
import com.example.devSns.domain.Member;
import com.example.devSns.domain.Post;
import com.example.devSns.dto.CommentCreateRequest;
import com.example.devSns.repository.CommentRepository;
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
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("댓글 생성 테스트")
    void createComment() {
        // given
        Member member = Member.create("허소영");
        Post post = Post.builder()
                .id(1L)
                .member(member)
                .content("테스트 게시글")
                .likes(0)
                .build();

        CommentCreateRequest request = new CommentCreateRequest("댓글러", "좋은 글이네요!");

        Member commenter = Member.create("댓글러");

        Comment comment = Comment.builder()
                .id(10L)
                .member(commenter)
                .content("좋은 글이네요!")
                .post(post)
                .build();

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(memberRepository.findByName("댓글러")).thenReturn(Optional.of(commenter));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // when
        Comment savedComment = commentService.create(post.getId(), request);

        // then
        assertThat(savedComment.getMember().getName()).isEqualTo("댓글러");
        assertThat(savedComment.getContent()).isEqualTo("좋은 글이네요!");
        assertThat(savedComment.getPost().getId()).isEqualTo(1L);
        verify(postRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("존재하지 않는 게시글에 댓글 생성 시 예외 발생")
    void createCommentWhenPostNotFound() {
        // given
        CommentCreateRequest request = new CommentCreateRequest("댓글러", "내용");

        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.create(999L, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("게시글을 찾을 수 없습니다.");
    }
}
