package com.infotree.qliktest.admin.web.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.DashBoardReports;
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.entity.referencedata.Permissions;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.RolePermissions;
import com.infotree.qliktest.admin.entity.referencedata.RolePermissionsComp;
import com.infotree.qliktest.admin.entity.referencedata.RoleReport;
import com.infotree.qliktest.admin.entity.referencedata.RoleReportComp;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.RoleReportsPojo;
import com.infotree.qliktest.admin.helperpojo.TeamProjectPojo;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ModuleService;
import com.infotree.qliktest.admin.service.referencedata.PermissionsService;
import com.infotree.qliktest.admin.service.referencedata.ReportsService;
import com.infotree.qliktest.admin.service.referencedata.RoleModuleService;
import com.infotree.qliktest.admin.service.referencedata.RolePermissionsService;
import com.infotree.qliktest.admin.service.referencedata.RoleReportService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.TeamService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserRoleService;
import com.infotree.qliktest.admin.service.referencedata.UserTeamService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/role")
public class RoleController extends AbstractQTAdminController<Role> {

	private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleReportService roleReportService;
	
	@Autowired
	private ReportsService reportsService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private PermissionsService permissionsService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private UserTeamService userTeamService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private RoleModuleService roleModuleService;
	
	@Autowired
	private RolePermissionsService rolePermissionsService;
	
