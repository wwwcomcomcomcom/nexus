package avengers.nexus.post.controller;

import avengers.nexus.auth.jwt.JWTUtil;
import avengers.nexus.post.service.CommentService;
import avengers.nexus.post.domain.Comment;
import avengers.nexus.post.dto.CreateCommentDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    private User getCurrentUser(HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        return userRepository.findById(userId).orElseThrow(()->
                new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private Long getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return jwtUtil.getUserId(token);
    }

    @PostMapping("/")
    public Comment createComment(@PathVariable String postId, @RequestBody CreateCommentDto comment) {
        return commentService.createComment(postId, comment);
    }
    @DeleteMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable String postId, @PathVariable String commentId, HttpServletRequest request) {
        User user = getCurrentUser(request);

        Comment comment = commentService.getCommentById(postId, commentId);
        if (!comment.getAuthor().equals(user.getId())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        commentService.deleteComment(postId, commentId);
    }

    @GetMapping
    public List<Comment> getComments(@PathVariable String postId) {
        return commentService.getComments(postId);
    }

}
