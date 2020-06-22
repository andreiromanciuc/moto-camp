package org.fasttrackit.motocamp.service.post;



import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.persistance.PostRepository;
import org.fasttrackit.motocamp.service.CommentService;
import org.fasttrackit.motocamp.service.UserService;
import org.fasttrackit.motocamp.transfer.comment.CommentResponse;
import org.fasttrackit.motocamp.transfer.post.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class PostAndCommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final PostRepository postRepository;
    private final CommentService commentService;

    public PostAndCommentService(PostRepository postRepository, CommentService commentService) {
        this.postRepository = postRepository;
        this.commentService = commentService;
    }


    @Transactional
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        LOGGER.info("Retrieving all posts for feed");

        Page<Post> posts = postRepository.findAll(pageable);

        List<PostResponse> postResponses = new ArrayList<>();

        for (Post post : posts.getContent()) {
            PostResponse dto = new PostResponse();
            dto.setContent(post.getContent());
            dto.setDate(post.getDate());
            dto.setTitle(post.getTitle());
            dto.setImageUrl(post.getImageUrl());
            dto.setNameFromUser(post.getNameFromUser());
            dto.setPhotoUser(post.getPhotoUser());
            dto.setId(post.getId());

            Page<CommentResponse> commentsForPost = commentService.getCommentsForPost(post.getId(), pageable);
            dto.setComments(commentsForPost);

            postResponses.add(dto);

        }
        Collections.reverse(postResponses);
        return new PageImpl<>(postResponses, pageable, posts.getTotalElements());
    }

    public PostResponse getPostByTitle(String request, Pageable pageable) {
        LOGGER.info("Retrieving post by title {}", request);
        Post byTitle = postRepository.getByTitle(request);
        Page<CommentResponse> commentsForPost = commentService.getCommentsForPost(byTitle.getId(), pageable);

        PostResponse postResponse = new PostResponse();
        postResponse.setId(byTitle.getId());
        postResponse.setTitle(byTitle.getTitle());
        postResponse.setContent(byTitle.getContent());
        postResponse.setDate(byTitle.getDate());
        postResponse.setImageUrl(byTitle.getImageUrl());
        postResponse.setPhotoUser(byTitle.getPhotoUser());
        postResponse.setNameFromUser(byTitle.getNameFromUser());
        postResponse.setComments(commentsForPost);

        return postResponse;
    }
}
