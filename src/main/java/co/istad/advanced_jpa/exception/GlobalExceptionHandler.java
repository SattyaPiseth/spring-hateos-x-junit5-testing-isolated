package co.istad.advanced_jpa.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Sattya
 * create at 9/1/2024 3:43 AM
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Handle ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException exception){
        ErrorResponse errorResponse = new ErrorResponse(exception.getStatusCode().value(), exception.getReason());
        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse);
    }

   public record ErrorResponse(int statusCode, String reason){
        public ErrorResponse(int statusCode, String reason) {
            this.statusCode = statusCode;
            this.reason = reason;
        }
    }
}
