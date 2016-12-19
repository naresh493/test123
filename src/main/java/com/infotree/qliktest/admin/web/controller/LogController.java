/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.ProjectTenantService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserRoleService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;


@Controller
@RequestMapping("/logs")
public class LogController extends AbstractQTAdminController<AuditLogRecord> {
	private static final Logger log = LoggerFactory.getLogger(LogController.class);
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private ProjectTenantService projectTenantService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserProfileService userProfileService;
	/**
	 * This method to fetch all the auditlogs
	 */
	@RequestMapping(value = "/auditlogs",method = RequestMethod.GET)
	public String viewLogs(Model model,HttpServletRequest request){
		try {
			List<AuditLogRecord> auditLog = new ArrayList<AuditLogRecord>();
			Integer id = (Integer)request.getSession().getAttribute("userid");
			try {
				model.addAttribute("auditlogs","auditlogs");
				model.addAttribute("flag","false");
				auditLog = new ArrayList<AuditLogRecord>();
				
				List<AuditLogRecord> list = auditLogService.getByUserId(id);
				Iterator<AuditLogRecord> iterator = list.iterator();
				while(iterator.hasNext()){
					AuditLogRecord record = new AuditLogRecord();
					AuditLogRecord auditRecord = iterator.next();
					//User u = userProfileService.getById(auditRecord.getUserId());
					//record.setUserName(u.getUserName());
					record.setUserName(auditRecord.getUserName());
					record.setActionType(auditRecord.getActionType());
					record.setActionPerformed(auditRecord.getActionPerformed());
					if(auditRecord.getProjectName() == null){
						record.setProjectName("<center>---</center>");
					}else{
						record.setProjectName(auditRecord.getProjectName());
					}
					
					record.setIpOrigin(auditRecord.getIpOrigin());
					SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
					String dateformat = formatt.format(auditRecord.getActionDate());
					record.setActionDateandTime(dateformat);
					auditLog.add(record);
				}
				UserRole userRole = userRoleService.findByUserId(id);
				Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
				String roleName = r.getName();
				if(roleName.equalsIgnoreCase("System Administrator")){
					model.addAttribute("project_list",projectService.list());
				}else{
					UserTenant userTenant = userTenantService.findByUserId(id);
					model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
				}
			} catch (Exception e) {
				log.error(e.toString());
				e.printStackTrace();
			}
			
			
			model.addAttribute("user_list",userProfileService.
					getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
			model.addAttribute("audit_log_list",auditLog);
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * This method is get the logs list from mongodb by applying the filters
	 */
	@RequestMapping(value = "/searchlogs",method = RequestMethod.POST)
	public String searchLogs(@ModelAttribute("crudObj") @Valid AuditLogRecord entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			Integer id = (Integer)request.getSession().getAttribute("userid");
			AuditType actionType = entity.getActionType();
			Integer userId = entity.getUserId();
			String projectName = entity.getProjectName();
			Date startDate = entity.getStartDate();
			Date endDate = entity.getEndDate();
			HttpSession session = request.getSession();
			String type = session.getAttribute("type").toString();
			session.setAttribute("entity",entity);
			model.addAttribute("auditlogs","auditlogs");
			model.addAttribute("entity", entity);
			
			if(type.equals("appadmin")){
				model.addAttribute("flag","false");
			}
			else
				model.addAttribute("flag","true");
			List<AuditLogRecord> auditLog = new ArrayList<AuditLogRecord>();
			
			Query query = new Query();
			if(actionType != null){
				query.addCriteria(Criteria.where("actionType").is(actionType));
			}
			if(userId != null){
				query.addCriteria(Criteria.where("userId").is(userId));
			}
			if(userId == null){
				if(type.equals("appadmin"))
					query.addCriteria(Criteria.where("userId").is(id));
				else
					query.addCriteria(Criteria.where("userId").ne(null));
			}
			if(projectName != ""){
				query.addCriteria(Criteria.where("projectName").is(projectName));
			}
			if(startDate != null && endDate != null){
				query.addCriteria(Criteria.where("actionDate").gt(startDate).andOperator(Criteria.where("actionDate").lte(endDate)));
			}
			if(startDate != null && endDate == null){
				query.addCriteria(Criteria.where("actionDate").gt(startDate));
			}
			if(endDate != null && startDate == null){
				query.addCriteria(Criteria.where("actionDate").lte(endDate));
			}
			
			int count = auditLogService.getNumberOfRecords(query);
			int limitsPerPage = 10;
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			if(count <10){
				limitsPerPage = count;
			}
			
			model.addAttribute("numberofpages", numberOfPages);
			model.addAttribute("currentpage", 0);
			
			query.skip(0 * limitsPerPage);
			query.limit(limitsPerPage);  
			
			List<AuditLogRecord> recordList = auditLogService.executeQuery(query);
			
			Iterator<AuditLogRecord> iterator = recordList.iterator();
			while(iterator.hasNext()){
				AuditLogRecord record = new AuditLogRecord();
				AuditLogRecord auditRecord = iterator.next();
				/*if(auditRecord.getUserId() != null){
					User u = userProfileService.getById(auditRecord.getUserId());
					record.setUserName(u.getUserName());
				}*/
				record.setUserName(auditRecord.getUserName());
				record.setActionType(auditRecord.getActionType());
				record.setActionPerformed(auditRecord.getActionPerformed());
				if(auditRecord.getProjectName() == null){
					record.setProjectName("<center>---</center>");
				}else{
					record.setProjectName(auditRecord.getProjectName());
				}
					record.setIpOrigin(auditRecord.getIpOrigin());
					SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					String dateformat = null;
					if(auditRecord.getActionDate()!=null)
					dateformat = formatt.format(auditRecord.getActionDate());
					record.setActionDateandTime(dateformat);
				
				auditLog.add(record);
			}
			UserRole userRole = userRoleService.findByUserId((Integer)request.getSession().getAttribute("userid"));
			Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
			String roleName = r.getName();
			if(roleName.equalsIgnoreCase("System Administrator")){
				model.addAttribute("project_list",projectService.list());
			}else{
				UserTenant userTenant = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
				model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
			}
			model.addAttribute("user_list",userProfileService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
			model.addAttribute("audit_log_list",auditLog);
			model.addAttribute("auditlogs","auditlogs");
			entity.setStartDate(startDate);
			entity.setEndDate(endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return "home";
	}
	
	/**
	 * This method is to get the list of audit logs of the first page
	 */
	@RequestMapping(value = "viewalllogs", method = RequestMethod.GET)
	public String viewauditLogs(Model model,HttpServletRequest request){
		try{
			Integer id = (Integer)request.getSession().getAttribute("userid");
			List<AuditLogRecord> auditLog = new ArrayList<AuditLogRecord>();
			request.getSession().setAttribute("entity",null);
			request.getSession().setAttribute("type","sysadmin");
			int limitsPerPage = 10;
			
			Query query =  new Query();
			query.addCriteria(Criteria.where("userId").ne(null));
			int count = auditLogService.getNumberOfRecords(query);
			//int count = auditLogService.allLogCount();
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			if(count <10){
				limitsPerPage = count;
			}
			model.addAttribute("numberofpages", numberOfPages);
			//List<AuditLogRecord> list = auditLogService.getByPage(0, limitsPerPage,id);
			List<AuditLogRecord> list = auditLogService.getAllLogsByPage(0, limitsPerPage);
			Iterator<AuditLogRecord> iterator = list.iterator();
			while(iterator.hasNext()){
				AuditLogRecord record = new AuditLogRecord();
				AuditLogRecord auditRecord = iterator.next();
				//User u = userProfileService.getById(auditRecord.getUserId());
				//record.setUserName(u.getUserName());
				record.setUserName(auditRecord.getUserName());
				record.setActionType(auditRecord.getActionType());
				record.setActionPerformed(auditRecord.getActionPerformed());
				if(auditRecord.getProjectName() == null){
					record.setProjectName("<center>---</center>");
				}else{
					record.setProjectName(auditRecord.getProjectName());
				}
				
				record.setIpOrigin(auditRecord.getIpOrigin());
				SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String dateformat = null;
				if(auditRecord.getActionDate()!=null)
				dateformat = formatt.format(auditRecord.getActionDate());
				record.setActionDateandTime(dateformat);
				auditLog.add(record);
			}
			UserRole userRole = userRoleService.findByUserId(id);
			Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
			String roleName = r.getName();
			if(roleName.equalsIgnoreCase("System Administrator")){
				model.addAttribute("project_list",projectService.list());
			}else{
				UserTenant userTenant = userTenantService.findByUserId(id);
				model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
			}
			List<User> listUsers = userProfileService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid"));
			model.addAttribute("user_list",listUsers);
			model.addAttribute("audit_log_list",auditLog);
			model.addAttribute("currentpage", 0);
			model.addAttribute("auditlogs","auditlogs");
			model.addAttribute("flag","true");
		}catch(Exception e){
			log.error(String.valueOf(e));
			e.printStackTrace();
		}
		
		return "home";
	}
	
	/**
	 * This method is to get the list of audit logs of the first page for the SysAdmin
	 */
	@RequestMapping(value="viewlogs", method = RequestMethod.GET)
	public String viewAllauditLogs(Model model,HttpServletRequest request){
		try{
			List<AuditLogRecord> auditLog = new ArrayList<AuditLogRecord>();
			Integer id = (Integer)request.getSession().getAttribute("userid");
			request.getSession().setAttribute("entity",null);
			request.getSession().setAttribute("type","appadmin");
			int limitsPerPage = 10;
			int count = auditLogService.logCount(id);
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			if(count <10){
				limitsPerPage = count;
			}
			
			model.addAttribute("numberofpages", numberOfPages);
			List<AuditLogRecord> list = auditLogService.getByPage(0, limitsPerPage,id);
			
			Iterator<AuditLogRecord> iterator = list.iterator();
			while(iterator.hasNext()){
				AuditLogRecord record = new AuditLogRecord();
				AuditLogRecord auditRecord = iterator.next();
				//User u = userProfileService.getById(auditRecord.getUserId());
				//record.setUserName(u.getUserName());
				record.setUserName(auditRecord.getUserName());
				record.setActionType(auditRecord.getActionType());
				record.setActionPerformed(auditRecord.getActionPerformed());
				if(auditRecord.getProjectName() == null){
					record.setProjectName("<center>---</center>");
				}else{
					record.setProjectName(auditRecord.getProjectName());
				}
				
				record.setIpOrigin(auditRecord.getIpOrigin());
				SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String dateformat = null;
				if(auditRecord.getActionDate()!=null)
				dateformat = formatt.format(auditRecord.getActionDate());
				record.setActionDateandTime(dateformat);
				auditLog.add(record);
			}
			UserRole userRole = userRoleService.findByUserId(id);
			Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
			String roleName = r.getName();
			if(roleName.equalsIgnoreCase("System Administrator")){
				model.addAttribute("project_list",projectService.list());
			}else{
				UserTenant userTenant = userTenantService.findByUserId(id);
				model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
			}
			model.addAttribute("user_list",userProfileService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
			model.addAttribute("audit_log_list",auditLog);
			model.addAttribute("currentpage", 0);
			model.addAttribute("auditlogs","auditlogs");
			model.addAttribute("flag","false");
		}catch(Exception e){
			log.error(String.valueOf(e));
			e.printStackTrace();
		}
		
		return "home";
	}
	
	
	/**
	 * this method to give the auditlogs based on the pagination
	 */
	
	@RequestMapping(value="viewpagelogs/{page}",method = RequestMethod.GET)
	public String viewPageLogs(@PathVariable Integer page,Model model,HttpServletRequest request){
		
		HttpSession session = request.getSession();
		Integer id = (Integer)request.getSession().getAttribute("userid");
		/*request.getSession().setAttribute("type","sysadmin");*/
		AuditLogRecord entity = null; 
		AuditType actionType = null;
		Integer userId = null;
		String projectName = null;
		Date startDate = null;
		Date endDate = null;
		Object audit = session.getAttribute("entity");
			Query query = new Query();
			if(audit != null ){
			entity = (AuditLogRecord)audit;
			actionType = entity.getActionType();
			userId = entity.getUserId();
			projectName = entity.getProjectName();
			startDate = entity.getStartDate();
			endDate = entity.getEndDate();
		}
		/*else{
			query.addCriteria(Criteria.where("userId").is(id));
		}*/
		if(actionType != null){
			query.addCriteria(Criteria.where("actionType").is(actionType));
		}
		if(userId != null){
			query.addCriteria(Criteria.where("userId").is(userId));
		}
		else
			query.addCriteria(Criteria.where("userId").is(id));
		if(projectName != "" && projectName != null){
			query.addCriteria(Criteria.where("projectName").is(projectName));
		}
		if(startDate != null && endDate != null){
			query.addCriteria(Criteria.where("actionDate").gt(startDate).andOperator(Criteria.where("actionDate").lte(endDate)));
		}
		if(startDate != null && endDate == null){
			query.addCriteria(Criteria.where("actionDate").gt(startDate));
		}
		if(endDate != null && startDate == null){
			query.addCriteria(Criteria.where("actionDate").lte(endDate));
		}
		int count = auditLogService.getNumberOfRecords(query);
		int limitsPerPage = 10;
		int numberOfPages = count/10;
		if(count % 10 != 0){
			numberOfPages = numberOfPages+1;
		}
		if(count <10){
			limitsPerPage = count;
		}
		model.addAttribute("numberofpages", numberOfPages);
		model.addAttribute("currentpage", page);
		
		//query.
		query.skip(page * limitsPerPage);
		query.limit(limitsPerPage);  
		
		List<AuditLogRecord> auditLog = new ArrayList<AuditLogRecord>();
		/*Integer id = (Integer)request.getSession().getAttribute("userid");
		int limitsPerPage = 10;
		int count = auditLogService.logCount(id);
		int noOfPages = count/10;
		if(count/10 != 0){
			noOfPages = noOfPages+1;
		}
		if(page == noOfPages-1){
			limitsPerPage = count % 10;
		}
		model.addAttribute("numberofpages", noOfPages);
		model.addAttribute("currentpage", page);
		List<AuditLogRecord> list = auditLogService.getByPage(page, limitsPerPage,id);*/
		
		List<AuditLogRecord> list = auditLogService.executeQuery(query);
		Iterator<AuditLogRecord> iterator = list.iterator();
		while(iterator.hasNext()) {
			AuditLogRecord record = new AuditLogRecord();
			AuditLogRecord auditRecord = iterator.next();
			//User u = userProfileService.getById(auditRecord.getUserId());
			record.setUserName(auditRecord.getUserName());
			record.setActionType(auditRecord.getActionType());
			record.setActionPerformed(auditRecord.getActionPerformed());
			if(auditRecord.getProjectName() == null){
				record.setProjectName("<center>---</center>");
			}else{
				record.setProjectName(auditRecord.getProjectName());
			}
			record.setIpOrigin(auditRecord.getIpOrigin());
			String dateformat = null;
			SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			if(auditRecord.getActionDate()!=null)
			dateformat = formatt.format(auditRecord.getActionDate());
			record.setActionDateandTime(dateformat);
			auditLog.add(record);
		}
		
		UserRole userRole = userRoleService.findByUserId(id);
		Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
		String roleName = r.getName();
		if(roleName.equalsIgnoreCase("System Administrator")){
			model.addAttribute("project_list",projectService.list());
		}else{
			UserTenant userTenant = userTenantService.findByUserId(id);
			model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
		}
		model.addAttribute("user_list",userProfileService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
		model.addAttribute("audit_log_list",auditLog);
		model.addAttribute("auditlogs","auditlogs");
		model.addAttribute("flag", "false");
		model.addAttribute("entity", entity);
		return "home";
	}
	
	@RequestMapping(value="viewpagealllogs/{page}",method = RequestMethod.GET)
	public String viewPageAllLogs(@PathVariable Integer page,Model model,HttpServletRequest request){
		
		HttpSession session = request.getSession();
		Integer id = (Integer)request.getSession().getAttribute("userid");
		AuditLogRecord entity = null; 
		AuditType actionType = null;
		Integer userId = null;
		String projectName = null;
		Date startDate = null;
		Date endDate = null;
		Object audit = session.getAttribute("entity");
			Query query =  new Query();
			if(audit != null ){
			
			entity = (AuditLogRecord)audit;
			actionType = entity.getActionType();
			userId = entity.getUserId();
			projectName = entity.getProjectName();
			startDate = entity.getStartDate();
			endDate = entity.getEndDate();
		}
		/*else{
			//query.addCriteria(Criteria.where("userId").is(id));
			query.addCriteria(Criteria.where("userId").ne(null));
		}*/
		if(actionType != null){
			query.addCriteria(Criteria.where("actionType").is(actionType));
		}
		if(userId != null){
			query.addCriteria(Criteria.where("userId").is(userId));
		}
		else{
			query.addCriteria(Criteria.where("userId").ne(null));
		}
		if(projectName != "" && projectName != null){
			query.addCriteria(Criteria.where("projectName").is(projectName));
		}
		if(startDate != null && endDate != null){
			query.addCriteria(Criteria.where("actionDate").gt(startDate).andOperator(Criteria.where("actionDate").lte(endDate)));
		}
		if(startDate != null && endDate == null){
			query.addCriteria(Criteria.where("actionDate").gt(startDate));
		}
		if(endDate != null && startDate == null){
			query.addCriteria(Criteria.where("actionDate").lte(endDate));
		}
		int count = auditLogService.getNumberOfRecords(query);
		int limitsPerPage = 10;
		int numberOfPages = count/10;
		if(count % 10 != 0){
			numberOfPages = numberOfPages+1;
		}
		if(count <10){
			limitsPerPage = count;
		}
		model.addAttribute("numberofpages", numberOfPages);
		model.addAttribute("currentpage", page);
		
		//query.
		query.skip(page * limitsPerPage);
		query.limit(limitsPerPage);  
	
		
		List<AuditLogRecord> auditLog = new ArrayList<AuditLogRecord>();
		/*Integer id = (Integer)request.getSession().getAttribute("userid");
		int limitsPerPage = 10;
		int count = auditLogService.logCount(id);
		int noOfPages = count/10;
		if(count/10 != 0){
			noOfPages = noOfPages+1;
		}
		if(page == noOfPages-1){
			limitsPerPage = count % 10;
		}
		model.addAttribute("numberofpages", noOfPages);
		model.addAttribute("currentpage", page);
		List<AuditLogRecord> list = auditLogService.getByPage(page, limitsPerPage,id);*/
		List<AuditLogRecord> list = auditLogService.executeQuery(query);
		Iterator<AuditLogRecord> iterator = list.iterator();
		String roleName = null;
		try{
		while(iterator.hasNext()) {
			AuditLogRecord record = new AuditLogRecord();
			AuditLogRecord auditRecord = iterator.next();
			//User u = userProfileService.getById(auditRecord.getUserId());
			record.setUserName(auditRecord.getUserName());
			record.setActionType(auditRecord.getActionType());
			record.setActionPerformed(auditRecord.getActionPerformed());
			if(auditRecord.getProjectName() == null){
				record.setProjectName("<center>---</center>");
			}else{
				record.setProjectName(auditRecord.getProjectName());
			}
			record.setIpOrigin(auditRecord.getIpOrigin());
			SimpleDateFormat formatt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String dateformat = null;
			if(auditRecord.getActionDate()!=null)
				dateformat = formatt.format(auditRecord.getActionDate());
			record.setActionDateandTime(dateformat);
			auditLog.add(record);
		}
		
		UserRole userRole = userRoleService.findByUserId(id);
		Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
		roleName = r.getName();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(roleName.equalsIgnoreCase("System Administrator")){
			model.addAttribute("project_list",projectService.list());
		}else{
			UserTenant userTenant = userTenantService.findByUserId(id);
			model.addAttribute("project_list",projectService.getProjectsByUserId(userTenant.getUserTenantComp().getTenantId()));
		}
		model.addAttribute("user_list",userProfileService.getUsersBasedOnCreatedBy((Integer)request.getSession().getAttribute("userid")));
		model.addAttribute("audit_log_list",auditLog);
		model.addAttribute("entity", entity);
		model.addAttribute("auditlogs","auditlogs");
		model.addAttribute("flag", "true");
		return "home";
	}
	
	
	
	
	@Override
	public QTAdminService<AuditLogRecord> getTService() {
		return auditLogService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}

}
