<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/configuration/savemobileconfiguration" />
  </c:set>
  <script type="text/javascript">
  	function validatepage(){
  		var name = document.getElementById('configName').value;
  		if(name.trim() == null || name.trim() == ""){
  			alert('Please enter the name');
  			return false;
  		}else{
  			return true;
  		}
  	}
  </script>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Mobile Configuration</b></font><br /><br />
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
		<tr>
				<td>
					<label>Name <font color='red' size='3'>*</font></label>
				</td>
				<td>
					<spring:bind path="configurationname">
			              <form:input id="configName" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="configurationname" size="30" type="text" class="input-large" placeholder="Configuration Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
		
			<tr>
				<td><label>Application Executable File Path <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="path">
			              <form:input path="path" size="30" type="url" class="input-large" placeholder="Url" required="true"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Element Wait Time To Load </label></td>
				<td>
					<spring:bind path="elementwaittime">
			               <form:input path="elementwaittime" size="30" pattern="[0-9]*" title="Only Numbers" type="text" class="input-large" placeholder="Wait Time" />
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td>
					<form:label for="projects" path="projectid">Select Project<font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<form:select id="projects" path="projectid" required="true">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>
			<spring:bind path="typeEntpc">
				<form:hidden path="typeEntpc" value="mobile"/>
			</spring:bind>
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
					&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
					&nbsp; &nbsp; &nbsp;
					<c:if test="${empty crudObj.id}">
					  	 <input type="submit" class="btn btn-primary" value="Save" onclick="return validatepage()"/>
					</c:if>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		       		<input type="reset" class="btn btn-primary" value="Reset" />
		        </td>
			<tr>
		</table>
	</form:form>
	