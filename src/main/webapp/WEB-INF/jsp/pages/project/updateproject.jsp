 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<html>
<head>
<script type="text/javascript">
$(document).ready(function() {
	
	$("input").filter('.datepicker').datepicker({
		minDate : 0,
		showOn: 'button',
		buttonImage: "/QlikTestAdmin/static/images/calendar.png",
		buttonImageOnly: true,
		dateFormat : 'yy-mm-dd'
	});
});
</script>
<script type="text/javascript">
function validatePages(){
	var startdate = document.getElementById('startdate').value;
	var enddate = document.getElementById('enddate').value;
	var projectName = document.getElementById('projectName').value;
	if((startdate == null || startdate == "") || (enddate == null || enddate == "")){
		alert("Please Select StartDate and End Date");
		return false;
	}
	for (i = 0, len = projectName.length; i < len; i++) {
	    code = projectName.charCodeAt(i);
	    if (!(code > 47 && code < 58) && // numeric (0-9)
	        !(code > 64 && code < 91) && // upper alpha (A-Z)
	        !(code > 96 && code < 123)&& // lower alpha (a-z)
	        !(code == 32 || code==95)) {
	        alert('only Alphanumeric, under Score and space allowed in Project Name.');
	      return false;
	    }
	  }
	selectAllValues();
	return true;
}
	</script>
</head>
<style type="text/css">
	    select {
	        width: 205px;
	        float: left;
	    }
	    .controls {
	        width: 40px;
	        float: left;
	        margin: 10px;
	    }
	    .controls a {
	        background-color: #222222;
	        border-radius: 4px;
	        border: 2px solid #000;
	        color: #ffffff;
	        padding: 2px;
	        font-size: 14px;
	        text-decoration: none;
	        display: inline-block;
	        text-align: center;
	        margin: 5px;
	        width: 20px;
	    }
    </style>
    <style>
    	#flip{
    		background-color:#c3c3c3;
    		color: #fff;
    	}
		#panel,#flip
		{
		padding:5px;
		text-align:center;
		border:solid 1px #c3c3c3;
		width:60%;
    	margin: 0 auto;
		}
		#panel
		{
		padding:50px;
		}
</style>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/project/update" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Update Project</b></font><br /><br />
				</td>
			</tr>
			<c:choose>
				<c:when test='${not empty projectupdated and projectupdated eq "Data cannot be saved. Pl. try again."}'> 
					<tr>
							<td colspan="2" align="center"><font color="red" size="3">${projectupdated}</font><br /><br /></td>
					</tr>
				</c:when>
				<c:when test='${not empty projectupdated}'> 
					<tr>
							<td colspan="2" align="center"><font color="green" size="3"><b>${projectupdated}</b></font><br /><br /></td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</table>
		<table class="tblFormData">
			<tr>
				<td><label>Project Name<font color='red'>*</font></label></td>
				<td>
					<spring:bind path="name">
			              <form:input path="name" id="projectName" autocomplete="off" size="30" type="text" class="input-large" placeholder="Project Name" required="true"/>


			        </spring:bind>
				</td>
			<tr>
			<tr>
				<td>
					<form:label for="startDate" path="startDate">Start Date <font color="red" size="3">*</font></form:label>
				</td>
				<td>
					<spring:bind path="startDate">
			              <form:input type="text" autocomplete="off" id="startdate" title="Select date from datepicker" class="datepicker" size="5" path="startDate" itemLabel="startDate" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="endDate" path="endDate">End Date <font color="red" size="3">*</font></form:label>
				</td>
				<td>
					<spring:bind path="endDate">
			              <form:input type="text" autocomplete="off" id="enddate" title="Select date from datepicker" class="datepicker" size="5" path="endDate" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			
			<tr>
				<td>
					<form:label for="tenantName" path="tenantName">Tenant Name</form:label>
				</td>
				<td>
					<spring:bind path="tenantName">
			              <form:input type="text" autocomplete="off" size="5" path="tenantName" disabled="true"/>
			        </spring:bind>
				</td>
			</tr>
			<%-- <tr>
				<td>
					<form:label for="id" path="modules">Available Modules<br /><font color="green" size="2"><b>(cannot be edited)</b></font></form:label>
				</td>
				<td>
					<form:select id="modules" path="modules" multiple="multiple" disabled="true">
						<form:options items="${module_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr> --%>
			</table>
			<div id="flip">Assign Packages To Project</div>
			<div id="panel">
			<table align="center">
				<tr align="center" >
			</table>
			<table align="center">
				<tr align="center">
					<td>
						<table align="center" >
							<tr id="traPermissions" >
								<td >
									<div><font size="2">Available Packages</font></div>
									<form:select id="from" path="modules" style="width:177px;height:155px;">
										<form:options items="${available_module_list}" itemLabel="name" itemValue="id" />
									</form:select>
							
								</td>
							</tr> 
						</table>
					</td>
					<td>
							<div class="controls"> 
						        <a href="javascript:moveAll('from', 'to')">&gt;&gt;</a> 
						        <a href="javascript:moveSelected('from', 'to')">&gt;</a> 
						        <a href="javascript:moveSelected('to', 'from')">&lt;</a> 
						        <a href="javascript:moveAll('to', 'from')" href="#">&lt;&lt;</a> 
						    </div>
					</td>
					<td>
					
						<table>
							<tr id="trPerm" >
								<td colspan="2" >
									<div><font size="2">Assigned Packages</font></div>
	        						<form:select id="to" path="moduleId" style="width:177px;height:155px;">
										<form:options items="${assigned_module_list}" itemLabel="name" itemValue="id" />
									</form:select>
   						 		</td>
   							 </tr>
						</table>
					</td>
										
				</tr>
			</table>
			</div>
			<br/>
	        
	        <spring:bind path="modifiedBy">
	              <form:hidden path="modifiedBy" value="${sessionScope.userid}" />
	        </spring:bind>
	        
	        <spring:bind path="modifiedDate">
	              <form:hidden path="modifiedDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	
	        <spring:bind path="id">
	              <form:hidden path="id" value="${idToBeUpdated}" />
	        </spring:bind>
	        <table align="center">
			<tr>
				<td colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" class="btn btn-primary" value="Update" onclick="return validatePages()"/>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			</tr>
		</table>
		<script>
		    function moveAll(from, to) {
		        $('#'+from+' option').remove().appendTo('#'+to); 
		    }
		    
		    function moveSelected(from, to) {
		        $('#'+from+' option:selected').remove().appendTo('#'+to); 
		    }
    </script> 
	</form:form>
	</html>