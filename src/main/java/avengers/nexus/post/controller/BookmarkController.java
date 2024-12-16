package avengers.nexus.post.controller;

import avengers.nexus.post.domain.Bookmark;
import avengers.nexus.post.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{userId}/bookmarks")
public class BookmarkController {
    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    // 북마크 추가
    @PostMapping
    public ResponseEntity<Bookmark> addBookmark(
            @RequestParam String userId,
            @RequestBody List<String> postIds
    ) {
        Bookmark bookmark = bookmarkService.addBookmark(userId, postIds);
        return ResponseEntity.ok(bookmark);
    }

    // 북마크 삭제
    @DeleteMapping("/{bookmarkId}")
    public ResponseEntity<Void> removeBookmark(@PathVariable String bookmarkId) {
        bookmarkService.removeBookmark(bookmarkId);
        return ResponseEntity.noContent().build();
    }

    // 사용자별 북마크 목록 조회
    @GetMapping()
    public ResponseEntity<List<Bookmark>> getBookmarksByUser(@RequestParam String userId) {
        List<Bookmark> bookmarks = bookmarkService.getBookmarksByUser(userId);
        return ResponseEntity.ok(bookmarks);
    }

}
