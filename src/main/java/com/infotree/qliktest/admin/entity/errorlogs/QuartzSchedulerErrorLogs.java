package com.infotree.qliktest.admin.entity.errorlogs;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="quartzscheduler")
public class QuartzSchedulerErrorLogs extends ErrorLogs {

}
