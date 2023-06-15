package io.dmitrikonnov.demo;

import com.amazonaws.services.kms.model.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;

public interface ExamTypeService {
    List<ExamType> getAll();

    void addExamType(ExamType examType);


}
