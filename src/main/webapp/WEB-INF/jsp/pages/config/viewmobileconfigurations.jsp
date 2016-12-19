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
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>View Mobile Configurations</b></font><br /><br />
			</td>
		</tr>
	</table>		
<table id="viewusers" class="table table-striped table-bordered table-hover" >
	<thead>
		<tr>
			<th>Name</th>
			<th>Application Executable File Path</th>
			<th>Element Wait Time To Load</th>
			<th>Created Date</th>
			<th>Modifed Date</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty mobile_configs}">
			<c:forEach items="${mobile_configs}" var="item">
				<tr>
					<td>${item.configurationname}</td>
					<td>${item.path}</td>
					<td>${item.elementwaittime}</td>
					<td>${item.createdDate}</td>
					<td>${item.modifiedDate}</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table><br />
<c:if test="${fn:length(mobile_configs)>10}">
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
	</script>
	</c:if>
</body>
</html>