package org.fasttrackit.motocamp.web;

import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.service.PostService;
import org.fasttrackit.motocamp.transfer.post.CreatePost;
import org.fasttrackit.motocamp.transfer.post.PostResponse;
import org.fasttrackit.motocamp.transfer.post.UpdatePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
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

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(Pageable pageable) {

        Page<PostResponse> allPosts = postService.getAllPosts(pageable);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id, @Valid @RequestBody UpdatePost request) {
        Post post = postService.updatePost(id, request);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
