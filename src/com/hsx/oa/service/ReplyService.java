package com.hsx.oa.service;

import java.util.List;

import com.hsx.oa.base.BaseSupport;
import com.hsx.oa.domain.PageBean;
import com.hsx.oa.domain.Reply;
import com.hsx.oa.domain.Topic;

public interface ReplyService extends BaseSupport<Reply> {

	/**
	 * 查询指定主题中的所有的回复列表，排序：按照回复的时间进行升序排序 - 先回复的在上面
	 * @param topic
	 * @return
	 */
	public List<Reply> findByTopic(Topic topic);

	/**
	 * 带分页   --- 查询指定主题中的所有的回复列表，排序：按照回复的时间进行升序排序 - 先回复的在上面
	 * @param pageNum
	 * @param pageSize
	 * @param topic
	 * @return
	 */
	@Deprecated
	public PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic);

}
