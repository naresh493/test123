
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
			width: 700px;
			background-color: #f9f9f9;
			margin: 0 auto;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
		label{
			cursor:auto !important;
		}
	</style>
</head>
<body>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/reports/search"/>
  </c:set>
<form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>Users</b></font><br /><br />
			</td>
		</tr>
	</table>
	
<table align="center">
	<tr align="center">		
				
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idImport" path="imported">Imported</form:label>
				</td>
				
				<td >
					<form:select id="idImport" path="imported">
						<form:option value="" label="--Please Select--"/>
						<form:option value="Yes">Yes</form:option>
						<form:option value="No">No</form:option>
					</form:select>
				</td>
				<td style="text-align:right;padding-right:18px;" >
					<form:label  for="idActive" path="active">Active</form:label>
				</td>
				<td >
					<form:select id="idActive" path="active">
						<form:option value="" label="--Please Select--"/>
						<form:option value="Yes">Yes</form:option>
						<form:option value="No">No</form:option>
					</form:select>
				</td>
				</tr>
				<tr align="center">
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idProject" path="projectId">Projects</form:label>
				</td>
				
				<td>
					<form:select id="idProject" path="projectId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idTeam" path="teamId">Teams</form:label>
				
				</td>
				<td>
					<form:select id="idTeam" path="teamId">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${team_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				</tr>
				<tr align="center">
				<td style="text-align:right;padding-right:18px;" >
					<form:label for="idRole" path="roleId">Roles</form:label>
				</td>
				
				<td>
					<form:select id="idRole" path="roleId" width="1px">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${role_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-bottom: 10px;" onclick="return validatePage()"/>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/reports/userreports'"/>
				</td>
				<!-- <td>
					<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/reports/userreports'"/>
				</td> -->
			</tr>
</table>	
			
<table id="viewusers" class="table table-striped table-bordered table-hover" style="width:100%; word-break: break-all;" >
	<thead>
	
		<tr>
			<th width="100px">First Name</th>
			<th width="100px">Last Name</th>
			<th width="100px">Imported</th>
			<th width="100px">Active</th>
			<th>Role</th>
			<th>Projects</th>
			<th width="100px">Team</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty user_list}">
			<c:forEach items="${user_list}" var="item">
				<tr>
					<td>${item.firstName}</td>
					<td>${item.lastName}</td>
					<td>${item.imported}</td>
					
					<td>${item.active}</td>
					<td>${item.role}</td>
					<td>${item.projects}</td>
					<td>${item.team}</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty user_list}">
				<tr>
					<td colspan="7"  align="center"><font color="blue">---------* No users Available *---------</font></td>
					
				</tr>
			</c:if>
	</tbody>
</table><br />
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
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
  			$("table#viewusers").tablesorter({widthFixed: true, widgets: ['zebra']})
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
		function validatePage(){
			var importid = document.getElementById('idImport').value;
			var activeid = document.getElementById('idActive').value;
			var idProject = document.getElementById('idProject').value;
			var idTeam = document.getElementById('idTeam').value;
			var idRole = document.getElementById('idRole').value;
			
			if((importid == null || importid == '') && (activeid == null || activeid == '') && (idProject == null || idProject == '') && (idTeam == null || idTeam == '') && (idRole == null || idRole == '')){
				alert('Please select atleast a option to search');
				return false;
			}else{
				return true;
			}
		}
	</script>
	</form:form>
</body>
</html>