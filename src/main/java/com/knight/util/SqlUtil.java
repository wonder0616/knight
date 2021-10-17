package com.knight.util;

import com.knight.condition.LikeCondition;
import com.knight.exception.ParamInvalidException;
import com.knight.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * SQL语句处理工具类
 *
 * @author knight
 * @since 2021-06-30
 */
@Slf4j
public class SqlUtil {
    private static final long ONE_DAY_MS = 3600 * 1000 * 24;

    private static final Pattern TABLE_PATTERN = Pattern.compile("@([\\w]+.[\\w]+)", Pattern.MULTILINE);

    private static final Pattern MAYBE_NULL_PATTERN = Pattern.compile("[a-zA-Z_]+=@([0-9]+)", Pattern.MULTILINE);

    private static final String DELIMITER = "_";

    private static final String IN_KEY_WORDS = " in\\(\\?\\)";

    /**
     * 根据开始结束时间，生成所有分表数据的合并查询语句 select a.colA from @PS.TBLA，只能是单表查询
     *
     * @param sql        分表查询语句
     * @param table      表名
     * @param partitions 所有分表的集合
     * @param params     参数
     * @return 合并查询语句
     */
    public static Pair<String, Object[]> getUnionSql(String sql, String table, Set<String> partitions,
                                                     Object[] params) {
        if (CollectionUtils.isEmpty(partitions)) {
            throw new ParamInvalidException("分表不存在");
        }
        StringJoiner stringJoiner = new StringJoiner(" UNION ALL ");
        Object[] newParams = new Object[0];
        for (String partition : partitions) {
            String newSql = sql.replaceAll("@" + table, table + partition);
            stringJoiner.add(newSql);
            newParams = ArrayUtils.addAll(newParams, params);
        }
        return Pair.of("select * from (" + stringJoiner.toString() + ")", newParams);
    }

    /**
     * 考虑可以在启动的时候，分析完成后，进行缓存
     *
     * @param sql sql语句
     * @return 表名
     */
    public static String[] grepTables(String sql) {
        Matcher matcher = TABLE_PATTERN.matcher(sql);
        Set<String> tables = new HashSet<>();
        // 使用find逐个查找
        while (matcher.find()) {
            tables.add(matcher.group(1));
        }
        return tables.toArray(new String[tables.size()]);
    }

    /**
     * 根据开始结束时间，生成所有分表名数组
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 分表名数组
     */
    public static Set<String> getDayPartitions(long startTime, long endTime) {
        Set<String> result = new TreeSet<>();
        long beginDayOffset = startTime / ONE_DAY_MS;
        long endDayOffset = endTime / ONE_DAY_MS;
        for (long i = beginDayOffset; i <= endDayOffset; i++) {
            result.add(DELIMITER + i);
        }
        return result;
    }

    /**
     * 根据开始结束时间，生成所有分表名数组
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 分表名数组
     */
    public static Set<String> getMonthPartitions(long startTime, long endTime) {
        Set<String> result = new TreeSet<>();
        int beginMonth = DateUtil.getMonthOffset(startTime);
        int endMonth = DateUtil.getMonthOffset(endTime);
        for (long i = beginMonth; i <= endMonth; i++) {
            result.add(DELIMITER + i);
        }
        return result;
    }

    /**
     * 获取今天分表名
     *
     * @return 分表名
     */
    public static String getTodayPartition() {
        long todayZero = DateUtil.getDayZero(System.currentTimeMillis()).getTime();
        long todayPartition = Math.round((double) todayZero / (double) ONE_DAY_MS);
        return String.valueOf(todayPartition);
    }

    /**
     * 根据开始结束时间，获取分表名
     *
     * @param startTime 开始时间
     * @return 分表名
     */
    public static String getPartition(long startTime) {
        long zeroDayMs = DateUtil.getDayZero(startTime).getTime();
        long beginDayOffset = Math.round((double) zeroDayMs / (double) ONE_DAY_MS);
        return String.valueOf(beginDayOffset);
    }

    /**
     * 获取前一天分表名
     *
     * @return 分表名
     */
    public static int getPrePartition() {
        long zeroDayMs = DateUtil.getDayZero(System.currentTimeMillis()).getTime();
        long beginDayOffset = Math.round((double) zeroDayMs / (double) ONE_DAY_MS);
        return (int) (beginDayOffset - 1);
    }

