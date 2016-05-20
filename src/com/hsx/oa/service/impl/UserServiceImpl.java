package com.hsx.oa.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsx.oa.base.BaseSupportImpl;
import com.hsx.oa.domain.User;
import com.hsx.oa.service.UserService;
import com.hsx.oa.util.MD5Encoder;

@Transactional
@Service
public class UserServiceImpl extends BaseSupportImpl<User> implements UserService {

	@Override
	public User findByLoginNameAndPassword(String loginName, String password) {
		String md5Password = MD5Encoder.encode(password);
		return (User) getSession().createQuery(//
				"from User u where u.loginName = ? and u.password = ?")//
				.setParameter(0, loginName)//
				.setParameter(1, md5Password)//
				.uniqueResult();
	}

}
