package io.dmitrikonnov.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepo extends JpaRepository<Exam,Long> {
}
