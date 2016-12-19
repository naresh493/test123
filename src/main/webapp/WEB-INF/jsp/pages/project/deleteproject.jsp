<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<style>
		body {
	        
	      }
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
<body>
  <c:set var="pageUrl" scope="request">
	 <c:url value="/content/project/delete" />
  </c:set>
  <form:form class="form-deletetenant" action="${pageUrl}" commandName="crudObj" method="POST">
	 <table class="tblHeader-inner">																																						
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					
					Delete Project</b></font><br />
			</td>
		</tr>
		<c:if test='${not empty projectdeleted}'> 
			<tr>
					<td align="center"><br />
						<font color="green" size="3"><b>${projectdeleted}</b></font>
				</td>
			</tr>
		</c:if>
	</table><br />	
	<div>
	<table id="tblDeleteProject" class="table table-striped table-bordered table-condensed table-hover">
		<tbody>
			<!-- <tr>
				<td align="center"><b>Name</b><td>
			</tr> -->
			
				<c:if test="${not empty active_projects}">
					<c:forEach items="${active_projects}" var="item">
						<tr>
							<td>
								<spring:bind path="id">
									&nbsp;&nbsp;&nbsp;<form:input path="id" type="radio" required="true" value="${item.id}" ></form:input>&nbsp;&nbsp;&nbsp;
								</spring:bind>
							</td>
							<td colspan="1"><c:out value="${item.name }"/><br/>
								<c:if test="${empty item.modifiedBy}">
									<em class="help-inline">${item.createdBy} created on ${item.createdDate}</em>
								</c:if>
								<c:if test="${not empty item.modifiedBy}">
									<em class="help-inline">${item.modifiedBy} ${empty item.modifiedBy?'':'updated on'}  ${item.modifiedDate}</em>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			
			<tr>
				<td colspan="2" align="center">
					
					<c:if test="${empty crudObj.id}">
					  	 <input id="btnSubmit" type="submit" class="btn btn-primary" value="Delete" />&nbsp;
					</c:if>
		        </td>
			<tr>
		</tbody>
	</table>
	
		<spring:bind path="createdBy">
              <form:hidden path="createdBy" value="${sessionScope.username}" />
        </spring:bind>
        
        <spring:bind path="createdDate">
              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
        </spring:bind>
        
        <spring:bind path="modifiedBy">
              <form:hidden path="modifiedBy" value="${sessionScope.username}" />
        </spring:bind>
        
        <spring:bind path="modifiedDate">
              <form:hidden path="modifiedDate" value="<%=new java.util.Date()%>" />
        </spring:bind>
        
	</div><br />
	<div id="pager" class="pagination">
		<ul>
			<li class="prev first"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt;&lt; First</a></li>
			<li class="prev"><a href="#" onmouseup="setTimeout('updatePage()',500);">&lt; Prev</a></li>
			<li class="active"><a href="#" id="curPageNumb">page</a></li>
			<li class="next"><a href="#" onmouseup="setTimeout('updatePage()',500);">Next &gt;</a></li>
			<li class="next last"><a href="#" onmouseup="setTimeout('updatePage()',500);">Last &gt;&gt;</a></li>
		</ul>
		<input type="hidden" class="pagedisplay" id="disp" value="1" />
		<input type="hidden" class="pagesize" value="4" />
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
  			$("table#tblDeleteProject").tablesorter({widthFixed: true, widgets: ['zebra']})
  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
  			updatePage();
		});
		/* $('.editusers').dataTable({
	        "sPaginationType": "bootstrap",
	        "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
	        "oLanguage": {
	            "sLengthMenu": "_MENU_ records per page"
	        },
	       "iDisplayLength": 5,
	       "aLengthMenu": [[15, 25, 50, -1], [15, 25, 50, "All"]]
	    }); */
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}
	</script>
	</form:form>
</body>
</html>