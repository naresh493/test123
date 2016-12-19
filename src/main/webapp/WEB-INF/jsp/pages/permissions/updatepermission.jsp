<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<style>
	label{
		cursor:auto !important;
	}
</style>
<script type="text/javascript">
	function validatePage(){
		var disable = document.getElementById('status').value;
		var disableStatus = document.getElementById('disableStatus').value;
		if(disable == 1){
			if(disableStatus == ""){
				//return true;
			}else{
				alert(disableStatus);
				return false;
			}
		}
		var code, i, len;
		var str =  document.getElementById('permissionDesc').value;
		var pName =  document.getElementById('permissionName').value;
		for (i = 0, len = pName.length; i < len; i++) {
		    code = pName.charCodeAt(i);
		    if (!(code > 47 && code < 58) && // numeric (0-9)
		        !(code > 64 && code < 91) && // upper alpha (A-Z)
		        !(code > 96 && code < 123)&& // lower alpha (a-z)
		        !(code == 32 || code==95)) {
		        alert('only Alphanumeric, under Score and space allowed in Permission Name.');
		      return false;
		    }
		  }
		  for (i = 0, len = str.length; i < len; i++) {
		    code = str.charCodeAt(i);
		    if (!(code > 47 && code < 58) && // numeric (0-9)
		        !(code > 64 && code < 91) && // upper alpha (A-Z)
		        !(code > 96 && code < 123)&& // lower alpha (a-z)
		        !(code == 32 || code==95)) {
		        alert('only Alphanumeric, under Score and space allowed in Description.');
		      return false;
		    }
		  }
		  
		  return true;
	}
</script>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/permissions/updatePermission" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Edit Permission</b></font><br /><br />
				</td>
			</tr>
			
		</table>
		<table class="tblFormData">
			<tr>
				<td><label>Permission Name</label></td>
				<td>
					<spring:bind path="name">
			              <form:input path="name" id="permissionName" autocomplete="off" pattern="[0-9a-zA-Z_ ]*" title="only Alphanumeric and under Score" size="30" type="text" class="input-large" placeholder="Permission Name" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Description</label></td>
				<td>
					<spring:bind path="description">
			              <form:textarea path="description" id="permissionDesc" autocomplete="off" size="30" type="textarea" class="input-large" placeholder="Description"/>
			              
			        </spring:bind>
				</td>
			</tr>
			
			<tr>
				<td><label>Display Name</label></td>
				<td>
					<spring:bind path="aliasName">
			              <form:input path="aliasName" autocomplete="off" size="30" type="text" class="input-large" placeholder="Display Name" disabled="true" style="background-color=white;"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Status</label></td>
				<td>
					<spring:bind path="disabled">
			            <form:select id="status" path="disabled" style="color:#333333;">
						<form:option value="0" label="Activate"/>
						<form:option value="1" label="DeActivate"/>
					</form:select>
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
	        
	        <spring:bind path="id">
	              <form:hidden path="id" value="${idToBeUpdated}" />
	        </spring:bind>
	        <spring:bind path="disableStatus">
			             <form:hidden id="disableStatus" path="disableStatus" />
			        </spring:bind>
	        
			<tr>
				<td colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" class="btn btn-primary" value="Update" onclick="return validatePage()"/>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			<tr>
		</table>  
		
	</form:form>
	