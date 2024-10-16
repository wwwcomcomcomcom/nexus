package avengers.nexus.reply.dto;


import avengers.nexus.reply.entity.Reply;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class ReplyDto {
    private String id;
    private String contents;
    private String author;

    public static ReplyDto toDto(Reply reply) {
        return ReplyDto.builder()
                .id(reply.getId())
                .contents(reply.getContents())
                .author(reply.getUser().getName())
                .build();
    }
}
