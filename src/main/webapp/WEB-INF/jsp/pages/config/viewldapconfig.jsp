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
</head>
<body>

<form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					LDap Configurations</b></font><br /><br />
			</td>
		</tr>
	</table>
	
	<br/>
	<table id="viewldapconfigs" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Name</th>
				<th align="center">IPAddress</th>
				<th align="center">Port</th>
				<th align="center">Base</th>
				<th align="center">UserName</th>
				<th align="center">Tenant Name</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty ldapconfiglist}">
				<c:forEach items="${ldapconfiglist}" var="item">
					<tr>
						<td align="center">${item.name}</td>
						<td align="center">${item.serverip}</td>
						<td align="center">${item.port}</td>
						<td align="center">${item.base}</td>
						<td align="center">${item.username}</td>
						<td align="center">${item.tenantName}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
<br />
<c:if test="${fn:length(ldapconfiglist)>10}">
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
	
	
	<link type="text/css" href='<%=request.getContextPath()%>/static/css/jquery-ui.css' rel="stylesheet">
	<script type="text/javascript">
		
		$(document).ready(function(){
  			$("table#viewldapconfigs").tablesorter({widthFixed: true, widgets: ['zebra']})
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
		
		</script>
		</c:if>
		<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
		<script type="text/javascript">
		function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center><p style='margin-left:240px;'>"+data+"</p>");
			
		}
		
	</script>
</form:form>
</body>
</html>