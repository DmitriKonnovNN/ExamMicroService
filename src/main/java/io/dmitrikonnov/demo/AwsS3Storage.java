package io.dmitrikonnov.demo;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AwsS3Storage {

    private final AmazonS3 s3Client;

    @Async
    public void upload(String path,
                       String fileName,
                       Optional<Map<String, String>> optionalMetaData,
                       InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            s3Client.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public String getPresignedUrl(String fileName) {

        return s3Client.generatePresignedUrl(BucketName.EXAM_MANAGEMENT_MEDIA_PATH.getBucketName(),
                fileName,
                Date.from(LocalDateTime.now().plusMinutes(3L).toInstant(ZoneOffset.UTC)) ).toString();
    }

    public byte[] download(String path, String fileName) {
        try {
            S3Object object = s3Client.getObject(path, fileName);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {

            throw new IllegalStateException("Failed to download the file", e);
        }
    }

    public void delete (String fileName){
        try {
            s3Client.deleteObject(BucketName.EXAM_MANAGEMENT_MEDIA_PATH.getBucketName(), fileName);
        }
        catch (AmazonServiceException e){
            throw new IllegalStateException("Deletion failed!", e);
        }
    }





}
