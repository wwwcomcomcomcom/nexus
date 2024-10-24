package avengers.nexus.post.dto;

import avengers.nexus.post.entity.Like;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class LikeDto {
    private String postId;
    private String user;
    private boolean liked;

    public static LikeDto toLikeDto(Like like, boolean liked) {
        return LikeDto.builder()
                .postId(like.getPost().getId())
                .user(like.getUser().getName())
                .liked(liked)
                .build();
    }
}
