/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.entity.referencedata.Project;
import com.infotree.qliktest.admin.entity.referencedata.ProjectModule;
import com.infotree.qliktest.admin.entity.referencedata.ProjectModuleComp;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.ProjectModulePojo;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ModuleService;
import com.infotree.qliktest.admin.service.referencedata.ProjectModuleService;
import com.infotree.qliktest.admin.service.referencedata.ProjectService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/projectmodule")
public class ModuleProjectController extends AbstractQTAdminController<ProjectModule> {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ProjectModuleService projectModuleService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	@Autowired
	private ProjectService projectService;
	/**
	 * This method returns the UI to assign the modules to the project
	 */
	@RequestMapping(value="/assignmodulestoproject",method = RequestMethod.GET)
	public String assignModulesToProject(Model model){
		try {
			model.addAttribute("crudObj",new ProjectModulePojo());
			model.addAttribute("assignmodulestoproject","assignmodulestoproject");
			model.addAttribute("module_list",moduleService.list());
			model.addAttribute("project_list",projectService.getActiveProjects());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * This method is to save the modules list to the project
	 */
	@RequestMapping(value="/saveassignmodulelist",method = RequestMethod.POST)
	public String saveAssignedModules(@Valid ProjectModulePojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		try {
			//Deleting the existing records
			projectModuleService.deleteByProjectId(entity.getProjectId());
			List<Integer> moduleId = entity.getModuleId();
			Iterator<Integer> iterator = moduleId.iterator();
			while(iterator.hasNext()){
				ProjectModule md = new ProjectModule();
				ProjectModuleComp modperComp = new ProjectModuleComp();
				modperComp.setProjectId(entity.getProjectId());
				modperComp.setModuleId(iterator.next());
				md.setProjectModuleComp(modperComp);
				md.setCreatedBy(entity.getCreatedBy());
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				
				md.setCreatedDate(dateInIndia);
				md.setModifiedBy(entity.getModifiedBy());
				md.setModifiedDate(dateInIndia);
				projectModuleService.save(md);
			}
			
			
				
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(new java.util.Date());
				record.setActionType(AuditType.ASSIGN);
				
				Project p = projectService.getById(entity.getProjectId());
				
				record.setActionData("Module and project name is"+p.getName());
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
				
				model.addAttribute("assignmodulestoproject","assignmodulestoproject");
				model.addAttribute("modulesassigned","Modules Assigned For Project");
				model.addAttribute("module_list",moduleService.list());
				model.addAttribute("crudObj",new ProjectModulePojo());
				model.addAttribute("project_list",projectService.getActiveProjects());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This is ajax method to get the projects based on the moduleid
	 */
	@RequestMapping(value="/getprojectbylicense",method=RequestMethod.GET)
	public void getAssignedPermissions(Model model,@RequestParam("projectId") String projectId,HttpServletRequest request,HttpServletResponse response){
		try {
			Integer projectid = Integer.parseInt(projectId);
			String trPerm = "";
			List<Module> moduleList = moduleService.getByModuleId(projectid);
			for(Module list : moduleList){
				trPerm += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
			}
			
			try{
				PrintWriter out = response.getWriter();
				out.println(trPerm);
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
	 * This method returns the list of moduels which were not assigned to project by taking the projectid as the parameter
	 */
	@RequestMapping(value="/getaprojectbylicense",method=RequestMethod.GET)
	public void getAssignedPermissionsByLicense(Model model,@RequestParam("projectId") String projectId,HttpServletRequest request,HttpServletResponse response){
		Integer projectid = Integer.parseInt(projectId);
		String traPermissions = "";
		List<Module> moduleList = moduleService.getByNotInModuleId(projectid);
		
		
		for(Module list : moduleList){
			traPermissions += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
		}
		try{
			PrintWriter out = response.getWriter();
			out.println(traPermissions);
			out.flush();
		}catch(Exception e){
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public QTAdminService<ProjectModule> getTService() {
		return projectModuleService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}

}
