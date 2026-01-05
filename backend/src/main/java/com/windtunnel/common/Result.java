package com.windtunnel.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果类
 * 
 * 定义API接口的统一响应格式
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 * @param <T> 响应数据类型
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public Result(Integer code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

    public Result(Integer code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    /**
     * 成功响应
     */
    public static <T> Result<T> success() {
        return new Result<>(Constants.ResponseCode.SUCCESS, "操作成功");
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(Constants.ResponseCode.SUCCESS, "操作成功", data);
    }

    /**
     * 成功响应（带消息和数据）
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(Constants.ResponseCode.SUCCESS, message, data);
    }

    /**
     * 失败响应
     */
    public static <T> Result<T> error() {
        return new Result<>(Constants.ResponseCode.ERROR, "操作失败");
    }

    /**
     * 失败响应（带消息）
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(Constants.ResponseCode.ERROR, message);
    }

    /**
     * 失败响应（带错误码和消息）
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message);
    }

    /**
     * 未授权响应
     */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(Constants.ResponseCode.UNAUTHORIZED, message);
    }

    /**
     * 禁止访问响应
     */
    public static <T> Result<T> forbidden(String message) {
        return new Result<>(Constants.ResponseCode.FORBIDDEN, message);
    }

    /**
     * 未找到响应
     */
    public static <T> Result<T> notFound(String message) {
        return new Result<>(Constants.ResponseCode.NOT_FOUND, message);
    }

}