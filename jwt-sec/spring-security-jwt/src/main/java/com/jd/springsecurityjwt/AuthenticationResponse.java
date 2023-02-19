package com.jd.springsecurityjwt;

/**
 * Created by jd birla on 18-02-2023 at 08:56
 */
import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}