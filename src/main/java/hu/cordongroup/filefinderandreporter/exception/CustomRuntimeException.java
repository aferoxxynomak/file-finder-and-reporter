package hu.cordongroup.filefinderandreporter.exception;

import hu.cordongroup.filefinderandreporter.util.Constants;

public class CustomRuntimeException extends Exception {
    public CustomRuntimeException() {
        super();
    }

    public CustomRuntimeException(String message) {
        super(message);
    }

    public CustomRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomRuntimeException(Throwable cause) {
        super(cause);
    }

    public CustomRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return Constants.ERROR_MESSAGE_COLOR + super.getMessage();
    }
}
