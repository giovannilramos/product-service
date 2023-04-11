package br.com.quaz.orderservice.handler;

import br.com.quaz.orderservice.dto.ExceptionResponse;
import br.com.quaz.orderservice.exceptions.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ExceptionResponse> handleExceptions(final BaseException e) {
        return ResponseEntity.status(e.getCode()).body(new ExceptionResponse(
                ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")),
                e.getMessage()
        ));
    }
}
