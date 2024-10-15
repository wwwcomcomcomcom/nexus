package avengers.nexus.post.controller;

import avengers.nexus.post.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
}
