package cz.roi.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General error
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CommunicationException extends RuntimeException {
    private Integer statusCode;
    private String details;

    public CommunicationException(Integer statusCode, String message, String details) {
        super(String.format("%s. Status code: %s. Details: %s", message, statusCode, details));
        this.statusCode = statusCode;
        this.details = details;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getDetails() {
        return details;
    }

    public Boolean getSuccess() {
        return false;
    }
}
