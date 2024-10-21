package avengers.nexus.post.dto;

import lombok.Getter;

@Getter
public class CreateReplyDto {
    private String content;
    private Long author;
    private String commentId;
}
