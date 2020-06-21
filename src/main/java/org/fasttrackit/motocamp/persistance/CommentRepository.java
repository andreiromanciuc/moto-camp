package org.fasttrackit.motocamp.persistance;

import org.fasttrackit.motocamp.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> getAllByPost_Id(long postId, Pageable pageable);

}
