package avengers.nexus.post.controller;

import avengers.nexus.auth.jwt.JWTUtil;
import avengers.nexus.post.dto.CommentDto;
import avengers.nexus.post.dto.CommentSummaryDto;
import avengers.nexus.post.service.CommentService;
import avengers.nexus.post.domain.Comment;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/summary")
    public CommentSummaryDto getCommentSummary(@PathVariable String postId) {
        return commentService.getCommentSummary(postId);
    }

    @Operation(summary = "전체 댓글 조회", description = "해당 게시글에 대한 댓글을 모두 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Comment> getComments(@PathVariable String postId) {
        return commentService.getComments(postId);
    }


    @Operation(summary = "댓글 작성", description = "해당 게시글에 댓글을 작성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createComment(@PathVariable String postId, @RequestBody CommentDto commentDto, HttpServletRequest request) {
        User user = getCurrentUser(request);
        commentService.createComment(postId, commentDto, user);
    }


    @Operation(summary = "댓글 삭제", description = "선택한 댓글을 삭제합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable String postId, @PathVariable String commentId, HttpServletRequest request) {
        User user = getCurrentUser(request);
        Comment comment = commentService.getCommentById(postId, commentId);
        if (!comment.getAuthor().equals(user)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        commentService.deleteComment(postId, commentId);
    }

}
