/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.infotree.qliktest.admin.dao.referencedata.SessionsDao;
import com.infotree.qliktest.admin.dao.referencedata.UserDao;
import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.Sessions;
import com.infotree.qliktest.admin.entity.referencedata.Tenant;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.PasswordPojo;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.PermissionsService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.ProjectTenantService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.TenantService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserRoleService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
@SuppressWarnings("unused")
@Controller
public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired UserDao userDao;
	
	@Autowired
	private SessionsDao sessionsDao;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleServie;
	
	@Autowired
	private ProjectTenantService projectTenantService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private UserTenantService userTenantService;
 	
	@Autowired
	private PermissionsService permissionsService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	/**
	 * This method is to authenticate the user by taking the userid and the password
	 */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String landingPage(HttpServletRequest request, HttpServletResponse response, Model model) {
    	String msg = "";
    	Sessions sessions = new Sessions();
    	//String ipAddress = request.getRemoteAddr();
    	//Integer id = (Integer)request.getSession().getAttribute("userid");
    	/*if(ipAddress.equals("0:0:0:0:0:0:0:1"))
    	{
    		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ipAddress = inetAddress.getHostAddress();
    		} catch (UnknownHostException e) {
			LOG.error(e.toString());
			e.printStackTrace();
    		}
    	}	*/
    	
    	String ip = request.getHeader("X-Forwarded-For");  
	 	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getHeader("Proxy-Client-IP");
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getHeader("WL-Proxy-Client-IP");
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getHeader("HTTP_CLIENT_IP");
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		    ip = request.getRemoteAddr();  
		}
		
		if(ip.equals("0:0:0:0:0:0:0:1"))
    	{
    		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			ip = inetAddress.getHostAddress();
    		} catch (UnknownHostException e) {
			LOG.error(e.toString());
			e.printStackTrace();
    		}
    	}	
    	
    	System.out.println("Client IPAddress : "+ip);
		try {
			HttpSession session = (HttpSession) request.getSession();
			String username = null; 
			String password = null;
			
			if(request.getParameter("username") != null){
				username = request.getParameter("username");
			}
			if(request.getParameter("password") != null){
				password = request.getParameter("password");
			}
			
			User user = validateUser(username, PasswordHashing.cryptWithMD5(password));
			
			if(user == null){
				model.addAttribute("error", "Invalid Username/Password");
				msg = "newerror";
			}else{
				
				model.addAttribute("username", user.getFirstName());	// get username
				//session.setAttribute("username", user.getFirstName());
				session.setAttribute("username", user.getUserName());
				session.setAttribute("userid",user.getId());
				session.setAttribute("ipAddress", ip);
				session.setAttribute("passwordchange", user.getIsPasswordChangeRequired());
				
				String userrole = getUserRole(username);
				
				if(userrole != null){
					model.addAttribute("roleName", userrole);			// get user role name
					session.setAttribute("roleName", userrole);
				}
				
				List<String> newPermList = new ArrayList<String>();		// get user permissions
				List<String> permList = getPermissions(user.getUserName());
				if(permList != null && permList.size() > 0){
					for (String string : permList) {
						newPermList.add(string);
					}
				}
				model = populatePermissionIntoModel(permList, model, session);
				model.addAttribute("permList", newPermList);
				session.setAttribute("permList", newPermList);
				model.addAttribute("pwdchangerequired", user.getIsPasswordChangeRequired());
				if(user.getIsPasswordChangeRequired() == 0){
					msg = "home";
				}else{
					model.addAttribute("crudObj",new PasswordPojo());
					model.addAttribute("changepasswordforfirstlogin", "changepasswordforfirstlogin");
					msg = "passwordhome";
				}
			}
			
			/*
			 * Placing the userid in the session
			 */
			sessions.setUserid(user.getId());
			sessionsDao.insertUser(sessions);
			
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			String clientZone = clientTimeZone.getID();
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID(clientZone);
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			record.setActionType(AuditType.LOGIN);
			record.setActionData("logged in username"+username);
			record.setActionPerformed("Logged into application");
			record.setIpOrigin(ip);
			/*try {
				InetAddress inetAddress = InetAddress.getLocalHost();
				record.setIpOrigin(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}*/
			record.setUserId((Integer)request.getSession().getAttribute("userid"));
			record.setUserName(request.getSession().getAttribute("username").toString());
			//record.setUserName(username);
			auditLogService.saveRecord(record);
			Role ur = roleService.getById(userRoleServie.findByUserId(user.getId()).getUserRoleComp().getRoleId());
			String role = ur.getName();
			
		
			if(role.equalsIgnoreCase("System Administrator")){
				List<User> tenantList = new ArrayList<User>();
				Integer id = (Integer)request.getSession().getAttribute("userid");
				List<User> userList = userProfileService.getUsersByCreated(id+"");
				if(userList != null){
					Iterator<User> itr = userList.iterator();
					while(itr.hasNext()){
						User user1 = new User();
						User u = itr.next();
						String projects = "";
						UserTenant ut = userTenantService.findByUserId(u.getId());
						user1.setUserName(u.getUserName());
						user1.setFirstName(u.getFirstName());
						user1.setSurName(u.getSurName());
						user1.setEmailAddress(u.getEmailAddress());
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						user1.setProjectCount(projectTenantService.getCountByTenantId(t.getId()));
						user1.setTenantName(t.getName());
						List<ProjectTenant> list = projectTenantService.getByTenantId(t.getId());
						if(list != null){
							Iterator<ProjectTenant> iterator = list.iterator();
							while(iterator.hasNext()){
								ProjectTenant pt = iterator.next();
								Project p = projectService.getById(pt.getProjectTenantComp().getProjectId());
								if(projects == ""){
									projects = p.getName();
								}else{
									projects = projects+" "+"<br>"+p.getName();
								}
								
								}
							user1.setProjectName(projects);
						}
						
						tenantList.add(user1);
						}
				}
				
				
				HttpSession session1=request.getSession();
			model.addAttribute("tenantList", tenantList);
				
			List<Project> projectLists = projectService.getActiveProjects();
			List<Project> projectList = new ArrayList<Project>();
			if(projectLists!=null){
				Iterator<Project> iterator = projectLists.iterator();
				while(iterator.hasNext()){
					Project project = iterator.next();
					ProjectTenant projectTenant = projectTenantService.findByProjectId(project.getId());
					Tenant tenant = null;
						tenant = tenantService.getById(projectTenant.getProjectTenantComp().getTenantId());
						project.setTenantName(tenant.getName());
					//String tenantName = tenantService.getById(projectTenant.getProjectTenantComp().getTenantId()).getName();
					projectList.add(project);
				}
			}
			
			//model.addAttribute("projectList", projectService.getActiveProjects());
			model.addAttribute("projectList", projectList);
			}
			else if(role.equalsIgnoreCase("Application Administrator")){ //For Appadmin
				Integer id = (Integer)request.getSession().getAttribute("userid");
				List<User> userList = userProfileService.getUsersByCreated(id+"");
				UserTenant ut = userTenantService.findByUserId(id);
				List<ProjectTenant> projectTenants = projectTenantService.getByTenantId(ut.getUserTenantComp().getTenantId());
				List<Project> projLists = new ArrayList<Project>();
				if( projectTenants != null ){
					for(ProjectTenant pt : projectTenants){
						Project p = null;
						p = projectService.getById(pt.getProjectTenantComp().getProjectId());
						projLists.add(p);
					}
				}
				model.addAttribute("usersCreatedByAppAdmin", userList);
				model.addAttribute("projectToTenant", projLists);
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		return msg;
    }
    /**
     * Putting the logged user details into the JSON object
     */
    @RequestMapping(value = "/userdetails", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getUserDetails(@RequestParam String username, @RequestParam String password) {
    
    	Map<String, Object> json=null;
		try {
			json = new HashMap<String, Object>();
			User user = null;
			if(username == null) {
				json.put("errorMsg", "Username is null");
			}
			else if (password == null){
				json.put("errorMsg", "Password is null");
			}else {
				user = validateUser(username, password);
				if(user == null){
					json.put("errorMsg", "Invalid Username/Password.");
				} 
				else {
					json.put("username", user.getFirstName());
					json.put("first_name", user.getFirstName());
					json.put("sur_name", user.getSurName());
					json.put("email_address", user.getEmailAddress());
					json.put("landline", user.getLandline());
					json.put("mobile", user.getMobile());
					json.put("id", user.getId());
					json.put("is_password_change_required", user.getIsPasswordChangeRequired());
					String roleName = getUserRole(username);
					if(roleName != null){
						json.put("roleName", roleName);
					}
					List<String> permissions = getPermissions(username);
					if(permissions != null && permissions.size() > 0){
						json.put("permissions", permissions);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
   		return json;
    }
    /**
     * This method is to go to the home page
     */
    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String homeLandingpage(Model model) {
		return "home";
    }
    /**
     * This method is to get the userrole by using the username
     */
    public String getUserRole(String username) {
    	return userDao.getUserRole(username);
    }
    /**
     * This method is to validate the user by using the credentials
     */
    public User validateUser(String username, String password) {
    	return userDao.getUserByUserNameAndPassword(username, password);
    }
    /**
     * This method is to get the permissions based on the username
     */
    public List<String> getPermissions(String username) {
    	return userDao.getPermissions(username);
    }
    /**
     * This method is to fetch all the permission which are applicabe for the user based on the role
     */
    public Model populatePermissionIntoModel(List<String> permissionsList, Model model, HttpSession session){
    	try {
			ArrayList<String> userList = new ArrayList<String>();
			ArrayList<String> projectsList = new ArrayList<String>();
			ArrayList<String> teamList = new ArrayList<String>();
			ArrayList<String> roleList = new ArrayList<String>();
			ArrayList<String> appAdminReports = new ArrayList<String>();
			ArrayList<String> logList = new ArrayList<String>();
			
			
			
			for (String string : permissionsList) {
				
				//for users of app admin
				//For users 
				if(string.contains("create_user")){
					userList.add(string);
				}
				
				//for appadmin reports
				else if(string.contains("view_users_report")){
					appAdminReports.add(string);
				}
				else if(string.contains("view_teams_reports")){
					appAdminReports.add(string);
				}
				
				else if(string.contains("view_users")){
					userList.add(string);
				}
				else if(string.contains("edit_user")){
					userList.add(string);
				}
				else if(string.contains("assign_reports_to_user")){
					userList.add(string);
				}
				else if(string.contains("import_users")) {
					userList.add(string);
				}
				
				
				
				//For project Management
				else if(string.contains("view_modules")){
					projectsList.add(string);
				}
				else if(string.contains("view_projects")){
					projectsList.add(string);
				}
				else if(string.contains("assign_users_to_project")){
					projectsList.add(string);
				}
				else if(string.contains("remove_users_from_project")){
					projectsList.add(string);
				}
				
				
				
				//for teams management
				else if(string.contains("create_team")){
					teamList.add(string);
				}
				else if(string.contains("view_teams")){
					teamList.add(string);
				}
				else if(string.contains("edit_team")){
					teamList.add(string);
				}
				else if(string.contains("assign_users_to_team")){
					teamList.add(string);
				}
				/*else if(string.contains("remove_users_from_team")){
					teamList.add(string);
				}*/
				else if(string.contains("assign_teams_to_project")){
					teamList.add(string);
				}
				/*else if(string.contains("remove_teams_from_project")){
					teamList.add(string);
				}
				*/
				
				//for Role Management
				else if(string.contains("view_roles")){
					roleList.add(string);
				}
				else if(string.contains("assign_roles_to_user")){
					roleList.add(string);
				}
				else if(string.contains("assign_roles_to_team")){
					roleList.add(string);
				}
				else if(string.contains("assign_reports_to_role")){
					roleList.add(string);
				}
				
				
				
				//for auditlogs
				else if(string.contains("view_audit_logs")){
					appAdminReports.add(string);
				}
				
			}
			
			//Adding the permissions to the model
			
			model.addAttribute("userList", userList);
			model.addAttribute("projectsList", projectsList);
			model.addAttribute("teamList", teamList);
			model.addAttribute("roleList",roleList);
			model.addAttribute("appAdminReports",appAdminReports);
			model.addAttribute("logList",logList);
			
			// Putting the permissions into the sessions
			
			session.setAttribute("userList", userList);
			session.setAttribute("projectsList", projectsList);
			session.setAttribute("teamList", teamList);
			session.setAttribute("appAdminReports", appAdminReports);
			session.setAttribute("roleList", roleList);
			session.setAttribute("logList", logList);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
     	
    	return model;
    }
}
