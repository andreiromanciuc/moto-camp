package org.fasttrackit.motocamp.service.post;

import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.exception.ResourceNotFoundException;
import org.fasttrackit.motocamp.persistance.PostRepository;
import org.fasttrackit.motocamp.service.CommentService;
import org.fasttrackit.motocamp.service.user.UserService;
import org.fasttrackit.motocamp.transfer.comment.CommentResponse;
import org.fasttrackit.motocamp.transfer.post.PostResponse;
import org.fasttrackit.motocamp.transfer.post.UpdatePost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class PostAndCommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final UserService userService;

    public PostAndCommentService(PostRepository postRepository, CommentService commentService, UserService userService) {
        this.postRepository = postRepository;
        this.commentService = commentService;
        this.userService = userService;
    }

    public PostResponse getPostById(long id, Pageable pageable) {
        LOGGER.info("Retrieving post by id nr. {}, for editing", id);

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post " + id + " not found."));

        Page<CommentResponse> commentsForPost = commentService.getCommentsForPost(id, pageable);

        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setPhotoUser(post.getPhotoUser());
        postResponse.setNameFromUser(post.getNameFromUser());
        postResponse.setDate(post.getDate());
        postResponse.setImageUrl(post.getImageUrl());
        postResponse.setComments(commentsForPost);

        return postResponse;
    }
    @Transactional
    public Page<PostResponse> getPostsForProfile(long id, Pageable pageable) {
        LOGGER.info("Retrieving posts for profile {}", id);

        Page<Post> postsPage = postRepository.getAllByUser_IdOrderByDateDesc(id, pageable);

        List<PostResponse> postDtos = new ArrayList<>();

        for (Post post : postsPage.getContent()) {
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

            postDtos.add(dto);
        }
        Collections.reverse(postDtos);
        return new PageImpl<>(postDtos, pageable, postsPage.getTotalElements());
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

    @Transactional
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

    @Transactional
    public PostResponse updatePost(UpdatePost request, Pageable pageable) {
        LOGGER.info("Updating post {}", request.getPostId());

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post " + request.getPostId() + " not found."));
        post.setContent(request.getContent());
        post.setDate(LocalDate.now());
        postRepository.save(post);

        PostResponse postResponse = new PostResponse();
        postResponse.setTitle(post.getTitle());
        postResponse.setContent(post.getContent());
        postResponse.setImageUrl(post.getImageUrl());
        postResponse.setDate(post.getDate());
        postResponse.setNameFromUser(post.getNameFromUser());
        postResponse.setPhotoUser(post.getPhotoUser());
        postResponse.setId(post.getId());
        Page<CommentResponse> commentsForPost = commentService.getCommentsForPost(post.getId(), pageable);
        postResponse.setComments(commentsForPost);

        return postResponse;


    }
}
