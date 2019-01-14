package cz.roi.test.dto;

public class GenericResponse {
    public Boolean success;
    public String error; // todo enum
    public String message;

    public GenericResponse(Boolean success, String error, String message) {
        this.success = success;
        this.error = error;
        this.message = message;
    }
}
