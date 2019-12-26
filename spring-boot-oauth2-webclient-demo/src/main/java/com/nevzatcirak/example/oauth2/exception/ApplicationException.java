package com.nevzatcirak.example.oauth2.exception;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
public abstract class ApplicationException extends RuntimeException {
    public static final int RESULT_CODE_FAILURE = 500;
    private static final long serialVersionUID = 1L;

    private String code = String.valueOf(RESULT_CODE_FAILURE);

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String code, String message) {
        super(message);
        setCode(code);
    }

    public ApplicationException(String code, String message, Throwable cause) {
        super(message, cause);
        setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}