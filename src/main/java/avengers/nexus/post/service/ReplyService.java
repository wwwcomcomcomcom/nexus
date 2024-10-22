package avengers.nexus.post.service;

import avengers.nexus.post.domain.Reply;
import avengers.nexus.post.dto.CreateReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final CommentService commentService;
    public Reply createReply(String postId, String commentId, CreateReplyDto dto){
        Reply reply = new Reply(dto.getContent(), dto.getAuthor());
        commentService.getCommentById(postId, commentId).addReply(reply);
        return reply;
    }
    public void deleteReply(String postId, String commentId, String replyId){
        commentService.getCommentById(postId, commentId).deleteReply(replyId);
    }
}
