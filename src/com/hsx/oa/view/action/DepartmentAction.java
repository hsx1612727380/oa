package com.hsx.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hsx.oa.base.BaseAction;
import com.hsx.oa.domain.Department;
import com.hsx.oa.util.DepartmentUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 * 部门的Action
 * @author hsx
 *
 */
@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department> {

	private Long parentId;
	
	/**
	 * 显示部门列表
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		List<Department> departmentList = null;
		
		if (parentId == null) { // 顶级部门列表
			departmentList = departmentService.findTopList();
		}
		else { // 子部门列表
			departmentList = departmentService.findChildren(parentId);
			Department parent = departmentService.getById(parentId);
			ActionContext.getContext().put("parent", parent);
		}
		
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}
	
	/**
	 * 删除部门
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		departmentService.delete(model.getId());
		return "toList";
	}
	
	/**
	 * 转发到添加部门页面
	 * @return
	 * @throws Exception
	 */
	public String addUI() throws Exception {
		//准备数据上级部门，departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		return "saveUI";
	}
	
	/**
	 * 添加部门
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		// 封装信息到对象中
		Department parent = departmentService.getById(parentId);
		model.setParent(parent);
		departmentService.save(model);
		return "toList";
	}
	
	/**
	 * 转发到修改部门页面
	 * @return
	 * @throws Exception
	 */
	public String editUI() throws Exception {
		// 准备数据上级部门，departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		// 准备回显数据
		Department department = departmentService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(department);
		if (department.getParent() != null) {
			parentId = department.getParent().getId();
		}
		return "saveUI";
	}
	
	/**
	 * 修改部门
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		Department department = departmentService.getById(model.getId());
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		department.setParent(departmentService.getById(parentId));
		departmentService.update(department);
		return "toList";
	}
	
	// ----------------------------------------------
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Long getParentId() {
		return parentId;
	}
	
}
