package com.nevzatcirak.example.oauth2.exception;


/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
public class ApplicationServiceApiException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public ApplicationServiceApiException(String message) {
        super(message);
    }

    public ApplicationServiceApiException(Throwable cause) {
        super(cause);
    }

    public ApplicationServiceApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationServiceApiException(String code, String message) {
        super(code, message);
    }

    public ApplicationServiceApiException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

}
