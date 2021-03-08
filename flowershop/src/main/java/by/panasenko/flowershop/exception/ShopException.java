package by.panasenko.flowershop.exception;

public class ShopException extends RuntimeException {
    public ShopException() {
        super();
    }

    public ShopException(Throwable cause) {
        super(cause);
    }

    public ShopException(String message) {
        super(message);
    }

    public ShopException(String message, Throwable cause) {
        super(message, cause);
    }
}
