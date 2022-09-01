package id.holigo.services.holigocouponservice.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException() {
        super();
    }

    public CouponNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponNotFoundException(String message) {
        super(message);
    }

    public CouponNotFoundException(Throwable cause) {
        super(cause);
    }

    public CouponNotFoundException(String message, Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
