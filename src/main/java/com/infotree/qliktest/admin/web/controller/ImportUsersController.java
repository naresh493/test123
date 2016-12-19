package com.infotree.qliktest.admin.web.controller;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.BadLdapGrammarException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.infotree.qliktest.admin.entity.referencedata.LdapConfig;
import com.infotree.qliktest.admin.entity.referencedata.Person;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserTenant;
import com.infotree.qliktest.admin.service.referencedata.LdapConfigService;
import com.infotree.qliktest.admin.service.referencedata.UserProfileService;
import com.infotree.qliktest.admin.service.referencedata.UserTenantService;
import com.infotree.qliktest.admin.web.validator.DoNothingValidator;

@Controller
@RequestMapping("/import")
public class ImportUsersController {

	private static final Logger LOG = LoggerFactory.getLogger(ImportUsersController.class);
	
	@Autowired
	private DoNothingValidator validator;
	
	@Autowired
	private UserTenantService userTenantService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private LdapConfigService ldapConfigService;
	/**
	 * This method returns the UI to select the configuration to import the users from the ldap
	 */
	@RequestMapping(value="/importusersfromldap", method = RequestMethod.GET)
	public String importUsers(Model model, HttpServletRequest request) {
		model.addAttribute("crudObj", new LdapConfig());
		model.addAttribute("importusersfromldap", "importusersfromldap");
		UserTenant userTenant = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
		model.addAttribute("ldapconfig_list", ldapConfigService.findByTenantId(userTenant.getUserTenantComp().getTenantId()));
		return "home";
	}
	/**
	 * This method is to map the ldap users to the attribute mapper
	 */
	private class PersonAttributesMapper implements AttributesMapper{
		public Person mapFromAttributes(Attributes attrs) throws NamingException {
			Person person = null;
			try {
				if(((String)attrs.get("cn").get() != null) && !((String)attrs.get("cn").get()).startsWith("SD")){
					person = new Person();
					person.setFullName((attrs.get("cn") == null) ? "" : (String)attrs.get("cn").get());
					String firstName = null;
					if(attrs.get("cn").get().toString().indexOf(" ") != -1)
						firstName = (attrs.get("cn") == null) ? "" : ((String)attrs.get("cn").get()).substring(0,((String)attrs.get("cn").get()).indexOf(" ")) ;
					else
						firstName = (attrs.get("cn") == null) ? "" : (attrs.get("cn").get()).toString() ;
					person.setFirstName(firstName);
					String surname = (attrs.get("sn") == null) ? "" : (String)attrs.get("sn").get();
					person.setSurName((surname == null || surname.isEmpty()) ? firstName : surname );
					person.setUserName(firstName+surname);
					String email = (attrs.get("mail") == null) ? "" : (String)attrs.get("mail").get();
					if(email.equals("") && attrs.get("userPrincipalName") == null){
						return null;
					}
					person.seteMail((email == null || email.isEmpty()) ? (String)attrs.get("userPrincipalName").get() : email );
					//person.setLandline("0");
					String mobile = (attrs.get("mobile") == null) ? "" : (String)attrs.get("mobile").get();
					String telephoneNumber = (attrs.get("telephoneNumber") == null) ? "" : (String)attrs.get("telephoneNumber").get();
					if(mobile != null && !mobile.isEmpty()){
						person.setMobile(mobile);
					}else if(telephoneNumber != null && !telephoneNumber.isEmpty()){
						person.setMobile(telephoneNumber);
					}else if((mobile == null || mobile.isEmpty()) && (telephoneNumber == null || telephoneNumber.isEmpty())){
						person.setMobile("");
					}
					String description = (attrs.get("title") == null) ? "" : (String)attrs.get("title").get();
					person.setDescription((description == null || description.isEmpty()) ? "" : description );
				}
			} catch (Exception e) {
				//e.printStackTrace();
				LOG.error(e.toString());
			}
			return person;
		}
	}
	/**
	 * This method is to fetch the all ldap users from ldap server to the local system
	 */
	@RequestMapping(value = "/getallldapusers", method = RequestMethod.POST)
	public String importUsersFromConfig(@ModelAttribute("crudObj") @Valid LdapConfig entity, BindingResult bindingResult, Model model,HttpServletRequest request) {
		try {
			model.addAttribute("crudObj", new LdapConfig());
			LOG.info("configuration value to import users : "+entity.getId());
			if( entity.getId() == null ){
				model.addAttribute("userimportedfromldap","Please select configuration to import users");
				model.addAttribute("importusersfromldap", "importusersfromldap");
				UserTenant userTenant = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
				model.addAttribute("ldapconfig_list", ldapConfigService.findByTenantId(userTenant.getUserTenantComp().getTenantId()));
				return "home";
			}
			Integer userId = (Integer)request.getSession().getAttribute("userid");
			String tenantName = userProfileService.getTenantName(userId);
			//entity.setId(6);
			//entity.setId(userId);
			//System.out.println("Ldap Id : "+entity.getId());
			LdapConfig config = ldapConfigService.getById(entity.getId());
			//System.out.println("Base Name : "+config.getBase());
			//System.out.println("Port ID: "+config.getPort());
			String ldapUrl = "ldap://"+config.getServerip()+":"+config.getPort();
			LdapContextSource ldapContext = new LdapContextSource();
			ldapContext.setUrl(ldapUrl);
			ldapContext.setBase(config.getBase());
			ldapContext.setUserDn(config.getUsername());
			ldapContext.setPassword(config.getPassword());
			LdapTemplate template = new LdapTemplate(ldapContext);
			ldapContext.afterPropertiesSet();
			@SuppressWarnings("unchecked")
			List<Person> personList = template.search("", "objectClass=person", new PersonAttributesMapper());
			List<User> list = userProfileService.insertLdapUsers(personList, userId, tenantName);
			model.addAttribute("userimportedfromldap", list.size()+" Users Imported from ldap");
		} catch(AuthenticationException e) {
			model.addAttribute("userimportedfromldap", "Invalid Credentials of Ldap Server");
			//e.printStackTrace();
			LOG.error(e.toString());
		}
		catch(BadLdapGrammarException e) {
			model.addAttribute("userimportedfromldap", "Invalid Credentials of Ldap Server");
			//e.printStackTrace();
			LOG.error(e.toString());
		}catch(Exception e) {
			//e.printStackTrace();
			model.addAttribute("userimportedfromldap", "An Error occured during importing users from ldap");
			LOG.error(e.toString());
		}
		model.addAttribute("importusersfromldap", "importusersfromldap");
		UserTenant userTenant = userTenantService.findByUserId((Integer)request.getSession().getAttribute("userid"));
		model.addAttribute("ldapconfig_list", ldapConfigService.findByTenantId(userTenant.getUserTenantComp().getTenantId()));
		return "home";
	}
}
