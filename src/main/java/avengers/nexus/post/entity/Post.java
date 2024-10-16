package avengers.nexus.post.entity;

import avengers.nexus.Timestamped;
import avengers.nexus.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Post extends Timestamped {
    @Id
    private String id;

    private String title;
    private String contents;
    private User author;
    private List<Post> comments;
    private Long likes;


    public void updatePost(String title, String contents, User author){
        this.title = title;
        this.contents = contents;
        this.author = author;
    }


}