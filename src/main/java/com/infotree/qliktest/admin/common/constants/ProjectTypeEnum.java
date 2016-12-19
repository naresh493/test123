package com.infotree.qliktest.admin.common.constants;

public enum ProjectTypeEnum {
	
	SIKULI("sikuli"),
	QLIKTEST_ADMIN("qliktestadmin"),
	VIDEO_CAPTURE("videocapture"),
	OBJECT_SPY("objectspy"),
	QUARTZ_SCHEDULER("quartzscheduler"),
	PARALLEL_EXECUTION("parallelExecution"),
	PERFORMANCE("performance"),
	WEB_SOCKET("socketproject"),
	IMPORT_TESTCASES("importtestcases"),
	BROKENLINKS("brokenlinks"),
	AUTOMATIONEXECUTION("automationexecution");
	
	private String projectType;
	
	private ProjectTypeEnum(String projectType){
		this.projectType=projectType;
	}
	public String getProjectType()
	{
		return projectType;
	}

}
