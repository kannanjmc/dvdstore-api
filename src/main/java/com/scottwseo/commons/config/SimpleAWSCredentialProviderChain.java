package com.scottwseo.commons.config;

import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;


public class SimpleAWSCredentialProviderChain  extends AWSCredentialsProviderChain {
    public SimpleAWSCredentialProviderChain() {
        super(new AWSCredentialsProvider[]{new EnvironmentVariableCredentialsProvider(),
                new SystemPropertiesCredentialsProvider(),
                new ProfileCredentialsProvider(EnvVariables.AWS_PROFILE.value()),
                new EC2ContainerCredentialsProviderWrapper()});
    }
}
