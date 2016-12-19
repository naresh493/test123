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
			width: 900px;
			background-color: #f9f9f9;
			margin: 0 auto;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
	</style>
	<script type="text/javascript">
		function validatePage(){
			var roleId = document.getElementById('idRoles').value;
			var permId = document.getElementById('idPermission').value;
			if((roleId == null || roleId == "") && (permId == null || permId == "")) {
				alert('Please Select atleast one option to search');
				return false;
			} else {
				return true;
			}
			
		}
	</script>
	
</head>
<body>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/role/searchrole"/>
</c:set>
<form:form class="form-viewteams" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="left"><font size="3"><b>
					<center>View Roles</center></b></font><br /><br />
			</td>
		</tr>
	</table>
	<table align="center">
		<tr>
			<td>
				<form:label for="name" path="id">Select Role</form:label>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
			<td>	
				<form:select id="idRoles" path="id" style="color:#333333">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${rolenames_list}" itemLabel="name" itemValue="id" />
				</form:select>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
			
			<td>
				<form:label for="name" path="permissionId">Select Permission</form:label>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
			<td>	
				<form:select id="idPermission" path="permissionId" style="color:#333333">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${permissions_list}" itemLabel="name" itemValue="id" />
				</form:select>
			</td>
			<td>
			&nbsp;&nbsp;&nbsp;
			</td>
			
			<td>
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-top:-10px;" onclick="return validatePage()"/>
				<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/role/viewrole'"/>
			</td>
		</tr>
	</table>
	<br>
	<table id="viewroles" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Role Name</th>
				<th align="center">Description</th>
				<th align="center">Role And Responsibility</th>
				<th align="center">Permission</th>
				<th align="center">Created Date</th>
				<th align="center">Modified Date</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty role_list}">
				<c:forEach items="${role_list}" var="item">
				
					<tr>
						<td align="center">${item.name}</td>
						<td align="center">${item.description}</td>
						<td align="center">${item.roleAndResponsibility}</td>
						<c:if test="${item.permissionsCount eq 0}">
							<td align="center">${item.permissionsCount}</td>
						</c:if>
						<c:if test="${item.permissionsCount>0}">
							<td align="center"><a href="#" onclick="openModal('Available Permissions','${item.permissionsName}')">${item.permissionsCount}</a></td>
						</c:if>
						<td align="center">${item.createdDate}</td>
						<td align="center">${item.modifiedDate}</td>
					</tr>
					
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	 
<br />
<c:if test="${empty role_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="5" align="center"><font color=blue>---------*No Roles Available*---------</font></td>
					
				</tr>
				</table>
	</c:if>
<c:if test="${fn:length(role_list)>10}">
	<div id="pager" class="pagination">
		<ul>
			<li class="prev first"><a href="#" onclick="setTimeout('updatePage()',100);">&lt;&lt; First</a></li>
			<li class="prev"><a href="#" onclick="setTimeout('updatePage()',100);">&lt; Prev</a></li>
			<li class="active"><a href="#" id="curPageNumb">page</a></li>
			<li class="next"><a href="#" onclick="setTimeout('updatePage()',100);">Next &gt;</a></li>
			<li class="next last"><a href="#" onclick="setTimeout('updatePage()',100);">Last &gt;&gt;</a></li>
		</ul>
		<input type="hidden" class="pagedisplay" id="disp" value="5" />
		<input type="hidden" class="pagesize" value="10" />
	</div>
	</c:if>
	<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<script type="text/javascript">
		 $(document).ready(function(){
  			$("table#viewroles").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		}); 
		/* function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		} */
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
			 var pageNum = document.getElementById('disp').value;
			var res = pageNum.split("/");
			if(res[0]==res[1]){
				$('.next').addClass("active");
			}else{
				$('.next').removeClass("active");
			}
			if(res[0]==1){
				$('.prev').addClass("active");
			}else{
				$('.prev').removeClass("active");
			}
			
		}
		function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center><p style='margin-left:240px;'>"+data+"</p>");
			
		}
	</script>
	</form:form>

</body>
</html>