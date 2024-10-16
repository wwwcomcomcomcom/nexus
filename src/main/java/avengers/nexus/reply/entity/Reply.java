package avengers.nexus.reply.entity;

import avengers.nexus.Timestamped;
import avengers.nexus.post.entity.Post;
import avengers.nexus.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder

public class Reply extends Timestamped {
    @Id
    private String id;

    private String contents;
    private User user;
    private Post post;

    public void updateReply(String contents, User user, Post post) {
        this.contents = contents;
        this.user = user;
        this.post = post;
    }
}
