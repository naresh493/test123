<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<head>
<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>

<link rel="stylesheet" href="/QlikTestAdmin/static/css/jquery-ui-1.8.16.custom.css">
	<script type="text/javascript">
	
	$(document).ready(function() {
		
		
		$("input").filter('.datepicker').datepicker({
			minDate : 0,
			showOn: 'button',
			buttonImage: "/QlikTestAdmin/static/images/calendar.png",
			buttonImageOnly: true,
			minDate: new Date(2014, 0, 1),
			dateFormat: 'yy-m-d',
			maxDate: new Date(2100, 11, 31),
	        autoclose: true
		})
	});
	
	/* (function() {

		  $(".dp").datepicker({
		    format: 'dd.mm.yyyy',
		    startDate: '01.01.2012',
		    endDate: ''
		  }).on("show", function() {
		    $(this).val("01.05.2012").datepicker('update');
		  });

		})();
	 */
</script>
<style>
		table.table.table-striped{
			height: 100px;
			width: 100%;
			background-color: #f9f9f9;
			margin: 0 auto;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
	</style>
	<style>
		label{
			cursor:auto !important;
		}
  #loading {
  width: 100%;
  height: 100%;
  top: 0px;
  left: 0px;
  position: fixed;
  display: block;
  opacity: 0.5;
  background-color: #fff;
  z-index: 99;
  text-align: center;
}

#loading-image {
  position: absolute;
  top: 30px;
  left: 50px;
  z-index: 100;
} 
.tbl
{
width:95%;
margin:0 auto;
}
 .tbl table tr td
 {
 word-break: break-word;
 }
 
 
</style>

<c:url var="firstUrl" value="/content/errorlogs/getErrorLogDetails/0" />
<c:url var="lastUrl" value="/content/errorlogs/getErrorLogDetails/${numberofpages - 1}" />
<c:url var="prevUrl" value="/content/errorlogs/getErrorLogDetails/${currentpage - 1}" />
<c:url var="nextUrl" value="/content/errorlogs/getErrorLogDetails/${currentpage + 1}" />



</head>
<body>

<div id="loading">
  <img id="loading-image" src="/QlikTestAdmin/static/images/ajax-loader.gif" alt="Loading..." />
</div>

<c:set var="pageUrl" scope="request">
	 <c:url value="getErrorLogDetails"/>
  </c:set>

<form:form class="form-errorlog" commandName="errorlog" action="${firstUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					Error Logs</b></font><br /><br />
			</td>
		</tr>
	</table>
	<table align="center">
		<tr align="center">
				
				<td>
					<form:label for="startDate" path="startingDate">Start Date</form:label>
				</td>
				<td>
					<spring:bind path="startingDate">
						 <form:input id="startDate" autocomplete="off" title="Select date from datepicker" type="text" class="datepicker" size="5" path="startingDate" style="width:200px; margin-left:10px;" itemLabel="startDate"/>
			        </spring:bind>
				</td>
				<td style="padding-left:20px;">
					<form:label for="endDate" path="endingDate" >End Date</form:label>
				</td>
				<td>
					<spring:bind path="endingDate">
			              <form:input id="endDate" autocomplete="off" title="Select date from datepicker" type="text" class="datepicker" size="5"  path="endingDate" style="width:200px; margin-left:10px;" itemLabel="endDate" />
			        </spring:bind>
				</td>
				</tr>
			
			<tr>
				
				<td align="right"><label>Project Type <font color='red' size='3'>*</font></label></td>
				<td>
					<form:select id="projectType" path="projectType" style="color:#333333;width:200px; margin-left:10px;" >
					
						<form:option value="" label="---Select Project Type---"/>					
						<form:option value="qliktestadmin" label="QlikTestAdmin"/>
						<form:option value="sikuli" label="Image Editor"/>
						<form:option value="videocapture" label="VideoCapture"/>
						<form:option value="objectspy" label="ObjectSpy"/>
						<form:option value="parallelexecution" label="ParallelExecution"/>
						<form:option value="performance" label="Performance"/>
						<form:option value="quartzscheduler" label="QuartzScheduler"/>
						<form:option value="importtestcases" label="ImportTestCases"/>
						<form:option value="socketproject" label="Socket Project" />
						<form:option value="brokenlinks" label="Broken Links" />
						<form:option value="automationexecution" label="Automation Execution" />
						
					</form:select>
				</td>
				<td align="right"><label>Level </label></td>
				<td>
					<form:select id="level" path="level" style="color:#333333;width:200px; margin-left:10px;" >
						<form:option value="" label="---level---"/>					
						<form:option value="INFO" label="INFO"/>
						<form:option value="ERROR" label="ERROR"/>
						<form:option value="WARN" label="WARN"/>
					</form:select> 
				</td>
			</tr>	
			<tr>
				<td colspan="4" align="center">
					<input name="submit" type="submit" id="get" class="btn btn-primary" value="Search" style="margin-top:-10px;" onclick="return validatePage();"/>&nbsp;
 					<input type="submit" name="submit" id="refresh" class="btn btn-primary" value="Refresh" style="margin-top:-10px; onclick="setTimeout(disableFunction, 1);"/>
				</td>
			</tr>
	</table>
