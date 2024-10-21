package avengers.nexus.post.controller;

import avengers.nexus.post.service.CommentService;
import avengers.nexus.post.domain.Comment;
import avengers.nexus.post.dto.CreateCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/")
    public Comment createComment(@PathVariable String postId, @RequestBody CreateCommentDto comment) {
        return commentService.createComment(postId, comment);
    }
    @DeleteMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable String postId, @PathVariable String commentId) {
        commentService.deleteComment(postId,commentId);
    }
}
