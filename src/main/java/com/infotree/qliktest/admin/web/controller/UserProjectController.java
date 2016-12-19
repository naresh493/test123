/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;
import com.infotree.qliktest.admin.entity.referencedata.UserProjectComp;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.TeamProjectPojo;
import com.infotree.qliktest.admin.helperpojo.UserProjectPojo;
import com.infotree.qliktest.admin.mails.UserProjectMail;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.mail.MailService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserProjectService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/userproject")
public class UserProjectController extends AbstractQTAdminController<UserProject> {
	private static final Logger LOG = LoggerFactory.getLogger(UserProjectController.class);
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserProjectService userProjectService;
	
	@Autowired
	private AuditLogRecordService auditLogService; 
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private NotificationsDao notificationsDao;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private DoNothingValidator validator;
	
	/**
	 * This method is to 
 the users to the project. It will return the UI to assign the users to the project
	 */
	@RequestMapping(value="/assignuserstoproject",method=RequestMethod.GET)
	public String assignUsersToProject(Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			model.addAttribute("assignuserstoproject","assignuserstoproject");
			model.addAttribute("crudObj",new UserProjectPojo());
			HttpSession session=request.getSession();
			Integer id=(Integer)session.getAttribute("userid");
			UserTenant userTenant = userTenantService.findByUserId(id);
			model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));	
			/*model.addAttribute("user_list",userService.getUsersBasedOnCreatedBy(id));*/
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to save the users to the project
	 */
	@RequestMapping(value="/assign",method=RequestMethod.POST)
	public String assignUsers(@Valid UserProjectPojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		String displayMessage = null;
		List<Integer> newUserIds = new ArrayList<Integer>();
		try {
			
			List<Integer> beforeAssignedUserList = (List<Integer>) request.getSession().getAttribute("userIds");
			List<Integer> newlyAssignedUserList = entity.getUsers();
			int beforeAssignedUserSize = beforeAssignedUserList.size();
			int newlyAssignedUserSize = 0;
			if(newlyAssignedUserList != null)
				newlyAssignedUserSize = newlyAssignedUserList.size();

			if (beforeAssignedUserSize == 0 && newlyAssignedUserSize == 0) {
				displayMessage = "Please assign atleast one User to the Project";
				model.addAttribute("displayMessage", displayMessage);
				model.addAttribute("assignuserstoproject","assignuserstoproject");
				UserTenant userTenant = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
				model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
				model.addAttribute("crudObj",new UserProjectPojo());
				return "home";
			} else if (beforeAssignedUserSize > 0 && newlyAssignedUserSize == 0) {
				if (beforeAssignedUserSize == 1)
					displayMessage = "1 user is removed";
				else
					displayMessage = beforeAssignedUserSize + " users are removed ";
			} else if(beforeAssignedUserSize == 0 && newlyAssignedUserSize > 0){
					newUserIds.addAll(newlyAssignedUserList);
					if(newlyAssignedUserSize == 1)
						displayMessage=" 1 user is assigned";
					else
						displayMessage = newlyAssignedUserSize+" users are assigned ";
			}
			
			else{
				int sameUserAssigned = 0;
				boolean flag ;
				if(newlyAssignedUserList != null){
					for(Integer userId : newlyAssignedUserList){
						flag = false;
						if( beforeAssignedUserList != null){
							for(Integer uid : beforeAssignedUserList){
								if(userId.intValue() == uid.intValue()){
									sameUserAssigned++;
									flag = true;
									break;
								}
							}
						}
						if(flag == false){
							newUserIds.add(userId);
						}
					}
					
				}
				int removedUser = beforeAssignedUserSize - sameUserAssigned;
				if(removedUser == 0)
					displayMessage = "";
				else if(removedUser == 1)
					displayMessage = " and 1 user is removed";
				else
					displayMessage = " and "+removedUser+" users are removed";
					
					int assignedUser = 	newlyAssignedUserSize - sameUserAssigned;
					
					if(assignedUser == 0 && beforeAssignedUserSize == newlyAssignedUserSize)
						displayMessage = "No changes are made";
					else if(assignedUser == 0){
							if(removedUser == 0)
								displayMessage = "";
							else if(removedUser == 1)
								displayMessage = " 1 user is removed";
							else
								displayMessage = removedUser+" users are removed";
					}
					else if(assignedUser == 1)
						displayMessage = "1 user is assigned"+displayMessage;
					else
						displayMessage = assignedUser+" projects are assigned"+displayMessage;
				
			}
			model.addAttribute("displayMessage", displayMessage);
			User tenant = userService.getById((Integer)request.getSession().getAttribute("userid"));
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
			String userNames = "";
			List<Integer> userList = entity.getUsers();
			Integer projectId = entity.getProjectId();
			userProjectService.deleteByProjectId(projectId);
			if(userList != null){
				Iterator<Integer> iterator = userList.iterator();
				UserProjectComp userProjectComp = new UserProjectComp();
				UserProject userproject = new UserProject();
				while(iterator.hasNext()){
					Integer userId = iterator.next();
					userProjectComp.setProjectId(entity.getProjectId());
					userProjectComp.setUserId(userId);
					userproject.setUserProjectComp(userProjectComp);
					userproject.setCreatedBy(entity.getCreatedBy());
					userproject.setCreatedDate(dateInIndia);
					userproject.setModifiedBy(entity.getModifiedBy());
					userproject.setModifiedDate(dateInIndia);
					userProjectService.save(userproject);
					User u = userService.getById(userId);
					Project p = projectService.getById(entity.getProjectId());
					/**
					 * save the notification
					 */
					Notifications notifications = new Notifications();
					notifications.setId(notificationsDao.getMaxId()+1);
					notifications.setAssignedBy((Integer)request.getSession().getAttribute("userid")+"");
					notifications.setAssignedTo(u.getId()+"");
					notifications.setAssignedData("You have been assigned for "+p.getName());
					notifications.setStatus("UnRead");
					notifications.setCreatedBy(entity.getCreatedBy());
					notifications.setModifiedBy(entity.getModifiedBy());
					notifications.setCreatedDate(dateInIndia);
					notifications.setModifiedDate(dateInIndia);
					
					notificationsDao.saveNotification(notifications);
					if(userNames == ""){
						userNames = u.getUserName();
					}else{
						userNames = userNames+","+u.getUserName();
					}
					/*
					UserProjectMail mail = new UserProjectMail();
					mail.setUserName(u.getUserName());
					mail.setProjectName(p.getName());
					mail.setEndDate(p.getEndDate());
					mail.setStartDate(p.getStartDate());
					mail.setTenantName(tenant.getUserName());
					
					try {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("entity", mail);
						mailService.sendMailUserProject("dev@infotreesolutions.com", u.getEmailAddress(), "You have been assigned for a project", map);
					} catch (Exception e) {
						e.printStackTrace();
					}*/
					model.addAttribute("usersassigned", "Users Assigned to The Project");
				}//end of While loop
				
				Project p = projectService.getById(entity.getProjectId());
				//System.out.println("mail will sent to : "+newUserIds.size());
				if(newUserIds.size() > 0){
					for(Integer userId : newUserIds){
						User u = userService.getById(userId);
						UserProjectMail mail = new UserProjectMail();
						mail.setUserName(u.getUserName());
						mail.setProjectName(p.getName());
						mail.setEndDate(p.getEndDate());
						mail.setStartDate(p.getStartDate());
						mail.setTenantName(tenant.getUserName());
						try {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("entity", mail);
							mailService.sendMailUserProject("dev@infotreesolutions.com", u.getEmailAddress(), "You have been assigned for a project", map);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}
			else{
				model.addAttribute("usersassigned", "Users Assigned to The Project");
			}
			
			/**
			 * Preparing the audit record
			 */
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			//record.setActionDate(entity.getCreatedDate());
			record.setActionType(AuditType.ASSIGN);
			
			Project p = projectService.getById(entity.getProjectId());
			record.setProjectName(p.getName());
			record.setActionData("User names "+userNames+" and project name is"+p.getName());
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
			HttpSession session=request.getSession();
			Integer id=(Integer)session.getAttribute("userid");
			UserTenant userTenant = userTenantService.findByUserId(id);
			model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
			model.addAttribute("assignuserstoproject","assignuserstoproject");
			model.addAttribute("crudObj",new UserProjectPojo());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
		
	}
	/**
	 * To remove the users from the project.
	 */
	@RequestMapping(value="/removeusersfromproject",method=RequestMethod.GET)
	public String removeUsers(Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			HttpSession session=request.getSession();
			Integer id=(Integer)session.getAttribute("userid");
			model.addAttribute("crudObj",new UserProjectPojo());
			model.addAttribute("removeusersfromproject","removeusersfromproject");
			UserTenant userTenant = userTenantService.findByUserId(id);
			
			model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));	
			model.addAttribute("user_list",userService.getUsersBasedOnCreatedBy(id));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method will remove the users from the project by taking the userid as the parameter
	 */
	@RequestMapping(value="/removeselectedusersfromproject", method=RequestMethod.POST)
	public String removeSelectedUsersFromTeam( @Valid UserProjectPojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
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
			Integer id = (Integer)request.getSession().getAttribute("userid");
			model.addAttribute("crudObj",entity);
			List<Integer> l=entity.getUserId();
			model.addAttribute("removeusersfromproject","removeusersfromproject");
			
			Iterator<Integer> itr=l.iterator();
			String userId="";
			while(itr.hasNext()){
				if(userId==""){
					userId=userId+itr.next();
				}
				else{
				userId=userId+","+itr.next();
				}
			}
			String userNames = "";
			Iterator<Integer> iterator = l.iterator();
			while(iterator.hasNext()){
				User u = userService.getById(iterator.next());
				if(userNames == ""){
					userNames = u.getUserName();
				}else{
					userNames = userNames+","+u.getUserName();
				}
			}
			List<Integer> userList = entity.getUserId();
			Iterator<Integer> iterate = userList.iterator();
			while(iterate.hasNext()){
				User u = userService.getById(iterate.next());
				User tenant = userService.getById((Integer)request.getSession().getAttribute("userid"));
				Project p = projectService.getById(entity.getProjectId());
				
				/**
				 * prepare the notification when the user removes from the project
				 */
				Notifications notifications = new Notifications();
				notifications.setId(notificationsDao.getMaxId()+1);
				notifications.setAssignedBy((Integer)request.getSession().getAttribute("userid")+"");
				notifications.setAssignedTo(u.getId()+"");
				notifications.setAssignedData("You have been Removed from "+p.getName());
				notifications.setStatus("UnRead");
				notifications.setCreatedBy(entity.getCreatedBy());
				notifications.setModifiedBy(entity.getModifiedBy());
				notifications.setCreatedDate(dateInIndia);
				notifications.setModifiedDate(dateInIndia);
				
				notificationsDao.saveNotification(notifications);
				UserProjectMail mail = new UserProjectMail();
				mail.setUserName(u.getUserName());
				mail.setProjectName(p.getName());
				mail.setTenantName(tenant.getUserName());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("entity", mail);
				mailService.sendMailUserRemoveProject("dev@infotreesolutions.com", u.getEmailAddress(), "You have been Removed from the project", map);
			}
			userProjectService.deleteMultUsersFromProject(entity.getProjectId(), userId);
			/**
			 * Preparing the audit record 
			 */
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			//record.setActionDate(entity.getModifiedDate());
			record.setActionType(AuditType.REMOVE);
			
			Project p = projectService.getById(entity.getProjectId());
			record.setProjectName(p.getName());
			record.setActionData("User names are "+userNames+" and project name is"+p.getName());
			record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
			/*
			 try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				record.setIpOrigin(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
			*/
			record.setUserId((Integer)request.getSession().getAttribute("userid"));
			record.setUserName(request.getSession().getAttribute("username").toString());
			auditLogService.saveRecord(record);
			model.addAttribute("usersremoved", "'" + l.size() + "'users removed." );
			UserTenant userTenant = userTenantService.findByUserId(id);
			
			model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * To get the users based on the project. it will return the user list by taking the user id as the parameter
	 */
	@RequestMapping(value="/getremoveusersdata",method=RequestMethod.GET)
	public void getusersdata(Model model,@RequestParam("projectid") String projectid,HttpServletRequest request,HttpServletResponse response)
	{
		
		try {
			int projectId = Integer.parseInt(projectid);
			List<User> userList=userProjectService.getUsersByProjectId(projectId);
			String trUsers="";
			if(userList.contains(null)){
				trUsers += "<option label='No Users Available' value='No Users Available' disabled='true'</option>";
				try{
					PrintWriter out=response.getWriter();
					out.print(trUsers);
					out.flush();
				}
				catch(IOException e){
					
				}
			}
			else if(userList !=null && userList.size() >0){
				
				for (User user : userList) {
					trUsers += "<option label='"+user.getFirstName()+"' value='"+user.getId()+"'>"+user.getFirstName()+"</option>";
				}
				try {
					PrintWriter out = response.getWriter();
					out.println(trUsers);
					out.flush();
				} catch (IOException e) {
					
				}
			}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * For calling the ajax related methods
	 */
	@RequestMapping(value="/getavailableusers",method=RequestMethod.GET)
	public void getAvailableUsers(Model model,@RequestParam("projectId") String projectId,HttpServletRequest request,HttpServletResponse response)
	{
		if(projectId == null || projectId == ""){
			//System.out.println("getAvailableUsers");
			return;
		}
		try {
			Integer userId = (Integer)request.getSession().getAttribute("userid");
			Integer projId = Integer.parseInt(projectId);
			String traUsers = "";
			List<User> userList = null;
			try {
				userList = userService.getUsersNotAssignedProjectAndCreated(projId, userId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(userList != null){
				for(User list : userList){
					traUsers += "<option label='"+list.getUserName()+"' value='"+list.getId()+"'>"+list.getUserName()+"</option>";
				}
			}
				try{
					PrintWriter out = response.getWriter();
					out.println(traUsers);
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
	 * This method will fetch the assigned users to the project by taking the project id as the parameter
	 */
	@RequestMapping(value="/getassignedusers",method=RequestMethod.GET)
	public void getAssignedUsers(Model model,@RequestParam("projectId") String projectId,HttpServletRequest request,HttpServletResponse response)
	{
		try {
			//System.out.println("Inside getAssignedUsers : "+projectId);
			if(projectId == null || projectId == ""){
				//System.out.println("getAssignedUsers");
				return;
			}
			Integer projId = Integer.parseInt(projectId);
			Integer userId = (Integer)request.getSession().getAttribute("userid");
			String trAsUsers = "";
			List<User> userList = userService.getUsersAssignedProjectAndCreated(projId, userId);
			List<Integer> userIds = new ArrayList<Integer>();
			
			if(userList != null){
				for(User list : userList){
					trAsUsers += "<option label='"+list.getUserName()+"' value='"+list.getId()+"'>"+list.getUserName()+"</option>";
					userIds.add(list.getId());
				}
			}
			request.getSession().setAttribute("userIds", userIds);
			//System.out.println("Added to Session : "+userIds);
				try{
					PrintWriter out = response.getWriter();
					out.println(trAsUsers);
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
	public QTAdminService<UserProject> getTService() {
		return userProjectService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}
}
