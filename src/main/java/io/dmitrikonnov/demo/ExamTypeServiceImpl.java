package io.dmitrikonnov.demo;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ExamTypeServiceImpl implements ExamTypeService {

    private final ExamTypeRepo examTypeRepo;

    @Override
    public List<ExamType> getAll() {
        return examTypeRepo.findAll();
    }

    @Override
    public void addExamType(ExamType examType) {
        examTypeRepo.save(examType);
    }



}
