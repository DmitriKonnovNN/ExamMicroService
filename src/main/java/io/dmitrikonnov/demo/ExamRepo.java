package io.dmitrikonnov.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExamRepo extends JpaRepository<Exam,Long> {

    Boolean existsByEmailPersonInChargeForExam(String email);
}
