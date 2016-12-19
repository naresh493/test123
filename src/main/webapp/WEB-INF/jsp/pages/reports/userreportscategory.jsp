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
	</style>
</head>
<body>
<c:set var="pageUrl" scope="request">
	 <c:url value="/content/reports/modifysearch"/>
  </c:set>
	<form:form id="signup" class="form-createuser" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>View Users</b></font><br /><br />
			</td>
		</tr>
	</table>		
<table id="viewusers" class="table table-striped table-bordered table-hover" >
	
	<thead>
	 <tr>
				<td colspan="2" align="center">
					
					  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="ModifySearch"/>&nbsp;
		        </td>
			<tr>
		<tr>
			<th>Id</th>
			<th>First Name</th>
			<th>Sur Name</th>
			<th>Email Address</th>
			<th>Landline</th>
			<th>Mobile</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty user_list}">
			<c:forEach items="${user_list}" var="item">
				<tr>
					<td>${item.id}</td>
					<td>${item.firstName}</td>
					<td>${item.surName}</td>
					<td colspan="0">${item.emailAddress}</td>
					<td>${item.landline}</td>
					<td>${item.mobile}</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table><br />
	<div id="pager" class="pagination">
		<ul>
			<li class="prev first"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt;&lt; First</a></li>
			<li class="prev"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt; Prev</a></li>
			<li class="active"><a href="#" id="curPageNumb">page</a></li>
			<li class="next"><a href="#" onmouseup="setTimeout('updatePage()',500);">Next &gt;</a></li>
			<li class="next last"><a href="#" onmouseup="setTimeout('updatePage()',500);">Last &gt;&gt;</a></li>
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
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
	</script>
	</form:form>
</body>
</html>