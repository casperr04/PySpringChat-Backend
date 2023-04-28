package com.casperr04.pyspringchatbackend.exception;

import com.casperr04.pyspringchatbackend.model.dto.ExceptionResponseModel;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(MissingEntityException.class)
    public ResponseEntity<ExceptionResponseModel> handleMissingEntityException(MissingEntityException e){
        return ResponseEntity.badRequest()
                .body(new ExceptionResponseModel(e.getMessage(), Instant.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseModel> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponseModel(e.getMessage(), Instant.now()));
    }

    /*
    TODO: make the response more detailed, list which constraints have been violated
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponseModel> handleConstraintViolationException(ConstraintViolationException e){
        return ResponseEntity.badRequest()
                .body(new ExceptionResponseModel("At least one of the request fields does not fit constraints", Instant.now()));
    }

}
