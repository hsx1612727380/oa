package com.hsx.oa.view.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsx.oa.base.BaseAction;
import com.hsx.oa.domain.Forum;
import com.hsx.oa.domain.PageBean;
import com.hsx.oa.domain.Topic;
import com.hsx.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum> {
	
	/**
	 * 0 - 查看全部主题
	 * 1 - 只看精华帖
	 */
	private int viewType = 0;
	
	/**
	 * 0 - 默认排序[所有的置顶帖在最上面（精华帖与普通帖之间没有顺序），并按照最后更新的时间排序]
	 * 1 - 只按最后更新时间排序
	 * 2 - 只按主题发表时间排序
	 * 3 - 只按回复数量排序
	 */
	private int orderBy = 0;
	
	/**
	 * true - 升序
	 * false - 降序
	 */
	private boolean asc = false;

	/**
	 * 版块列表
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	/**
	 * 显示单个版块[主图列表]
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		// 准备数据 ：forum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		// 准备数据： topicList
		//List<Topic> topicList = topicService.findByForum(forum);
		//ActionContext.getContext().put("topicList", topicList);
		
		// 准备分页数据(单独模块) v1
		//PageBean pageBean = topicService.getPageBeanByForum(pageNum, pageSize, forum);
		
		// 准备分页数据(调用公共方法) v2
		//String hql = "from Topic t where t.forum = ? order by (case t.type when 2 then 2 else 0 end) desc, t.lastUpdateTime desc";
		//List<Object> parameters = new ArrayList<Object>();
		//parameters.add(forum);
		//PageBean pageBean = topicService.getPageBean(pageNum, pageSize, hql, parameters);
		
		// 准备分页数据(调用公共方法+viewType/orderBy/asc) v3
		//String hql = "from Topic t where t.forum = ? order by (case t.type when 2 then 2 else 0 end) desc, t.lastUpdateTime desc";
		/*List<Object> parameters = new ArrayList<Object>();
		String hql = "from Topic t where t.forum = ? ";
		parameters.add(forum);
		if (viewType == 1) { // 1 - 只看精华帖
			hql += " and t.type = ? ";
			parameters.add(viewType);
		}
		if (orderBy == 1) { // 1 - 只按最后更新时间排序
			hql += " order by t.lastUpdateTime " + (asc ? "asc" : "desc");
		}
		else if (orderBy == 2) { // 2 - 只按主题发表时间排序
			hql += " order by t.postTime " + (asc ? "asc" : "desc");
		}
		else if (orderBy == 3) { // 3 - 只按回复数量排序
			hql += " order by t.replyCount " + (asc ? "asc" : "desc");
		}
		else { // 0 - 默认排序[所有的置顶帖在最上面（精华帖与普通帖之间没有顺序），并按照最后更新的时间排序]
			hql += " order by (case t.type when 2 then 2 else 0 end) desc, t.lastUpdateTime desc";
		}
		PageBean pageBean2 = topicService.getPageBean(pageNum, pageSize, hql, parameters);*/
		
		// 准备分页数据(调用公共方法 + viewType/orderBy/asc + 调用QueryHelper的方法) v4
		new QueryHelper(Topic.class, "t")//
				//过滤条件(where语句)
				.addWhereCondition( "t.forum = ?", forum)//
				.addWhereCondition((viewType == 1), "t.type = ?", Topic.TYPE_BEST)// 1 - 只看精华帖
				.addOrderByProperty((orderBy == 1), "t.lastUpdateTime", asc)// 1 - 只按最后更新时间排序
				.addOrderByProperty((orderBy == 2), "t.postTime", asc) // 2 - 只按主题发表时间排序
				.addOrderByProperty((orderBy == 3), "t.replyCount", asc) // 3 - 只按回复数量排序
				.addOrderByProperty((orderBy == 0), "case t.type when 2 then 2 else 0 end", false)//
				.addOrderByProperty((orderBy == 0), "t.lastUpdateTime", false) //0 - 默认排序[所有的置顶帖在最上面（精华帖与普通帖之间没有顺序），并按照最后更新的时间排序]
				.preparePageBean(topicService, pageNum, pageSize);
		
		return "show";
	}

	// --------------------------------------------
	
	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

}
