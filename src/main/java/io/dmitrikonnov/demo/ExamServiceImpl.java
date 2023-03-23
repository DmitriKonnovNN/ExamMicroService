package io.dmitrikonnov.demo;

import com.amazonaws.services.kms.model.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;

@Service
@Transactional
@AllArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final String NO_SUCH_ELEMENT_EXC_ON_DELETE_MSG = "Deletion failed. No exam with id %d exists!";
    private final ExamRepo examRepo;
    private final AwsS3Storage awsS3Storage;

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


    public String updateExamImageByExamId(Long id, MultipartFile file) {
        StringJoiner fullPath;
        if(!examRepo.existsById(id))
            throw new NotFoundException(String.format("Keine Aufgabe mit id %d gefunden! Die Datei wurde nicht gespeichert.", id));
        if (file==null || file.isEmpty()) {
            throw new IllegalStateException("File to be uploaded is empty or not found!");
        }
        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("FIle uploaded is not an image");
        }
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.EXAM_MANAGEMENT_MEDIA_PATH.getBucketName(),id);
        String fileName = String.format("%s", file.getOriginalFilename());

        try {
            awsS3Storage.upload(path, fileName, Optional.of(metadata), file.getInputStream());
            fullPath = new StringJoiner("/");
            fullPath.add(path).add(fileName);
            examRepo.updateImageDataById(id,fullPath.toString());
        } catch ( IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
        return fullPath.toString();
    }

    public String getPresignedUrlToImageByExamsId(long id){
        var exam = examRepo.findById(id).orElseThrow(()-> {
            throw new NotFoundException(String.format("No exam by id %d found!",id));});
        var fileAndPath = exam.previewImageLink.split("/");

        return awsS3Storage.getPresignedUrl(fileAndPath[1]);
    }

    public byte[] downloadImageById(long id) {

        var exam = examRepo.findById(id).orElseThrow(()-> {
            throw new NotFoundException(String.format("No exam by id %d found!",id));});
        var fileAndPath = exam.previewImageLink.split("/");
        return awsS3Storage.download(fileAndPath[0],fileAndPath[1]);
    }

    public String deleteImageByExamId(long id) {
        var exam = examRepo.findById(id).orElseThrow(()-> {
            throw new NotFoundException(String.format("No exam by id %d found!",id));});
        var fileAndPath = exam.previewImageLink.split(":");
        awsS3Storage.delete(fileAndPath[1]);
        exam.setPreviewImageLink("");
        examRepo.save(exam);
        return String.format("PreviewImage %d has been deleted",id);
    }
}
