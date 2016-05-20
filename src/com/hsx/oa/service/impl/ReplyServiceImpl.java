package com.hsx.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsx.oa.base.BaseSupportImpl;
import com.hsx.oa.domain.Forum;
import com.hsx.oa.domain.PageBean;
import com.hsx.oa.domain.Reply;
import com.hsx.oa.domain.Topic;
import com.hsx.oa.service.ReplyService;

@Transactional
@Service
@SuppressWarnings("unchecked")
public class ReplyServiceImpl extends BaseSupportImpl<Reply> implements
		ReplyService {

	@Override
	public List<Reply> findByTopic(Topic topic) {
		return getSession().createQuery(//
				"from Reply r where r.topic = ? order by r.postTime asc")//
				.setParameter(0, topic)//
				.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic) {
		// 一页所有的Reply
		List recordList = getSession().createQuery(//
				"from Reply r where r.topic = ? order by r.postTime asc")//
				.setParameter(0, topic)//
				.setFirstResult((pageNum - 1) * pageSize)// 每页从哪一条记录开始显示,从0开始
				.setMaxResults(pageSize)// 每页显示多少条记录
				.list();
		// 数据库中的总记录数
		Long recordCount = (Long) getSession().createQuery(//
				"select count(*) from Reply r where r.topic = ?")//
				.setParameter(0, topic)//
				.uniqueResult();
		return new PageBean(pageNum, pageSize, recordCount.intValue(), recordList);
	}

	@Override
	public void save(Reply reply) {
		// 1、设置默认属性并保存,这里没有默认值需要设置
		getSession().save(reply);
		// 2、维护属性并更新到数据库中
		Topic topic = reply.getTopic();
		topic.setReplyCount(topic.getReplyCount() + 1);
		topic.setLastReply(topic.getLastReply());
		topic.setLastUpdateTime(topic.getLastUpdateTime());
		getSession().update(topic);
		Forum forum = topic.getForum();
		forum.setArtricleCount(forum.getArtricleCount() + 1);
		getSession().update(forum);
	}

}
