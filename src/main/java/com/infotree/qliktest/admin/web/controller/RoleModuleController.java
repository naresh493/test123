/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.RoleModule;
import com.infotree.qliktest.admin.entity.referencedata.RoleModuleComp;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.RoleModulePojo;
import com.infotree.qliktest.admin.helperpojo.UserProjectPojo;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ModuleService;
import com.infotree.qliktest.admin.service.referencedata.RoleModuleService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller

@RequestMapping("/rolemodule")
public class RoleModuleController extends AbstractQTAdminController<RoleModule> {

	private static final Logger LOG = LoggerFactory.getLogger(RoleModuleController.class);
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private AuditLogRecordService auditRecordService;
	
	@Autowired
	private RoleModuleService roleModuleService;
	
	/**
	 * This method will return the UI to assign the role the module
	 */
	@RequestMapping(value="assignroletomodule",method = RequestMethod.GET)
	public String assignRoleToModule(Model model){
		try {
			model.addAttribute("assignroletomodule","assignroletomodule");
			model.addAttribute("crudObj",new RoleModulePojo());
			model.addAttribute("module_list",moduleService.list());
			List<Role> roleList = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			if(roleList != null){
				Iterator<Role> iterator = roleList.iterator();
				while(iterator.hasNext()){
					Role r = iterator.next();
					if(!r.getName().equalsIgnoreCase("System Administrator")){
						newRoleList.add(r);
					}
				}
			}
			model.addAttribute("role_list", newRoleList);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is used to save the roles to the modules
	 */
	@RequestMapping(value="/saveassignroletomodule", method = RequestMethod.POST)
	public String saveAssignRoleToModule(@Valid RoleModulePojo entity,BindingResult bindingResule,Model model,HttpServletRequest request){
		
		String displayMessage = null;
		try {
			List<Integer> beforeAssignedRoleList = (List<Integer>) request.getSession().getAttribute("roleIds");
			List<Integer> newlyAssignedRoleList = entity.getRoleIds();
			//System.out.println("beforeAssignedUserList >>> "+beforeAssignedUserList);
			//System.out.println("newlyassignedUserList >>> "+newlyAssignedUserList);
			int beforeAssignedRoleSize = beforeAssignedRoleList.size();
			int newlyAssignedRoleSize = 0;
			if(newlyAssignedRoleList != null)
				newlyAssignedRoleSize = newlyAssignedRoleList.size();

			if (beforeAssignedRoleSize == 0 && newlyAssignedRoleSize == 0) {
				//System.out.println("No changes done....");
				displayMessage = "Please assign atleast one role to the package";
				//System.out.println("displayMessage : "+displayMessage);
				model.addAttribute("displayMessage", displayMessage);
				model.addAttribute("assignroletomodule","assignroletomodule");
				model.addAttribute("crudObj",new RoleModulePojo());
				model.addAttribute("module_list",moduleService.list());
				return "home";
			} else if (beforeAssignedRoleSize > 0 && newlyAssignedRoleSize == 0) {
				if (beforeAssignedRoleSize == 1)
					displayMessage = "1 role is removed";
				else
					displayMessage = beforeAssignedRoleSize + " roles are removed ";
			} else if(beforeAssignedRoleSize == 0 && newlyAssignedRoleSize > 0){
					if(newlyAssignedRoleSize == 1)
						displayMessage=" 1 role is assigned";
					else
						displayMessage = newlyAssignedRoleSize+" roles are assigned ";
			}
			else{
				int sameRoleAssigned = 0;
				if(newlyAssignedRoleList != null){
					for(Integer roleId : newlyAssignedRoleList){
						if( beforeAssignedRoleList != null){
							for(Integer rid : beforeAssignedRoleList){
								if(roleId.intValue() == rid.intValue()){
										sameRoleAssigned++;
										break;
								}
							}
						}
					}
					
				}
				
			
				int removedRole = beforeAssignedRoleSize - sameRoleAssigned;
				if(removedRole == 0)
					displayMessage = "";
				else if(removedRole == 1)
					displayMessage = " and 1 role is removed";
				else
					displayMessage = " and "+removedRole+" roles are removed";
					int assignedRole = 	newlyAssignedRoleSize - sameRoleAssigned;
					if(assignedRole == 0 && beforeAssignedRoleSize == newlyAssignedRoleSize)
						displayMessage = "No changes are made";
					else if(assignedRole == 0){
							if(removedRole == 0)
								displayMessage = "";
							else if(removedRole == 1)
								displayMessage = " 1 role is removed";
							else
								displayMessage = removedRole+" roles are removed";
					}
					else if(assignedRole == 1)
						displayMessage = "1 role is assigned"+displayMessage;
					else
						displayMessage = assignedRole+" roles are assigned"+displayMessage;
				
			}
			//System.out.println(displayMessage);
			model.addAttribute("displayMessage", displayMessage);
			
			roleModuleService.deleteByModuleId(entity.getModuleId());
			List<Integer> roleIds = entity.getRoleIds();
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
			if(roleIds != null){
				Iterator<Integer> iterator = roleIds.iterator();
				while(iterator.hasNext()){
					RoleModule roleModule = new RoleModule();
					RoleModuleComp rolemodComp = new RoleModuleComp();
					rolemodComp.setModuleId(entity.getModuleId());
					rolemodComp.setRoleId(iterator.next());
					roleModule.setRoleModuleComp(rolemodComp);
					roleModule.setCreatedBy(entity.getCreatedBy());
					roleModule.setCreatedDate(dateInIndia);
					roleModule.setModifiedBy(entity.getModifiedBy());
					roleModule.setModifiedDate(dateInIndia);
					roleModuleService.save(roleModule);
				}
				model.addAttribute("assignroletomodule","assignroletomodule");
				
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				
				Module module = moduleService.getById(entity.getModuleId());
				
				record.setActionData("Module name is "+module.getName());
				record.setActionType(AuditType.ASSIGN);
				record.setActionPerformed("Module Assigned");
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
				model.addAttribute("roleassigned","Role(s) Assigned.");
			}else{
				model.addAttribute("roleassigned","Role(s) Assigned.");
			}
			model.addAttribute("assignroletomodule","assignroletomodule");
			model.addAttribute("crudObj",new RoleModulePojo());
			model.addAttribute("module_list",moduleService.list());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This is web request method which will return the all the available roles
	 */
	@ModelAttribute("role_list")
    public List<Role> roles(WebRequest request) {
		List<Role> newRoleList=null;
		try {
			List<Role> rolelist = roleService.list();
			newRoleList = new ArrayList<Role>();
			for (Role role : rolelist) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
						newRoleList.add(role);
				}
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		} 
		return newRoleList;
    }
	
	/**
	 * This is ajax based request which will give the roleslist by taking the moduleid as the parameter
	 */
	@RequestMapping(value="/getrolebylicense",method=RequestMethod.GET)
	public void getAssignedPermissions(Model model,@RequestParam("moduleId") String moduleId,HttpServletRequest request,HttpServletResponse response){
		try {
			Integer licenseid = Integer.parseInt(moduleId);
			String traPermissions = "";
			List<Role> moduleList = roleService.getByModuleId(licenseid);
			List<Integer> roleIds = new ArrayList<Integer>();
			if(moduleList != null){
				for(Role list : moduleList){
					if(!list.getName().equalsIgnoreCase("System Administrator")){
						traPermissions += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
						roleIds.add(list.getId());
					}
					
				}
			}
			//System.out.println("Added to Session : "+roleIds);
			request.getSession().setAttribute("roleIds", roleIds);
			
			try{
				PrintWriter out = response.getWriter();
				out.println(traPermissions);
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
	 * This method returns the available roles to the module by taking the moduleid as the parameter
	 */
	@RequestMapping(value="/getroleavalablelicense",method=RequestMethod.GET)
	public void getAvalablePermissions(Model model,@RequestParam("moduleId") String moduleId,HttpServletRequest request,HttpServletResponse response){
		try {
			Integer licenseid = Integer.parseInt(moduleId);
			String trPerm = "";
			List<Role> moduleList = roleService.getByModuleNotInId(licenseid);
			
			for(Role list : moduleList){
				if(!list.getName().equalsIgnoreCase("System Administrator")){
					trPerm += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
				}
				
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
	
	
	@Override
	public QTAdminService<RoleModule> getTService() {
		return roleModuleService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}

}
