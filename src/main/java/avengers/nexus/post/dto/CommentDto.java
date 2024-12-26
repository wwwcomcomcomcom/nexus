package avengers.nexus.post.dto;

import avengers.nexus.post.domain.Comment;
import avengers.nexus.post.domain.Reply;
import avengers.nexus.user.entity.User;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder
public class CommentDto {

    @Id
    private String id;
    private String content;
    private User author;
    private List<Reply> replies;

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .replies(comment.getReplies())
                .build();

    }

}
