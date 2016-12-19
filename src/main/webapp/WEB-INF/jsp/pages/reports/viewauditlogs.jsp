<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<html>
<head>

	<style>
		table.table.table-striped{
			height: 100px;
			width: 500px;
			background-color: #f9f9f9;
			margin: 0 auto;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
	</style>

<c:if test="${flag=='false' }">	
<c:url var="firstUrl" value="/content/logs/viewpagelogs/0" />
<c:url var="lastUrl" value="/content/logs/viewpagelogs/${numberofpages - 1}" />
<c:url var="prevUrl" value="/content/logs/viewpagelogs/${currentpage - 1}" />
<c:url var="nextUrl" value="/content/logs/viewpagelogs/${currentpage + 1}" />
<c:url var="refresh" value="/content/logs/viewlogs" />
</c:if>

<c:if test="${flag == 'true'}">
	<c:url var="firstUrl" value="/content/logs/viewpagealllogs/0" />
	<c:url var="lastUrl" value="/content/logs/viewpagealllogs/${numberofpages - 1}" />
	<c:url var="prevUrl" value="/content/logs/viewpagealllogs/${currentpage - 1}" />
	<c:url var="nextUrl" value="/content/logs/viewpagealllogs/${currentpage + 1}" />
	<c:url var="refresh" value="/content/logs/viewalllogs"/>
</c:if>
</head>
<body>
	<script type="text/javascript">
	
	
	$(document).ready(function() {
		
		$("input").filter('.startdatepicker').datepicker({
			minDate : 0,
			showOn: 'button',
			buttonImage: "/QlikTestAdmin/static/images/calendar.png",
			buttonImageOnly: true,
			minDate: new Date(2014, 0, 1),
			dateFormat: 'yy-m-d',
			maxDate: '+0d',
	        autoclose: true
		});
		$("input").filter('.enddatepicker').datepicker({
			minDate : 0,
			showOn: 'button',
			buttonImage: "/QlikTestAdmin/static/images/calendar.png",
			buttonImageOnly: true,
			minDate: new Date(2014, 0, 1),
			dateFormat: 'yy-m-d',
			maxDate: new Date(2100, 11, 31),
	        autoclose: true
		});
	});
</script>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/logs/searchlogs"/>
  </c:set>
<form:form class="form-viewteams" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					Audit Trail</b></font><br /><br />
			</td>
		</tr>
	</table>
	
	<table align="center">
		<tr align="center">		
				
				<td align="center" colspan="2">
					<form:label for="idUser" path="userId" >Users</form:label>
				</td>
				<td>
					<form:select id="idUser" path="userId" style="color:#333333;width:150px !important">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${user_list}" itemLabel="userName" itemValue="id" />
					</form:select>
				</td>
				
				<td>
					<form:label for="startDate" path="startDate" style="margin-left:20px; padding-right:10px;">StartDate</form:label>
				</td>
				<td>
					<spring:bind path="startDate">
			              <form:input id="startDate" autocomplete="off" title="Select date from datepicker" type="text" class="startdatepicker" size="5" path="startDate" itemLabel="startDate"/>
			        </spring:bind>
				</td>
				
		</tr>
				<tr>
				
					<td align="center" colspan="2">
					<form:label for="idProject" path="projectName" >Projects</form:label>
				</td>
				<td>
					<form:select id="idProject" path="projectName" style="color:#333333;width:150px !important">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="name" />
					</form:select>
				</td>
				
				<td align="right">
					<form:label for="endDate" path="endDate" style="margin-left:20px; padding-right:10px;">EndDate</form:label>
				</td>
				<td>
					<spring:bind path="endDate">
			              <form:input id="endDate" autocomplete="off" title="Select date from datepicker" type="text" class="enddatepicker" size="5" path="endDate" itemLabel="endDate"/>
			              
			        </spring:bind>
				</td>
				
				
				</tr>
				<tr>
				
			<td align="center" colspan="2">
					<form:label for="actionType" path="actionType" style="padding-right:10px;">Activity</form:label>
				</td>
				<td>
					<form:select id="actionType" path="actionType" style="color:#333333;width:150px !important">
						<form:option value="" label="--Please Select--"/>
						<form:option value="CREATE" label="CREATE"/>
						<form:option value="VIEW" label="VIEW"/>
						<form:option value="MODIFY" label="MODIFY"/>
						<form:option value="IMPORT" label="IMPORT"/>
						<form:option value="REMOVE" label="REMOVE"/>
						<form:option value="ASSIGN" label="ASSIGN"/>
						<form:option value="LOGIN" label="LOGIN"/>
					</form:select>
				</td>
			
				<td align="center" colspan="2">
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-top:-10px;"  onclick="return validatePage()"/>&nbsp;
					<!-- <input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/logs/viewalllogs'"/> -->
					<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='${refresh}' "/>
				</td>
			</tr>
</table>	
	<table id="viewteams" style="width: 800px;" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">UserName</th>
				
				<th align="center">Activity</th>
				<th align="center" width="700px">Action</th>
				<th align="center">Project</th>
				<th align="center">IPAddress</th>
				<th align="center" width="700px">DateTime</th>
				
				
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty audit_log_list}">
				<c:forEach items="${audit_log_list}" var="item">
				
					<tr>
						<td align="center">${item.userName}</td>
						<td align="center">${item.actionType}</td>
						<td align="center" width="700px">${item.actionPerformed}</td>
						<td align="center">${item.projectName}</td>
						<td align="center">${item.ipOrigin}</td>
						<td align="center" width="700px">${item.actionDateandTime}</td>
						
						
					</tr>
					
				</c:forEach>
			</c:if>
			
			
		</tbody>
	</table>
	 </form:form>
	 <c:if test="${empty audit_log_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="5" align="center"><font color=blue>---------*No Audit Records Available*---------</font></td>
					
				</tr>
				</table>
	</c:if>
<br />
<c:if test="${(numberofpages >0 )}">
<div class="pagination" align="center">
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
	<script type="text/javascript">
		
		function validatePage(){
			var idUser = document.getElementById('idUser').value;
			var idProject = document.getElementById('idProject').value;
			var actionType = document.getElementById('actionType').value;
			var startDate = document.getElementById('startDate').value;
			var endDate = document.getElementById('endDate').value;
			
			if((idUser == null || idUser == '') && (idProject == null || idProject =='') && (actionType == null || actionType == '')
					&& (startDate == null || startDate == '') && (endDate == null || endDate == '')){
				
				alert('Please Select Atleast a Option To Search');
				return false;
			}else{
				return true;
			}
		}
	</script>
	

</body>
</html>