package avengers.nexus.post.service;

import avengers.nexus.post.domain.Comment;
import avengers.nexus.post.domain.Post;
import avengers.nexus.post.dto.CommentDto;
import avengers.nexus.post.dto.CommentSummaryDto;
import avengers.nexus.post.repository.CommentRepository;
import avengers.nexus.post.repository.PostRepository;
import avengers.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostService postService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentSummaryDto getCommentSummary(String postId) {
        Post post = postRepository.getPostById(postId);
        int commentCount = post.getComments().size();
        int replyCount = post.getComments().stream().mapToInt(comment -> comment.getReplies().size()).sum();
        return new CommentSummaryDto(commentCount, replyCount);
    }

    @Transactional
    public void createComment(String postId, CommentDto commentDto, User author) {
        postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다.")
        );

        Comment comment = new Comment(postId, commentDto.getContent(), author);

        commentRepository.save(comment);
    }

    public void deleteComment(String postId, String commentId) {
        Post post = postRepository.getPostById(postId);
        post.deleteComment(commentId);
        postService.savePost(post);
    }
    public List<Comment> getComments(String postId) {
        Post post = postRepository.getPostById(postId);
        if (post == null) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }

        List<Comment> comments = post.getComments();
        if (comments == null) {
            throw new RuntimeException("게시글에 달린 댓글을 찾을 수 없습니다.");
        }

        return comments;
    }
    public Comment getCommentById(String postId, String commentId) {
        Post post = postRepository.getPostById(postId);
        return post.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
    }
}
