package io.dmitrikonnov.demo;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExamService {
    List<Exam> getAll();

    void addExam(Exam exam);

    void updateExam(Exam exam);

    void deleteExamById(Long id);

    Boolean existsByEmailPersonInChargeForExam(String email);

    String updateExamImageByExamId(Long id, MultipartFile file);

    String getPresignedUrlToImageByExamsId(long id);

    byte[] downloadImageById(long id);

    String deleteImageByExamId(long id);



}
