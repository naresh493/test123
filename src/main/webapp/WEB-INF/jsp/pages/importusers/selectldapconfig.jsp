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
<c:set var="pageUrl" scope="request">
	<c:url value="/content/import/getallldapusers" />
</c:set>
</head>
<form:form id="signup" class="form-createuser" action="${pageUrl}" commandName="crudObj" method="POST">
<body>
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>Select a configuration to Import Users</b></font><br /><br />
			</td>
		</tr>
	</table>
	<table class="tblHeader-inner">
			
			<c:if test='${not empty userimportedfromldap}'> 
				<tr>
					<td colspan="2" align="center">
						<font color="green" size="2"><b>${userimportedfromldap}</b></font><br /><br />
					</td>
				</tr>
			</c:if>
			
		</table>	
		
<table id="viewusers" class="table table-striped table-bordered table-hover" >
	<thead>
		<tr>
			<th>Select Config</th>
			<th>Name</th>
			<th>Base</th>
			<th>UserName</th>
			<th>port</th>
			<th>Server Ip</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${not empty ldapconfig_list}">
			<c:forEach items="${ldapconfig_list}" var="item">
				<tr>
					<td><form:radiobutton path="id" value="${item.id}"/>
					<td>${item.name}</td>
					<td>${item.base}</td>
					<td>${item.username}</td>
					<td>${item.port}</td>
					<td>${item.serverip}</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<br>
<table align="center">
	<tr>
		<td colspan="2" align="center">
			<c:if test="${fn:length(ldapconfig_list)>10}">
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
        </td>
	</tr>
</table>
<table align="center">
	<tr>
		<td colspan="2" align="center">
			<input type="submit" class="btn btn-primary" value="Import"/>&nbsp;
        </td>
	</tr>
</table>
<br />


<%-- <c:if test="${fn:length(ldapconfig_list)>10}">
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
</c:if> --%>
</form:form>
</body>
</html>