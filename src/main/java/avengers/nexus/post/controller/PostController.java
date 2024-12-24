package avengers.nexus.post.controller;

import avengers.nexus.post.dto.PostDto;
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
    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not logged in");
        return (User) authentication.getPrincipal();
    }
    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable String id) {
        return postService.getPost(id);
    }
    @GetMapping("/")
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }
    @PostMapping("/create")
    public Post createPost(@RequestBody CreatePostDto post) {
        User user = getUser();
        return postService.createPost(post,user.getId());
    }
    @PostMapping("/like/{id}")
    public void likePost(@PathVariable String id) {
        postService.likePost(id,getUser());
    }
    @PostMapping("/dislike/{id}")
    public void dislikePost(@PathVariable String id) {
        postService.dislikePost(id,getUser());
    }
    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable String id){
        postService.deletePost(id,getUser());
    }
}
