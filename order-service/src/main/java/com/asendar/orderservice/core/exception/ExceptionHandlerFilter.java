package com.asendar.orderservice.core.exception;

import com.asendar.orderservice.core.aspect.ResponseEnvelope;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author asendar
 */
@Component
@Order(1)
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (OrderServiceException e) {
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.getWriter().print(convertObjectToJson(ResponseEnvelope.error(e)));
        } catch (Exception e) {
            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(convertObjectToJson(ResponseEnvelope.error(e)));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}