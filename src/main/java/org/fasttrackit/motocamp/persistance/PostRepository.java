package org.fasttrackit.motocamp.persistance;

import org.fasttrackit.motocamp.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> getAllByUser_Id (long profileId);

    Post getByTitle(String title);

    Page<Post> getAllByUser_IdOrderByDateDesc (long profileId, Pageable pageable);

}
