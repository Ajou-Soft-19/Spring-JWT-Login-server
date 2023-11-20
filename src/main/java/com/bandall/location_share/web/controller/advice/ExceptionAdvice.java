package com.bandall.location_share.web.controller.advice;

import com.bandall.location_share.domain.exceptions.BadResponseException;
import com.bandall.location_share.domain.exceptions.EmailNotVerifiedException;
import com.bandall.location_share.web.controller.json.ApiResponseJson;
import com.bandall.location_share.web.controller.json.TokenStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;


@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    private static final String ERROR_MSG_KEY = "errMsg";
    private static final String EMAIL_KEY = "email";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ApiResponseJson handleRuntimeException(RuntimeException e) {
        log.error("", e);
        return new ApiResponseJson(HttpStatus.INTERNAL_SERVER_ERROR, TokenStatusCode.SERVER_ERROR, Map.of(ERROR_MSG_KEY, ControllerMessage.INTERNAL_SERVER_ERROR_MSG));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
    public ApiResponseJson handleDataAccessException(Exception e) {
        log.error("DB 오류 발생", e);
        return new ApiResponseJson(HttpStatus.INTERNAL_SERVER_ERROR, TokenStatusCode.SERVER_ERROR, Map.of(ERROR_MSG_KEY, ControllerMessage.INTERNAL_SERVER_ERROR_MSG));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResponseJson handleNoHandlerFoundException() {
        return new ApiResponseJson(HttpStatus.NOT_FOUND, TokenStatusCode.URL_NOT_FOUND, Map.of(ERROR_MSG_KEY, ControllerMessage.WRONG_PATH_ERROR_MSG));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ApiResponseJson handleBadRequestException(Exception e) {
        return new ApiResponseJson(HttpStatus.BAD_REQUEST, TokenStatusCode.WRONG_PARAMETER, Map.of(ERROR_MSG_KEY, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, TypeMismatchException.class, HttpMessageNotReadableException.class})
    public ApiResponseJson handleBadRequestBody() {
        return new ApiResponseJson(HttpStatus.BAD_REQUEST, TokenStatusCode.WRONG_PARAMETER, Map.of(ERROR_MSG_KEY, ControllerMessage.WRONG_REQUEST_ERROR_MSG));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ApiResponseJson handleBadCredentialsException(Exception e) {
        return new ApiResponseJson(HttpStatus.UNAUTHORIZED, TokenStatusCode.LOGIN_FAILED, Map.of(ERROR_MSG_KEY, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadResponseException.class)
    public ApiResponseJson handleBadResponseException(Exception e) {
        return new ApiResponseJson(HttpStatus.UNAUTHORIZED, TokenStatusCode.LOGIN_FAILED, Map.of(ERROR_MSG_KEY, e.getMessage()));
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponseJson handleMethodNotAllowedException() {
        return new ApiResponseJson(HttpStatus.METHOD_NOT_ALLOWED, 0, Map.of());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(EmailNotVerifiedException.class)
    public ApiResponseJson handleEmailNotVerifiedException(EmailNotVerifiedException e) {
        return new ApiResponseJson(HttpStatus.UNAUTHORIZED, TokenStatusCode.EMAIL_NOT_VERIFIED, Map.of(ERROR_MSG_KEY, e.getMessage(), EMAIL_KEY, e.getEmail()));
    }
}
