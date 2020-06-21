package org.fasttrackit.motocamp.web;

import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.service.post.PostAndCommentService;
import org.fasttrackit.motocamp.service.post.PostService;
import org.fasttrackit.motocamp.transfer.post.CreatePost;
import org.fasttrackit.motocamp.transfer.post.PostResponse;
import org.fasttrackit.motocamp.transfer.post.UpdatePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@CrossOrigin
@RestController
@RequestMapping("/newsfeed/post")
public class PostController {
    private final PostService postService;
    private final PostAndCommentService postAndCommentService;

    @Autowired
    public PostController(PostService postService, PostAndCommentService postAndCommentService) {
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

        Page<PostResponse> postsForProfile = postService.getPostsForProfile(id, pageable);
        return new ResponseEntity<>(postsForProfile, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Post> getPostByTitle(CreatePost request) {
        Post postByTitle = postService.getPostByTitle(request.getTitle());
        return new ResponseEntity<>(postByTitle, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(Pageable pageable) {

        Page<PostResponse> allPosts = postAndCommentService.getAllPosts(pageable);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Post> updatePost(@RequestBody UpdatePost request) {
        Post post = postService.updatePost(request);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id, Principal principal) {
        postService.deletePost(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
