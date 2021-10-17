package com.knight.util;

import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.lookup.StringLookupFactory;

/**
 * 配置项替换
 *
 * @since 2020-07-16
 */
public abstract class SubstitutorUtils {
    private static final StringSubstitutor ENV_SUBSTITUTOR;

    private static final StringSubstitutor SYS_SUBSTITUTOR;

    static {
        ENV_SUBSTITUTOR = new StringSubstitutor(StringLookupFactory.INSTANCE.environmentVariableStringLookup());
        SYS_SUBSTITUTOR = new StringSubstitutor(StringLookupFactory.INSTANCE.systemPropertyStringLookup());
    }

    /**
     * 用来自系统属性或系统属性的匹配值替换给定源对象中所有出现的变量。
     *
     * @param source 包含要替换的变量的源文本，null返回null
     * @return 替换操作的结果
     */
    public static String replacePlaceHolder(final String source) {
        return ENV_SUBSTITUTOR.replace(SYS_SUBSTITUTOR.replace(source));
    }
}