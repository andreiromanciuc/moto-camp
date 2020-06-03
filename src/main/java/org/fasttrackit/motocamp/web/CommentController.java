package org.fasttrackit.motocamp.web;


import org.fasttrackit.motocamp.domain.Comment;
import org.fasttrackit.motocamp.service.CommentService;
import org.fasttrackit.motocamp.transfer.comment.CommentResponse;
import org.fasttrackit.motocamp.transfer.comment.CreateComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CreateComment request) {
        Comment comment = commentService.createComment(request);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<CommentResponse>> getComments(@PathVariable long postId, Pageable pageable) {
        Page<CommentResponse> commentsForPost = commentService.getCommentsForPost(postId, pageable);

        return new ResponseEntity<>(commentsForPost, HttpStatus.OK);
    }

    @PutMapping("/id")
    public ResponseEntity<Comment> updateComment(@PathVariable long id, @Valid @RequestBody CreateComment request) {
        Comment comment = commentService.updateComment(id, request);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}
