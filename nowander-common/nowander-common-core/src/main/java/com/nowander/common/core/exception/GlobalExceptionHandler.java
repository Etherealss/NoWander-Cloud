package com.nowander.common.core.exception;

import com.nowander.common.core.enums.ApiInfo;
import com.nowander.common.core.exception.internal.BugException;
import com.nowander.common.core.exception.rest.ParamErrorException;
import com.nowander.common.core.exception.rest.RestException;
import com.nowander.common.core.exception.service.AuthenticationException;
import com.nowander.common.core.exception.service.ServiceFiegnException;
import com.nowander.common.core.exception.service.SimpleServiceException;
import com.nowander.common.core.pojo.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * @author wtk
 * @version 2.0 将多个异常合并
 * @description 全局异常处理
 * @date 2021-08-13
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            UnsupportedOperationException.class,
            SimpleServiceException.class,
            RestException.class
    })
    public Msg<Void> handle(BaseException e) {
        log.info("业务异常：" + e.getMessage());
        return new Msg<>(e);
    }

    /**
     * 权限认证异常
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthenticationException.class)
    public Msg<Object> handle(AuthenticationException e) {
        log.info("权限认证异常: {}", e.getMessage());
        return new Msg<>(e);
    }

    /**
     * 前后端交接的接口参数缺失
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Msg<Object> handle(MissingServletRequestParameterException e) {
        log.warn("前后端交接的接口参数缺失:" + e.getMessage());
        return new Msg<>(false, ApiInfo.ERROR_PARAM, "前后端交接的接口参数缺失");
    }

    /**
     * 参数不可读
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Msg<Object> handle(HttpMessageNotReadableException e) {
        log.warn("参数不可读异常：" + e.getMessage());
        return new Msg<>(false, ApiInfo.ERROR_PARAM, "参数不可读，请检查参数列表是否完整：" + e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Msg<Void> handle(MethodArgumentTypeMismatchException e) {
        // 可能会收到 EnumIllegalException
        Throwable throwable = e;
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        if (throwable instanceof BaseException) {
            return handle((BaseException) throwable);
        } else {
            return new Msg<>(false, ApiInfo.OPERATE_UNSUPPORTED, "方法参数不匹配：" + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Msg<Void> handle(HttpRequestMethodNotSupportedException e) {
        return new Msg<>(false, ApiInfo.OPERATE_UNSUPPORTED, "请求方式不支持：" + e.getMessage());
    }

    /**
     * 包装绑定异常结果
     */
    @ExceptionHandler(BindException.class)
    private Msg<Void> wrapperBindingResult(BindException bindException) {
        BindingResult bindingResult = bindException.getBindingResult();
        StringBuilder msg = new StringBuilder();

        for (ObjectError error : bindingResult.getAllErrors()) {
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
            msg.append(", ");

        }
        String s = msg.delete(msg.length() - 2, msg.length()).toString();
        return new Msg<>(false, ApiInfo.ERROR_PARAM, s);
    }

    /**
     * 验证异常处理
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Msg<Void> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        StringBuilder msg = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            msg.append(constraintViolation.getPropertyPath()).append(": ");
            msg.append(constraintViolation.getMessage() == null ? "" : constraintViolation.getMessage());
            msg.append(", ");
        }
        String s = msg.delete(msg.length() - 2, msg.length()).toString();
        return new Msg<>(false, ApiInfo.ERROR_PARAM, s);
    }

    /**
     * 后端有bug
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BugException.class)
    public Msg<?> handle(BugException e) {
        return new Msg<>(e);
    }

    /**
     * 其他未处理异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServiceFiegnException.class)
    public Msg<?> handle(ServiceFiegnException e) {
        log.warn("[全局异常处理器] OpenFeign 远程调用出现异常: {}", e.getMsg());
        return e.getMsg();
    }

    /**
     * 其他未处理异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Msg<Object> handle(Exception e) {
        if (e instanceof IllegalStateException) {
            if (e.getMessage().contains("argument type mismatch\nController")) {
                log.warn("参数类型错误:", e);
                return new Msg<>(new ParamErrorException("参数类型错误：" + e.getMessage()));
            }
        }
        log.warn("[全局异常处理器]其他异常:", e);
        return new Msg<>(e);
    }
}
