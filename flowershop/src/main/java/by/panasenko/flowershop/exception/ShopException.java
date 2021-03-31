package by.panasenko.flowershop.exception;

public class ShopException extends Exception {
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
