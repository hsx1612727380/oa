package com.hsx.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import com.hsx.oa.domain.User;
import com.hsx.oa.service.DepartmentService;
import com.hsx.oa.service.ForumManageService;
import com.hsx.oa.service.ForumService;
import com.hsx.oa.service.PrivilegeService;
import com.hsx.oa.service.ReplyService;
import com.hsx.oa.service.RoleService;
import com.hsx.oa.service.TopicService;
import com.hsx.oa.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	protected T model;
	
	public BaseAction() {
		try {
			// 通过反射获取model的真实类型
			ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
			// 通过反射创建model的实例
			model = clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public T getModel() {
		return model;
	}
	
	// ------------------------------
	
	@Resource
	protected RoleService roleService;
	
	@Resource
	protected DepartmentService departmentService;
	
	@Resource
	protected UserService userService;

	@Resource
	protected PrivilegeService privilegeService;
	
	@Resource
	protected ForumManageService forumManageService;
	
	@Resource
	protected ForumService forumService;
	
	@Resource 
	protected TopicService topicService;
	
	@Resource
	protected ReplyService replyService;
	
	/**
	 * 获取当前用户
	 * @return
	 */
	protected User getCurrentUser() {
		return (User) ActionContext.getContext().getSession().get("user");
	}
	
	// 分页信息------------------------------
	protected int pageNum = 1; // 当前页
	protected int pageSize = 10; // 每页显示多少条记录
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
