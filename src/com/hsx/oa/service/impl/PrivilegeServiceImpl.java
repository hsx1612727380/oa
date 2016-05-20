package com.hsx.oa.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsx.oa.base.BaseSupportImpl;
import com.hsx.oa.domain.Privilege;
import com.hsx.oa.service.PrivilegeService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl extends BaseSupportImpl<Privilege> implements PrivilegeService {

	@Override
	public List<Privilege> findTopList() {
		return getSession().createQuery(//
				"from Privilege p where p.parent is null")//
				.list();
	}

	@Override
	public Collection<String> getAllPrivilegeUrls() {
		return getSession().createQuery(//
				"select distinct p.url from Privilege p where p.url is not null")//
				.list();
	}

}
