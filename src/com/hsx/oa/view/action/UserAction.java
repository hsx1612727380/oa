package com.hsx.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsx.oa.base.BaseAction;
import com.hsx.oa.domain.Department;
import com.hsx.oa.domain.PageBean;
import com.hsx.oa.domain.Role;
import com.hsx.oa.domain.User;
import com.hsx.oa.util.DepartmentUtils;
import com.hsx.oa.util.MD5Encoder;
import com.hsx.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private Long departmentId;
	private Long[] roleIds;

	/**
	 * 用户列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		//List<User> userList = userService.findAll();
		//ActionContext.getContext().put("userList", userList);
		
		//准备分页数据
		new QueryHelper(User.class, "u").preparePageBean(userService, pageNum, pageSize);
		
		return "list";
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		userService.delete(model.getId());
		return "toList";
	}

	/**
	 * 跳转到添加用户页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		// 准备数据 departmentList 来自哪个部门
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils
				.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		// 准本数据 roleList 属于什么岗位
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "saveUI";
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// 封装到对象中(当model是实体类型，也可以使用model，但是要设置未封装的属性)
		model.setDepartment(departmentService.getById(departmentId));
		List<Role> roleList = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roleList));
		model.setPassword(MD5Encoder.encode("1234"));
		// 保存到数据库
		userService.save(model);
		return "toList";
	}

	/**
	 * 跳转到用户修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {
		// 准备数据 departmentList 来自哪个部门
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils
				.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		// 准本数据 roleList 属于什么岗位
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		// 准备回显数据
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		if (user.getDepartment() != null) {
			departmentId = user.getDepartment().getId();
		}
		if (user.getRoles() != null) {
			roleIds = new Long[user.getRoles().size()];
			int index = 0;
			for (Role role : user.getRoles()) {
				roleIds[index++] = role.getId();
			}
		}
		return "saveUI";
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		// 从数据库中取出源对象
		User user = userService.getById(model.getId());
		// 设置要修改的属性
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		user.setDepartment(departmentService.getById(departmentId));
		List<Role> roleList = roleService.getByIds(roleIds);
		user.setRoles(new HashSet<Role>(roleList));
		// 更新到数据库
		userService.update(user);
		return "toList";
	}

	/**
	 * 初始化用户密码1234
	 * 
	 * @return
	 * @throws Exception
	 */
	public String initPassword() throws Exception {
		// 从数据库中取出源对象
		User user = userService.getById(model.getId());
		// 设置密码属性为1234
		user.setPassword(MD5Encoder.encode("1234"));
		// 保存到数据库中
		userService.update(user);
		return "toList";
	}

	/**
	 * 跳转到用户登录页面
	 * 
	 * @return
	 * @throws Exception
	 */
	public String loginUI() throws Exception {
		return "loginUI";
	}

	/**
	 * 登录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		User user = userService.findByLoginNameAndPassword(
				model.getLoginName(), model.getPassword());
		if (user == null) {
			addFieldError("login", "用户名或者密码不正确!");
			return "loginUI";
		} else {
			ActionContext.getContext().getSession().put("user", user);
			return "toIndex";
		}
	}

	/**
	 * 注销
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		ActionContext.getContext().getSession().remove("user");
		return "logout";
	}

	// ----------------------------------------

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

}
