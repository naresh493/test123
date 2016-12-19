package com.infotree.qliktest.admin.security.authentication;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.infotree.qliktest.admin.dao.referencedata.UserDao;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.web.controller.PasswordHashing;


/**
 * Custom implementation of AuthenticationUserDetailsService that provides a custom implementation of UserDetails
 */

@SuppressWarnings("rawtypes")
public class AuthenticationUserDetailsServiceImpl implements AuthenticationUserDetailsService {
    
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationUserDetailsServiceImpl.class);

	private UserDao userDao;
	
	//private GenericAccessGrantService accessGrantService;

	/**
	 * Looks up a user in the database by window user name specified in an authentication token
	 * and returns a custom implementation of the Spring UserDetails interface.
	 */
	public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {

		String username = token.getPrincipal().toString();
		String password = token.getCredentials().toString();
		
		String hashedPwd = PasswordHashing.cryptWithMD5(password);
		
		User user = null;
		try {
			
			user = userDao.findByLoginCredentials(username, hashedPwd);
		} catch(NoResultException err) {
			throw new UsernameNotFoundException(username+" is not a user of this system.");
		} catch(Exception err) {
			throw new AuthenticationServiceException("problem connecting to database", err);
		}
		
		if(user == null) {
			throw new UsernameNotFoundException(username+" is not a user of this system.");
		}
		
		if(user.isDisabled()) {
			throw new DisabledException(username+" user account has been disabled in this system.");
		}

		LOG.info(username+" successfully logged into the system.");

		UserDetailsImpl userDetailsImpl= new UserDetailsImpl(user);
		userDetailsImpl.setAllowedFunctions(loadAllowedPermissionsForUser(user));
		return userDetailsImpl;
	}
	
	private String loadAllowedPermissionsForUser(User user){
		
		String permissions = "";
		List<String> permissionsList = new ArrayList<String>();
		try {
			//user = userDao.getById(username);
			permissionsList = userDao.getPermissions(user.getFirstName());
			if(permissionsList != null && permissionsList.size() > 0){
				for (String permision: permissionsList) {
					permissions += permision + ",";
				}
				permissions = permissions.substring(0,permissions.length()-1);
			}
			
		} catch(Exception err) {
			throw new AuthenticationServiceException("problem connecting to database", err);
		}
		return permissions;
	}
	
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
