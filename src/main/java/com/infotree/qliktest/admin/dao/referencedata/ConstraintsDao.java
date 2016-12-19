/**
 * This is the dao inteface related to the controls operations
 * @author koteswara.g
 */
package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.referencedata.Constraints;

public interface ConstraintsDao extends QTAdminDao<Constraints> {

	Constraints findByName(String name);
	
	Constraints findByNameAndNotId(String name,Integer id);
	
	List<Constraints> findNotById(Integer id);
	
	List<Constraints> getByOrder();
	
	int updateConstraint(Constraints constraints);
	
	List<Constraints> findByModuleId(Integer moduleId);
	
	List<Constraints> findByConstraintNamePattern(String name);
	
	List<Constraints> findByModIdAndConstraintNamePattern(Integer moduleId,String name);
}
