package com.knight.exception;

/**
 * 系统初始化异常
 *
 * @author knight
 * @since 2021-06-23
 */
public class AppInitialException extends RuntimeException {
    /**
     * 构造函数
     */
    public AppInitialException() {
        super();
    }

    /**
     * 构造函数
     *
     * @param message 异常消息
     */
    public AppInitialException(String message) {
        super(message);
    }
}
