package org.fasttrackit.motocamp.service.post;

import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.exception.ResourceNotFoundException;
import org.fasttrackit.motocamp.persistance.PostRepository;
import org.fasttrackit.motocamp.service.UserService;
import org.fasttrackit.motocamp.transfer.post.CreatePost;
import org.fasttrackit.motocamp.transfer.post.PostResponse;
import org.fasttrackit.motocamp.transfer.post.UpdatePost;
import org.fasttrackit.motocamp.transfer.user.CreateUser;
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
import java.util.*;

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


    public Post getPost(long id) {
        LOGGER.info("Retrieving post {}", id);

        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post " + id + " not found."));
    }



    public Post updatePost(UpdatePost request, Principal principal) {
        LOGGER.info("Updating post {}", request.getPostId());

        String principalName = principal.getName();

        UserResponse user = userService.getUserBySession(principalName);
        long principalId = user.getId();

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post " + request.getPostId() + " not found."));
        long userId = post.getUser().getId();

        if (principalId != userId) {
            LOGGER.info("This is not your post");
            return post;
        }

        LOGGER.info("Updating post by id {}", post.getId());
        post.setDate(LocalDate.now());
        post.setContent(request.getContent());
        return postRepository.save(post);
    }

    public void updateUserPhotoToPost(long id, CreateUser request) {
        LOGGER.info("Update photo to user");

        List<Post> allByUser_id = postRepository.getAllByUser_Id(id);

        List<Post> updatedPosts = new ArrayList<>();

        for (Post post : allByUser_id) {
            post.setPhotoUser(request.getImageUrl());
            updatedPosts.add(post);
        }
        postRepository.saveAll(updatedPosts);

    }
    public void deletePost(long id, Principal principal) {

        String principalName = principal.getName();

        UserResponse user = userService.getUserBySession(principalName);
        long principalId = user.getId();

        Post post = getPost(id);
        long userId = post.getUser().getId();

        if (principalId == userId) {
            LOGGER.info("Deleting post by id {}", post.getId());
            postRepository.delete(post);
        } else {
            LOGGER.info("This is not your post");
        }

    }

}
