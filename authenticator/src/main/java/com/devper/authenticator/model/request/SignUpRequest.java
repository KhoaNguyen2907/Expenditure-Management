package com.devper.authenticator.model.request;

import com.devper.authenticator.validation.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank
    @UniqueUsername
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
}
