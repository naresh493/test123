/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.infotree.qliktest.admin.dao.referencedata.NotificationsDao;
import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Notifications;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenantComp;
import com.infotree.qliktest.admin.entity.referencedata.Tenant;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.TenantProjectPojo;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.mail.MailService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.ProjectTenantService;
import com.infotree.qliktest.admin.service.referencedata.TenantService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserProjectService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;
@Controller
@RequestMapping("/tenant")
public class TenantController extends AbstractQTAdminController<Tenant> {

	private static final Logger LOG = LoggerFactory.getLogger(TenantController.class);
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserProjectService userProjectService;
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private ProjectTenantService projectTenantService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private NotificationsDao notificationsDao;
	
	@Autowired
	private DoNothingValidator validator;
	
	
	/**
	 * This method is to return the UI for assigning the projects to  the tenant
	 */
	@RequestMapping(value = "/assignprojectstotenant", method = RequestMethod.GET)
    public String assignProjectsToDefaultUser(Model model,HttpServletRequest req,HttpServletResponse res) {
		try {
			model.addAttribute("crudObj", new TenantProjectPojo());
			model.addAttribute("project_list", projectService.list());
			model.addAttribute("tenant_list",tenantService.list());
			model.addAttribute("assignprojectstotenants", "assignprojectstotenants");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * To save the projects to the tenants
	 */
	@RequestMapping(value = "/saveprojectstotenants", method = RequestMethod.POST)
    public String saveProjectsToTenants(@Valid TenantProjectPojo entity, BindingResult bindingResult, Model model,HttpServletRequest request) {
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
			model.addAttribute("crudObj",new TenantProjectPojo());
			Notifications notifications = new Notifications();
			int count = 0;
			String projectNames = "";
			UserTenant userTenant = userTenantService.findByTenantId(entity.getTenantId());
			projectTenantService.deleteByTenantId(entity.getTenantId());
			List<Integer> projects = entity.getProjectId();
			if(projects != null){
				Iterator<Integer> iterator = projects.iterator();
				while(iterator.hasNext()){
					Integer projectId = iterator.next();
					ProjectTenant tenant = projectTenantService.findByProjectId(projectId);
					if(tenant == null){
						ProjectTenant projectTenant=new ProjectTenant();
						ProjectTenantComp projectTenantComp = new ProjectTenantComp();
						projectTenantComp.setProjectId(projectId);
						projectTenantComp.setTenantId(entity.getTenantId());
						projectTenant.setProjectTenantComp(projectTenantComp);
						projectTenant.setCreatedBy(entity.getCreatedBy());
						projectTenant.setCreatedDate(dateInIndia);
						projectTenant.setModifiedBy(entity.getModifiedBy());
						projectTenant.setModifiedDate(dateInIndia);
						try {
							projectTenantService.insertNewProjectTenantEntity(projectTenant);
						} catch (Exception e1) {
							LOG.error(e1.toString());
							e1.printStackTrace();
						}
						Project p = projectService.getById(projectId);
						
						/**
						 * Sending the mail for the tenant with the project names assigned for the tenant
						 */
						User u = userProfileService.getById(userTenant.getUserTenantComp().getUserId());
						Map<String, Object> map = new HashMap<String, Object>();
						Project proj = new Project();
						proj.setName(p.getName());
						proj.setStartDate(p.getStartDate());
						proj.setEndDate(p.getEndDate());
						map.put("entity", proj);
						mailService.sendMailWithProjects("dev@infotreesolutions.com", u.getEmailAddress(), "New Project Assigned for You", map);
						/**
						 * Preparing the notification
						 */
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
						
						if(projectNames == ""){
							projectNames = p.getName();
						}else{
							projectNames = projectNames+","+p.getName();
						}
					}else{
						count = count+1;
					}
				
				/**
				 * Preparing the audit log record for the user
				 */
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				record.setActionType(AuditType.ASSIGN);
				record.setActionPerformed("Project Assigned.");
				Tenant t = tenantService.getById(entity.getTenantId());
				record.setActionData("Tenant name "+t.getName()+" and project names are"+projectNames);
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
				if(count==0){
					model.addAttribute("projectsaddedtotenants","Project(s) Assigned To Tenant'");
				}else{
				
				model.addAttribute("projectsaddedtotenants", "Project(s) Assigned To Tenant.'"+"<font color='red'>" + count+ "'Project(s) Not Assigned(Already Assigned To Other Tenant.)</font>" );
				}
				model.addAttribute("tenant_list",tenantService.list());
				}
			}else{
				model.addAttribute("projectsaddedtotenants", "Project(s) Assigned To Tenant.'"+"<font color='red'>Project(s) Not Assigned(Already Assigned To Other Tenant.)</font>" );
			}
			model.addAttribute("assignprojectstotenants","assignprojectstotenants");
			model.addAttribute("crudObj",entity);
			model.addAttribute("project_list",projectService.list());
			model.addAttribute("tenant_list",tenantService.list());
			
			entity.setTenantId(null);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	
		return "home";
	
	}
	
	/**
	 * This method will fetch the all tenants details
	 */
	@RequestMapping(value="/viewtenants", method=RequestMethod.GET)
	public String viewTenant(@ModelAttribute("crudObj") @Valid Tenant entity, Model model,HttpServletRequest req) {
		try {
			List<User> tenantList = new ArrayList<User>();
			
			Integer id = (Integer)req.getSession().getAttribute("userid");
			
				List<User> userList = userProfileService.getUsersByCreated(id+"");
				if(userList != null){
					Iterator<User> itr = userList.iterator();
					
					
					
					while(itr.hasNext()){
						User user = new User();
						User u = itr.next();
						String projects = "";
						UserTenant ut = userTenantService.findByUserId(u.getId());
						user.setUserName(u.getUserName());
						user.setFirstName(u.getFirstName());
						user.setSurName(u.getSurName());
						user.setEmailAddress(u.getEmailAddress());
						
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						user.setCreatedDate(t.getCreatedDate());
						user.setModifiedDate(t.getModifiedDate());
						user.setProjectCount(projectTenantService.getCountByTenantId(t.getId()));
						user.setTenantName(t.getName());
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
							user.setProjectName("<center><table width=70% border=1><tr><th><center>Project Names</center></th></tr><tr><td><center>"+projects+"</center></td></tr></table></center>");
						}
						
						tenantList.add(user);
						}
				}
				
				
			model.addAttribute("tenant_list", tenantList);
			model.addAttribute("tenantname_list", tenantService.list());
			model.addAttribute("project_list", projectService.getActiveProjects());
			model.addAttribute("crudObj",new User());
			model.addAttribute("viewtenant", "viewtenant");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method returns the UI inteface to edit a particular tenant
	 */
	@RequestMapping(value = "/edittenant", method = RequestMethod.GET)
    public String editTenant(Model model) {
			try {
				List<Tenant> tenantList = tenantService.list();
				List<Tenant> tenantsList = new ArrayList<Tenant>();
				if(tenantList != null){
					Iterator<Tenant> iterator = tenantList.iterator();
					while(iterator.hasNext()){
						Tenant t = iterator.next();
						if(t.getModifiedBy() != null){
							Integer id = Integer.parseInt(t.getModifiedBy());
							User u = userProfileService.getById(id);
							t.setCreatedName(u.getUserName());
						}else{
							Integer id = Integer.parseInt(t.getCreatedBy());
							User u = userProfileService.getById(id);
							t.setCreatedName(u.getUserName());
						}
						t.setCreatedDate(t.getCreatedDate());
						t.setModifiedDate(t.getModifiedDate());
						tenantsList.add(t);
					}
				}
				model.addAttribute("crudObj", getEntityInstance());
				model.addAttribute("tenantname_list", tenantService.list());
				model.addAttribute("tenant_list",tenantsList);
				model.addAttribute("projectname_list",projectService.getActiveProjects());
				model.addAttribute("edittenant", "edittenant");
			} catch (NumberFormatException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
		
		return "home";
	}
	/**
	 * which will return the particular tenant which was selected by the user to edit
	 */
	@RequestMapping(value="/edit/{tenantId}/update", method=RequestMethod.GET)
	public String update(@ModelAttribute("crudObj") @Valid Tenant entity, @PathVariable Integer tenantId, Model model,HttpServletRequest request) {
		
		try {
			Tenant tenant = null;
			UserTenant userTenant = null;
			try {
				tenant = tenantService.findByTenantIdAndCreatedBy(tenantId,(Integer)request.getSession().getAttribute("userid"));
				userTenant = userTenantService.findByTenantIdAndCreatedBy(tenantId,(Integer)request.getSession().getAttribute("userid"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			User entityToBeUpdated = userProfileService.getById(userTenant.getUserTenantComp().getUserId());
			entityToBeUpdated.setTenantId(tenant.getId());
			entityToBeUpdated.setTenantName(tenant.getName());
			request.getSession().setAttribute("userMailId", entityToBeUpdated.getEmailAddress());
			model.addAttribute("crudObj", entityToBeUpdated);
			model.addAttribute("idToBeUpdated", entityToBeUpdated.getId());
			model.addAttribute("updatetenant", "updatetenant");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method is to update the tenant details
	 */
	@RequestMapping(value = "/updateTenant",method = RequestMethod.POST)
	public String updateTenant(@Valid User entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			model.addAttribute("crudObj", new User());
			UserTenant userTenant = userTenantService.findByUserId(entity.getId());
			Tenant tenant = tenantService.findByNameAndNotId(entity.getTenantName(), userTenant.getUserTenantComp().getTenantId());
			String previousMailID = request.getSession().getAttribute("userMailId").toString();
			User userMail = null;
			
			if( ! previousMailID.equals(entity.getEmailAddress())){
				userMail = userProfileService.findByEmail(entity.getEmailAddress());
			}
			
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
			if(tenant == null && userMail == null){
				Tenant t = new Tenant();
				t.setId(userTenant.getUserTenantComp().getTenantId());
				t.setName(entity.getTenantName());
				t.setModifiedBy(entity.getModifiedBy());
				t.setModifiedDate(dateInIndia);
				tenantService.updateTenant(t);
				
				User u = new User();
				u.setId(entity.getId());
				u.setFirstName(entity.getFirstName());
				u.setSurName(entity.getSurName());
				u.setEmailAddress(entity.getEmailAddress());
				u.setLandline(entity.getLandline());
				u.setMobile(entity.getMobile());
				userProfileService.updateUserById(u);
				
				/**
				 * Prepare the notification for the update tenant
				 */
				Notifications notifications = new Notifications();
				notifications.setId(notificationsDao.getMaxId()+1);
				notifications.setAssignedBy((Integer)request.getSession().getAttribute("userid")+"");
				notifications.setAssignedTo(userTenant.getUserTenantComp().getUserId()+"");
				notifications.setAssignedData("Your tenant details have been modified");
				notifications.setStatus("UnRead");
				notifications.setCreatedBy(entity.getCreatedBy());
				notifications.setModifiedBy(entity.getModifiedBy());
				notifications.setCreatedDate(dateInIndia);
				notifications.setModifiedDate(dateInIndia);
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("entity", entity);
				mailService.sendUpdateTenantMail("dev@infotreesolutions.com", entity.getEmailAddress(), "Updated Details", map);
				model.addAttribute("tenantupdated","Tenant Updated Successfully");
				
			}else if(tenant != null){
				model.addAttribute("tenantupdated","Tenant Name Already Exists");
			}
			else
				model.addAttribute("tenantupdated","Given MailId Already Exists");
			
			List<Tenant> tenantList = tenantService.list();
			List<Tenant> tenantsList = new ArrayList<Tenant>();
			if(tenantList != null){
				Iterator<Tenant> iterator = tenantList.iterator();
				while(iterator.hasNext()){
					Tenant t = iterator.next();
					if(t.getModifiedBy() != null){
						Integer id = Integer.parseInt(t.getModifiedBy());
						User u = userProfileService.getById(id);
						t.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(t.getCreatedBy());
						User u = userProfileService.getById(id);
						t.setCreatedName(u.getUserName());
					}
					tenantsList.add(t);
				}
			}
			model.addAttribute("tenant_list", tenantsList);
			model.addAttribute("edittenant", "edittenant");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		model.addAttribute("projectname_list",projectService.getActiveProjects());
		return "home";
	}
	/**
	 * This method will return the UI to delete a tenant
	 */
	@RequestMapping(value = "/deletetenant", method = RequestMethod.GET)
    public String deleteTenant(Model model) {
		try {
			model.addAttribute("crudObj", getEntityInstance());
			model.addAttribute("active_tenants", userProfileService.getActiveUsers());
			model.addAttribute("deletetenant", "deletetenant");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
	/**
	 * To fetch all the projects which were assigned to the tenant by taking the tenantid as the parameter
	 */
	@RequestMapping(value="/getprojectbytenant",method=RequestMethod.GET)
	public void getAvailableProjects(Model model,@RequestParam("tenantId") String tenantId,HttpServletRequest request,HttpServletResponse response){
		try {
			Integer tenantid = Integer.parseInt(tenantId);
			String trProjects = "";
			List<Project> projectList = projectService.searchByTenantId(tenantid);
			
			for(Project list : projectList){
				trProjects += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
			}
			
			try{
				PrintWriter out = response.getWriter();
				out.println(trProjects);
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
	 * To fetch the projects which were not assigned to a tenant by taking the tenant id as the parameter
	 */
	@RequestMapping(value="/getprojectnotbytenant",method=RequestMethod.GET)
	public void getAvailablePermissions(Model model,@RequestParam("tenantId") String tenantId,HttpServletRequest request,HttpServletResponse response){
		try {
			Integer tenantid = Integer.parseInt(tenantId);
			String trAProjects = "";
			List<Project> projectList = projectService.searchByNotInTenantId(tenantid);
			
			for(Project list : projectList){
				trAProjects += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
			}
			
			try{
				PrintWriter out = response.getWriter();
				out.println(trAProjects);
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
	 * Searching tenants based on projects and tenants
	 */
	@RequestMapping(value="/searchtenants",method = RequestMethod.POST)
	public String searchTenants(@ModelAttribute("crudObj") @Valid Tenant entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			Integer projectId = entity.getProjectId();
			Integer tenantId = entity.getId();
			List<User> tenantList = new ArrayList<User>();
			if(tenantId == null && projectId == null){
				List<User> userList = userProfileService.getUsersByCreated((Integer)request.getSession().getAttribute("userid")+"");
				if(userList != null){
					Iterator<User> itr = userList.iterator();
					String projects = "";
					while(itr.hasNext()){
						User user = new User();
						User u = itr.next();
						UserTenant ut = userTenantService.findByUserId(u.getId());
						user.setUserName(u.getUserName());
						user.setFirstName(u.getFirstName());
						user.setSurName(u.getSurName());
						user.setEmailAddress(u.getEmailAddress());
						Tenant t = tenantService.findByTenantIdAndCreatedBy(ut.getUserTenantComp().getTenantId(), (Integer)request.getSession().getAttribute("userid"));
						user.setCreatedDate(t.getCreatedDate());
						user.setModifiedDate(t.getModifiedDate());
						user.setProjectCount(projectTenantService.getCountByTenantId(t.getId()));
						user.setTenantName(t.getName());
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
						}
						String allprojects = "<center><table width=70% border=1><tr><th><center>Project Names</center></th></tr><tr><td><center>"+projects+"</center></td></tr></table></center>";
						user.setProjectName(allprojects);
						//user.setProjectName(projects);
						tenantList.add(user);
						model.addAttribute("tenant_list", tenantList);
						model.addAttribute("tenantname_list", tenantService.list());
						model.addAttribute("project_list", projectService.getActiveProjects());
					
					}
				}
				
				}else if(projectId == null && tenantId != null){
					UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(tenantId, (Integer)request.getSession().getAttribute("userid"));
					String projects = "";
					if(userTenant != null){
						User user = new User();
						User u = userProfileService.getById(userTenant.getUserTenantComp().getUserId());
						UserTenant ut = userTenantService.findByUserId(u.getId());
						user.setUserName(u.getUserName());
						user.setFirstName(u.getFirstName());
						user.setSurName(u.getSurName());
						user.setEmailAddress(u.getEmailAddress());
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						user.setCreatedDate(t.getCreatedDate());
						user.setModifiedDate(t.getModifiedDate());
						user.setProjectCount(projectTenantService.getCountByTenantId(t.getId()));
						user.setTenantName(t.getName());
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
						String allprojects = "<center><table width=70% border=1><tr><th><center>Project Names</center></th></tr><tr><td><center>"+projects+"</center></td></tr></table></center>";
						user.setProjectName(allprojects);
						//user.setProjectName(projects);
						tenantList.add(user);
					}
						
						model.addAttribute("tenant_list", tenantList);
						model.addAttribute("tenantname_list", tenantService.list());
						model.addAttribute("project_list", projectService.getActiveProjects());
					
				}else if(projectId != null && tenantId == null){
					
					ProjectTenant projectTenant = projectTenantService.findByProjectId(projectId);
					if(projectTenant != null){
						UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(projectTenant.getProjectTenantComp().getTenantId(), (Integer)request.getSession().getAttribute("userid"));
						String projects = "";
						User user = new User();
						User u = userProfileService.getById(userTenant.getUserTenantComp().getUserId());
						UserTenant ut = userTenantService.findByUserId(u.getId());
						user.setUserName(u.getUserName());
						user.setFirstName(u.getFirstName());
						user.setSurName(u.getSurName());
						user.setEmailAddress(u.getEmailAddress());
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						user.setCreatedDate(t.getCreatedDate());
						user.setModifiedDate(t.getModifiedDate());
						user.setProjectCount(projectTenantService.getCountByTenantId(t.getId()));
						user.setTenantName(t.getName());
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
						String allprojects = "<center><table width=70% border=1><tr><th><center>Project Names</center></th></tr><tr><td><center>"+projects+"</center></td></tr></table></center>";
						user.setProjectName(allprojects);
						//user.setProjectName(projects);
						tenantList.add(user);
					}
					
					model.addAttribute("tenant_list", tenantList);
					model.addAttribute("tenantname_list", tenantService.list());
					model.addAttribute("project_list", projectService.getActiveProjects());
				
					
				}else if(projectId != null && tenantId != null){
					ProjectTenant projectTenant = projectTenantService.findByIds(projectId, tenantId);
					if(projectTenant != null){
						UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(tenantId, (Integer)request.getSession().getAttribute("userid"));
						String projects = "";
						
						User user = new User();
						User u = userProfileService.getById(userTenant.getUserTenantComp().getUserId());
						UserTenant ut = userTenantService.findByUserId(u.getId());
						user.setUserName(u.getUserName());
						user.setFirstName(u.getFirstName());
						user.setSurName(u.getSurName());
						user.setEmailAddress(u.getEmailAddress());
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						user.setCreatedDate(t.getCreatedDate());
						user.setModifiedDate(t.getModifiedDate());
						user.setProjectCount(projectTenantService.getCountByTenantId(t.getId()));
						user.setTenantName(t.getName());
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
						String allprojects = "<center><table width=70% border=1><tr><th><center>Project Names</center></th></tr><tr><td><center>"+projects+"</center></td></tr></table></center>";
						user.setProjectName(allprojects);
						//user.setProjectName(projects);
						tenantList.add(user);
					}
					
					model.addAttribute("tenant_list", tenantList);
					model.addAttribute("tenantname_list", tenantService.list());
					model.addAttribute("project_list", projectService.getActiveProjects());
				}
			
				
			
			model.addAttribute("crudObj",entity);
			model.addAttribute("viewtenant", "viewtenant");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * searching the edit tenant filter
	 */
	
	@RequestMapping(value="/searchedittenant",method = RequestMethod.POST)
	public String searchEditTenants(@ModelAttribute("crudObj") @Valid Tenant entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			Integer projectId = entity.getProjectId();
			Integer tenantId = entity.getId();
			List<Tenant> tenantList = new ArrayList<Tenant>();
			if(tenantId == null && projectId == null){
				List<User> userList = userProfileService.getUsersByCreated((Integer)request.getSession().getAttribute("userid")+"");
				if(userList != null){
					Iterator<User> itr = userList.iterator();
					while(itr.hasNext()){
						Tenant user = new Tenant();
						User u = itr.next();
						UserTenant ut = userTenantService.findByUserId(u.getId());
						Tenant t = tenantService.findByTenantIdAndCreatedBy(ut.getUserTenantComp().getTenantId(), (Integer)request.getSession().getAttribute("userid"));
						user.setCreatedDate(t.getCreatedDate());
						user.setModifiedDate(t.getModifiedDate());
						if(t.getModifiedBy() != null){
							Integer id = Integer.parseInt(t.getModifiedBy());
							User use = userProfileService.getById(id);
							user.setCreatedName(use.getUserName());
						}else{
							Integer id = Integer.parseInt(t.getCreatedBy());
							User use = userProfileService.getById(id);
							user.setCreatedName(use.getUserName());
						}
						user.setId(t.getId());
						user.setName(t.getName());
						
						tenantList.add(user);
						model.addAttribute("tenant_list", tenantList);
						model.addAttribute("tenantname_list", tenantService.list());
						model.addAttribute("project_list", projectService.getActiveProjects());
					
					}
				}
				
				}else if(projectId == null && tenantId != null){
					UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(tenantId, (Integer)request.getSession().getAttribute("userid"));
					if(userTenant != null){
						Tenant user = new Tenant();
						User u = userProfileService.getById(userTenant.getUserTenantComp().getUserId());
						UserTenant ut = userTenantService.findByUserId(u.getId());
						
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						user.setCreatedDate(t.getCreatedDate());
						user.setModifiedDate(t.getModifiedDate());
						if(t.getModifiedBy() != null){
							Integer id = Integer.parseInt(t.getModifiedBy());
							User use = userProfileService.getById(id);
							user.setCreatedName(use.getUserName());
						}else{
							Integer id = Integer.parseInt(t.getCreatedBy());
							User use = userProfileService.getById(id);
							user.setCreatedName(use.getUserName());
						}
						user.setId(t.getId());
						user.setName(t.getName());
						
						tenantList.add(user);
					}
						
						model.addAttribute("tenant_list", tenantList);
						model.addAttribute("tenantname_list", tenantService.list());
						model.addAttribute("project_list", projectService.getActiveProjects());
					
					
					
				}else if(projectId != null && tenantId == null){
					
					ProjectTenant projectTenant = projectTenantService.findByProjectId(projectId);
					if(projectTenant != null){
						UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(projectTenant.getProjectTenantComp().getTenantId(), (Integer)request.getSession().getAttribute("userid"));
						
						Tenant user = new Tenant();
						User u = userProfileService.getById(userTenant.getUserTenantComp().getUserId());
						UserTenant ut = userTenantService.findByUserId(u.getId());
						
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						user.setCreatedDate(t.getCreatedDate());
						user.setModifiedDate(t.getModifiedDate());
						if(t.getModifiedBy() != null){
							Integer id = Integer.parseInt(t.getModifiedBy());
							User use = userProfileService.getById(id);
							user.setCreatedName(use.getUserName());
						}else{
							Integer id = Integer.parseInt(t.getCreatedBy());
							User use = userProfileService.getById(id);
							user.setCreatedName(use.getUserName());
						}
						user.setId(t.getId());
						user.setName(t.getName());
						
						tenantList.add(user);
					}
					
					model.addAttribute("tenant_list", tenantList);
					model.addAttribute("tenantname_list", tenantService.list());
					model.addAttribute("project_list", projectService.getActiveProjects());
				
					
				}else if(projectId != null && tenantId != null){
					ProjectTenant projectTenant = projectTenantService.findByIds(projectId, tenantId);
					if(projectTenant != null){
						UserTenant userTenant = userTenantService.findByTenantIdAndCreatedBy(tenantId, (Integer)request.getSession().getAttribute("userid"));
						
						Tenant user = new Tenant();
						User u = userProfileService.getById(userTenant.getUserTenantComp().getUserId());
						UserTenant ut = userTenantService.findByUserId(u.getId());
						Tenant t = tenantService.findByTenantId(ut.getUserTenantComp().getTenantId());
						user.setId(t.getId());
						user.setName(t.getName());
						user.setCreatedDate(t.getCreatedDate());
						user.setModifiedDate(t.getModifiedDate());
						if(t.getModifiedBy() != null){
							Integer id = Integer.parseInt(t.getModifiedBy());
							User use = userProfileService.getById(id);
							user.setCreatedName(use.getUserName());
						}else{
							Integer id = Integer.parseInt(t.getCreatedBy());
							User use = userProfileService.getById(id);
							user.setCreatedName(use.getUserName());
						}
						tenantList.add(user);
					}
					model.addAttribute("tenant_list", tenantList);
					model.addAttribute("tenantname_list", tenantService.list());
					model.addAttribute("project_list", projectService.getActiveProjects());
				}
			entity.setProjectId(projectId);
			entity.setProjectId(projectId);
			model.addAttribute("tenantname_list", tenantService.list());
			model.addAttribute("project_list", projectService.getActiveProjects());
			model.addAttribute("crudObj", entity);
			model.addAttribute("edittenant", "edittenant");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
	
    @ModelAttribute("project_list")
    public HashSet<Project> projects(WebRequest request) {
    	return new HashSet<Project>(projectService.list());
    }
    
	@Override
	public QTAdminService<Tenant> getTService() {
		return tenantService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}
}
