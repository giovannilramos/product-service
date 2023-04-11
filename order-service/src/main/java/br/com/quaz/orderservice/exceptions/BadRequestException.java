package br.com.quaz.orderservice.exceptions;

public class BadRequestException extends BaseException {
    public BadRequestException(final String msg) {
        super(msg, 400);
    }
}
