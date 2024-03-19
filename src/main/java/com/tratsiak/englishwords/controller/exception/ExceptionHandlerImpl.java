package com.tratsiak.englishwords.controller.exception;

import com.tratsiak.englishwords.service.exception.LevelException;
import com.tratsiak.englishwords.service.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Slf4j
public class ExceptionHandlerImpl implements ExceptionHandler {
    @Override
    public ResponseStatusException handle(ServiceException e) throws ResponseStatusException {

        LevelException levelException = e.getLevelException();

        if (levelException.equals(LevelException.INFO)) {
            log.info(e.getMessage());
            return new ResponseStatusException(HttpStatus.NOT_FOUND, e.getPublicMessage());
        }

        if (levelException.equals(LevelException.WARM)) {
            log.warn(e.getMessage());
            return new ResponseStatusException(HttpStatus.NOT_FOUND, e.getPublicMessage());
        }

        if (levelException.equals(LevelException.ERROR)) {
            log.error(e.getMessage(), e);
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getPublicMessage());
        }

        return null;
    }
}
