package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.UserProjectDao;
import com.infotree.qliktest.admin.entity.referencedata.User;
import com.infotree.qliktest.admin.entity.referencedata.UserProject;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class UserProjectServiceImpl extends AbstractQTAdminService<UserProject> implements UserProjectService{

	private static final Logger LOG = LoggerFactory.getLogger(UserProjectServiceImpl.class);
	@Autowired
	private UserProjectDao userProjectDao;

	public UserProject save(UserProject t) {
		return super.save(t);
	}

	public String delete(UserProject t) {
		return super.delete(t);
	}

	public int deleteByUserIdAndProjectId(Integer userId, Integer projectId) {
		try {
			return userProjectDao.deleteByUserIdAndProjectId(userId, projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	protected QTAdminDao<UserProject> getTDao() {
		try {
			return userProjectDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	
	public int deleteByProjectId(Integer projectId) {
		try {
			return userProjectDao.deleteByProjectId(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
			
		}
	}
	public List<User> getUsersByProjectId(Integer projectId){
		try {
			return userProjectDao.getUsersByProjectId(projectId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}
	public int deleteMultUsersFromProject(Integer projectId,String users){
		try {
			return userProjectDao.deleteMultUsersFromProject(projectId, users);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}
	public List<UserProject> getByProjectId(Integer projectid){
		try {
			return userProjectDao.getByProjectId(projectid);
		} catch (Exception e) {

			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<UserProject> getByUserId(Integer userId) {
		// TODO Auto-generated method stub
		try {
			return userProjectDao.getByUserId(userId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteByUserId(Integer userId) {
		try{
			return userProjectDao.deleteByUserId(userId);
		}catch(Exception e){
			return 0;
		}
	}

	
}