    /**
     * 获取当月表名
     *
     * @return 分表名
     */
    public static String getCurMonthPartition() {
        return String.valueOf(DateUtil.getMonthOffset(System.currentTimeMillis()));
    }

    /**
     * 根据时间，获取月分表名
     *
     * @param startTime 开始时间
     * @return 分表名
     */
    public static String getMonthPartition(long startTime) {
        return String.valueOf(DateUtil.getMonthOffset(startTime));
    }

    /**
     * 获取前一月分表名
     *
     * @return 分表名
     */
    public static String getPreMonthPartition() {
        return String.valueOf(DateUtil.getMonthOffset(System.currentTimeMillis()) - 1);
    }

    /**
     * 处理in查询中的？，替换成实际的问号数量，并将二维入参替换成一维数组
     * 提示：如果是in查询，需要保证传入非空数组
     *
     * @param sql    原始sql数据 select xxx from xxx where xxx in(?) and xx=xx and xx in(?)
     * @param params 入参
     * @return 处理后的sql语句和入参
     */
    public static Pair<String, Object[]> processInConditions(String sql, Object[] params) {
        if (ArrayUtils.isEmpty(params)) {
            return Pair.of(sql, params);
        }
        String newSql = sql;
        boolean existInQuery = false;
        for (Object param : params) {
            if (isArray(param)) {
                Object[] arrayParam = (Object[]) param;
                newSql = newSql.replaceFirst(IN_KEY_WORDS, createNewInCondition(arrayParam));
                existInQuery = true;
            }
        }
        if (existInQuery) {
            Object[] flatParams = Arrays.stream(params).flatMap(param -> {
                if (isArray(param)) {
                    Object[] arrayParam = (Object[]) param;
                    return Arrays.stream(arrayParam);
                }
                return Stream.of(param);
            }).toArray();
            return Pair.of(newSql, flatParams);
        }
        return Pair.of(sql, params);
    }

