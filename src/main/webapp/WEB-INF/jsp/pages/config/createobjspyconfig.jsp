<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<script type="text/javascript">
	function browserName(){
		var browser = document.getElementById('browser').value;
		if(browser == "chrome") {
			$('#trVersion').show();
		} else {
			$('#trVersion').hide();
		}
	}
</script>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/configuration/saveobjspy" />
  </c:set>
	<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create Web Object Editor Configuration</b></font><br /><br />
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
					<form:label for="configName" path="configurationname">Name <font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<spring:bind path="configurationname">
			              <form:input id="configName" pattern="[a-zA-Z0-9_]*" title="Only Alphabets,Numbers and Underscore" path="configurationname" size="30" type="text" class="input-large" placeholder="Configuration Name" required="true"/>
			             
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="projects" path="projectId">Select Project<font color='red' size='3'>*</font></form:label>
				</td>
				<td>
					<form:select id="projects" path="projectId" required="true">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>
			
			<tr>
				<td><label>Browser <font color='red' size='3'>*</font></label></td>
				<td>
					<form:select id="browser" path="browser" required="true" style="color:#333333" onchange="browserName()">
						<form:option value="" label="--Please Select Browser--"/>
						<form:option value="firefox" label="FireFox"/>
						<form:option value="iexplorer" label="InternetExplorer"/>
						<form:option value="chrome" label="GoogleChrome"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td><label>Port Number <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="portNumber">
			              <form:input path="portNumber" pattern="[0-9]*" title="only numerics" size="30" type="text" class="input-large" placeholder="Port" required="true"/>
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Element Wait Time </label></td>
				<td>
					<spring:bind path="elementwaittime">
			              <form:input path="elementwaittime" size="30" pattern="[0-9]*" title="Only Numbers" type="text" class="input-large" placeholder="Wait Time" />
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr>
				<td><label>Url <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="url">
			              <form:input path="url" size="30" type="url" class="input-large" placeholder="Url" required="true"/>
			             
			        </spring:bind>
			    </td>
			</tr>
			<tr id="trVersion">
				<td><label>Chrome Version <font color='red' size='3'>*</font></label></td>
				<td>
					<spring:bind path="version">
			              <form:input path="version" type="number" class="input-large" placeholder="Version" max="46" min="29"/>
			        </spring:bind>
				</td>
				<td>Supported Versions 29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46</td>
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
					&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
					&nbsp; &nbsp; &nbsp;
					<c:if test="${empty crudObj.id}">
					  	 <input type="submit" class="btn btn-primary" value="Save" onclick="return browserValidation()"/>
					</c:if>
					<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		       		<input type="reset" class="btn btn-primary" value="Reset" />

		        </td>
			<tr>
		</table>
		<script type="text/javascript">
		function browserValidation(){
			var name = document.getElementById('browser').val;
			if(name !=null || name == ""){
				alert('Please select a browser');
				return false;
			}else{
				return true;
			}
		}
			
		</script>
		
	</form:form>
	