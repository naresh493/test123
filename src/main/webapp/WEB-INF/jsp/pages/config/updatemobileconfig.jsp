 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/configuration/updatemobileconfig" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Update Mobile Configuration</b></font><br /><br />
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
					<form:label for="configName" path="configurationname">Name <font color='red' size='3'>*</font></form:label>
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
			              <form:input path="elementwaittime" size="30" type="text" title="Only Numbers" class="input-large" placeholder="elementwaittime" />
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td>
					<form:label for="projects" path="projectid">Select Project<font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<form:select id="projects" path="projectid" required="true">
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
	