<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/licenseauth/savelicenseauthperm" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;
				<font size="3"><b>Manage LicenseAuth Permissions</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty licenseauthpermissioncreated}'> 
				<tr id="trMsg">
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${licenseauthpermissioncreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty licenseauthpermissioncreatedalreadyexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${licenseauthpermissioncreatedalreadyexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		<table style="text-align:center;margin-left: 115px;margin-bottom: 17px;font-size: 14px; font-weight:bold" id="selectMenu" class="tblData">
			<tr>
				<td align="center">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;
			</tr>
		</table>
		<table class="tblFormData">
			<tr id="trCreateLicenseAuthPermissions">
				<spring:bind path="permissions">
					<td>
						<form:label path="permissions">Select Permissions<br /></form:label>
					</td>
					<td>
						<form:select id="permissionsList" path="permissions" multiple="multiple" disabled="false" style="width:225px;height:230px;">
							<form:options items="${permissions_list}" itemLabel="name" itemValue="id" />
						</form:select>
					</td>
				</spring:bind>
			</tr>
			<tr>
				<td align="center" colspan="2"><font color="red" size="3">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<b>(or)</b></font><br /></td>
			</tr>
			<tr>
			<td align="center" colspan="2"></td>
			</tr>
			<tr id="trAddNewPermission">
				<td>
					<form:label for="name" path="permissionname">Add New Permission</form:label>
				</td>
				<td>
					<spring:bind  path="permissionname">
			              <form:input id="permissionName" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="permissionname" size="30" type="text" class="input-large" placeholder="Permission Name" onchange="return checkSpaces(this.value)"/>
			             
			             
			        </spring:bind>
				</td>
			</tr>
			<tr id="trCreateLicenseAuthPermissions1">
				<td>
					<form:label for="id" path="id">Select License Authority<font color='red'>*</font></form:label>
				</td>
				<td>
					<form:select id="licenseAuthList" path="id" style="width:225px;">
						<form:options items="${active_licenseauth_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
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
			<tr>
				<td colspan="2" align="center">
					
					<c:if test="${empty crudObj.id}">
					  	 <input id="btnCreate" type="submit" class="btn btn-primary" value="Create"/>
					  	 <input id="btnEdit" type="submit" class="btn btn-primary" value="Update"/>
					  	 <!-- <input id="btnDelete" type="submit" class="btn btn-primary" value="Delete"/> -->
					</c:if>
		        </td>
			<tr>
		</table>
	</form:form>	