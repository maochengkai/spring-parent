package com.emily.infrastructure.autoconfigure.exception.handler;


import com.emily.infrastructure.common.entity.BaseResponse;
import com.emily.infrastructure.common.enums.HttpStatusType;
import com.emily.infrastructure.common.exception.BasicException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.UnknownContentTypeException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author Emily
 * @Description: 控制并统一处理异常类
 * @ExceptionHandler标注的方法优先级问题，它会找到异常的最近继承关系，也就是继承关系最浅的注解方法
 * @Version: 1.0
 */
@RestControllerAdvice
public class DefaultGlobalExceptionHandler extends GlobalExceptionCustomizer {
    /**
     * 未知异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public BaseResponse exceptionHandler(Exception e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.EXCEPTION.getStatus(), e.getMessage());
    }

    /**
     * 运行时异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.EXCEPTION.getStatus(), e.getMessage());
    }

    /**
     * 业务异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BasicException.class)
    public BaseResponse basicException(BasicException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(e.getStatus(), e.getMessage());
    }

    /**
     * 非法代理
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UndeclaredThrowableException.class)
    public BaseResponse undeclaredThrowableException(UndeclaredThrowableException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_PROXY);
    }

    /**
     * 空指针异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NullPointerException.class)
    public BaseResponse nullPointerExceptionHandler(NullPointerException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_DATA);
    }

    /**
     * 类型转换异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ClassCastException.class)
    public BaseResponse classCastExceptionHandler(ClassCastException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_DATA);
    }

    /**
     * IO异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IOException.class)
    public BaseResponse ioExceptionHandler(IOException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.EXCEPTION);
    }

    /**
     * 数组越界异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public BaseResponse indexOutOfBoundsException(IndexOutOfBoundsException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_DATA);
    }

    /**
     * API-参数类型不匹配
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(TypeMismatchException.class)
    public BaseResponse requestTypeMismatch(TypeMismatchException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_ARGUMENT);
    }

    /**
     * API-缺少参数，如Get请求@RequestParam注解
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingRequestValueException.class)
    public BaseResponse missingRequestValueException(MissingRequestValueException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_ARGUMENT);
    }


    /**
     * API-控制器方法参数Validate异常
     *
     * @throws BindException
     * @throws MethodArgumentNotValidException
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class})
    public BaseResponse bindException(BindException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_ARGUMENT);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse httpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_ARGUMENT);
    }

    /**
     * Get请求参数校验，如@NotEmpty、@NotNull等等
     *
     * @param e
     * @param request
     * @return
     * @throws ConstraintViolationException,ValidationException
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public BaseResponse validationException(ValidationException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_ARGUMENT);
    }

    /**
     * 非法参数异常捕获
     *
     * @param e
     * @param request
     * @return
     * @throws IllegalArgumentException
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse validationException(IllegalArgumentException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_ARGUMENT);
    }

    /**
     * API-请求method不匹配
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_METHOD);
    }

    /**
     * 数字格式异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NumberFormatException.class)
    public BaseResponse numberFormatException(NumberFormatException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_DATA);
    }

    /**
     * 非法计算异常
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ArithmeticException.class)
    public BaseResponse arithmeticException(ArithmeticException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_DATA);
    }

    /**
     * 非法访问
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UnknownContentTypeException.class)
    public BaseResponse unknownContentTypeException(UnknownContentTypeException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_ACCESS);
    }

    /**
     * 非法访问
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ResourceAccessException.class)
    public BaseResponse resourceAccessException(ResourceAccessException e, HttpServletRequest request) {
        recordErrorMsg(e, request);
        return BaseResponse.buildResponse(HttpStatusType.ILLEGAL_ACCESS);
    }
}

