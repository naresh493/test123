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
	 <c:url value="/content/userprofile/deletetenants" />
  </c:set>
  <form:form class="form-deletetenant" action="${pageUrl}" commandName="crudObj" method="POST">
	 <table class="tblHeader-inner">																																						
		<tr>
			<td colspan="2" align="Center"><font size="3"><b>
					
					Delete Tenant</b></font><br />
			</td>
		</tr>
		<c:if test='${not empty tenantdeleted}'> 
			<tr>
					<td align="center">&nbsp;&nbsp;&nbsp;<br />
						<font color="green" size="3">${tenantdeleted}</font>
				</td>
			</tr>
		</c:if>
	</table><br />	
	<div>
	<table id="edittenant" class="table table-striped table-bordered table-condensed table-hover">
		<tbody>
			<c:if test="${not empty active_tenants}">
				<c:forEach items="${active_tenants}" var="item">
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;<form:input path="name" type="radio" required="true" name="${item.name}" value="${item.name}" ></form:input>&nbsp;&nbsp;&nbsp;
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
              <form:hidden path="createdBy" value="${sessionScope.userid}" />
        </spring:bind>
        
        <spring:bind path="createdDate">
              <form:hidden path="createdDate" value="<%=new java.util.Date()%>" />
        </spring:bind>
        
        <spring:bind path="modifiedBy">
              <form:hidden path="modifiedBy" value="${sessionScope.userid}" />
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
		<input type="hidden" class="pagesize" value="6" />
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
  			$("table#edittenant").tablesorter({widthFixed: true, widgets: ['zebra']})
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