<c:if test="${flag == 'true'}">
<div  class="tbl">
	<table id="viewallerrorlogs"  class="test-tbl table table-striped table-bordered table-hover ">
		<thead align="center">
			<tr>
				<th align="center">Level</th>
				<th align="center">ClassName</th>
				<th align = "center">Method</th>
				<th align="center" >Message</th>
				<th align = "center">Timestamp</th>
				<th align = "center">LineNumber</th>
				<th align = "center">Host-ip</th>
				<th align = "center">Project Type</th>
			</tr>
		</thead>
			<c:if test="${empty errorlogs}">
				<tbody align="center">
					<!-- <tr><td align="center" colspan="8"> Sorry, No Record Found in Database.</td></tr> -->
					<!-- <td colspan="2" align="center">$80</td> -->
					<tr><td colspan="8" align="center">Sorry, No Record Found in Database.</td></tr>
				</tbody>
			</c:if>
			
		<tbody align="center">
			<c:if test="${not empty errorlogs}">
				<c:forEach items="${errorlogs}" var="errorLog">
					<tr>
						<td align="center">${errorLog.level}</td>
						<td align="center">${errorLog.loggerName.className}</td> 
						<td align="center" >${errorLog.method}</td>
						<td align="center">${errorLog.message}</td>
						<td align="center" >${errorLog.timestamp}</td>
						<td align="center" >${errorLog.lineNumber}</td>
						<td align="center" >${errorLog.host.ip}</td> 
						
						<c:choose>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.QlikTestErrorLogs'}">
						       <td align="center" >QlikTestAdmin</td>
						    </c:when>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.VideoCaptureErrorLogs'}">
						       <td align="center" >VideoCapture</td>
						    </c:when>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.ObjectSpyErrorLogs'}">
						       <td align="center" >ObjectSpy</td>
						    </c:when>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.SikuliErrorLogs'}">
						       <td align="center" >Image Editor</td>
						    </c:when>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.QuartzSchedulerErrorLogs'}">
						       <td align="center" >QuartzScheduler</td>
						    </c:when>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.ParallelExecutionErrorLogs'}">
						       <td align="center" >ParallelExecution</td>
						    </c:when>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.PerformanceErrorLogs'}">
						       <td align="center" >Performance</td>
						    </c:when>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.ImportTestCasesErrorLogs'}">
						       <td align="center" >ImportTestCasesErrorLogs</td>
						    </c:when>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.BrokenlinksErrorLogs'}">
						       <td align="center" >BrokenLinks</td>
						    </c:when>
						    <c:when test="${errorLog.getClass().name =='com.infotree.qliktest.admin.entity.errorlogs.AutomationExecutionErrorLogs'}">
						       <td align="center" >AutomationExecution</td>
						    </c:when>
						</c:choose>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	</div>
