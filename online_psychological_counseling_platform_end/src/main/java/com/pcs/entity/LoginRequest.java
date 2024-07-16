package com.pcs.entity;

import lombok.Data;

@Data
public class LoginRequest {

    private String input;

    private String password;

    private String accountIdentify;


}
