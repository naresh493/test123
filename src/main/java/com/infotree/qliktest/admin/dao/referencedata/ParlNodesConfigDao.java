package com.infotree.qliktest.admin.dao.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.dao.QTAdminDao;
import com.infotree.qliktest.admin.entity.configs.ParallelNodes;

public interface ParlNodesConfigDao extends QTAdminDao<ParallelNodes> {

	public ParallelNodes findByConfigName(String name);
	
	public List<ParallelNodes> getParallelNodesByCreated(String id);
	
	public String getParallelNodesById(String id);
	
	public ParallelNodes findByNameAndIdNot(String name,Integer id);
	
	int updateParallelNodesConfiguration(ParallelNodes spy);
	
	List<ParallelNodes> findByOrder();
}
