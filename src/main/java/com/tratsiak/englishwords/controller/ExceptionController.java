package com.tratsiak.englishwords.controller;


import com.tratsiak.englishwords.model.dto.ErrorResponse;
import com.tratsiak.englishwords.service.exception.LevelException;
import com.tratsiak.englishwords.service.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processValidationError(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = null;
        for (FieldError fieldError : fieldErrors) {
            message = "; " + fieldError.getDefaultMessage();
        }

        assert message != null;
        message = message.replaceFirst("; ", "");
        return ErrorResponse.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .error("Bad request")
                .status(500)
                .message(message)
                .path(request.getRequestURL().toString())
                .build();
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> processValidationError(ServiceException e, HttpServletRequest request) {
        LevelException levelException = e.getLevelException();
        HttpStatus status = null;

        if (levelException.equals(LevelException.INFO)) {
            log.info(e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        if (levelException.equals(LevelException.WARM)) {
            log.warn(e.getMessage());
            status = HttpStatus.NOT_FOUND;
        }

        if (levelException.equals(LevelException.ERROR)) {
            log.error(e.getMessage(), e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;

        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Timestamp.from(Instant.now()))
                .error(status.getReasonPhrase())
                .status(status.value())
                .message(e.getPublicMessage())
                .path(request.getRequestURL().toString())
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}
