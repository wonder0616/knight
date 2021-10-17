package com.knight.util;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 参数校验工具类
 *
 * @author knight
 * @since 2021-07-26
 */
public class ValidateUtil {
    /**
     * 参数校验是否有错
     *
     * @param bindingResult 参数校验类
     * @return Boolean
     */
    public static Boolean paramError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return true;
        }
        return false;
    }

    /**
     * 获取参数校验所有错误信息
     *
     * @param bindingResult 参数校验类
     * @return String
     */
    public static String getErrorMsg(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorList = bindingResult.getAllErrors()
                    .stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return String.join(",", errorList);
        }
        return "error message is null";
    }

    /**
     * 校验开始和结束时间
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return Boolean
     */
    public static Boolean timeError(Long startTime, Long endTime) {
        if (startTime.longValue() > endTime.longValue()) {
            return true;
        }
        return false;
    }
}
