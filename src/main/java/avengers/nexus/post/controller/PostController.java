package avengers.nexus.post.controller;

import avengers.nexus.auth.jwt.JWTUtil;
import avengers.nexus.post.repository.PostRepository;
import avengers.nexus.post.service.PostService;
import avengers.nexus.post.dto.PostDto;
import avengers.nexus.user.entity.User;
import avengers.nexus.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/post")
@RequiredArgsConstructor

public class PostController {

    private final PostService postService;

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    private User getCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtUtil.getUserId(token);
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not logged in");
        return (User) authentication.getPrincipal();
    }

    @Operation(summary = "전체 게시글 보기", description = "전체 게시글 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<PostDto> getPosts() {
        return postService.getPosts();
    }


    @Operation(summary = "특정 게시글 보기", description = "게시글을 조회한다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable("id") String id) {
        return postService.getPost(id);
    }


    @Operation(summary = "게시글 작성", description = "게시글을 작성함")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/write")
    public PostDto writePost(@RequestBody PostDto postDto, HttpServletRequest request) {
        User user = getCurrentUser(request);
        return postService.writePost(postDto, user);
    }


//    @Operation(summary = "게시글 수정", description = "게시글을 수정함")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PutMapping("/update/{id}")
//    public PostDto updatePost(@PathVariable("id") String id, @RequestBody PostDto postDto, HttpServletRequest request) {
//        User user = getCurrentUser(request);
//        PostDto updatedPost = postService.updatePost(id, postDto, user);
//        return postService.updatePost(id, postDto, user);
//    }


    @Operation(summary = "게시글 삭제", description = "게시글을 삭제함.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void deletePost(@PathVariable("id") String id, HttpServletRequest request) {
        User user = getCurrentUser(request);
        postService.delete(id);
    }


}