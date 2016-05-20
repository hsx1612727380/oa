package com.hsx.oa.service;

import java.util.Collection;
import java.util.List;

import com.hsx.oa.base.BaseSupport;
import com.hsx.oa.domain.Privilege;

public interface PrivilegeService extends BaseSupport<Privilege> {

	/**
	 * 查询顶级权限
	 * @return
	 */
	public List<Privilege> findTopList();

	/**
	 * 从数据库oa_privilege中查询所有的权限
	 * @return
	 */
	public Collection<String> getAllPrivilegeUrls();

}
