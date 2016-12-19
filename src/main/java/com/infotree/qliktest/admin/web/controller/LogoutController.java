/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infotree.qliktest.admin.dao.referencedata.SessionsDao;

@Controller
@RequestMapping("/user")
public class LogoutController {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private SessionsDao sessionsDao;
	/**
	 * This method is to remove the user from the session when the user logout
	 */
	@RequestMapping(value="logout",method = RequestMethod.GET)
	public String logOut(Model model,HttpServletRequest request){
		try {
			Integer userId = (Integer)request.getSession().getAttribute("userid");
			sessionsDao.deleteUser(userId);
			HttpSession session = request.getSession(false);
			if(session != null){
				session.invalidate();
				System.out.println("session invalidated successfully");
			}
			model.addAttribute("logout","logout");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "logouthome";
	}

}
