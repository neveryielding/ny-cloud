package com.nycloud.auth.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author super
 */
@Data
public class Login {

    @NotEmpty
    @Size(min = 5, max = 16)
    private String username;

    @NotEmpty
    @Size(min = 32, max = 32)
    private String password;

    @NotEmpty
    private String client;

}