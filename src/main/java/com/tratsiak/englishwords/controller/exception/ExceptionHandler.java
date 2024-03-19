package com.tratsiak.englishwords.controller.exception;

import com.tratsiak.englishwords.service.exception.ServiceException;
import org.springframework.web.server.ResponseStatusException;

public interface ExceptionHandler {

    ResponseStatusException handle(ServiceException e);
}
