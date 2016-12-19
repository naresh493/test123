 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/configuration/updateprlnodes" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Edit Parallel Nodes Configuration</b></font><br /><br />
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
					<form:label for="prlnode" path="nodeurl">Node Path <font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind path="nodeurl">
			              <form:input id="prlalnod" pattern="[a-zA-Z0-9_.:/]*" title="Only Alphabets,Numbers and Underscore" path="nodeurl" size="30" type="url" class="input-large" placeholder="Node Path" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			
			<tr>
				<td><label>Platform <font color='red' size='3'>*</font></label></td>
				<td>
					<form:select id="platform" path="platform" style="color:#333333">
						<form:option value="" label="--Please Select Platform--"/>
						<form:option value="WINDOWS-XP-ie6" label="WINDOWS-XP-IE6"/>
						<form:option value="WINDOWS-XP-ie7" label="WINDOWS-XP-IE7"/>
						<form:option value="WINDOWS7-ie8" label="WINDOWS7-IE8"/>
						<form:option value="WINDOWS7-ie9" label="WINDOWS7-IE9"/>
						<form:option value="WINDOWS7-ie10" label="WINDOWS7-IE10"/>
						<form:option value="WINDOWS8-ie11" label="WINDOWS8-IE11"/>
						<form:option value="WINDOWS8-ie12" label="WINDOWS8-IE12"/>
						<form:option value="LINUX" label="LINUX"/>
					</form:select>
				</td>
			</tr>
			
			<tr>
				<td><label>Ip Address <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="ipAddress">
			              <form:input path="ipAddress" size="30" pattern="[a-zA-Z0-9_.]*" title="Only Alphabets,Numbers and Underscore" type="text" class="input-large" placeholder="Wait Tile" required="true"/>
			             
			        </spring:bind>
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
	