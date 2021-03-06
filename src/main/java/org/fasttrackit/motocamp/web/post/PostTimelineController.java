package org.fasttrackit.motocamp.web.post;

import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.service.post.PostAndCommentService;
import org.fasttrackit.motocamp.service.post.PostService;
import org.fasttrackit.motocamp.transfer.post.CreatePost;
import org.fasttrackit.motocamp.transfer.post.PostResponse;
import org.fasttrackit.motocamp.transfer.post.UpdatePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/timeline/post")
public class PostTimelineController {

    private final PostService postService;
    private final PostAndCommentService postAndCommentService;

    public PostTimelineController(PostService postService, PostAndCommentService postAndCommentService) {
        this.postService = postService;
        this.postAndCommentService = postAndCommentService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePost request) {
        Post post = postService.createPost(request);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<PostResponse>> getProfilePosts(@PathVariable long id, Pageable pageable) {

        Page<PostResponse> postsForProfile = postAndCommentService.getPostsForProfile(id, pageable);
        return new ResponseEntity<>(postsForProfile, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PostResponse> getPostByTitle(CreatePost request, Pageable pageable) {
        PostResponse postByTitle = postAndCommentService.getPostByTitle(request.getTitle(), pageable);
        return new ResponseEntity<>(postByTitle, HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long id, Pageable pageable) {
        PostResponse postById = postAndCommentService.getPostById(id, pageable);
        return new ResponseEntity<>(postById, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PostResponse> updatePost(@RequestBody UpdatePost request, Pageable pageable) {
        PostResponse postResponse = postAndCommentService.updatePost(request, pageable);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id, Principal principal) {
        postService.deletePost(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
