package org.fasttrackit.motocamp.steps;

import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.service.PostService;
import org.fasttrackit.motocamp.transfer.post.CreatePost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@Component
public class PostTestSteps {

    @Autowired
    private PostService postService;
    @Autowired
    private UserTestSteps userTestSteps;


    public Post createPost() {
        User user = userTestSteps.createUser();

        CreatePost createPost = new CreatePost();
        createPost.setTitle("Test");
        createPost.setContent("Test");
        createPost.setImageUrl("Test");
        createPost.setUserId(user.getId());

        Post post = postService.createPost(createPost);

        assertThat(post, notNullValue());
        assertThat(post.getId(), greaterThan(0L));
        assertThat(post.getImageUrl(), is(createPost.getImageUrl()));
        assertThat(post.getTitle(), is(createPost.getTitle()));
        assertThat(post.getContent(), is(createPost.getContent()));
        assertThat(post.getDate(), notNullValue());

        return post;
    }
}
