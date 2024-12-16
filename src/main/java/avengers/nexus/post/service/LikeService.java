package avengers.nexus.post.service;

import avengers.nexus.post.dto.LikeDto;
import avengers.nexus.post.domain.Like;
import avengers.nexus.post.repository.LikeRepository;
import avengers.nexus.post.repository.PostRepository;
import avengers.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final CommentService commentService;
    private final LikeService likeService;
    private final PostRepository postRepository;

    public Like existingLike(User user, Object target) {
        return likeRepository.findByUserAndTarget(user, target);
    }

    public LikeDto likeObject(Object target, User user, boolean isAdding) {
        Like existingLike = likeService.existingLike(user, target);

        if (isAdding) {
            if (existingLike == null) {
                Like newLike = new Like(user, target);
                likeRepository.save(newLike);
                return LikeDto.toLikeDto(newLike, true);
            } else {
                likeRepository.delete(existingLike);
                return LikeDto.toLikeDto(existingLike, false);
            }
        } else {
            if (existingLike != null) {
                likeRepository.delete(existingLike);
                return LikeDto.toLikeDto(existingLike, false);
            } else {
                throw new IllegalArgumentException("좋아요가 존재하지 않습니다.");
            }
        }
    }

    public Object getTargetById(String postId, String commentId, String replyId) {
        if (replyId != null) {
            return commentService.getCommentById(postId, replyId);
        } else if (commentId != null) {
            return commentService.getCommentById(postId, commentId);
        } else {
            return postRepository.getPostById(postId);
        }
    }
}