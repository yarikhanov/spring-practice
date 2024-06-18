package com.example.spring_practice.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class AwsClient {

    private static final AWSCredentials credentials = new BasicAWSCredentials("YCAJEVYiUKLnvkjxS5M55cHWV", "YCOxRrQd8fSDYDO3sTsV_BEk7Uugqdq9lmbIgz1Y");

    public static AmazonS3 getAwsClient() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.DEFAULT_REGION)
                .build();
    }

}
