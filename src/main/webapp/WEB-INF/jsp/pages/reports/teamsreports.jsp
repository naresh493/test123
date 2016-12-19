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
		table.table.table-striped3{
			height: 100px;
			width: 30px;
			margin: 0 auto;
			background-color: white;
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
	 <c:url value="/content/reports/searchteams"/>
  </c:set>
<form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					Teams</b></font><br /><br />
			</td>
		</tr>
	</table>
	<table class="table table-striped3">
	<tr align="center">	
				<td>
					<form:label for="idRole" path="roleId">Role</form:label>
				</td>
				<td>
					<form:select id="idRole" path="roleId" style="color:#333333;width:150px !important">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${role_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				
				<td>
					<form:label for="idProject" path="projectid">Project</form:label>
				</td>
				<td>
					<form:select id="idProject" path="projectid" style="color:#333333;width:150px !important">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td>
				
				<td>
					<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" onclick="return validatePage()"/>
				</td>
				<td>
					<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/reports/teamsreports'"/>
				</td>
			</tr>
</table>	
	<table id="viewprojects" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Name</th>
				<th align="center">NoOfUsers</th>
				<th align="center">Projects</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty team_list}">
				<c:forEach items="${team_list}" var="item">
					<tr>
						<td align="center">${item.teamName}</td>
						<c:if test="${item.teamCount eq 0}">
							<td align="center">${item.teamCount}</td>
						</c:if>
						<c:if test="${item.teamCount>0}">
							<td align="center"><a href="#" onclick="openModal('Available Users','${item.userName}')">${item.teamCount}</a></td>
						</c:if>
						
						<td align="center">${item.projects}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
		 <c:if test="${empty team_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="3" align="center"><font color=blue>---------*No Teams Available*---------</font></td>
					
				</tr>
				</table>
	</c:if>
	</table>

<br />
<%-- 
	<div id="pager" class="pagination">
		<ul>
			<li class="prev first"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt;&lt; First</a></li>
			<li class="prev"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt; Prev</a></li>
			<li class="active"><a href="#" id="curPageNumb">page</a></li>
			<li class="next"><a href="#" onmouseup="setTimeout('updatePage()',500);">Next &gt;</a></li>
			<li class="next last"><a href="#" onmouseup="setTimeout('updatePage()',500);">Last &gt;&gt;</a></li>
		</ul>
		<input type="hidden" class="pagedisplay" id="disp" value="5" />
		<input type="hidden" class="pagesize" value="8" />
	</div>
	
	
	<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<link type="text/css" href='<%=request.getContextPath()%>/static/css/jquery-ui.css' rel="stylesheet">
	<script type="text/javascript">
		$(document).ready(function(){
  			$("table#viewprojects").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
		function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center><p style='margin-left:240px;'>"+data+"</p>");
			
		}
		function validatePage(){
			var idrole = document.getElementById('idRole').value;
			var idproject = document.getElementById('idProject').value;
			if((idrole == null || idrole == '') && (idproject == null || idproject == '')){
				
				alert('Please select atleast a option to search');
				return false;
			}else{
				return true;
			}
		}
	</script> --%>

<c:if test="${fn:length(team_list)>10}">
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
			$("table#viewprojects").tablesorter({widthFixed: true, widgets: ['zebra']})
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
	<%-- 	<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
			$("table#viewprojects").tablesorter({widthFixed: true, widgets: ['zebra']})
			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
			updatePage();
	});
	function updatePage() {
		document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
	}
	function openModal(title,data){
		bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center><p style='margin-left:240px;'>"+data+"</p>");
		
	}
		function validatePage(){
			var idrole = document.getElementById('idRole').value;
			var idproject = document.getElementById('idProject').value;
			if((idrole == null || idrole == '') && (idproject == null || idproject == '')){
				
				alert('Please select atleast a option to search');
				return false;
			}else{
				return true;
			}
		}
	</script> --%>
</c:if>	

		<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<script type="text/javascript">
	/* $(document).ready(function(){
			$("table#viewprojects").tablesorter({widthFixed: true, widgets: ['zebra']})
			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
			updatePage();
	});
	function updatePage() {
		document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
	} */
	function openModal(title,data){
		bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center><p style='margin-left:240px;'>"+data+"</p>");
		
	}
		function validatePage(){
			var idrole = document.getElementById('idRole').value;
			var idproject = document.getElementById('idProject').value;
			if((idrole == null || idrole == '') && (idproject == null || idproject == '')){
				
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