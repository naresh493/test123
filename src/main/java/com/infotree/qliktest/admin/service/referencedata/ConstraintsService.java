/**
 * This interface represents the service operations related to controls part 
 * @author koteswara.g
 * 
 */
package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.referencedata.Constraints;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface ConstraintsService extends QTAdminService<Constraints>{

	Constraints findByName(String name);
	
	Constraints findByNameAndNotId(String name,Integer id);
	
	List<Constraints> findNotById(Integer id);
	
	List<Constraints> getByOrder();
	
	int updateConstraint(Constraints constraints);
	
	List<Constraints> findByModuleId(Integer moduleId);
	
	List<Constraints> findByConstraintNamePattern(String name);
	
	List<Constraints> findByModIdAndConstraintNamePattern(Integer moduleId,String name);
}
