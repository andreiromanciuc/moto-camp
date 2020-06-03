package org.fasttrackit.motocamp.persistance;

import org.fasttrackit.motocamp.domain.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Page<Profile> findByFullNameContaining(String namePartialName, Pageable pageable);

    Page<Profile> findByUserUsername(String partialUserName, Pageable pageable);

}
