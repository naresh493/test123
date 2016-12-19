 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/configuration/updatePerformance" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Edit Performance Configuration</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty configurationupdated}'> 
				<tr>
					<td colspan="2" align="center"><b>${configurationupdated}</b><br /><br />
				</td>
			</tr>
			</c:if>
		</table>
		<table class="tblFormData">
		<tr>
				<td>
					<form:label for="configName" path="name">Name <font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind path="name">
			              <form:input id="configName" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="name" size="30" type="text" class="input-large" placeholder="Configuration Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
		
			<tr>
				<td><label>Application Executable File Path <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="url">
			              <form:input path="url" size="30" type="url" class="input-large" placeholder="Url" required="true"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			
			<tr>
				<td><label>Port Number <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="port">
			              <form:input path="port" title="only numerics" size="30" type="text" class="input-large" placeholder="Url" required="true"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Element Wait Time To Load </label></td>
				<td>
					<spring:bind path="waitTime">
			               <form:input path="waitTime" size="30" pattern="[0-9]*" title="Only Numbers" type="text" class="input-large" placeholder="Wait Time" />
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td>
					<form:label for="projects" path="projid">Select Project<font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<form:select id="projects" path="projid" required="true">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>
				
				

							<spring:bind path="id">
	              <form:hidden path="id"/>
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
					<input type="submit" class="btn btn-primary" value="Update" />
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			<tr>
		</table>
	</form:form>
	