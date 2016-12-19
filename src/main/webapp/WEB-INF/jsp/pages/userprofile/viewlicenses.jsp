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
			width: 500px;
			background-color: #f9f9f9;
			margin: 0 auto;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
	</style>
</head>
<body>
<form:form class="form-viewmodule" commandName="crudObj" action="${pageUrl}" method="GET">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					View Packages</b></font><br /><br />
			</td>
		</tr>
	</table>
	<table id="viewmodule" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Name</th>
				<th align="center">Description</th>
				<!-- <th align="center">No.of Assigned Permissions</th>
				<th align="center">No.of Assigned Roles</th>
				<th align="center">No. of Assigned Reports</th>  -->
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty module_list}">
				<c:forEach items="${module_list}" var="item">
					<tr>
						<td align="center">${item.name}</td>
						<td align="center">${item.description}</td>
						<%-- <td align="center"><a href="#" onclick="openModal('Permissions','${item.permissionNames}')">${item.noOfPermissions}</a></td>
						<td align="center"><a href="#" onclick="openModal('Roles','${item.roleNames}')">${item.noOfRoles}</a></td>
					    <td align="center"><a href="#" onclick="openModal('Reports','${item.reportNames}')">${item.noOfReports}</a></td> --%>
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
<c:if test="${fn:length(user_list)>10}">
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
		<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
  			$("table#viewmodule").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center><p style='margin-left:240px;'>"+data+"</p>");
			
		}
		
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
	</script>
</c:if>	
</form:form>
</body>
</html>