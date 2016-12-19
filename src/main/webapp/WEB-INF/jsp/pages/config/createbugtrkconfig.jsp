<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/configuration/savebugtrk" />
  </c:set>
  <script type="text/javascript">
  	function validatepage(){
  		var name = document.getElementById('configName').value;
  		if(name.trim() == null || name.trim() == ""){
  			alert('Please enter name');
  			return false;
  		}else{
  			return true;
  		}
  	}
  </script>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create Bug Tracking Configuration</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty configurationcreated}'> 
				<tr>
					<td colspan="2" align="center">
						<b>${configurationcreated}</b><br /><br />
					</td>
				</tr>
			</c:if>
			
		</table>
		<table class="tblFormData">
		<tr align="center">
				<td>
					<label>Name <font color='red' size='3'>*</font></label>
				</td>
				<td>
					<spring:bind path="configurationname">
			              <form:input id="configName" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="configurationname" size="30" type="text" class="input-large" placeholder="Configuration Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			<tr align="center">
				<td><label>Url<font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="url">
			              <form:input path="url" size="30" type="url" class="input-large" placeholder="Url" required="true"/>
			             
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
					  	 <input type="submit" class="btn btn-primary" value="Save" onclick="return validatepage()"/>
					</c:if>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		       		<input type="reset" class="btn btn-primary" value="Reset" />

		        </td>
			<tr>
		</table>
		
		
	</form:form>
	