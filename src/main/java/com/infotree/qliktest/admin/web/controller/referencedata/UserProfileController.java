package com.infotree.qliktest.admin.web.controller.referencedata;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.entity.referencedata.Tenant;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;
import com.infotree.qliktest.admin.entity.referencedata.UserProjectComp;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;
import com.infotree.qliktest.admin.entity.referencedata.UserRoleComp;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;
import com.infotree.qliktest.admin.entity.referencedata.UserTeamComp;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.referencedata.UserTenantComp;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.mail.MailService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.PermissionsService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.ProjectTenantService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.TeamService;
import com.infotree.qliktest.admin.service.referencedata.TenantService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserProjectService;
import com.infotree.qliktest.admin.service.referencedata.UserRoleService;
import com.infotree.qliktest.admin.service.referencedata.UserTeamService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
import com.infotree.qliktest.admin.service.system.AuditService;
import com.infotree.qliktest.admin.web.controller.AbstractQTAdminController;
import com.infotree.qliktest.admin.web.controller.PasswordHashing;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;


/**
 * Controller bean for user creation.
 */
@Controller
@RequestMapping("/userprofile")
public class UserProfileController extends AbstractQTAdminController<User> {

	private static final Logger LOG = LoggerFactory.getLogger(UserProfileController.class);
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private UserProjectService userProjectService;
	
	@Autowired
	private ProjectTenantService projectTenantService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private PermissionsService permissionsService;
	
	@Autowired
	private AuditLogRecordService auditRecordService;
	

	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private AuditService auditService;
	
