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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.Permissions;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.RolePermissions;
import com.infotree.qliktest.admin.entity.referencedata.RolePermissionsComp;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.RolePermissionsPojo;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.PermissionsService;
import com.infotree.qliktest.admin.service.referencedata.RolePermissionsService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/rolepermissions")
public class RolePermissionsController extends AbstractQTAdminController<RolePermissions>{
	private static final Logger LOG = LoggerFactory.getLogger(RolePermissionsController.class);
	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	@Autowired
	private PermissionsService permissionsService;
	
	@Autowired
	private RolePermissionsService rolePermissionsService;
	
	
	/**
	 * This method will return the UI to assign the permissions to the role
	 */
	@RequestMapping(value="/managerolesandpermissions",method=RequestMethod.GET)
	public String manageRolesAndPermissions(Model model){
		try {
			model.addAttribute("managerolesandpermissions","managerolesandpermissions");
			List<Role> roleList = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for(Role role : roleList){
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					newRoleList.add(role);
				}
			}
			model.addAttribute("role_list",newRoleList);
			model.addAttribute("permissions_list",permissionsService.list());
			model.addAttribute("crudObj", new RolePermissionsPojo());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is used to save the permissions to the role
	 */
	@RequestMapping(value="/savepermissionstorole",method=RequestMethod.POST)
	public String saveLicenseAuth(@Valid RolePermissionsPojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		rolePermissionsService.deleteByRoleId(entity.getRoleId());
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
			try {
				int count=0;
				String permissionName = "";
				model.addAttribute("managerolesandpermissions","managerolesandpermissions");
				model.addAttribute("crudObj",new RolePermissionsPojo());
				List<Integer> permissions = entity.getPermissionIds();
				if(permissions != null){
					Iterator<Integer> iterator = permissions.iterator();
					while(iterator.hasNext()){
						RolePermissions rolePerm = new RolePermissions();
						RolePermissionsComp comp = new RolePermissionsComp();

						Integer permId = iterator.next();
						comp.setPermissionId(permId);
						comp.setRoleId(entity.getRoleId());
						rolePerm.setUserPermissionsComp(comp);
						rolePerm.setCreatedBy(entity.getCreatedBy());
						rolePerm.setCreatedDate(dateInIndia);
						
						rolePermissionsService.save(rolePerm);
						
						Permissions p = permissionsService.getById(permId);
						if(permissionName == ""){
							permissionName = p.getName();
						}else{
							permissionName = permissionName+","+p.getName();
						}
						
					}
				}else{
					
				}
				
				
				
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				record.setActionType(AuditType.ASSIGN);
				
				Role role = roleService.getById(entity.getRoleId());
				
				record.setActionData("Permissions names "+permissionName+" and Role name is"+role.getName());
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
				model.addAttribute("managerolesandpermissions","managerolesandpermissions");
				model.addAttribute("role_list",roleService.list());
				model.addAttribute("permissions_list",permissionsService.list());
				model.addAttribute("permissionsassigned", "Permissions Assigned ");
				
				if(count==0){
					model.addAttribute("permissionsassigned","Permission(s) Assigned.'");
				}else{
				model.addAttribute("permissionsassigned", "Permission(s) assigned.'"+"<font color='red'>" + count+ "'Permission(s) not assigned(Already available.)</font>" );
				}
			} catch (Exception e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
		
		return "home";
	}
	/**
	 * This is a  ajax based request which will return the permissions by taking the roleid as the parameter
	 */
	@RequestMapping(value="/getprojectbypermissionrole",method=RequestMethod.GET)
	public void getAssignedPermissions(Model model,@RequestParam("roleId") String roleId,HttpServletRequest request,HttpServletResponse response){
		try {
			Integer roleid = Integer.parseInt(roleId);
			String trPerm = "";
			List<Permissions> moduleList = permissionsService.getByRoleId(roleid);
			for(Permissions list : moduleList){
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
	 * This is ajax based request which will return the permissions by taking the role id as the parameter
	 */
	@RequestMapping(value="/getprojectbypermissionrole1",method=RequestMethod.GET)
	public void getAvailablePermissions(Model model,@RequestParam("roleId") String roleId,HttpServletRequest request,HttpServletResponse response){
		try {
			Integer roleid = Integer.parseInt(roleId);
			String traPermissions = "";
			List<Permissions> moduleList = permissionsService.getNotByRoleId(roleid);
			for(Permissions list : moduleList){
				traPermissions += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
			}
			
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
	
	@Override
	public QTAdminService<RolePermissions> getTService() {
		return null;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}
 
}
