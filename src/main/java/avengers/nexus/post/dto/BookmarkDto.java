package avengers.nexus.post.dto;

import avengers.nexus.post.domain.Bookmark;
import avengers.nexus.post.domain.Post;
import avengers.nexus.user.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Data
public class BookmarkDto {
    private String id;

    private User userId;
    private List<Post> postIds;

    public static BookmarkDto toDto(Bookmark bookmark) {
        return BookmarkDto.builder()
                .id(bookmark.getId())
                .userId(bookmark.getUserId())
                .postIds(bookmark.getPostIds())
                .build();
    }

}
