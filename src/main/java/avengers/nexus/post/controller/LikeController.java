package avengers.nexus.post.controller;

import avengers.nexus.auth.jwt.JWTUtil;
import avengers.nexus.post.dto.LikeDto;
import avengers.nexus.post.service.LikeService;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{postid}/like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @GetMapping("/count")
    public ResponseEntity<Integer> getLikeCount(@PathVariable("postid") String postId) {
        Object target = likeService.getTargetById(postId, null, null);
        int likeCount = likeService.getLikeCount(target);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/comment/{commentId}/count")
    public ResponseEntity<Integer> getCommentLikeCount(@PathVariable("postid") String postId,
                                                       @PathVariable("commentId") String commentId) {
        Object target = likeService.getTargetById(postId, commentId, null);
        int likeCount = likeService.getLikeCount(target);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/reply/{replyId}/count")
    public ResponseEntity<Integer> getReplyLikeCount(@PathVariable("postid") String postId,
                                                     @PathVariable("replyId") String replyId) {
        Object target = likeService.getTargetById(postId, null, replyId);
        int likeCount = likeService.getLikeCount(target);
        return ResponseEntity.ok(likeCount);
    }

    private User getCurrentUser(HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private Long getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return jwtUtil.getUserId(token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LikeDto likePost(@PathVariable("postid") String postId, HttpServletRequest request) {
        User user = getCurrentUser(request);
        Object target = likeService.getTargetById(postId, null, null);
        return likeService.likeObject(target, user, true);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LikeDto removeLikePost(@PathVariable("postid") String postId, HttpServletRequest request) {
        User user = getCurrentUser(request);
        Object target = likeService.getTargetById(postId, null, null);
        return likeService.likeObject(target, user, false);
    }

    @PostMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LikeDto likeComment(@PathVariable("postid") String postId,
                               @PathVariable("commentId") String commentId,
                               HttpServletRequest request) {
        User user = getCurrentUser(request);
        Object target = likeService.getTargetById(postId, commentId, null);
        return likeService.likeObject(target, user, true);
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LikeDto removeLikeComment(@PathVariable("postid") String postId,
                                     @PathVariable("commentId") String commentId,
                                     HttpServletRequest request) {
        User user = getCurrentUser(request);
        Object target = likeService.getTargetById(postId, commentId, null);
        return likeService.likeObject(target, user, false);
    }

    @PostMapping("/reply/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LikeDto likeReply(@PathVariable("postid") String postId,
                             @PathVariable("replyId") String replyId,
                             HttpServletRequest request) {
        User user = getCurrentUser(request);
        Object target = likeService.getTargetById(postId, null, replyId);
        return likeService.likeObject(target, user, true);
    }

    @DeleteMapping("/reply/{replyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public LikeDto removeLikeReply(@PathVariable("postid") String postId,
                                   @PathVariable("replyId") String replyId,
                                   HttpServletRequest request) {
        User user = getCurrentUser(request);
        Object target = likeService.getTargetById(postId, null, replyId);
        return likeService.likeObject(target, user, false);
    }
}
