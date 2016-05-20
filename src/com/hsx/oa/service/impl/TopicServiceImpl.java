package com.hsx.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsx.oa.base.BaseSupportImpl;
import com.hsx.oa.domain.Forum;
import com.hsx.oa.domain.PageBean;
import com.hsx.oa.domain.Topic;
import com.hsx.oa.service.TopicService;

@Transactional
@Service
@SuppressWarnings("unchecked")
public class TopicServiceImpl extends BaseSupportImpl<Topic> implements TopicService {

	@Override
	public List<Topic> findByForum(Forum forum) {
		// 置顶在上面，普通与精华不要求排序
		return getSession().createQuery(//
				"from Topic t where t.forum = ? order by (case t.type when 2 then 2 else 0 end) desc, t.lastUpdateTime desc")//
				.setParameter(0, forum)//
				.list();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum) {
		// 每一页数据(主题)列表
		List recordList = getSession().createQuery(//
				"from Topic t where t.forum = ? order by (case t.type when 2 then 2 else 0 end) desc, t.lastUpdateTime desc")//
				.setParameter(0, forum)//
				.setFirstResult((pageNum - 1) * pageSize)//
				.setMaxResults(pageSize)//
				.list();
		// 总共多少条数据(主题)
		Long recordCount = (Long) getSession().createQuery(//
				"select count(*) from Topic t where t.forum = ?")//
				.setParameter(0, forum)//
				.uniqueResult();
		return new PageBean(pageNum, pageSize, recordCount.intValue(), recordList);
	}
	
	@Override
	public void save(Topic topic) {
		// 1、设置属性并保存
		topic.setType(Topic.TYPE_NORMAL); // 默认为普通帖
		topic.setReplyCount(0); // 发表主贴[主题],当没有回复时，默认为0
		topic.setLastReply(null); // / 发表主贴[主题],当没有回复时，默认为null
		topic.setLastUpdateTime(topic.getPostTime()); //z 最后更新的时间[主题/帖的发表时间或最后回复的时间]，发表主贴[主题],当没有回复时，默认为主贴发表的时间
		getSession().save(topic); // 保存
		// 2、维护相关的特殊属性 - 关联到的Forum的特殊属性
		Forum forum = topic.getForum();
		forum.setTopicCount(forum.getTopicCount() + 1); // 主题数量:原数量加1
		forum.setArtricleCount(forum.getArtricleCount() + 1); // 文章数量[主题数+回复数]：原数量加1
		forum.setLastTopic(topic); // 最后发表的主题：更新为最新的主题
		getSession().update(forum); // 更新到数据库中
	}

}
