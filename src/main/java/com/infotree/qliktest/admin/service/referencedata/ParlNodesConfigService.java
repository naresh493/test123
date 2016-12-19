package com.infotree.qliktest.admin.service.referencedata;

import java.util.List;

import com.infotree.qliktest.admin.entity.configs.ParallelNodes;
import com.infotree.qliktest.admin.service.QTAdminService;

public interface ParlNodesConfigService extends QTAdminService<ParallelNodes> {
	
	public ParallelNodes findByConfigName(String name);
	
	public List<ParallelNodes> getParallelNodesCreated(String id);
	
	public String getParallelNodesById(String id);
	
	public ParallelNodes findByNameAndIdNotIn(String name,Integer id);
	
	public int updateParallelNodesConfiguration(ParallelNodes spy);
	
	List<ParallelNodes> findByOrder();
}
