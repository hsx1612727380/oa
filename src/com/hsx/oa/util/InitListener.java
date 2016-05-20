package com.hsx.oa.util;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hsx.oa.domain.Privilege;
import com.hsx.oa.service.PrivilegeService;

/**
 * 用户访问时就显示所有的权限，即左边的导航栏
 * @author hsx
 *
 */
//@Component
public class InitListener implements ServletContextListener {
	
	/**
	 * 发布到Tomcat中后，Tomcat使用的是这个监听器本身的通过反射生成的对象，而不是在Spring容器中注入的对象，所以不能通过Spring注入的方式
	 * 使用@Component注入后，会生成一个InitListener类的对象，这时是有两个对象的[1.Tomcat反射生成的，2.Spring注入生成]，
	 * 但是使用的是Tomcat反射生成的对象，那么通过@Resource注入的资源[PrivilegeService]就不会被Spring容器管理，所以应该使用
	 * ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
	 * PrivilegeService privilegeService = (PrivilegeService) context.getBean("privilegeServiceImpl");
	 * 获取PrivilegeService
	 * 如果使用ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");，则会产生两个对象
	 * [1.一个是自己new  Class...  2.两一个是在web.xml中配置了Spring的监听器ContextLoaderListener]
	 */
	
	//@Resource
	//private PrivilegeService privilegeService;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		PrivilegeService privilegeService = (PrivilegeService) context.getBean("privilegeServiceImpl");
		
		// 准备顶级权限
		List<Privilege> topPrivilegeList = privilegeService.findTopList();
		sce.getServletContext().setAttribute("topPrivilegeList", topPrivilegeList);
		System.out.println("------------------->准备顶级权限");
		
		// 从数据库oa_privilege中查询所有的权限
		Collection<String> allPrivilegeUrls = privilegeService.getAllPrivilegeUrls();
		sce.getServletContext().setAttribute("allPrivilegeUrls", allPrivilegeUrls);
		System.out.println("-------------------> 从数据库oa_privilege中查询所有的权限");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
	private void getTopPrivilegeList() {
		
	}

}
