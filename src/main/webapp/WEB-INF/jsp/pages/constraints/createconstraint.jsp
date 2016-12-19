<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<style type="text/css">
label{
cursor: auto !important;
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
	 <c:url value="/content/constraints/save" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Create Control</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty constraintcreated}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${constraintcreated}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			
		</table>
		<table id="tblCreateProject" class="tblFormData">
			
			<tr>
					<td><label>Control Name<font color='red'>*</font></label></td>
				<td>
					<spring:bind  path="name">
			              <form:input id="constraintname" autocomplete="off" pattern="[0-9a-zA-Z_ ]*" title="only Alphanumeric and under Score" path="name" size="30" type="text" class="input-large" placeholder="Control Name" required="true"/>
			              
			        </spring:bind>
				</td>
			</tr>
			<tr>
				<td>
					<form:label for="description" path="description">Description</form:label>
				</td>
				<td>
					<spring:bind  path="description">
			              <form:textarea id="constDesc" maxlength="255" path="description" rows="3" cols="28" type="text" class="input-large"  placeholder="Description  (Maximum 255 Characters)"/>
			              
			        </spring:bind>
				</td>
			</tr>
			
			
			<spring:bind path="createdBy">
	              <form:hidden path="createdBy" value="${sessionScope.userid}" />
	        </spring:bind>
	        
	        <spring:bind path="createdDate">
	              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
	        </spring:bind>
	        
	       <tr>
				<td colspan="3" align="center">
					
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="Create" onclick="return validatepage()"/>
		       		<input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
					<input type="reset" class="btn btn-primary" value="Reset" />
		        </td>
			</tr>
			
		</table>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
			
	<script type="text/javascript" src="<%= request.getContextPath() %>/dwr/engine.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/dwr/interface/DWRService.js"></script>
    <script type="text/javascript" src="<%= request.getContextPath() %>/dwr/util.js" ></script>
		 
	</form:form>
	