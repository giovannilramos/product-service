package br.com.quaz.orderservice.exceptions;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(final String msg) {
        super(msg, 503);
    }
}
