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
  <c:set var="searchUrl" scope="request">
	 <c:url value="/content/tenant/searchedittenant" />
  </c:set>
	 <table class="tblHeader-inner">																																						
		<tr>
			<td colspan="2" align="center"><font size="3"><b>
					
					Edit Tenant</b></font><br />
			</td>
		</tr>
		<c:if test='${not empty tenantupdated}'> 
			<tr>
					<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<font color="red" size="3">${tenantupdated}</font>
				</td>
			</tr>
		</c:if>
	</table><br />	
	<form:form class="form-viewproject" commandName="crudObj" action="${searchUrl}" method="POST">
	 <table align="center">
		<tr>
			 <td><label>Tenant <font color='red' size='3'></font></label></td>
			 <td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
				<td>
					<form:select id="tenant" path="id" style="color:#333333">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${tenantname_list}" itemLabel="name" itemValue="id" />
					</form:select>
				</td> 
				<td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
			
			<td>
				<form:label for="name" path="projectId">Select Project</form:label>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;</td>
			
			<td>
			<td>	
				<form:select id="idModules" path="projectId" style="color:#333333">
						<form:option value="" label="--Please Select--"/>
						<form:options items="${project_list}" itemLabel="name" itemValue="id" />
				</form:select>
			</td>
			<td>
			&nbsp;&nbsp;&nbsp;
			</td>
			<td>
				<input id="btnSubmit" type="submit" class="btn btn-primary" value="Search" style="margin-top:-10px;" />&nbsp;
			</td>
		</tr>
	</table>
	<%-- </form:form> --%>
	<table id="edittenant" class="table table-striped table-bordered table-hover">
		<thead/>
		<tbody>
			<c:if test="${not empty tenant_list}">
				<c:forEach items="${tenant_list}" var="item">
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
			<c:if test="${empty tenant_list}">
				<tr>
					<td><font color=blue>---------*No Tenants Available*---------</font></td>
					
				</tr>
			</c:if>
		</tbody>
	</table>
	<br />
	<%-- <c:if test="${fn:length(tenant_list)>10}">
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
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
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
	</c:if>
	<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script> --%>
	
	<c:if test="${fn:length(tenant_list)>10}">
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
	<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<script type="text/javascript">
		 $(document).ready(function(){
  			$("table#edittenant").tablesorter({widthFixed: true, widgets: ['zebra']})
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
		
		/* function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5></center><p style='margin-left:240px;'>"+data+"</p>");
			
		} */
	</script>
	</form:form>
</body>
</html>