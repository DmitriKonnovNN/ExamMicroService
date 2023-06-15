package io.dmitrikonnov.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BucketName {
    EXAM_MANAGEMENT_MEDIA_PATH("exam-management-service-dev");
    private final String bucketName;
}