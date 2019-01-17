package cz.roi.test.dto;

import org.springframework.http.HttpStatus;

public class GenericResponse {
    public int status;
    public String message;

    public GenericResponse(HttpStatus statusCode, String message) {
        this.status = statusCode.value();
        this.message = message;
    }
}
