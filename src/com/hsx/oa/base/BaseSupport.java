package com.hsx.oa.base;

import java.util.List;

import com.hsx.oa.domain.Forum;
import com.hsx.oa.domain.PageBean;
import com.hsx.oa.util.QueryHelper;

/**
 * 基本的Dao~Service - 持久层
 * @author hsx
 *
 */
public interface BaseSupport<T> {
	
	/**
	 * 保存实体
	 * @param entity
	 */
	public void save(T entity);
	
	/**
	 * 删除实体
	 * @param id
	 */
	public void delete(Long id);
	
	/**
	 * 修改实体
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public T getById(Long id);
	
	/**
	 * 根据ID数组查询
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(Long[] ids);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll();
	
	/**
	 * 查询带有分页数据
	 * @param pageNum
	 * @param pageSize
	 * @param hql 数据库查询语句
	 * @param parameters 参数列表，器顺序与hql语句中的问号一一对应
	 * @return
	 */
	@Deprecated
	public PageBean getPageBean(int pageNum, int pageSize, String hql, List<Object> parameters);

	/**
	 * 查询带有分页数据(最终版)
	 * @param pageNum
	 * @param pageSize
	 * @param queryHelper - HQL语句与参数列表
	 * @return
	 */
	public PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper);
}
