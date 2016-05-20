package com.hsx.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hsx.oa.domain.Department;

/**
 * 部门工具类
 * @author hsx
 *
 */
public class DepartmentUtils {

	/**
	 * 遍历部门树，把所有部门遍历出来，放到集合(List)中返回，有层次感
	 * @param toplist
	 * @return
	 */
	public static List<Department> getAllDepartments(List<Department> topList) {
		List<Department> list = new ArrayList<Department>();
		walkDepartmentTreeList(topList, list, "┣");
		return list;
	}
	
	/**
	 * 遍历部门树，把遍历出的部门信息放到指定的集合中
	 * @param topList
	 */
	private static void walkDepartmentTreeList(Collection<Department> topList, List<Department> list, String prefix) {
		for (Department top : topList) {
			Department copy = new Department(); //使用副本，因为源对象在Session中，当有事务对其操作，那马数据库中的数据会发生变化
			copy.setId(top.getId());
			copy.setName(prefix + top.getName());
			list.add(copy); //把副本添加到集合中
			walkDepartmentTreeList(top.getChildren(), list, "　" + prefix);
		}
	}
	
}
