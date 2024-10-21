package avengers.nexus.post.controller;

import avengers.nexus.post.domain.Reply;
import avengers.nexus.post.dto.CreateReplyDto;
import avengers.nexus.post.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment/{commentId}/reply")
public class ReplyController {
    private final ReplyService replyService;
    @PostMapping("/")
    public Reply createReply(@PathVariable String postId, @PathVariable String commentId, @RequestBody CreateReplyDto dto) {
        return replyService.createReply(postId, commentId, dto);
    }
    @DeleteMapping("/{replyId}")
    public void deleteReply(@PathVariable String postId, @PathVariable String commentId, @PathVariable String replyId) {
        replyService.deleteReply(postId, commentId, replyId);
    }
}
