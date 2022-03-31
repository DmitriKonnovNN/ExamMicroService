package io.dmitrikonnov.demo;

import java.util.List;

public interface ExamService {
    List<Exam> getAll();

    void addExam(Exam exam);

    void deleteExamById(Long id);

    Boolean existsByEmailPersonInChargeForExam(String email);
}
