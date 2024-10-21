package avengers.nexus.post.dto;

import lombok.Getter;

@Getter
public class CreateCommentDto {
    private String content;
    private Long author;
}
