package avengers.nexus.post.controller;

import avengers.nexus.auth.jwt.JWTUtil;
import avengers.nexus.post.domain.Reply;
import avengers.nexus.post.dto.CreateReplyDto;
import avengers.nexus.post.service.CommentService;
import avengers.nexus.post.service.ReplyService;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment/{commentId}/reply")
public class ReplyController {
    private final ReplyService replyService;
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
    public Reply createReply(@PathVariable String postId, @PathVariable String commentId, @RequestBody CreateReplyDto dto) {
        return replyService.createReply(postId, commentId, dto);
    }
    @DeleteMapping("/{replyId}")
    public void deleteReply(@PathVariable String postId, @PathVariable String commentId, @PathVariable String replyId, HttpServletRequest request) {
        User user = getCurrentUser(request);

        Reply reply = replyService.getReplyById(postId, commentId, replyId);
        if(!reply.getAuthor().equals(user.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        replyService.deleteReply(postId, commentId, replyId);
    }

    @GetMapping
    public List<Reply> getReplies(@PathVariable String postId, @PathVariable String commentId) {
        return replyService.getReplies(postId, commentId);
    }
}
