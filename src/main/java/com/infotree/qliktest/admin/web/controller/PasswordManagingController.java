package com.infotree.qliktest.admin.web.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infotree.qliktest.admin.dao.referencedata.SessionsDao;
import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.PasswordPojo;
import com.infotree.qliktest.admin.service.mail.MailService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;

@Controller
@RequestMapping("/password")
public class PasswordManagingController {
	private static final Logger LOG = LoggerFactory.getLogger(PasswordManagingController.class);
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	@Autowired
	private SessionsDao sessionsDao;
	
	@Autowired
	private MailService mailService;
	
	/**
	 * This method returns the UI to forgotpassword page
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String forgotPassword(Model model){
		try {
			model.addAttribute("crudObj",new PasswordPojo());
			model.addAttribute("forgotpassword","forgotpassword");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "passwordhome";
	}
	/**
	 * This method sends the new password to the user by taking the email id from the user
	 */
	@RequestMapping(value = "/getpassword", method = RequestMethod.POST)
	public String sendPassword(@Valid PasswordPojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		try {
			model.addAttribute("crudObj",new PasswordPojo());
			User u = userProfileService.findByUserName(entity.getUserName());
			if(u != null){
				
			char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 10; i++) {
			    char c = chars[random.nextInt(chars.length)];
			    sb.append(c);
			}
			String output = sb.toString();
			u.setPassword(output);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("entity", u);
			map.put("appUrl", "predev.qliktest.com:8080/QlikTestAdmin");
			try {
				userProfileService.updatePasswordByUserName(entity.getUserName(), PasswordHashing.cryptWithMD5(output));
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(new java.util.Date());
				record.setActionType(AuditType.CREATE);
				record.setActionData("Random password:"+output);
				record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
				/*try {
					java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
					record.setIpOrigin(inetAddress.getHostAddress());
				} catch (UnknownHostException e) {
					LOG.error(e.toString());
					e.printStackTrace();
				}*/
				record.setUserId((Integer)request.getSession().getAttribute("userid"));
				record.setUserName(request.getSession().getAttribute("username").toString());
				auditLogService.saveRecord(record);
				mailService.sendMail("dev@infotreesolutions.com", u.getEmailAddress(), "The Password For QlikTest Admin Account", map);
			} catch (Exception e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
			model.addAttribute("passwordsent","Successfully sent the password to your registered email.");
			}else{
				model.addAttribute("passwordsent","No Details found for the given username");
			}
			model.addAttribute("forgotpassword","forgotpassword");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "passwordhome";
	}
	/**
	 * This method returns the UI to change the password
	 */
	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String changePassword(Model model){
		try {
			model.addAttribute("crudObj",new PasswordPojo());
			model.addAttribute("changepassword","changepassword");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to update the password with first login values
	 */
	@RequestMapping(value = "/changepasswordfirstlogin", method = RequestMethod.POST)
	public String changePasswordFirstLogin(@Valid PasswordPojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(new java.util.Date());
			record.setActionType(AuditType.MODIFY);
			record.setActionData("Changed password is :"+entity.getNewPassword());
			record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
			/*try {
				java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
				record.setIpOrigin(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}*/
			record.setUserId((Integer)request.getSession().getAttribute("userid"));
			record.setUserName(request.getSession().getAttribute("username").toString());
			auditLogService.saveRecord(record);
			
			userProfileService.updatePasswordByIdAndRequired((Integer)request.getSession().getAttribute("userid"), PasswordHashing.cryptWithMD5(entity.getNewPassword()));
			model.addAttribute("crudObj",new PasswordPojo());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
	/**
	 * This method is to update the password with the new values
	 */
	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	public String updatePassword(@Valid PasswordPojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			User u = userProfileService.getById((Integer)request.getSession().getAttribute("userid"));
			String oldPass = u.getPassword();
			String newPass = PasswordHashing.cryptWithMD5(entity.getCurrentPassword());
			
			if(oldPass.equals(newPass)){
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(new java.util.Date());
				record.setActionType(AuditType.MODIFY);
				record.setActionPerformed("Changed the password");
				record.setActionData("Changed password is :"+newPass);
				record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
				/*try {
					InetAddress inetAddress = InetAddress.getLocalHost();
					record.setIpOrigin(inetAddress.getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}*/
				record.setUserId((Integer)request.getSession().getAttribute("userid"));
				record.setUserName(request.getSession().getAttribute("username").toString());
				auditLogService.saveRecord(record);
				model.addAttribute("passwordchanged","Password Changed Successfully");
				userProfileService.updatePasswordById((Integer)request.getSession().getAttribute("userid"),PasswordHashing.cryptWithMD5(entity.getNewPassword()),new Date(System.currentTimeMillis()));
				Map<String, Object> map = new HashMap<String, Object>();
				u.setPassword(entity.getNewPassword());
				map.put("entity", u);
				map.put("appUrl", "predev.qliktest.com:8080/QlikTestAdmin");
				mailService.sendMail("dev@infotreesolutions.com", u.getEmailAddress(), "The New Password For QlikTest Admin Account", map);
			}else{
				model.addAttribute("passwordchanged","<font color='red'>Please Enter A Valid Current Password</font>");
			}
			model.addAttribute("crudObj",new PasswordPojo());
			model.addAttribute("changepassword","changepassword");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
}
