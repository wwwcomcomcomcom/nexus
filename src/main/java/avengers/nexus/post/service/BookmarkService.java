package avengers.nexus.post.service;

import avengers.nexus.post.domain.Bookmark;
import avengers.nexus.post.domain.Post;
import avengers.nexus.post.repository.BookmarkRepository;
import avengers.nexus.post.repository.PostRepository;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 북마크 추가
    @Transactional
    public Bookmark addBookmark(String userId, List<String> postIds) {
        Optional<Bookmark> existingBookmark = bookmarkRepository.findByUserIdAndPostIdsContaining(userId, postIds.get(0));
        if (existingBookmark.isPresent()) {
            throw new IllegalStateException("이미 북마크에 추가된 항목입니다.");
        }

        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));

        // Post 객체 리스트 생성
        List<Post> posts = postIds.stream()
                .map(postId -> postRepository.findById(postId)
                        .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + postId)))
                .collect(Collectors.toList());

        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(user);
        bookmark.setPostIds(posts);
        return bookmarkRepository.save(bookmark);
    }

    // 북마크 삭제
    public void removeBookmark(String bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }

    // 사용자의 북마크 조회
    public List<Bookmark> getBookmarksByUser(String userId) {
        return bookmarkRepository.findByUserId(userId);
    }

}