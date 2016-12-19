package com.infotree.qliktest.admin.web.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

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
import com.infotree.qliktest.admin.entity.referencedata.Constraints;
import com.infotree.qliktest.admin.entity.referencedata.DashBoardReports;
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.entity.referencedata.ModuleConstraintComp;
import com.infotree.qliktest.admin.entity.referencedata.ModuleConstraints;
import com.infotree.qliktest.admin.entity.referencedata.ModulePermissions;
import com.infotree.qliktest.admin.entity.referencedata.ModulePermissionsComp;
import com.infotree.qliktest.admin.entity.referencedata.ModuleReportComp;
import com.infotree.qliktest.admin.entity.referencedata.ModuleReportsAssign;
import com.infotree.qliktest.admin.entity.referencedata.Permissions;
import com.infotree.qliktest.admin.entity.referencedata.ProjectTenant;
import com.infotree.qliktest.admin.entity.referencedata.Role;
import com.infotree.qliktest.admin.entity.referencedata.RoleModule;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.entity.system.AuditType;
import com.infotree.qliktest.admin.helperpojo.ModulePermissionsPojo;
import com.infotree.qliktest.admin.helperpojo.ModulePojo;
import com.infotree.qliktest.admin.helperpojo.ModuleReportsPojo;
import com.infotree.qliktest.admin.helperpojo.RoleModulePojo;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.AuditLogRecordService;
import com.infotree.qliktest.admin.service.referencedata.ConstraintsService;
import com.infotree.qliktest.admin.service.referencedata.ModuleConstraintsService;
import com.infotree.qliktest.admin.service.referencedata.ModulePermissionsService;
import com.infotree.qliktest.admin.service.referencedata.ModuleReportService;
import com.infotree.qliktest.admin.service.referencedata.ModuleService;
import com.infotree.qliktest.admin.service.referencedata.PermissionsService;
import com.infotree.qliktest.admin.service.referencedata.ProjectTenantService;
import com.infotree.qliktest.admin.service.referencedata.ReportsService;
import com.infotree.qliktest.admin.service.referencedata.RoleModuleService;
import com.infotree.qliktest.admin.service.referencedata.RoleService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/module")
public class ModuleController extends AbstractQTAdminController<Module> {
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private RoleModuleController roleModuleController;
	
	@Autowired
	private ConstraintsService constraintsService;
	
	@Autowired
	private AuditLogRecordService auditRecordService;
	
	@Autowired
	private RoleModuleService roleModuleService;
	
	@Autowired
	private RoleService roleService;
	
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ReportsService reportsService;
	
	@Autowired
	private ModuleReportService moduleReportService;
	
	@Autowired
	private ModuleConstraintsService moduleConstraintsService;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private PermissionsService permissionsService;
	
	@Autowired
	private ProjectTenantService projectTenantService;
	
	@Autowired
	private AuditLogRecordService auditLogService;
	
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private ModulePermissionsService modulePermissionsService;
	
	@Autowired
	private DoNothingValidator validator;
	
	/**
	 * This method returns the UI to create the new module/package
	 */
	
