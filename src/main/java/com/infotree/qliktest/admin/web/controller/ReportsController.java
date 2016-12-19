package com.infotree.qliktest.admin.web.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.DashBoardReports;
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.entity.referencedata.ModulePermissions;
import com.infotree.qliktest.admin.entity.referencedata.ModuleReports;
import com.infotree.qliktest.admin.entity.referencedata.Permissions;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.ProjectModule;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.ProjectsReports;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.Team;
import com.infotree.qliktest.admin.entity.referencedata.TeamProject;
import com.infotree.qliktest.admin.entity.referencedata.TeamReports;
import com.infotree.qliktest.admin.entity.referencedata.Tenant;
import com.infotree.qliktest.admin.entity.referencedata.TenantReports;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;
import com.infotree.qliktest.admin.entity.referencedata.UserReports;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;
import com.infotree.qliktest.admin.entity.referencedata.UserTeam;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ModulePermissionsService;
import com.infotree.qliktest.admin.service.referencedata.ModuleService;
import com.infotree.qliktest.admin.service.referencedata.PermissionsService;
import com.infotree.qliktest.admin.service.referencedata.ProjectModuleService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.ProjectTenantService;
import com.infotree.qliktest.admin.service.referencedata.ReportsService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.TeamProjectService;
import com.infotree.qliktest.admin.service.referencedata.TeamService;
import com.infotree.qliktest.admin.service.referencedata.TenantService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserProjectService;
import com.infotree.qliktest.admin.service.referencedata.UserRoleService;
import com.infotree.qliktest.admin.service.referencedata.UserTeamService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;


@RequestMapping("/reports")
@Controller
public class ReportsController {
	private static final Logger LOG = LoggerFactory.getLogger(ReportsController.class);
	@Autowired
	private DoNothingValidator validator;
	
	
	
	@Autowired
	private ReportsService reportsService;
	
	
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ProjectModuleService projectModuleService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private ProjectTenantService projectTenantService;
	
	@Autowired
	private UserTeamService userTeamService;
	
	@Autowired
	private UserProjectService userProjectService;
	
	@Autowired
	private ModulePermissionsService modulePermissionsService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private PermissionsService permissionsService;
	
	@Autowired
	private AuditLogRecordService auditRecordService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserProfileService userService;
	
	
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TeamProjectService teamProjectService;

