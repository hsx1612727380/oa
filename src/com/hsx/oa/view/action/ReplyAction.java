package com.hsx.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsx.oa.base.BaseAction;
import com.hsx.oa.domain.Reply;
import com.hsx.oa.domain.Topic;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ReplyAction extends BaseAction<Reply> {
	
	private Long topicId;

	/**
	 * 转发到回复主题页面
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		// 准备数据：topic
		Topic topic = topicService.getById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}
	
	/**
	 * 添加回复[发表新回复]
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// 封装
		// 1、表单字段，已经封装了
		//model.setTitle(title);
		//model.setContent(content);
		model.setTopic(topicService.getById(topicId)); // 隐藏字段传过来的
		// 2、当前信息
		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		// 保存
		replyService.save(model);
		return "toShow"; // 转到新回复所在的主题页面
	}
	
	// --------------------------------------------------
	
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
	public Long getTopicId() {
		return topicId;
	}
	
}