	@RequestMapping(value = "/createmodule", method = RequestMethod.GET)
    public String landingPage(Model model) {
		try {
			model.addAttribute("crudObj", getEntityInstance());
			model.addAttribute("createmodule", "createmodule");
			model.addAttribute("permissions_list", permissionsService.list());
			model.addAttribute("reports_list", reportsService.list());
			model.addAttribute("active_module_list", moduleService.getModuleList());
			model.addAttribute("constraints_list",constraintsService.list());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to save the new module/package
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid Module entity, BindingResult bindingResult, Model model,HttpServletRequest request) {
		try {
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			Module m = null;
			String constraintId = entity.getConstraintId();
			String value = entity.getValue();
			
			String[] constraints  =constraintId.split(",");
			String[] values = value.split(",");
			
			int numDupes = 0;

			for(int i = 1; i < constraints.length; i++) {
			    if(constraints[i].equals(constraints[i-1])) {
			       numDupes++;
			    }
			}
			
			if(numDupes != 0){
				model.addAttribute("createmodule", "createmodule");
				model.addAttribute("active_module_list", moduleService.getModuleList());
				model.addAttribute("constraints_list",constraintsService.list());
				model.addAttribute("modulealreadyexists", "You Can Not Assign Two Values for the same control" );
				model.addAttribute("permissions_list", permissionsService.list());
				model.addAttribute("reports_list", reportsService.list());
			}else{
				
				String[] namelist = entity.getName().split(",");
				String name = namelist[0];
				model.addAttribute("crudObj", getEntityInstance());
					if(moduleService.findByName(name) != null){
						model.addAttribute("createmodule", "createmodule");
						model.addAttribute("active_module_list", moduleService.getModuleList());
						model.addAttribute("modulealreadyexists", "Package already exists. Pl. enter a different Name." );
					}else{
						Module module = new Module();
						module.setName(name.trim());
						module.setDescription(entity.getDescription());
						module.setCreatedBy(entity.getCreatedBy());
						
						module.setCreatedDate(dateInIndia);
						m = moduleService.save(module); 
							/**
							 * Prepare the audit log record
							 */
							AuditLogRecord record = new AuditLogRecord();
							record.setActionDate(new java.util.Date());
							record.setActionType(AuditType.CREATE);
							record.setActionData("Module Name :"+name);
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
							//assigning the permissions to the module
							List<Integer> permissionsList = entity.getPermissionsId();
							if(permissionsList != null){
								Iterator<Integer> iterator = permissionsList.iterator();
								while(iterator.hasNext()){
									ModulePermissions md = new ModulePermissions();
									ModulePermissionsComp modperComp = new ModulePermissionsComp();
									modperComp.setModuleId(module.getId());
									modperComp.setPermissionId(iterator.next());
									md.setModulePermissionsComp(modperComp);
									md.setCreatedBy(entity.getCreatedBy());
									md.setCreatedDate(dateInIndia);
									
									modulePermissionsService.save(md);
								}
							}
							/**
							 * assign reports to the module
							 */
							List<Integer> reportsList = entity.getReportIds();
							if(reportsList != null){
								Iterator<Integer> iterator = reportsList.iterator();
								while(iterator.hasNext()){
									ModuleReportsAssign moduleReport = new ModuleReportsAssign();
									ModuleReportComp moduleReportComp = new ModuleReportComp();
									moduleReportComp.setModuleId(module.getId());
									moduleReportComp.setReportId(iterator.next());
									moduleReport.setModuleReportComp(moduleReportComp);
									moduleReport.setCreatedBy(entity.getCreatedBy());
									moduleReport.setCreatedDate(dateInIndia);
									moduleReportService.save(moduleReport);
									model.addAttribute("moduleassigned","Reports Assigned");
								}
							}
							
							/**
							 * assigning the constraints and values to the package
							 */
							ModuleConstraintComp comp = new ModuleConstraintComp();
							ModuleConstraints moduleConstraints = new ModuleConstraints();
							for(int i =0;i<constraints.length;i++){
								comp.setConstraintId(Integer.parseInt(constraints[i]));
								comp.setModuleId(m.getId());
								moduleConstraints.setModuleConstraintComp(comp);
								if(values[i]==null || values[i].trim().equals(""))
								moduleConstraints.setValue(0);	
								else
								moduleConstraints.setValue(Integer.parseInt(values[i]));
								moduleConstraints.setCreatedBy(entity.getCreatedBy());
								moduleConstraints.setCreatedDate(dateInIndia);
								moduleConstraintsService.save(moduleConstraints);
							}
							model.addAttribute("permissions_list", permissionsService.list());
							model.addAttribute("reports_list", reportsService.list());
							model.addAttribute("createmodule", "createmodule");
							model.addAttribute("active_module_list", moduleService.getModuleList());
							model.addAttribute("modulecreated", "Package '" + name + "' created." );
							model.addAttribute("constraints_list",constraintsService.list());
					}
			}
			
			entity.setName(null);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to view the list of modules/packages
	 */
	@RequestMapping(value="/viewmodule",method=RequestMethod.GET)
	public String viewModule(Model model, HttpServletRequest request){
		
		try {
			request.getSession().setAttribute("query", null);
			
			int count = moduleService.getModuleCount();
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			model.addAttribute("numberofpages", numberOfPages);
			model.addAttribute("currentpage", "0");
			
			model.addAttribute("crudObj",new ModulePojo());
			model.addAttribute("viewmodule","viewmodule");
			List<ModulePojo> modules = new ArrayList<ModulePojo>();
			
			List<Module> list = moduleService.getByPage((long)1);
			Iterator<Module> itr = list.iterator();
			while(itr.hasNext()){
				String permissions = "";
				String roles = "";
				String reports = "";
				String constraints = "";
				ModulePojo pojo = new ModulePojo();
				Module m = itr.next();
				Integer countOfPermissions = modulePermissionsService.getPermissionsCountByModuleId(m.getId());
				Integer countOfReports=moduleReportService.getReportCountByModuleId(m.getId());
				Integer countOfRoles  = roleModuleService.getRoleCountByModuleId(m.getId());
				Integer countOfConstraints = moduleConstraintsService.getCountOfConstraints(m.getId());
				pojo.setName(m.getName());
				pojo.setDescription(m.getDescription());
				pojo.setNoOfPermissions(countOfPermissions);
				pojo.setNoOfReports(countOfReports);
				pojo.setNoOfRoles(countOfRoles);
				pojo.setNoOfControls(countOfConstraints);
				pojo.setCreatedDate(m.getCreatedDate());
				pojo.setModifiedDate(m.getModifiedDate());
				modules.add(pojo);
				List<ModulePermissions> permList = modulePermissionsService.findByModuleId(m.getId());
				
				List<RoleModule> roleList = roleModuleService.findByModuleId(m.getId());
				
				List<ModuleReportsAssign> reportList = moduleReportService.getByModuleId(m.getId());
				
				List<ModuleConstraints> modConList = moduleConstraintsService.findByModuleId(m.getId());
				
				if(countOfPermissions == 0){
					
					permissions = "No Permissions Available";
				}
				if(!(countOfPermissions == 0)){
					if(permList != null){
						Iterator<ModulePermissions> iterator = permList.iterator();
						while(iterator.hasNext()){
							ModulePermissions pm = iterator.next();
							Permissions m1 = permissionsService.getById(pm.getModulePermissionsComp().getPermissionId());
							if(permissions == "")
								permissions = m1.getName();
							else
								permissions = permissions+" "+"<br>"+m1.getName();
						}
					}
					
				}
				
				
				if(countOfRoles==0){
					roles="No Roles Available";
				}
				if(!(countOfRoles==0)){
					if(roleList != null){
						Iterator<RoleModule> iteretoRole = roleList.iterator();
						while(iteretoRole.hasNext()){
							RoleModule rm=iteretoRole.next();
							Role r1 = roleService.getById(rm.getRoleModuleComp().getRoleId());
							if(roles == "")
								roles = r1.getName();
							
							else 
								roles = roles+" "+"<br>"+r1.getName();
							}
					}
					
				}
				if(countOfConstraints == 0){
					constraints = "No Constraints Available";
				}else{
					if(modConList != null){
						Iterator<ModuleConstraints> iterator = modConList.iterator();
						while(iterator.hasNext()){
							ModuleConstraints modCon = iterator.next();
							Constraints con = constraintsService.getById(modCon.getModuleConstraintComp().getConstraintId());
							if(constraints == "")
								constraints = con.getName();
							
							else 
								constraints = constraints+" "+"<br>"+con.getName();
						}
					}
				}
					try{
					if(countOfReports == 0){
						reports = " No Reports Available";
						
					}
					if(!(countOfReports == 0)){
						if(reportList != null){
							Iterator<ModuleReportsAssign> iteratorReport = reportList.iterator();
							while(iteratorReport.hasNext()){
								ModuleReportsAssign mr = iteratorReport.next();
								DashBoardReports d1 = reportsService.getById(mr.getModuleReportComp().getReportId());
								if(reports == "")
									reports = d1.getName();
								else
									reports = reports +""+"<br>"+d1.getName();
							}
						}
						
						
					}
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					pojo.setRoleNames("<center><table width=70% border=1><tr><th><center>Role Names</center></th></tr><tr><td><center>"+roles+"</center></td></tr></table></center>");
					pojo.setPermissionNames("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
					pojo.setReportNames("<center><table width=70% border=1><tr><th><center>Report Names</center></th></tr><tr><td><center>"+reports+"</center></td></tr></table></center>");	
					pojo.setControleNames("<center><table width=70% border=1><tr><th><center>Control Names</center></th></tr><tr><td><center>"+constraints+"</center></td></tr></table></center>");
			}
			model.addAttribute("permissions_list",permissionsService.list());
			model.addAttribute("roles_list",roleService.list());
			model.addAttribute("reports_list", reportsService.list());
			model.addAttribute("constraints_list",constraintsService.list());
			model.addAttribute("module_list",modules);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to view the packages which are assigned to the particular app admin
	 */
	@RequestMapping(value="/viewpackages",method=RequestMethod.GET)
	public String viewModuleByUser(Model model,HttpServletRequest request){
	
		try {
			UserTenant userTenant = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
			List<ProjectTenant> list = projectTenantService.getByTenantId(userTenant.getUserTenantComp().getTenantId());
			String projIds = "";
			Iterator<ProjectTenant> itr = list.iterator();
			while(itr.hasNext()){
				ProjectTenant tenant = itr.next();
				if(projIds == ""){
					projIds = tenant.getProjectTenantComp().getProjectId()+"";
				}else{
					projIds = projIds+","+tenant.getProjectTenantComp().getProjectId()+"";
				}
			}
			List<Module> moduleList = moduleService.getModulesByProjId(projIds);
			model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("viewlicenses","viewlicenses");
			model.addAttribute("module_list",moduleList);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to edit the module with the list of modules/packages
	 */
	@RequestMapping(value="/editmodule",method=RequestMethod.GET)
	public String editModule(Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("editmodule","editmodule");
			List<Module> moduleList = moduleService.getOrderBydesc();
			List<Module> modules = new ArrayList<Module>();
			if(moduleList != null){
				Iterator<Module> iterator = moduleList.iterator();
				while(iterator.hasNext()){
					Module m = iterator.next();
					Integer userId;
					if(m.getModifiedBy() != null){
						 userId = Integer.parseInt(m.getModifiedBy()); 
						 User u = userService.getById(userId);
						 m.setCreatedName(u.getUserName());
					}else{
						 userId = Integer.parseInt(m.getCreatedBy()); 
						 User u = userService.getById(userId);
						 m.setCreatedName(u.getUserName());
					}
					
					modules.add(m);
				}
			}
			model.addAttribute("permissions_list",permissionsService.list());
			model.addAttribute("roles_list",roleService.list());
			model.addAttribute("reports_list", reportsService.list());
			model.addAttribute("constraints_list",constraintsService.list());
			model.addAttribute("module_list",modules);
		} catch (NumberFormatException e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to edit the module with the selected module in the editable mode
	 */
	@RequestMapping(value="/edit/{moduleId}/update", method=RequestMethod.GET)
	public String updateModule(@PathVariable Integer moduleId, Model model) {
		
			try {
				Module entityToBeUpdated = moduleService.getById(moduleId);
				model.addAttribute("crudObj", entityToBeUpdated);
				model.addAttribute("idToBeUpdated", entityToBeUpdated.getId());
				model.addAttribute("updatemodules", "updatemodules");
				model.addAttribute("available_permissions_list",permissionsService.getByModuleId(entityToBeUpdated.getId()));
				model.addAttribute("assigned_permissions_list",permissionsService.getPermissionsNotInModule(entityToBeUpdated.getId()));
				model.addAttribute("available_reports_list",reportsService.getReportsByModuleId(entityToBeUpdated.getId()));
				model.addAttribute("assigned_reports_list",reportsService.getReportsByModuleId1(entityToBeUpdated.getId()));
				List<Constraints> conList = new ArrayList<Constraints>();
				List<ModuleConstraints> modConList = moduleConstraintsService.findByModuleId(moduleId);
				
				if(modConList != null){
					Iterator<ModuleConstraints> iterator = modConList.iterator();
					while(iterator.hasNext()){
						ModuleConstraints mc = iterator.next();
						Constraints con = constraintsService.getById(mc.getModuleConstraintComp().getConstraintId());
						List<Constraints> list = constraintsService.findNotById(mc.getModuleConstraintComp().getConstraintId());
						con.setRemainList(list);
						con.setValue(mc.getValue());
						conList.add(con);
					}
				}
				model.addAttribute("constraints_list",conList);
				model.addAttribute("list_constraints",constraintsService.list());
			} catch (Exception e) {
				LOG.error(e.toString());
				e.printStackTrace();
			}
			
		return "home";
	}
	/**
	 * This method returns the UI to assign the permissions to the module
	 */
	@RequestMapping(value="/assignpermissionstomodule",method=RequestMethod.GET)
	public String assignPermissionsToModule(Model model,HttpServletRequest request,HttpServletResponse response){
		
		try {
			model.addAttribute("crudObj",new ModulePermissionsPojo());
			model.addAttribute("assignpermissionstomodule","assignpermissionstomodule");
			model.addAttribute("module_list",moduleService.list());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to save the permissioins to the module
	 */
	@RequestMapping(value="/saveassignpermissionstomodule",method=RequestMethod.POST)
	public String saveAssignPermissionsToModule(@Valid ModulePermissionsPojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try {
			List<Integer> permissionIds = entity.getPermissionsId();
			modulePermissionsService.deleteByModuleId(entity.getModuleId());
			if(permissionIds != null){
				Iterator<Integer> iterator = permissionIds.iterator();
				while(iterator.hasNext()){
					ModulePermissions md = new ModulePermissions();
					ModulePermissionsComp modperComp = new ModulePermissionsComp();
					modperComp.setModuleId(entity.getModuleId());
					modperComp.setPermissionId(iterator.next());
					md.setModulePermissionsComp(modperComp);
					md.setCreatedBy(entity.getCreatedBy());
					DateTime dt = new DateTime();
					DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
					DateTime dtus = dt.withZone(dtZone);
					Date dateInIndia = dtus.toLocalDateTime().toDate();
					md.setCreatedDate(dateInIndia);
					
					modulePermissionsService.save(md);
				}
				model.addAttribute("modulesassigned", "Permissions Assigned");
			}else{
				model.addAttribute("modulesassigned", " All Permissions Removed Successfully");
			}
			
			model.addAttribute("modulesassigned", "Permissions Assigned");
			model.addAttribute("crudObj", new ModulePermissionsPojo());
			model.addAttribute("module_list",moduleService.list());
			model.addAttribute("assignpermissionstomodule","assignpermissionstomodule");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	
	

	/**
	 * This method is to update the module with the new data
	 */
	@RequestMapping(value = "/updatemodule", method = RequestMethod.POST)
    public String updateModule(@ModelAttribute("crudObj") @Valid Module entity, BindingResult bindingResult, Model model,HttpServletRequest request) {
		try {
			DateTime dt = new DateTime();
			DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
			DateTime dtus = dt.withZone(dtZone);
			Date dateInIndia = dtus.toLocalDateTime().toDate();
			int status = 0;
			String conArr[] = null;
			String valArr[] = null;
			List<String> conList = null;
			
			Module mod = moduleService.getById(entity.getId());
			Module module = moduleService.getModuleByNameAndId(entity.getName(), entity.getId());
			String constraintId = entity.getConstraintId();
			String value = entity.getValue();
			
			if(constraintId != null){
				conArr = new String[constraintId.length()];
				conArr = constraintId.split(",");
				conList = Arrays.asList(conArr);
			}
			if(value != null){
				valArr = new String[value.length()];
				valArr = value.split(",");
			}
			if(conList.size() != 0){
				TreeSet<String> conSet = new TreeSet<String>(conList);
				if(conList.size() != conSet.size()){
					status = 1;
				}
			}
			if(module != null){
				status = 2;
			}
			if(status != 0){
				if(status == 1){
					model.addAttribute("moduleupdated", "You can't assign multiple values for the same control");
				}else{
					model.addAttribute("moduleupdated", "Package Name Already Exists");
				}
			}else{
				entity.setName(entity.getName().trim());
				
				entity.setModifiedDate(dateInIndia);
				moduleService.updateModule(entity);
				
				/**
				 * preparing the audit log 
				 */
				AuditLogRecord record = new AuditLogRecord();
				record.setActionDate(new java.util.Date());
				record.setActionType(AuditType.MODIFY);
				record.setActionData("Old Module Name :"+mod.getName()+" And new module name is "+entity.getName());
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
				 * assigning the permission to the license
				 */
				
				modulePermissionsService.deleteByModuleId(entity.getId());
				List<Integer> permissionsList = entity.getPermissionsId();
				if(permissionsList != null){
					Iterator<Integer> iterator = permissionsList.iterator();
					while(iterator.hasNext()){
						ModulePermissions md = new ModulePermissions();
						ModulePermissionsComp modperComp = new ModulePermissionsComp();
						modperComp.setModuleId(entity.getId());
						modperComp.setPermissionId(iterator.next());
						md.setModulePermissionsComp(modperComp);
						md.setCreatedBy(entity.getCreatedBy());
						
						md.setCreatedDate(dateInIndia);
						
						modulePermissionsService.save(md);
					}
				}
				/**
				 * assign reports to the module
				 */
				moduleReportService.deleteByModuleId(entity.getId());
				List<Integer> reportsList = entity.getReportIds();
				if(reportsList != null){
					Iterator<Integer> iterator = reportsList.iterator();
					while(iterator.hasNext()){
						ModuleReportsAssign moduleReport = new ModuleReportsAssign();
						ModuleReportComp moduleReportComp = new ModuleReportComp();
						moduleReportComp.setModuleId(entity.getId());
						moduleReportComp.setReportId(iterator.next());
						moduleReport.setModuleReportComp(moduleReportComp);
						moduleReport.setCreatedBy(entity.getCreatedBy());
						moduleReport.setCreatedDate(dateInIndia);
						moduleReportService.save(moduleReport);
						model.addAttribute("moduleassigned","Reports Assigned");
					}
				}
				
				/**
				 * assigning the constraints to the module
				 */
				if(conArr != null && valArr != null){
					moduleConstraintsService.deleteByModuleId(entity.getId());
					ModuleConstraintComp comp = new ModuleConstraintComp();
					ModuleConstraints moduleConstraints = new ModuleConstraints();
					for(int i =0;i<conArr.length;i++){
						comp.setConstraintId(Integer.parseInt(conArr[i]));
						comp.setModuleId(entity.getId());
						moduleConstraints.setModuleConstraintComp(comp);
						moduleConstraints.setValue(Integer.parseInt(valArr[i]));
						moduleConstraints.setCreatedBy(entity.getCreatedBy());
						moduleConstraints.setCreatedDate(dateInIndia);
						moduleConstraintsService.save(moduleConstraints);
					}
				}
				
				
				model.addAttribute("moduleupdated", "Package Updated Successfully");
			}
			model.addAttribute("crudObj",getEntityInstance());
			model.addAttribute("editmodule","editmodule");
			List<Module> moduleList = moduleService.getOrderBydesc();
			List<Module> modules = new ArrayList<Module>();
			if(moduleList != null){
				Iterator<Module> iterator = moduleList.iterator();
				while(iterator.hasNext()){
					Module m = iterator.next();
					Integer userId;
					if(m.getModifiedBy() != null){
						 userId = Integer.parseInt(m.getModifiedBy()); 
						 User u = userService.getById(userId);
						 m.setCreatedName(u.getUserName());
					}else{
						 userId = Integer.parseInt(m.getCreatedBy()); 
						 User u = userService.getById(userId);
						 m.setCreatedName(u.getUserName());
					}
					
					modules.add(m);
				}
			}
			model.addAttribute("permissions_list",permissionsService.list());
			model.addAttribute("roles_list",roleService.list());
			model.addAttribute("reports_list", reportsService.list());
			model.addAttribute("constraints_list",constraintsService.list());
			model.addAttribute("module_list",modules);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to assign the reports to the module
	 */
	@RequestMapping(value="/assignreportstomodule", method=RequestMethod.GET)
	public String assignReportsToModule( Model model,HttpServletRequest request,HttpServletResponse response) {
		try {
			model.addAttribute("crudObj",new ModuleReportsPojo());
			model.addAttribute("other_reports",reportsService.getOtherReports());
			model.addAttribute("module_list",moduleService.list());
			model.addAttribute("assignreportstomodule", "assignreportstomodule");
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		
		return "home";
	}
	
	/**
	 * This method is to saave the reports to the module/package
	 */

	@RequestMapping(value = "/saveassignedreports", method = RequestMethod.POST)
    public String assignReportsToRoleCompleted(@Valid ModuleReportsPojo entity,BindingResult bindingResult,Model model) {
	try {
		moduleReportService.deleteByModuleId(entity.getModuleId());
		List<Integer> reportsList = entity.getReportIds();
		if(reportsList != null){
			Iterator<Integer> iterator = reportsList.iterator();
			while(iterator.hasNext()){
				ModuleReportsAssign moduleReport = new ModuleReportsAssign();
				ModuleReportComp moduleReportComp = new ModuleReportComp();
				moduleReportComp.setModuleId(entity.getModuleId());
				moduleReportComp.setReportId(iterator.next());
				moduleReport.setModuleReportComp(moduleReportComp);
				moduleReport.setCreatedBy(entity.getCreatedBy());
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				moduleReport.setCreatedDate(dateInIndia);
				moduleReportService.save(moduleReport);
				model.addAttribute("moduleassigned","Reports Assigned");
			}
		}else{
			model.addAttribute("moduleassigned","Reports Removed Successfully");
		}
			
		
			model.addAttribute("crudObj", new ModuleReportsPojo());
			model.addAttribute("assignreportstomodule", "assignreportstomodule");
			model.addAttribute("module_list",moduleService.list());
	} catch (Exception e) {
		LOG.error(e.toString());
		e.printStackTrace();
	}
		return "home";
	}
	
	/**
	 * This method to save the module/package to the role
	 */
	@RequestMapping(value="/assignmoduletorole", method=RequestMethod.GET)
	public String assignModuleToRole( Model model,HttpServletRequest request,HttpServletResponse response) {
	   
		try {
			roleModuleController.create(model);
			model.addAttribute("assignmoduletorole","assignmoduletorole");
			model.addAttribute("crudObj",new RoleModulePojo());
			model.addAttribute("module_list",moduleService.list());
			model.addAttribute("role_list",roleService.list());
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method to get the permissions which are assigned to the module by taking the moduleid/package id as the input
	 */
	@RequestMapping(value="/getpermissionsbylicense",method=RequestMethod.GET)
	public void getAssignedPermissions(Model model,@RequestParam("licenseId") String licenseId,HttpServletRequest request,HttpServletResponse response){
		try {
			Integer licenseid = Integer.parseInt(licenseId);
			String trPerm = "";
			List<Permissions> moduleList = permissionsService.getByModuleId(licenseid);
			
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
	 * Thie methods gets the permissions list which are not assigned to the module by taking the license id as the parameter
	 */
	@RequestMapping(value="/getapermissionsbylicense",method=RequestMethod.GET)
	public void getAssignedPermissionsByLicense(Model model,@RequestParam("licenseId") String licenseId,HttpServletRequest request,HttpServletResponse response){
		try {
			Integer licenseid = Integer.parseInt(licenseId);
			String traPermissions = "";
			List<Permissions> licenseList = permissionsService.getPermissionsNotInModule(licenseid);
			
			
			for(Permissions list : licenseList){
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
	
	/**
	 * This method is to get the reports list which are assigned to the module by taking the module id/packageid
	 */
	
	@RequestMapping(value="/getmodulereportsdata",method=RequestMethod.GET)
	public void getusersdata(Model model,@RequestParam("moduleId") String moduleId,HttpServletRequest request,HttpServletResponse response)
	{
		Integer moduleid = Integer.parseInt(moduleId);
		String trReports = "";
		List<DashBoardReports> reportsList = reportsService.getReportsByModuleId(moduleid);
		for(DashBoardReports list : reportsList){
			trReports += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
		}
			try{
				PrintWriter out = response.getWriter();
				out.println(trReports);
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
	}
	
	/**
	 * This method is to get the reports list which are not assigned to the module/package by taking the moduleid as the parameter
	 */
	
	@RequestMapping(value="/getmodulereportsdata1",method=RequestMethod.GET)
	public void getusersdata1(Model model,@RequestParam("moduleId") String moduleId,HttpServletRequest request,HttpServletResponse response)
	{
		Integer moduleid = Integer.parseInt(moduleId);
		String trReports1 = "";
		List<DashBoardReports> reportsList1 = reportsService.getReportsByModuleId1(moduleid);
		for(DashBoardReports list : reportsList1){
			trReports1 += "<option label='"+list.getName()+"' value='"+list.getId()+"'>"+list.getName()+"</option>";
		}
			try{
				PrintWriter out = response.getWriter();
				out.println(trReports1);
				out.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}
	/**
	 * This method is to search the list of modules
	 */
	@RequestMapping(value="/searchmodules", method = RequestMethod.POST)
	public String searchModules(@Valid ModulePojo entity,BindingResult bindingResult,Model model,HttpServletRequest request){
		try{
			model.addAttribute("crudObj",entity);
			String name = entity.getName();
			String permId = entity.getPermissionId();
			String roleId = entity.getRoleId();
			String reportId = entity.getReportId();
			String conId = entity.getConId();
			
			final HashMap<String, String> map = new HashMap<String, String>();
			
			if(name != null && name != ""){
				map.put("name", name);
			}
			if(permId != null && permId != ""){
				map.put("permission_id", permId);
			}
			if(roleId != null && roleId != ""){
				map.put("role_id", roleId);
			}
			if(reportId != null && reportId != ""){
				map.put("report_id", reportId);
			}
			if(conId != null && conId != ""){
				map.put("constraint_id", conId);
			}
			
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("select * from module where ");
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			int i = 1;
			while(iterator.hasNext()){
				Map.Entry<String, String> pairs = (Map.Entry<String, String>)iterator.next();
				if(i>1){
					stringBuffer.append(" and ");
				}
				
				if(pairs.getKey().toString().equalsIgnoreCase("name")){
					stringBuffer.append(pairs.getKey().toString()+" like '%"+pairs.getValue().toString()+"%'");
		        }
				if(pairs.getKey().toString().equalsIgnoreCase("permission_id")){
					stringBuffer.append("id in (select module_id from module_permissions where ");
					stringBuffer.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
					stringBuffer.append(")");
				}
				if(pairs.getKey().toString().equalsIgnoreCase("role_id")){
					stringBuffer.append("id in (select module_id from role_module where ");
					stringBuffer.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
					stringBuffer.append(")");
				}
				if(pairs.getKey().toString().equalsIgnoreCase("report_id")){
					stringBuffer.append("id in (select module_id from module_reports where ");
					stringBuffer.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
					stringBuffer.append(")");
				}
				if(pairs.getKey().toString().equalsIgnoreCase("constraint_id")){
					stringBuffer.append("id in (select module_id from module_constraints where ");
					stringBuffer.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
					stringBuffer.append(")");
				}
				i++;
			}
			
			request.getSession().setAttribute("query", stringBuffer);
			
			List<ModulePojo> modules = new ArrayList<ModulePojo>();
			
			List<Module> list = moduleService.executeQuery(stringBuffer.toString());
			
			
			int count = list.size();
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			model.addAttribute("numberofpages", numberOfPages);
			model.addAttribute("currentpage", "0");
			
			Iterator<Module> itr = list.iterator();
			while(itr.hasNext()){
				String permissions = "";
				String roles = "";
				String reports = "";
				String constraints = "";
				ModulePojo pojo = new ModulePojo();
				Module m = itr.next();
				Integer countOfPermissions = modulePermissionsService.getPermissionsCountByModuleId(m.getId());
				Integer countOfReports=moduleReportService.getReportCountByModuleId(m.getId());
				Integer countOfRoles  = roleModuleService.getRoleCountByModuleId(m.getId());
				Integer countOfConstraints = moduleConstraintsService.getCountOfConstraints(m.getId());
				pojo.setName(m.getName());
				pojo.setDescription(m.getDescription());
				pojo.setNoOfPermissions(countOfPermissions);
				pojo.setNoOfReports(countOfReports);
				pojo.setNoOfRoles(countOfRoles);
				pojo.setNoOfControls(countOfConstraints);
				pojo.setCreatedDate(m.getCreatedDate());
				pojo.setModifiedDate(m.getModifiedDate());
				modules.add(pojo);
				List<ModulePermissions> permList = modulePermissionsService.findByModuleId(m.getId());
				
				List<RoleModule> roleList = roleModuleService.findByModuleId(m.getId());
				
				List<ModuleReportsAssign> reportList = moduleReportService.getByModuleId(m.getId());
				
				List<ModuleConstraints> modConList = moduleConstraintsService.findByModuleId(m.getId());
				
				if(countOfPermissions == 0){
					
					permissions = "No Permissions Available";
				}
				if(!(countOfPermissions == 0)){
					if(permList != null){
						Iterator<ModulePermissions> mpIterator = permList.iterator();
						while(mpIterator.hasNext()){
							ModulePermissions pm = mpIterator.next();
							Permissions m1 = permissionsService.getById(pm.getModulePermissionsComp().getPermissionId());
							if(permissions == "")
								permissions = m1.getName();
							else
								permissions = permissions+" "+"<br>"+m1.getName();
						}
					}
					
				}
				
				
				if(countOfRoles==0){
					roles="No Roles Available";
				}
				if(!(countOfRoles==0)){
					if(roleList != null){
						Iterator<RoleModule> iteretoRole = roleList.iterator();
						while(iteretoRole.hasNext()){
							RoleModule rm=iteretoRole.next();
							Role r1 = roleService.getById(rm.getRoleModuleComp().getRoleId());
							if(roles == "")
								roles = r1.getName();
							
							else 
								roles = roles+" "+"<br>"+r1.getName();
							}
					}
					
				}
				if(countOfConstraints == 0){
					constraints = "No Constraints Available";
				}else{
					if(modConList != null){
						Iterator<ModuleConstraints> mcIterator = modConList.iterator();
						while(mcIterator.hasNext()){
							ModuleConstraints modCon = mcIterator.next();
							Constraints con = constraintsService.getById(modCon.getModuleConstraintComp().getConstraintId());
							if(constraints == "")
								constraints = con.getName();
							
							else 
								constraints = constraints+" "+"<br>"+con.getName();
						}
					}
				}
					try{
					if(countOfReports == 0){
						reports = " No Reports Available";
						
					}
					if(!(countOfReports == 0)){
						if(reportList != null){
							Iterator<ModuleReportsAssign> iteratorReport = reportList.iterator();
							while(iteratorReport.hasNext()){
								ModuleReportsAssign mr = iteratorReport.next();
								DashBoardReports d1 = reportsService.getById(mr.getModuleReportComp().getReportId());
								if(reports == "")
									reports = d1.getName();
								else
									reports = reports +""+"<br>"+d1.getName();
							}
						}
						
						
					}
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					pojo.setRoleNames("<center><table width=70% border=1><tr><th><center>Role Names</center></th></tr><tr><td><center>"+roles+"</center></td></tr></table></center>");
					pojo.setPermissionNames("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
					pojo.setReportNames("<center><table width=70% border=1><tr><th><center>Report Names</center></th></tr><tr><td><center>"+reports+"</center></td></tr></table></center>");	
					pojo.setControleNames("<center><table width=70% border=1><tr><th><center>Control Names</center></th></tr><tr><td><center>"+constraints+"</center></td></tr></table></center>");
			}
			model.addAttribute("module_list",modules);
			model.addAttribute("viewmodule","viewmodule");
			model.addAttribute("permissions_list",permissionsService.list());
			model.addAttribute("roles_list",roleService.list());
			model.addAttribute("reports_list", reportsService.list());
			model.addAttribute("constraints_list",constraintsService.list());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * This method is to search the modules by applying the edit modules filters
	 */
	@RequestMapping(value="/searcheditmodules" , method = RequestMethod.POST)
	public String searchEditModules( @Valid ModulePojo entity,BindingResult bindingResult,Model model){
		try{
			model.addAttribute("crudObj",entity);
			String name = entity.getName();
			String permId = entity.getPermissionId();
			String roleId = entity.getRoleId();
			String reportId = entity.getReportId();
			String conId = entity.getConId();
			
			final HashMap<String, String> map = new HashMap<String, String>();
			
			if(name != null && name != ""){
				map.put("name", name);
			}
			if(permId != null && permId != ""){
				map.put("permission_id", permId);
			}
			if(roleId != null && roleId != ""){
				map.put("role_id", roleId);
			}
			if(reportId != null && reportId != ""){
				map.put("report_id", reportId);
			}
			if(conId != null && conId != ""){
				map.put("constraint_id", conId);
			}
			
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("select * from module where ");
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			int i = 1;
			while(iterator.hasNext()){
				Map.Entry<String, String> pairs = (Map.Entry<String, String>)iterator.next();
				if(i>1){
					stringBuffer.append(" and ");
				}
				
				if(pairs.getKey().toString().equalsIgnoreCase("name")){
					stringBuffer.append(pairs.getKey().toString()+" like '%"+pairs.getValue().toString()+"%'");
		        }
				if(pairs.getKey().toString().equalsIgnoreCase("permission_id")){
					stringBuffer.append("id in (select module_id from module_permissions where ");
					stringBuffer.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
					stringBuffer.append(")");
				}
				if(pairs.getKey().toString().equalsIgnoreCase("role_id")){
					stringBuffer.append("id in (select module_id from role_module where ");
					stringBuffer.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
					stringBuffer.append(")");
				}
				if(pairs.getKey().toString().equalsIgnoreCase("report_id")){
					stringBuffer.append("id in (select module_id from module_reports where ");
					stringBuffer.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
					stringBuffer.append(")");
				}
				if(pairs.getKey().toString().equalsIgnoreCase("constraint_id")){
					stringBuffer.append("id in (select module_id from module_constraints where ");
					stringBuffer.append(pairs.getKey().toString()+"="+pairs.getValue().toString());
					stringBuffer.append(")");
				}
				i++;
			}
			List<ModulePojo> modules = new ArrayList<ModulePojo>();
			
			List<Module> list = moduleService.executeQuery(stringBuffer.toString());
			Iterator<Module> itr = list.iterator();
			while(itr.hasNext()){
				String permissions = "";
				String roles = "";
				String reports = "";
				String constraints = "";
				ModulePojo pojo = new ModulePojo();
				Module m = itr.next();
				Integer countOfPermissions = modulePermissionsService.getPermissionsCountByModuleId(m.getId());
				Integer countOfReports=moduleReportService.getReportCountByModuleId(m.getId());
				Integer countOfRoles  = roleModuleService.getRoleCountByModuleId(m.getId());
				Integer countOfConstraints = moduleConstraintsService.getCountOfConstraints(m.getId());
				pojo.setId(m.getId());
				pojo.setName(m.getName());
				pojo.setDescription(m.getDescription());
				pojo.setNoOfPermissions(countOfPermissions);
				pojo.setNoOfReports(countOfReports);
				pojo.setNoOfRoles(countOfRoles);
				pojo.setNoOfControls(countOfConstraints);
				pojo.setCreatedDate(m.getCreatedDate());
				pojo.setModifiedDate(m.getModifiedDate());
				if(m.getModifiedBy() != null){
					 Integer userId = Integer.parseInt(m.getModifiedBy()); 
					 User u = userService.getById(userId);
					 pojo.setCreatedName(u.getUserName());
				}else{
					Integer userId = Integer.parseInt(m.getCreatedBy()); 
					 User u = userService.getById(userId);
					 pojo.setCreatedName(u.getUserName());
				}
				modules.add(pojo);
				List<ModulePermissions> permList = modulePermissionsService.findByModuleId(m.getId());
				
				List<RoleModule> roleList = roleModuleService.findByModuleId(m.getId());
				
				List<ModuleReportsAssign> reportList = moduleReportService.getByModuleId(m.getId());
				
				List<ModuleConstraints> modConList = moduleConstraintsService.findByModuleId(m.getId());
				
				if(countOfPermissions == 0){
					
					permissions = "No Permissions Available";
				}
				if(!(countOfPermissions == 0)){
					if(permList != null){
						Iterator<ModulePermissions> mpIterator = permList.iterator();
						while(mpIterator.hasNext()){
							ModulePermissions pm = mpIterator.next();
							Permissions m1 = permissionsService.getById(pm.getModulePermissionsComp().getPermissionId());
							if(permissions == "")
								permissions = m1.getName();
							else
								permissions = permissions+" "+"<br>"+m1.getName();
						}
					}
					
				}
				
				
				if(countOfRoles==0){
					roles="No Roles Available";
				}
				if(!(countOfRoles==0)){
					if(roleList != null){
						Iterator<RoleModule> iteretoRole = roleList.iterator();
						while(iteretoRole.hasNext()){
							RoleModule rm=iteretoRole.next();
							Role r1 = roleService.getById(rm.getRoleModuleComp().getRoleId());
							if(roles == "")
								roles = r1.getName();
							
							else 
								roles = roles+" "+"<br>"+r1.getName();
							}
					}
					
				}
				if(countOfConstraints == 0){
					constraints = "No Constraints Available";
				}else{
					if(modConList != null){
						Iterator<ModuleConstraints> mcIterator = modConList.iterator();
						while(mcIterator.hasNext()){
							ModuleConstraints modCon = mcIterator.next();
							Constraints con = constraintsService.getById(modCon.getModuleConstraintComp().getConstraintId());
							if(constraints == "")
								constraints = con.getName();
							
							else 
								constraints = constraints+" "+"<br>"+con.getName();
						}
					}
				}
					try{
					if(countOfReports == 0){
						reports = " No Reports Available";
						
					}
					if(!(countOfReports == 0)){
						if(reportList != null){
							Iterator<ModuleReportsAssign> iteratorReport = reportList.iterator();
							while(iteratorReport.hasNext()){
								ModuleReportsAssign mr = iteratorReport.next();
								DashBoardReports d1 = reportsService.getById(mr.getModuleReportComp().getReportId());
								if(reports == "")
									reports = d1.getName();
								else
									reports = reports +""+"<br>"+d1.getName();
							}
						}
						
						
					}
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					pojo.setRoleNames("<center><table width=70% border=1><tr><th><center>Role Names</center></th></tr><tr><td><center>"+roles+"</center></td></tr></table></center>");
					pojo.setPermissionNames("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
					pojo.setReportNames("<center><table width=70% border=1><tr><th><center>Report Names</center></th></tr><tr><td><center>"+reports+"</center></td></tr></table></center>");	
					pojo.setControleNames("<center><table width=70% border=1><tr><th><center>Control Names</center></th></tr><tr><td><center>"+constraints+"</center></td></tr></table></center>");
			}
			model.addAttribute("module_list",modules);
			model.addAttribute("editmodule","editmodule");
			model.addAttribute("permissions_list",permissionsService.list());
			model.addAttribute("roles_list",roleService.list());
			model.addAttribute("reports_list", reportsService.list());
			model.addAttribute("constraints_list",constraintsService.list());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "home";
		
	}
	
	/**
	 * This method is to get the list of modules/packages based on the page
	 */
	@RequestMapping(value="/viewpaginationmodule",method = RequestMethod.GET)
	public String getPermissionsPagination(Model model){
		int count = moduleService.getModuleCount();
		int numberOfPages = count/10;
		if(count % 10 != 0){
			numberOfPages = numberOfPages+1;
		}
		model.addAttribute("numberofpages", numberOfPages);
		model.addAttribute("currentpage", "0");
		List<Module> modList = moduleService.getByPage((long)1);
		model.addAttribute("module_list", modList);
		model.addAttribute("viewmodule", "viewmodule");
		return "home";
	}
	/**
	 * This method is to get the list of modules/packages by applying the pagination
	 */
	@RequestMapping(value="viewpagemodule/{page}",method = RequestMethod.GET)
	public String viewPagePermissions(@PathVariable Integer page,Model model,HttpServletRequest request){
		/*int totalRecords = moduleService.getModuleCount();
		System.out.println("Total Record : "+totalRecords);
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
		
		List<Module> modList = moduleService.getModulesByPageAndCount(page, limitsPerPage);
		System.out.println(modList.size());
		model.addAttribute("module_list", modList);
		model.addAttribute("viewmodule", "viewmodule");
		
		*/
		
		try {
			/*int count = moduleService.getModuleCount();
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			model.addAttribute("numberofpages", numberOfPages);
			model.addAttribute("currentpage", "0");
			
			model.addAttribute("crudObj",new ModulePojo());
			model.addAttribute("viewmodule","viewmodule");*/
			
			/*int count = moduleService.getModuleCount();
			int numberOfPages = count/10;
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			int limitsPerPage = 10;
			if(page == numberOfPages){
				limitsPerPage = count % 10;
				
			}*/
			/*int totalRecords = moduleService.getModuleCount();
			int limitsPerPage = 10;
			int noOfPages = totalRecords/10;
			if(totalRecords/10 != 0){
				noOfPages = noOfPages+1;
			}
			if(page == noOfPages-1){
				limitsPerPage = totalRecords % 10;
				
			}*/
			
			int count = 0;
			int numberOfPages = 0;
			int limitsPerPage = 10;
			List<Module> list = null;
			
			StringBuffer query = (StringBuffer)request.getSession().getAttribute("query");
			if(query == null || query.equals("")){
				count = moduleService.getModuleCount();
				list  = moduleService.getModulesByPageAndCount(page, limitsPerPage);
			}
			else{
				count = moduleService.executeQueryCount(query.toString());
			    list  = moduleService.executeQueryPerPage(query.toString(), Integer.valueOf(page), limitsPerPage);
			}
			
			numberOfPages = count/10;
			list  = moduleService.getModulesByPageAndCount(page, limitsPerPage);
			if(count % 10 != 0){
				numberOfPages = numberOfPages+1;
			}
			if(page == numberOfPages){
				limitsPerPage = count % 10;
			}
			
			model.addAttribute("numberofpages", numberOfPages);
			model.addAttribute("currentpage", page);
			model.addAttribute("viewmodule", "viewmodule");
			List<ModulePojo> modules = new ArrayList<ModulePojo>();
			//List<Module> list = moduleService.getByPage((long)1);
			//list  = moduleService.getModulesByPageAndCount(page, limitsPerPage);
			Iterator<Module> itr = list.iterator();
			while(itr.hasNext()){
				String permissions = "";
				String roles = "";
				String reports = "";
				String constraints = "";
				ModulePojo pojo = new ModulePojo();
				Module m = itr.next();
				Integer countOfPermissions = modulePermissionsService.getPermissionsCountByModuleId(m.getId());
				Integer countOfReports=moduleReportService.getReportCountByModuleId(m.getId());
				Integer countOfRoles  = roleModuleService.getRoleCountByModuleId(m.getId());
				Integer countOfConstraints = moduleConstraintsService.getCountOfConstraints(m.getId());
				pojo.setName(m.getName());
				pojo.setDescription(m.getDescription());
				pojo.setNoOfPermissions(countOfPermissions);
				pojo.setNoOfReports(countOfReports);
				pojo.setNoOfRoles(countOfRoles);
				pojo.setNoOfControls(countOfConstraints);
				pojo.setCreatedDate(m.getCreatedDate());
				pojo.setModifiedDate(m.getModifiedDate());
				modules.add(pojo);
				List<ModulePermissions> permList = modulePermissionsService.findByModuleId(m.getId());
				
				List<RoleModule> roleList = roleModuleService.findByModuleId(m.getId());
				
				List<ModuleReportsAssign> reportList = moduleReportService.getByModuleId(m.getId());
				
				List<ModuleConstraints> modConList = moduleConstraintsService.findByModuleId(m.getId());
				
				if(countOfPermissions == 0){
					
					permissions = "No Permissions Available";
				}
				if(!(countOfPermissions == 0)){
					if(permList != null){
						Iterator<ModulePermissions> iterator = permList.iterator();
						while(iterator.hasNext()){
							ModulePermissions pm = iterator.next();
							Permissions m1 = permissionsService.getById(pm.getModulePermissionsComp().getPermissionId());
							if(permissions == "")
								permissions = m1.getName();
							else
								permissions = permissions+" "+"<br>"+m1.getName();
						}
					}
					
				}
				
				
				if(countOfRoles==0){
					roles="No Roles Available";
				}
				if(!(countOfRoles==0)){
					if(roleList != null){
						Iterator<RoleModule> iteretoRole = roleList.iterator();
						while(iteretoRole.hasNext()){
							RoleModule rm=iteretoRole.next();
							Role r1 = roleService.getById(rm.getRoleModuleComp().getRoleId());
							if(roles == "")
								roles = r1.getName();
							
							else 
								roles = roles+" "+"<br>"+r1.getName();
							}
					}
					
				}
				if(countOfConstraints == 0){
					constraints = "No Constraints Available";
				}else{
					if(modConList != null){
						Iterator<ModuleConstraints> iterator = modConList.iterator();
						while(iterator.hasNext()){
							ModuleConstraints modCon = iterator.next();
							Constraints con = constraintsService.getById(modCon.getModuleConstraintComp().getConstraintId());
							if(constraints == "")
								constraints = con.getName();
							
							else 
								constraints = constraints+" "+"<br>"+con.getName();
						}
					}
				}
					try{
					if(countOfReports == 0){
						reports = " No Reports Available";
						
					}
					if(!(countOfReports == 0)){
						if(reportList != null){
							Iterator<ModuleReportsAssign> iteratorReport = reportList.iterator();
							while(iteratorReport.hasNext()){
								ModuleReportsAssign mr = iteratorReport.next();
								DashBoardReports d1 = reportsService.getById(mr.getModuleReportComp().getReportId());
								if(reports == "")
									reports = d1.getName();
								else
									reports = reports +""+"<br>"+d1.getName();
							}
						}
						
						
					}
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					pojo.setRoleNames("<center><table width=70% border=1><tr><th><center>Role Names</center></th></tr><tr><td><center>"+roles+"</center></td></tr></table></center>");
					pojo.setPermissionNames("<center><table width=70% border=1><tr><th><center>Permission Names</center></th></tr><tr><td><center>"+permissions+"</center></td></tr></table></center>");
					pojo.setReportNames("<center><table width=70% border=1><tr><th><center>Report Names</center></th></tr><tr><td><center>"+reports+"</center></td></tr></table></center>");	
					pojo.setControleNames("<center><table width=70% border=1><tr><th><center>Control Names</center></th></tr><tr><td><center>"+constraints+"</center></td></tr></table></center>");
			}
			model.addAttribute("permissions_list",permissionsService.list());
			model.addAttribute("roles_list",roleService.list());
			model.addAttribute("reports_list", reportsService.list());
			model.addAttribute("constraints_list",constraintsService.list());
			model.addAttribute("module_list",modules);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
		}
	
		return "home";
	}
	
	
	@Override
	public QTAdminService<Module> getTService() {
		return moduleService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}
}
