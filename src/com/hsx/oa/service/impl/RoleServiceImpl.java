package com.hsx.oa.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsx.oa.base.BaseSupportImpl;
import com.hsx.oa.domain.Role;
import com.hsx.oa.service.RoleService;

/**
 * 角色RoleService实现类 - 业务逻辑层
 * @author hsx
 *
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseSupportImpl<Role> implements RoleService {
	
}
