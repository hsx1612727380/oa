package com.hsx.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hsx.oa.domain.PageBean;
import com.hsx.oa.util.QueryHelper;

/**
 * 基本Dao的实现类 - 持久层
 * 
 * @author hsx
 * 
 * @param <T>
 */
@Transactional
@SuppressWarnings("unchecked")
public class BaseSupportImpl<T> implements BaseSupport<T> {

	@Resource
	private SessionFactory sessionFactory;

	private Class<T> clazz;

	/**
	 * 通过反射技术获取T的真实类型
	 */
	public BaseSupportImpl() {
		// 获取当前 new的对象的泛型的父类的类型
		ParameterizedType parameterizedType = (ParameterizedType) this
				.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
		System.out.println("clazz -----> " + clazz);
	}

	/**
	 * 获取当前的可用的Session，用protected,表示子类也可以通过这个方法获取Session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T entity) {
		getSession().save(entity);
	}

	@Override
	public void delete(Long id) {
		Object object = getById(id);
		if (object != null) {
			getSession().delete(object);
		}
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public T getById(Long id) {
		if (id == null) {
			return null;
		} else {
			return (T) getSession().get(clazz, id);
		}
	}

	@Override
	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		} else {
			return getSession().createQuery(//
					"from " + clazz.getSimpleName() + " where id in (:ids)")//
					.setParameterList("ids", ids)//
					.list();
		}
	}

	@Override
	public List<T> findAll() {
		return getSession().createQuery(//
				"from " + clazz.getSimpleName())//
				.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageBean getPageBean(int pageNum, int pageSize, String hql,
			List<Object> parameters) {
		// 每一页数据列表
		/*List recordList = getSession().createQuery(//
				hql)//
				.setParameter(0, forum)//
				.setFirstResult((pageNum - 1) * pageSize)//
				.setMaxResults(pageSize)//
				.list();*/
		Query listQuery = getSession().createQuery(hql);
		if (parameters != null && parameters.size() != 0) {
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		List recordList =  listQuery.setFirstResult((pageNum - 1) * pageSize)//
		.setMaxResults(pageSize)//
		.list();
		
		// 总共多少条数据
		/*Long recordCount = (Long) getSession().createQuery(//
				"select count(*) from Topic t where t.forum = ?")//
				.setParameter(0, forum)//
				.uniqueResult();*/
		Query countQuery = getSession().createQuery("select count(*) " + hql);
		if (parameters != null && parameters.size() != 0) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long recordCount = (Long) countQuery.uniqueResult();
		
		return new PageBean(pageNum, pageSize, recordCount.intValue(),
				recordList);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public PageBean getPageBean(int pageNum, int pageSize,
			QueryHelper queryHelper) {
		List<Object> parameters = queryHelper.getWhereParameters();
		
		// 每一页数据列表
		Query listQuery = getSession().createQuery(queryHelper.getListQueryHql());
		if (parameters != null && parameters.size() != 0) {
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		List recordList =  listQuery.setFirstResult((pageNum - 1) * pageSize)//
				.setMaxResults(pageSize)//
				.list();
		
		// 总共多少条数据
		Query countQuery = getSession().createQuery(queryHelper.getCountQueryHql());
		if (parameters != null && parameters.size() != 0) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long recordCount = (Long) countQuery.uniqueResult();
		
		return new PageBean(pageNum, pageSize, recordCount.intValue(),
				recordList);
	}

}
