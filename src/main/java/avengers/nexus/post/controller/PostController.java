package avengers.nexus.post.controller;

import avengers.nexus.post.service.PostService;
import avengers.nexus.post.domain.Post;
import avengers.nexus.post.dto.CreatePostDto;
import avengers.nexus.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable String id) {
        return postService.getPostById(id);
    }
    @GetMapping("/")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
    @PostMapping("/create")
    public Post createPost(@RequestBody CreatePostDto post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not logged in");
        User user = (User) authentication.getCredentials();
        return postService.createPost(post,user.getId());
    }
    @PostMapping("/like/{id}")
    public void likePost(@PathVariable String id) {
        postService.likePost(id);
    }
    @PostMapping("/dislike/{id}")
    public void dislikePost(@PathVariable String id) {
        postService.dislikePost(id);
    }
    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable String id){
        postService.deletePost(id);
    }
}
