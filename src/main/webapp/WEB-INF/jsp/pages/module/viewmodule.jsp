<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<html>
<head>
	<style>
		table.table.table-striped{
			height: 100px;
			width: 930px;
			background-color: #f9f9f9;
			margin: 0 auto;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
	</style>
	<style type="text/css">
		label{
		cursor: auto !important;
		}
	</style>
<c:url var="firstUrl" value="/content/module/viewpagemodule/0" />
<c:url var="lastUrl" value="/content/module/viewpagemodule/${numberofpages - 1}" />
<c:url var="prevUrl" value="/content/module/viewpagemodule/${currentpage - 1}" />
<c:url var="nextUrl" value="/content/module/viewpagemodule/${currentpage + 1}" />	
<script type="text/javascript">
		function validatePage() {
			var name = document.getElementById('name').value;
			var permId = document.getElementById('idPermission').value;
			var roleId = document.getElementById('idRole').value;
			var reportId = document.getElementById('idReport').value;
			var controlId = document.getElementById('idControl').value;
			if((name == null || name == "") && (permId == null || permId == "")&&(roleId == null || roleId == "")&&
					(reportId == null || reportId == "")&&(controlId == null || controlId == "")){
				alert("Please select a option to search");
				return false;
			}else{
				return true;
			}
		}
	</script>
</head>
<body>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/module/searchmodules"/>
  </c:set>
<form:form class="form-viewmodule" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					View Packages</b></font><br /><br />
			</td>
		</tr>
	</table>
	<table align="center">
	<tr align="center">		
				
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idName" path="name">Name</form:label>
				</td>
				
				<td>	
					<form:input id="name" path="name" autocomplete="off" type = "text" placeholder="Package Name" />
				</td>
				<td style="text-align:right;padding-right:18px;" >
					<form:label  for="idPermission" path="permissionId">Permission</form:label>
				</td>
				<td >
					<form:select id="idPermission" path="permissionId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${permissions_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>
				<tr align="center">
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idRole" path="roleId">Role</form:label>
				</td>
				
				<td>
					<form:select id="idRole" path="roleId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${roles_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idReport" path="reportId">Report</form:label>
				
				</td>
				<td>
					<form:select id="idReport" path="reportId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${reports_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>
				<tr align="center">
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idControl" path="conId">Control</form:label>
				</td>
				
				<td>
					<form:select id="idControl" path="conId" width="1px">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${constraints_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td style="text-align:right;padding-right:18px;">
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" onclick="return validatePage()"/>&nbsp;
				</td>
				
			</tr>
</table>	
	<table id="viewmodule" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Name</th>
				<th align="center">Description</th>
				<th align="center">No.of Assigned Permissions</th>
				<th align="center">No.of Assigned Roles</th>
				<th align="center">No. of Assigned Reports</th> 
				<th align="center">No. Of Controls</th>
				<th align="center">Created Date</th>
				<th align="center">Modified Date</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty module_list}">
				<c:forEach items="${module_list}" var="item">
					<tr>
						<td align="center">${item.name}</td>
						<td align="center">${item.description}</td>
						<c:if test="${item.noOfPermissions eq 0}">
							<td align="center">${item.noOfPermissions}</td>
						</c:if>
						<c:if test="${item.noOfPermissions>0}">
							<td align="center"><a href="#" onclick="openModal('Assigned permissions','${item.permissionNames}')">${item.noOfPermissions}</a></td>
						</c:if>
						
						<c:if test="${item.noOfRoles eq 0}">
							<td align="center">${item.noOfRoles}</td>
						</c:if>
						<c:if test="${item.noOfRoles>0}">
							<td align="center"><a href="#" onclick="openModal('Assigned roles','${item.roleNames}')">${item.noOfRoles}</a></td>
						</c:if>
						
						<c:if test="${item.noOfReports eq 0}">
							<td align="center">${item.noOfReports}</td>
						</c:if>
						<c:if test="${item.noOfReports>0}">
							<td align="center"><a href="#" onclick="openModal('Assigned reports','${item.reportNames}')">${item.noOfReports}</a></td>
						</c:if>
						
						<c:if test="${item.noOfControls eq 0}">
							<td align="center">${item.noOfControls}</td>
						</c:if>
						<c:if test="${item.noOfControls>0}">
							<td align="center"><a href="#" onclick="openModal('Assigned Controls','${item.controleNames}')">${item.noOfControls}</a></td>
						</c:if>
					    <td align="center">${item.createdDate}</td>
					    <td align="center">${item.modifiedDate}</td>
					</tr>
				</c:forEach>
			</c:if>
			
		</tbody>
	</table>
	<c:if test="${empty module_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="2" align="center"><font color=blue>---------*No Packages Available for This User*---------</font></td>
					
				</tr>
				</table>
				</c:if>
<br />
</form:form>
<c:if test="${numberofpages>0}">
	<div class="pagination" align="center">
	    <ul>
	        <c:choose>
	            <c:when test="${currentpage == 0}">
	                <li class="disabled"><a href="#">&lt;&lt;First</a></li>
	                <li class="disabled"><a href="#">&lt;Previous</a></li>
	            </c:when>
	            <c:otherwise>
	                <li><a href="${firstUrl}">&lt;&lt;First</a></li>
	                <li><a href="${prevUrl}">&lt;Previous</a></li>
	            </c:otherwise>
	        </c:choose>
	        <c:choose>
	        	<c:when test="true">
	        		<li class="disabled"><a>Page<c:out value="${currentpage+1}"></c:out>/<c:out value="${numberofpages}"></c:out></a>
	        	</c:when>
	        </c:choose>
	        <c:choose>
	            <c:when test="${currentpage+1 == numberofpages}">
	                <li class="disabled"><a href="#">Next&gt;</a></li>
	                <li class="disabled"><a href="#">Last&gt;&gt;</a></li>
	            </c:when>
	            <c:otherwise>
	                <li><a href="${nextUrl}">Next&gt;</a></li>
	                <li><a href="${lastUrl}">Last&gt;&gt;</a></li>
	            </c:otherwise>
	        </c:choose>
	    </ul>
	</div>
</c:if>	
	<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<script type="text/javascript">
		function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center>"+data);
			
		}
	</script>
</body>
</html>