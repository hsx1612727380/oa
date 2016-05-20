package com.hsx.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsx.oa.base.BaseAction;
import com.hsx.oa.domain.Forum;
import com.opensymphony.xwork2.ActionContext;

/**
 * 版块管理的Action
 * @author hsx
 *
 */
@Controller
@Scope("prototype")
public class ForumManageAction extends BaseAction<Forum> {

	/**
	 * 版块列表
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		List<Forum> forumList = forumManageService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}
	
	/**
	 * 删除版块
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		forumManageService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 转发到添加版块页面
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		return "saveUI";
	}
	
	/**
	 * 添加版块
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		forumManageService.save(model);
		return "toList";
	}
	
	/**
	 * 转发到修改版块页面
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {
		Forum forum = forumManageService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";
	}
	
	/**
	 * 修改版块
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		Forum forum = forumManageService.getById(model.getId());
		forum.setName(model.getName());
		forum.setDescription(model.getDescription());
		forumManageService.update(forum);
		return "toList";
	}
	
	/**
	 * 版块上移
	 * @return
	 * @throws Exception
	 */
	public String moveUp() throws Exception {
		forumManageService.moveUp(model.getId());
		return "toList";
	}
	
	/**
	 * 版块下移
	 * @return
	 * @throws Exception
	 */
	public String moveDown() throws Exception {
		forumManageService.moveDown(model.getId());
		return "toList";
	}

	
}
