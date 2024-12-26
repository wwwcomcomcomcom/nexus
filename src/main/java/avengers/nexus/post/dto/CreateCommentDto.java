package avengers.nexus.post.dto;

import avengers.nexus.post.domain.Comment;
import avengers.nexus.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateCommentDto {
    private String content;
    private User author;
    private String id;

    public static CreateCommentDto toDto(Comment comment) {
        return CreateCommentDto.builder()
                .content(comment.getContent())
                .author(comment.getAuthor())
                .build();
    }
}
