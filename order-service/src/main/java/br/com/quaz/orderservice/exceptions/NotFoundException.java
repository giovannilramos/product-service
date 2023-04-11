package br.com.quaz.orderservice.exceptions;

public class NotFoundException extends BaseException {
    public NotFoundException(final String msg) {
        super(msg, 404);
    }
}
