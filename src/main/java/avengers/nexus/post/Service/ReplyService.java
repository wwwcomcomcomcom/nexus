package avengers.nexus.post.service;

import avengers.nexus.post.domain.Comment;
import avengers.nexus.post.domain.Reply;
import avengers.nexus.post.dto.CreateReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final CommentService commentService;

    public Reply createReply(String postId, String commentId, CreateReplyDto dto){
        Reply reply = new Reply(
                dto.getContent(),
                dto.getAuthor()
        );
        commentService.getCommentById(postId, commentId).addReply(reply);
        return reply;
    }
    public void deleteReply(String postId, String commentId, String replyId){
        Comment comment = commentService.getCommentById(postId, commentId);

        comment.deleteReply(replyId);
    }
    public List<Reply> getReplies(String postId, String commentId){
        Comment comment = commentService.getCommentById(postId, commentId);
        return comment.getReplies();
    }

    public Reply getReplyById(String postId, String commentId, String replyId) {
        Comment comment = commentService.getCommentById(postId, commentId);
        return comment.getReplies().stream()
                .filter(reply -> reply.getId().equals(replyId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Reply not found"));
    }

}
