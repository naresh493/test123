<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>
<style type="text/css">
label{
cursor: default !important;
}
</style>
<style type="text/css">
	    select {
	        width: 200px;
	        float: left;
	    }
	    .controls {
	        width: 40px;
	        margin: 10px;
	    }
	    .controls a {
	        background-color: #222222;
	        border-radius: 4px;
	        border: 2px solid #000;
	        color: #ffffff;
	        padding: 2px;
	        font-size: 14px;
	        text-decoration: none;
	        display: inline-block;
	        text-align: center;
	        margin: 5px;
	        width: 20px;
	    }
    </style>
<script type="text/javascript">
	function validatePage(){
		var modId = document.getElementById('idModule').value;
		if(modId == null || modId == "") {
			alert("Please Select a Package");
			return false;
		} else{
			$("#to option").prop("selected", "selected");
	    	$("#signup").submit();
			return true;
		}
	}
</script>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/rolemodule/saveassignroletomodule" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Assign Roles To Package</b></font><br /><br />
				</td>
			</tr>
			<%-- <c:if test='${not empty roleassigned}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${roleassigned}</b></font><br /><br />
					</td>
				</tr>
			</c:if> --%>
			<c:if test='${not empty displayMessage}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${displayMessage}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		
		<c:if test="${not empty module_list}">
		
		
		
		<table id="tblCreateProject" class="tblFormData">
		
			
				<tr>
				<td align="center">
					<form:label for="moduleList" path="moduleId">Select A Package<br /><font color="green" size="2"></font></form:label>
				</td>
				<td>
					<form:select id="idModule" required="true" path="moduleId" style="color:#333333" onchange="getRoleBasedLicenses('rolemodule');getRoleBasedAvalableLicenses('rolemodule')">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${module_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>
				</table>
				<table align="center">
					<tr align="center" >
					
					</table>
					<table align="center">
					<tr align="center">
					<td>
					<table align="center" >
					<tr id="traPermissions" >
					<td >
							<div><font size="2">Available Roles</font></div>
							<form:select id="from"  for="permId" path="roles" multiple="multiple" style="width:177px;height:155px;">
								<form:options itemValue="id" />
							</form:select>
						</td>
						</tr>
					</table>
					</td>
					
					<td>
						<div class="controls"> 
					        <a href="javascript:moveAll('from', 'to')">&gt;&gt;</a> 
					        <a href="javascript:moveSelected('from', 'to')">&gt;</a> 
					        <a href="javascript:moveSelected('to', 'from')">&lt;</a> 
					        <a href="javascript:moveAll('to', 'from')" href="#">&lt;&lt;</a> 
					    </div>
					</td>
					<td>
					
					<table>
					<tr id="trPerm" >
						<td colspan="2" >
						<div><font size="2">Assigned Roles</font></div>
        						<form:select id="to" for="permaId" path="roleIds" multiple="multiple" style="width:177px;height:155px;">
									<form:options itemValue="id"/>
								</form:select>
   						 	</td>
   						 </tr>
					</table>
					</td>
										
					</tr>
				
				</table>
			
			<table align="center">
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
			<br>
				<td colspan="2" align="center">
					
					 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Save" onclick="return validatePage()"/>&nbsp;
					 <input type=button name="cancel" class="btn btn-primary" value="Cancel" onClick="location.href='/QlikTestAdmin/content/cancel'"/>
		        </td>
			<tr>
		</table>
		</c:if>
		 <c:if test="${empty module_list}">
		 <table class="tblHeader-inner">
				<tr>
					<td colspan="2" align="center"><font color=blue>---------*No Packages Available*---------</font></td>
					
				</tr>
				</table>
				
		</c:if>
		<script>
    function moveAll(from, to) {
        $('#'+from+' option').remove().appendTo('#'+to); 
    }
    
    function moveSelected(from, to) {
        $('#'+from+' option:selected').remove().appendTo('#'+to); 
    }
    </script> 
   
		</form:form>