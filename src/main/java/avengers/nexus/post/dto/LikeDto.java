package avengers.nexus.post.dto;

import avengers.nexus.post.domain.Like;

import avengers.nexus.post.entity.Post;
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
        // `target`을 `Post`로 캐스팅
        if (!(like.getTarget() instanceof Post)) {
            throw new IllegalArgumentException("Target is not a Post");
        }
        Post post = (Post) like.getTarget();

        return LikeDto.builder()
                .postId(post.getId()) // `target`에서 `Post`의 ID 가져오기
                .user(like.getUser().getName())
                .liked(liked)
                .build();
    }

}
