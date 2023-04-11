package br.com.quaz.orderservice.dto;

import java.time.ZonedDateTime;

public record ExceptionResponse(ZonedDateTime zonedDateTime, String message) {
}
