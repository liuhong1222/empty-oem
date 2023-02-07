package com.cc.oem.common.exception;

import com.cc.oem.common.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 异常处理器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class RRExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());

        return r;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public R handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return R.error("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public R bindException(BindException ex) {
        StringBuilder strBuilder = new StringBuilder();
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : errors) {
            strBuilder.append(fieldError.getDefaultMessage() + ",");
        }
        strBuilder.deleteCharAt(strBuilder.length() - 1);
        logger.error(String.format("参数检验异常:%s", strBuilder.toString()), ex);
        return R.error(400, String.format("校验异常：%s", strBuilder.toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public R handleResourceNotFoundException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "\n");
        }
        strBuilder.deleteCharAt(strBuilder.length() - 1);
        logger.error(String.format("检验异常:%s", strBuilder.toString()), ex);
        return R.error(400, String.format("校验异常：%s", strBuilder.toString()));
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        if (result.hasErrors()) {
            Iterator<ObjectError> iterator = result.getAllErrors().iterator();
            while (iterator.hasNext()) {
                ObjectError objectError = iterator.next();
                stringBuilder.append(objectError.getDefaultMessage());
                stringBuilder.append(",");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        logger.error(String.format("检验异常:%s", stringBuilder.toString()), ex);
        return R.error(400, String.format("校验异常：%s", stringBuilder.toString()));
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public R handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("检验异常:", ex);
        return R.error(400, String.format("校验异常：%s", ex.getMessage()));
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public R handleSocketTimeoutException(SocketTimeoutException ex) {
        logger.error("请求超时:", ex);
        return R.error(400, String.format("请求超时，请重试"));
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error();
    }

}
