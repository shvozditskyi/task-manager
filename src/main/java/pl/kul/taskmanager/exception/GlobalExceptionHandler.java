package pl.kul.taskmanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> runtimeException(HttpServletRequest req, RuntimeException e) {
        log.error(e.getMessage(), e);
        ErrorDTO result = new ErrorDTO(e.getMessage(), HttpStatus.BAD_REQUEST, req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> methodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        String error = result.getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse(null);
        ErrorDTO errorDTO = new ErrorDTO(error, req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
}
