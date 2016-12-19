package com.infotree.qliktest.admin.security.authentication;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.infotree.qliktest.admin.entity.referencedata.User;


/**
 * Implementation of Spring's UserDetails interface that wraps a User entity from the database.
 * As security in this system will be more fine-grained than role-based-access-control, this class
 * will always return an empty collection of roles.
 */

//TODO do we need to extend UserDetails
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private final User user;
	
	private String allowedFunctions;
	
	/**
	 * Constructor. Must provide an instance of User for this object to wrap.
	 */
	public UserDetailsImpl(User user) {
		if(user == null) {
			throw new IllegalArgumentException("a valid User entity from the database must be supplied to UserDetailsImpl");
		}
		this.user = user;
	}

	/**
	 * The User entity from the database that this object corresponds to. Cannot return <code>null</code>.
	 */
	public User getUser() {
		return user;
	}

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     */
	public Collection<GrantedAuthority> getAuthorities() {
		return AuthorityUtils.NO_AUTHORITIES;
	}

    /**
     * Returns the password used to authenticate the user. Cannot return <code>null</code>.
     */
	public String getPassword() {
		return user.getPassword();
	}

    /**
     * Returns the user name used to authenticate the user. Cannot return <code>null</code>.
     */
	public String getUsername() {
		return String.valueOf(user.getId());
	}

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     */
	public boolean isAccountNonExpired() {
		return true;
	}

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     */
	public boolean isAccountNonLocked() {
		return true;
	}

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
     */
	public boolean isCredentialsNonExpired() {
		return true;
	}

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     */
	public boolean isEnabled() {
		return true;
	}
	
	//TODO the allowedFunctions need to be set when the user is Authenticated for the first time.
	public void setAllowedFunctions(String allowedFunctions) {
		this.allowedFunctions = allowedFunctions;
	}
	
	public String getAllowedFunctions() {
		return allowedFunctions;
	}

}
