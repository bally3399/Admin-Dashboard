package com.fortunae.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AWSConfig {
    
    @Value("${aws.accessKey}")
    private String accessKey;


  @Value("${aws.secretKey}")
  private String secretKey;



    @Bean
    public AmazonEC2 amazonEC2() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion("us-east-1") // specify your desired region
                .build();
    }
}