	@Autowired
	private UserTeamService userTeamService;

	
	/*
	 * This method will return the UI for the creation of the tenant
	 */
	@RequestMapping(value="/createtenant",method = RequestMethod.GET)
	public String createTenant(Model model){
		try {
			model.addAttribute("curdObj", getEntityInstance());
			//Chcking the app admin role available or not to assign that role to the creating user
			Role role = roleService.findByName("Application Administrator");
			if(role == null){
				model.addAttribute("defaultusercreated","<font color='red'>Create the Application Administrator role first");
				model.addAttribute("roleName","");
			}else{
				model.addAttribute("roleName",role.getName());
				model.addAttribute("permissions_list",permissionsService.getpermissionsByRoelId(role.getId()));
			}
			model.addAttribute("tenant_list",tenantService.list());
			model.addAttribute("createdefaultusertenant","createdefaultusertenant");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/*
	 * This method saves the tenant and user details
	 */
	@RequestMapping(value="/savedefaultuser", method = RequestMethod.POST)
    public String saveDefaultUser(@ModelAttribute("curdObj") @Valid User entity, BindingResult bindingResult, Model model,HttpServletRequest request) {  //NOPMD
			
		DateTime dt = new DateTime();
		DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
		DateTime dtus = dt.withZone(dtZone);
		Date dateInIndia = dtus.toLocalDateTime().toDate();
					try {
						Role role = roleService.findByName("Application Administrator");
						User user = userProfileService.findByUserName(entity.getUserName());
						User userMail = userProfileService.findByEmail(entity.getEmailAddress());
						Tenant tenant = tenantService.findByName(entity.getTenantName());
						if(role == null){
							model.addAttribute("defaultusercreated","<font color='red'>Unable to create the tenant. Please create the role first</font>");
							model.addAttribute("createdefaultusertenant", "createdefaultusertenant");
							model.addAttribute("savedefaultuser", "savedefaultuser");
							model.addAttribute("roleName","");
						}else if(user != null){
							model.addAttribute("defaultusercreated","<font color='red'>UserName already exists</font>");
							model.addAttribute("createdefaultusertenant", "createdefaultusertenant");
							model.addAttribute("savedefaultuser", "savedefaultuser");
						}else if(userMail != null){
							model.addAttribute("defaultusercreated","<font color='red'>Email already exists</font>");
							model.addAttribute("createdefaultusertenant", "createdefaultusertenant");
							model.addAttribute("savedefaultuser", "savedefaultuser");
						}else if(tenant != null){
							model.addAttribute("defaultusercreated","<font color='red'>Tenant Name already exists</font>");
							model.addAttribute("createdefaultusertenant", "createdefaultusertenant");
							model.addAttribute("savedefaultuser", "savedefaultuser");
						}else{
							//save the tenant
							Tenant t = new Tenant();
							t.setName(entity.getTenantName());
							t.setCompanyName(entity.getCompanyName());
							t.setDisplayName(entity.getDisplayName());
							t.setCreatedBy(entity.getCreatedBy());
							t.setCreatedDate(dateInIndia);
							Tenant savedTenant = tenantService.save(t);
							//Generating the password for the user
							char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
							StringBuilder sb = new StringBuilder();
							Random random = new Random();
							for (int i = 0; i < 10; i++) {
							    char c = chars[random.nextInt(chars.length)];
							    sb.append(c);
							}
							
							
							//save the user
							User u = new User();
							u.setUserName(entity.getUserName());
							u.setFirstName(entity.getFirstName());
							u.setSurName(entity.getSurName());
							u.setEmailAddress(entity.getEmailAddress());
							u.setLandline(entity.getLandline());
							u.setMobile(entity.getMobile());
							u.setPassword(PasswordHashing.cryptWithMD5(sb.toString()));
							u.setCreatedBy(entity.getCreatedBy());
							u.setCreatedDate(dateInIndia);
							u.setIsPasswordChangeRequired((byte) 1);
							u.setImportedFromLdap((byte) 0);
							u.setNda((byte)1);
							u.setDomain(null);
							u.setExperience(null);
							u.setNoOfProjectsWorked(null);
							
							//setting the password to the user class to send it to the user
							entity.setPassword(sb.toString());
							Role r = roleService.findByName("Application Administrator");
							
							//save the role to the user
							User savedUser = userProfileService.save(u);
							UserRole ur = new UserRole();
							UserRoleComp userRoleComp = new UserRoleComp();
							userRoleComp.setUserId(savedUser.getId());
							userRoleComp.setRoleId(r.getId());
							ur.setUserRoleComp(userRoleComp);
							ur.setCreatedBy(entity.getCreatedBy());
							ur.setCreatedDate(dateInIndia);
							userRoleService.save(ur);
							//Assign user id to the tenant id
							UserTenant ut = new UserTenant();
							UserTenantComp utComp = new UserTenantComp();
							utComp.setUserId(savedUser.getId());
							utComp.setTenantId(savedTenant.getId());
							ut.setUserTenantComp(utComp);
							ut.setCreatedBy(entity.getCreatedBy());
							ut.setCreatedDate(dateInIndia);
							userTenantService.save(ut);
							
							
							// Preparing the log statement
							
							AuditLogRecord record = new AuditLogRecord();
							//record.setActionDate(new java.util.Date());
							record.setActionDate(entity.getCreatedDate());
							record.setUserId((Integer)request.getSession().getAttribute("userid"));
							record.setUserName(request.getSession().getAttribute("username").toString());
							record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
							/*try {
								java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
								record.setIpOrigin(inetAddress.getHostAddress());
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
							record.setActionData("Tenant Created With Name "+entity.getTenantName());
							record.setActionType(AuditType.CREATE);
							auditRecordService.saveRecord(record);
							
							//Reverting back to the UI
							model.addAttribute("createdefaultusertenant", "createdefaultusertenant");
							model.addAttribute("savedefaultuser", "savedefaultuser");
							model.addAttribute("crudObj",getEntityInstance());
							model.addAttribute("defaultusercreated","'" + savedUser.getFirstName() + "' Tenant created successfully ");
							model.addAttribute("permissions_list",permissionsService.getpermissionsByRoelId(r.getId()));
							
							Map<String, Object> map = new HashMap<String, Object>();
							//map.put("appUrl","preprod.qliktest.com/QlikTestAdmin"); 
							map.put("entity", entity);
							//String
							mailService.sendMail("dev@infotreesolutions.com", entity.getEmailAddress(), "New user created in QlikTest Admin Account", map);
						}
						entity.setCompanyName(null);
						entity.setUserName(null);
						entity.setFirstName(null);
						entity.setSurName(null);
						entity.setEmailAddress(null);
						entity.setLandline(null);
						entity.setMobile(null);
						
						entity.setTenantName(null);
						entity.setDisplayName(null);
						model.addAttribute("crudObj",getEntityInstance());
					} catch (Exception e) {
						LOG.error(e.toString());
						e.printStackTrace();
					}
				
			
		return "home";
		}
	
	
	/*
	 * This method returns the UI for the creation of the user
	 */
	@RequestMapping(value="/createuser", method=RequestMethod.GET)
	public String create(Model model,HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			Integer id = (Integer)request.getSession().getAttribute("userid");
			UserTenant userTenant = userTenantService.findByUserId((Integer)session.getAttribute("userid"));
			model.addAttribute("project_list", projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
			List<Role> rolelist = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for (Role role : rolelist) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					if(!role.getName().equalsIgnoreCase("Application Administrator")){
						newRoleList.add(role);
					}
				}
			}
			/*
			AuditLogRecord record = new AuditLogRecord();
			
			record.setActionDate(new java.util.Date());
			record.setUserId((Integer)request.getSession().getAttribute("userid"));
			record.setUserName(request.getSession().getAttribute("username").toString());
			record.setActionPerformed("User Created");
			record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
			try {
				java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
				record.setIpOrigin(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			record.setActionType(AuditType.CREATE);
			auditRecordService.saveRecord(record);*/
			
			
			model.addAttribute("available_roles",newRoleList);
			model.addAttribute("available_projects",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
			model.addAttribute("available_teams",teamService.getTeamsById(id+""));
			
			model.addAttribute("createuser", "createuser");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/*
	 * This method is to view the list of users created by the particular app admin
	 */
	@RequestMapping(value ="/viewuser", method = RequestMethod.GET)
	public String view(Model model,HttpServletRequest request,HttpServletResponse response) {
		try {
			HttpSession session=request.getSession();
			Integer id = (Integer)session.getAttribute("userid");
			model.addAttribute("crudObj", getEntityInstance());
			AuditLogRecord record = new AuditLogRecord();
			
			record.setActionDate(new java.util.Date());
			record.setUserId((Integer)request.getSession().getAttribute("userid"));
			record.setUserName(request.getSession().getAttribute("username").toString());
			record.setActionPerformed("Viewed the users");
			record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
			/*try {
				java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
				record.setIpOrigin(inetAddress.getHostAddress());
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			record.setActionType(AuditType.VIEW);
			auditRecordService.saveRecord(record);
			model.addAttribute("viewappuser", "viewappuser");
			model.addAttribute("user_list", userProfileService.getUsersByCreated(id+""));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/*
	 * This method returns the UI for the editing the user with list of users which are created by the particular app admin
	 */
	@RequestMapping(value="/edituser", method=RequestMethod.GET)
	public String edit(Model model,HttpServletRequest request) {
		try {
			Integer id = (Integer)request.getSession().getAttribute("userid");
			model.addAttribute("crudObj", getEntityInstance());
			model.addAttribute("edituser", "edituser");
			List<User> useList = userProfileService.getUsersByCreated(id+"");
			List<User> usersList = new ArrayList<User>();
			if(useList != null){
				Iterator<User> iterator = useList.iterator();
				while(iterator.hasNext()){
					User u = iterator.next();
					if(u.getModifiedBy() != null){
						Integer i = Integer.parseInt(u.getModifiedBy());
						User us = userProfileService.getById(i);
						u.setCreatedName(us.getUserName());
					}else{
						Integer i = Integer.parseInt(u.getCreatedBy());
						User us = userProfileService.getById(i);
						u.setCreatedName(us.getUserName());
					}
					usersList.add(u);
				}
			}
			model.addAttribute("user_list",usersList);
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/*
	 * This method checks the ldap server that the users are there or not.
	 */
	@RequestMapping(value="/checkldap", method=RequestMethod.GET)
	public void checkLdap(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Integer result = (userProfileService.checkUsersFromLdap() == null) ? new Integer(0) : new Integer(1);
			session.setAttribute("result", result);
			try {
				PrintWriter out = response.getWriter();
				out.print(result);
				out.flush();
			} catch (IOException e) {
				LOG.error("IOException in getData() in Controller ");
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	/*
	 * This method imports the users from the ldap server to the local data base
	 */
	@RequestMapping(value="/importfromldap", method = RequestMethod.POST)
    public void importLdapUsers(HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer userId = (Integer)request.getSession().getAttribute("userid");
			String tenantName = userProfileService.getTenantName(userId);
			List<User> userList = userProfileService.getUsersFromLdap(userId,tenantName);
			
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(new java.util.Date());
			
			record.setActionData("Total number of users imported "+userList.size());
			record.setActionType(AuditType.IMPORT);
			record.setActionPerformed(userList.size()+" users imported from ldap");
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
			auditRecordService.saveRecord(record);
			
			try {
				PrintWriter out = response.getWriter();
				out.print("'" + userList.size() + "' users imported from Ldap.");
				out.flush();
			} catch (IOException e) {
				LOG.error("IOException in getData() in Controller ");
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
    }
	/*
	 * This method returns the UI to edit an user which was selected by the user with the existing values
	 */
	@RequestMapping(value="/users/{profileId}/update", method=RequestMethod.GET)
	public String updateUserById(@ModelAttribute("crudObj") @Valid User entity, @PathVariable Integer profileId, Model model,HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			Integer id = (Integer)session.getAttribute("userid");
			UserTenant ut = userTenantService.findByUserId(id);
			Integer tenantId = ut.getUserTenantComp().getTenantId();
			User entityToBeUpdated = userProfileService.getById(profileId);
			model.addAttribute("crudObj", entityToBeUpdated);
			model.addAttribute("updateuser", "updateuser");
			List<Role> roleList = roleService.getRolesNotAssignedToUser(profileId);
			List<Role> newRoleList = new ArrayList<Role>();
			for (Role role : roleList) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					if(!role.getName().equalsIgnoreCase("Application Administrator")){
						newRoleList.add(role);
					}
				}
			}
			model.addAttribute("available_roles",newRoleList);
			model.addAttribute("available_projects",projectService.getProjectsNotAssignedToUser(profileId,tenantId));
			model.addAttribute("available_teams",teamService.getAvailableTeamsForUser(profileId,id));
			model.addAttribute("assigned_roles",roleService.getRolesByUser(profileId));
			
			model.addAttribute("assigned_projects",projectService.getAssignedProjectsForUser(profileId));
			model.addAttribute("assigned_teams",teamService.getAssignedTeamsForUser(profileId,id));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/*
	 * This method will updates the user details with the new values which are given by the userw
	 */
	@RequestMapping(value="/updateuser", method = RequestMethod.POST)
    public String update(@ModelAttribute("crudObj") @Valid User entity, BindingResult bindingResult, Model model,HttpServletRequest request) {  //NOPMD
		try {
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			Integer id = (Integer)request.getSession().getAttribute("userid");
			User user = userProfileService.findByUserDetailsToUpdate(entity);
			if(user != null){
				model.addAttribute("userupdated","ALready exists please try with another Details");
			}
			else{
				User u = new User();
				u.setId(entity.getId());
				u.setFirstName(entity.getFirstName());
				u.setUserName(entity.getUserName());
				u.setSurName(entity.getSurName());
				u.setEmailAddress(entity.getEmailAddress());
				u.setLandline(entity.getLandline());
				u.setMobile(entity.getMobile());
				u.setModifiedBy(entity.getModifiedBy());
				u.setModifiedDate(dateInIndia);
				u.setDomain(entity.getDomain());
				u.setExperience(entity.getExperience());
				u.setNoOfProjectsWorked(entity.getNoOfProjectsWorked());
				userRoleService.deleteByUserId(u.getId());
				if(entity.getRolesList() != null){
					List<Integer> roleList = entity.getRolesList();
					Iterator<Integer> iterator = roleList.iterator();
					while(iterator.hasNext()){
						UserRole ur = new UserRole();
						UserRoleComp userRoleComp = new UserRoleComp();
						userRoleComp.setUserId(entity.getId());
						userRoleComp.setRoleId(iterator.next());
						ur.setUserRoleComp(userRoleComp);
						ur.setCreatedBy(entity.getCreatedBy());
						ur.setCreatedDate(dateInIndia);
						ur.setModifiedBy(entity.getModifiedBy());
						ur.setModifiedDate(dateInIndia);
						userRoleService.save(ur);
					}
				}
				userProjectService.deleteByUserId(u.getId());
				if(entity.getProjectsList() != null){
					List<Integer> projectList = entity.getProjectsList();
					Iterator<Integer> iterator = projectList.iterator();
					while(iterator.hasNext()){
						UserProjectComp comp = new UserProjectComp();
						UserProject userProject = new UserProject();
						comp.setProjectId(iterator.next());
						comp.setUserId(u.getId());
						userProject.setUserProjectComp(comp);
						userProject.setCreatedBy(entity.getCreatedBy());
						userProject.setCreatedDate(dateInIndia);
						userProjectService.save(userProject);
					}
				}
				userTeamService.deleteByUserId(u.getId());
				if(entity.getTeamsList() != null){
					List<Integer> list = entity.getTeamsList();
					Iterator<Integer> iterator = list.iterator();
					while(iterator.hasNext()){
						UserTeamComp comp = new UserTeamComp();
						UserTeam userTeam = new UserTeam();
						comp.setTeamId(iterator.next());
						comp.setUserId(u.getId());
						userTeam.setUserTeamComp(comp);
						userTeam.setCreatedBy(entity.getCreatedBy());
						userTeam.setCreatedDate(dateInIndia);
						userTeamService.save(userTeam);
					}
				}
				userProfileService.updateAppUser(u);
				AuditLogRecord record = new AuditLogRecord();
				//record.setActionDate(new java.util.Date());
				record.setActionDate(entity.getModifiedDate());
				record.setActionData("user updated with name "+entity.getUserName());
				record.setActionType(AuditType.MODIFY);
				record.setActionPerformed("User updated");
				record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
				/*try {
					InetAddress inetAddress = InetAddress.getLocalHost();
					record.setIpOrigin(inetAddress.getHostAddress());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}*/
				record.setUserId((Integer)request.getSession().getAttribute("userid"));
				record.setUserName(request.getSession().getAttribute("username").toString());
				auditRecordService.saveRecord(record);
				model.addAttribute("userupdated", "User updated Successfully.");
			}
			List<User> useList = userProfileService.getUsersByCreated(id+"");
			List<User> usersList = new ArrayList<User>();
			if(useList != null){
				Iterator<User> iterator = useList.iterator();
				while(iterator.hasNext()){
					User u = iterator.next();
					if(u.getModifiedBy() != null){
						Integer i = Integer.parseInt(u.getModifiedBy());
						User us = userProfileService.getById(i);
						u.setCreatedName(us.getUserName());
					}else{
						Integer i = Integer.parseInt(u.getCreatedBy());
						User us = userProfileService.getById(i);
						u.setCreatedName(us.getUserName());
					}
					usersList.add(u);
				}
			}
			model.addAttribute("user_list",usersList);
			model.addAttribute("edituser", "edituser");
			model.addAttribute("crudObj", getEntityInstance());
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
    }
	/*
	 * This method saves the user which was created by the app admin
	 */
	@RequestMapping(value="/saveuser", method = RequestMethod.POST)
    public String save(@ModelAttribute("crudObj") @Valid User entity, BindingResult bindingResult, Model model,HttpServletRequest request) {  //NOPMD    	
	
		try {
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			User user = userProfileService.findByEmail(entity.getEmailAddress());
			User userName = userProfileService.findByUserName(entity.getUserName());
			if(userName != null){
				entity.setUserName(null);
				model.addAttribute("userexists","User Name already exists");
			}else if(user != null){
				entity.setEmailAddress(null);
				model.addAttribute("userexists","Email already exists");
			}else{
				
			try{
				//Generating the random secured password for the user
				char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
				StringBuilder sb = new StringBuilder();
				Random random = new Random();
				for (int i = 0; i < 10; i++) {
				    char c = chars[random.nextInt(chars.length)];
				    sb.append(c);
				}
			    	entity.setImportedFromLdap((byte) 0);
			    	entity.setIsPasswordChangeRequired((byte) 1);
			    	entity.setPassword(PasswordHashing.cryptWithMD5(sb.toString()));
			    	entity.setNda((byte)1);
			    	entity.setCreatedDate(dateInIndia);
					super.save(entity, bindingResult, model);
					model.addAttribute("crudObj", getEntityInstance());
					model.addAttribute("createuser", "createuser");
					model.addAttribute("saveuser", "saveuser");
					model.addAttribute("usercreated","User Created Successfully.");
					/*
					 * assign the user to the tenant
					 * 
					 */
					UserTenant userTenant = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
					UserTenant userTen = new UserTenant();
					UserTenantComp userTenantComp = new UserTenantComp();
					userTenantComp.setUserId(entity.getId());
					userTenantComp.setTenantId(userTenant.getUserTenantComp().getTenantId());
					userTen.setUserTenantComp(userTenantComp);
					userTen.setCreatedBy(entity.getCreatedBy());
					userTen.setCreatedDate(dateInIndia);
					userTenantService.save(userTen);
					/*
					 * preparing the audit trail record for the user
					 */
					AuditLogRecord record = new AuditLogRecord();
					//record.setActionDate(new java.util.Date());
					record.setActionDate(entity.getCreatedDate());
					record.setUserId((Integer)request.getSession().getAttribute("userid"));
					record.setUserName(request.getSession().getAttribute("username").toString());
					record.setActionPerformed("User created with name "+entity.getUserName());
					record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
					/*try {
						InetAddress inetAddress = InetAddress.getLocalHost();
						record.setIpOrigin(inetAddress.getHostAddress());
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}*/
					record.setActionType(AuditType.CREATE);
					record.setActionData("UserName is"+entity.getUserName());
					if(entity.getProjectsList() != null){
						addProjectToUser(entity);
					}
					if(entity.getTeamsList() != null){
						addUserToTeam(entity);
					}
					if(entity.getRolesList() != null){
						addRolesToUser(entity);
						entity.setPassword(sb.toString());
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("entity", entity);
						model.addAttribute("usercreated","User Created Successfully. Password Sent to Registered Email");
						mailService.sendMail("dev@infotreesolutions.com", entity.getEmailAddress(), "New user created in QlikTest Admin Account", map);
					}
					auditRecordService.saveRecord(record);
					//return result;
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			}
			HttpSession session = request.getSession();
			Integer id = (Integer)request.getSession().getAttribute("userid");
			UserTenant userTenant = userTenantService.findByUserId((Integer)session.getAttribute("userid"));
			model.addAttribute("project_list", projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
			List<Role> rolelist = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for (Role role : rolelist) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					if(!role.getName().equalsIgnoreCase("Application Administrator")){
						newRoleList.add(role);
					}
				}
			}
			model.addAttribute("available_roles",newRoleList);
			model.addAttribute("available_projects",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
			model.addAttribute("available_teams",teamService.getTeamsById(id+""));
			model.addAttribute("createuser", "createuser");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
    }
	/*
	 * This is a web request method which will return the all the roles in the form of list
	 */
	@ModelAttribute("role_list")
    public List<Role> roles(WebRequest request) {
		List<Role> newRoleList=null;
		try {
			List<Role> rolelist = roleService.list();
			newRoleList = new ArrayList<Role>();
			for (Role role : rolelist) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					if(!role.getName().equalsIgnoreCase("Application Administrator")){
						newRoleList.add(role);
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		} 
		return newRoleList;
    }
	/*
	 * This is a web request method which will return the teams list which are created by the particular user
	 */
	@ModelAttribute("team_list")
    public List<Team> teams(WebRequest request,HttpServletRequest req,HttpServletResponse res) {
		return teamService.getTeamsById((Integer)req.getSession().getAttribute("userid")+"");
    }
	/*
	 * This is a web request method which will return the users list which are created by the particular list
	 */
	@ModelAttribute("user_list")
    public List<User> users(WebRequest request,HttpServletRequest request1,HttpServletResponse response) {
		Integer id = (Integer)request1.getSession().getAttribute("userid");
		return userProfileService.getUsersByCreated(id+"");
    }
	/*
	 * This method is used to add the roles to the user
	 */
	private void addRolesToUser(User entity){
		try {
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			List<Integer> rolesList = entity.getRolesList();
			Iterator<Integer> iterator = rolesList.iterator();
			while(iterator.hasNext()){
				UserRole userrole = new UserRole();
				UserRoleComp userRoleComp = new UserRoleComp();
				userRoleComp.setRoleId(iterator.next());
				userRoleComp.setUserId(entity.getId());
				userrole.setUserRoleComp(userRoleComp);
				userrole.setCreatedBy(entity.getCreatedBy());
				userrole.setCreatedDate(dateInIndia);
				userRoleService.save(userrole);
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
	}
	/*
	 * This method is userd to assign the projects to the user
	 */
	private void addProjectToUser(User entity){
		try {
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			List<Integer> projectsList = entity.getProjectsList();
			Iterator<Integer> iterator = projectsList.iterator();
			while(iterator.hasNext()){
				UserProject userproject = new UserProject();
				UserProjectComp userProjectComp = new UserProjectComp();
				userProjectComp.setProjectId(iterator.next());
				userProjectComp.setUserId(entity.getId());
				userproject.setUserProjectComp(userProjectComp);
				userproject.setCreatedBy(entity.getCreatedBy());
				userproject.setModifiedBy(entity.getModifiedBy());
				userproject.setCreatedDate(dateInIndia);
				userproject.setModifiedDate(dateInIndia);
				userProjectService.save(userproject);
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
	}
	/*
	 * This method is used to add the user to the team
	 */
	private void addUserToTeam(User entity){
		try {
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			List<Integer> teamsList = entity.getTeamsList();
			Iterator<Integer> iterator = teamsList.iterator();
			while(iterator.hasNext()){
				UserTeam userteam = new UserTeam();
				UserTeamComp userTeamComp = new UserTeamComp();
				userTeamComp.setTeamId(iterator.next());
				userTeamComp.setUserId(entity.getId());
				userteam.setUserTeamComp(userTeamComp);
				userteam.setCreatedBy(entity.getCreatedBy());
				userteam.setModifiedBy(entity.getModifiedBy());
				userteam.setCreatedDate(dateInIndia);
				userteam.setModifiedDate(dateInIndia);
				userTeamService.save(userteam);
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
	}
	/*
	 * This method is used to save the tenant for the project
	 */
	@RequestMapping(value="/saveDefaultUserFromProject", method = RequestMethod.POST)
    public void saveDefaultUserFromProject(@ModelAttribute("curdObj") @Valid User entity, BindingResult bindingResult, Model model,HttpServletRequest request,HttpServletResponse response) {  //NOPMD
		PrintWriter out = null;
		try {
				out = response.getWriter();
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				String trTenants ="";
				String createdBy = request.getSession().getAttribute("userid").toString();
				entity.setCreatedBy(createdBy);
				Role role = roleService.findByName("Application Administrator");
				User user = userProfileService.findByUserName(request.getParameter("username"));
				User userMail = userProfileService.findByEmail(request.getParameter("emailid"));
				Tenant tenant = tenantService.findByName(request.getParameter("tenantname"));
				if(role == null){
					model.addAttribute("defaultusercreated","<font color='red'>Unable to create the tenant. Please create the role first</font>");
					model.addAttribute("roleName","");
				}else if(user != null){
					trTenants += "User Name Already Exist";
				}else if(tenant != null){
					
					
					trTenants += "Tenant Name Already Exist";
				
					
					String tenants = "";
					
						tenants += "<option label='"+tenant.getName()+"' value='"+tenant.getId()+"'>"+tenant.getName()+"</option>";
					//try {
					//	PrintWriter out = response.getWriter();
					//} catch (IOException e) {
						
					//}
					
					model.addAttribute("defaultusercreated","<font color='red'>Tenant Name already exists</font>");
				}else if(userMail != null){
					trTenants += "Mail Id Already Exist";
				}else{
					//save the tenant
					Tenant t = new Tenant();
					t.setName(request.getParameter("tenantname"));
					t.setCompanyName(request.getParameter("companyname"));
					t.setDisplayName(request.getParameter("displayname"));
					t.setCreatedBy(entity.getCreatedBy());
					//t.setCreatedBy(request.getSession().getAttribute("userid").toString());
					t.setCreatedDate(dateInIndia);
					Tenant savedTenant = tenantService.save(t);
					trTenants+="'"+t.getName()+"' Tenant Created Successfully.....:";
					trTenants += "<option label='"+t.getName()+"' value='"+t.getId()+"'>"+t.getName()+"</option> ";
					/*try {
						PrintWriter out = response.getWriter();
						out.println(trTenants);
						out.flush();
					} catch (IOException e) {
						
					}*/
					String tenants =  "<option label='"+t.getName()+"' value='"+t.getId()+"'>"+t.getName()+"</option>";
					//out.println(tenants);
					//out.flush();
					System.out.println("Written to Response 1 : "+tenants);
					
					//Generating the password for the user
					char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
					StringBuilder sb = new StringBuilder();
					Random random = new Random();
					for (int i = 0; i < 10; i++) {
					    char c = chars[random.nextInt(chars.length)];
					    sb.append(c);
					}
					
					
					//save the user
					User u = new User();
					u.setUserName(request.getParameter("username"));
					u.setFirstName(request.getParameter("firstname"));
					u.setSurName(request.getParameter("lastname"));
					u.setEmailAddress(request.getParameter("emailid"));
					u.setLandline(request.getParameter("landline"));
					u.setMobile(request.getParameter("mobile"));
					u.setPassword(PasswordHashing.cryptWithMD5(sb.toString()));
					u.setCreatedBy(entity.getCreatedBy());
					u.setCreatedDate(dateInIndia);
					u.setIsPasswordChangeRequired((byte) 1);
					u.setImportedFromLdap((byte) 0);
					u.setNda((byte)1);
					//setting the password to the user class to send it to the user
					entity.setPassword(sb.toString());
					entity.setUserName(request.getParameter("username"));
					entity.setFirstName(request.getParameter("firstname"));
					Role r = roleService.findByName("Application Administrator");
					
					//save the role to the user
					User savedUser = userProfileService.save(u);
					UserRole ur = new UserRole();
					UserRoleComp userRoleComp = new UserRoleComp();
					userRoleComp.setUserId(savedUser.getId());
					userRoleComp.setRoleId(r.getId());
					ur.setUserRoleComp(userRoleComp);
					ur.setCreatedBy(entity.getCreatedBy());
					ur.setCreatedDate(dateInIndia);
					userRoleService.save(ur);
					
					//Assign user id to the tenant id
					UserTenant ut = new UserTenant();
					UserTenantComp utComp = new UserTenantComp();
					utComp.setUserId(savedUser.getId());
					utComp.setTenantId(savedTenant.getId());
					ut.setUserTenantComp(utComp);
					ut.setCreatedBy(entity.getCreatedBy());
					ut.setCreatedDate(dateInIndia);
					userTenantService.save(ut);
					
					
					// Preparing the log statement
					
					AuditLogRecord record = new AuditLogRecord();
					//record.setActionDate(new java.util.Date());
					record.setActionDate(entity.getCreatedDate());
					record.setUserId((Integer)request.getSession().getAttribute("userid"));
					record.setUserName(request.getSession().getAttribute("username").toString());
					record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
					record.setActionData("Tenant Created With Name "+request.getParameter("tenantname"));
					record.setActionType(AuditType.CREATE);
					auditRecordService.saveRecord(record);
					
					//Reverting back to the UI
					model.addAttribute("crudObj",getEntityInstance());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("entity", entity);
					mailService.sendMail("admin@infotreesolutions.com", request.getParameter("emailid"), "New user created in QlikTest Admin Account", map);
				}
				/*Tenant tenant1 = tenantService.findByName(request.getParameter("tenantname"));
				String trTenants = "";
				if(tenant1 != null){
					
					trTenants += "Tenant Name Already Exist'";
					try {
						System.out.println("now in try");
						PrintWriter out = response.getWriter();
						out.println(trTenants);
						out.flush();
						System.out.println("now in try completed");
					} catch (IOException e) {
						
				}
					
					}*/
				
					out.println(trTenants);
					out.flush();
					
				model.addAttribute("crudObj",getEntityInstance());
				entity.setCompanyName(null);
				entity.setTenantName(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	
	public QTAdminService<User> getTService() {
		return userProfileService;
	}
	
	protected Validator getValidator() {
		return validator;
	}
}
