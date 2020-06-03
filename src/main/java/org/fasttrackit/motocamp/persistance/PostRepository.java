package org.fasttrackit.motocamp.persistance;

import org.fasttrackit.motocamp.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> getAllByUser_Id (long profileId, Pageable pageable);


}
