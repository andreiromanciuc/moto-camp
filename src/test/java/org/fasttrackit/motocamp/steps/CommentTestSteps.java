package org.fasttrackit.motocamp.steps;

import org.fasttrackit.motocamp.domain.Comment;
import org.fasttrackit.motocamp.domain.Post;
import org.fasttrackit.motocamp.service.CommentService;
import org.fasttrackit.motocamp.transfer.comment.CreateComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@Component
public class CommentTestSteps {
    @Autowired
    private CommentService commentService;
    @Autowired
    private PostTestSteps postTestSteps;

    public Comment createComment() {
        Post post = postTestSteps.createPost();

        CreateComment request = new CreateComment();
        request.setContent("Comment test");
        request.setPostId(post.getId());
        request.setUserId(post.getUser().getId());

        Comment comment = commentService.createComment(request);

        assertThat(comment, notNullValue());
        assertThat(comment.getContent(), is(request.getContent()));

        return comment;
    }
}
