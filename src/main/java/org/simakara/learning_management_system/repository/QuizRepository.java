package org.simakara.learning_management_system.repository;

import org.simakara.learning_management_system.model.Course;
import org.simakara.learning_management_system.model.Quiz;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    boolean existsByName(String name);

    Optional<Quiz> findByCode(String code);

    List<Quiz> findByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Quiz> findByCourses(Course course, Pageable pageable);
}
