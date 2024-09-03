package co.istad.advanced_jpa.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Sattya
 * create at 9/1/2024 1:54 AM
 */
@RestControllerAdvice
public class ValidationException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException exception) {
        StringBuilder message = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach(error -> message.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; "));
        return new ErrorResponse(message.toString().trim());
    }

    public record ErrorResponse(String message) {
        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}

