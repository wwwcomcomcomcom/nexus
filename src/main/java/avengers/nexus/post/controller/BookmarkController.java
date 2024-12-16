package avengers.nexus.post.controller;

import avengers.nexus.post.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{userId}/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    //북마크 추가
    @PostMapping("/{postId}")
    public ResponseEntity<String> addBookmark(@RequestParam String userId, @PathVariable String postId) {
        bookmarkService.addBookmark(userId, postId);
        return ResponseEntity.ok("Bookmark added");
    }

    //북마크 제거
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable String userId, @PathVariable String postId) {
        bookmarkService.removeBookmark(userId, postId);
        return ResponseEntity.ok("Bookmark deleted");
    }


}
