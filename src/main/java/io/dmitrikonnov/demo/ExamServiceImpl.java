package io.dmitrikonnov.demo;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@AllArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final String NO_SUCH_ELEMENT_EXC_ON_DELETE_MSG = "Deletion failed. No exam with id %d exists!";
    private final ExamRepo examRepo;

    @Override
    public List<Exam> getAll() {
        return examRepo.findAllByOrderByLastModified();
    }

    @Override
    public void addExam(Exam exam) {
        examRepo.save(exam);
    }

    @Override
    public void deleteExamById(Long id) {
        try {
            examRepo.deleteById(id);
        }
        catch (EmptyResultDataAccessException ex) {
                throw new NoSuchElementException(String.format(NO_SUCH_ELEMENT_EXC_ON_DELETE_MSG,id));
        }
    }

    @Override
    public void updateExam(Exam exam) {
        examRepo.findById(exam.id).ifPresent(entity->
                entity.setExamSummary(exam.getExamSummary())
        );
    }

    @Override
    public Boolean existsByEmailPersonInChargeForExam(String email) {
        return examRepo.existsByEmailPersonInChargeForExam(email);
    }
}
