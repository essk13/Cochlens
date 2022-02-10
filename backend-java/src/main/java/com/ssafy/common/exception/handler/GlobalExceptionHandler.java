package com.ssafy.common.exception.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Value("${spa.default-file}")
    String defaultFile;

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> renderDefaultPage(NoHandlerFoundException ex) {
        String url = ex.getRequestURL();
        if(url.startsWith("/api/")) {
            return ResponseEntity.notFound().build();
        }else {
            try {
                ClassPathResource classPathResource = new ClassPathResource(defaultFile);
                InputStream inputStream = classPathResource.getInputStream();
                String body = StreamUtils.copyToString(inputStream, Charset.defaultCharset());
                return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(body);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There was an error completing the action.");
            }
        }
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessLogicHandle(BusinessException exception) {
        return ErrorResponse.toResponseEntity(exception.getErrorCode());
    }
}
