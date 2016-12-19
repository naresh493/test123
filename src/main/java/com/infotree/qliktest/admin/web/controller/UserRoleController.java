/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.infotree.qliktest.admin.dao.referencedata.NotificationsDao;
import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Notifications;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.entity.referencedata.TeamRole;
import com.infotree.qliktest.admin.entity.referencedata.TeamRoleComp;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;
import com.infotree.qliktest.admin.entity.referencedata.UserRoleComp;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.TeamRolePojo;
import com.infotree.qliktest.admin.helperpojo.UserRolePojo;
import com.infotree.qliktest.admin.mails.UserRoleMail;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.mail.MailService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.TeamRoleService;
import com.infotree.qliktest.admin.service.referencedata.TeamService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserRoleService;
import com.infotree.qliktest.admin.service.referencedata.UserTeamService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;
@RequestMapping("/userrole")
@Controller
public class UserRoleController extends AbstractQTAdminController<UserRole> {
	private static final Logger LOG = LoggerFactory.getLogger(UserRoleController.class);
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private NotificationsDao notificationsDao;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserTeamService userTeamService;
	
	@Autowired
	private TeamRoleService teamRoleService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private AuditLogRecordService auditRecordService;
	
	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * This method will return the UI to add the role to the user.
	 */
	@RequestMapping(value="/addroletouser",method=RequestMethod.GET)
	public String addRoleToUser(Model model,HttpServletRequest request){
		try {
			/*List<Role> newRoleList = new ArrayList<Role>();
			List<Role> rolelist = roleService.list();
			for (Role role : rolelist) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					if(!role.getName().equalsIgnoreCase("Application Administrator")){
						newRoleList.add(role);
					}
				}
			} 
			model.addAttribute("role_list",newRoleList);*/
			model.addAttribute("crudObj",new UserRolePojo());
			model.addAttribute("user_list", userService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
			model.addAttribute("addroletouser","addroletouser");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method is to save the users to the role
	 */
	@RequestMapping(value="/saveuserrole",method=RequestMethod.POST)
	public String saveUserRole(@Valid UserRolePojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		String displayMessage = null;
		try {
			
			List<Integer> beforeAssignedRoles = (List<Integer>) request.getSession().getAttribute("roleIds");
			List<Integer> newAssignedRoles = entity.getRoleId();
			int assignedRoleSize = beforeAssignedRoles.size();
			int newlyAssignedRoleSize = 0;
			if(newAssignedRoles != null)
				newlyAssignedRoleSize = newAssignedRoles.size();

			if (assignedRoleSize == 0 && newlyAssignedRoleSize == 0) {
				displayMessage = "Please assign atleast one Role";
				model.addAttribute("displayMessage", displayMessage);
				model.addAttribute("user_list", userService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
				model.addAttribute("addroletouser","addroletouser");
				model.addAttribute("crudObj", new UserRolePojo());
				return "home";
			} else if (assignedRoleSize > 0 && newlyAssignedRoleSize == 0) {
				if (assignedRoleSize == 1)
					displayMessage = "1 role is removed";
				else
					displayMessage = assignedRoleSize+ " Roles are removed ";
			} else if(assignedRoleSize == 0 && newlyAssignedRoleSize > 0){
				if(newlyAssignedRoleSize == 1)
					displayMessage=" 1 Role is assigned";
				else
					displayMessage = newlyAssignedRoleSize+" Roles are assigned ";
			}
			
			else{
				int sameRoleAssigned = 0;
				for(Integer roleId : newAssignedRoles){
					
					for(Integer pid : beforeAssignedRoles){
						if(roleId.intValue() == pid.intValue())
							sameRoleAssigned++;
					}
				}
			
				int removedProject = assignedRoleSize - sameRoleAssigned;
				if(removedProject == 0)
					displayMessage = "";
				else if(removedProject == 1)
					displayMessage = " and 1 Role is removed";
				else
					displayMessage = " and "+removedProject+" Roles are removed";
					
					int assignedProject = 	newlyAssignedRoleSize - sameRoleAssigned;
					
					if(assignedProject == 0 && assignedRoleSize == newlyAssignedRoleSize)
					{
						displayMessage = "No changes are made";
						model.addAttribute("displayMessage", displayMessage);
						model.addAttribute("user_list", userService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
						model.addAttribute("addroletouser","addroletouser");
						model.addAttribute("crudObj", new UserRolePojo());
						return "home";
					}
					else if(assignedProject == 0){
						
							if(removedProject == 0)
								displayMessage = "";
							else if(removedProject == 1)
								displayMessage = " 1 Role is removed";
							else
								displayMessage = removedProject+" Roles are removed";
					}
					else if(assignedProject == 1)
						displayMessage = "1 Role is assigned"+displayMessage;
					else
						displayMessage = assignedProject+" Roles are assigned"+displayMessage;
				
			}
			model.addAttribute("displayMessage", displayMessage);
			
			
			/*DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();*/
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			String clientZone = clientTimeZone.getID();
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID(clientZone);
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
			StringBuilder sb = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i < 10; i++) {
			    char c = chars[random.nextInt(chars.length)];
			    sb.append(c);
			}
			String output = sb.toString();
			model.addAttribute("addroletouser","addroletouser");
			model.addAttribute("crudObj", new UserRolePojo());
			Integer userId = entity.getUserId();
			List<UserRole> existingRoles = userRoleService.getByUserId(userId);
			List<Integer> roleIds = entity.getRoleId();
			User user = userProfileService.getById(userId);
			User tenant = userProfileService.getById((Integer)request.getSession().getAttribute("userid"));
			userRoleService.deleteByUserId(userId);
			String roleNames = "";
			if(roleIds != null){
				Iterator<Integer> iterator = roleIds.iterator();
				while(iterator.hasNext()){
					Integer roleId = iterator.next();
					UserRole userRole = new UserRole();
					UserRoleComp userRoleComp = new UserRoleComp();
					userRoleComp.setUserId(entity.getUserId());
					userRoleComp.setRoleId(roleId);
					userRole.setUserRoleComp(userRoleComp);
					userRole.setCreatedBy(entity.getCreatedBy());
					userRole.setCreatedDate(dateInIndia);
					userRoleService.save(userRole);
					Role role = roleService.getById(roleId);
					roleNames = roleNames+","+role.getName();
					/*User user = userProfileService.getById(userId);*/
					/**
					 * Preparing the notification for assigning the role
					 */
					Notifications notifications = new Notifications();
					notifications.setId(notificationsDao.getMaxId()+1);
					notifications.setAssignedBy((Integer)request.getSession().getAttribute("userid")+"");
					notifications.setAssignedTo(user.getId()+"");
					notifications.setAssignedData("You have been Assigned to "+role.getName()+" Role");
					notifications.setStatus("UnRead");
					notifications.setCreatedBy(entity.getCreatedBy());
					notifications.setModifiedBy(entity.getModifiedBy());
					notifications.setCreatedDate(dateInIndia);
					notifications.setModifiedDate(entity.getModifiedDate());
					
					/*User tenant = userProfileService.getById((Integer)request.getSession().getAttribute("userid"));*/
					/**
					 * set the details to send the mail
					 */
					/**
					 * If the user doesnt have any roles then we need to send the mail with the password
					 */
					/*if(existingRoles == null){
					userProfileService.updatePasswordByIdAndRequired(user.getId(), PasswordHashing.cryptWithMD5(output));
					UserRoleMail userRoleMail = new UserRoleMail();
					userRoleMail.setUserName(user.getFirstName());
					userRoleMail.setRoleName(role.getName());
					userRoleMail.setPassword(output);
					userRoleMail.setTenantName(tenant.getFirstName());
					Map<String, Object> map = new HashMap<String, Object>();
					
						map.put("entity", userRoleMail);
						mailService.sendMailUserRole("dev@infotreesolutions.com", user.getEmailAddress(), "New Role Assigned For You", map);
						model.addAttribute("rolesassigned","Role Assigned To User");
					}*/
				}
				//	System.out.println("Roles Assigned : "+roleNames+" >>> "+roleNames.substring(1));
				if(existingRoles == null){
					userProfileService.updatePasswordByIdAndRequired(user.getId(), PasswordHashing.cryptWithMD5(output));
					UserRoleMail userRoleMail = new UserRoleMail();
					userRoleMail.setUserName(user.getUserName());
					userRoleMail.setRoleName(roleNames.substring(1));
					userRoleMail.setPassword(output);
					userRoleMail.setTenantName(tenant.getFirstName());
					userRoleMail.setEmailAddress(user.getEmailAddress());
					Map<String, Object> map = new HashMap<String, Object>();
					
						map.put("entity", userRoleMail);
						mailService.sendMailUserRole("dev@infotreesolutions.com", user.getEmailAddress(), "New Role Assigned For You", map,"");
						//System.out.println("Mail sent to : "+user.getFirstName());
						model.addAttribute("rolesassigned","Role Assigned To User");
					}
				else if(existingRoles != null && roleNames != ""){
					UserRoleMail userRoleMail = new UserRoleMail();
					userRoleMail.setUserName(user.getUserName());
					userRoleMail.setRoleName(roleNames.substring(1));
					userRoleMail.setTenantName(tenant.getFirstName());
					userRoleMail.setEmailAddress(user.getEmailAddress());
					Map<String, Object> map = new HashMap<String, Object>();
					
						map.put("entity", userRoleMail);
						mailService.sendMailUserRole("dev@infotreesolutions.com", user.getEmailAddress(), "New Role Assigned For You", map,"again");
						//System.out.println("Mail sent to again : "+user.getFirstName());
				}
				
			}else{
				model.addAttribute("rolesassigned","Role Assigned To User");
			}
			
			/**
			 * Preparing the audit log record for tracking the application administrator
			 */
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			User u = userProfileService.getById(entity.getUserId());
			
			record.setActionData("role assigned to user "+u.getUserName());
			record.setActionType(AuditType.ASSIGN);
			record.setActionPerformed("role assigned to user "+u.getUserName());
			record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
			/*try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				record.setIpOrigin(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}*/
			record.setUserId((Integer)request.getSession().getAttribute("userid"));
			record.setUserName(request.getSession().getAttribute("username").toString());
			auditRecordService.saveRecord(record);
			model.addAttribute("user_list", userService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
			model.addAttribute("addroletouser","addroletouser");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method is used to add the role to the entire team. It will return the user interface to add the role to the team
	 */
	@RequestMapping(value="/addroletoteam",method = RequestMethod.GET)
	public String addRoleToTeam(Model model,HttpServletRequest request){
		try {
			model.addAttribute("addroletoteam", "addroletoteam");
			model.addAttribute("crudObj",new TeamRolePojo());
			model.addAttribute("team_list",teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+""));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * saving the role to the entire team
	 */
	@RequestMapping(value="/saveteamrole",method = RequestMethod.POST)
	public String saveTeamRole(@Valid TeamRolePojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		String displayMessage = null;

		try {
			List<Integer> beforeAssignedRoles = (List<Integer>) request.getSession().getAttribute("roleIds");
			List<Integer> newAssignedRoles = entity.getRoleId();
			//System.out.println("beforeAssignedList >>> "+beforeAssignedRoles);
			//System.out.println("assignedList >>> "+newAssignedRoles);
			int assignedRoleSize = beforeAssignedRoles.size();
			int newlyAssignedRoleSize = 0;
			if(newAssignedRoles != null)
				newlyAssignedRoleSize = newAssignedRoles.size();

			if (assignedRoleSize == 0 && newlyAssignedRoleSize == 0) {
				displayMessage = "Please assign atleast one role to the Team";
				model.addAttribute("displayMessage", displayMessage);
				model.addAttribute("addroletoteam", "addroletoteam");
				model.addAttribute("crudObj",new TeamRolePojo());
				model.addAttribute("team_list",teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+""));
				// UserTenant ut =
				// userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
				// model.addAttribute("project_list",
				// projectService.getProjectsByUserId(ut.getUserTenantComp().getTenantId()));
				//model.addAttribute("crudObj", new TeamProjectPojo());
				return "home";
			} else if (assignedRoleSize > 0 && newlyAssignedRoleSize == 0) {
				if (assignedRoleSize == 1)
					displayMessage = "1 role is removed";
				else
					displayMessage = assignedRoleSize+ " roles are removed ";
			} else if(assignedRoleSize == 0 && newlyAssignedRoleSize > 0){
				if(newlyAssignedRoleSize == 1)
					displayMessage=" 1 role is assigned";
				else
					displayMessage = newlyAssignedRoleSize+" roles are assigned ";
			}
			
			else{
				int sameRoleAssigned = 0;
				for(Integer roleId : newAssignedRoles){
					
					for(Integer pid : beforeAssignedRoles){
						if(roleId.intValue() == pid.intValue())
							sameRoleAssigned++;
					}
				}
			
				int removedProject = assignedRoleSize - sameRoleAssigned;
				if(removedProject == 0)
					displayMessage = "";
				else if(removedProject == 1)
					displayMessage = " and 1 role is removed";
				else
					displayMessage = " and "+removedProject+" roles are removed";
					
					int assignedProject = 	newlyAssignedRoleSize - sameRoleAssigned;
					
					if(assignedProject == 0 && assignedRoleSize == newlyAssignedRoleSize)
						displayMessage = "No changes are made";
					else if(assignedProject == 0){
						
							if(removedProject == 0)
								displayMessage = "";
							else if(removedProject == 1)
								displayMessage = " 1 role is removed";
							else
								displayMessage = removedProject+" roles are removed";
					}
					else if(assignedProject == 1)
						displayMessage = "1 role is assigned"+displayMessage;
					else
						displayMessage = assignedProject+" roles are assigned"+displayMessage;
				
			}
			//System.out.println(displayMessage);
			model.addAttribute("displayMessage", displayMessage);
			
			/*DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();*/
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			String clientZone = clientTimeZone.getID();
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID(clientZone);
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			model.addAttribute("addroletoteam","addroletoteam");
			model.addAttribute("crudObj", new TeamRolePojo());
			Integer teamId = entity.getTeamId();
			
			teamRoleService.deleteByTeamId(teamId);
			List<Integer> roleList = entity.getRoleId();
			if(roleList != null){
				Iterator<Integer> iterator = roleList.iterator();
				while(iterator.hasNext()){
					Integer roleId = iterator.next();
					TeamRole teamRole = new TeamRole();
					TeamRoleComp teamRoleComp = new TeamRoleComp();
					teamRoleComp.setRoleId(roleId);
					teamRoleComp.setTeamId(teamId);
					teamRole.setTeamRoleComp(teamRoleComp);
					teamRole.setCreatedBy(entity.getCreatedBy());
					teamRole.setCreatedDate(dateInIndia);
					teamRole.setModifiedBy(entity.getModifiedBy());
					teamRole.setModifiedDate(dateInIndia);
					teamRoleService.save(teamRole);
					List<User> userList = userTeamService.getUsersByTeamId(teamId);
					if(userList != null){
						Iterator<User> itr = userList.iterator();
						while(itr.hasNext()){
							User user = itr.next();
							UserRole ur = userRoleService.getByUserAndRoleId(user.getId(), roleId);
							if(ur == null){
								UserRole userRole = new UserRole();
								UserRoleComp userRoleComp = new UserRoleComp();
								userRoleService.deleteByUserAndRole(user.getId(), roleId);
								userRoleComp.setUserId(user.getId());
								userRoleComp.setRoleId(roleId);
								userRole.setUserRoleComp(userRoleComp);
								userRole.setCreatedBy(entity.getCreatedBy());
								userRole.setCreatedDate(dateInIndia);
								userRole.setModifiedBy(entity.getModifiedBy());
								userRole.setModifiedDate(dateInIndia);
								userRoleService.save(userRole);
							}
						}
					}
				}
			}
			
			/**
			 * Preparing the audit log
			 */
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			Team team = teamService.getById(entity.getTeamId());
			
			record.setActionData("role assigned to Team "+team.getName());
			record.setActionType(AuditType.ASSIGN);
			record.setActionPerformed("role assigned to Team "+team.getName());
			record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
			/*try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				record.setIpOrigin(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}*/
			record.setUserId((Integer)request.getSession().getAttribute("userid"));
			record.setUserName(request.getSession().getAttribute("username").toString());
			auditRecordService.saveRecord(record);
			
			model.addAttribute("rolesassigned","Role Assigned To Team");
			model.addAttribute("crudObj",new TeamRolePojo());
			auditRecordService.saveRecord(record);
			model.addAttribute("team_list",teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+""));
			model.addAttribute("addroletoteam","addroletoteam");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This is a ajax based request to get the available roles by taking the userid as the parameter
	 */
	
	@RequestMapping(value="/getavailableroles",method=RequestMethod.GET)
	public void getAvailableRoles(Model model,@RequestParam("userId") String userId,HttpServletRequest request,HttpServletResponse response)
	{
		if(userId == null || userId == ""){
			//System.out.println("getAvailableRoles");
			return;
		}
		try {
			Integer user = Integer.parseInt(userId);
			String traRoles = "";
			List<Role> roleList = roleService.getRolesNotAssignedToUser(user);
			for(Role list : roleList){
				if(!list.getName().equalsIgnoreCase("System Administrator")){
					if(!list.getName().equalsIgnoreCase("Application Administrator")){
						traRoles += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
					}
				}
			}
				try{
					PrintWriter out = response.getWriter();
					out.println(traRoles);
					out.flush();
				}catch(Exception e){
					e.printStackTrace();
				}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
	}
	/**
	 * to get the assigned roles to the user by taking the user id as the parameter
	 */
	@RequestMapping(value="/getassignedroles",method=RequestMethod.GET)
	public void getAssignedRoles(Model model,@RequestParam("userId") String userId,HttpServletRequest request,HttpServletResponse response)
	{
		if(userId == null || userId == ""){
			return;
		}
		List<Integer> roleIds = new ArrayList<Integer>();
		//System.out.println("getAssignedRoles : "+userId);
		try {
			Integer user = Integer.parseInt(userId);
			String trRoles = "";
			List<Role> roleList = roleService.getRolesByUser(user);
			//System.out.println("Inside UserRoleController : "+roleList);
			
			if(roleList != null){
				for(Role list : roleList){
					trRoles += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
					roleIds.add(list.getId());
				}
			}
			//System.out.println("Added to session : "+roleIds);
			request.getSession().setAttribute("roleIds", roleIds);
				try{
					PrintWriter out = response.getWriter();
					out.println(trRoles);
					out.flush();
				}catch(Exception e){
					LOG.error(e.toString());
					e.printStackTrace();
				}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	/**
	 * This method is to get the available roles to the team by taking the team id as the parameter
	 */
	@RequestMapping(value="/getavailablerolestoteam",method=RequestMethod.GET)
	public void getAvailableRolesToTeam(Model model,@RequestParam("teamId") String teamId,HttpServletRequest request,HttpServletResponse response)
	{
		
		if(teamId == null || teamId == ""){
			return;
		}
		try {
			Integer team = Integer.parseInt(teamId);
			String traRoles = "";
			List<Role> roleList = roleService.getRolesNotAssignedToTeam(team);
			for(Role list : roleList){
				if(!list.getName().equalsIgnoreCase("System Administrator")){
					if(!list.getName().equalsIgnoreCase("Application Administrator")){
						traRoles += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
					}
				}
			}
				try{
					PrintWriter out = response.getWriter();
					out.println(traRoles);
					out.flush();
				}catch(Exception e){
					LOG.error(e.toString());
					e.printStackTrace();
				}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * method to get the assigned roles to the team. It will take the teamid as the parameter
	 */
	@RequestMapping(value="/getassignedrolestoteam",method=RequestMethod.GET)
	public void getAssignedRolesToTeam(Model model,@RequestParam("teamId") String teamId,HttpServletRequest request,HttpServletResponse response)
	{
			if(teamId == null || teamId == ""){
				//System.out.println("getAssignedRolesToTeam");
				return;
			}
		try {
			//System.out.println("inside getassignedrolestoteam");
			Integer team = Integer.parseInt(teamId);
			String trRoles = "";
			List<Role> roleList = roleService.findByTeamId(team);
			List<Integer> roleIds = new ArrayList<Integer>();
			if(roleList != null){
				for(Role list : roleList){
					trRoles += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
					roleIds.add(list.getId());
				}
			}
			request.getSession().setAttribute("roleIds", roleIds);
			//System.out.println("Added to Session : "+roleIds);
				try{
					PrintWriter out = response.getWriter();
					out.println(trRoles);
					out.flush();
				}catch(Exception e){
					e.printStackTrace();
				}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
	}
	@Override
	public QTAdminService<UserRole> getTService() {
		return userRoleService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}

}
