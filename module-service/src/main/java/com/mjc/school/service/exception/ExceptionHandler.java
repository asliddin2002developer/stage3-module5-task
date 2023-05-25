package com.mjc.school.service.exception;

import com.mjc.school.repository.exception.EntityCreationConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        ExceptionPayload exceptionPayload = new ExceptionPayload(
                e.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionPayload, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceErrorException.class)
    public ResponseEntity<Object> handleServiceErrorException(ServiceErrorException e){
        ExceptionPayload payload = new ExceptionPayload(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(payload, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(EntityCreationConflictException.class)
    public ResponseEntity<Object> handleConflictCreatingEntityException(EntityCreationConflictException e){
        ExceptionPayload payload = new ExceptionPayload(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(payload, HttpStatus.BAD_REQUEST);
    }

}
