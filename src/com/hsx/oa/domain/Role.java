package com.hsx.oa.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色（岗位）Role - 实体类
 * 
 * @author hsx
 * 
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private Set<User> users = new HashSet<User>(); // 一个岗位Role上有很多用户User
	private Set<Privilege> privileges = new HashSet<Privilege>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
}
