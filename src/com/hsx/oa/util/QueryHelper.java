package com.hsx.oa.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.support.DaoSupport;

import com.hsx.oa.base.BaseSupport;
import com.hsx.oa.domain.PageBean;
import com.opensymphony.xwork2.ActionContext;

/**
 *  用于辅助拼接HQL语句
 * @author hsx
 *
 */
public class QueryHelper {

	private String fromClause; //From子句
	private String whereClause = ""; // Where子句
	private String orderByClause = ""; // orderBy子句
	
	private List<Object> whereParameters = new ArrayList<Object>(); // 参数列表
	
	/**
	 * 生成from子句，from子句必须有
	 * @param clazz
	 * @param alias 别名
	 */
	public QueryHelper(Class clazz, String alias) {
		fromClause = "from " + clazz.getSimpleName() + " " + alias;
	}
	
	/**
	 * 拼接where子句,可以没有where子句
	 * @param condition
	 * @param params
	 * @return
	 */
	public QueryHelper addWhereCondition(String condition, Object... params) {
		if (whereClause.length() == 0) {
			whereClause = " where " + condition;
		}
		else {
			whereClause += " and " + condition;
		}
		if (params != null) { //这里是给问号传值
			for (Object p : params) {
				whereParameters.add(p);
			}
		}
		return this;
	}
	
	/**
	 * 如果第一个参数是true，则拼接where子句
	 * @param append
	 * @param condition
	 * @param params
	 * @return
	 */
	public QueryHelper addWhereCondition(boolean append, String condition, Object... params) {
		if (append) {
			addWhereCondition(condition, params);
		}
		return this;
	}
	
	/**
	 * 拼接orderBy子句,可以没有orderBy子句
	 * @param propertyName - 参与排序的属性名
	 * @param asc - true表示升序，false表示降序
	 * @return
	 */
	public QueryHelper addOrderByProperty(String propertyName, boolean asc) {
		if (orderByClause.length() == 0) {
			orderByClause = " order by " + propertyName + (asc ? " asc" : " desc");
		}
		else {
			orderByClause += " , " +  propertyName + (asc ? " asc" : " desc");
		}
		return this;
	}
	
	/**
	 * 如果第一个参数是true，则拼接orderBy子句
	 * @param append
	 * @param propertyName
	 * @param asc
	 * @return
	 */
	public QueryHelper addOrderByProperty(boolean append, String propertyName, boolean asc) {
		if (append) {
			addOrderByProperty(propertyName, asc);
		}
		return this;
	}
	
	/**
	 * 获取生成的用于查询数据列表的HQL语句
	 * @return
	 */
	public String getListQueryHql() {
		return fromClause + whereClause + orderByClause;
	}
	
	/**
	 * 获取生成的用于查询数据总记录数的HQL语句
	 * @return
	 */
	public String getCountQueryHql() {
		return "select count(*) " + fromClause + whereClause;
	}
	
	/**
	 * 获取HQL中的参数值列表
	 * @return
	 */
	public List<Object> getWhereParameters() {
		return whereParameters;
	}
	
	/**
	 * 准备分页信息，并把pageBean放到值栈栈顶
	 * @param service
	 * @param pageNum
	 * @param pageSize
	 * @param queryHelper
	 */
	public void preparePageBean(BaseSupport<?> service, int pageNum, int pageSize) {
		PageBean pageBean = service.getPageBean(pageNum, pageSize, this);
		ActionContext.getContext().getValueStack().push(pageBean);
	}
	
}
