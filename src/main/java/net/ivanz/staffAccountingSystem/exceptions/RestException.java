package net.ivanz.staffAccountingSystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class RestException extends RuntimeException{
    private final String errorCode;
    private final String message;
    private final Date date;
    private final HttpStatus httpStatus;

    public RestException(ErrorCodes error) {
        errorCode = error.getErrorCode();
        message = error.getMessage();
        httpStatus = error.getHttpStatus();
        date = new Date();
    }

    public RestException(String errorCode, String message, HttpStatus httpStatus) {
        this.message = message;
        this.errorCode = errorCode;
        this.date = new Date();
        this.httpStatus = httpStatus;
    }
}
