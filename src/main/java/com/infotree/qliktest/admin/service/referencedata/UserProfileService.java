package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.Person;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.service.QTAdminService;


public interface UserProfileService extends QTAdminService<User>{

	/**
	 * Get a user profile from the database that matches the given name.
	 * @param name - the name of the user profile
	 */
	User findByName(String name);

	/**
	 * Sets the collection of system functions that users asigned to the given user profile have access to.
	 * @return the updated instance of UserProfile that has been persisted to the database
	 */
	//UserProfile updatePermissions(Integer userProfileId, Set<Function> permissions);
	
	/**
	 * get active users(is_disabled=0) from users table.
	 * @return List<User>
	 */
	List<User> getActiveUsers();
	
	/**
	 * @param finds an existing user based on the given criteria
	 * @return User entity
	 */
	User findExistingUser(User user);
	
	/**
	 * fetches the List of Roles for the user
	 * @param user
	 * @return
	 */
	String getUserRoles(User user);
	
	/**
	 * fetches the List of Projects for the user
	 * @param user
	 * @return
	 */
	String getUserProjects(User user);
	
	/**
	 * fetches the List of Teams for the user
	 * @param user
	 * @return
	 */
	String getUserTeams(User user);
	
	/**
	 * deletes all existing relations of the User (Roles, Projects, Teams)
	 * @param user
	 * @return
	 */
	String deleteOldRelations(User user);
	
	/**
	 * insert new relations of the User (Roles, Projects, Teams)
	 * @param user
	 * @return
	 */
	String insertNewRelations(User user);
	
	
	
	/**
	 * @return
	 */
	List<User> getUsersFromLdap(Integer userId,String tenantName);
	
	User checkUsersFromLdap();
	
	String getUsersById(String id);
	
	List<User> getUsersByCreated(String id);
	
	int deleteByTenantName(String name);
	
	
	List<User> getUsersByUserNameAndPassword(String uname,String pwd);
	
	List<User> getUsersBasedOnCreatedBy(Integer id);
	
	int updateUserById(User u);
	
	List<User> getListNotIn(Integer id);
	List<User> getTenantNames(Integer id);
	
	User findByFirstName(String name);
	
	
	User findByFirstNameAndEmail(User entity);
	
	List<User> getImportedUsers(Integer userId);
	
	List<User> getCreatedUsers(Integer userId);
	
	int updateAppUser(User user);
	
	List<User> executeSqlQuery(String query);
	
	String getTenantName(Integer userId);
	
	User findByUserName(String username);
	
	int updatePasswordByUserName(String username,String password);
	
	int updatePasswordById(Integer id,String password,java.sql.Date date);
	
	int updatePasswordByIdAndRequired(Integer id, String password);
	
	User findByUserNameAndMail(String username,String mail);
	
	public User findByUserDetailsToUpdate(User user);
	
	List<User> getAllOrderby();
	
	User findByUserNameOrMailId(String mail,String username);
	
	User findByEmail(String email);
	
	int updatePasswordByUserIdAndRequired(Integer userid,String password);
	
	List<User> getUsersNotAssignedProjectAndCreated(Integer projectId,Integer userId);
	
	List<User> getUsersAssignedProjectAndCreated(Integer projectId,Integer userId);
	
	List<User> getUsersByTeamAndCreatedBy(Integer teamId,Integer userId);
	
	List<User> getUsersNotByTeamAndCreatedBy(Integer teamId,Integer userId);
	
	List<User> getUsersByTeamId(Integer teamId);
	
	List<User> insertLdapUsers(List<Person> personList,Integer userId,String tenantName);
}
