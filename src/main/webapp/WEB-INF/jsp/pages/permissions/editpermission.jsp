<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<style>
		body {
	       
	      }
		table.table.table-striped{
			width: 350px;
			margin: 0 auto;
			background-color: #f9f9f9;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
	</style>
	<style>
		label{
			cursor:auto !important;
		}
	</style>
	<script type="text/javascript">
		function validatepage(){
			var namewspaces = document.getElementById('name').value;
			var name = namewspaces.trim();
			if(name.length==0)
				{
					alert('Please give a permission name');
					return false;
				}
			if(namewspaces.length != name.length){
				alert('Name should not contain leading and tailing spaces');
				return false;
			}else{
				return true;
			}
		}
	</script>
		<c:url var="firstUrl" value="/content/permissions/editpagepermissions/0" />
<c:url var="lastUrl" value="/content/permissions/editpagepermissions/${numberofpages - 1}" />
<c:url var="prevUrl" value="/content/permissions/editpagepermissions/${currentpage - 1}" />
<c:url var="nextUrl" value="/content/permissions/editpagepermissions/${currentpage + 1}" />
<c:url var="linkvalue" value="/content/permissions"/>
</head>
<body>
<c:set var="searchUrl" scope="request">
	 <c:url value="/content/permissions/searcheditpermissions"/>
</c:set>
  <c:set var="pageUrl" scope="request">
	 <c:url value="/content/updateuser" />
  </c:set>
  <form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="GET">
	 <table class="tblHeader-inner">																																						
		<tr>
			<td colspan="2" align="center"><font size="3"><b>Edit Permission</b></font><br />
			</td>
		</tr>
		<c:if test='${not empty permissionupdated}'> 
			<tr>
					<td align="center">&nbsp;&nbsp;
						<font color="red" size="3">${permissionupdated}</font>
				</td>
			</tr>
		</c:if>
	</table><br />
	
	</form:form>
	
	
	<form:form class="form-viewproject" commandName="crudObj" action="${searchUrl}" method="POST">
	 <%-- <table class="tblHeader-inner" style="width:34%;margin: 0 auto;">																																						
		<tr>
			<!-- 
			<td>
				<label>Name</label>
			</td>
			<td>
			&nbsp;&nbsp;
			</td> -->
			<td>
				<form:label for="permissionsName" path="name">Name</form:label>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>	
				<form:input id="name" path="name" autocomplete="off" type = "text" placeholder="Permission Name" />
			</td>
			<td>
			&nbsp;&nbsp;
			</td>
			
			<td>
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-bottom: 10px;" onclick="return validatepage()"/>&nbsp;
			</td>
			<td>
 				<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/permissions/editpermission'"/>
			</td>
			
			<td>	
				<form:input id="name" path="name" autocomplete="off" type = "text" placeholder="Permission Name" />
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-top:-10px;" onclick="return validatepage()"/>
			</td>
			<td>
 				<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/permissions/editpermission'"/>
			</td>
			
		</tr>
		
	</table> --%>
	
	<table align="center">
		<tr>
			<td>
				<form:label for="permissionsName" path="name">Name</form:label>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>	
				<form:input id="name" path="name" autocomplete="off" type = "text" placeholder="Display Name" />
			</td>
			<td>
			&nbsp;&nbsp;&nbsp;
			</td>
			<td>
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-bottom: 10px;" onclick="return validatepage()"/>&nbsp;
			</td>
			<td>
 				<input type="button" name="cancel" class="btn btn-primary" value="Refresh" style="margin-bottom: 10px;" onClick="location.href='/QlikTestAdmin/content/permissions/editpermission'"/>
			</td>
			
		</tr>
	</table>
	
	
	<br />
	
	</form:form>
	
	
	<div>
	<table id="editusers" class="table table-striped table-bordered table-hover">
		<thead>
		</thead>
		<tbody>
			<c:if test="${not empty permission_list}">
				<c:forEach items="${permission_list}" var="item">
					<tr>
						<td colspan="1"><a href="${linkvalue}/edit/${item.id}/update">${item.name}</a><br/>
							<c:if test="${empty item.modifiedBy}">
								<em class="help-inline">${item.createdName} created on ${item.createdDate}</em>
							</c:if>
							<c:if test="${not empty item.modifiedBy}">
								<em class="help-inline">${item.createdName} ${empty item.modifiedBy?'':'updated on'}  ${item.modifiedDate}</em>
							</c:if>
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	
	</div><br />
	<c:if test="${empty permission_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="1" align="center"><font color=blue>---------*No Permissions Available*---------</font></td>
					
				</tr>
				</table>
	</c:if>
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
	<!-- <div id="pager" class="pagination">
		<ul>
			<li class="prev first"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt;&lt; First</a></li>
			<li class="prev"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt; Prev</a></li>
			<li class="active"><a href="#" id="curPageNumb">page</a></li>
			<li class="next"><a href="#" onmouseup="setTimeout('updatePage()',500);">Next &gt;</a></li>
			<li class="next last"><a href="#" onmouseup="setTimeout('updatePage()',500);">Last &gt;&gt;</a></li>
		</ul>
		<input type="hidden" class="pagedisplay" id="disp" value="5" />
		<input type="hidden" class="pagesize" value="10" />
	</div> -->
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
	</c:if> --%>
	<!-- <script type="text/javascript">
		$(document).ready(function(){
  			$("table#editusers").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
	</script>	 -->
</body>
</html>