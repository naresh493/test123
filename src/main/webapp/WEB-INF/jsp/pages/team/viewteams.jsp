<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Date" %>

<html>
<head>
	<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<link type="text/css" href='<%=request.getContextPath()%>/static/css/jquery-ui.css' rel="stylesheet">
	<script type="text/javascript">
		/* $(document).ready(function(){
  			$("table#viewteams").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		} */
		function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center><p style='margin-left:240px;'>"+data+"</p>");
			
		}
	</script>
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

<form:form class="form-viewteams" commandName="crudObj" action="${pageUrl}" method="GET">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					View Teams</b></font><br /><br />
			</td>
		</tr>
	</table>
	<table id="viewteams" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Team Name</th>
				<th align="center">Created By</th>
				<th align="center">No Of Users</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty view_team}">
				<c:forEach items="${view_team}" var="item">
				
					<tr>
						<td align="center">${item.name}</td>
						<td align="center">${item.createdBy}</td>
						<c:if test="${item.count eq 0}">
							<td align="center">${item.count}</td>
						</c:if>
						<c:if test="${item.count>0}">
							<td align="center"><a href="#" onclick="openModal('Available Users','${item.userName}')">${item.count}</a></td>
						</c:if>
						
						
					</tr>
					
				</c:forEach>
			</c:if>
			<c:if test="${empty view_team}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="2" align="center"><font color=blue>---------*No Teams Available for This User*---------</font></td>
				</tr>
			 </table>	
			</c:if>
		</tbody>
	</table>
	 <div id="maindiv"  ></div>
<br />

<c:if test="${fn:length(view_team)>10}">
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
	
	<script type="text/javascript">
		$(document).ready(function(){
  			$("table#viewteams").tablesorter({widthFixed: true, widgets: ['zebra']})
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
	
	</form:form>

</body>
</html>