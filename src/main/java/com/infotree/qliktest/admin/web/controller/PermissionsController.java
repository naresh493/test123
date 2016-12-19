package com.infotree.qliktest.admin.web.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
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

import com.infotree.qliktest.admin.entity.referencedata.AuditLogRecord;
import com.infotree.qliktest.admin.entity.referencedata.ModulePermissions;
import com.infotree.qliktest.admin.entity.referencedata.Permissions;
import com.infotree.qliktest.admin.entity.referencedata.RolePermissions;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ModulePermissionsService;
import com.infotree.qliktest.admin.service.referencedata.PermissionsService;
import com.infotree.qliktest.admin.service.referencedata.RolePermissionsService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/permissions")
public class PermissionsController extends AbstractQTAdminController<Permissions> {
	
	public static final int LIMITSPERPAGE=10;
	
	private static final Logger LOG = LoggerFactory.getLogger(PermissionsController.class);
	
	@Autowired
	private PermissionsService permissionsService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	@Autowired
	private RolePermissionsService rolePermissionsService;
	
	@Autowired
	private ModulePermissionsService modulePermissionsService;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private DoNothingValidator validator;
	
	/**
	 * This method returns the UI to create a new permission
	 */
	@RequestMapping(value="/createpermission", method=RequestMethod.GET)
	public String createPermissions(Model model,HttpServletRequest request){
		HttpSession ss = request.getSession();
		model.addAttribute("crudObj",getEntityInstance());
		model.addAttribute("createpermission","createpermission");
		//model.addAttribute("permission_list",permissionsService.list());
		
		return "home";
	}
	/**
	 * This method is to view the list of permissions for the first page
	 */
	/*@RequestMapping(value="/viewpermissions", method=RequestMethod.GET)
	public String viewPermissions(Model model){
			
			int numberOfPages = 0;
			model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("viewpermissions","viewpermissions");
			int count = permissionsService.permissionsCount();
			if(count % 10 != 0){
				numberOfPages = (count /10)+1;
			}else{
				numberOfPages = count /10;
			}
			model.addAttribute("noofpages", numberOfPages);
			model.addAttribute("permission_list",permissionsService.getOrderBydesc());
			
		return "home";
	}*/
	/**
	 * This method is to search the permissions by taking the name as the parameter
	 */
	@RequestMapping(value="/searchpermissions", method = RequestMethod.POST)
	public String searchPermissions(@ModelAttribute("crudObj") @Valid Permissions entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		//model.addAttribute("crudObj",getEntityInstance());
		String pattern = entity.getName().trim();
		request.getSession().setAttribute("pattern", pattern);
		int count = permissionsService.permissionsCountByPattern(pattern);
		int numberOfPages = count/10;
		if(count % 10 != 0){
			numberOfPages = numberOfPages+1;
		}
		model.addAttribute("numberofpages", numberOfPages);
		model.addAttribute("currentpage", "0");
		List<Permissions> permList = permissionsService.getByPageByPattern(pattern, (long)1);
		model.addAttribute("permission_list", permList);
		model.addAttribute("viewpermissions", "viewpermissions");
		return "home";
	}
	/**
	 * This method is to search the permissins by taking the name as the parameter
	 */
	@RequestMapping(value="/searcheditpermissions", method = RequestMethod.POST)
	public String searchPermissionsForEdit(@ModelAttribute("crudObj") @Valid Permissions entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		String pattern = entity.getName().trim();
		request.getSession().setAttribute("pattern", pattern);
		try {
			int count = permissionsService.permissionsCountByPattern(pattern);
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			model.addAttribute("numberofpages", numberOfPages);
			model.addAttribute("currentpage", "0");
		//	List<Permissions> permList = permissionsService.getByPageEdit((long)1);
			List<Permissions> permList =  permissionsService.getByPageByPattern(pattern, (long)1);
			List<Permissions> permissionsList = new ArrayList<Permissions>();
			if(permList != null){
				Iterator<Permissions> iterator = permList.iterator();
				while(iterator.hasNext()){
					Permissions p = iterator.next();
					if(p.getModifiedBy() != null){
						Integer id = Integer.parseInt(p.getModifiedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(p.getCreatedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}
					
					permissionsList.add(p);
				}
			}
			
			
			model.addAttribute("permission_list", permissionsList);
			model.addAttribute("editpermission","editpermission");
		}
		catch(Exception e){
			//System.out.println(e);
			LOG.error(e.toString());
		}
		
		/*List<Permissions> permList = permissionsService.searchByPettern(entity.getName());
		List<Permissions> permissionsList = new ArrayList<Permissions>();
		if(permList != null){
			Iterator<Permissions> iterator = permList.iterator();
			while(iterator.hasNext()){
				Permissions p = iterator.next();
				if(p.getModifiedBy() != null){
					Integer id = Integer.parseInt(p.getModifiedBy());
					User u = userService.getById(id);
					p.setCreatedName(u.getUserName());
				}else{
					Integer id = Integer.parseInt(p.getCreatedBy());
					User u = userService.getById(id);
					p.setCreatedName(u.getUserName());
				}
				
				permissionsList.add(p);
			}
		}
	*/	
		entity.setName(entity.getName());
		return "home";
	}
	/**
	 * This method returns the UI to edit the permission
	 */
	@RequestMapping(value="/editpermission", method=RequestMethod.GET)
	public String editPermissions(@ModelAttribute("crudObj") @Valid Permissions entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		try {
			request.getSession().setAttribute("pattern", null);
			int count = permissionsService.permissionsCount();
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			model.addAttribute("numberofpages", numberOfPages);
			model.addAttribute("currentpage", "0");
			List<Permissions> permList = permissionsService.getByPageEdit((long)1);
			
			List<Permissions> permissionsList = new ArrayList<Permissions>();
			if(permList != null){
				Iterator<Permissions> iterator = permList.iterator();
				while(iterator.hasNext()){
					Permissions p = iterator.next();
					if(p.getModifiedBy() != null){
						Integer id = Integer.parseInt(p.getModifiedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(p.getCreatedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}
					
					permissionsList.add(p);
				}
			}
			
			
			model.addAttribute("permission_list", permissionsList);
			model.addAttribute("editpermission","editpermission");
		}
		catch(Exception e){
			//System.out.println(e);
			LOG.error(e.toString());
		}
		return "home";
			/*
			model.addAttribute("editpermission","editpermission");
			
			List<Permissions> permList = permissionsService.getOrderBydesc();
			List<Permissions> permissionsList = new ArrayList<Permissions>();
			if(permList != null){
				Iterator<Permissions> iterator = permList.iterator();
				while(iterator.hasNext()){
					Permissions p = iterator.next();
					if(p.getModifiedBy() != null){
						Integer id = Integer.parseInt(p.getModifiedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(p.getCreatedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}
					
					permissionsList.add(p);
				}
			}
			model.addAttribute("permission_list",permissionsList);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "home";*/
	}
	/**
	 * This method returns the UI to edit the user selected permission in the editable mode
	 */
	@RequestMapping(value="/edit/{permissionId}/update", method=RequestMethod.GET)
	public String updatePermission(@PathVariable Integer permissionId, Model model) {
		try {
			Permissions entityToBeUpdated = permissionsService.getById(permissionId);
			
			model.addAttribute("crudObj", entityToBeUpdated);
			List<RolePermissions> rolePermissions = rolePermissionsService.findByPermissionId(entityToBeUpdated.getId());
			List<ModulePermissions> modulePermissions = modulePermissionsService.findByPermissionsId(entityToBeUpdated.getId());
			if(rolePermissions != null && modulePermissions != null){
				entityToBeUpdated.setDisableStatus("Permission Alrady assigned to role and Package");
			}else if(rolePermissions != null && modulePermissions == null){
				entityToBeUpdated.setDisableStatus("Permission Alrady assigned to role");
			}else if(rolePermissions == null && modulePermissions != null){
				entityToBeUpdated.setDisableStatus("Permission Alrady assigned to Package");
			}else{
				entityToBeUpdated.setDisableStatus("");
			}
			model.addAttribute("idToBeUpdated", entityToBeUpdated.getId());
			model.addAttribute("updatepermissions", "updatepermissions");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.toString());
		}
		return "home";
	}
	/**
	 * This method updates the permission with newly entered values
	 */
	@RequestMapping(value="/updatePermission", method=RequestMethod.POST)
	public String updatePermission(@ModelAttribute("crudObj") @Valid Permissions entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			request.getSession().setAttribute("pattern", null);
			Permissions permissions = permissionsService.getById(entity.getId());
			Permissions perm = permissionsService.getPermissionsByNameAndId(entity.getName(), entity.getId());
			if(perm != null){
				model.addAttribute("permissionupdated", "Permission Already Exists");
			}else{
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
				permissionsService.updatePermissions(entity);
				/**
				 *Preparing the audit log 
				 */
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(dateInIndia);
				record.setActionType(AuditType.MODIFY);
				record.setActionPerformed("Old Permissions Name :"+permissions.getName()+" And new Permission name is "+entity.getName());
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
				model.addAttribute("permissionupdated", "Permission Updated Successfully");
			}
			/*model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("editpermission","editpermission");
			List<Permissions> permList = permissionsService.list();
			List<Permissions> permissionsList = new ArrayList<Permissions>();
			if(permList != null){
				Iterator<Permissions> iterator = permList.iterator();
				while(iterator.hasNext()){
					Permissions p = iterator.next();
					if(p.getModifiedBy() != null){
						Integer id = Integer.parseInt(p.getModifiedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(p.getCreatedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}
					permissionsList.add(p);
				}
			*/
			int count = permissionsService.permissionsCount();
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			model.addAttribute("numberofpages", numberOfPages);
			model.addAttribute("currentpage", "0");
			List<Permissions> permList = permissionsService.getByPageEdit((long)1);
			
			List<Permissions> permissionsList = new ArrayList<Permissions>();
			if(permList != null){
				Iterator<Permissions> iterator = permList.iterator();
				while(iterator.hasNext()){
					Permissions p = iterator.next();
					if(p.getModifiedBy() != null){
						Integer id = Integer.parseInt(p.getModifiedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(p.getCreatedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}
					
					permissionsList.add(p);
				}
			}
			model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("permission_list", permissionsList);
			model.addAttribute("editpermission","editpermission");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error(e.toString());
		}

		/*try {
			request.getSession().setAttribute("pattern", null);
			int count = permissionsService.permissionsCount();
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			model.addAttribute("numberofpages", numberOfPages);
			model.addAttribute("currentpage", "0");
			List<Permissions> permList = permissionsService.getByPageEdit((long)1);
			
			List<Permissions> permissionsList = new ArrayList<Permissions>();
			if(permList != null){
				Iterator<Permissions> iterator = permList.iterator();
				while(iterator.hasNext()){
					Permissions p = iterator.next();
					if(p.getModifiedBy() != null){
						Integer id = Integer.parseInt(p.getModifiedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(p.getCreatedBy());
						User u = userService.getById(id);
						p.setCreatedName(u.getUserName());
					}
					
					permissionsList.add(p);
				}
			}
			
			
			model.addAttribute("permission_list", permissionsList);
			model.addAttribute("editpermission","editpermission");
		}
		catch(Exception e){
			System.out.println(e);
		}*/
		return "home";
		
	
	}
	/**
	 * This method is to save the new permission
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(@ModelAttribute("crudObj") @Valid Permissions entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		
		if(permissionsService.findByName(entity.getName()) != null){
			model.addAttribute("createpermission", "createpermission");
			model.addAttribute("permission_list", permissionsService.list());
			
			model.addAttribute("permissionalreadyexists", "'" + entity.getName()+"' already exists. Pl. enter a different Name." );
		}else{
			Permissions permissions = new Permissions();
			permissions.setId(entity.getId());
			permissions.setName(entity.getName().trim());
			permissions.setAliasName(entity.getAliasName().trim());
			permissions.setCreatedBy(entity.getCreatedBy());
			
			/*DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			permissions.setCreatedDate(dateInIndia);*/
			
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			String clientZone = clientTimeZone.getID();
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID(clientZone);
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			permissions.setCreatedDate(dateInIndia);
			permissions.setDescription(entity.getDescription());
			permissions.setDisabled(entity.getDisabled());
			permissionsService.save(permissions);
			/**
			 * Preparing the audit log record
			 */
			AuditLogRecord record = new AuditLogRecord();
			record.setActionDate(dateInIndia);
			//record.setActionDate(entity.getCreatedDate());
			//record.setCreationDate(entity.getCreationDate());
			//record.setActionDate(new Date(entity.getCreationDate()));
			record.setActionType(AuditType.CREATE);
			record.setActionPerformed("created new Permission with name "+entity.getName());
			record.setActionData("Permission Name :"+entity.getName());
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
			
			model.addAttribute("createpermission", "createpermission");
			model.addAttribute("permissioncreated", "'" + entity.getName() + "' created." );
		}
		model.addAttribute("createpermission", "createpermission");
		model.addAttribute("crudObj",getEntityInstance());
		return "home";
	}
	/**
	 * This method returns the UI to edit the permission
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String edit(@ModelAttribute("crudObj") @Valid Permissions entity,BindingResult bindingResult,Model model){
		
		model.addAttribute("crudObj",getEntityInstance());
		model.addAttribute("editpermission","editpermission");
		return "home";
	}
	/**
	 * This mehtod returns the UI to delete the permission with the list of permissions
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(@ModelAttribute("crudObj") @Valid Permissions entity,BindingResult bindingResult,Model model){
		model.addAttribute("crudObj",getEntityInstance());
		model.addAttribute("deletepermission","deletepermission");
		return "home";
	}
	/**
	 * This method is to view the permissions by applying the pagination
	 */
	@RequestMapping(value="/viewpermission",method = RequestMethod.GET)
	public String getPermissionsPagination(Model model,HttpServletRequest request){
		request.getSession().setAttribute("pattern", null);
		int count = permissionsService.permissionsCount();
		int numberOfPages = count/10;
		if(count % 10 != 0){
			numberOfPages = numberOfPages+1;
		}
		model.addAttribute("numberofpages", numberOfPages);
		model.addAttribute("currentpage", "0");
		List<Permissions> permList = permissionsService.getByPage((long)1);
		model.addAttribute("permission_list", permList);
		model.addAttribute("viewpermissions", "viewpermissions");
		return "home";
	}
	/**
	 * This method is to apply the pagination
	 */
	@RequestMapping(value="viewpagepermissions/{page}",method = RequestMethod.GET)
	public String viewPagePermissions(@PathVariable Integer page,HttpServletRequest request,Model model){
		String pattern = (String)request.getSession().getAttribute("pattern");
		int totalRecords = 0;
		if(pattern == null)
			totalRecords = permissionsService.permissionsCount();
		else
			totalRecords = permissionsService.permissionsCountByPattern(pattern);
		int limitsPerPage = 10;
		int noOfPages = totalRecords/10;
		if(totalRecords/10 != 0){
			noOfPages = noOfPages+1;
		}
		if(page == noOfPages-1){
			limitsPerPage = totalRecords % 10;
			
		}
		model.addAttribute("numberofpages", noOfPages);
		model.addAttribute("currentpage", page);
		List<Permissions> permList = null;
		if(pattern == null)
			permList = permissionsService.getPermissionsByPageAndCount(page, limitsPerPage);
		else
			permList = permissionsService.getPermissionsByPageAndCountByPattern(pattern, page, limitsPerPage);
		model.addAttribute("permission_list", permList);
		model.addAttribute("viewpermissions", "viewpermissions");
		return "home";
	}
	@RequestMapping(value="editpagepermissions/{page}",method = RequestMethod.GET)
	public String editPagePermissions(@PathVariable Integer page,HttpServletRequest request,Model model){
		String pattern = (String)request.getSession().getAttribute("pattern");
		int totalRecords = 0;
		if(pattern == null)
			totalRecords = permissionsService.permissionsCount();
		else
			totalRecords = permissionsService.permissionsCountByPattern(pattern);
		int limitsPerPage = 10;
		int noOfPages = totalRecords/10;
		if(totalRecords/10 != 0){
			noOfPages = noOfPages+1;
		}
		if(page == noOfPages-1){
			limitsPerPage = totalRecords % 10;
		}
		model.addAttribute("numberofpages", noOfPages);
		model.addAttribute("currentpage", page);
		List<Permissions> permList = null;
		if(pattern == null)
			permList = permissionsService.getPermissionsByPageAndCount(page, limitsPerPage);
		else
			permList = permissionsService.getPermissionsByPageAndCountByPattern(pattern, page, limitsPerPage);
		
		List<Permissions> permissionsList = new ArrayList<Permissions>();
		if(permList != null){
			Iterator<Permissions> iterator = permList.iterator();
			while(iterator.hasNext()){
				Permissions p = iterator.next();
				if(p.getModifiedBy() != null){
					Integer id = Integer.parseInt(p.getModifiedBy());
					User u = userService.getById(id);
					p.setCreatedName(u.getUserName());
				}else{
					Integer id = Integer.parseInt(p.getCreatedBy());
					User u = userService.getById(id);
					p.setCreatedName(u.getUserName());
				}
				
				permissionsList.add(p);
			}
		}
		
		model.addAttribute("permission_list", permissionsList);
		//model.addAttribute("viewpermissions", "viewpermissions");
		model.addAttribute("editpermission","editpermission");
		return "home";
	}
	@Override
	public QTAdminService<Permissions> getTService() {
		return permissionsService;
	}
	
	@Override
	protected Validator getValidator() {
		return validator;
	}

}
