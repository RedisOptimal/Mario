package com.renren.infra.xweb.util;

import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

/**
 * mybatis的分页拦截器
 * 
 * @author yong.cao
 * @create-time 2013-10-11
 * @revision 1.0.0
 * @E_mail yong.cao@renren-inc.com
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {

    private final static String SQL_SELECT_REGEX = "(?is)^\\s*SELECT.*$";

    private final static String SQL_COUNT_REGEX = "(?is)^\\s*SELECT\\s+COUNT\\s*\\(\\s*(?:\\*|\\w+)\\s*\\).*$";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();

        String sql = boundSql.getSql();
        if (StringUtils.isBlank(sql)) {
            return invocation.proceed();
        }

        //select sql do 
        if (sql.matches(SQL_SELECT_REGEX) && !Pattern.matches(SQL_COUNT_REGEX, sql)) {
            Object obj = FieldUtils.readField(statementHandler, "delegate", true);
            // 反射获取 RowBounds 对象。
            RowBounds rowBounds = (RowBounds) FieldUtils.readField(obj, "rowBounds", true);

            // 分页参数存在且不为默认值时进行分页SQL构造
            if (rowBounds != null && rowBounds != RowBounds.DEFAULT) {
                FieldUtils.writeField(boundSql, "sql", newSql(sql, rowBounds), true);

                // 一定要还原否则将无法得到下一组数据(第一次的数据被缓存了)
                FieldUtils.writeField(rowBounds, "offset", RowBounds.NO_ROW_OFFSET, true);
                FieldUtils.writeField(rowBounds, "limit", RowBounds.NO_ROW_LIMIT, true);
            }
        }

        return invocation.proceed();
    }

    /**
     * 构造加入分页的SQL语句,暂时只是针对MySQL数据库
     * @param sql
     * @param rowBounds
     * @return SQL语句
     */
    private String newSql(String sql, RowBounds rowBounds) {
        StringBuffer sb = new StringBuffer();
        sb.append(sql).append(" limit ").append(rowBounds.getOffset()).append(",").append(rowBounds.getLimit());
        return sb.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println(properties);
    }

}
