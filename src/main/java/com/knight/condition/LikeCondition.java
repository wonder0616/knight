
package com.knight.condition;

/**
 * 模糊查询参数值
 *
 * @author knight
 * @since 2021-09-04
 */
public interface LikeCondition {
    /**
     * 模糊查询关键字
     */
    String LIKE_KEY = "%";

    /**
     * 获取sql语句中的模糊查询表达式
     *
     * @return String
     */
    default String getSqlCause() {
        return " like ? ";
    }

    /**
     * 查询参数值
     *
     * @return Object
     */
    Object getValue();
}
