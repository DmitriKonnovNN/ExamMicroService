package io.dmitrikonnov.demo;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/examtypes")
@AllArgsConstructor
public class ExamTypeController {

    private final ExamTypeService examTypeService;

    @GetMapping
    public List<ExamType> getAllExamTypes (){
        return examTypeService.getAll();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addExamType(@RequestBody ExamType examType){
        examTypeService.addExamType(examType);
    }
}
