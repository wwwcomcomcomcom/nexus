package avengers.nexus.post.service;

import avengers.nexus.post.domain.Comment;
import avengers.nexus.post.domain.Post;
import avengers.nexus.post.dto.CommentSummaryDto;
import avengers.nexus.post.dto.CreateCommentDto;
import avengers.nexus.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostService postService;
    private final PostRepository postRepository;
    private final PostRepository commentRepository;

    public CommentSummaryDto getCommentSummary(String postId) {
        Post post = postRepository.getPostById(postId);
        int commentCount = post.getComments().size();
        int replyCount = post.getComments().stream().mapToInt(comment -> comment.getReplies().size()).sum();
        return new CommentSummaryDto(commentCount, replyCount);
    }

    public Comment createComment(String postId, CreateCommentDto comment) {
        Post post = postRepository.getPostById(postId);
        Comment newComment = new Comment(
                comment.getContent(),
                comment.getAuthor()
        );
        post.addComment(newComment);
        postService.savePost(post);
        return newComment;
    }
    public void deleteComment(String postId, String commentId) {
        Post post = postRepository.getPostById(postId);
        post.deleteComment(commentId);
        postService.savePost(post);
    }
    public List<Comment> getComments(String postId) {
        List<Comment> comments = postRepository.getPostById(postId).getComments();
        return comments;
    }
    public Comment getCommentById(String postId, String commentId) {
        Post post = postRepository.getPostById(postId);
        return post.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }
}
