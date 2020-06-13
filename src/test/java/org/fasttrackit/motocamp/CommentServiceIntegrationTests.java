package org.fasttrackit.motocamp;

import org.fasttrackit.motocamp.domain.Comment;
import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.exception.ResourceNotFoundException;
import org.fasttrackit.motocamp.service.CommentService;
import org.fasttrackit.motocamp.steps.CommentTestSteps;
import org.fasttrackit.motocamp.steps.PostTestSteps;
import org.fasttrackit.motocamp.transfer.comment.CommentResponse;
import org.fasttrackit.motocamp.transfer.comment.CreateComment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;

@SpringBootTest
public class CommentServiceIntegrationTests {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentTestSteps commentTestSteps;
    @Autowired
    private PostTestSteps postTestSteps;

    @Test
    void createComment_whenValidRequest_thenReturnComment() {
        Post post = postTestSteps.createPost();

        CreateComment request = new CreateComment();
        request.setContent("Comment test");
        request.setPostId(post.getId());
        request.setUserId(post.getUser().getId());

        Comment comment = commentService.createComment(request);

        assertThat(comment, notNullValue());
        assertThat(comment.getContent(), is(request.getContent()));
    }

    @Test
    void getAllCommentsForPost_whenExistingComments_thenReturnComments() {

        Comment comment = commentTestSteps.createComment();
        Page<CommentResponse> commentsForPost = commentService.getCommentsForPost(comment.getPost().getId(), Pageable.unpaged());

        assertThat(commentsForPost, notNullValue());
    }

//    @Test
//    void deleteComment_whenCommentExist_thenReturnException() {
//        Comment comment = commentTestSteps.createComment();
//
//        commentService.deleteComment(comment.getId());
//
//        Assertions.assertThrows(ResourceNotFoundException.class, ()-> commentService.getComment(comment.getId()));
//    }

    @Test
    void updateComment_whenCommentExist_thenReturnUpdatedComment() {
        Comment comment = commentTestSteps.createComment();

        CreateComment request = new CreateComment();
        request.setContent(comment.getContent() + "update");

        Comment updated = commentService.updateComment(comment.getId(), request);

        assertThat(updated, notNullValue());
        assertThat(updated.getContent(), not(comment.getContent()));

    }
}
