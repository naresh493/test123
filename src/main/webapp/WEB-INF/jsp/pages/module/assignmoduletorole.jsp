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
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/module/saveassignedroles" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Assign Roles To License</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty moduleassigned}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${moduleassigned}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		
		<c:if test="${not empty module_list}">
		
		
		
		<table id="tblCreateProject" class="tblFormData">
		
				<tr>
				<td>
					<form:label for="moduleList" path="moduleId">Select A License<br /><font color="green" size="2"></font></form:label>
				</td>
				<td>
					<form:select  id="idModule" path="moduleId" required="true"  style="color:#333333" onchange="getRoleModule('module');getRoleModule1('module')">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${module_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>

				</table>
				<table align="center">
					<tr align="center" >
					<tr align="center" >
						<td >
							<form:label id="idRole"  path="roleId">Available Roles<br /><font color="green" size="2"></font></form:label>
						</td>
						
						<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<form:label id="idRole"  path="roleId">Selected Roles<br /><font color="green" size="2"></font></form:label>
						</td>
					</tr>
					</table>
					<table align="center">
					<tr align="center">
					<td>
					<table align="center" >
					<tr id="Reports" >
					<td >
							<form:select id="idReport1"  for="idReport" path="roleId" multiple="multiple">
								<form:options itemValue="id" />
							</form:select>
						</td>
						</tr>
					</table>
					</td>
					<td>
					<table align="center">
			
				<tr align="center" >

    <td ><input id="moveright" type="button" value=" >  "  onclick="move_list_items('idReport1','idReport');" /></td></tr>
    
    <tr align="center" ><td ><input id="moverightall" type="button"   value=" >>" onclick="move_list_items_all('idReport1','idReport');" /></td></tr>
    
  <tr align="center" ><td ><input id="moveleft" type="button"  value="  < "onclick="move_list_items('idReport','idReport1'); " /></td></tr>
    
    <tr align="center" ><td><input id="moveleftall" type="button"  value=" << "  onclick="move_list_items_all('idReport','idReport1'); " /></td><tr>
</tr>

</table>
					
					</td>
					<td>
					
					<table>
					<tr id="trReports" >
						<td colspan="2" >
        						<form:select id="idReport" for="idReport" path="roleId" multiple="multiple" required="true" style="width:250px;height:70px;">
									<form:options itemValue="id"/>
								</form:select>
   						 	</td>
   						 </tr>
					</table>
					</td>
										
					</tr>
				
				</table>
				 
					
				
				
				
				
			<table>
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
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Assign" onclick="return CheckBoxValidation()"/>&nbsp;
					 <a href=<%=request.getContextPath() %>/content/cancel>
 							<input type="button" name="cancel" class="btn btn-primary" value="Cancel" />
						</a>
		        </td>
			<tr>
		</table>
		</c:if>
		 <c:if test="${empty module_list}">
		 <table class="tblHeader-inner">
				<tr>
					<td colspan="2" align="center"><font color=blue>---------*No Modules Available*---------</font></td>
					
				</tr>
				</table>
				
				</c:if>
		 
		 <script type="text/javascript">
			 function move_list_items(sourceid, destinationid)
			  {
			    $("#"+sourceid+"  option:selected").appendTo("#"+destinationid);
			  }
			 function move_list_items_all(sourceid, destinationid)
			  {
			    $("#"+sourceid+" option").appendTo("#"+destinationid);
			  }
		 </script>
		 
		</form:form>