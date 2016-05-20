package com.hsx.oa.service;

import java.util.List;

import com.hsx.oa.base.BaseSupport;
import com.hsx.oa.domain.Forum;
import com.hsx.oa.domain.PageBean;
import com.hsx.oa.domain.Topic;
import com.hsx.oa.util.QueryHelper;

public interface TopicService extends BaseSupport<Topic> {

	/**
	 * 查询指定版块下的所有主题，注意：排序问题[所有置顶帖在最上面，并按最后更新时间排序，让新状态在上面]
	 * @param forum
	 * @return
	 */
	public List<Topic> findByForum(Forum forum);

	/**
	 * 带有分页 --- 查询指定版块下的所有主题，注意：排序问题[所有置顶帖在最上面，并按最后更新时间排序，让新状态在上面]
	 * @param pageNum
	 * @param pageSize
	 * @param forum
	 * @return
	 */
	@Deprecated
	public PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum);

}
