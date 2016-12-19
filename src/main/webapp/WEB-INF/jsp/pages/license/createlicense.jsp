<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>


<c:set var="pageUrl" scope="request">
	 <c:url value="/content/license/save" />
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
				<font size="3"><b>Manage Licenses</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty licensecreated}'> 
				<tr id="trMsg">
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${licensecreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			<c:if test='${not empty licensealreadyexists}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="red" size="2"><b>${licensealreadyexists}</b></font><br /><br />
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
				<a style="underline" style="text-align:center" class="green" href="#" onclick="ManageLicense('create')"><b>Create</b></a></td>
				<td align="center">/ <a class="yellow"  href="#" onclick="ManageLicense('edit')"><b>Edit</b></a></td>
				
			</tr>
		</table>
		<table class="tblFormData">
			<tr id="trCreateLicense">
				<td>
					<form:label for="id" path="id">Existing Licenses<br /></form:label>
				</td>
				<td>
					<form:select id="licenseList" path="id" multiple="multiple" disabled="true">
						<form:options items="${active_license_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			<tr id="trEditLicense">
				<td> 
					<form:label for="id" path="id">Select License<font color='red' size="3">*</font></form:label>
				</td>
				<td>
					<table>
							<c:forEach items="${active_license_list}" var="item">
								<tr id="trChkBoxLicense">
									<td><form:radiobutton path="name" id="licenseName" value="${item.name}" onclick="populateLicenseName()"/></td>
									<td>&nbsp;&nbsp;${item.name}<br /></td>
								</tr>
							</c:forEach>
							<tr><td><br /></td></tr>
					</table>
				</td>
			</tr>
			
			<tr id="trLicenseName">
				<td>
					<form:label for="name" path="name">License Name<font color='red' size="3">*</font></form:label>
				</td>
				<td>
					<spring:bind path="name">
			              <form:input id="licenseName" path="name" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" size="30" type="text" class="input-large" value="" placeholder="License Name" required="true" onchange="return checkSpaces(this.value)"/>
			             
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