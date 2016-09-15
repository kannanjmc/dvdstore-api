package com.scottwseo.commons.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sseo on 9/7/16.
 */
public class S3Util {

    private static final Logger LOG = LoggerFactory.getLogger(S3Util.class);

    public static void put(String bucket, String key, File file) throws IOException {
        AmazonS3 s3client = new AmazonS3Client(new DefaultAWSCredentialsProviderChain());
        try {
            s3client.putObject(new PutObjectRequest(bucket, key, file));
        } catch (AmazonServiceException ase) {
            log(ase);
        } catch (AmazonClientException ace) {
            loge(ace);
        }
    }

    public static InputStream get(String bucket, String key) throws IOException {
        AmazonS3 s3client = new AmazonS3Client(new DefaultAWSCredentialsProviderChain());
        try {
            S3Object s3Object = s3client.getObject(bucket, key);
            return s3Object.getObjectContent();
        } catch (AmazonServiceException ase) {
            log(ase);
        } catch (AmazonClientException ace) {
            // loge(ace);
        }
        return null;
    }

    private static void loge(AmazonClientException ace) {
        LOG.warn("Caught an AmazonClientException, which " +
                "means the client encountered " +
                "an internal error while trying to " +
                "communicate with S3, " +
                "such as not being able to access the network.");
        LOG.warn("Error Message: " + ace.getMessage());
    }

    private static void log(AmazonServiceException ase) {
        LOG.info("Caught an AmazonServiceException, which " +
                "means your request made it " +
                "to Amazon S3, but was rejected with an error response" +
                " for some reason.");
        LOG.info("Error Message:    " + ase.getMessage());
        LOG.info("HTTP Status Code: " + ase.getStatusCode());
        LOG.info("AWS Error Code:   " + ase.getErrorCode());
        LOG.info("Error Type:       " + ase.getErrorType());
        LOG.info("Request ID:       " + ase.getRequestId());
    }

}