	@Autowired
	private DoNothingValidator validator;
	/**
	 * This method returns the UI to assign the roles to the permissions
	 */
	@RequestMapping(value = "/managerolesandpermissions", method = RequestMethod.GET)
    public String landingPage(Model model) {
		try {
			model.addAttribute("crudObj", getEntityInstance());
			List<Role> roleList = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for(Role role : roleList){
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					newRoleList.add(role);
				}
			}
			model.addAttribute("role_list", newRoleList);
			model.addAttribute("permissions_list", permissionsService.getActivePermissions());
			model.addAttribute("managerolesandpermissions", "managerolesandpermissions");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to create the role
	 */
	@RequestMapping(value="/createrole",method=RequestMethod.GET)
	public String createRole(Model model){
		
		try {
			model.addAttribute("crudObj", getEntityInstance());
			model.addAttribute("createrole","createrole");
			model.addAttribute("role_list",roleService.list());
			model.addAttribute("permissions_list",permissionsService.getActivePermissions());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to show the roles which are applicable to application admin
	 */
	@RequestMapping(value="/viewrolesappadmin",method = RequestMethod.GET)
	public String viewRolesAppAdmin(Model model,HttpServletRequest request){
		try {
			List<Role> list = roleService.list();
			Iterator<Role> itr = list.iterator();
			List<Role> rolelist = new ArrayList<Role>();
			List<Role> roleList = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for (Role role : roleList) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					if(!role.getName().equalsIgnoreCase("Application Administrator")){
						newRoleList.add(role);
					}
					
				}
			} 
			while(itr.hasNext()){
				Role r = itr.next();
				if(!r.getName().equalsIgnoreCase("System Administrator")){
					if(!r.getName().equalsIgnoreCase("Application Administrator")){
						Role r1 = new Role();
						String permissions = "";
						r1.setId(r.getId());
						r1.setDescription(r.getDescription());
						r1.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
						r1.setName(r.getName());
						r1.setCreatedDate(r.getCreatedDate());
						r1.setModifiedDate(r.getModifiedDate());
						r1.setPermissionsCount(rolePermissionsService.getPermissionsCountByRole(r.getId()));
						List<Permissions> permList = permissionsService.getPermissionsByRoleId(r.getId());
						Iterator<Permissions> iterator = permList.iterator();
						while(iterator.hasNext()){
							Permissions p = iterator.next();
							if(permissions == ""){
								permissions = p.getName();
							}else{
								permissions = permissions+" "+"<br>"+p.getName();
							}
						}
						r1.setPermissionsName("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
						rolelist.add(r1);
					}
				}
			
			}
			model.addAttribute("permissions_list", permissionsService.list());
			model.addAttribute("viewrole","viewrole");
			model.addAttribute("role_list",rolelist);
			model.addAttribute("rolenames_list",newRoleList);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method is to show all the roles
	 */
	@RequestMapping(value="/viewrole",method=RequestMethod.GET)
	public String viewRole(Model model,HttpServletRequest request){
		try {
			List<Role> list = roleService.findByOrder();
			Iterator<Role> itr = list.iterator();
			List<Role> rolelist = new ArrayList<Role>();
			
			while(itr.hasNext()){
				Role r = itr.next();
				if(!r.getName().equalsIgnoreCase("System Administrator")){
					Role r1 = new Role();
					String permissions = "";
					r1.setId(r.getId());
					r1.setDescription(r.getDescription());
					r1.setRoleAndResponsibility(r.getRoleAndResponsibility());
					r1.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
					r1.setName(r.getName());
					r1.setCreatedDate(r.getCreatedDate());
					r1.setModifiedDate(r.getModifiedDate());
					r1.setPermissionsCount(rolePermissionsService.getPermissionsCountByRole(r.getId()));
					List<Permissions> permList = permissionsService.getPermissionsByRoleId(r.getId());
					Iterator<Permissions> iterator = permList.iterator();
					while(iterator.hasNext()){
						Permissions p = iterator.next();
						if(permissions == ""){
							permissions = p.getName();
						}else{
							permissions = permissions+" "+"<br>"+p.getName();
						}
					}
					r1.setPermissionsName("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
					rolelist.add(r1);
				}
				
			
			}
			List<Role> roleList = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for (Role role : roleList) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
						newRoleList.add(role);
				}
			} 
			
			model.addAttribute("viewrole","viewrole");
			model.addAttribute("role_list",rolelist);
		 	model.addAttribute("rolenames_list",newRoleList);
			model.addAttribute("permissions_list",permissionsService.list());
			return "home";
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
	
	
	
	/**
	 * To search the role based on the roleid and permissionid
	 */
	@RequestMapping(value="/searchrole" , method = RequestMethod.POST)
	public String searchRoles(@ModelAttribute("crudObj") @Valid Role entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		try {
			Integer roleId = entity.getId();
			Integer permId = entity.getPermissionId();
			
			if(roleId == null && permId == null){
				List<Role> list = roleService.list();
				List<Role> rolelist = new ArrayList<Role>();
				if(rolelist != null){
					Iterator<Role> itr = list.iterator();
					while(itr.hasNext()){
						String permissions = "";
						Role r = itr.next();
						if(!r.getName().equalsIgnoreCase("System Administrator")){
								Role r1 = new Role();
								r1.setId(r.getId());
								r1.setDescription(r.getDescription());
								r1.setRoleAndResponsibility(r.getRoleAndResponsibility());
								r1.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
								r1.setName(r.getName());
								r1.setCreatedDate(r.getCreatedDate());
								r1.setModifiedDate(r.getModifiedDate());
								r1.setPermissionsCount(rolePermissionsService.getPermissionsCountByRole(r.getId()));
								List<Permissions> permList = permissionsService.getPermissionsByRoleId(r.getId());
								if(permList != null){
									Iterator<Permissions> iterator = permList.iterator();
									while(iterator.hasNext()){
										Permissions p = iterator.next();
										if(permissions == ""){
											permissions = p.getName();
										}else{
											permissions = permissions+" "+"<br>"+p.getName();
										}
									}
								}
								
								r1.setPermissionsName("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
								rolelist.add(r1);
						}
						
					
					}
				}
				
				model.addAttribute("viewrole","viewrole");
				model.addAttribute("role_list",rolelist);
			}else if(roleId == null && permId != null){
				List<Role> list = roleService.findByPermId(permId);
				List<Role> rolelist = new ArrayList<Role>();
				if(list != null){
					Iterator<Role> itr = list.iterator();
					while(itr.hasNext()){
						String permissions = "";
						Role r = itr.next();
						Role r1 = new Role();
						r1.setId(r.getId());
						r1.setDescription(r.getDescription());
						r1.setRoleAndResponsibility(r.getRoleAndResponsibility());
						r1.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
						r1.setName(r.getName());
						r1.setCreatedDate(r.getCreatedDate());
						r1.setModifiedDate(r.getModifiedDate());
						r1.setPermissionsCount(rolePermissionsService.getPermissionsCountByRole(r.getId()));
						List<Permissions> permList = permissionsService.getPermissionsByRoleId(r.getId());
						if(permList != null){
							Iterator<Permissions> iterator = permList.iterator();
							while(iterator.hasNext()){
								Permissions p = iterator.next();
								if(permissions == ""){
									permissions = p.getName();
								}else{
									permissions = permissions+" "+"<br>"+p.getName();
								}
							}
						}
						
						r1.setPermissionsName("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
						rolelist.add(r1);
					
					}
				}
				
				model.addAttribute("viewrole","viewrole");
				model.addAttribute("role_list",rolelist);
			}else if(roleId != null && permId == null){
				Role r = roleService.getById(roleId);
				List<Role> rolelist = new ArrayList<Role>();
				String permissions = "";
				if(!r.getName().equalsIgnoreCase("System Administrator")){
					
						Role r1 = new Role();
						
						r1.setId(r.getId());
						r1.setDescription(r.getDescription());
						r1.setRoleAndResponsibility(r.getRoleAndResponsibility());
						r1.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
						r1.setName(r.getName());
						r1.setCreatedDate(r.getCreatedDate());
						r1.setModifiedDate(r.getModifiedDate());
						r1.setPermissionsCount(rolePermissionsService.getPermissionsCountByRole(r.getId()));
						List<Permissions> permList = permissionsService.getPermissionsByRoleId(r.getId());
						if(permList != null){
							Iterator<Permissions> iterator = permList.iterator();
							while(iterator.hasNext()){
								Permissions p = iterator.next();
								if(permissions == ""){
									permissions = p.getName();
								}else{
									permissions = permissions+" "+"<br>"+p.getName();
								}
							}
						}
						
						r1.setPermissionsName("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
						rolelist.add(r1);
				}
					
				model.addAttribute("viewrole","viewrole");
				model.addAttribute("role_list",rolelist);
			}else if(roleId != null && permId != null){
				List<Role> list = roleService.getByRoleIdAndPermId(roleId, permId);
				List<Role> rolelist = new ArrayList<Role>();
				if(list != null){
					Iterator<Role> itr = list.iterator();
					while(itr.hasNext()){
						String permissions = "";
						Role r = itr.next();
						if(!r.getName().equalsIgnoreCase("System Administrator")){
								Role r1 = new Role();
								r1.setId(r.getId());
								r1.setDescription(r.getDescription());
								r1.setRoleAndResponsibility(r.getRoleAndResponsibility());
								r1.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
								r1.setName(r.getName());
								r1.setCreatedDate(r.getCreatedDate());
								r1.setModifiedDate(r.getModifiedDate());
								r1.setPermissionsCount(rolePermissionsService.getPermissionsCountByRole(r.getId()));
								List<Permissions> permList = permissionsService.getPermissionsByRoleId(r.getId());
								if(permList != null){
									Iterator<Permissions> iterator = permList.iterator();
									while(iterator.hasNext()){
										Permissions p = iterator.next();
										if(permissions == ""){
											permissions = p.getName();
										}else{
											permissions = permissions+" "+"<br>"+p.getName();
										}
									}
								}
								
								r1.setPermissionsName("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
								rolelist.add(r1);
							
						}
						
					}
				}
				
				model.addAttribute("viewrole","viewrole");
				model.addAttribute("role_list",rolelist);
			}
			
			List<Role> roleList = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for (Role role : roleList) {
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					newRoleList.add(role);
					
				}
			} 
			
			model.addAttribute("rolenames_list",newRoleList);
			model.addAttribute("permissions_list",permissionsService.list());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/*
	 * To search the role which are from the edit role page
	 */
	@RequestMapping(value="/searcheditrole" , method = RequestMethod.POST)
	public String searchEditRoles(@ModelAttribute("crudObj") @Valid Role entity,BindingResult bindingResult,Model model){
		try {
			model.addAttribute("editrole","editrole");
			model.addAttribute("role_list",roleService.searchByPattern(entity.getName()));
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method returns the UI to edit the role with list of roles
	 */
	@RequestMapping(value="/editrole",method=RequestMethod.GET)
	public String editRole(Model model){
		try {
			List<Role> roleList = roleService.findByOrder();
			List<Role> rolesList = new ArrayList<Role>();
			if(roleList != null){
				Iterator<Role> iterator = roleList.iterator();
				while(iterator.hasNext()){
					Role r = iterator.next();
					if(r.getModifiedBy() != null){
						Integer id = Integer.parseInt(r.getModifiedBy());
						User u = userService.getById(id);
						r.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(r.getCreatedBy());
						User u = userService.getById(id);
						r.setCreatedName(u.getUserName());
					}
					rolesList.add(r);
				}
			}
			model.addAttribute("editrole","editrole");
			model.addAttribute("role_list",rolesList);
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		
		return "home";
	}
	/**
	 * This method return the UI to delete the role
	 */
	@RequestMapping(value="/deleterole",method=RequestMethod.GET)
	public String deleteRole(Model model){
		try {
			model.addAttribute("deleterole","deleterole");
			
			List<Role> rolelist = roleService.list();
			List<Role> newRoleList = new ArrayList<Role>();
			for (Role role : rolelist) {
				if(!role.getName().equalsIgnoreCase("SYS_ADMIN")){
					newRoleList.add(role);
				}
			} 
			model.addAttribute("role_list",newRoleList);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method deletes the role by taking the roleid as the parameter
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deleteRole(@ModelAttribute("crudObj") @Valid Role entity,BindingResult bindingResult,Model model){
		try {
			model.addAttribute("deleterole","deleterole");
			model.addAttribute("crudObj", getEntityInstance());
			Role role = roleService.getById(entity.getId());
			if(role == null){
				model.addAttribute("deleterole", "deleterole");
				model.addAttribute("role_list", roleService.list());
				model.addAttribute("roledeleted", "Data cannot be deleted. Pl. try again." );
			}else{
				role.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
				role.setModifiedDate(new java.sql.Date(System.currentTimeMillis()));
				roleService.delete(role);
				model.addAttribute("crudObj", getEntityInstance());
				model.addAttribute("deleterole", "deleterole");
				model.addAttribute("role_list",roleService.list());
				model.addAttribute("roledeleted", "'"+ entity.getName() +"' deleted.");
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the scope to edit the role which was selected by the user with the values
	 */
	@RequestMapping(value="/edit/{roleId}/update", method=RequestMethod.GET)
	public String updateModules(@ModelAttribute("crudObj") @Valid Role entity, @PathVariable Integer roleId, Model model) {
		try {
			Role entityToBeUpdated = roleService.getById(roleId);
			model.addAttribute("role_list", roleService.list());
			model.addAttribute("crudObj", entityToBeUpdated);
			model.addAttribute("idToBeUpdated", entityToBeUpdated.getId());
			model.addAttribute("assigned_permissions_list", permissionsService.getByRoleId(entityToBeUpdated.getId()));
			model.addAttribute("available_permissions_list", permissionsService.getNotByRoleId(entityToBeUpdated.getId()));
			model.addAttribute("updaterole", "updaterole");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method updates the role with the values which are given by the user
	 */
	@RequestMapping(value="/updaterole",method=RequestMethod.POST)
	public String updateRole(@ModelAttribute("crudObj") @Valid Role entity,BindingResult bindingResult,Model model){
			try {
				Role r = roleService.findByNameAndIdNot(entity.getName(), entity.getId());
				
				if(r != null){
					model.addAttribute("editrole", "editrole");
					model.addAttribute("role_list", roleService.list());
					model.addAttribute("roleupdated", "Role Already Exists Choose another name" );
				}else{
					Role role = new Role();
					role.setId(entity.getId());
					role.setName(entity.getName());
					role.setDescription(entity.getDescription());
					role.setRoleAndResponsibility(entity.getRoleAndResponsibility());
					role.setModifiedBy(entity.getModifiedBy());
					DateTime dt = new DateTime();
					DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
					DateTime dtus = dt.withZone(dtZone);
					Date dateInIndia = dtus.toLocalDateTime().toDate();
					role.setModifiedDate(dateInIndia);
					roleService.updateRole(role);
					model.addAttribute("editrole", "editrole");
					model.addAttribute("role_list", roleService.list());
					rolePermissionsService.deleteByRoleId(entity.getId());
					List<Integer> permissions = entity.getPermissionsIds();
					if(permissions != null){
						Iterator<Integer> iterator = permissions.iterator();
						while(iterator.hasNext()){
							RolePermissions rolePerm = new RolePermissions();
							RolePermissionsComp comp = new RolePermissionsComp();

							Integer permId = iterator.next();
							comp.setPermissionId(permId);
							comp.setRoleId(role.getId());
							rolePerm.setUserPermissionsComp(comp);
							rolePerm.setCreatedBy(entity.getCreatedBy());
							rolePerm.setCreatedDate(dateInIndia);
							
							rolePermissionsService.save(rolePerm);
						}
					}
					model.addAttribute("roleupdated", "'" + entity.getName() + "' updated." );
				}
				List<Role> roleList = roleService.list();
				List<Role> rolesList = new ArrayList<Role>();
				if(roleList != null){
					Iterator<Role> iterator = roleList.iterator();
					while(iterator.hasNext()){
						Role role = iterator.next();
						if(role.getModifiedBy() != null){
							Integer id = Integer.parseInt(role.getModifiedBy());
							User u = userService.getById(id);
							role.setCreatedName(u.getUserName());
						}else{
							Integer id = Integer.parseInt(role.getCreatedBy());
							User u = userService.getById(id);
							role.setCreatedName(u.getUserName());
						}
						rolesList.add(role);
					}
				}
				model.addAttribute("role_list", rolesList);
				entity.setName(null);
			} catch (NumberFormatException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
		return "home";
	}
	
	/**
	 * This method is used to save the role
	 */
	@RequestMapping(value="/saverole",method=RequestMethod.POST)
	public String saveRole(@ModelAttribute("crudObj") @Valid Role entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			model.addAttribute("crudObj",getEntityInstance());
			if(roleService.findByName(entity.getName()) != null){
				model.addAttribute("createrole", "createrole");
				model.addAttribute("rolealreadyexists", "'" + entity.getName()+"' already exists. Pl. enter a different Name." );
			}else{
				Role role = new Role();
				role.setName(entity.getName().trim());
				role.setCreatedBy(entity.getCreatedBy());
				role.setDescription(entity.getDescription());
				role.setRoleAndResponsibility(entity.getRoleAndResponsibility());
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				role.setCreatedDate(dateInIndia);
				if(roleService.save(role) == null){
					model.addAttribute("createrole", "createrole");
					model.addAttribute("project_list", roleService.list());
					model.addAttribute("rolenotselected", "Error in creating Role. Pl. contact Sys Admin." );
				}else{
					
					/**
					 * Preparing the audit log
					 */
					AuditLogRecord record = new AuditLogRecord();
					record.setActionDate(new java.util.Date());
					record.setActionType(AuditType.CREATE);
					record.setActionData("Role Name :"+entity.getName());
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
					
					/**
					 * assigning the permissions to the role
					 */
					List<Integer> permissions = entity.getPermissionsIds();
					if(permissions != null){
						Iterator<Integer> iterator = permissions.iterator();
						while(iterator.hasNext()){
							RolePermissions rolePerm = new RolePermissions();
							RolePermissionsComp comp = new RolePermissionsComp();
							comp.setPermissionId(iterator.next());
							comp.setRoleId(role.getId());
							rolePerm.setUserPermissionsComp(comp);
							rolePerm.setCreatedBy(entity.getCreatedBy());
							rolePerm.setCreatedDate(dateInIndia);
							rolePermissionsService.save(rolePerm);
						}
					}
					model.addAttribute("createrole", "createrole");
					model.addAttribute("permissions_list",permissionsService.getActivePermissions());
					model.addAttribute("rolecreated", "'" + entity.getName() + "' created." );
				}
			}
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This is a ajax based request to give the permissions list
	 */
	@RequestMapping(value = "/getpermissions", method = RequestMethod.GET)
    public void getPermissions(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Permissions> permList = permissionsService.list();
			String result = "";
			if(permList != null && permList.size() > 0){
				for (Permissions p : permList) {
					result += "<option value='"+p.getId()+"'>"+p.getName()+"</option>";
				}
			}
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
	
	@RequestMapping(value = "/getroles", method = RequestMethod.GET)
    public void getRoles(@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Role> roleList = roleService.list();
			String result = "";
			if(roleList != null && roleList.size() > 0){
				if(!type.equalsIgnoreCase("create")){
					result += "<option value='' selected></option>";
				}
				for (Role r : roleList) {
					result += "<option value='"+r.getId()+"'>"+r.getName()+"</option>";
				}
			}
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
	/**
	 * This method returns the permissions which are not assigned to the role by taking the roleid as the parameter
	 */
	@RequestMapping(value = "/getpermissionsnotinroleid", method = RequestMethod.GET)
    public void getPermForRoleId(@RequestParam String roleId,
    		HttpServletRequest request, HttpServletResponse response) {
		try {
			if(roleId != null && !roleId.isEmpty()){
				List<Integer> roles = new ArrayList<Integer>();
				roles.add(Integer.valueOf(roleId));
				List<Permissions> permList = permissionsService.getPermissionsNotInRoleId(roles);
				String result = "";
				if(permList != null && permList.size() > 0){
					for (Permissions p : permList) {
						result += "<option value='"+p.getId()+"'>"+p.getName()+"</option>";
					}
				}
				try {
					PrintWriter out = response.getWriter();
					out.print(result);
					out.flush();
				} catch (IOException e) {
					LOG.error("IOException in getData() in Controller ");
				}
			}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
/**
 * This method is used to save the role
 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("roleName") String roleName, @RequestParam("permList") String permList,
    		@RequestParam("newPerm") String newPerm, HttpServletRequest request, HttpServletResponse response) {
			try {
				Role role = new Role();
				role.setName(roleName);
				role.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
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
				role.setCreatedDate(dateInIndia);
				role.setModifiedBy((Integer)request.getSession().getAttribute("userid")+"");
				role.setModifiedDate(dateInIndia);
				Role newRole = roleService.save(role);
				if(newRole == null){
					try {
						PrintWriter out = response.getWriter();
						out.print("Error in creating Role. Pl. try again.");
						out.flush();
					} catch (IOException e) {
						LOG.error("IOException in getData() in Controller ");
					}
				}else{
					String[] arr = permList.split(",");
					List<Permissions> listPerm = new ArrayList<Permissions>();
					Permissions p = null;
					for(int i=0;i<arr.length;i++){								
						p = new Permissions();
						p.setName((permissionsService.getById(Integer.valueOf(arr[i]))).getName());
						p.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
						p.setCreatedDate(dateInIndia);
						p.setModifiedBy((Integer)request.getSession().getAttribute("userid")+"");
						p.setModifiedDate(dateInIndia);
						listPerm.add(p);
					}
					p = new Permissions();						
					p.setName(newPerm);
					p.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
					p.setCreatedDate(dateInIndia);
					p.setModifiedBy((Integer)request.getSession().getAttribute("userid")+"");
					p.setModifiedDate(dateInIndia);
					listPerm.add(p);
					permissionsService.save(listPerm);
					try {
						PrintWriter out = response.getWriter();
						out.print("'" + roleName + "' created.");
						out.flush();
					} catch (IOException e) {
						LOG.error("IOException in getData() in Controller ");
					}
				}
			} catch (NumberFormatException e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
		return "home";
	}
	/**
	 * This method updates the role with the user values
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam("roleId") String roleId, @RequestParam("permList") String permList,
    			@RequestParam("newPerm") String newPerm, HttpServletRequest request, HttpServletResponse response) {
		try {
			String[] arr = permList.split(",");
			List<Permissions> listPerm = new ArrayList<Permissions>();
			Permissions p = null;
			for(int i=0;i<arr.length;i++){								
				p = new Permissions();
				p.setName((permissionsService.getById(Integer.valueOf(arr[i]))).getName());
				p.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				p.setCreatedDate(dateInIndia);
				p.setModifiedBy((Integer)request.getSession().getAttribute("userid")+"");
				p.setModifiedDate(dateInIndia);
				listPerm.add(p);
			}
			p = new Permissions();						
			p.setName(newPerm);
			p.setCreatedBy((Integer)request.getSession().getAttribute("userid")+"");
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			p.setCreatedDate(dateInIndia);
			p.setModifiedBy((Integer)request.getSession().getAttribute("userid")+"");
			p.setModifiedDate(dateInIndia);
			listPerm.add(p);
			permissionsService.save(listPerm);
			try {
				PrintWriter out = response.getWriter();
				out.print("");
				out.flush();
			} catch (IOException e) {
				LOG.error("IOException in getData() in Controller ");
			}
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to assign the reports to the role
	 */
	@RequestMapping(value = "/assignreportstorole", method = RequestMethod.GET)
    public String assignreportstorole(Model model , HttpServletRequest request, HttpServletResponse response) {
		
		try {
			List<Role> roleList = new ArrayList<Role>();
			List<Role> roles = roleService.list();
			for(Role role:roles){
				if(!role.getName().equalsIgnoreCase("System Administrator")){
					if(!role.getName().equalsIgnoreCase("Application Administrator")){
						roleList.add(role);
				}
			}
			}
			model.addAttribute("crudObj",new RoleReportsPojo());
			model.addAttribute("other_reports",reportsService.getOtherReports());
			
			model.addAttribute("role_list",roleList);
		
			model.addAttribute("assignreportstorole","assignreportstorole");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method is used to save the reports to the role
	 */
	@RequestMapping(value = "/saveassignedreports", method = RequestMethod.POST)
    public String assignReportsToRoleCompleted(@Valid RoleReportsPojo entity,BindingResult bindingResult,Model model, HttpServletRequest request, HttpServletResponse response) {
		
		String displayMessage = null;
		try {
			List<Integer> beforeAssignedList = (List<Integer>) request.getSession().getAttribute("reportIds");
			List<Integer> assignedList = entity.getReportIds();
			//System.out.println("beforeAssignedList >>> "+beforeAssignedList);
			//System.out.println("assignedList >>> "+assignedList);
			int assignedReportSize = beforeAssignedList.size();
			int newlyAssignedReportSize = 0;
			if(assignedList != null)
				newlyAssignedReportSize = assignedList.size();

			if (assignedReportSize == 0 && newlyAssignedReportSize == 0) {
				displayMessage = "Please assign atleast one report";
				List<Role> roleList = new ArrayList<Role>();
				List<Role> roles = roleService.list();
				for (Role role : roles) {
					if (!role.getName()
							.equalsIgnoreCase("System Administrator")) {
						if (!role.getName().equalsIgnoreCase(
								"Application Administrator")) {
							roleList.add(role);
						}
					}
				}
				model.addAttribute("crudObj", new RoleReportsPojo());
				model.addAttribute("role_list", roleList);
				model.addAttribute("assignreportstorole", "assignreportstorole");
				model.addAttribute("displayMessage", displayMessage);
				return "home";
			} else if (assignedReportSize > 0 && newlyAssignedReportSize == 0) {
				if (assignedReportSize == 1)
					displayMessage = "1 report is removed";
				else
					displayMessage = assignedReportSize + " reports are removed ";
			} else if(assignedReportSize == 0 && newlyAssignedReportSize > 0){
				if(newlyAssignedReportSize == 1)
					displayMessage=" 1 report is assigned";
				else
					displayMessage = newlyAssignedReportSize+" reports are assigned ";
			}
			
			else{
				int sameReportAssigned = 0;
				for(Integer reportId : assignedList){
					
					for(Integer rid : beforeAssignedList){
						if(reportId.intValue() == rid.intValue())
							sameReportAssigned++;
					}
				}
			
				int removedReport = assignedReportSize - sameReportAssigned;
				if(removedReport == 0)
					displayMessage = "";
				else if(removedReport == 1)
					displayMessage = " and 1 report is removed";
				else
					displayMessage = " and "+removedReport+" reports are removed";
					
					int assignedReport = 	newlyAssignedReportSize - sameReportAssigned;
					
					if(assignedReport == 0 && assignedReportSize == newlyAssignedReportSize){
						displayMessage = "No changes are made";
						
						List<Role> roleList = new ArrayList<Role>();
						List<Role> roles = roleService.list();
						for (Role role : roles) {
							if (!role.getName()
									.equalsIgnoreCase("System Administrator")) {
								if (!role.getName().equalsIgnoreCase(
										"Application Administrator")) {
									roleList.add(role);
								}
							}
						}
						model.addAttribute("crudObj", new RoleReportsPojo());
						model.addAttribute("role_list", roleList);
						model.addAttribute("assignreportstorole", "assignreportstorole");
						model.addAttribute("displayMessage", displayMessage);
						return "home";
					}
					else if(assignedReport == 0){
						
							if(removedReport == 0)
								displayMessage = "";
							else if(removedReport == 1)
								displayMessage = " 1 report is removed";
							else
								displayMessage = removedReport+" reports are removed";
							
						
					}
					else if(assignedReport == 1)
						displayMessage = "1 report is assigned"+displayMessage;
					else
						displayMessage = assignedReport+" reports are assigned"+displayMessage;
				
			}
			//System.out.println(displayMessage);
			model.addAttribute("displayMessage", displayMessage);
			
			
				List<Role> roleList = new ArrayList<Role>();
				Integer roleId = entity.getRoleId();
				roleReportService.deleteByRoleId(roleId);
				List<Integer> reportIds = entity.getReportIds();
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				if(reportIds != null){
					Iterator<Integer> iterator = reportIds.iterator();
					while(iterator.hasNext()){
						Integer reportId = iterator.next();
						RoleReport roleReport = new RoleReport();
						RoleReportComp roleReportComp = new RoleReportComp();
						roleReportComp.setReportId(reportId);
						roleReportComp.setRoleId(roleId);
						roleReport.setRoleReportComp(roleReportComp);
						roleReport.setCreatedBy(entity.getCreatedBy());
						roleReport.setCreatedDate(dateInIndia);
						roleReport.setModifiedBy(entity.getModifiedBy());
						roleReport.setModifiedDate(dateInIndia);
						roleReportService.save(roleReport);
						model.addAttribute("roleassigned","Reports Assigned to the Role");
					}
				}else{
					model.addAttribute("roleassigned","Reports Assigned to the Role");
				}
				
					
				List<Role> roles = roleService.list();
				for(Role role:roles){
					if(!role.getName().equalsIgnoreCase("System Administrator")){
						if(!role.getName().equalsIgnoreCase("Application Administrator")){
							roleList.add(role);
						}
					}
				}
				model.addAttribute("crudObj", new RoleReportsPojo());
				model.addAttribute("other_reports",reportsService.getOtherReports());
				model.addAttribute("role_list",roleList);
				model.addAttribute("assignreportstorole","assignreportstorole");
			} catch (Exception e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
		return "home";
	}
	/**
	 * This is a ajax method that returns the reports which are assigned to the role 
	 */
	@RequestMapping(value="/getreportsdata",method=RequestMethod.GET)
	public void getusersdata(Model model,@RequestParam("roleId") String roleId,HttpServletRequest request,HttpServletResponse response)
	{
		if(roleId == null || roleId == ""){
			//System.out.println("getusersdata");
			return;
		}
		try {
			Integer roleid = Integer.parseInt(roleId);
			//System.out.println("inside /getReportsData : "+roleid);
			List<Integer> reporId = new ArrayList<Integer>();
			String moduleIds = "";
			List<Module> moduleList = moduleService.getByRoleId(roleid);
			if(moduleList != null){
				Iterator<Module> iterator = moduleList.iterator();
				while(iterator.hasNext()){
					Module m = iterator.next();
					if(moduleIds == ""){
						moduleIds = m.getId()+"";
					}else{
						moduleIds = moduleIds +","+m.getId()+"";
					}
				}
				//System.out.println("moduleIds : "+moduleIds);
			}
			
			String trReports = "";
			List<DashBoardReports> reportsList = reportsService.findByModuleId(moduleIds);
			List<DashBoardReports> reList = reportsService.getReportsByRoleId(roleid);
			Iterator<DashBoardReports> itr = reList.iterator();
			while(itr.hasNext()){
				DashBoardReports dr = itr.next();
				reporId.add(dr.getId());
			}
			for(DashBoardReports list : reportsList){
				Integer id = list.getId();
				
				if(reporId.contains(id)){
				}else{
					trReports += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
					//System.out.println("list.getId():"+list.getId()+" , "+list.getName());
				}
				
			}
			try{
				PrintWriter out = response.getWriter();
				//System.out.println("trReports : "+trReports);
				out.println(trReports);
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
			model.addAttribute("report_list1", reportsService.getReportsByRoleId(roleid));
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	}
	/**
	 * This is a ajax method which will return the reports which are not assigned to the role by taking the roleid as the parameter
	 */
	@RequestMapping(value="/getreportsdata1",method=RequestMethod.GET)
	public void getusersdata1(Model model,@RequestParam("roleId") String roleId,HttpServletRequest request,HttpServletResponse response)
	{
		if(roleId == null || roleId == ""){
			//System.out.println("getusersdata1");
			return;
		}
		List<Integer> reportIds = new ArrayList<Integer>();
		try {
			Integer roleid = Integer.parseInt(roleId);
			String traReports = "";
			List<DashBoardReports> reportsList = reportsService.getReportsByRoleId(roleid);
			if(reportsList != null){
				for(DashBoardReports list : reportsList){
					traReports += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
					reportIds.add(list.getId());
				}
			}
			//System.out.println("traReports : >>>> "+traReports);
			request.getSession().setAttribute("reportIds",reportIds);
			//System.out.println("Added to Session : "+reportIds);
			try{
				PrintWriter out = response.getWriter();
				out.println(traReports);
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
	public QTAdminService<Role> getTService() {
		return roleService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}
}
