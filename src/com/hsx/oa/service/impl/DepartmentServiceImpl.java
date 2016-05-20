package com.hsx.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsx.oa.base.BaseSupportImpl;
import com.hsx.oa.domain.Department;
import com.hsx.oa.service.DepartmentService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class DepartmentServiceImpl extends BaseSupportImpl<Department> implements DepartmentService {
	
	@Resource
	private SessionFactory sessionFactory;

	@Override
	public List<Department> findTopList() {
		return sessionFactory.getCurrentSession().createQuery(//
				"from Department d where d.parent is null")//
				.list();
	}

	@Override
	public List<Department> findChildren(Long parentId) {
		return sessionFactory.getCurrentSession().createQuery(//
				"from Department d where d.parent.id = ?")//
				.setParameter(0, parentId)//
				.list();
	}

}
