package com.hsx.oa.util;

import com.hsx.oa.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CkeckPrivilegeInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		/*// 之前
		System.out.println("--> 之前");
		// 放行
		String result = invocation.invoke();
		// 之后
		System.out.println("--> 之后");
		return result;*/
		
		// 获取信息
		User user = (User) ActionContext.getContext().getSession().get("user");
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String privilegeUrl = namespace + actionName;
		
		if (user == null) { // 如果未登录
			if (privilegeUrl.startsWith("/user_login")) { // 如果去登录，就放行
				return invocation.invoke();
			}
			else { // 如果不是去登录，则转到登录
				return "loginUI";
			}
		}
		else { // 如果已经登录，就判断权限
				if (user.hasPrivilegeByUrl(privilegeUrl)) { // 如果有权限，就放行
				return invocation.invoke();
			}
			else { // 如果没有权限，就转到提示页面
				return "noPrivilegeError";
			}
		}
		
	}

}
