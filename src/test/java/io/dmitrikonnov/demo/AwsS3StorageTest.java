package io.dmitrikonnov.demo;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class AwsS3StorageTest {
    private AwsS3Storage underTest;
    private final AmazonS3 s3;

    AwsS3StorageTest(@Autowired AmazonS3 s3) {
        this.s3 = s3;
    }


    @BeforeEach
    void setUp() {
        underTest = new AwsS3Storage(s3);
    }

    @Test
    void upload() {
    }

    @Test
    void getPresignedUrl() {
    }

    @Test
    void download() {
    }

    @Test
    void delete() {
    }
}