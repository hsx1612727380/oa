package com.hsx.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsx.oa.base.BaseSupportImpl;
import com.hsx.oa.domain.Forum;
import com.hsx.oa.service.ForumManageService;

@Transactional
@Service
@SuppressWarnings("unchecked")
public class ForumManageServiceImpl extends BaseSupportImpl<Forum> implements
		ForumManageService {

	@Override
	public List<Forum> findAll() {
		return getSession().createQuery(//
				"from Forum f order by f.position")//
				.list();
	}
	
	@Override
	public void save(Forum forum) {
		// 保存
		super.save(forum);
		// 设置position的值
		System.err.println(forum.getId());
		forum.setPosition(forum.getId().intValue() + 1);
		System.err.println(forum.getPosition());
		// 更新到数据库中 -- 对象现在是持久化状态，同步到数据库中
		// getSession().update(forum);
	}

	@Override
	public void moveUp(Long id) {
		// 找出相关的Forum
		Forum forum = getById(id); // 当前要移动的Forum
		Forum other = (Forum) getSession().createQuery(// // 上面的那个Forum
				"from Forum f where f.position < ? order by f.position desc")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//
				.setMaxResults(1)//
				.uniqueResult();
		// 最上面的不能上移
		if (other == null) {
			return;
		}
		// 交换position的值
		int position = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(position);
		// 更新到数据库中 -- 对象现在是持久化状态，同步到数据库中
		// getSession().update(forum);
		// getSession().update(other);
	}

	@Override
	public void moveDown(Long id) {
		// 找出相关的Forum
		Forum forum = getById(id); // 当前要移动的Forum
		Forum other = (Forum) getSession().createQuery(// // 上面的那个Forum
				"from Forum f where f.position > ? order by f.position asc")//
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)//
				.setMaxResults(1)//
				.uniqueResult();
		// 最上面的不能上移
		if (other == null) {
			return;
		}
		// 交换position的值
		int position = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(position);
	}

}
