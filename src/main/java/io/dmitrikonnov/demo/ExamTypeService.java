package io.dmitrikonnov.demo;

import java.util.List;

public interface ExamTypeService {
    List<ExamType> getAll();

    void addExamType(ExamType examType);
}
