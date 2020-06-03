package org.fasttrackit.motocamp.persistance;

import org.fasttrackit.motocamp.domain.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorRepository extends JpaRepository<Motorcycle, Long> {
}
