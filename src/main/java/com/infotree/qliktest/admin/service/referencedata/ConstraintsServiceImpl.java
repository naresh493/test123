/**
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.service.referencedata;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infotree.qliktest.admin.dao.referencedata.ConstraintsDao;
import com.infotree.qliktest.admin.entity.referencedata.Constraints;

@Service
public class ConstraintsServiceImpl implements ConstraintsService {

	private static final Logger LOG = LoggerFactory.getLogger(ConstraintsServiceImpl.class);
	@Autowired
	private ConstraintsDao constraintsDao;
	
	@Override
	public Constraints findByName(String name) {
		Constraints con = null;
		try{
			con = constraintsDao.findByName(name);
			return con;
		}catch(Exception e){
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Constraints findByNameAndNotId(String name, Integer id) {
		try {
			return constraintsDao.findByNameAndNotId(name, id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Constraints getById(Serializable id) {
		// TODO Auto-generated method stub
		try {
			return constraintsDao.getById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Constraints save(Constraints t) {
		// TODO Auto-generated method stub
		try {
			return constraintsDao.save(t);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String delete(Constraints t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Constraints> list() {
		// TODO Auto-generated method stub
		try {
			return constraintsDao.list();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int updateConstraint(Constraints constraints) {
		// TODO Auto-generated method stub
		try {
			return constraintsDao.updateConstraint(constraints);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Constraints> findNotById(Integer id) {
		// TODO Auto-generated method stub
		try {
			return constraintsDao.findNotById(id);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Constraints> getByOrder() {
		// TODO Auto-generated method stub
		try {
			return constraintsDao.getByOrder();
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Constraints> findByModuleId(Integer moduleId) {
		// TODO Auto-generated method stub
		try {
			return constraintsDao.findByModuleId(moduleId);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Constraints> findByConstraintNamePattern(String name) {
		// TODO Auto-generated method stub
		try {
			return constraintsDao.findByConstraintNamePattern(name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Constraints> findByModIdAndConstraintNamePattern(
			Integer moduleId, String name) {
		// TODO Auto-generated method stub
		try {
			return constraintsDao.findByModIdAndConstraintNamePattern(moduleId, name);
		} catch (Exception e) {
			LOG.error(e.toString());
			e.printStackTrace();
			return null;
		}
	}

}
