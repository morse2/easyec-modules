package com.googlecode.easyec.modules.bpmn2.query;

import com.googlecode.easyec.spirit.query.AbstractQuery;
import com.googlecode.easyec.spirit.web.controller.formbean.impl.AbstractSearchFormBean;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * 支持自定义Join的查询条件类
 *
 * @author JunJie
 */
public abstract class CustomJoinQuery<T> extends AbstractQuery<T> {

    private StringBuffer join = new StringBuffer();
    private StringBuffer clause = new StringBuffer();

    /**
     * 加入join语句
     *
     * @param s join语句
     * @return 返回泛型对象实例
     */
    protected T join(String s) {
        if (isNotBlank(s)) {
            join.append(s);
        }

        return getSelf();
    }

    /**
     * 加入查询条件语句，
     * 不需要带where关键字
     *
     * @param s 查询条件分句
     * @return 返回泛型对象实例
     */
    protected T clause(String s) {
        if (isNotBlank(s)) {
            clause.append(s);
        }

        return getSelf();
    }

    /**
     * 添加自定义的join语句。
     * 第二个参数的列应该跟
     * BPM_PROC_ENTITY的uidpk列
     * 相对应
     *
     * @param table  业务表
     * @param column 要做on匹配的字段
     * @return 返回泛型对象实例
     */
    public T customJoin(String table, String column) {
        return join(
            new StringBuffer()
                .append(" join ")
                .append(table)
                .append(" on ")
                .append(getProcessEntityAlias())
                .append(".uidpk = ")
                .append(column)
                .toString()
        );
    }

    /**
     * 添加自定义的join语句。
     * 第二个参数的列应该跟
     * Table B的{@code colB}相对应
     *
     * @param tableA 业务表
     * @param colA   Table A中的列名
     * @param colB   Table B中的列名
     * @return 返回泛型对象实例
     */
    public T customJoin(String tableA, String colA, String colB) {
        return customJoin(tableA, colA, colB, false);
    }

    /**
     * 添加自定义的join语句。
     * 第二个参数的列应该跟
     * Table B的{@code colB}相对应
     *
     * @param tableA   业务表
     * @param colA     Table A中的列名
     * @param colB     Table B中的列名
     * @param leftJoin 表示是否做left join
     * @return 返回泛型对象实例
     */
    public T customJoin(String tableA, String colA, String colB, boolean leftJoin) {
        return join(

            new StringBuffer()
                .append(leftJoin ? " left " : "")
                .append(" join ")
                .append(tableA)
                .append(" on ")
                .append(colA)
                .append(" = ")
                .append(colB)
                .toString()
        );
    }

    /**
     * 添加一句自定义的查询条件语句。
     * 该语句不应包含where关键字
     *
     * @param clause 查询条件语句
     * @return 返回泛型对象实例
     */
    public T customWhere(String clause) {
        return clause(clause);
    }

    /**
     * 添加自定义查询条件的参数值
     *
     * @param key   参数KEY
     * @param value 参数VALUE
     * @return 返回泛型对象实例
     */
    public T customTerm(String key, Object value) {
        addSearchTerm(key, value);

        return getSelf();
    }

    /**
     * 返回BPM_PROC_ENTITY表的别名
     *
     * @return 别名
     */
    abstract protected String getProcessEntityAlias();

    @Override
    public AbstractSearchFormBean getSearchFormBean() {
        AbstractSearchFormBean bean = super.getSearchFormBean();

        String join = this.join.toString();
        String clause = this.clause.toString();

        if (isNotBlank(join)) {
            bean.addSearchTerm("customJoinSQL", join);

            if (isNotBlank(clause)) {
                bean.addSearchTerm("customClauseSQL", clause);
            }
        }

        return bean;
    }
}
