package io.dmitrikonnov.demo;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;


@Lazy
@Configuration
@Slf4j
@EnableAsync
@EnableConfigurationProperties
public class AwsS3Config {

    private final String s3RegionName = "us-east-1";
    @Autowired
    private Environment environment;

    @Bean
    public AmazonS3 S3Client() {
        boolean isLocal = Arrays.stream(environment.getActiveProfiles())
                .peek(profileName -> log.info("Currently active profile - " + profileName))
                .anyMatch(profile -> profile.contains("local"));
        if(isLocal) {
            return S3LocalMachineClient();
        }
        return S3InCloudClient();
    }
    private AmazonS3 S3LocalMachineClient (){

        final AWSCredentials credentials = DefaultAWSCredentialsProviderChain.getInstance().getCredentials();
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(s3RegionName)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

    }

    private AmazonS3 S3InCloudClient() {
        return AmazonS3ClientBuilder.standard().withRegion(s3RegionName).build();
    }
}
