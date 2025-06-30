package com.klef.jfsd.userservice.dto;


import java.util.Set;

public record ProfileRequest(
         String bio,
         String githubUrl,
         String linkedInUrl,
         String location,
         Set<String> skills ,
         String profilePictureUrl,
         String portfolioUrl) {
}