    /**
     * 替换空值参数
     *
     * @param sqlPair Pair
     * @return Pair
     */
    public static Pair<String, Object[]> processNullWhereParam(Pair<String, Object[]> sqlPair) {
        String newSql = sqlPair.getLeft();
        Object[] params = sqlPair.getRight();
        newSql = replaceNullWhereParam(newSql, params);
        if (params == null) {
            return Pair.of(newSql, null);
        }
        Object[] newParam = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Object value = params[i];
            if (value instanceof LikeCondition) {
                value = ((LikeCondition) value).getValue();
            }
            newParam[i] = value;
        }
        params = newParam == null ? null : Arrays.stream(newParam).filter(item -> item != null).toArray(Object[]::new);
        return Pair.of(newSql, params);
    }

    private static boolean isArray(Object arg) {
        if (arg == null) {
            return false;
        }
        return arg.getClass().isArray();
    }

    private static String createNewInCondition(Object[] array) {
        if (ArrayUtils.isEmpty(array)) {
            return IN_KEY_WORDS;
        }
        String inCondition = " in(";
        inCondition += StringUtils.repeat("?", ",", array.length);
        inCondition += ") ";
        return inCondition;
    }

    /**
     * 根据当前和每页页数分页sql
     *
     * @param sql         原始sql
     * @param currentPage 当前页数
     * @param pages       每页数量
     * @return 分页后sql
     */
    public static String getSqlPages(String sql, int currentPage, int pages) {
        int startIndex = (currentPage - 1) * pages;
        return "select * from ( " + sql + " ) " + " limit " + startIndex + "," + pages;
    }

    /**
     * 根据入参替换sql语句的条件 where id=@0 and ic=@1 and name=@2,对于空值，替换为 1=1
     *
     * @param sql    sql语句
     * @param params 入参
     * @return 新的sql
     */
    public static String replaceNullWhereParam(String sql, Object[] params) {
        Matcher matcher = MAYBE_NULL_PATTERN.matcher(sql);
        List<Integer> maybeNullIndex = new ArrayList<>();
        Map<Integer, String> conditions = new HashMap<>();
        while (matcher.find()) {
            int index = NumberUtils.toInt(matcher.group(1));
            maybeNullIndex.add(index);
            conditions.put(index, matcher.group(0));
        }
        if (maybeNullIndex.isEmpty()) {
            return sql;
        }
        String newSql = sql;
        for (Integer integer : maybeNullIndex) {
            if (params[integer] != null) {
                if (params[integer] instanceof LikeCondition) {
                    newSql = parseForLikeCondition(params, conditions, newSql, integer);
                } else {
                    newSql = newSql.replace("@" + integer, " ? ");
                }
            } else {
                newSql = newSql.replace(conditions.get(integer), " 1=1 ");
            }
        }
        log.info("newSql:{}", newSql);
        return newSql;
    }

    private static String parseForLikeCondition(Object[] params, Map<Integer, String> conditions, String sql,
                                                Integer integer) {
        String newSql = sql;
        LikeCondition likeCondition = (LikeCondition) params[integer];
        if (likeCondition.getValue() == null) {
            newSql = newSql.replace(conditions.get(integer), " 1=1 ");
        } else {
            newSql = newSql.replace("=@" + integer, likeCondition.getSqlCause());
        }
        return newSql;
    }

    /**
     * 根据传入的sql替换所有占位符为当天分表 select START_TIME,CALL_ID,PATH_KEY from @PS.TDR_SIP_INVITE and @PS.TDR_SIP_BYE
     *
     * @param sql sql语句
     * @return 新的sql
     */
    public static String replaceTodayPartition(String sql) {
        String[] tables = SqlUtil.grepTables(sql);
        String newSql = sql;
        String partitionTable = SqlUtil.getTodayPartition();
        for (String table : tables) {
            newSql = newSql.replaceAll("@" + table, table + '_' + partitionTable);
        }
        return newSql;
    }

    /**
     * 根据传入的sql替换所有占位符为当月分表 select START_TIME,CALL_ID,PATH_KEY from @PS.TDR_SIP_INVITE and @PS.TDR_SIP_BYE
     *
     * @param sql sql语句
     * @return 新的sql
     */
    public static String replaceCurMonthPartition(String sql) {
        String[] tables = SqlUtil.grepTables(sql);
        String newSql = sql;
        String curPartitionTable = SqlUtil.getCurMonthPartition();
        for (String table : tables) {
            newSql = newSql.replaceAll("@" + table, table + '_' + curPartitionTable);
        }
        return newSql;
    }

    /**
     * 根据传入的sql和时间替换所有占位符为根据时间获得的月分表 select START_TIME,CALL_ID,PATH_KEY from @PS.TDR_SIP_INVITE and @PS.TDR_SIP_BYE
     *
     * @param sql  sql语句
     * @param time 时间
     * @return 新的sql
     */
    public static String replaceMonthPartition(String sql, Long time) {
        String[] tables = SqlUtil.grepTables(sql);
        String newSql = sql;
        String partitionTable = SqlUtil.getMonthPartition(time);
        for (String table : tables) {
            newSql = newSql.replaceAll("@" + table, table + '_' + partitionTable);
        }
        return newSql;
    }

    /**
     * 根据sql嵌套返回查询总数量
     *
     * @param sql sql语句
     * @return 新的sql
     */
    public static String conposeSqlToQueryTotalCount(String sql) {
        if (StringUtils.isBlank(sql)) {
            log.error("[replaceQueryTotalWithSql] sql is null");
            return null;
        }
        return String.format("select count(*) as total from ( %s )", sql);
    }

    /**
     * 将sql替换为完整的执行语句 select XXX from Table where XXX in(?)
     *
     * @param sql sql语句
     * @return 新的sql
     */
    public static String replaceSqlPlaceholder(String sql, Object[] params) {
        Pair<String, Object[]> sqlAndParams = processNullWhereParam(Pair.of(sql, params));
        String newSql = sqlAndParams.getLeft();
        params = sqlAndParams.getRight();
        for (Object param : params) {
            if (param instanceof Integer) {
                param = String.valueOf(param);
            } else if (param instanceof String) {
                param = "'" + param + "'";
            }
            newSql = newSql.replaceFirst("\\?", String.valueOf(param));
        }
        return newSql;
    }
}