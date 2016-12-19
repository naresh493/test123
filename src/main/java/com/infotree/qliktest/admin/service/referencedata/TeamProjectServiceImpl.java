package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.dao.referencedata.TeamProjectDao;
import com.infotree.qliktest.admin.entity.referencedata.TeamProject;
import com.infotree.qliktest.admin.service.AbstractQTAdminService;


@Service
public class TeamProjectServiceImpl extends AbstractQTAdminService<TeamProject> implements
		TeamProjectService {

	private static final Logger LOG = LoggerFactory.getLogger(TeamProjectServiceImpl.class);
	@Autowired
	private TeamProjectDao teamprojectDao;
	
	@Override
	public TeamProject getById(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TeamProject save(TeamProject t) {
		// TODO Auto-generated method stub
		try {
			return teamprojectDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(TeamProject t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TeamProject> list() {
		// TODO Auto-generated method stub
		try {
			return teamprojectDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected QTAdminDao<TeamProject> getTDao() {
		// TODO Auto-generated method stub
		try {
			return teamprojectDao;
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TeamProject> getProjectsByTeamId(Integer teamId) {
		// TODO Auto-generated method stub
		try {
			return teamprojectDao.getProjectsByTeamId(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	

	@Override
	public int deleteByTeamId(Integer teamId) {
		// TODO Auto-generated method stub
		try {
			return teamprojectDao.deleteByTeamId(teamId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

}
