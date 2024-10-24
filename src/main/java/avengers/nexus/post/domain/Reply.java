package avengers.nexus.post.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Reply {
    @Id
    private String id;
    private String content;
    private Long author;
    public Reply(String content, Long author){
        this.content = content;
        this.author = author;
    }
}
