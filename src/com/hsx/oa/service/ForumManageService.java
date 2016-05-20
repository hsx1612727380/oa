package com.hsx.oa.service;

import com.hsx.oa.base.BaseSupport;
import com.hsx.oa.domain.Forum;

public interface ForumManageService extends BaseSupport<Forum> {

	/**
	 * 版块上移，最上面的不能在上移了
	 * @param id
	 */
	public void moveUp(Long id);
	
	/**
	 * 版块下移，最下面的不能在下移了
	 * @param id
	 */
	public void moveDown(Long id);

}
