package pl.kul.taskmanager.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String path;

    public ErrorDTO(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public ErrorDTO(String message, LocalDateTime timestamp) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
        this.timestamp = timestamp.truncatedTo(ChronoUnit.SECONDS);
    }

    public ErrorDTO(String message, String path) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
        this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.path = path;
    }

    public ErrorDTO(String message, HttpStatus status, String path) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        this.path = path;
    }

    public ErrorDTO(String message, HttpStatus status, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp.truncatedTo(ChronoUnit.SECONDS);;
    }

    public ErrorDTO(String message, HttpStatus status, LocalDateTime timestamp, String path) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp.truncatedTo(ChronoUnit.SECONDS);;
        this.path = path;
    }
}
