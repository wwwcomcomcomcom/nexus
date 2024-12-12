package avengers.nexus.post.domain;

import avengers.nexus.Timestamped;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Reply extends Timestamped {
    @Id
    private String id;
    private String content;
    private Long author;

    public Reply(String content, Long author){
        this.content = content;
        this.author = author;
    }
}
