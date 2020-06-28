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

import java.security.Principal;


@CrossOrigin
@RestController
@RequestMapping("/newsfeed/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CreateComment request, Principal principal) {
        Comment comment = commentService.createComment(request, principal);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Page<CommentResponse>> getComments(Pageable pageable, @PathVariable long id) {
        Page<CommentResponse> commentsForPost = commentService.getCommentsForPost(id, pageable);

        return new ResponseEntity<>(commentsForPost, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable long id, @RequestBody CreateComment request) {
        Comment comment = commentService.updateComment(id, request);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable long id, Principal principal) {
        commentService.deleteComment(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
