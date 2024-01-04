package com.adelekand.speer.dto.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequest {
    private String username;
    private String password;
}
