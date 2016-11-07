package com.scottwseo.commons.rest.auth;

import io.dropwizard.auth.Authorizer;

public class SimpleAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role) {
        return user.getName().equals("admin") && role.equals("ADMIN");
    }

}
