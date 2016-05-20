package com.hsx.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsx.oa.base.BaseAction;
import com.hsx.oa.domain.Privilege;
import com.hsx.oa.domain.Role;
import com.opensymphony.xwork2.ActionContext;

/**
 * 角色的Action -- ModelDriven把model放到对象栈的栈顶
 * @author hsx
 *
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	
	private Long[] privilegeIds;
	
	/**
	 * 显示岗位列表
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList); // 放入到Map中
		return "list";
	}
	
	/**
	 * 删除岗位
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		roleService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 转发到添加岗位页面
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		return "saveUI";
	}
	
	/**
	 * 添加岗位
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		/*// 封装到对象中
		Role role = new Role();
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		// 保存到数据库中
		roleService.save(role);*/
		
		roleService.save(model); //model中封装了属性
		return "toList";
	}
	
	/**
	 * 转发到修改岗位页面
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {
		// 准备回显的数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role); // 放到对象栈中
		return "saveUI";
	}
	
	/**
	 * 修改岗位
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		// 从数据库中获取原对象
		Role role = roleService.getById(model.getId());
		// 设置要修改的属性
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		// 更新到数据库
		roleService.update(role);
		return "toList";
	}
	
	/**
	 * 转发到设置权限页面
	 * @return
	 * @throws Exception
	 */
	public String setPrivilegeUI() throws Exception {
		// 准备回显的数据
		Role role = roleService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(role); // 放到对象栈中
		if (role.getPrivileges() != null) { // 某个岗位关联了好几个权限，要把这几个权限传递到设置权限页面
			privilegeIds = new Long[role.getPrivileges().size()];
			int index = 0;
			for (Privilege privilege : role.getPrivileges()) {
				privilegeIds[index++] = privilege.getId();
			}
		}
		List<Privilege> privilegeList = privilegeService.findAll();
		ActionContext.getContext().put("privilegeList", privilegeList);
		return "setPrivilegeUI";
	}
	
	/**
	 * 设置权限
	 * @return
	 * @throws Exception
	 */
	public String setPrivilege() throws Exception {
		// 从数据库中获取原对象
		Role role = roleService.getById(model.getId());
		// 设置权限的属性
		List<Privilege> privilegeList = privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		// 更新到数据库
		roleService.update(role);
		return "toList";
	}
	
	// ----------------------------------------------
	
	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	
	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}
	
}
