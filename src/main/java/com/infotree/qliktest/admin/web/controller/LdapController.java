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

import com.infotree.qliktest.admin.entity.referencedata.LdapConfig;
import com.infotree.qliktest.admin.entity.referencedata.Tenant;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.service.QTAdminService;
import com.infotree.qliktest.admin.service.referencedata.LdapConfigService;
import com.infotree.qliktest.admin.service.referencedata.TenantService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/ldapconfiguration")
public class LdapController extends AbstractQTAdminController<LdapConfig> {

	private static final Logger LOG = LoggerFactory.getLogger(LdapController.class);
	
	@Autowired
	private LdapConfigService ldapConfigService;
	
	@Autowired
	private TenantService tenantService;
	
	@Autowired
	private UserProfileService userService;
	
	@Autowired
	private DoNothingValidator validator;
	
	/**
	 * This method returns the UI to create the ldap configuration
	 */
	@RequestMapping(value="createldapconfig", method = RequestMethod.GET)
	public String createLdapConfig(Model model) {
		try {
			model.addAttribute("createldapconfiguration", "createldapconfiguration");
			model.addAttribute("tenant_list", tenantService.list());
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return "home";
	}
	/**
	 * This method saves the new ldap configuration
	 */
	@RequestMapping(value="/saveldap",method = RequestMethod.POST)
	public String saveldapConfig(@ModelAttribute("crudObj") @Valid LdapConfig entity,
			BindingResult bindingResult,Model model) {
		try {
			LdapConfig config = ldapConfigService.findByName(entity.getName());
			if(config != null) {
				model.addAttribute("ldapconfigurationcreated", "<font color='red'>Name already exists</font>");
			}else{
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				entity.setCreatedDate(dateInIndia);
				ldapConfigService.save(entity);
				model.addAttribute("ldapconfigurationcreated", "Configuration Created Successfully");
			}
			model.addAttribute("crudObj", getEntityInstance());
			model.addAttribute("createldapconfiguration", "createldapconfiguration");
			model.addAttribute("tenant_list", tenantService.list());
			
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return "home";
	}
	/**
	 * This method to get the list of ldapconfig to show them to the user
	 */
	@RequestMapping(value = "/viewldapconfig", method = RequestMethod.GET)
	public String viewLdapConfig(Model model) {
		List<LdapConfig> configList = ldapConfigService.list();
		List<LdapConfig> ldapList = new ArrayList<LdapConfig>();
		if(configList != null) {
			Iterator<LdapConfig> iterator = configList.iterator();
			while(iterator.hasNext()) {
				LdapConfig config = iterator.next();
				Tenant tenant = tenantService.getById(config.getTenantId());
				config.setTenantName(tenant.getName());
				ldapList.add(config);
			}
		}
		model.addAttribute("ldapconfiglist", ldapList);
		model.addAttribute("viewldapconfiglist", "viewldapconfiglist");
		return "home";
	}
	/**
	 * This method returns the UI to edit the ldap configuration
	 */
	@RequestMapping(value = "/editldapconfig", method = RequestMethod.GET)
	public String editLdapConfig(Model model) {
		try {
			List<LdapConfig> ldapList = ldapConfigService.list();
			List<LdapConfig> list = new ArrayList<LdapConfig>();
			if(ldapList != null) {
				Iterator<LdapConfig> iterator = ldapList.iterator();
				while(iterator.hasNext()) {
					LdapConfig config = iterator.next();
					if(config.getModifiedBy() != null){
						Integer id = Integer.parseInt(config.getModifiedBy());
						User u = userService.getById(id);
						config.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(config.getCreatedBy());
						User u = userService.getById(id);
						config.setCreatedName(u.getUserName());
					}
					list.add(config);
				}
			}
		model.addAttribute("editldapconfig", "editldapconfig");
		model.addAttribute("ldapconfig_list", list);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	/**
	 * This method returns the UI to update the ldap configuration which was selected by the user in the editable mode
	 */
	@RequestMapping(value = "/ldapconfig/{id}/updateldap", method = RequestMethod.GET)
	public String updateLdapConfig(@PathVariable Integer id,Model model) {
		try {
			LdapConfig entityToBeUpdated = ldapConfigService.getById(id);
			model.addAttribute("crudObj", entityToBeUpdated);
			model.addAttribute("idToBeUpdated", entityToBeUpdated.getId());
			model.addAttribute("updateldapconfig", "updateldapconfig");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method updates the ldap configuraion 
	 */
	@RequestMapping(value = "/updateLdap", method = RequestMethod.POST)
	public String updateLdap(@ModelAttribute("crudObj") @Valid LdapConfig entity,BindingResult bindingResult,Model model) {
		try {
			
			List<LdapConfig> list = new ArrayList<LdapConfig>();
			LdapConfig config = ldapConfigService.findByNameAndIdNot(entity.getId(), entity.getName());
			if(config != null) {
				model.addAttribute("configurationupdated", "<font color='red'>Name Already Exists. Choose another name</font>");
			} else {
				DateTime dt = new DateTime();
				DateTimeZone dtZone = DateTimeZone.forID("Asia/Kolkata");
				DateTime dtus = dt.withZone(dtZone);
				Date dateInIndia = dtus.toLocalDateTime().toDate();
				entity.setModifiedDate(dateInIndia);
				ldapConfigService.updateLdapConfig(entity);
				model.addAttribute("configurationupdated","<font color='green'>Configuration updated successfully</font>");
			}
			List<LdapConfig> ldapList = ldapConfigService.list();
			if(ldapList != null) {
				Iterator<LdapConfig> iterator = ldapList.iterator();
				while(iterator.hasNext()) {
					LdapConfig ldap = iterator.next();
					if(ldap.getModifiedBy() != null){
						Integer id = Integer.parseInt(ldap.getModifiedBy());
						User u = userService.getById(id);
						ldap.setCreatedName(u.getUserName());
					}else{
						Integer id = Integer.parseInt(ldap.getCreatedBy());
						User u = userService.getById(id);
						ldap.setCreatedName(u.getUserName());
					}
					list.add(ldap);
				}
			}
			model.addAttribute("editldapconfig", "editldapconfig");
			model.addAttribute("ldapconfig_list", list);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
	@Override
	public QTAdminService<LdapConfig> getTService() {
		return ldapConfigService;
	}

	@Override
	protected Validator getValidator() {
		return validator;
	}

}
