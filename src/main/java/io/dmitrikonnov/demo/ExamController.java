package io.dmitrikonnov.demo;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/exams")
@AllArgsConstructor
public class ExamController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ExamService examService;

    @GetMapping
    public List<Exam>getAllExams (){
        return examService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addExam (@Valid @RequestBody Exam exam){
        log.error(exam.toString());
        examService.addExam(exam);

    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void updateExam (@Valid @RequestBody Exam exam){
        log.error(exam.toString());
        examService.updateExam(exam);

    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.OK)
    public void deleteExamById (@PathVariable Long id) {
    examService.deleteExamById(id);
    }

    @ExceptionHandler (NoSuchElementException.class)
    public ResponseEntity<String> throwNoSuchElementException (NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
