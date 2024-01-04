package com.adelekand.speer.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Data
@Getter
@Setter
@Builder
public class ApiExceptionResponse {
    private Boolean error;
    private HttpStatus statusCode;
    private String message;
}
