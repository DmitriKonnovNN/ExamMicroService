package io.dmitrikonnov.demo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ExamServiceImpl implements ExamService {
    private final ExamRepo examRepo;

    @Override
    public List<Exam> getAll() {
        return examRepo.findAll();
    }

    @Override
    public void addExam(Exam exam) {
        examRepo.save(exam);
    }

    @Override
    public void deleteExamById(Long id) {
        examRepo.deleteById(id);
    }
}
