package com.infotree.qliktest.admin.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.infotree.qliktest.admin.entity.configs.BugTracking;
import com.infotree.qliktest.admin.entity.configs.EnterpriseSpy;
import com.infotree.qliktest.admin.entity.configs.ObjectSpy;
import com.infotree.qliktest.admin.entity.configs.ParallelNodes;
import com.infotree.qliktest.admin.entity.configs.PerformanceTesting;
import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserRole;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.BugTrackingConfigService;
import com.infotree.qliktest.admin.service.referencedata.EnterpriseSpyConfigService;
import com.infotree.qliktest.admin.service.referencedata.ObjectspyConfigService;
import com.infotree.qliktest.admin.service.referencedata.ParlNodesConfigService;
import com.infotree.qliktest.admin.service.referencedata.PerformanceConfigService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserRoleService;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController{
	

	/**
	 *  ObjectSpy Configuration begin
	 */
	
	private static final Logger log = LoggerFactory.getLogger(ConfigurationController.class);
	
	@Autowired
	private ObjectspyConfigService objectspyConfigService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	/**
	 * To create the objectspy configurations
	 */
	@RequestMapping(value = "/createobjspy", method = RequestMethod.GET)
    public String createobjspy(Model model) {
		try {
			model.addAttribute("crudObj", new ObjectSpy());
			model.addAttribute("createobjspyconfig", "createobjspyconfig");
			model.addAttribute("objspy_list",objectspyConfigService.findByOrder());
			model.addAttribute("project_list", projectService.getActiveProjects());
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * To edit the objectspy configuration
	 */
	@RequestMapping(value = "/editobjspy", method = RequestMethod.GET)
    public String editobjspy(Model model) {
		try{
			List<ObjectSpy> spylist = objectspyConfigService.findByOrder();
			List<ObjectSpy> objectspyList = new ArrayList<ObjectSpy>();
			if(spylist != null){
				Iterator<ObjectSpy> iterator = spylist.iterator();
				while(iterator.hasNext()){
					ObjectSpy p = iterator.next();
					if(p.getModifiedBy() != null){
						Integer id = Integer.parseInt(p.getModifiedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(p.getCreatedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}
					objectspyList.add(p);
				}
				
			}
			model.addAttribute("crudObj", new ObjectSpy());
			model.addAttribute("objspy_list",objectspyList);
			model.addAttribute("editobjspyconfig", "editobjspyconfig");
			
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * To view all the objectspy configuraitons
	 */
	@RequestMapping(value = "/viewobjspy", method = RequestMethod.GET)
    public String viewobjspy(Model model,HttpServletRequest request) {
		
		try{
			List<ObjectSpy> objectsList = objectspyConfigService.findByOrder();
			Iterator<ObjectSpy> iterator = objectsList.iterator();
			List<ObjectSpy> objspylist = new ArrayList<ObjectSpy>();
			while(iterator.hasNext()){
				ObjectSpy spy = iterator.next();
				ObjectSpy object = new ObjectSpy();
				object.setConfigurationname(spy.getConfigurationname());
				object.setBrowser(spy.getBrowser());
				object.setPortNumber(spy.getPortNumber());
				object.setElementwaittime(spy.getElementwaittime());
				Project p = projectService.getById(spy.getProjectId());
				object.setProjectName(p.getName());
				object.setUrl(spy.getUrl());
				objspylist.add(object);
			}
			/**
			 * Preparing the auditlog record
			 */
			
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
			record.setActionType(AuditType.VIEW);
			record.setActionData("Viewd the objectspy configurations");
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
			model.addAttribute("objspy_list",objspylist);
			model.addAttribute("project_list", projectService.getActiveProjects());
			model.addAttribute("crudObj",new ObjectSpy());
			model.addAttribute("viewobjspyconfig", "viewobjspyconfig");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
	
		return "home";
	}
	/**
	 * To save a new objectspy configuration
	 */
	@RequestMapping(value = "/saveobjspy", method = RequestMethod.POST)
	public String saveObjspyConfiguration(@ModelAttribute("crudObj") @Valid ObjectSpy entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		ObjectSpy spy = null;
		try {
			spy = objectspyConfigService.findByConfigName(entity.getConfigurationname());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(spy == null){
			ObjectSpy objectspy = new ObjectSpy();
			objectspy.setConfigurationname(entity.getConfigurationname());
			objectspy.setProjectId(entity.getProjectId());
			objectspy.setBrowser(entity.getBrowser());
			objectspy.setElementwaittime(entity.getElementwaittime());
			objectspy.setUrl(entity.getUrl());
			objectspy.setPortNumber(entity.getPortNumber());
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
			
			objectspy.setCreatedDate(dateInIndia);
			objectspy.setCreatedBy(entity.getCreatedBy());
			objectspy.setVersion(entity.getVersion());
			try {
				objectspyConfigService.save(objectspy);
			} catch (Exception e) {
				e.printStackTrace();
			}
			/**
			 * Preparing the auditlog record
			 */
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			record.setActionType(AuditType.CREATE);
			record.setActionPerformed("created new objectspy configuration >> "+entity.getConfigurationname());
			record.setActionData("Configuration Name :"+entity.getConfigurationname());
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
			
			model.addAttribute("configurationcreated", "<font color='green' size='3'>Configuration with '"+entity.getConfigurationname()+"' Created Successfully");
		}else{
			model.addAttribute("configurationcreated", "<font color='red' size='3'>Configuration with '"+spy.getConfigurationname()+"' already exists");
		}
		model.addAttribute("crudObj", new ObjectSpy());
		entity.setCreatedDate(null);
		entity.setModifiedDate(null);
		model.addAttribute("createobjspyconfig", "createobjspyconfig");
		model.addAttribute("project_list", projectService.getActiveProjects());
		return "home";
	}
	/**
	 * To give the UI which will edit the objectspy configuration
	 */
	@RequestMapping(value="objspy/{objspyid}/updateobjspy", method=RequestMethod.GET)
	public String updateobjspyById(@PathVariable Integer objspyid,Model model) {
		
		try{
			ObjectSpy objectspy = objectspyConfigService.getById(objspyid);
			List<Project> projList = new ArrayList<Project>();
			Project p = projectService.getById(objectspy.getProjectId());
			projList.add(p);
			model.addAttribute("projList", projList);
			
			model.addAttribute("project_list", projectService.getActiveProjects());
			model.addAttribute("crudObj", objectspy);
			
			model.addAttribute("updateobjectspy", "updateobjectspy");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * To update the objectspy configuration which was edited
	 */
	@RequestMapping(value = "/updateobjspy", method = RequestMethod.POST)
	public String updateObjspyConfiguration(@ModelAttribute("crudObj") @Valid ObjectSpy entity,BindingResult bindingResult,Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			ObjectSpy spy = objectspyConfigService.findByNameAndIdNotIn(entity.getConfigurationname(), entity.getId());
			if(spy != null){
				model.addAttribute("configurationupdated","<font color='red' size='3'>Configuration With '"+entity.getConfigurationname()+"' Already Exists");
			}
			else{
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
					entity.setModifiedDate(dateInIndia);
					entity.setConfigurationname(entity.getConfigurationname().trim());
					objectspyConfigService.updateObjspyConfiguration(entity);
				/*
				 * Preparing the auditlog record
				 */
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				record.setActionType(AuditType.MODIFY);
				record.setActionPerformed("updated objectspy configuration new Permission");
				record.setActionData("Configuration Name :"+entity.getConfigurationname());
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
				model.addAttribute("configurationupdated","<font color='green' size='3'>Configuration With '"+entity.getConfigurationname()+"' Updated Successfully");
			}
			List<ObjectSpy> spylist = objectspyConfigService.findByOrder();
			List<ObjectSpy> objectspyList = new ArrayList<ObjectSpy>();
			if(spylist != null){
				
				Iterator<ObjectSpy> iterator = spylist.iterator();
				while(iterator.hasNext()) {
					ObjectSpy p = iterator.next();
					if(p.getModifiedBy() != null){
						Integer userid = Integer.parseInt(p.getModifiedBy());
						User u = userService.getById(userid);
						p.setCreatedName(u.getUserName());
					}else{
						Integer userid = Integer.parseInt(p.getCreatedBy());
						User u = userService.getById(userid);
						p.setCreatedName(u.getUserName());
					}
					objectspyList.add(p);
				}
				
			}
			model.addAttribute("editobjspyconfig", "editobjspyconfig");
			model.addAttribute("objspy_list", objectspyList);
			model.addAttribute("crudObj", entity);
		} catch (NumberFormatException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**@ModelAttribute("objspy_list")
    public List<ObjectSpy> objspylist(WebRequest request,HttpServletRequest request1,HttpServletResponse response) {
		
		return objectspyConfigService.list();
    }*/
	
	/**
	 *  ObjectSpy Configuration end
	 */
		
	/**
	 *  Enterprise Configuration begin
	 */
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private EnterpriseSpyConfigService entpspyConfigService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private RoleService roleService;
	/**
	 * This method is to create the enterprise configurations
	 */
	@RequestMapping(value = "/createentpspy", method = RequestMethod.GET)
    public String createentpspy(Model model,HttpServletRequest request) {
		model.addAttribute("crudObj", new EnterpriseSpy());
		model.addAttribute("project_list", projectService.list());
		/*List<User> user = userService.list();
		List<User> userList = new ArrayList<User>();
		if(user != null){
			Iterator<User> itr = user.iterator();
			while(itr.hasNext()){
				User us = itr.next();
				UserRole userRole = userRoleService.findByUserId(us.getId());
				if(userRole != null){
					Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
					if(!r.getName().equalsIgnoreCase("System Administrator")){
						if(!r.getName().equalsIgnoreCase("Application Administrator")){
							userList.add(us);
						}
					}
				}else{
					userList.add(us);
				}
				
			}
		}*/
		//model.addAttribute("user_list",userList);
		model.addAttribute("createentpspyconfig", "createentpspyconfig");
		return "home";
	}
	/**
	 * to edit the enterprise configurations
	 */
	@RequestMapping(value = "/editentpspy", method = RequestMethod.GET)
    public String editentpspy(Model model) {
		try{
			model.addAttribute("crudObj", new EnterpriseSpy());
			model.addAttribute("entpspy_list", entpspyConfigService.findByConfigType("entp"));
			model.addAttribute("editentpspyconfig", "editentpspyconfig");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * To view the enterprise configurations
	 */
	@RequestMapping(value = "/viewentpspy", method = RequestMethod.GET)
    public String viewentpspy(Model model) {
		try{
			model.addAttribute("entpspy_list", entpspyConfigService.findByConfigType("entp"));
			model.addAttribute("viewentpspyconfig", "viewentpspyconfig");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * this method is to save the enterprise configurations
	 */
	@RequestMapping(value = "/saveentpspy", method = RequestMethod.POST)
	public String saveObjspyConfiguration(@ModelAttribute("crudObj") @Valid EnterpriseSpy entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		EnterpriseSpy spy = entpspyConfigService.findByConfigName(entity.getConfigurationname());
		if(spy == null){
			EnterpriseSpy entpspy = new EnterpriseSpy();
			entpspy.setConfigurationname(entity.getConfigurationname().trim());
			entpspy.setPath(entity.getPath());
			entpspy.setElementwaittime(entity.getElementwaittime());
			entpspy.setTypeEntpc(entity.getTypeEntpc());
			entpspy.setProjectid(entity.getProjectid());
			entpspy.setCreatedBy(entity.getCreatedBy());
			
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
			entpspy.setCreatedDate(dateInIndia);
			try {
				entpspyConfigService.save(entpspy);
				/**
				 * Preparing the audit log for the user
				 */
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				record.setActionType(AuditType.CREATE);
				Project p = projectService.getById(entpspy.getProjectid());
				record.setProjectName(p.getName());
				record.setActionPerformed("created enterprise configurations >> "+entity.getConfigurationname());
				record.setActionData("Configuration Name :"+entity.getConfigurationname());
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			model.addAttribute("configurationcreated", "<font color='green' size='3'>Configuration with '"+entity.getConfigurationname()+"' Created Successfully");
		}else{
			model.addAttribute("configurationcreated", "<font color='red' size='3'>Configuration with '"+spy.getConfigurationname()+"' already exists");
		}
		model.addAttribute("crudObj", new EnterpriseSpy());
		model.addAttribute("createentpspyconfig", "createentpspyconfig");
		model.addAttribute("project_list", projectService.list());
		model.addAttribute("user_list",userService.list());
		return "home";
	}
	/**
	 * to return the selected enterprise configuration which will be editable mode
	 */
	@RequestMapping(value="entpspy/{entpspyid}/updateentpspy", method=RequestMethod.GET)
	public String updateentpspyById(@PathVariable Integer entpspyid,Model model,HttpServletRequest request) {
		try{
			EnterpriseSpy entpspy = entpspyConfigService.getById(entpspyid);
			model.addAttribute("crudObj", entpspy);
			model.addAttribute("project_list", projectService.list());
			/*List<User> user = userService.list();
			List<User> userList = new ArrayList<User>();
			if(user != null){
				Iterator<User> itr = user.iterator();
				while(itr.hasNext()){
					User us = itr.next();
					UserRole userRole = userRoleService.findByUserId(us.getId());
					if(userRole != null){
						Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
						if(!r.getName().equalsIgnoreCase("System Administrator")){
							if(!r.getName().equalsIgnoreCase("Application Administrator")){
								userList.add(us);
							}
						}
					}else{
						userList.add(us);
					}
				}
			}
			model.addAttribute("user_list",userList);*/
			model.addAttribute("updateentpspy", "updateentpspy");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * To update  the enterprise configuration which was edited
	 */
	@RequestMapping(value = "/updateentpspy", method = RequestMethod.POST)
	public String updateentpspyConfiguration(@ModelAttribute("crudObj") @Valid EnterpriseSpy entity,BindingResult bindingResult,Model model,HttpServletRequest request,HttpServletResponse response){
		
		EnterpriseSpy spy = entpspyConfigService.findByNameAndIdNotIn(entity.getConfigurationname(), entity.getId());
		
		if(spy != null){
			model.addAttribute("configurationupdated","<font color='red' size='3'>Configuration With '"+entity.getConfigurationname()+"' Already Exists</font>");
		}
		else{
			
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			String clientZone = clientTimeZone.getID();
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID(clientZone);
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			
			entity.setModifiedDate(dateInIndia);
			entity.setConfigurationname(entity.getConfigurationname().trim());
			entpspyConfigService.updateEnterpriseSpyConfiguration(entity);
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			record.setActionType(AuditType.MODIFY);
			record.setActionPerformed("updated enterprise configuration new Permission");
			record.setActionData("Configuration Name :"+entity.getConfigurationname());
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
			model.addAttribute("configurationupdated","<font color='green' size='3'>Configuration With '"+entity.getConfigurationname()+"' Updated Successfully");
		}
		model.addAttribute("editentpspyconfig", "editentpspyconfig");
		model.addAttribute("entpspy_list", entpspyConfigService.findByConfigType("entp"));
		model.addAttribute("crudObj", entity);
		return "home";
	}
	
	/**
	 *  entp configuration end
	 */
	
	/**
	 *  parallel nodes configuration begin
	 */
	
	@Autowired
	private ParlNodesConfigService parallelNodesConfigService;
	/**
	 * To create the parallel nodes configuraion
	 */
	@RequestMapping(value = "/createprlnodes", method = RequestMethod.GET)
    public String createParallelNodes(Model model) {
		try{
			model.addAttribute("crudObj", new ParallelNodes());
			model.addAttribute("createprlnodesconfig", "createprlnodesconfig");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * To edit the parallel nodes configuraion
	 */
	@RequestMapping(value = "/editprlnodes", method = RequestMethod.GET)
    public String editprlnodes(Model model) {
		try{
			model.addAttribute("crudObj", new ParallelNodes());
			model.addAttribute("editprlNodesconfig", "editprlNodesconfig");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
	
		return "home";
	}
	/**
	 * To view the parallel nodes configuraion
	 */
	@RequestMapping(value = "/viewprlnodes", method = RequestMethod.GET)
    public String viewprlnodes(Model model) {
		try{
			model.addAttribute("viewprlnodesconfig", "viewprlnodesconfig");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * To save the new parallel nodes configuration
	 */
	@RequestMapping(value = "/saveprlnodes", method = RequestMethod.POST)
	public String saveprlnodesConfiguration(@ModelAttribute("crudObj") @Valid ParallelNodes entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		ParallelNodes spy = parallelNodesConfigService.findByConfigName(entity.getNodeurl());
		if(spy == null){
			ParallelNodes parlnode = new ParallelNodes();
			parlnode.setNodeurl(entity.getNodeurl());
			parlnode.setIpAddress(entity.getIpAddress());
			parlnode.setPlatform(entity.getPlatform());
			parlnode.setStatus(entity.getStatus());
			parlnode.setCreatedBy(entity.getCreatedBy());
			
			/*DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			*/
			
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			String clientZone = clientTimeZone.getID();
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID(clientZone);
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			
			parlnode.setCreatedDate(dateInIndia);
			try {
				parallelNodesConfigService.save(parlnode);
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				record.setActionType(AuditType.CREATE);
				record.setActionPerformed("created parallel configuration for url >> "+entity.getNodeurl());
				record.setActionData("The node url is :"+entity.getNodeurl());
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			model.addAttribute("configurationcreated", "<font color='green' size='3'>Node with '"+entity.getNodeurl()+"' Created Successfully");
		}else{
			model.addAttribute("configurationcreated", "<font color='red' size='3'>Node with '"+spy.getNodeurl()+"' already exists");
		}
		model.addAttribute("crudObj", new ParallelNodes());
		model.addAttribute("createprlnodesconfig", "createprlnodesconfig");
		return "home";
	}
	/**
	 * To select one parallel node configuration which is going to be edited
	 */
	@RequestMapping(value="prlnodes/{prlnodeid}/updateprlnodes", method=RequestMethod.GET)
	public String updateprlnodesById(@PathVariable Integer prlnodeid,Model model) {
		try{
			ParallelNodes entpspy = parallelNodesConfigService.getById(prlnodeid);
			model.addAttribute("crudObj", entpspy);
			model.addAttribute("updateprlnodes", "updateprlnodes");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * To update the edited configuration
	 */
	@RequestMapping(value = "/updateprlnodes", method = RequestMethod.POST)
	public String updateprlnodesConfiguration(@ModelAttribute("crudObj") @Valid ParallelNodes entity,BindingResult bindingResult,Model model,HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		Integer id = (Integer)session.getAttribute("userid");
		ParallelNodes prl = parallelNodesConfigService.findByNameAndIdNotIn(entity.getNodeurl(), entity.getId());
		if(prl != null){
			model.addAttribute("configurationupdated","<font color='red' size='3'>Node With '"+prl.getNodeurl()+"' Already Exists");
		}
		else{
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
			
			entity.setModifiedDate(dateInIndia);
			parallelNodesConfigService.updateParallelNodesConfiguration(entity);
			/**
			 * Preparing the audit log record
			 */
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			record.setActionType(AuditType.MODIFY);
			record.setActionPerformed("updated parallel nodes configuration");
			record.setActionData("Configuration Node url :"+entity.getNodeurl());
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
			model.addAttribute("configurationupdated","<font color='green' size='3'>Node With '"+entity.getNodeurl()+"' Updated Successfully");
		}
		model.addAttribute("editprlNodesconfig", "editprlNodesconfig");
		model.addAttribute("prlNodes_list", parallelNodesConfigService.getParallelNodesCreated(id+""));
		model.addAttribute("crudObj", entity);
		return "home";
	}
	/**
	 * Retrieving all the parallel nodes configurations
	 */
	@ModelAttribute("prlNodes_list")
    public List<ParallelNodes> prlNodesList(WebRequest request,HttpServletRequest request1,HttpServletResponse response) {
		
		return parallelNodesConfigService.findByOrder();
    }
	
	/**
	 *  Parallel Nodes configuration ended
	 */
	/**
	 * Bug Tracking Tool Configuration
	 */
	@Autowired
	private BugTrackingConfigService bugTrackingConfigService;
	/**
	 * method to create bug tracking configurations
	 */
	@RequestMapping(value = "/createbugtrk", method = RequestMethod.GET)
    public String createbugtrk(Model model) {
		try{
			model.addAttribute("crudObj", new BugTracking());
			model.addAttribute("createbugtrkconfig", "createbugtrkconfig");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
	
		return "home";
	}
	/**
	 * Method to update the bugtracking configurations
	 */
	@RequestMapping(value = "/editbugtrk", method = RequestMethod.GET)
    public String editbugtrk(Model model) {
		try{
			model.addAttribute("crudObj", new BugTracking());
			model.addAttribute("editbugtrkconfig", "editbugtrkconfig");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * Method to view the bugtracking configurations
	 */
	@RequestMapping(value = "/viewbugtrk", method = RequestMethod.GET)
    public String viewbugtrk(Model model) {
		try{
			model.addAttribute("viewbugtrkconfig", "viewbugtrkconfig");
		}catch(Exception e){
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * Method to save a new bugtracking configurations
	 */
	@RequestMapping(value = "/savebugtrk", method = RequestMethod.POST)
	public String savebugtrkConfiguration(@ModelAttribute("crudObj") @Valid BugTracking entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		BugTracking spy = bugTrackingConfigService.findByConfigName(entity.getConfigurationname());
		if(spy == null){
			BugTracking bugtrk = new BugTracking();
			bugtrk.setConfigurationname(entity.getConfigurationname().trim());
			bugtrk.setUrl(entity.getUrl());
			bugtrk.setCreatedBy(entity.getCreatedBy());
			
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
			
			bugtrk.setCreatedDate(dateInIndia);
			try {
				bugTrackingConfigService.save(bugtrk);
				/**
				 * Preparing the auditlog record
				 */
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				//record.setActionDate(entity.getCreatedDate());
				record.setActionType(AuditType.CREATE);
				record.setActionPerformed("created bugzilla configurations with name"+entity.getConfigurationname());
				record.setActionData("bugzilla configuration Name :"+entity.getConfigurationname());
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
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			model.addAttribute("configurationcreated", "<font color='green' size='3'>Configuration with '"+entity.getConfigurationname()+"' Created Successfully");
		}else{
			model.addAttribute("configurationcreated", "<font color='red' size='3'>Configuration with '"+spy.getConfigurationname()+"' already exists");
		}
		model.addAttribute("crudObj", new ObjectSpy());
		model.addAttribute("createbugtrkconfig", "createbugtrkconfig");
		return "home";
	}
	/**
	 * Method to update the bugtracking configuration
	 */
	@RequestMapping(value="bugtrk/{bugtrkid}/updatebugtrk", method=RequestMethod.GET)
	public String updatebugtrkById(@PathVariable Integer bugtrkid,Model model) {
		try{
			BugTracking bugtrk = bugTrackingConfigService.getById(bugtrkid);
			model.addAttribute("crudObj", bugtrk);
			model.addAttribute("updatebugtrkconfig", "updatebugtrkconfig");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * method to update the bugtracking configuration which was already edited
	 */
	@RequestMapping(value = "/updatebugtrk", method = RequestMethod.POST)
	public String updatebugtrkConfiguration(@ModelAttribute("crudObj") @Valid BugTracking entity,BindingResult bindingResult,Model model,HttpServletRequest request,HttpServletResponse response){
		BugTracking spy = bugTrackingConfigService.findByNameAndIdNotIn(entity.getConfigurationname(), entity.getId());
		
		if(spy != null){
			model.addAttribute("configurationupdated","<font color='red' size='3'>Configuration With '"+entity.getConfigurationname()+"' Already Exists");
		}
		else{
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
			
			entity.setModifiedDate(dateInIndia);
			entity.setConfigurationname(entity.getConfigurationname().trim());
			bugTrackingConfigService.updateBugTrackingConfiguration(entity);
			/**
			 * Preparing the auditlog record
			 */
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			//record.setActionDate(entity.getModifiedDate());
			record.setActionType(AuditType.MODIFY);
			record.setActionPerformed("updated bugzilla configuration ");
			record.setActionData("Configuration Name :"+entity.getConfigurationname());
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
			model.addAttribute("configurationupdated","<font color='green' size='3'>Configuration With '"+entity.getConfigurationname()+"' Updated Successfully");
		}
		model.addAttribute("editbugtrkconfig", "editbugtrkconfig");
		model.addAttribute("bugtrk_list", bugTrackingConfigService.findByOrder());
		model.addAttribute("crudObj", entity);
		return "home";
	}
	/**
	 * method to fetch all the bug tracking configurations
	 */
	@ModelAttribute("bugtrk_list")
    public List<BugTracking> bugtrklist(WebRequest request,HttpServletRequest request1,HttpServletResponse response) {
		
		return bugTrackingConfigService.findByOrder();
    }
	/**
	 * Bug Tracking Tool Configuration ended
	 */
	
	/**
	 * Performance testing configuration started
	 */
	@Autowired
	private PerformanceConfigService performanceConfigService;
	
	/**
	 * To create the performance testing configurations
	 */
	@RequestMapping(value = "/createperformanceconfigs", method = RequestMethod.GET)
    public String createPerformance(Model model,HttpServletRequest request) {
	
		try{
			model.addAttribute("crudObj", new PerformanceTesting());
			model.addAttribute("project_list", projectService.list());
			/*List<User> user = userService.list();
			List<User> userList = new ArrayList<User>();
			if(user != null){
				Iterator<User> itr = user.iterator();
				while(itr.hasNext()){
					User us = itr.next();
					UserRole userRole = userRoleService.findByUserId(us.getId());
					if(userRole != null){
						Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
						if(!r.getName().equalsIgnoreCase("System Administrator")){
							if(!r.getName().equalsIgnoreCase("Application Administrator")){
								userList.add(us);
							}
						}
					}else{
						userList.add(us);
					}
					
				}
			}
			model.addAttribute("user_list",userList);*/
			model.addAttribute("createperformanceconfigs", "createperformanceconfigs");
			
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
			
		}
		
		
		return "home";
	}
	/**
	 * This method is to save the new performance configuration
	 */
	@RequestMapping(value = "/savePerformance", method = RequestMethod.POST)
	public String savePerformanceConfiguration(@ModelAttribute("crudObj") @Valid PerformanceTesting entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		PerformanceTesting performance = performanceConfigService.findByName(entity.getName());
		if(performance == null){
			PerformanceTesting perform = new PerformanceTesting();
			perform.setName(entity.getName().trim());
			perform.setPort(entity.getPort());
			perform.setUrl(entity.getUrl());
			perform.setProjid(entity.getProjid());
			perform.setWaitTime(entity.getWaitTime());
			perform.setCreatedBy(entity.getCreatedBy());
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
			
			perform.setCreatedDate(dateInIndia);
			try {
				performanceConfigService.save(perform);
				
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				//record.setActionDate(entity.getCreatedDate());
				record.setActionType(AuditType.CREATE);
				record.setActionPerformed("created performance configurations");
				Project p = projectService.getById(perform.getProjid());
				record.setProjectName(p.getName());
				record.setActionData("Configuration Name :"+entity.getName());
				record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
				/*try {
					java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
					record.setIpOrigin(inetAddress.getHostAddress());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				record.setUserId((Integer)request.getSession().getAttribute("userid"));
				record.setUserName(request.getSession().getAttribute("username").toString());
				auditLogService.saveRecord(record);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			model.addAttribute("configurationcreated", "<font color='green' size='3'>Configuration with '"+entity.getName()+"' Created Successfully");
		}else{
			model.addAttribute("configurationcreated", "<font color='red' size='3'>Configuration with '"+entity.getName()+"' already exists");
		}
		model.addAttribute("crudObj", new PerformanceTesting());
		model.addAttribute("createperformanceconfigs", "createperformanceconfigs");
		model.addAttribute("project_list", projectService.list());
		return "home";
	}
	/**
	 * This method is to view all the performance configurations
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewperformanceconfigs", method = RequestMethod.GET)
    public String viewPerformanceConfig(Model model) {
		try{
			model.addAttribute("crudObj", new PerformanceTesting());
			model.addAttribute("viewperformanceconfigs", "viewperformanceconfigs");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * This method returns the UI to edit the performance configurations with list of configurations
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/editperformanceconfigs", method = RequestMethod.GET)
    public String editPerformanceConfig(Model model) {
		try{
			model.addAttribute("crudObj", new PerformanceTesting());
			model.addAttribute("project_list",projectService.list());
			model.addAttribute("editperformanceconfigs", "editperformanceconfigs");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@RequestMapping(value="performance/{performanceid}/updateperformance", method=RequestMethod.GET)
	public String updatePerformanceConfig(@PathVariable Integer performanceid,Model model,HttpServletRequest request) {
			
			try{
				PerformanceTesting performance = performanceConfigService.getById(performanceid);
				model.addAttribute("crudObj", performance);
				model.addAttribute("idToBeUpdated", performance.getId());
				model.addAttribute("updateperformanceconfigs", "updateperformanceconfigs");
				/*List<User> user = userService.list();
				List<User> userList = new ArrayList<User>();
				if(user != null){
					Iterator<User> itr = user.iterator();
					while(itr.hasNext()){
						User us = itr.next();
						UserRole userRole = userRoleService.findByUserId(us.getId());
						if(userRole != null){
							Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
							if(!r.getName().equalsIgnoreCase("System Administrator")){
								if(!r.getName().equalsIgnoreCase("Application Administrator")){
									userList.add(us);
								}
							}
						}else{
							userList.add(us);
						}
						
					}
				}
				model.addAttribute("user_list",userList);*/
				model.addAttribute("project_list",projectService.list());
			}catch(Exception e)
			{
				log.error(e.toString());
				e.printStackTrace();
			}
			
		return "home";
	}
	
	@RequestMapping(value = "/updatePerformance", method = RequestMethod.POST)
	public String updatePerformanceConfiguration(@ModelAttribute("crudObj") @Valid PerformanceTesting entity,BindingResult bindingResult,Model model,HttpServletRequest request,HttpServletResponse response){
		try{
			PerformanceTesting spy = performanceConfigService.findByNameAndIdNotIn(entity.getName(), entity.getId());
			
			if(spy != null){
				model.addAttribute("configurationupdated","<font color='red' size='3'>Configuration With '"+entity.getName()+"' Already Exists");
			}
			else{
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
				
				entity.setModifiedDate(dateInIndia);
				entity.setName(entity.getName().trim());
				performanceConfigService.updatePerformanceConfig(entity);
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				//record.setActionDate(entity.getCreatedDate());
				record.setActionType(AuditType.CREATE);
				record.setActionPerformed("updated performance configuration ");
				record.setActionData("Configuration Name :"+entity.getName());
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
				model.addAttribute("configurationupdated","<font color='green' size='3'>Configuration With '"+entity.getName()+"' Updated Successfully");
			}
			model.addAttribute("project_list",projectService.list());
			model.addAttribute("editperformanceconfigs", "editperformanceconfigs");
			model.addAttribute("performance_list", performanceConfigService.getByOrder());
			model.addAttribute("crudObj", entity);
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	@ModelAttribute("performance_list")
    public List<PerformanceTesting> performanceList(WebRequest request,HttpServletRequest request1,HttpServletResponse response) {
		return performanceConfigService.getByOrder();
    }
	
	@RequestMapping(value = "/searcheditperformance" ,method = RequestMethod.POST)
	public String searchEditPerformance(@ModelAttribute("crudObj") @Valid PerformanceTesting entity,BindingResult bindingResult,Model model){
	
		try{
			Integer projectId = entity.getProjid();
			if(projectId == null){
				List<PerformanceTesting> performList = new ArrayList<PerformanceTesting>();
				List<PerformanceTesting> list = performanceConfigService.list();
				Iterator<PerformanceTesting> iterator = list.iterator();
				while(iterator.hasNext()){
					PerformanceTesting testing = new PerformanceTesting();
					PerformanceTesting performance = iterator.next();
					testing.setId(performance.getId());
					testing.setName(performance.getName());
					testing.setPort(performance.getPort());
					testing.setUrl(performance.getUrl());
					testing.setWaitTime(performance.getWaitTime());
					testing.setName(projectService.getById(performance.getProjid()).getName());
					performList.add(testing);
				}
				model.addAttribute("performance_list", performList);
				model.addAttribute("project_list",projectService.getActiveProjects());
				
			}else if(projectId != null){
				List<PerformanceTesting> performList = new ArrayList<PerformanceTesting>();
				List<PerformanceTesting> list = performanceConfigService.getByProjectId(projectId);
				Iterator<PerformanceTesting> iterator = list.iterator();
				while(iterator.hasNext()){
					PerformanceTesting testing = new PerformanceTesting();
					PerformanceTesting performance = iterator.next();
					testing.setId(performance.getId());
					testing.setName(performance.getName());
					testing.setPort(performance.getPort());
					testing.setUrl(performance.getUrl());
					testing.setWaitTime(performance.getWaitTime());
					testing.setName(projectService.getById(performance.getProjid()).getName());
					performList.add(testing);
				}
				model.addAttribute("performance_list", performList);
				model.addAttribute("project_list",projectService.getActiveProjects());
			}
			model.addAttribute("editperformanceconfigs", "editperformanceconfigs");
		}catch(Exception e)
		{
			log.error(e.toString());
			e.printStackTrace();
		}
	
		return "home";
	}
	
	
	
	@RequestMapping(value = "/searchperformance" ,method = RequestMethod.POST)
	public String searchPerformance(@ModelAttribute("crudObj") @Valid PerformanceTesting entity,BindingResult bindingResult,Model model){
		
		try {
			Integer projectId = entity.getProjid();
			if(projectId == null){
				List<PerformanceTesting> performList = new ArrayList<PerformanceTesting>();
				List<PerformanceTesting> list = performanceConfigService.list();
				Iterator<PerformanceTesting> iterator = list.iterator();
				while(iterator.hasNext()){
					PerformanceTesting testing = new PerformanceTesting();
					PerformanceTesting performance = iterator.next();
					testing.setId(performance.getId());
					testing.setName(performance.getName());
					testing.setPort(performance.getPort());
					testing.setUrl(performance.getUrl());
					testing.setWaitTime(performance.getWaitTime());
					testing.setName(projectService.getById(performance.getProjid()).getName());
					performList.add(testing);
				}
				model.addAttribute("performance_list", performList);
				model.addAttribute("project_list",projectService.getActiveProjects());
				
			}else if(projectId != null){
				List<PerformanceTesting> performList = new ArrayList<PerformanceTesting>();
				List<PerformanceTesting> list = performanceConfigService.getByProjectId(projectId);
				Iterator<PerformanceTesting> iterator = list.iterator();
				while(iterator.hasNext()){
					PerformanceTesting testing = new PerformanceTesting();
					PerformanceTesting performance = iterator.next();
					testing.setId(performance.getId());
					testing.setName(performance.getName());
					testing.setPort(performance.getPort());
					testing.setUrl(performance.getUrl());
					testing.setWaitTime(performance.getWaitTime());
					testing.setName(projectService.getById(performance.getProjid()).getName());
					performList.add(testing);
				}
				model.addAttribute("performance_list", performList);
				model.addAttribute("project_list",projectService.getActiveProjects());
			}
			model.addAttribute("viewperformanceconfigs", "viewperformanceconfigs");
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	@RequestMapping(value = "/searcheditobjectspy" , method = RequestMethod.POST)
	public String searchEditObjectSpy(@ModelAttribute("crudObj") @Valid ObjectSpy entity,BindingResult bindingResult,Model model){
		try {
			String browser = entity.getBrowser();
			if(browser == null || browser == ""){
				List<ObjectSpy> objectsList = objectspyConfigService.list();
				Iterator<ObjectSpy> iterator = objectsList.iterator();
				List<ObjectSpy> objspylist = new ArrayList<ObjectSpy>();
				while(iterator.hasNext()){
					ObjectSpy spy = iterator.next();
					ObjectSpy object = new ObjectSpy();
					object.setConfigurationname(spy.getConfigurationname());
					object.setBrowser(spy.getBrowser());
					object.setElementwaittime(spy.getElementwaittime());
					object.setUrl(spy.getUrl());
					objspylist.add(object);
				}
				
				model.addAttribute("objspy_list",objspylist);
				model.addAttribute("project_list", projectService.getActiveProjects());
			}else {
				List<ObjectSpy> objectsList = objectspyConfigService.list();
				Iterator<ObjectSpy> iterator = objectsList.iterator();
				List<ObjectSpy> objspylist = new ArrayList<ObjectSpy>();
				while(iterator.hasNext()){
					ObjectSpy spy = iterator.next();
					ObjectSpy object = new ObjectSpy();
					object.setConfigurationname(spy.getConfigurationname());
					object.setBrowser(spy.getBrowser());
					object.setElementwaittime(spy.getElementwaittime());
					object.setUrl(spy.getUrl());
					objspylist.add(object);
				}
				
				model.addAttribute("objspy_list",objspylist);
				model.addAttribute("project_list", projectService.getActiveProjects());
			}
			
			model.addAttribute("editobjspyconfig", "editobjspyconfig");
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
	/*
	 * mobile configurations
	 */
	
	
	@RequestMapping(value = "/createmobileconfig", method = RequestMethod.GET)
    public String createmobileconfig(Model model,HttpServletRequest request) {
		try {
			model.addAttribute("crudObj", new EnterpriseSpy());
			model.addAttribute("project_list", projectService.list());
			/*List<User> user = userService.list();
			List<User> userList = new ArrayList<User>();
			if(user != null){
				Iterator<User> itr = user.iterator();
				while(itr.hasNext()){
					User us = itr.next();
					UserRole userRole = userRoleService.findByUserId(us.getId());
					if(userRole != null){
						Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
						if(!r.getName().equalsIgnoreCase("System Administrator")){
							if(!r.getName().equalsIgnoreCase("Application Administrator")){
								userList.add(us);
							}
						}
					}else{
						userList.add(us);
					}
					
				}
			}
			model.addAttribute("user_list",userList);*/
			model.addAttribute("createmobileconfigurations", "createmobileconfigurations");
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
	@RequestMapping(value = "/savemobileconfiguration", method = RequestMethod.POST)
	public String saveMobileConfiguration(@ModelAttribute("crudObj") @Valid EnterpriseSpy entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		try {
			EnterpriseSpy spy = entpspyConfigService.findByConfigName(entity.getConfigurationname());
			if(spy == null){
				EnterpriseSpy entpspy = new EnterpriseSpy();
				entpspy.setConfigurationname(entity.getConfigurationname().trim());
				entpspy.setPath(entity.getPath());
				entpspy.setElementwaittime(entity.getElementwaittime());
				entpspy.setTypeEntpc(entity.getTypeEntpc());
				entpspy.setProjectid(entity.getProjectid());
				entpspy.setCreatedBy(entity.getCreatedBy());
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
				entpspy.setCreatedDate(dateInIndia);
				try {
					entpspyConfigService.save(entpspy);
					AuditLogRecord record = new AuditLogRecord();
					record.setActionDate(dateInIndia);
					//record.setActionDate(entity.getCreatedDate());
					record.setActionType(AuditType.CREATE);
					Project p = projectService.getById(entpspy.getProjectid());
					record.setProjectName(p.getName());
					record.setActionPerformed("created enterprise configurations");
					record.setActionData("Configuration Name :"+entity.getConfigurationname());
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
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				model.addAttribute("configurationcreated", "<font color='green' size='3'>Configuration with '"+entity.getConfigurationname()+"' Created Successfully");
			}else{
				model.addAttribute("configurationcreated", "<font color='red' size='3'>Configuration with '"+spy.getConfigurationname()+"' already exists");
			}
			model.addAttribute("crudObj", new EnterpriseSpy());
			model.addAttribute("createmobileconfigurations", "createmobileconfigurations");
			model.addAttribute("project_list", projectService.list());
			model.addAttribute("user_list",userService.list());
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	@RequestMapping(value = "/viewmobileconfigs", method = RequestMethod.GET)
    public String viewMobileConfigs(Model model) {
		try {
			model.addAttribute("viewmobileconfigurations", "viewmobileconfigurations");
			model.addAttribute("mobile_configs",entpspyConfigService.findByConfigType("mobile"));
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	@RequestMapping(value = "/editmobileconfig", method = RequestMethod.GET)
    public String editMobileConfig(Model model) {
		try {
			model.addAttribute("crudObj", new EnterpriseSpy());
			model.addAttribute("mobileconfig_list", entpspyConfigService.findByConfigType("mobile"));
			model.addAttribute("editmobileconfiguration", "editmobileconfiguration");
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	@RequestMapping(value="mobile/{mobileconfigid}/updatemobileconfig", method=RequestMethod.GET)
	public String updateMobileConfigById(@PathVariable Integer mobileconfigid,Model model,HttpServletRequest request) {
		try {
			EnterpriseSpy entpspy = entpspyConfigService.getById(mobileconfigid);
			model.addAttribute("crudObj", entpspy);
			model.addAttribute("project_list", projectService.list());
			List<User> user = userService.list();
			List<User> userList = new ArrayList<User>();
			if(user != null){
				Iterator<User> itr = user.iterator();
				while(itr.hasNext()){
					User us = itr.next();
					UserRole userRole = userRoleService.findByUserId(us.getId());
					if(userRole != null){
						Role r = roleService.getById(userRole.getUserRoleComp().getRoleId());
						if(!r.getName().equalsIgnoreCase("System Administrator")){
							if(!r.getName().equalsIgnoreCase("Application Administrator")){
								userList.add(us);
							}
						}
					}else{
						userList.add(us);
					}
				}
			}
			model.addAttribute("user_list",userList);
			model.addAttribute("updatemobileconfig", "updatemobileconfig");
		} catch (Exception e) {

			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	@RequestMapping(value = "/updatemobileconfig", method = RequestMethod.POST)
	public String updateMobileConfiguration(@ModelAttribute("crudObj") @Valid EnterpriseSpy entity,BindingResult bindingResult,Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			EnterpriseSpy spy = entpspyConfigService.findByNameAndIdNotIn(entity.getConfigurationname(), entity.getId());
			
			if(spy != null){
				model.addAttribute("configurationupdated","<font color='red' size='3'>Configuration With '"+entity.getConfigurationname()+"' Already Exists</font>");
			}
			else{
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
				
				entity.setModifiedDate(dateInIndia);
				entity.setConfigurationname(entity.getConfigurationname().trim());
				entpspyConfigService.updateEnterpriseSpyConfiguration(entity);
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				record.setActionDate(entity.getModifiedDate());
				record.setActionType(AuditType.MODIFY);
				record.setActionPerformed("updated enterprise configuration new Permission");
				record.setActionData("Configuration Name :"+entity.getConfigurationname());
				record.setIpOrigin(request.getSession().getAttribute("ipAddress").toString());
				/*try {
					java.net.InetAddress inetAddress = java.net.InetAddress.getLocalHost();
					record.setIpOrigin(inetAddress.getHostAddress());
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				record.setUserId((Integer)request.getSession().getAttribute("userid"));
				record.setUserName(request.getSession().getAttribute("username").toString());
				auditLogService.saveRecord(record);
				model.addAttribute("configurationupdated","<font color='green' size='3'>Configuration With '"+entity.getConfigurationname()+"' Updated Successfully");
			}
			model.addAttribute("editmobileconfiguration", "editmobileconfiguration");
			model.addAttribute("mobileconfig_list", entpspyConfigService.findByConfigType("mobile"));
			model.addAttribute("crudObj", entity);
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
}
