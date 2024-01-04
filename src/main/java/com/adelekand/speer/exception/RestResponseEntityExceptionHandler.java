package com.adelekand.speer.exception;

import com.adelekand.speer.dto.response.ApiExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AuthenticationException.class })
    @ResponseBody
    public ResponseEntity<ApiExceptionResponse> handleAuthenticationException(Exception ex) {

        var response = ApiExceptionResponse.builder()
                .error(true)
                .message("Invalid Credentials")
                .statusCode(HttpStatus.UNAUTHORIZED)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    @ExceptionHandler({ TooManyRequestsException.class })
    @ResponseBody
    public ResponseEntity<Object> handleTooManyRequestsException(TooManyRequestsException ex, WebRequest request) {

        var response = ApiExceptionResponse.builder()
                .error(true)
                .message(ex.getMessage())
                .statusCode(HttpStatus.TOO_MANY_REQUESTS)
                .build();

        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.TOO_MANY_REQUESTS, request);
    }

    @ExceptionHandler({NoteNotFoundException.class})
    protected ResponseEntity<Object> handleNoteNotFoundException(NoteNotFoundException ex, WebRequest request) {
        var response = ApiExceptionResponse.builder()
                .error(true)
                .message("Note with id '"+ ex.getMessage() + "' not found!")
                .statusCode(HttpStatus.NOT_FOUND)
                .build();

        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        var response = ApiExceptionResponse.builder()
                .error(true)
                .message("Something went wrong")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return handleExceptionInternal(ex, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
