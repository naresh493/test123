/**
' * This class represents the spring controller which handles all the controls related operations
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import com.infotree.qliktest.admin.entity.referencedata.Constraints;
import com.infotree.qliktest.admin.entity.referencedata.Module;
import com.infotree.qliktest.admin.entity.referencedata.ModuleConstraints;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.ConstraintsService;
import com.infotree.qliktest.admin.service.referencedata.ModuleConstraintsService;
import com.infotree.qliktest.admin.service.referencedata.ModuleService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/constraints")
public class ConstraintsController extends AbstractQTAdminController<Constraints> {
	private static final Logger log = LoggerFactory.getLogger(ConfigurationController.class);
	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private ModuleConstraintsService moduleConstraintsService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private ConstraintsService constraintsService;
	
	/**
	 * This method returns the UI to create the control
	 */
	@RequestMapping(value="/createconstraint",method = RequestMethod.GET)
	public String createConstraint(Model model){
		try {
			model.addAttribute("createconstraint", "createconstraint");
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method saves the new constraint
	 */
	@RequestMapping(value="/save",method = RequestMethod.POST)
	public String saveConstraint(@ModelAttribute("crudObj") @Valid Constraints entity,BindingResult bindingResult,Model model){
		try {
			model.addAttribute("createconstraint", "createconstraint");
			Constraints con = constraintsService.findByName(entity.getName());
			if(con != null){
				model.addAttribute("constraintcreated", "<font color='red'>Control Name Already Exists</font>");
				
			}else{
				entity.setName(entity.getName().trim());
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				entity.setCreatedDate(dateInIndia);
				constraintsService.save(entity);
				model.addAttribute("constraintcreated", "Control Created Successfully");
			}
			model.addAttribute("crudObj",getEntityInstance());
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is to show the list of controls to the user
	 */
	@RequestMapping(value="/viewconstraints",method = RequestMethod.GET)
	public String viewConstraints(Model model){
		try {
			model.addAttribute("viewconstraints", "viewconstraints");
			List<Constraints> list = constraintsService.getByOrder();
			List<Constraints> conList = new ArrayList<Constraints>();
			if(list != null){
				Iterator<Constraints> iterator = list.iterator();
				while(iterator.hasNext()){
					Constraints con = iterator.next();
					String packageNames = "";
					Integer noOfPackages = moduleConstraintsService.getCountOfPackages(con.getId());
					con.setNumberOfPackages(noOfPackages);
					if(noOfPackages == 0) {
						packageNames = "No Packages Available";
					} else {
						List<ModuleConstraints> modConList = moduleConstraintsService.findByConstraintId(con.getId());
						if(modConList != null){
							Iterator<ModuleConstraints> itr = modConList.iterator();
							while(itr.hasNext()){
								ModuleConstraints md = itr.next();
								Module m = moduleService.getById(md.getModuleConstraintComp().getModuleId());
								if(packageNames == "")                                                                                                                                                                                                                                                               {
									packageNames = m.getName();
								}else{
									packageNames = packageNames+" "+"<br>"+m.getName(); 
								}
							}
						}
					}
					con.setPackageNames("<center><table width=70% border=1><tr><th><center>Package Names</center></th></tr><tr><td><center>"+packageNames+"</center></td></tr></table></center>");
					conList.add(con);
				}
			}
			model.addAttribute("module_list", moduleService.list());
			model.addAttribute("constraint_list", conList);
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the UI to edit the control with the list of controls
	 */
	@RequestMapping(value="/editconstraint",method = RequestMethod.GET)
	public String editConstraint(Model model){
		try {
			model.addAttribute("editconstraint", "editconstraint");
			List<Constraints> list = constraintsService.getByOrder();
			List<Constraints> conList = new ArrayList<Constraints>();
			if(list != null) {
				Iterator<Constraints> iterator = list.iterator();
				while(iterator.hasNext()){
					Constraints con = iterator.next();
					if(con.getModifiedBy() != null){
						Integer userId = Integer.parseInt(con.getModifiedBy()); 
						User u = userService.getById(userId);
						con.setCreatedName(u.getUserName());
					}else{
						Integer userId = Integer.parseInt(con.getCreatedBy());
						User u = userService.getById(userId);
						con.setCreatedName(u.getUserName());
					}
					conList.add(con);
				}
			}
			model.addAttribute("module_list", moduleService.list());
			model.addAttribute("constraint_list", conList);
		} catch (NumberFormatException e) {
			log.error(e.toString());
			e.printStackTrace();
		}	
		return "home";
	}
	/**
	 * This method returns the UI to edit the conrol which was selected by the user in the editable mode
	 */
	@RequestMapping(value="/edit/{constraintId}/update", method=RequestMethod.GET)
	public String updatePermission(@PathVariable Integer constraintId, Model model) {
			try {
				Constraints entityToBeUpdated = constraintsService.getById(constraintId);
				model.addAttribute("crudObj", entityToBeUpdated);
				model.addAttribute("idToBeUpdated", entityToBeUpdated.getId());
				model.addAttribute("updateconstraint", "updateconstraint");
			} catch (Exception e) {
				log.error(e.toString());
				e.printStackTrace();
			}
		return "home";
	}
	/**
	 * This method is to update the control
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public String updateConstraint(@ModelAttribute("crudObj") @Valid Constraints entity,BindingResult bindingResult,Model model){
		try {
			Constraints constr = constraintsService.findByNameAndNotId(entity.getName(), entity.getId());
			if(constr != null){
				model.addAttribute("constraintupdate", "<font color='red'>Name Already Exists</font>");
			}else{
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				entity.setModifiedDate(dateInIndia);
				constraintsService.updateConstraint(entity);
				model.addAttribute("constraintupdate", "Control Updated Successfully");
			}
			List<Constraints> list = constraintsService.list();
			List<Constraints> conList = new ArrayList<Constraints>();
			if(list != null){
				Iterator<Constraints> iterator = list.iterator();
				while(iterator.hasNext()){
					Constraints con = iterator.next();
					if(con.getModifiedBy() != null){
						Integer userId = Integer.parseInt(con.getModifiedBy()); 
						User u = userService.getById(userId);
						con.setCreatedName(u.getUserName());
					}else{
						Integer userId = Integer.parseInt(con.getCreatedBy());
						User u = userService.getById(userId);
						con.setCreatedName(u.getUserName());
					}
					conList.add(con);
				}
			}
			model.addAttribute("module_list", moduleService.list());
			model.addAttribute("constraint_list", conList);
			model.addAttribute("editconstraint", "editconstraint");
		} catch (NumberFormatException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method returns the search options for the view constraints based on constraint name and package name
	 */
	@RequestMapping(value="/searchconstraints", method = RequestMethod.POST)
	public String searchConstraints(@ModelAttribute("crudObj") @Valid Constraints entity,BindingResult bindingResult,Model model){
		try {
			String constraintName = entity.getName();
			Integer modId = entity.getModuleId();
			List<Constraints> list = null;
			List<Constraints> conList = new ArrayList<Constraints>();
			if(constraintName != null && modId != null){
				list = constraintsService.findByModIdAndConstraintNamePattern(modId, constraintName);
			}else if(constraintName != null && modId == null){
				list = constraintsService.findByConstraintNamePattern(constraintName);
			}else if(constraintName == null && modId != null){
				list = constraintsService.findByModuleId(modId);
			}else{
				list = constraintsService.getByOrder();
			}
			if(list != null){
				Iterator<Constraints> iterator = list.iterator();
				while(iterator.hasNext()){
					Constraints con = iterator.next();
					String packageNames = "";
					Integer noOfPackages = moduleConstraintsService.getCountOfPackages(con.getId());
					con.setNumberOfPackages(noOfPackages);
					if(noOfPackages == 0){
						packageNames = "No Packages Available";
					}else{
						List<ModuleConstraints> modConList = moduleConstraintsService.findByConstraintId(con.getId());
						if(modConList != null){
							Iterator<ModuleConstraints> itr = modConList.iterator();
							while(itr.hasNext()){
								ModuleConstraints md = itr.next();
								Module m = moduleService.getById(md.getModuleConstraintComp().getModuleId());
								if(packageNames == ""){
									packageNames = m.getName();
								}else{
									packageNames = packageNames+" "+"<br>"+m.getName(); 
								}
							}
						}
					}
					con.setPackageNames("<center><table width=70% border=1><tr><th><center>Package Names</center></th></tr><tr><td><center>"+packageNames+"</center></td></tr></table></center>");
					conList.add(con);
				}
			}
			model.addAttribute("constraint_list", conList);
			model.addAttribute("module_list", moduleService.list());
			model.addAttribute("viewconstraints", "viewconstraints");
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	/**
	 * This method returns the search options for the edit controls
	 */
	@RequestMapping(value="/searcheditconstraints", method = RequestMethod.POST)
	public String searchEditConstraints(@ModelAttribute("crudObj") @Valid Constraints entity,BindingResult bindingResult,Model model){
		try {
			String constraintName = entity.getName();
			Integer modId = entity.getModuleId();
			List<Constraints> list = null;
			List<Constraints> conList = new ArrayList<Constraints>();
			if(constraintName != null && modId != null){
				list = constraintsService.findByModIdAndConstraintNamePattern(modId, constraintName);
			}else if(constraintName != null && modId == null){
				list = constraintsService.findByConstraintNamePattern(constraintName);
			}else if(constraintName == null && modId != null){
				list = constraintsService.findByModuleId(modId);
			}else{
				list = constraintsService.getByOrder();
			}
			if(list != null){
				Iterator<Constraints> iterator = list.iterator();
				while(iterator.hasNext()){
					Constraints con = iterator.next();
					if(con.getModifiedBy() != null){
						Integer userId = Integer.parseInt(con.getModifiedBy()); 
						User u = userService.getById(userId);
						con.setCreatedName(u.getUserName());
					}else{
						Integer userId = Integer.parseInt(con.getCreatedBy());
						User u = userService.getById(userId);
						con.setCreatedName(u.getUserName());
					}
					conList.add(con);
				}
			}
			model.addAttribute("constraint_list", conList);
			model.addAttribute("module_list", moduleService.list());
			model.addAttribute("editconstraint", "editconstraint");
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return "home";
	}
	
	@Override
	public QTAdminService<Constraints> getTService() {
		return constraintsService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}
}
