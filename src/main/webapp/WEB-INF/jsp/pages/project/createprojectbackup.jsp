<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<style>
/* tr.menu{
	visibility:hidden;
}
td.menu{
	visibility:hidden;
} */
table.menu{
	font-size:100%;
	position:absolute;
	/* visibility:hidden; */
}
</style>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/project/save" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner" style="width:1300px;">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create New Project</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty projectcreated}'> 
				<tr>
					<td colspan="2" align="center">
						<%-- <ul class="nav nav-pills">
						  <li class="active"><a href="#">${usercreated}</a></li>
						</ul>
						<div class="alert alert-success" data-alert="alert" >
							<a class="close" href="#">&times;</a>
							<p>${usercreated}</p>
						</div> --%>
						<font color="green" size="2"><b>${projectcreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<%-- <c:if test='${not empty projectsnotselected}'> 
				<tr>
					<td colspan="2" align="center">
						<div class="alert-message error fade in" data-alert="alert" >
							<a class="close" href="#">&times;</a>
							<p>${tenantcreated}</p>
						</div>
						<font color="red" size="2"><b>${projectsnotselected}</b></font><br /><br />
					</td>
				</tr>
			</c:if> --%>
			<c:if test='${not empty projectalreadyexists}'> 
				<tr>
					<td colspan="2" align="center">
						<%-- <div class="alert-message error fade in" data-alert="alert" >
							<a class="close" href="#">&times;</a>
							<p>${tenantalreadyexists}</p>
						</div> --%>
						<font color="red" size="2"><b>${projectalreadyexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty projectcreationerror}'> 
				<tr>
					<td colspan="2" align="center">
						<%-- <div class="alert-message error fade in" data-alert="alert" >
							<a class="close" href="#">&times;</a>
							<p>${tenantalreadyexists}</p>
						</div> --%>
						<font color="red" size="2"><b>${projectcreationerror}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		<table id="tblData" class="tblFormData">
			<tr>
				<td>
					<form:label for="name" path="name">Project Name</form:label>
				</td>
				<td>
					<spring:bind path="name">
			              <form:input onkeyup="show()" id="projectName" path="name" size="30" type="text" class="input-large" placeholder="Project Name" required="true"/>
			              <form:errors path="name" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="startDate" path="startDate">Start Date</form:label>
				</td>
				<td>
					<spring:bind path="startDate">
			              <form:input type="text" class="datepicker" size="5" path="startDate" required="true"/>
			              <form:errors path="startDate" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="endDate" path="endDate">End Date</form:label>
				</td>
				<td>
					<spring:bind path="endDate">
			              <form:input type="text" class="datepicker" size="5" path="endDate" required="true"/>
			              <form:errors path="endDate" cssClass="help-inline" />
			              <c:if test="${empty status.errorMessage}">
							 <span class="help-inline"></span>
						  </c:if>
			        </spring:bind>
				</td>
			</tr>
			</table>
			<%-- <div id="moduleDiv">         	
        		<table id="tblModule" class="tblFormData">
        			<tr>
						<td>
							<form:label for="name" path="name">Module Name</form:label>
						</td>
						<td>
							<spring:bind path="name">
					              <form:input onkeypress="showTestingDiv()" id="projectName" path="name" size="30" type="text" class="input-large" placeholder="Module Name" required="true"/>
					              <form:errors path="name" cssClass="help-inline" />
					        </spring:bind>
						</td>
					</tr>
        		</table>
       		</div> --%>
       		<div id="moduleDiv">         	
        		<table id="tblModule" class="tblFormData">
					<spring:bind path="name">
						<tr>
							<td></td>
							<td><input type="checkbox" id="chkBoxFunctionalTesting" onclick="showLicense1('functional')" name="name"  value="functionalTesting"/></td>
						    <td>Functional Testing</td>
						</tr>
						<tr>
							<td style="colspan:3;rowspan:3;vertical-align:center"><form:label for="name" path="name">Testing Type</form:label></td>
							<td><input type="checkbox" id="chkBoxPerformanceTesting" onclick="showLicense2('performance')" name="name" value="performanceTesting" /></td>
						    <td>Performance Testing</td>
						</tr>
						<tr>
							<td></td>
							<td><input type="checkbox" id="chkBoxSecurityTesting" onclick="showLicense3('security')" name="name" value="securityTesting" /></td>
						    <td>Security Testing</td>
						</tr>
						<form:errors path="name" cssClass="help-inline" />
					</spring:bind>
        		</table>
       		</div><br/>
       		<div id="licenseDiv">         	
        		<table id="tblLicense" class="tblFormData">
        			<spring:bind path="name">
						<tr>
							<td>License Type</td>
							<td><input type="checkbox" id="chkBoxWrite" onclick="showWrite()" name="name" value="WRITE"/></td>
						    <td id="lblWRITE">WRITE</td>
						</tr>
						<tr id="trWrite">
							<td></td>
							<td></td>
							<td>
								<div id="divFunctionalWrite">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxTestScenario" name="name" onclick="clickWriteAutomationTestCase()" value="testScenario"/>&nbsp;&nbsp;&nbsp;Test Scenario<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxObjects" name="name" onclick="clickWriteAutomationTestCase()" value="objects"/>&nbsp;&nbsp;&nbsp;Objects<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxAutomationTestCase" name="name" onclick="clickWriteAutomationTestCase()" value="functionalTestCaseAutomation"/>&nbsp;&nbsp;&nbsp;Functional Test Case-Automation<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxManualTestCase" name="name" onclick="clickWriteManualTestCase()" value="functionalTestCaseManual"/>&nbsp;&nbsp;&nbsp;Functional Test Case-Manual
								</div>
								<div id="divPerformanceWrite">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxPerformanceTestCase" name="name" onclick="clickWriteAutomationTestCase()" value="performanceTestCase"/>&nbsp;&nbsp;&nbsp;Performance Test Case<br/>
								</div>
								<div id="divSecurityWrite">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxSecurityTestCase" name="name" onclick="clickWriteAutomationTestCase()" value="securityTestCase"/>&nbsp;&nbsp;&nbsp;Security Test Case<br/>
								</div>
							</td>
						</tr>
						<tr id="trExecute">
							<td></td>
							<td><input type="checkbox" id="chkBoxExecute" onclick="showExecute()" name="name" value="EXECUTE"/></td>
						    <td id="lblEXECUTE">EXECUTE</td>
						</tr>
						<tr id="trExecuteInner">
							<td></td>
							<td></td>
							<td>
								<div id="divFunctionalExecute">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxFunctionalTestSuiteAutomation" name="name" onclick="clickWriteAutomationTestCase()" value="functionalTestSuiteAutomation"/>&nbsp;&nbsp;&nbsp;Functional Test Suite-Automation<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxFunctionalTestSuiteManual" name="name" onclick="clickWriteAutomationTestCase()" value="functionalTestSuiteManual"/>&nbsp;&nbsp;&nbsp;Functional Test Suite-Manual<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxFunctionalTestManageOnlyAutomation" name="name" onclick="clickWriteAutomationTestCase()" value="functionalTestManageOnlyAutomation"/>&nbsp;&nbsp;&nbsp;Functional Test Manage(Only Automation)<br/>
								</div>
								<div id="divPerformanceExecute">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxPerformanceTestExecute" name="name" onclick="clickWriteAutomationTestCase()" value="performanceTestExecute"/>&nbsp;&nbsp;&nbsp;Performance Test Execute<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxPerformanceTestManage" name="name" onclick="clickWriteAutomationTestCase()" value="performanceTestManage"/>&nbsp;&nbsp;&nbsp;Performance Test Manage<br/>
								</div>
								<div id="divSecurityExecute">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxSecurityTestExecute" name="name" onclick="clickWriteAutomationTestCase()" value="securityTestExecute"/>&nbsp;&nbsp;&nbsp;Security Test Execute<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<form:checkbox for="name" path="name"  id="chkBoxSecurityTestManage" name="name" onclick="clickWriteAutomationTestCase()" value="securityTestManage"/>&nbsp;&nbsp;&nbsp;Security Test Manage<br/>
								</div>
							</td>
						</tr>
						<tr id="trTestResultsFunctional">
							<td></td>
							<td><form:checkbox for="name" path="name"  id="chkBoxlicenseTestResultsFunctional" name="name" value="testResultsFunctional"/></td>
						    <td id="lbllicenseTestResultsFunctional">Test Results-Functional</td>
						</tr>
						<tr id="trTestResultsPerformance">
							<td></td>
							<td><form:checkbox for="name" path="name"  id="chkBoxlicenseTestResultsPerformance" name="name" value="testResultsPerformance"/></td>
						    <td id="lbllicenseTestResultsPerformance">Test Results-Performance</td>
						</tr>
						<%-- <form:errors path="name" cssClass="help-inline" /> --%>
					</spring:bind>
        		</table>
       		</div><br/>
       		<%-- <table id="tblLicenseusers" class="tblFormData">
        		<tr>
					<td>
						<form:label for="noOfAllowedUsers" path="noOfAllowedUsers">No. of Allowed Users</form:label>
					</td>
					<td>
						<spring:bind path="noOfAllowedUsers">
				              <form:input type="text" size="5" path="noOfAllowedUsers" required="true"/>
				              <form:errors path="noOfAllowedUsers" cssClass="help-inline" />
				              <c:if test="${empty status.errorMessage}">
								 <span class="help-inline"></span>
							  </c:if>
				        </spring:bind>
					</td>
				</tr>
       		</table> --%>
			<spring:bind path="createdBy">
             		<form:hidden path="createdBy" value="${sessionScope.userid}" />
	        </spring:bind>
	        
	        <spring:bind path="createdDate">
	              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	        
	        <spring:bind path="modifiedBy">
	              <form:hidden path="modifiedBy" value="${sessionScope.userid}" />
	        </spring:bind>
	        
	        <spring:bind path="modifiedDate">
	              <form:hidden path="modifiedDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	        <table id="tblDatabutton" class="tblData" style="width:1300px;">
				<tr>
					<td colspan="2" align="center">
						<c:if test="${empty crudObj.id}">
						  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Create" />&nbsp;
						</c:if>
			        </td>
				<tr>
			</table>
	</form:form>
	<script type="text/javascript">
	$(document).ready(function() {
		$("input").filter('.datepicker').datepicker({
			minDate : 0,
			dateFormat : 'yy-mm-dd'
		});
	});
</script>
