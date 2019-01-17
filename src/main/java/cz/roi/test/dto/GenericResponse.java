package cz.roi.test.dto;

import org.springframework.http.HttpStatus;

/**
 * Common generic response
 */
public class GenericResponse {
    public int status;
    public String message;
    public Object data;

    public GenericResponse(HttpStatus statusCode, String message) {
        this.status = statusCode.value();
        this.message = message;
    }

    public GenericResponse(Object data) {
        this.status = HttpStatus.OK.value();
        this.data = data;
    }
}