	/**
	 * This method returns the users related reports
	 * projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId())
	 */
	@RequestMapping(value="/userreports",method = RequestMethod.GET)
	public String userReports(Model model,HttpServletRequest request){
		try {
			model.addAttribute("crudObj", new UserReports());
			List<Project> projList = new ArrayList<Project>();
			List<UserReports> userReports = new ArrayList<UserReports>();
			List<User> userlist = userService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid"));
			if(userlist != null){
				Iterator<User> itr1 = userlist.iterator();
				try {
					while(itr1.hasNext()){
						User u = itr1.next();
						UserReports reports = new UserReports();
						reports.setFirstName(u.getFirstName());
						reports.setLastName(u.getSurName());
						if(u.getDisabled() == 0){
							reports.setActive("Yes");
						}else{
							reports.setActive("No");
						}
						if(u.getImportedFromLdap() == 0){
							reports.setImported("No");
						}else if(u.getImportedFromLdap() == 1){
							reports.setImported("Yes");
						}
						List<UserRole> role = userRoleService.getByUserId(u.getId());
						String userrole = "";
						if(role != null){
							Iterator<UserRole> itr = role.iterator();
							while(itr.hasNext()){
								UserRole userRole = itr.next();
								String r = roleService.getNameById(userRole.getUserRoleComp().getRoleId());
								if(userrole == ""){
									userrole = r;
								}else{
									userrole = userrole+","+r;
								}
							}
						}
						
						reports.setRole(userrole);
						List<UserProject> userProject = userProjectService.getByUserId(u.getId());
						String userprojects = "";
						if(userProject != null){
							Iterator<UserProject> userprojitr = userProject.iterator();
							
							while(userprojitr.hasNext()){
								UserProject projects = userprojitr.next();
								Project proj = projectService.getById(projects.getUserProjectComp().getProjectId());
								if(userprojects.equals("")){
									userprojects = proj.getName();
								}else{
									userprojects = userprojects+","+proj.getName();
								}
							}
						}
						
						reports.setProjects(userprojects);
						List<UserTeam> userTeamList = userTeamService.findByUserId(u.getId());
						String userTeamReport = "";
						if(userTeamList != null){
							Iterator<UserTeam> userTeamItr = userTeamList.iterator();
							while(userTeamItr.hasNext()){
								UserTeam userTeam = userTeamItr.next();
								Team team = teamService.getById(userTeam.getUserTeamComp().getTeamId());
								if(userTeamReport.equals("") && team !=null){
									userTeamReport = team.getName();
								}else if(team != null){
									userTeamReport = userTeamReport+"," + team.getName();
								}
							}
						}
						
						reports.setTeam(userTeamReport);
						userReports.add(reports);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
			model.addAttribute("user_list",userReports);
			List<UserProject> projects = userProjectService.getByUserId((Integer)request.getSession().getAttribute("userid"));
			if(projects != null){
				Iterator<UserProject> itr = projects.iterator();
				while(itr.hasNext()){
					UserProject userProj = itr.next();
					projList.add(projectService.getById(userProj.getUserProjectComp().getProjectId()));
				}
			}
			
			model.addAttribute("team_list",teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+""));
			List<Role> list = roleService.list();
			List<Role> rolelist = new ArrayList<Role>();
			if(list != null){
				Iterator<Role> itrRole = list.iterator();
				while(itrRole.hasNext()){
					Role r=itrRole.next();
					if(!r.getName().equalsIgnoreCase("System Administrator")){
						if(!r.getName().equalsIgnoreCase("Application Administrator")){
							rolelist.add(r);
						}
					}
				}
			}
			HttpSession session=request.getSession();
			Integer id=(Integer)session.getAttribute("userid");
			UserTenant userTenant = userTenantService.findByUserId(id);
			model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
			
			/*model.addAttribute("viewrole","viewrole");*/
			model.addAttribute("role_list",rolelist);
			//model.addAttribute("project_list",projectService.list());
			model.addAttribute("userreports","userreports");
			
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
			
			record.setActionData("Viewed the users reports");
			record.setActionType(AuditType.VIEW);
			record.setActionPerformed("Viewed the users reports");
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
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * This method returns the tenants related reports
	 */
	@RequestMapping(value="/tenantsreports",method = RequestMethod.GET)
	public String tenantReports(Model model,HttpServletRequest request){
		try {
			model.addAttribute("crudObj",new TenantReports());
			model.addAttribute("tenant_list",userService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
			List<TenantReports> tenantList = new ArrayList<TenantReports>();
			List<User> userList = userService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid"));
			if(userList != null){
				Iterator<User> itr = userList.iterator();
				
				while(itr.hasNext()){
					TenantReports tenantReports = new TenantReports();
					User u = itr.next();
					UserTenant userTenant = userTenantService.findByUserId(u.getId());
					Tenant t = tenantService.findByTenantId(userTenant.getUserTenantComp().getTenantId());
					
					tenantReports.setName(t.getName());
					tenantReports.setFirstName(u.getFirstName());
					tenantReports.setUserName(u.getUserName());
					tenantReports.setCreatedDate(t.getCreatedDate());
					tenantReports.setModifiedDate(t.getModifiedDate());
					
					String projects = "";
					UserTenant userTenants = userTenantService.findByUserId(u.getId());
					List<ProjectTenant> tenantProjList = projectTenantService.getByTenantId(userTenants.getUserTenantComp().getTenantId());
					if(tenantProjList != null){
						Iterator<ProjectTenant> itrerator = tenantProjList.iterator();
						while(itrerator.hasNext()){
							ProjectTenant proj = itrerator.next();
							Project p = projectService.getById(proj.getProjectTenantComp().getProjectId());
							if(projects.equals("")){
								projects = p.getName();
							}else{
								projects = projects+","+p.getName();
							}
						}
					}
					
					tenantReports.setProjects(projects);
					tenantReports.setEmail(u.getEmailAddress());
					tenantReports.setLastName(u.getSurName());
					tenantReports.setMobile(u.getMobile());
					tenantReports.setLandLine(u.getLandline());
					tenantList.add(tenantReports);
				}
			}
			
			model.addAttribute("tenant_list", tenantList);
			model.addAttribute("tenantname_list", tenantService.list());
			model.addAttribute("project_list", projectService.getActiveProjects());
			model.addAttribute("tenantsreports","tenantsreports");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the teams related reports
	 */
	@RequestMapping(value="/teamsreports",method = RequestMethod.GET)
	public String teamsReports(Model model,HttpServletRequest request){
		try {
			model.addAttribute("crudObj",new TeamReports());
			List<Project> projList = new ArrayList<Project>(); 
			List<User> listusers = new ArrayList<User>();
			List<TeamReports> teamReports = new ArrayList<TeamReports>();
			List<Team> teamsList = teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+"");
			if(teamsList != null){
				Iterator<Team> teamsItr = teamsList.iterator();
				while(teamsItr.hasNext()){
					
					String users = "";
					String projects = "";
					TeamReports teams = new TeamReports();
					Team team = teamsItr.next();
					teams.setTeamName(team.getName());
					int count=userTeamService.getNoOfUsers(team.getId());
					if(count==0){
						teams.setTeamCount(count);
						users="No Users Available";
					}else{
						teams.setTeamCount(userTeamService.getNoOfUsers(team.getId()));
					}
					if(!(count==0)){
						listusers = userService.getUsersByTeamId(team.getId());
						Iterator<User> iter=listusers.iterator();
						while(iter.hasNext()){
							User u=iter.next();
							if(users=="")
								users=users+u.getFirstName();
							else
							users=users+" "+"<br>"+u.getFirstName();
						}
					}
					List<TeamProject> projectList = teamProjectService.getProjectsByTeamId(team.getId());
					if(projectList != null){
						Iterator<TeamProject> itr = projectList.iterator();
						
						while(itr.hasNext()){
							TeamProject teamproj = itr.next();
							Project p = projectService.getById(teamproj.getTeamProjId().getProjectId());
							if(projects.equals("")){
								projects = projects+p.getName();
							}else{
								projects = projects+","+p.getName();
							}
							
						}
					}
					
					
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
					
					
					record.setActionData("Viewed the Teams Reports");
					record.setActionType(AuditType.VIEW);
					record.setActionPerformed("Viewed the Teams Reports");
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
					
					teams.setProjects(projects);
					teams.setUserName("<center><table width=70% border=1><tr><th><center>User Names</center></th></tr><tr><td><center>"+users+"</center></td></tr></table></center>");
					teamReports.add(teams);
					
				}
			}
			
			List<UserProject> projectList = userProjectService.getByUserId((Integer)request.getSession().getAttribute("userid"));
			
			if(projectList != null){
				Iterator<UserProject> itr = projectList.iterator();
				while(itr.hasNext()){
					UserProject userProj = itr.next();
					projList.add(projectService.getById(userProj.getUserProjectComp().getProjectId()));
				}
			}
			
			List<Role> rolelist = new ArrayList<Role>();
			List<Role> list = roleService.list();
			if(list != null){
				Iterator<Role> itrRole = list.iterator();
				while(itrRole.hasNext()){
					Role r=itrRole.next();
					Role r1 = new Role();
					r1.setId(r.getId());
					r1.setDescription(r.getDescription());
					r1.setCreatedBy("1");
					r1.setName(r.getName());
					rolelist.add(r1);
				
				}
			}
			
			List<Project> projs = new ArrayList<Project>();
			UserTenant ut = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
			List<ProjectTenant> projTenant = projectTenantService.getByTenantId(ut.getUserTenantComp().getTenantId());
			if(projTenant != null){
				Iterator<ProjectTenant> projTenantItr = projTenant.iterator();
				while(projTenantItr.hasNext()){
					ProjectTenant pt = projTenantItr.next();
					Project p = projectService.getById(pt.getProjectTenantComp().getProjectId());
					projs.add(p);
				}
			}
			
			model.addAttribute("project_list",projs);
			
			List<Role> rolesList = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for (Role role : rolesList) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					if(!role.getName().equalsIgnoreCase("Application Administrator")){
						newRoleList.add(role);
					}
					
				}
			} 
			model.addAttribute("role_list",newRoleList);
			model.addAttribute("team_list",teamReports);
			
			model.addAttribute("teamsreports","teamsreports");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the projects related reports
	 */
	@RequestMapping(value="/projectsreports",method = RequestMethod.GET)
	public String projectsReports(Model model){
		
		try {
			List<Project> projectList = projectService.getActiveProjects();
			List<ProjectsReports> projReports = new ArrayList<ProjectsReports>();
			if(projectList != null){
				Iterator<Project> itr = projectList.iterator();
				while(itr.hasNext()){
					String modules = "";
					ProjectsReports report = new ProjectsReports();
					Project p = itr.next();
					int count = projectModuleService.getModuleCountByProjectId(p.getId());
					report.setName(p.getName());
					SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd");
					report.setStartingDate(formatt.format(p.getStartDate()));
					report.setEndingDate(formatt.format(p.getEndDate()));
					report.setStartDate(p.getStartDate());
					report.setEndDate(p.getEndDate());
					report.setModuleCount(count);
					report.setCreatedDate(p.getCreatedDate());
					report.setModifiedDate(p.getModifiedDate());
					List<ProjectModule> projectsList = projectModuleService.findByProjectId(p.getId());
					if(projectsList != null){
						Iterator<ProjectModule> iterator = projectsList.iterator();
						if(count==0){
							
							modules="No Modules Available";
							report.setModuleName(modules);
						}
						else if(count !=0){
								
								while(iterator.hasNext()){
									ProjectModule pm = iterator.next();
									Module m = moduleService.getById(pm.getProjectModuleComp().getModuleId());
									if(modules == ""){
										modules = modules+m.getName();
										//report.setModuleName(modules);
										report.setModuleName("<center><table width=70% border=1><tr><th><center>License Names</center></th></tr><tr><td><center>"+modules+"</center></td></tr></table></center>");
									}
									else{
										modules = modules+" "+"<br>"+m.getName();
										report.setModuleName("<center><table width=70% border=1><tr><th><center>License Names</center></th></tr><tr><td><center>"+modules+"</center></td></tr></table></center>");
									}
								}
							}
					}
					projReports.add(report);
				}
			}
			
				
			
			model.addAttribute("crudObj",new ProjectsReports());
			model.addAttribute("module_list", moduleService.list());
			model.addAttribute("project_list",projReports);
			model.addAttribute("projectname_list", projectService.getActiveProjects());
			model.addAttribute("projectsreports","projectsreports");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the modules related reports
	 */
	@RequestMapping(value="/modulereports",method = RequestMethod.GET)
	public String moduleReports(Model model){
		
		try {
			List<ModuleReports> moduleReports = new ArrayList<ModuleReports>();
			model.addAttribute("crudObj",new ModuleReports());
			List<Module> moduleList = moduleService.list();
			if(moduleList != null){
				Iterator<Module> itr = moduleList.iterator();
				while(itr.hasNext()){
					
					String permissions = "";
					Module module = itr.next();
					ModuleReports report = new ModuleReports();
					int count = modulePermissionsService.getPermissionsCountByModuleId(module.getId());
					
					report.setName(module.getName());
					report.setDescription(module.getDescription());
					report.setPermissionsCount(count+"");
					report.setCreatedDate(module.getCreatedDate());
					report.setModifiedDate(module.getModifiedDate());
					List<ModulePermissions> permList = modulePermissionsService.findByModuleId(module.getId());
					if(permList != null){
						Iterator<ModulePermissions> iterator = permList.iterator();
						
						if(count==0){
							
							permissions="No Permissions Available";
						}
						else {
							while(iterator.hasNext()){
								ModulePermissions pm = iterator.next();
								
								Permissions m = permissionsService.getById(pm.getModulePermissionsComp().getPermissionId());
								if(permissions == "")
									permissions = m.getName();
								else
									permissions = permissions+" "+"<br>"+m.getName();
							}
						}
					}
					
					report.setPermissionsNames("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
					moduleReports.add(report);
				}
			}
			
			model.addAttribute("modulereports", "modulereports");
			model.addAttribute("module_list", moduleReports);
			model.addAttribute("modulename_list", moduleService.list());
			model.addAttribute("permissions_list",permissionsService.list());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}	
	
/**
 * This method is used to search the users based on the filters selected by the user
 */
@RequestMapping(value = "/search",method = RequestMethod.POST)
public String searchUsers(@Valid UserReports entity,BindingResult bindingResult,Model model,HttpServletRequest request){
try {
		
		Integer userId = (Integer)request.getSession().getAttribute("userid");
		model.addAttribute("crudObj", new UserReports());
		String projid = entity.getProjectId();
		String teamid = entity.getTeamId();
		String roleId = entity.getRoleId();
		
		int isImported = 2;
		int isActive = 2;
		
		
		//********************************//*
		
		 final  HashMap<String,String> map = new HashMap<String,String>();
		 
		 
		
		if(entity.getImported() != null && entity.getImported().equalsIgnoreCase("yes")){
			isImported = 1;
			
			map.put("imported_from_ldap", String.valueOf(isImported));
		}else if(entity.getImported() != null && entity.getImported().equalsIgnoreCase("no")){
			isImported = 0;
			map.put("imported_from_ldap", String.valueOf(isImported));
		}

		
		if(entity.getActive() != null && entity.getActive().equalsIgnoreCase("yes")){
			isActive = 0;
			map.put("is_disabled", String.valueOf(isActive));
		}else if(entity.getActive() != null && entity.getActive().equalsIgnoreCase("no")){
			isActive = 1;
			map.put("is_disabled", String.valueOf(isActive));
		}
		
		if(projid!=null && projid!="")
		{
			map.put("project_id", projid);
		}
		
		if(teamid!=null && teamid!="")
		{
			map.put("team_id", teamid);
		}
		
		
		if(roleId!=null && roleId!="")
		{
			map.put("role_id", roleId);
		}
		
		
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("select * from user where ");
		 Iterator<Entry<String, String>> it = map.entrySet().iterator();
		  int i=1;
		  while (it.hasNext()) {
		        @SuppressWarnings("rawtypes")
				Map.Entry pairs = (Map.Entry)it.next();
		       
		         
		        if(i>1){
		        	sb.append(" and ");
		        }
		        
		        if(pairs.getKey().toString().equalsIgnoreCase("imported_from_ldap")){
		        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
		        }
		        
		       
		        if(pairs.getKey().toString().equalsIgnoreCase("is_disabled")){
		        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
		        }
		        
		        if(pairs.getKey().toString().equalsIgnoreCase("project_id")){
		        	sb.append("id in (select user_id from user_project where ");
		        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
		        	sb.append(")");
		        }
		        
		        
		        if(pairs.getKey().toString().equalsIgnoreCase("team_id")){
		        	sb.append("id in (select user_id from user_team where ");
		        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
		        	sb.append(")");
		        }
		        
		        if(pairs.getKey().toString().equalsIgnoreCase("role_id")){
		        	sb.append("id in (select user_id from user_role where ");
		        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
		        	sb.append(")");
		        }
		        
		       
		       
		        
		        i++;
		        
		    }
		sb.append(" and created_by="+userId);
		List<User> userList = userService.executeSqlQuery(sb.toString());
		List<UserReports> userReports = new ArrayList<UserReports>();
		List<Project> projList = new ArrayList<Project>();
		if(projList != null){
			Iterator<User> itr1 = userList.iterator();
			
			while(itr1.hasNext()){
				String userprojects = "";
				String userrole = "";
				
				String userTeamReport = "";
				User u = itr1.next();
				UserReports reports = new UserReports();
				reports.setFirstName(u.getFirstName());
				reports.setLastName(u.getSurName());
				if(u.isDisabled()){
					reports.setActive("No");
				}else{
					reports.setActive("Yes");
				}
				if(u.getImportedFromLdap() == 0){
					reports.setImported("No");
				}else{
					reports.setImported("Yes");
				}
				List<UserRole> role = userRoleService.getByUserId(u.getId());
				if(role != null){
					Iterator<UserRole> itr = role.iterator();
					while(itr.hasNext()){
						UserRole userRole = itr.next();
						String r = roleService.getNameById(userRole.getUserRoleComp().getRoleId());
						if(userrole == ""){
							userrole = r;
						}else{
							userrole = userrole+","+r;
						}
					}
				}
				
				reports.setRole(userrole);
				userrole = "";
				List<UserProject> userProject = userProjectService.getByUserId(u.getId());
				if(userProject != null){
					Iterator<UserProject> userprojitr = userProject.iterator();
					while(userprojitr.hasNext()){
						UserProject projects = userprojitr.next();
						Project proj = projectService.getById(projects.getUserProjectComp().getProjectId());
						if(userprojects.equals("")){
							userprojects = proj.getName();
						}else{
							userprojects = userprojects+","+proj.getName();
						}
					}
				}
				
				reports.setProjects(userprojects);
				userprojects = "";
				List<UserTeam> userTeamList = userTeamService.findByUserId(u.getId());
				if(userTeamList != null){
					Iterator<UserTeam> userTeamItr = userTeamList.iterator();
					while(userTeamItr.hasNext()){
						UserTeam userTeam = userTeamItr.next();
						Team team = teamService.getById(userTeam.getUserTeamComp().getTeamId());
						if(userTeamReport.equals("")){
							userTeamReport = team.getName();
						}else{
							userTeamReport = userTeamReport+"," + team.getName();
						}
					}
				}
				
				reports.setTeam(userTeamReport);
				userTeamReport = "";
				userReports.add(reports);
			}
		}
		
		
		
		model.addAttribute("user_list",userReports);
		List<UserProject> projects = userProjectService.getByUserId((Integer)request.getSession().getAttribute("userid"));
		if(projects != null){
			Iterator<UserProject> itr = projects.iterator();
			while(itr.hasNext()){
				UserProject userProj = itr.next();
				projList.add(projectService.getById(userProj.getUserProjectComp().getProjectId()));
			}
		}
		model.addAttribute("project_list",projList);
		model.addAttribute("team_list",teamService.getTeamsById((Integer)request.getSession().getAttribute("userid")+""));
		List<Role> list = roleService.list();
		List<Role> rolelist = new ArrayList<Role>();
		for(Role r:list){
			if(!r.getName().equalsIgnoreCase("System Administrator")){
				if(!r.getName().equalsIgnoreCase("Application Administrator")){
					rolelist.add(r);
				}
			}
			
		}
		model.addAttribute("crudObj",entity);
		model.addAttribute("viewrole","viewrole");
		model.addAttribute("role_list",rolelist);
		//model.addAttribute("project_list",projList);
		HttpSession session=request.getSession();
		Integer id=(Integer)session.getAttribute("userid");
		UserTenant userTenant = userTenantService.findByUserId(id);
		model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
		//model.addAttribute("project_list", projectService.list());
		model.addAttribute("userreports","userreports");
	} catch (Exception e) {
		LOG.error(e.toString());
		e.printStackTrace();
	}
	
	return "home";
}
	/**
	 * This method is used to search the projects based on the filters given by the user
	 */
	@RequestMapping(value = "/searchprojects", method = RequestMethod.POST)
	public String searchProjects(@ModelAttribute("crudObj") @Valid ProjectsReports entity,BindingResult bindingResult,Model model){
		try {
			String modules = "";
			Map<String,String> map = new HashMap<String,String>();
			String moduleId = entity.getModuleId();
			
			//new code
			
			if(moduleId!=null && moduleId!="")
			{
				map.put("module_Id", moduleId);
			}
			
			if(entity.getStartingDate()!=null && entity.getStartingDate() !="")
			{
				map.put("start_date", entity.getStartingDate());
			}
			
			if(entity.getEndingDate()!=null && entity.getEndingDate() !="")
			{
				map.put("end_date", entity.getEndingDate());
			}
			if(entity.getProjectId() != null && entity.getProjectId() != ""){
				map.put("id", entity.getProjectId());
			}
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("select * from project where ");
			 Iterator<Entry<String, String>> it = map.entrySet().iterator();
			  int i=1;
			  while (it.hasNext()) {
			        @SuppressWarnings("rawtypes")
					Map.Entry pairs = (Map.Entry)it.next();
			       
			        if(i>1){
			        	sb.append(" or ");
			        }
			        
			        if(pairs.getKey().toString().equalsIgnoreCase("module_id")){
			        	sb.append("id in (select project_id from project_module where ");
			        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
			        	sb.append(")");
			        }
			        
			        if(pairs.getKey().toString().equalsIgnoreCase("start_date")){
			        	sb.append(pairs.getKey().toString()+">='"+pairs.getValue().toString()+"'");
			        }
			        
			        if(pairs.getKey().toString().equalsIgnoreCase("end_date")){
			        	sb.append(pairs.getKey().toString()+"<='"+pairs.getValue().toString()+"'");
			        }
			        if(pairs.getKey().toString().equalsIgnoreCase("id")){
			        	sb.append(pairs.getKey().toString()+"='"+pairs.getValue().toString()+"'");
			        }
			        
			        i++;
			    }
			
			
			List<Project> projectList = projectService.executeQuery(sb.toString());
			List<ProjectsReports> projReports = new ArrayList<ProjectsReports>();
			if(projectList != null){
				Iterator<Project> itr = projectList.iterator();
				
				while(itr.hasNext()){
					
					modules = "";
					ProjectsReports report = new ProjectsReports();
					Project p = itr.next();
					int count = projectModuleService.getModuleCountByProjectId(p.getId());
					report.setName(p.getName());
					SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd");
					report.setStartingDate(formatt.format(p.getStartDate()));
					report.setEndingDate(formatt.format(p.getEndDate()));
					report.setStartDate(p.getStartDate());
					report.setEndDate(p.getEndDate());
					report.setModuleCount(count);
					report.setCreatedDate(p.getCreatedDate());
					report.setModifiedDate(p.getModifiedDate());
					List<ProjectModule> projectsList = projectModuleService.findByProjectId(p.getId());
					if(projectsList != null){
						Iterator<ProjectModule> iterator = projectsList.iterator();
						if(count==0){
							
							modules="No Modules Available";
						}
						else {
								while(iterator.hasNext()){
									ProjectModule pm = iterator.next();
									Module m = moduleService.getById(pm.getProjectModuleComp().getModuleId());
									if(modules == "")
										modules = modules+m.getName();
									else
										modules = modules+" "+"<br>"+m.getName();
								}
							}
					}
					
					//report.setModuleName(modules);
					report.setModuleName("<center><table width=70% border=1><tr><th><center>License Names</center></th></tr><tr><td><center>"+modules+"</center></td></tr></table></center>");
					projReports.add(report);
					}
			}
			
				
			
			model.addAttribute("module_list", moduleService.list());
			model.addAttribute("project_list",projReports);
			model.addAttribute("projectname_list", projectService.getActiveProjects());
			model.addAttribute("projectsreports","projectsreports");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		entity.setStartingDate(entity.getStartingDate());
		entity.setEndingDate(entity.getEndingDate());
		entity.setModuleName(entity.getName());
		return "home";
	}
	
	/**
	 * This method is used to search the teams based on the filters selected by the user
	 */
	@RequestMapping(value = "/searchteams", method = RequestMethod.POST)
	public String searchTeams(@Valid TeamReports entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			String roleid=entity.getRoleId();
			String projectid = entity.getProjectid();
			String sqlQuery = "";
			String users = "";
			String projects = "";
			List<User> listusers = new ArrayList<User>();
			List<TeamReports> teamReports = new ArrayList<TeamReports>();
			String userId = request.getSession().getAttribute("userid").toString();
			
			if((projectid!=null || projectid!="") && (roleid == null || roleid == ""))
			{
				sqlQuery="select * from team where id in (select team_id from team_project where project_id="+projectid+") and created_by = "+userId+"";
			}
			
			
			if((roleid!=null && roleid!="") && (projectid == null || projectid == ""))
			{
			sqlQuery="select * from team where id in (select team_id from user_team where user_id in (select user_id from user_role where role_id="+roleid+") and created_by = "+userId+")";
			}

			if(projectid!=null && projectid!="" && roleid!=null && roleid!="")
			{
			
			sqlQuery="select * from team where id in (select team_id from user_team where user_id in (select up.user_id from user_project up ,user_role ur where up.user_id=ur.user_id and up.project_id="+projectid+" and ur.role_id="+roleid+") and created_by = "+userId+")";
			

			}
			List<Team> teamList = teamService.executeSqlQuery(sqlQuery);
			if(teamList != null){
				Iterator<Team> teamsItr = teamList.iterator();
				while(teamsItr.hasNext()){
					TeamReports teams = new TeamReports();
					Team team = teamsItr.next();
					teams.setTeamName(team.getName());
					int count=userTeamService.getNoOfUsers(team.getId());
					if(count==0){
						teams.setTeamCount(count);
						users="No Users Available";
					}else{
						teams.setTeamCount(userTeamService.getNoOfUsers(team.getId()));
					}
					if(!(count==0)){
						listusers = userService.getUsersByTeamId(team.getId());
						Iterator<User> iter=listusers.iterator();
						users = "";
						while(iter.hasNext()){
							User u=iter.next();
							if(users=="")
								users=users+u.getFirstName();
							else
							users=users+" "+"<br>"+u.getFirstName();
						}
					}
					List<TeamProject> projectList = teamProjectService.getProjectsByTeamId(team.getId());
					if(projectList != null){
						Iterator<TeamProject> itr = projectList.iterator();
						
						while(itr.hasNext()){
							TeamProject teamproj = itr.next();
							Project p = projectService.getById(teamproj.getTeamProjId().getProjectId());
							if(projects.equals("")){
								projects = projects+p.getName();
							}else{
								projects = p.getName();
							}
							
						}
					}
					teams.setProjects(projects);
					//teams.setUserName(users);
					teams.setUserName("<center><table width=70% border=1><tr><th><center>User Names</center></th></tr><tr><td><center>"+users+"</center></td></tr></table></center>");
					teamReports.add(teams);
				
				}
			}
			
			List<Project> projs = new ArrayList<Project>();
			UserTenant ut = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
			List<ProjectTenant> projTenant = projectTenantService.getByTenantId(ut.getUserTenantComp().getTenantId());
			if(projTenant != null){
				Iterator<ProjectTenant> projTenantItr = projTenant.iterator();
				while(projTenantItr.hasNext()){
					ProjectTenant pt = projTenantItr.next();
					Project p = projectService.getById(pt.getProjectTenantComp().getProjectId());
					projs.add(p);
				}
			}
			
			model.addAttribute("project_list",projs);
			List<Role> rolelist = new ArrayList<Role>();
			List<Role> list = roleService.list();
			if(list != null){
				Iterator<Role> itrRole = list.iterator();
				while(itrRole.hasNext()){
					Role r=itrRole.next();
					Role r1 = new Role();
					r1.setId(r.getId());
					r1.setDescription(r.getDescription());
					r1.setCreatedBy("SYSADMIN");
					r1.setName(r.getName());
					rolelist.add(r1);
				
				} 
			}
			
			List<Role> rolesList = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for (Role role : rolesList) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					if(!role.getName().equalsIgnoreCase("Application Administrator")){
						newRoleList.add(role);
					}
					
				}
			} 
					
			model.addAttribute("crudObj",new TeamReports());
			model.addAttribute("role_list",newRoleList);
			model.addAttribute("team_list",teamReports);
			model.addAttribute("teamsreports","teamsreports");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * searching the modules based on the filters given by the user
	 */
	@RequestMapping(value = "/searchmodules", method = RequestMethod.POST)
	public String searchModules(@Valid ModuleReports entity,BindingResult bindingResult,Model model){
		try{
			Integer moduleId = entity.getModuleId();
			Integer permissionId = entity.getPermissionId();
			List<Module> modList = null;
			model.addAttribute("crudObj",entity);
			
			if(moduleId != null && permissionId != null){
				modList = moduleService.findByModuleIdAndPermissionId(moduleId, permissionId);
			}else if(moduleId != null && permissionId == null){
				modList = moduleService.findByModuleId(moduleId);
			}else if(moduleId == null && permissionId != null){
				modList = moduleService.findByPermissionId(permissionId);
			}else{
				modList = moduleService.list();
			}
				List<ModuleReports> moduleReports = new ArrayList<ModuleReports>();
				model.addAttribute("modulereports", "modulereports");
				if(modList != null){
					Iterator<Module> iterator = modList.iterator();
					while(iterator.hasNext()){
						Module module = iterator.next();
						String permissions = "";
						ModuleReports report = new ModuleReports();
						int count = modulePermissionsService.getPermissionsCountByModuleId(module.getId());
						report.setName(module.getName());
						report.setDescription(module.getDescription());
						report.setPermissionsCount(count+"");
						report.setCreatedDate(module.getCreatedDate());
						report.setModifiedDate(module.getModifiedDate());
						List<ModulePermissions> permList = modulePermissionsService.findByModuleId(module.getId());
						if(permList != null){
							Iterator<ModulePermissions> itr = permList.iterator();
							if(count==0){
								
								permissions="No Permissions Available";
							}
							if(!(count==0)){
								
								while(itr.hasNext()){
									ModulePermissions pm = itr.next();
									Permissions m = permissionsService.getById(pm.getModulePermissionsComp().getPermissionId());
									if(permissions == "")
										permissions = permissions+m.getName();
									else
										permissions = permissions+" "+"<br>"+m.getName();
								}
							}
						}
						report.setPermissionsNames(permissions);
						moduleReports.add(report);
					}
				}
				model.addAttribute("modulereports", "modulereports");
				model.addAttribute("module_list", moduleReports);
				model.addAttribute("permissions_list",permissionsService.list());
				model.addAttribute("modulename_list", moduleService.list());
		
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method is used to search the tenants based on the filters given by the user
	 */
	@RequestMapping(value="/searchtenants",method = RequestMethod.POST)
	public String searchTenants(@Valid TenantReports entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			Integer projectId = entity.getProjectId();
			Integer tenantId = entity.getId();
			List<TenantReports> tenantList = new ArrayList<TenantReports>();
			if(tenantId == null && projectId == null){
				List<User> userList = userService.getUsersByCreated((Integer)request.getSession().getAttribute("userid")+"");
				if(userList != null){
					Iterator<User> itr = userList.iterator();
					while(itr.hasNext()){
						TenantReports reports = new TenantReports();
						User u = itr.next();
						UserTenant ut = userTenantService.findByUserId(u.getId());
						reports.setUserName(u.getUserName());
						reports.setFirstName(u.getFirstName());
						reports.setLastName(u.getSurName());
						reports.setEmail(u.getEmailAddress());
						reports.setMobile(u.getMobile());
						reports.setLandLine(u.getLandline());
						Tenant t = tenantService.findByTenantIdAndCreatedBy(ut.getUserTenantComp().getTenantId(), (Integer)request.getSession().getAttribute("userid"));
						reports.setCreatedDate(t.getCreatedDate());
						reports.setModifiedDate(t.getModifiedDate());
						reports.setName(t.getName());
						List<ProjectTenant> list = projectTenantService.getByTenantId(t.getId());
						String projects = "";
						if(list != null){
							Iterator<ProjectTenant> iterator = list.iterator();
							while(iterator.hasNext()){
								ProjectTenant pt = iterator.next();
								Project p = projectService.getById(pt.getProjectTenantComp().getProjectId());
								if(projects == ""){
									projects = p.getName();
								}else{
									projects = projects+","+p.getName();
								}
								
							}
						}
						
						reports.setProjects(projects);
						tenantList.add(reports);
						model.addAttribute("tenant_list", tenantList);
						model.addAttribute("tenantname_list", tenantService.list());
						model.addAttribute("project_list", projectService.getActiveProjects());
					
					}
				}
				
				}else if(projectId == null && tenantId != null){
					UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(tenantId, (Integer)request.getSession().getAttribute("userid"));
					if(userTenant != null){
						TenantReports reports = new TenantReports();
						User u = userService.getById(userTenant.getUserTenantComp().getUserId());
						UserTenant ut = userTenantService.findByUserId(u.getId());
						reports.setUserName(u.getUserName());
						reports.setFirstName(u.getFirstName());
						reports.setLastName(u.getSurName());
						reports.setEmail(u.getEmailAddress());
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						reports.setName(t.getName());
						List<ProjectTenant> list = projectTenantService.getByTenantId(t.getId());
						String projects = "";
						if(list != null){
							Iterator<ProjectTenant> itr = list.iterator();
							while(itr.hasNext()){
								ProjectTenant pt = itr.next();
								Project p = projectService.getById(pt.getProjectTenantComp().getProjectId());
								if(projects == ""){
									projects = p.getName();
								}else{
									projects = projects+","+p.getName();
								}
								
							}
						}
						
						reports.setProjects(projects);
						tenantList.add(reports);
					}
						
						model.addAttribute("tenant_list", tenantList);
						model.addAttribute("tenantname_list", tenantService.list());
						model.addAttribute("project_list", projectService.getActiveProjects());
					
					
					
				}else if(projectId != null && tenantId == null){
					
					ProjectTenant projectTenant = projectTenantService.findByProjectId(projectId);
					if(projectTenant != null){
						UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(projectTenant.getProjectTenantComp().getTenantId(), (Integer)request.getSession().getAttribute("userid"));
						String projects = "";
						TenantReports reports = new TenantReports();
						User u = userService.getById(userTenant.getUserTenantComp().getUserId());
						UserTenant ut = userTenantService.findByUserId(u.getId());
						reports.setUserName(u.getUserName());
						reports.setFirstName(u.getFirstName());
						reports.setLastName(u.getSurName());
						reports.setEmail(u.getEmailAddress());
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						reports.setName(t.getName());
						List<ProjectTenant> list = projectTenantService.getByTenantId(t.getId());
						if(list != null){
							Iterator<ProjectTenant> itr = list.iterator();
							while(itr.hasNext()){
								ProjectTenant pt = itr.next();
								Project p = projectService.getById(pt.getProjectTenantComp().getProjectId());
								if(projects == ""){
									projects = p.getName();
								}else{
									projects = projects+" "+"<br>"+p.getName();
								}
								
							}
						}
						
						reports.setProjects(projects);
						tenantList.add(reports);
					}
					
					model.addAttribute("tenant_list", tenantList);
					model.addAttribute("tenantname_list", tenantService.list());
					model.addAttribute("project_list", projectService.getActiveProjects());
				
					
				}else if(projectId != null && tenantId != null){
					ProjectTenant projectTenant = projectTenantService.findByIds(projectId, tenantId);
					if(projectTenant != null){
						UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(tenantId, (Integer)request.getSession().getAttribute("userid"));
						TenantReports reports = new TenantReports();
						User u = userService.getById(userTenant.getUserTenantComp().getUserId());
						UserTenant ut = userTenantService.findByUserId(u.getId());
						reports.setUserName(u.getUserName());
						reports.setFirstName(u.getFirstName());
						reports.setLastName(u.getSurName());
						reports.setEmail(u.getEmailAddress());
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						reports.setName(t.getName());
						List<ProjectTenant> list = projectTenantService.getByTenantId(t.getId());
						String projects = "";
						if(list != null){
							Iterator<ProjectTenant> itr = list.iterator();
							while(itr.hasNext()){
								ProjectTenant pt = itr.next();
								Project p = projectService.getById(pt.getProjectTenantComp().getProjectId());
								if(projects == ""){
									projects = p.getName();
								}else{
									projects = projects+","+p.getName();
								}
								
							}
						}
						reports.setProjects(projects);
						tenantList.add(reports);
					}
					
					model.addAttribute("tenant_list", tenantList);
					model.addAttribute("tenantname_list", tenantService.list());
					model.addAttribute("project_list", projectService.getActiveProjects());
				}
			
				
			
			model.addAttribute("crudObj",entity);
			model.addAttribute("tenantsreports", "tenantsreports");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to create the other reports
	 */
	@RequestMapping(value = "/otherreports", method = RequestMethod.GET)
	public String otherreports(Model model,HttpServletResponse response, HttpServletRequest request) {
		try {
			model.addAttribute("crudObj",new DashBoardReports());
			model.addAttribute("otherreports", "otherreports");
			model.addAttribute("other_reports", reportsService.getOtherReports());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to edit the other reports
	 */
	@RequestMapping(value = "/editotherreports", method = RequestMethod.GET)
	public String editotherreports(@Valid DashBoardReports entity,BindingResult bindingResult,Model model,HttpServletResponse response, HttpServletRequest request) {
		try {
			Integer repid = Integer.parseInt(request.getParameter("repid"));
			DashBoardReports entityToBeUpdated = reportsService.getById(repid);
			model.addAttribute("crudObj",entityToBeUpdated);
			model.addAttribute("updatereports","updatereports");
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the report screen to edit the report
	 */
	@RequestMapping(value="/edit/{reportId}/update", method=RequestMethod.GET)
	public String updateReport(@PathVariable Integer reportId, Model model) {
		try {
			DashBoardReports entityToBeUpdated = reportsService.getById(reportId);
			model.addAttribute("crudObj", entityToBeUpdated);
			model.addAttribute("idToBeUpdated", entityToBeUpdated.getId());
			model.addAttribute("updatemodules", "updatemodules");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	
	return "home";
	}
	/**
	 * This method is used to update the other reports
	 */
	@RequestMapping(value = "/updatereports", method = RequestMethod.POST)
    public String updateReport(@ModelAttribute("crudObj") @Valid DashBoardReports entity, BindingResult bindingResult, Model model,HttpServletRequest request) {
		
		try {
			reportsService.updateReports(entity);
			model.addAttribute("otherreports", "otherreports");
			model.addAttribute("other_reports", reportsService.getOtherReports());
			model.addAttribute("report_list",reportsService.list());
			model.addAttribute("reportupdated","Report Updated Successfully");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is used to save the other reports
	 */
	@RequestMapping(value = "/savedashboardreports", method = RequestMethod.POST)
    public String save(@Valid DashBoardReports entity,BindingResult bindingResult,Model model,HttpServletRequest request) {
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
			DashBoardReports  dashBoard = new DashBoardReports();
			DashBoardReports report = reportsService.findByName(entity.getName());
			Integer userid=(Integer)request.getSession().getAttribute("userid");
			if(report != null){
				model.addAttribute("reportupdated", "Report Name Already Exists");
			}else{
				dashBoard.setId(entity.getId());
				dashBoard.setName(entity.getName());
				dashBoard.setDisplayname(entity.getDisplayname());
				dashBoard.setDescription(entity.getDescription());
				//dashBoard.setCreatedBy(entity.getCreatedBy());
				dashBoard.setCreatedBy(userid.toString());
				dashBoard.setCreatedDate(dateInIndia);
				dashBoard.setModifiedBy(entity.getModifiedBy());
				dashBoard.setModifiedDate(dateInIndia);
				
				reportsService.save(dashBoard);
				if(reportsService.save(dashBoard) == null){
				model.addAttribute("reportupdated", "Report Saved Successfully");
				}else { 
				model.addAttribute("reportupdated", "Report Saved Successfully");
				}
			}
			

		model.addAttribute("crudObj",new DashBoardReports());
		model.addAttribute("other_reports", reportsService.getOtherReports());
		model.addAttribute("otherreports","otherreports");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
}