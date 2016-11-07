package com.scottwseo.commons.rest.auth;

import javax.security.auth.Subject;
import java.security.Principal;

/**
 * Created by seos on 9/16/15.
 */
public class User implements Principal {

    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

}
