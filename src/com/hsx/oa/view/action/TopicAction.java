package com.hsx.oa.view.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.type.TrueFalseType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsx.oa.base.BaseAction;
import com.hsx.oa.domain.Forum;
import com.hsx.oa.domain.PageBean;
import com.hsx.oa.domain.Reply;
import com.hsx.oa.domain.Topic;
import com.hsx.oa.domain.User;
import com.hsx.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic>{

	private Long forumId;
	
	/**
	 * 显示单个主题
	 * @return
	 * @throws Exception
	 */
	public String show() throws Exception {
		// 准备数据:topic
		Topic topic = topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		// 准备数据：replyList
		//List<Reply> replyList = replyService.findByTopic(topic);
		//ActionContext.getContext().put("replyList", replyList);
		
		// 准备分页信息(单独模块)
		//PageBean pageBean = replyService.getPageBeanByTopic(pageNum, pageSize, topic);
		
		// 准备分页信息(调用公共的方法)
		//String hql = "from Reply r where r.topic = ? order by r.postTime asc";
		//List<Object> parameters = new ArrayList<Object>();
		//parameters.add(topic);
		//PageBean pageBean = replyService.getPageBean(pageNum, pageSize, hql, parameters);
		
		// 准备分页数据(调用公共方法 + viewType/orderBy/asc + 调用QueryHelper的方法) 最终版
		new QueryHelper(Reply.class, "r")//
				//过滤条件(where语句)
				.addWhereCondition( "r.topic = ?", topic)//
				.addOrderByProperty("r.postTime", true)//
				.preparePageBean(replyService, pageNum, pageSize);
		
		return "show";
	}
	
	/**
	 * 转发到添加主题页面
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		// 准备版块信息[版块名称]：forum
		Forum forum = forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);
		return "addUI";
	}
	
	/**
	 * 添加主题[发表新主题]
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// 封装
		// 1、表单参数，已经封装了title、content
		//model.setTilte(tilte);
		//model.setContent(content);
		model.setForum(forumService.getById(forumId)); //forumId从页面中传回来
		// 2、当前直接获取的信息
		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		// 3、 根据需求需要修改的数据 - 放到Service层中重写save()方法
		// 保存
		topicService.save(model);
		return "toShow"; // 转到这个主题的页面
	}
	
	//---------------------
	
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
	
	public Long getForumId() {
		return forumId;
	}

}
