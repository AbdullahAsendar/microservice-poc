package com.asendar.productservice.core.aspect;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author asendar
 */

@Builder
public record ResponseEnvelope(boolean success, int code, Object result) {

    public static ResponseEnvelope error(Exception e) {
        return ResponseEnvelope.builder()//
                .success(false)//
                .result(e.getMessage())//
                .code(-1)//
                .build();
    }

    public static ResponseEnvelope success(Object result) {
        return ResponseEnvelope.builder()//
                .success(true)//
                .result(result)//
                .code(1)//
                .build();
    }
}