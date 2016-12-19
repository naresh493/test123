/**
 * @author koteswara.g
 */
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
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infotree.qliktest.admin.dao.referencedata.NotificationsDao;
import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.entity.referencedata.Notifications;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.ProjectModule;
import com.infotree.qliktest.admin.entity.referencedata.ProjectModuleComp;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenantComp;
import com.infotree.qliktest.admin.entity.referencedata.Tenant;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.mail.MailService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ModuleService;
import com.infotree.qliktest.admin.service.referencedata.PermissionsService;
import com.infotree.qliktest.admin.service.referencedata.ProjectModuleService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.ProjectTenantService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.TenantService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserRoleService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/project")
public class ProjectController extends AbstractQTAdminController<Project> {
	private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private PermissionsService permissionsService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private NotificationsDao notificationsDao;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private ProjectTenantService projectTenantService;
	
	@Autowired
	private ProjectModuleService projectModuleService;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private ModuleService testingTypeService;
	
	@Autowired
	private DoNothingValidator validator;
	/**
	 * This method retturns the UI to create the project
	 */
	@RequestMapping(value = "/createproject", method = RequestMethod.GET)
    public String landingPage(Model model) {
		try {
			model.addAttribute("crudObj", getEntityInstance());
			model.addAttribute("createproject", "createproject");
			model.addAttribute("module_list",moduleService.list());
			model.addAttribute("project_list", projectService.list());
			model.addAttribute("tenant_list",tenantService.list());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is used to view the projects
	 */
	@RequestMapping(value="/viewproject", method=RequestMethod.GET)
	public String viewTenant(@ModelAttribute("crudObj") @Valid Project entity, Model model) {
		try {
			
			model.addAttribute("crudObj", getEntityInstance());
			List<Project> projList = new ArrayList<Project>();
			
			//List<Project> projectsList = projectService.getActiveProjects();
			List<Project> projectsList = projectService.list();
			Iterator<Project> iterator = projectsList.iterator();
			while(iterator.hasNext()){
				String moduleNames = "";
				Project proj = iterator.next();
				SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd");
				
				proj.setStartingDate(formatt.format(proj.getStartDate()));
				proj.setEndingDate(formatt.format(proj.getEndDate()));
				proj.setDisabled(proj.isDisabled());
				Tenant t = tenantService.findByProject(proj.getId());
				if(t != null){
					proj.setTenantName(t.getName());
				}else{
					proj.setTenantName("No Tenant");
				}
				int moduleCount = projectModuleService.getModuleCountByProjectId(proj.getId());
				List<ProjectModule> projModList = projectModuleService.findByProjectId(proj.getId());
				proj.setModuleCount(moduleCount);
				if(moduleCount == 0){
					moduleNames = "No Packages Available";
				}else{
					if(projModList != null){
						Iterator<ProjectModule> itr = projModList.iterator();
						while(itr.hasNext()){
							ProjectModule pm = itr.next();
							Module m = moduleService.getById(pm.getProjectModuleComp().getModuleId());
							if(moduleNames == ""){
								moduleNames = m.getName();
							}else{
								moduleNames = moduleNames+" "+"<br>"+m.getName();
							}
						}
						
					}
				}
				proj.setModuleNames("<center><table width=70% border=1><tr><th><center>Package Names</center></th></tr><tr><td><center>"+moduleNames+"</center></td></tr></table></center>");
				projList.add(proj);
				
			}
			model.addAttribute("tenantname_list", tenantService.list());
			model.addAttribute("project_list", projList);
			model.addAttribute("modulename_list", moduleService.list());
			//model.addAttribute("projectname_list",projectService.getActiveProjects());
			model.addAttribute("projectname_list",projectService.list());
			model.addAttribute("viewproject", "viewproject");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method retruns the UI to edit project with the list of the projects
	 */
	@RequestMapping(value = "/editproject", method = RequestMethod.GET)
    public String editTenant(Model model) {
		try {
			model.addAttribute("crudObj", getEntityInstance());
			//List<Project> projList =  projectService.getActiveProjects();
			List<Project> projList =  projectService.list();
			List<Project> projectsList = new ArrayList<Project>();
			if(projList != null){
				Iterator<Project> iterator = projList.iterator();
				while(iterator.hasNext()){
					Project p = iterator.next();
					if(p.getModifiedBy() != null){
						Integer id = Integer.parseInt(p.getModifiedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(p.getCreatedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}
					projectsList.add(p);
				}
			}
			
			model.addAttribute("tenantname_list", tenantService.list());
			model.addAttribute("project_list", projectsList);
			model.addAttribute("editproject", "editproject");
			//model.addAttribute("projectname_list", projectService.getActiveProjects());
			model.addAttribute("projectname_list", projectService.list());
			
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to delete the project with the list of projects
	 */
	@RequestMapping(value = "/deleteproject", method = RequestMethod.GET)
    public String deleteTenant(Model model) {
		try {
			model.addAttribute("crudObj", getEntityInstance());
			//model.addAttribute("active_projects", projectService.getActiveProjects());
			model.addAttribute("active_projects", projectService.list());
			model.addAttribute("deleteproject", "deleteproject");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to save the new projects
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProject(@ModelAttribute("crudObj") @Valid Project entity, BindingResult bindingResult, Model model,HttpServletRequest request) {
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
			model.addAttribute("crudObj", getEntityInstance());
			if(projectService.findByName(entity.getName()) != null){
				model.addAttribute("createproject", "createproject");
				model.addAttribute("project_list", projectService.list());
				//model.addAttribute("active_project_list", projectService.getActiveProjects());
				model.addAttribute("active_project_list", projectService.list());
				model.addAttribute("projectalreadyexists", "'" + entity.getName()+"' already exists. Pl. enter a different Name." );
			}else{
				Notifications notifications = new Notifications();
				Project p = new Project();
				p.setName(entity.getName().trim());
				p.setStartDate(entity.getStartDate());
				p.setEndDate(entity.getEndDate());
				p.setDisabled(false);
				p.setDescription(entity.getDescription());
				p.setCreatedBy(entity.getCreatedBy());
				
				p.setCreatedDate(dateInIndia);
				if(projectService.save(p) == null){
					model.addAttribute("createproject", "createproject");
					model.addAttribute("project_list", projectService.list());
					model.addAttribute("projectsnotselected", "Error in creating Module. Pl. contact Sys Admin." );
				}else{
					/**
					 * save the project to the tenant
					 */
					ProjectTenant projectTenant=new ProjectTenant();
					ProjectTenantComp projectTenantComp = new ProjectTenantComp();
					projectTenantComp.setProjectId(p.getId());
					projectTenantComp.setTenantId(entity.getTenantId());
					projectTenant.setProjectTenantComp(projectTenantComp);
					
					projectTenant.setCreatedBy(entity.getCreatedBy());
					
					projectTenant.setCreatedDate(dateInIndia);
					projectTenant.setModifiedBy(entity.getModifiedBy());
					projectTenant.setModifiedDate(dateInIndia);
					try {
						projectTenantService.insertNewProjectTenantEntity(projectTenant);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					/**
					 * assigning the modules to the project
					 */
					List<Integer> moduleList = entity.getModuleId();
					if(moduleList != null){
						Iterator<Integer> iterator = moduleList.iterator();
						while(iterator.hasNext()){
							ProjectModule projectModule = new ProjectModule();
							ProjectModuleComp projectModuleComp = new ProjectModuleComp();
							projectModuleComp.setProjectId(p.getId());
							projectModuleComp.setModuleId(iterator.next());
							projectModule.setProjectModuleComp(projectModuleComp);
							projectModule.setCreatedBy(entity.getCreatedBy());
							projectModule.setCreatedDate(dateInIndia);
							projectModule.setModifiedBy(entity.getModifiedBy());
							projectModule.setModifiedDate(dateInIndia);
							projectModuleService.save(projectModule);
							
						}
					}
					
					/**
					 * Preparing the notification for the application admin 
					 */
					UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(entity.getTenantId(),(Integer)request.getSession().getAttribute("userid"));
					notifications.setId(notificationsDao.getMaxId()+1);
					notifications.setAssignedBy((Integer)request.getSession().getAttribute("userid")+"");
					notifications.setAssignedTo(userTenant.getUserTenantComp().getUserId()+"");
					notifications.setAssignedData(p.getName()+" Assigned for you");
					notifications.setStatus("UnRead");
					notifications.setCreatedBy(entity.getCreatedBy());
					notifications.setModifiedBy(entity.getModifiedBy());
					notifications.setCreatedDate(dateInIndia);
					notifications.setModifiedDate(dateInIndia);
					notificationsDao.saveNotification(notifications);
					/**
					 * preparing the audit record
					 */
					
					
					AuditLogRecord record = new AuditLogRecord();
					record.setActionDate(dateInIndia);
					record.setActionType(AuditType.CREATE);
					record.setActionPerformed("Project Created with Name "+entity.getName());
					record.setActionData("Project Name :"+entity.getName()+" start date is :"+entity.getStartDate()+" end date is"+entity.getEndDate());
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
					model.addAttribute("createproject", "createproject");
					model.addAttribute("project_list", projectService.list());
					model.addAttribute("module_list",moduleService.list());
					model.addAttribute("tenant_list",tenantService.list());
					//model.addAttribute("active_project_list", projectService.getActiveProjects());
					model.addAttribute("active_project_list", projectService.list());
					model.addAttribute("projectcreated", "'" + entity.getName() + "' created." );
				}
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to update project with the editable mode
	 */
	@RequestMapping(value="/edit/{projectId}/update", method=RequestMethod.GET)
	public String updateModules(@ModelAttribute("crudObj") @Valid Project entity, @PathVariable Integer projectId, Model model) {
		try {
			Project entityToBeUpdated = projectService.getById(projectId);
			Project project = new Project();
			project.setName(entityToBeUpdated.getName());
			project.setDescription(entityToBeUpdated.getDescription());
			project.setStartDate(entityToBeUpdated.getStartDate());
			project.setEndDate(entityToBeUpdated.getEndDate());
			Tenant t = tenantService.findByProject(entityToBeUpdated.getId());
			if(t != null){
				project.setTenantName(t.getName());
			}else{
				project.setTenantName("No Tenant");
			}
			model.addAttribute("module_list", moduleService.getByProjectId(entityToBeUpdated.getId()));
			model.addAttribute("crudObj", project);
			//model.addAttribute("project_list", projectService.getActiveProjects());
			model.addAttribute("project_list", projectService.list());
			model.addAttribute("idToBeUpdated", entityToBeUpdated.getId());
			model.addAttribute("updateproject", "updateproject");
			model.addAttribute("assigned_module_list",moduleService.getByModuleId(entityToBeUpdated.getId()));
			model.addAttribute("available_module_list", moduleService.getByNotInModuleId(entityToBeUpdated.getId()));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * Thie method is to update the project
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProject(@ModelAttribute("crudObj") @Valid Project entity, BindingResult bindingResult, Model model,HttpServletRequest request) {
		//System.out.println("Time in Project: "+entity.getCreatedDate()+" , @: "+entity.getModifiedDate());
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
				Project p = projectService.getById(entity.getId());
				if(p == null){
					model.addAttribute("createproject", "createproject");
					//model.addAttribute("project_list", projectService.getActiveProjects());
					model.addAttribute("project_list", projectService.list());
					model.addAttribute("projectcreated", "Data cannot be saved. Pl. try again." );
				}else{
					p.setName(entity.getName().trim());
					p.setStartDate(entity.getStartDate());
					p.setEndDate(entity.getEndDate());
					p.setDisabled(false);
					p.setModifiedBy(entity.getModifiedBy());
					
					p.setModifiedDate(dateInIndia);
					super.save(p, bindingResult, model);
					/**
					 * preparing the audit log service
					 */
					
					AuditLogRecord record = new AuditLogRecord();
					record.setActionDate(dateInIndia);
					record.setActionType(AuditType.MODIFY);
					record.setActionData("after updation Project Name :"+entity.getName()+"start date is :"+entity.getStartDate()+" end date is"+entity.getEndDate());
					record.setActionPerformed("Project updated with name "+entity.getName());
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
					//delete the existing modules which were already assigned to the project
					
					projectModuleService.deleteByProjectId(entity.getId());
					/**
					 * assigning the licenses to the project
					 */
					List<Integer> moduleList = entity.getModuleId();
					if(moduleList != null){
						Iterator<Integer> iterator = moduleList.iterator();
						while(iterator.hasNext()){
							ProjectModule md = new ProjectModule();
							ProjectModuleComp modperComp = new ProjectModuleComp();
							modperComp.setProjectId(entity.getId());
							modperComp.setModuleId(iterator.next());
							md.setProjectModuleComp(modperComp);
							md.setCreatedBy(entity.getCreatedBy());
							md.setCreatedDate(dateInIndia);
							md.setModifiedBy(entity.getModifiedBy());
							md.setModifiedDate(dateInIndia);
							projectModuleService.save(md);
						}
						
					}
					model.addAttribute("editproject", "editproject");
					
					model.addAttribute("projectupdated", "'" + entity.getName() + "' updated." );
				}
				
				//List<Project> projList =  projectService.getActiveProjects();
				List<Project> projList =  projectService.list();
				List<Project> projectsList = new ArrayList<Project>();
				if(projList != null){
					Iterator<Project> iterator = projList.iterator();
					while(iterator.hasNext()){
						Project proj = iterator.next();
						if(proj.getModifiedBy() != null){
							Integer id = Integer.parseInt(proj.getModifiedBy());
							User u = userService.getById(id);
							proj.setCreatedName(u.getUserName());
						}else{
							Integer id = Integer.parseInt(proj.getCreatedBy());
							User u = userService.getById(id);
							proj.setCreatedName(u.getUserName());
						}
						projectsList.add(proj);
					}
				}
				model.addAttribute("tenantname_list", tenantService.list());
				model.addAttribute("project_list", projectsList);
				//model.addAttribute("projectname_list", projectService.getActiveProjects());
				model.addAttribute("projectname_list", projectService.list());
				model.addAttribute("crudObj", getEntityInstance());
				entity.setEndDate(null);
				entity.setStartDate(null);
			} catch (NumberFormatException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
		return "home";
	}
	/**
	 * This method deletes the project which was selected by the user
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@ModelAttribute("crudObj") @Valid Project entity, BindingResult bindingResult, Model model) {
		try {
			Project p = projectService.getById(entity.getId());
			if(p == null){
				model.addAttribute("deleteproject", "deleteproject");
				//model.addAttribute("active_projects", projectService.getActiveProjects());
				model.addAttribute("active_projects", projectService.list());
				model.addAttribute("projectdeleted", "Data cannot be deleted. Pl. try again." );
			}else{
				p.setDisabled(true);
				p.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
				p.setModifiedDate(new java.sql.Date(System.currentTimeMillis()));
				projectService.delete(p);
				model.addAttribute("crudObj", getEntityInstance());
				model.addAttribute("deleteproject", "deleteproject");
				//model.addAttribute("active_projects",projectService.getActiveProjects());
				model.addAttribute("active_projects",projectService.list());
				model.addAttribute("projectdeleted", "'"+ entity.getName() +"' deleted.");
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to get the list of projects which are assigned to the tenant
	 */
	@RequestMapping(value="/getprojectsbyid",method=RequestMethod.GET)
	public String getProjectsById(Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			model.addAttribute("viewprojectsbasedonid","viewprojectsbasedonid");
			
			HttpSession session=request.getSession();
			Integer id=(Integer)session.getAttribute("userid");
			UserTenant userTenant = userTenantService.findByUserId(id);
			List<Project> projects = projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId());
			model.addAttribute("projects_list_byid",projects);
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(new java.util.Date());
			record.setActionType(AuditType.VIEW);
			record.setActionPerformed("viewed the list of projects");
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
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to search the list of projects by taking the filters
	 */
	@RequestMapping(value="/searchprojects",method = RequestMethod.POST)
	public String searchProjects(@ModelAttribute("crudObj") @Valid Project entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			List<Project> projList = new ArrayList<Project>();
			try {
				Map<String,String> map = new HashMap<String,String>();
				Integer tenantId = entity.getTenantId();
				String tenId = "";
				if(tenantId != null){
					tenId = tenantId+"";
				}
				 
				
				if(tenId !=null && tenId != "")
				{
					map.put("tenant_id", tenId);
				}
				
				
				if(entity.getStartingDate() != null && entity.getStartingDate() !="")
				{
					map.put("start_date", entity.getStartingDate());
				}
				
				
				if(entity.getEndingDate() != null && entity.getEndingDate() !="")
				{
					map.put("end_date", entity.getEndingDate());
				}
				if(entity.getModId() != null && entity.getModId() != ""){
					map.put("module_id", entity.getModId());
				}
				if(entity.getId() != null){
					map.put("id", entity.getId()+"");
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
				        
				        if(pairs.getKey().toString().equalsIgnoreCase("start_date")){
				        	sb.append(pairs.getKey().toString()+">='"+pairs.getValue().toString()+"'");
				        }
				        
				        if(pairs.getKey().toString().equalsIgnoreCase("end_date")){
				        	sb.append(pairs.getKey().toString()+"<='"+pairs.getValue().toString()+"'");
				        }
				        
				        if(pairs.getKey().toString().equalsIgnoreCase("tenant_id")){
				        	sb.append("id in (select project_id from project_tenant where ");
				        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
				        	sb.append(")");
				        }
				        if(pairs.getKey().toString().equalsIgnoreCase("id")){
				        	sb.append(pairs.getKey().toString()+"='"+pairs.getValue().toString()+"'");
				        }
				        if(pairs.getKey().toString().equalsIgnoreCase("module_id")){
				        	sb.append("id in (select project_id from project_module where ");
				        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
				        	sb.append(")");
				        }
				        
				        i++;
				        
				    }
				  List<Project> projectList = new ArrayList<Project>();
				  if(sb.toString().equals("select * from project where ")){
					  //projectList = projectService.getActiveProjects();
					  projectList = projectService.list();
				  }else{
					  projectList = projectService.executeQuery(sb.toString());
				  }
					Iterator<Project> iterator = projectList.iterator();
					while(iterator.hasNext()){
						String moduleNames = "";
						Project proj = iterator.next();
						SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd");
						
						proj.setStartingDate(formatt.format(proj.getStartDate()));
						proj.setEndingDate(formatt.format(proj.getEndDate()));
						proj.setDisabled(proj.isDisabled());
						Tenant t = tenantService.findByProject(proj.getId());
						if(t != null){
							proj.setTenantName(t.getName());
						}else{
							proj.setTenantName("No Tenant");
						}
						int moduleCount = projectModuleService.getModuleCountByProjectId(proj.getId());
						List<ProjectModule> projModList = projectModuleService.findByProjectId(proj.getId());
						proj.setModuleCount(moduleCount);
						if(moduleCount == 0){
							moduleNames = "No Packages Available";
						}else{
							if(projModList != null){
								Iterator<ProjectModule> itr = projModList.iterator();
								while(itr.hasNext()){
									ProjectModule pm = itr.next();
									Module m = moduleService.getById(pm.getProjectModuleComp().getModuleId());
									if(moduleNames == ""){
										moduleNames = m.getName();
									}else{
										moduleNames = moduleNames+" "+"<br>"+m.getName();
									}
								}
								
							}
						}
						proj.setModuleNames("<center><table width=70% border=1><tr><th><center>Package Names</center></th></tr><tr><td><center>"+moduleNames+"</center></td></tr></table></center>");
						projList.add(proj);
						
					}
					model.addAttribute("tenantname_list", tenantService.list());
					model.addAttribute("project_list", projList);
					model.addAttribute("modulename_list", moduleService.list());
					model.addAttribute("projectname_list",projectService.getActiveProjects());
					model.addAttribute("viewproject", "viewproject");
					model.addAttribute("crudObj", entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			entity.setEndingDate(entity.getEndingDate());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
		}
	/**
	 * This method is to search the projects by taking user entered filters
	 */
	@RequestMapping(value="/searcheditproject",method = RequestMethod.POST)
	public String searchEditProjects(@ModelAttribute("crudObj") @Valid Project entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		List<Project> projList = new ArrayList<Project>();
		try {
			Map<String,String> map = new HashMap<String,String>();
			
			Integer tenantId = entity.getTenantId();
			String tenId = "";
			if(tenantId != null){
				tenId = tenantId+"";
			}
			 
			
			
			if(tenId !=null && tenId != "")
			{
				map.put("tenant_id", tenId);
			}
			
			
			if(entity.getStartingDate() != null && entity.getStartingDate() !="")
			{
				map.put("start_date", entity.getStartingDate());
			}
			
			
			if(entity.getEndingDate() != null && entity.getEndingDate() !="")
			{
				map.put("end_date", entity.getEndingDate());
			}
			
			if(entity.getId() != null){
				map.put("id", entity.getId()+"");
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
			        
			        if(pairs.getKey().toString().equalsIgnoreCase("start_date")){
			        	sb.append(pairs.getKey().toString()+">='"+pairs.getValue().toString()+"'");
			        }
			        
			        if(pairs.getKey().toString().equalsIgnoreCase("end_date")){
			        	sb.append(pairs.getKey().toString()+"<='"+pairs.getValue().toString()+"'");
			        }
			        
			        if(pairs.getKey().toString().equalsIgnoreCase("tenant_id")){
			        	sb.append("id in (select project_id from project_tenant where ");
			        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
			        	sb.append(")");
			        }
			        
			        if(pairs.getKey().toString().equalsIgnoreCase("id")){
			        	sb.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
			        }
			        
			       
			        
			        i++;
			        
			    }
			  List<Project> projectList = null;
			  if(sb.toString().equals("select * from project where ")){
				  //projectList = projectService.getActiveProjects();
				  projectList = projectService.list();
			  }else{
				  projectList = projectService.executeQuery(sb.toString());
			  }
			  Iterator<Project> iterator = projectList.iterator();
			  while(iterator.hasNext()){
				  Project proj = iterator.next();
				  Project p = new Project();
				  p.setId(proj.getId());
				  p.setName(proj.getName());
				  p.setDescription(proj.getDescription());
				  p.setStartDate(proj.getStartDate());
				  p.setEndDate(proj.getEndDate());
				  p.setCreatedDate(proj.getCreatedDate());
				  if(proj.getModifiedBy() != null){
						Integer id = Integer.parseInt(proj.getModifiedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(proj.getCreatedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}
				  p.setCreatedBy(proj.getCreatedBy());
				  p.setModifiedBy(proj.getModifiedBy());
				  p.setModifiedDate(proj.getModifiedDate());
				  Tenant t = tenantService.findByProject(proj.getId());
					if(t != null){
						p.setTenantName(t.getName());
					}else{
						p.setTenantName("No Tenant");
					}
					projList.add(p);
			  }
			
			model.addAttribute("tenantname_list", tenantService.list());
     		model.addAttribute("project_list", projList);
     		//model.addAttribute("projectname_list", projectService.getActiveProjects());
     		model.addAttribute("projectname_list", projectService.list());
			model.addAttribute("editproject", "editproject");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
		}
	

	
	
	
	@Override
	public QTAdminService<Project> getTService() {
		return projectService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}
}
