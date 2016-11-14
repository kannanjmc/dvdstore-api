package com.scottwseo.commons.config;

import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;


public class SimpleAWSCredentialProviderChain  extends AWSCredentialsProviderChain {
    public SimpleAWSCredentialProviderChain(String profileName) {
        super(new AWSCredentialsProvider[]{new EnvironmentVariableCredentialsProvider(),
                new SystemPropertiesCredentialsProvider(),
                new ProfileCredentialsProvider(profileName),
                new EC2ContainerCredentialsProviderWrapper()});
    }
}
