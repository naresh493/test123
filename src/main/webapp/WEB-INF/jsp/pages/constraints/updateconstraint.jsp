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
	function validatepage(){
		var namewspaces = document.getElementById('constraintname').value;
		var name = namewspaces.trim();
		if(namewspaces.length != name.length){
			alert('Please enter the name with out leading and tailing spaces');
			return false;
		}else if(name == "" || name == null){
			alert('Please enter the name');
			return false;
		}
		var code, i, len;
		var str =  document.getElementById('constDesc').value;
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
	 <c:url value="/content/constraints/update" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Edit Control</b></font><br /><br />
				</td>
			</tr>
			
		</table>
		<table class="tblFormData">
			<tr>
				<td><label>Control Name</label></td>
				<td>
					<spring:bind path="name">
			              <form:input path="name" id="constraintname" autocomplete="off" pattern="[0-9a-zA-Z_ ]*" title="only Alphanumeric and under Score" size="30" type="text" class="input-large" placeholder="Constraint Name" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td><label>Description</label></td>
				<td>
					<spring:bind path="description">
			              <form:textarea path="description" id="constDesc" autocomplete="off" size="30" type="textarea" class="input-large" placeholder="Description" required="true"/>
			              
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
	        
			<tr>
				<td colspan="2">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" class="btn btn-primary" value="Update" onclick="return validatepage()"/>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			<tr>
		</table>  
		
	</form:form>
	