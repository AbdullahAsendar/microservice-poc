package com.asendar.productservice.core.aspect;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author asendar
 */
@Slf4j
@ControllerAdvice
public class ResponseInterceptorAspect implements ResponseBodyAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception e, WebRequest request) {
        log.error("Error occurred while processing request", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseEnvelope.error(e));

    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (selectedContentType != MediaType.APPLICATION_JSON)
            return body;
        return body instanceof ResponseEnvelope ? body : ResponseEnvelope.success(body);
    }

}
