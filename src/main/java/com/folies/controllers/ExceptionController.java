package com.folies.controllers;

import com.folies.entity.ErrorResponse;
import com.folies.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(EntityIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleEntityIllegalArgumentException(EntityIllegalArgumentException exception) {
        return createErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        return createErrorResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityHasDetailsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponse handleEntityIllegalArgumentException(EntityHasDetailsException exception) {
        return createErrorResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleEntityIllegalArgumentException(EntityNotFoundException exception) {
        return createErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    private static ErrorResponse createErrorResponse(BaseException exception, HttpStatus httpStatus) {
        return new ErrorResponse(exception.getMessage(), httpStatus.getReasonPhrase(), httpStatus.value());
    }
}