</c:if>	
<c:if test="${flag == 'false'}">
	<table id="viewallerrorlogs" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center" width="300px">Socket File Name</th>
				<th align="center" width="200px">Download</th>
			</tr>
		</thead>
		<c:if test="${empty socketWebLogs}">
				<tbody align="center">
					<tr><td colspan="2"> Sorry, No Record Found in Database.</td></tr>
				</tbody>
		</c:if>
		<tbody align="center">
			<c:if test="${not empty socketWebLogs}">
				<c:forEach items="${socketWebLogs}" var="socketWebLog">
					<tr>
						<td align="center">${socketWebLog.filename}</td>
						<td align="center"><a href=<%=request.getContextPath()%>/content/errorlogs/getSocketWeb?id=${socketWebLog.id }&filename=${socketWebLog.filename}>download</a></td> 
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</c:if>
	
	<c:if test="${(flag == 'false')  && (fn:length(socketWebLogs)>10) }">
	<%-- <c:if test="${((not empty errorlogs) and (fn:length(errorlogs)>10)) or ((not empty socketWebLogs) and (fn:length(socketWebLogs)>10))}">
	<c:if test="${(empty object_1.attribute_A) and (empty object_2.attribute_B)}"> --%>
	<div id="pager" class="pagination">
		<ul>
			<li class="prev first"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt;&lt; First</a></li>
			<li class="prev"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt; Prev</a></li>
			<li class="active"><a href="#" id="curPageNumb">page</a></li>
			<li class="next"><a href="#" onmouseup="setTimeout('updatePage()',500);">Next &gt;</a></li>
			<li class="next last"><a href="#" onmouseup="setTimeout('updatePage()',500);">Last &gt;&gt;</a></li>
		</ul>
		<input type="hidden" class="pagedisplay" id="disp" value="5" />
		<input type="hidden" class="pagesize" value="10" />
	</div>
	</c:if>
	
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<script type="text/javascript">
		  $(document).ready(function(){
  			$("table#viewallerrorlogs").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		 
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
		function disableFunction() {
				document.getElementById("get").disabled = 'true';
				document.getElementById("refresh").disabled = 'true';
		}
		
		</script>

	</form:form>
	
<%-- <c:if test="${flag == 'true'} || ${flag == 'false'}}">	 --%>
<%-- <c:if test="${(flag == 'true')  || (flag == 'false')}"> --%>
<c:if test="${(flag == 'true')  && (numberofpages >0 )}">
<div id="pager" class="pagination" align="center">
    <ul>
        <c:choose>
            <c:when test="${currentpage == 0}">
                <li class="disabled"><a href="#">&lt;&lt;First</a></li>
                <li class="disabled"><a href="#">&lt;Previous</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${firstUrl}">&lt;&lt;First</a></li>
                <li><a href="${prevUrl}">&lt;Previous</a></li>
            </c:otherwise>
        </c:choose>
        <c:choose>
        	<c:when test="true">
        		<li class="disabled"><a>Page<c:out value="${currentpage+1}"></c:out>/<c:out value="${numberofpages}"></c:out></a>
        	</c:when>
        </c:choose>
        <c:choose>
            <c:when test="${currentpage+1 == numberofpages}">
                <li class="disabled"><a href="#">Next&gt;</a></li>
                <li class="disabled"><a href="#">Last&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${nextUrl}">Next&gt;</a></li>
                <li><a href="${lastUrl}">Last&gt;&gt;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
</c:if>	
 <script language="javascript" type="text/javascript">
  $(window).load(function() {
    $('#loading').hide();
  });


function validatePage(){
			var pType = document.getElementById('projectType').value;
			var startDate = document.getElementById('startDate').value;
			var endDate = document.getElementById('endDate').value;
			
			if((pType == null || pType == '')){
				
				alert('Please Select One Project Type');
				return false;
			}else{
				setTimeout(disableFunction, 1);
				return true;
			}
		}

</script>
</body>
</html>