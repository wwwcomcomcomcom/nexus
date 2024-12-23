package avengers.nexus.post.entity;

import avengers.nexus.Timestamped;
import avengers.nexus.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("replies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Reply extends Timestamped {
    @Id
    private String id;

    private String contents;
    private User user;
    private Post post;

}