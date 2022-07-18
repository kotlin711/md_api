package org.example.api.web.excption;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.example.api.model.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLException.class)
    public Result handleSqlException() {
        log.info("服务器异常");
        return Result.fail().message("服务器异常");
    }

    @ExceptionHandler(Exception.class)
    public Result AllException() {
        log.info("服务器异常");
        return Result.fail().message("服务器异常");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Result notFound(HttpServletResponse response) {
        response.setStatus(403);
        return Result.fail().message("服务器异常");
    }

    //    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public Result not_method(HttpServletResponse response){
//        response.setStatus(403);
//        return  Result.fail();
//    }
    @ExceptionHandler(JWTDecodeException.class)
    public Result jwt_decode(HttpServletResponse response) {
        response.setStatus(403);
        return Result.build(403, "拒绝访问");
    }

    @ExceptionHandler(TokenExpiredException.class)
    public Result token_expired() {
        return Result.build(405, "登录过期");
    }
}
