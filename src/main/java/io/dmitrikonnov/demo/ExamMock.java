package io.dmitrikonnov.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamMock {
    Long id;
    String examName;
    String results;
    String examiners;
    String examLevel;
    List<String> examType;
    String subjectOfExam;
    String status;
}
