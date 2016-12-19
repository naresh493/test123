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
	        float: left;
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
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/tenant/saveprojectstotenants" />
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
		<table class="tblHeader-inner">
			<tr>
				<td colspan="2" align="center"><font size="3"><b>Assign Project To Tenant</b></font><br /><br />
				</td>
			</tr>
			<c:if test='${not empty projectsaddedtotenants}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${projectsaddedtotenants}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
		</table>
		
		<c:if test="${not empty tenant_list}">
		
		
		
		<table id="tblCreateProject" class="tblFormData">
		
				<tr>
				<td>
					<form:label for="tenantList" path="tenantId">Select A Tenant<font color='red'>*</font><br /><font color="green" size="2"></font></form:label>
				</td>
				<td>
					<form:select id="tenantList" required="true" path="tenantId" style="color:#333333" onchange="getTenantBasedProject('tenant');getTenantNotInBasedProject('tenant')">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${tenant_list}" itemLabel="name" itemValue="id" />
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
					<tr id="trProjects" >
					<td >
							<div><font size="2">Available Projects</font></div>
							<form:select id="from"  for="permId" path="projects" multiple="multiple" style="width:177px;height:155px;">
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
					<tr id="trAProjects" >
						<td colspan="2" >
						<div><font size="2">Assigned Projects</font></div>
        						<form:select id="to" for="permaId" path="projectId" multiple="multiple" style="width:177px;height:155px;">
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
			<br>
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
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					
					 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Save" onclick="selectAllValues()"/>&nbsp;
					 <a href=<%=request.getContextPath() %>/content/cancel>
 							<input type="button" name="cancel" class="btn btn-primary" value="Cancel" />
						</a>
		        </td>
			<tr>
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