package app.deyal.deyal_server.model;

import java.util.HashMap;

public class ApiError extends Exception {
    // Meta errors
    public static ApiError SUCCESS = new ApiError(0, "Success");
    public static ApiError UNAVAILABLE = new ApiError(1, "Unavailable");
    public static ApiError UNKNOWN = new ApiError(999, "Unknown error");

    // Validity errors
    public static ApiError INVALID_EMAIL = new ApiError(100, "Email address is not valid");
    public static ApiError EMAIL_NOT_VERIFIED = new ApiError(100, "Email address could not be verified");
    public static ApiError WRONG_PASSWORD = new ApiError(100, "Wrong Password");
    public static ApiError INVALID = new ApiError(100, "Invalid");
    public static ApiError INVALID_OTP = new ApiError(100, "Invalid code");

    // Database errors
    public static ApiError EMAIL_EXISTS = new ApiError(101, "Email already exists");
    public static ApiError EMAIL_NOT_FOUND = new ApiError(101, "Email address is not found");
    public static ApiError NOT_FOUND = new ApiError(102, "Not found");

    /* ------------------------------------------------------------------------- */

    private int code;

    protected ApiError(int code) {
        super();
        this.code = code;
    }

    protected ApiError(int code, String message) {
        super(message);
        this.code = code;
    }

    protected ApiError(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    protected ApiError(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /* ------------------------------------------------------------------------- */

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", getCode());
        result.put("message", getMessage());
        return result;
    }

    public <T> HashMap<String, Object> toMap(T payload) {
        HashMap<String, Object> result = this.toMap();
        result.put("payload", payload);
        return result;
    }

}
