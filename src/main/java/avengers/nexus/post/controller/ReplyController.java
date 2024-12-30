package avengers.nexus.post.controller;


import avengers.nexus.auth.jwt.JWTUtil;
import avengers.nexus.post.dto.ReplyDto;
import avengers.nexus.post.service.ReplyService;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/post/{postId}/reply")
//@RestController
public class ReplyController {
    private final ReplyService replyService;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    @Operation(summary = "대댓글 작성", description = "해당 게시글에 대댓글을 작성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void writeReply(@PathVariable String postId, @RequestBody ReplyDto replyDto, @RequestHeader("Authorization") String authHeader) {
        User user = jwtUtil.getUserByAuthHeader(authHeader);
        replyService.writeReply(postId, replyDto, user);
    }

    @Operation(summary = "전체 대댓글 조회", description = "해당 게시글에 대한 대댓글을 모두 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ReplyDto> getReplies(@PathVariable String postId) {
        return replyService.getReplies(postId);
    }

    @Operation(summary = "대댓글 삭제", description = "선택한 대댓글을 삭제합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{replyId}")
    public void deleteReply(@PathVariable String postId, @PathVariable String replyId, @RequestHeader("Authorization") String authorizationHeader) {
        User user = jwtUtil.getUserByAuthHeader(authorizationHeader);
        replyService.deleteReply(postId, replyId, user);
    }

//    @Operation(summary = "대댓글 수정", description = "선택한 대댓글을 수정합니다.")
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/{replyId}")
//    public ReplyDto updateReply(@PathVariable String postId, @PathVariable String replyId, @RequestBody ReplyDto replyDto, HttpServletRequest request) {
//        User user = getCurrentUser(request);
//        return replyService.updateReply(postId, replyId, replyDto, user);
//    }



}

