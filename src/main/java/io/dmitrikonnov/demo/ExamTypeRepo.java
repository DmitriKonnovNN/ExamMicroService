package io.dmitrikonnov.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamTypeRepo extends JpaRepository<ExamType,Long> {
}
