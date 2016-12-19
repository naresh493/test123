<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<style>
table.menu{
	font-size:100%;
	position:absolute;
}
</style>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/licenseauthmapping/addlicensestomodules"/>
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Add License To Module</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty licensesadded}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${licensesadded}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			
		</table>
		<table id="tblCreateProject" class="tblFormData">
		<tr>
				<td>
					<form:label for="moduleList" path="licenseAuthMappingComp.moduleId">Select A Module<font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<form:select id="moduleList" path="licenseAuthMappingComp.moduleId" required="true" style="color:#333333">
						<form:option value="" label="--Please Select Module--"/>
						<form:options items="${module_list}" itemLabel="name" itemValue="id"/>
					</form:select>
				</td>
		</tr>
		
			<tr>
				<td>
					<form:label for="moduleList" path="licenseAuthMappingComp.licenseId">Select A License<font color='red' size="3">*</font></form:label>
				</td>
				<td>
					<form:select id="moduleList" path="licenseAuthMappingComp.licenseId" required="true" style="color:#333333">
						<form:option value="" label="--Please Select License--"/>
						<form:options items="${license_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			
			<tr>
				<td>
					<form:label for="moduleList" path="licenseAuthMappingComp.licenseAuthId">Select A LicenseAuth<font color='red' size="3">*</font></form:label>
				</td>
				<td>
					<form:select id="moduleList" path="licenseAuthMappingComp.licenseAuthId" required="true" style="color:#333333">
						<form:option value="" label="--Please Select LicenseAuth--"/>
						<form:options items="${licenseauth_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
			</tr>
			
		
			<spring:bind path="createdBy">
             		<form:hidden id="createdBy" path="createdBy" value="${sessionScope.userid}" />
	        </spring:bind>
	         <spring:bind path="createdDate">
	              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	       <spring:bind path="modifiedBy">
	              <form:hidden id="modifiedBy" path="modifiedBy" value="${sessionScope.userid}" />
	        </spring:bind>
	         <spring:bind path="modifiedDate">
	              <form:hidden path="modifiedDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	      <tr>
				<td colspan="2" align="center">
					
					  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Assign"/>&nbsp;
		        </td>
			<tr>
			</table>
			
				
				
			
	
	</form:form>
	