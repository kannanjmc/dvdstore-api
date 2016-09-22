package com.scottwseo.commons.cfg;

import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.scottwseo.commons.util.EnvVariables;


public class SimpleAWSCredentialProviderChain  extends AWSCredentialsProviderChain {
    public SimpleAWSCredentialProviderChain() {
        super(new AWSCredentialsProvider[]{new EnvironmentVariableCredentialsProvider(),
                new SystemPropertiesCredentialsProvider(),
                new ProfileCredentialsProvider(EnvVariables.AWS_PROFILE.getString()),
                new EC2ContainerCredentialsProviderWrapper()});
    }
}
