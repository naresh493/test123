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
	<style>
		label{
			cursor: auto !important;
		}
	</style>
	
<script language="javascript" type="text/javascript">
function validatePage(){
			var pName = document.getElementById('permissionName').value;
			
			if((pName == null || pName == '')){
				
				alert('Please Give a Permission Name.');
				return false;
			}else{
				return true;
			}
		}

</script>
	
	<c:url var="firstUrl" value="/content/permissions/viewpagepermissions/0" />
<c:url var="lastUrl" value="/content/permissions/viewpagepermissions/${numberofpages - 1}" />
<c:url var="prevUrl" value="/content/permissions/viewpagepermissions/${currentpage - 1}" />
<c:url var="nextUrl" value="/content/permissions/viewpagepermissions/${currentpage + 1}" />
</head>
<body>

<c:set var="pageUrl" scope="request">
	 <c:url value="/content/permissions/searchpermissions"/>
</c:set>
<c:set var="refreshUrl" scope="request">
	 <c:url value="/content/permissions/viewpermissions"/>
</c:set>
<form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="POST">
	<table class="tblHeader-inner">
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					View Permissions</b></font><br /><br />
			</td>
		</tr>
		
	</table>
	<table align="center">
		<tr>
			<td>
				<form:label for="permissionsName" path="name">Name</form:label>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>	
				<form:input path="name" id="permissionName" autocomplete="off" type = "text" placeholder="Display Name" />
			</td>
			<td>
			&nbsp;&nbsp;&nbsp;
			</td>
			<td>
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-bottom: 10px;" onclick="return validatePage();"/>&nbsp;
			</td>
			<td>
 				<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/permissions/viewpermission'"/>
			</td>
			
		</tr>
	</table>
	<br>
<table id="viewprojects" class="table table-striped table-bordered table-hover">
		<thead align="center">
			<tr>
				<th align="center">Permission Name</th>
				<th align = "center">Description</th>
				<th align = "center">Display Name</th>
				<th align="center">Status</th>
				<th align="center">Created Date</th>
				<th align="center">Modified Date</th>
			</tr>
		</thead>
		<tbody align="center">
			<c:if test="${not empty permission_list}">
				<c:forEach items="${permission_list}" var="item">
					<tr>
						<td align="center">${item.name}</td>
						<td align="center">${item.description}</td>
						<td align="center">${item.aliasName}</td>
						<td align="center">${item.disabled ? 'InActive' : 'Active'}</td>
						<td align="center">${item.createdDate}</td>
						<td align="center">${item.modifiedDate}</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<c:if test="${empty permission_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="6" align="center"><font color=blue>---------*No Permissions Available*---------</font></td>
					
				</tr>
				</table>
	</c:if>
	</form:form>
<c:if test="${numberofpages > 1}">
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
<%-- <c:if test="${fn:length(permission_list)>10}">
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
	</c:if>
	
	
	<script type="text/javascript">
		 $(document).ready(function(){
  			$("table#viewprojects").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
		</script> --%>
</body>
</html>