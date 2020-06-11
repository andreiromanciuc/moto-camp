package org.fasttrackit.motocamp.service;

import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.exception.ResourceNotFoundException;
import org.fasttrackit.motocamp.persistance.PostRepository;
import org.fasttrackit.motocamp.transfer.post.CreatePost;
import org.fasttrackit.motocamp.transfer.post.PostResponse;
import org.fasttrackit.motocamp.transfer.post.UpdatePost;
import org.fasttrackit.motocamp.transfer.user.CreateUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final PostRepository postRepository;
    private final UserService userService;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }
    @Transactional
    public Post createPost(CreatePost request) {
        LOGGER.info("Creating post from profile {}", request.getUserId());
        User user = userService.getUser(request.getUserId());

        Post post = new Post();
        post.setUser(user);
        post.setContent(request.getContent());
        post.setTitle(request.getTitle());
        post.setImageUrl(request.getImageUrl());
        post.setDate(LocalDate.now());
        post.setNameFromUser(user.getUsername());
        post.setPhotoUser(user.getImageUrl());

        return postRepository.save(post);

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

            postDtos.add(dto);
        }
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

            postResponses.add(dto);
        }
        return new PageImpl<>(postResponses, pageable, posts.getTotalElements());
    }

    public Post getPost(long id) {
        LOGGER.info("Retrieving post {}", id);

        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post " + id + " not found."));
    }

    public Post getPostByTitle(String request) {
        LOGGER.info("Retrieving post by title {}", request);

        return postRepository.getByTitle(request);
    }
    public Post updatePost(UpdatePost request) {
        LOGGER.info("Updating post {}", request.getPostId());

        Post post = getPost(request.getPostId());
        post.setDate(LocalDate.now());
        post.setContent(request.getContent());
        return postRepository.save(post);
    }



    public void deletePost(long id) {
        LOGGER.info("Removing post {}", id);
        Post post = getPost(id);
        postRepository.delete(post);
    }

}
