package com.infotree.qliktest.admin.helperpojo;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.User;

public class ViewTeamPojo {
private Integer id;
private String name;
private String createdBy;
private Integer count;
private List<User> firstName;
private String userName;

/**
 * @return the userName
 */
public String getUserName() {
	return userName;
}
/**
 * @param userName the userName to set
 */
public void setUserName(String userName) {
	this.userName = userName;
}
/**
 * @return the id
 */
public Integer getId() {
	return id;
}
/**
 * @param id the id to set
 */
public void setId(Integer id) {
	this.id = id;
}
/**
 * @return the name
 */
public String getName() {
	return name;
}
/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}
/**
 * @return the createdBy
 */
public String getCreatedBy() {
	return createdBy;
}
/**
 * @param createdBy the createdBy to set
 */
public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
}
/**
 * @return the count
 */
public Integer getCount() {
	return count;
}
/**
 * @param count the count to set
 */
public void setCount(Integer count) {
	this.count = count;
}
/**
 * @return the firstName
 */
public List<User> getFirstName() {
	return firstName;
}
/**
 * @param firstName the firstName to set
 */
public void setFirstName(List<User> firstName) {
	this.firstName = firstName;
}


}
