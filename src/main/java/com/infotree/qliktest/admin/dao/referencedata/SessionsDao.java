package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.Sessions;

public interface SessionsDao {

	Sessions insertUser(Sessions session);
	
	Sessions deleteUser(Integer userId);
	
	List<Sessions> getAllUsers();
	
	List<Sessions> findByUserId(Integer userId);
}
