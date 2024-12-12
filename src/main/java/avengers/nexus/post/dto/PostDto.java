package avengers.nexus.post.dto;

import avengers.nexus.post.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostDto {
    private String id;
    private String title;
    private String contents;
    private String author;
    private boolean liked;

    public static PostDto toDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .author(post.getAuthor().getName())
                .liked(post.isLiked())
                .build();

    }




}
