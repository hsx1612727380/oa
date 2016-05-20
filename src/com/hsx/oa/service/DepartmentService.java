package com.hsx.oa.service;

import java.util.List;

import com.hsx.oa.base.BaseSupport;
import com.hsx.oa.domain.Department;

public interface DepartmentService extends BaseSupport<Department> {

	/**
	 * 查询顶级部门列表
	 * @return
	 */
	public List<Department> findTopList();

	/**
	 * 查询下级部门列表
	 * @param parentId
	 * @return
	 */
	public List<Department> findChildren(Long parentId);

}
