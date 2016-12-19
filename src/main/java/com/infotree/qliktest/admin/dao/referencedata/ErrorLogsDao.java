package com.infotree.qliktest.admin.dao.referencedata;

import java.io.InputStream;
import java.util.List;

import com.infotree.qliktest.admin.entity.errorlogs.ErrorLogs;
import com.infotree.qliktest.admin.entity.errorlogs.SocketWebLogPojo;
import com.infotree.qliktest.admin.helperpojo.ErrorLogFormPojo;

public interface ErrorLogsDao {

	/*List<ErrorLogs> getAllLogs();*/
	List<ErrorLogs> getLogsByTimeAndProjectName(ErrorLogFormPojo errorLogFormPojo,int page,int pageLimit);
	List<SocketWebLogPojo> getSocketWebLogs(ErrorLogFormPojo errorLogFormPojo);
	InputStream getSocketWebFile(String filename);
	int getColumnCount(ErrorLogFormPojo errorLogFormPojo);
}
