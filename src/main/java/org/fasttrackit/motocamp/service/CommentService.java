package org.fasttrackit.motocamp.service;

import org.fasttrackit.motocamp.domain.Comment;
import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.exception.ResourceNotFoundException;
import org.fasttrackit.motocamp.persistance.CommentRepository;
import org.fasttrackit.motocamp.transfer.comment.CommentResponse;
import org.fasttrackit.motocamp.transfer.comment.CreateComment;
import org.fasttrackit.motocamp.transfer.user.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public Comment createComment(CreateComment request) {
        LOGGER.info("Creating comment {}", request);
        User user = userService.getUser(request.getUserId());
        Post post = postService.getPost(request.getPostId());

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setDate(LocalDate.now());
        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Transactional
    public Page<CommentResponse> getCommentsForPost(long postId, Pageable pageable) {
        LOGGER.info("Retrieving comments for post {}", postId);

        Page<Comment> commentsByPost = commentRepository.getCommentsByPost(postId, pageable);

        List<CommentResponse> commentDtos = new ArrayList<>();

        for (Comment comment : commentsByPost.getContent()) {
            CommentResponse dto = new CommentResponse();
            dto.setContent(comment.getContent());
            dto.setDate(comment.getDate());
            dto.setUsername(comment.getUser().getUsername());
            dto.setImageUrl(comment.getUser().getImageUrl());
            commentDtos.add(dto);
        }
        return new PageImpl<>(commentDtos, pageable, commentsByPost.getTotalElements());
    }

    public Comment getComment(long id) {
        LOGGER.info("Retrieving comment {}", id);
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment " + id + " not found."));
    }

    public Comment updateComment(long id, CreateComment request) {
        LOGGER.info("Updating comment {}", id);
        Comment comment = getComment(id);
        comment.setContent(request.getContent());
        comment.setDate(LocalDate.now());
        return commentRepository.save(comment);
    }

    public void deleteComment(long id, Principal principal) {

        String principalName = principal.getName();
        UserResponse user = userService.getUserBySession(principalName);
        long principalId = user.getId();

        Comment comment = getComment(id);
        long userId = comment.getUser().getId();

        if (principalId == userId) {
            LOGGER.info("Delete comment {}", id);
            commentRepository.delete(comment);
        } else {
            LOGGER.info("This is not your comment");
        }

    }
}
