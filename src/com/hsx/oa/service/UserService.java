package com.hsx.oa.service;

import com.hsx.oa.base.BaseSupport;
import com.hsx.oa.domain.User;

public interface UserService extends BaseSupport<User> {

	/**
	 * 根据用户名和密码查询用户
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User findByLoginNameAndPassword(String loginName, String password);

}
