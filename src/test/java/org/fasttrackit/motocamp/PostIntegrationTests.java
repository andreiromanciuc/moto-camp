package org.fasttrackit.motocamp;

import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.service.post.PostService;
import org.fasttrackit.motocamp.steps.PostTestSteps;
import org.fasttrackit.motocamp.transfer.post.UpdatePost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;

@SpringBootTest
public class PostIntegrationTests {
    @Autowired
    public PostService postService;

    @Autowired
    private PostTestSteps postTestSteps;

    @Test
    void createPost_whenValidRequest_thenReturnPost() {
        postTestSteps.createPost();
    }

    @Test
    void updatePost_whenValidRequest_thenReturnPost() {
        Post post = postTestSteps.createPost();

        UpdatePost request = new UpdatePost();
        request.setContent(post.getContent() + "update");

        assertThat(request, notNullValue());
        assertThat(request.getContent(), not(post.getContent()));
    }

    @Test
    void getPosts_whenPostsExist_thenReturnPosts() {
        Post post = postTestSteps.createPost();

        Post response = postService.getPost(post.getId());

        assertThat(response, notNullValue());
        assertThat(response.getDate(), is(post.getDate()));
        assertThat(response.getContent(), is(post.getContent()));
        assertThat(response.getImageUrl(), is(post.getImageUrl()));

    }

}
