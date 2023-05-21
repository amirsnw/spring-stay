package com.stay.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed",
                ex.getBindingResult().toString());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    // Convert EntityNotFoundException to a REST response
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                     WebRequest request) {
        Set<String> objectViolations = new HashSet<>();
        Map<String, Set<String>> propertyViolations = new HashMap<>();

        Set<ValidationResult> results = new HashSet<>();
        String lastBeanClass = null;
        ValidationResult result = null;

        // reading all violations, make distinction between field constraints
        // and class constraints
        Set<ConstraintViolation<?>> violationSet = ex.getConstraintViolations();
        if (violationSet == null || violationSet.isEmpty()) {
            ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
                    ex.getClass().getSimpleName());
            return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
        }

        for (ConstraintViolation<?> violation : violationSet) {
            if (lastBeanClass == null
                    || !lastBeanClass.equals(violation.getRootBeanClass()
                    .getSimpleName())) {
                lastBeanClass = violation.getRootBeanClass().getSimpleName();
                result = new ValidationResult();
                result.setModel(lastBeanClass);
                results.add(result);
            }

            if ("".equals(violation.getPropertyPath().toString())) {
                objectViolations.add(violation.getMessage());
            } else {
                if (propertyViolations.get(violation.getPropertyPath()) == null) {
                    propertyViolations.put(violation.getPropertyPath()
                            .toString(), new HashSet<String>());
                }

                propertyViolations.get(violation.getPropertyPath().toString())
                        .add(violation.getMessage());
            }

            result.getObjectViolations().addAll(objectViolations);
            result.getPropertyViolations().putAll(propertyViolations);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity(results, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        Throwable rootCause = ex;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        if (rootCause instanceof javax.validation.ConstraintViolationException) {
            return handleConstraintViolationException((javax.validation.ConstraintViolationException) rootCause, request);
        }
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), rootCause.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
