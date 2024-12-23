package avengers.nexus.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class CreateReplyDto {
    private String content;
    private Long author;
    private String commentId;

}
