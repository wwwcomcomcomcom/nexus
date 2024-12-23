package avengers.nexus.post.domain;

import avengers.nexus.Timestamped;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Comment extends Timestamped {
    @Id
    private String id;
    private String content;
    private Long author;
    private List<Reply> replies;
    public Comment(String content, Long author){
        this.content = content;
        this.author = author;
        this.replies = new ArrayList<>();
    }

    public void updateComment(String content, Long author){
        this.content = content;
        this.author = author;
        this.replies = new ArrayList<>();
    }

    public void addReply(Reply reply){
        this.replies.add(reply);
    }
    public void deleteReply(String replyId){
        this.replies.removeIf(reply -> reply.getId().equals(replyId));
    }
}
