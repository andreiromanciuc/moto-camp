package org.fasttrackit.motocamp.persistance;

import org.fasttrackit.motocamp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("user_repository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String partialUserName);

    User findByEmail(String partialEmail);

}
