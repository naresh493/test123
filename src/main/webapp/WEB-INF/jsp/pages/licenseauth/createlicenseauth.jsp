<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/licenseauth/save" />
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
				<font size="3"><b>Manage License Authorities</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty licenseauthcreated}'> 
				<tr id="trMsg">
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${licenseauthcreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty licenseauthalreadyexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${licenseauthalreadyexists}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		<table style="text-align:center;margin-left: 115px;margin-bottom: 17px;font-size: 14px; font-weight:bold" id="selectMenu" class="tblData">
			<tr>
				<!-- <td align="center"><input type="button" class="btn btn-secondary" value="Create" onclick="ManageModule('create')" /></td>
				<td align="center"><input type="button" class="btn btn-secondary" value="Edit" onclick="ManageModule('edit')" /></td>
				<td align="center"><input type="button" class="btn btn-secondary" value="Delete" onclick="ManageModule('delete')" /></td> -->
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
				<a style="underline" class="green" href="#" onclick="ManageLicenseAuthpermissions('create')">Create</a></td>
				<td align="center">/ <a class="yellow" href="#" onclick="ManageLicenseAuthpermissions('edit')">Edit</a></td>
				<!-- <td align="center">/ <a class="yellow" href="#" onclick="ManageLicenseAuth('delete')">Delete</a></td> -->
			</tr>
		</table>
		<table class="tblFormData">
			<tr id="trCreateLicenseAuth">
				<td>
					<form:label for="id" path="id">Existing LicenseAuths<br /></form:label>
				</td>
				<td>
					<form:select id="licenseAuthList" path="id" multiple="multiple" disabled="true" style="width:300px;height:230px;">
						<form:options items="${active_licenseauth_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			<tr id="trEditLicenseAuth">
				<td>
					<form:label for="id" path="id">Select LicenseAuth<font color='red'>*</font></form:label>
				</td>
				<td>
					<table>
							<c:forEach items="${active_licenseauth_list}" var="item">
								<tr id="trChkBoxLicenseAuth">
									<td><form:radiobutton path="name" value="${item.name}" onclick="populateLicenseAuthName()"/></td>
									<td>&nbsp;&nbsp;${item.name}<br /></td>
								</tr>
							</c:forEach>
							<tr><td><br /></td></tr>
					</table>
				</td>
			</tr>
			<tr id="trDeleteLicenseAuth">
				<td>
					<form:label for="id" path="id">Select LicenseAuth<br /><font color="green" size="2"></font></form:label>
				</td>
				<td>
					<table>
							<c:forEach items="${active_licenseauth_list}" var="item">
								<tr id="trChkBoxLicenseAuth">
									<td><form:radiobutton path="name" value="${item.name}"/></td>
									<td>&nbsp;&nbsp;${item.name}<br /></td>
								</tr>
							</c:forEach>
					</table>
				</td>
			</tr>
			<tr id="trLicenseAuthName">
				<td>
					<form:label for="name" path="name">LicenseAuth Name<font color='red'>*</font></form:label>
				</td>
				<td>
					<spring:bind path="name">
			              <form:input id="licenseAuthName" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="name" size="30" style="width:240px;" type="text" class="input-large" placeholder="LicenseAuth Name" required="true" onchange="return checkSpaces(this.value)"/>
			              
			        </spring:bind>
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