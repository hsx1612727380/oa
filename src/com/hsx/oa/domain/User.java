package com.hsx.oa.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户User - 实体类
 * 
 * @author hsx
 * 
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String loginName;
	private String password;
	private String name;
	private String gender;
	private String phoneNumber;
	private String email;
	private String description;
	private Department department;
	private Set<Role> roles = new HashSet<Role>(); // 一个用户User可以属于多个岗位

	/**
	 * 判断本用户是否有指定的名称的权限
	 * @param name
	 * @return
	 */
	public boolean hasPrivilegeByName(String privilegeName) {
		//超级管理员所有权限
		if (isAdmin()) {
			return true;
		}
		// 判断普通用户是否含有这个权限
		for (Role role : roles) {
			for (Privilege privilege : role.getPrivileges()) {
				if (privilegeName.equals(privilege.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断本用户是否有指定Url的权限
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean hasPrivilegeByUrl(String privilegeUrl) {
		// 超级管理员有所有
		if (isAdmin()) {
			return true;
		}
		
		// >> 去掉后面的参数
    	int pos = privilegeUrl.indexOf("?");
    	if (pos > -1) {
			privilegeUrl = privilegeUrl.substring(0, pos);
		}
    	// >> 去掉后面的UI
    	if (privilegeUrl.endsWith("UI")) {
    		privilegeUrl = privilegeUrl.substring(0, privilegeUrl.length() - 2);
    	}
		
    	// 如果Url不需要控制，则登录用户就可以使用
    	Collection<String> allPrivilegeUrls = (Collection<String>) ActionContext.getContext().getApplication().get("allPrivilegeUrls"); //数据库oa_privilege中已经存在的所有的Url
    	if (!allPrivilegeUrls.contains(privilegeUrl)) {
			return true;
		}
    	
		// 判断普通用户是否含有这个权限
		for (Role role : roles) {
			for (Privilege privilege : role.getPrivileges()) {
				if (privilegeUrl.equals(privilege.getUrl())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断是否是超级管理员
	 * @return
	 */
	public boolean isAdmin() {
		return "admin".equals(loginName);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}