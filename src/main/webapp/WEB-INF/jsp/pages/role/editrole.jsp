<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<style>
		
		table.table.table-striped{
			width: 300px;
			margin: 0 auto;
			background-color: #f9f9f9;
		}
		div.pagination{
			width: 400px;
			margin: 0 auto;
			text-align: center;
		}
	</style>
</head>
<script type="text/javascript">
function validatepage() {
	var roleName = document.getElementById('roleName').value;
	if(roleName == null || roleName == ""){
		alert("Please enter rolename to search");
		return false;
	} else {
		return true;
	}
}
</script>
<body>
<c:set var="searchUrl" scope="request">
	 <c:url value="/content/role/searcheditrole"/>
</c:set>
  
  <c:set var="pageUrl" scope="request">
	 <c:url value="/content/role/update" />
  </c:set>
<form:form class="form-viewproject" commandName="crudObj" action="${pageUrl}" method="GET">
	 <table class="tblHeader-inner">																																						
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					
					Edit Role</b></font><br />
			</td>
		</tr>
		<c:choose>
				
				<c:when test='${not empty roleupdated}'> 
					<tr>
							<td colspan="2" align="center"><font color="green" size="3"><b>${roleupdated}</b></font><br /><br /></td>
					</tr>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
	</table><br />	
	</form:form>
	<form:form class="form-viewproject" commandName="crudObj" action="${searchUrl}" method="POST">
	 <table class="tblHeader-inner">	
	 																																				
		<table align="center">
		<tr>
			<td>
				<form:label for="name" path="name">Role Name</form:label>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
			<td>	
				<form:input id="roleName" path="name" autocomplete="off" type = "text" placeholder="Role Name" />
			</td>
			<td>
			&nbsp;&nbsp;&nbsp;
			</td>
			<td>
				
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-top:-10px;" onclick="return validatepage()"/>&nbsp;
			</td>
		</tr>
	</table>
	</table><br />
	
	</form:form>
	<div>
	<table id="editproject" class="table table-striped table-bordered table-condensed table-hover">
		<thead/>
		<tbody>
			<c:if test="${not empty role_list}">
				<c:forEach items="${role_list}" var="item">
					<tr>
						<td colspan="1"><a href="edit/${item.id}/update">${item.name}</a><br/>
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
	<c:if test="${empty role_list}">
			 <table class="tblHeader-inner">
				<tr>
					<td colspan="1" align="center"><font color=blue>---------*No Roles Available*---------</font></td>
					
				</tr>
				</table>
	</c:if>
	<c:if test="${fn:length(role_list)>10}">
	<div id="pager" class="pagination">
		<ul>
			<li class="prev first"><a href="#" onmouseup="setTimeout('updatePage()',100);">&lt;&lt; First</a></li>
			<li class="prev"><a href="#" onmouseup="setTimeout('updatePage()',100);">&lt; Prev</a></li>
			<li class="active"><a href="#" id="curPageNumb">page</a></li>
			<li class="next"><a href="#" onmouseup="setTimeout('updatePage()',100);">Next &gt;</a></li>
			<li class="next last"><a href="#" onmouseup="setTimeout('updatePage()',100);">Last &gt;&gt;</a></li>
		</ul>
		<input type="hidden" class="pagedisplay" id="disp" value="1" />
		<input type="hidden" class="pagesize" value="10" />
	</div>
	</c:if>
	<script type="text/javascript">
		$(document).ready(function(){
  			$("table#editproject").tablesorter({widthFixed: true, widgets: ['zebra']})
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
</body>
</html>