package com.infotree.qliktest.admin.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionValidateInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		System.out.println("Interceptor preHandle method");
		HttpSession session = request.getSession(false);
		if(session == null){
			//response.sendRedirect("/WEB-INF/index.jsp");
			request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
			return false;
		}
		return true;
	}
	
}
