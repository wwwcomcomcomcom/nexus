package avengers.nexus.post.dto;

import avengers.nexus.post.domain.Reply;
import avengers.nexus.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class ReplyDto {
    private String id;
    private String content;
    private User author;
    private String postId;


    public static ReplyDto toDto(Reply reply) {
        return ReplyDto.builder()
                .id(reply.getId())
                .content(reply.getContent())
                .author(reply.getAuthor())
                .build();
    }
}