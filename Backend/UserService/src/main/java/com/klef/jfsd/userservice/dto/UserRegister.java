package com.klef.jfsd.userservice.dto;

public record UserRegister(String firstName,
                           String lastName,
                           String email,
                           String password,
                           String userType) {
}
