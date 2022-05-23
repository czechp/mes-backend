package pl.bispol.mesbispolserver.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.bispol.mesbispolserver.exception.BadRequestException;
import pl.bispol.mesbispolserver.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;

@ControllerAdvice()
public class WebExceptionHandler {

    @ExceptionHandler({BadRequestException.class, ConstraintViolationException.class})
    ResponseEntity badRequestExceptionHandler(Exception exception, WebRequest webRequest) {
        return createResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    ResponseEntity notFoundExceptionHandler(Exception exception, WebRequest webRequest) {
        return createResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    private ResponseEntity createResponse(HttpStatus httpStatus, String message) {
        HashMap<String, String> body = new HashMap<>();
        body.put("message", message);
        body.put("time stamp", LocalDateTime.now().toString());
        body.put("status", httpStatus.toString());
        return new ResponseEntity(body, httpStatus);
    }
}
