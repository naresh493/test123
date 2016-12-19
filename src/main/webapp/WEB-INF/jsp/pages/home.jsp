<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ page import="java.util.Date" %>
<%@page session="true" %>
<!DOCTYPE html>
<html lang="en">


<head>


<%
	//TODO: remove this  when pre-authentication solution is in place
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", -1);
%>
		<title>QlikTest Administration</title>
		<jsp:include page="/WEB-INF/jsp/template/css.jsp" />
	<%-- 	
		<script src="<%=request.getContextPath()%>/static/js/bootbox.js"></script>
	<script type="text/javascript">
			
		function updatePage() {
			document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
		}

		
		 $(document).ready(function(){
	  			$("table#viewProjects").tablesorter({widthFixed: true, widgets: ['zebra']})
	  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
	  			updatePage();
			});
	</script>
	 --%>	
</head>
<body>

<div class="wrapper">
		<!-- Header -->
		<jsp:include page="/WEB-INF/jsp/template/header.jsp" />
		<!-- Menubar -->
		<jsp:include page="/WEB-INF/jsp/template/menu.jsp" />	
		
		<!-- body -->
		<div class="maincontent">
			<c:choose>
				<c:when test='${empty createdefaultusertenant and empty assigndefaultusertotenant 
								and empty assignprojectstotenants and empty createuser 
								and empty deleteuser and empty viewappuser 
								and empty edituser and empty updateuser and empty createrole
								and empty createtenant and empty viewtenant and empty edittenant
								and empty updatetenant and empty deletetenant
								and empty createproject and empty viewproject
								and empty editproject and empty updateproject and empty deleteproject
								and empty createmodule and empty createlicense
								and empty createlicenseauth and empty assignmodulestoproject
								and empty createlicenseauthpermissions and empty createteam
								and empty viewteam and empty editteam and empty deleteteam 
								and empty updateteam and empty viewmodule and empty editmodule
								and empty removeuserfromteam and empty assignusertoteam
								and empty createpermission and empty managerolesandpermissions
								and empty viewprojectsbasedonid and empty assignuserstoproject
								and empty removeusersfromproject and empty addlicensestomodule
								and empty createrole and empty viewrole and empty editrole and empty updaterole
								and empty deleterole and empty addroletouser and empty addroletoteam
								and empty userreports and empty tenantsreports and empty teamsreports
								and empty projectsreports and empty modulereports and empty userreportscategory
								and empty assignprojectstoteam and empty viewpermissions and empty editpermission
								and empty assignpermissionstomodule and empty updatepermissions and empty updatemodules
								and empty changepassword
								and empty createobjspyconfig and empty editobjspyconfig and empty viewobjspyconfig and empty updateobjectspy 
								and empty createentpspyconfig and empty editentpspyconfig and empty viewentpspyconfig and empty updateentpspy 
								and empty createprlnodesconfig and empty editprlNodesconfig and empty viewprlnodesconfig and empty updateprlnodes 
								and empty createbugtrkconfig and empty editbugtrkconfig and empty viewbugtrkconfig and empty updatebugtrkconfig 
								and empty auditlogs and empty createperformanceconfigs and empty viewperformanceconfigs
								and empty editperformanceconfigs and empty updateperformanceconfigs and empty assignroletomodule
								and empty logout and empty otherreports and empty removeteamfromproject and empty viewotherreport and empty editotherreport
								and empty assignreportstorole and empty assignreportstomodule and empty assignmoduletorole
								and empty updatereports and empty viewlicenses  and empty tenantList and empty projectList and empty createmobileconfigurations
								and empty viewmobileconfigurations and empty editmobileconfiguration and empty updatemobileconfig 
								and empty createconstraint and empty viewconstraints and empty editconstraint and empty updateconstraint 
								and empty createldapconfiguration and empty viewldapconfiglist and empty editldapconfig
								and empty updateldapconfig and empty importusersfromldap
								and empty errorloghome and empty errorlogview
								and empty usersCreatedByAppAdmin}'>
						 <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
				</c:when>
				<c:when test='${not empty createdefaultusertenant}'>
					<jsp:include page="/WEB-INF/jsp/pages/userprofile/createdefaultuser.jsp"/>
				</c:when>
				
				<c:when test='${not empty auditlogs}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/viewauditlogs.jsp"/>
				</c:when>
				
				<c:when test='${not empty changepassword}'>
					<jsp:include page="/WEB-INF/jsp/pages/password/changepassword.jsp"/>
				</c:when>
				
				<c:when test='${not empty assignpermissionstomodule}'>
					<jsp:include page="/WEB-INF/jsp/pages/module/assignpermissionstomodule.jsp"/>
				</c:when>
				
				<c:when test='${not empty createpermission}'>
					<jsp:include page="/WEB-INF/jsp/pages/permissions/createpermission.jsp" />
				</c:when>
				<c:when test='${not empty viewpermissions}'>
					<jsp:include page="/WEB-INF/jsp/pages/permissions/viewpermissions.jsp"/>
				</c:when>
				<c:when test='${not empty editpermission}'>
					<jsp:include page="/WEB-INF/jsp/pages/permissions/editpermission.jsp" />
				</c:when>
				<c:when test='${not empty updatepermissions}'>
					<jsp:include page="/WEB-INF/jsp/pages/permissions/updatepermission.jsp" />
				</c:when>
				
				<c:when test='${not empty updatemodules}'>
					<jsp:include page="/WEB-INF/jsp/pages/module/updatemodule.jsp" />
				</c:when>
				
				<c:when test='${not empty userreportscategory}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/userreportscategory.jsp"/>
				</c:when>
				
				<c:when test='${not empty assignprojectstoteam}'>
					<jsp:include page="/WEB-INF/jsp/pages/team/assignteamtoprojects.jsp"/>
				</c:when>
				
				
				<c:when test='${not empty userreports}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/userreports.jsp"/>
				</c:when>
				<c:when test='${not empty tenantsreports}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/tenantsreports.jsp"/>
				</c:when>
				<c:when test='${not empty teamsreports}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/teamsreports.jsp"/>
				</c:when>
				<c:when test='${not empty projectsreports}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/projectsreports.jsp"/>
				</c:when>
				<c:when test='${not empty modulereports}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/modulesreports.jsp"/>
				</c:when>
				
				
				<c:when test='${not empty addroletouser}'>
					<jsp:include page="/WEB-INF/jsp/pages/role/addroletouser.jsp"/>
				</c:when>
				<c:when test='${not empty addroletoteam}'>
					<jsp:include page="/WEB-INF/jsp/pages/role/addroletoteam.jsp"/>
				</c:when>
				
				<c:when test='${not empty assigndefaultusertotenant}'>
					<jsp:include page="/WEB-INF/jsp/pages/userprofile/assigndefaultusertotenant.jsp" />
				</c:when>
				<c:when test='${not empty assignprojectstotenants}'>
					<jsp:include page="/WEB-INF/jsp/pages/tenant/assignprojectstotenants.jsp" />
				</c:when>
				<c:when test='${not empty createuser}'>
					<jsp:include page="/WEB-INF/jsp/pages/userprofile/createuser.jsp" />
				</c:when>
				<c:when test='${not empty deleteuser}'>
					<jsp:include page="/WEB-INF/jsp/pages/userprofile/deleteuser.jsp" />
				</c:when>
				<c:when test='${not empty viewappuser}'>
					<jsp:include page="/WEB-INF/jsp/pages/userprofile/viewuser.jsp" />
				</c:when>
				<c:when test='${not empty edituser}'>
					<jsp:include page="/WEB-INF/jsp/pages/userprofile/edituser.jsp" />
				</c:when>
				<c:when test='${not empty updateuser}'>
					<jsp:include page="/WEB-INF/jsp/pages/userprofile/updateuser.jsp" />
				</c:when>
				<c:when test='${not empty viewlicenses}'>
					<jsp:include page="/WEB-INF/jsp/pages/userprofile/viewlicenses.jsp" />
				</c:when>
				<c:when test='${not empty managerolesandpermissions}'>
					<jsp:include page="/WEB-INF/jsp/pages/role/managerolesandpermissions.jsp" />
				</c:when>
				<c:when test='${not empty createtenant}'>
					<jsp:include page="/WEB-INF/jsp/pages/tenant/createtenant.jsp" />
				</c:when>
				<c:when test='${not empty viewtenant}'>
					<jsp:include page="/WEB-INF/jsp/pages/tenant/viewtenants.jsp" />
				</c:when>
				<c:when test='${not empty edittenant}'>
					<jsp:include page="/WEB-INF/jsp/pages/tenant/edittenant.jsp" />
				</c:when>
				<c:when test='${not empty updatetenant}'>
					<jsp:include page="/WEB-INF/jsp/pages/tenant/updatetenant.jsp" />
				</c:when>
				
				<c:when test='${not empty createrole}'>
					<jsp:include page="/WEB-INF/jsp/pages/role/createrole.jsp" />
				</c:when>
				<c:when test='${not empty viewrole}'>
					<jsp:include page="/WEB-INF/jsp/pages/role/viewrole.jsp" />
				</c:when>
				<c:when test='${not empty editrole}'>
					<jsp:include page="/WEB-INF/jsp/pages/role/editrole.jsp" />
				</c:when>
				<c:when test='${not empty updaterole}'>
					<jsp:include page="/WEB-INF/jsp/pages/role/updaterole.jsp" />
				</c:when>
				<c:when test='${not empty deleterole}'>
					<jsp:include page="/WEB-INF/jsp/pages/role/deleterole.jsp" />
				</c:when>
				
				<c:when test='${not empty deletetenant}'>
					<jsp:include page="/WEB-INF/jsp/pages/tenant/deletetenant.jsp" />
				</c:when>
				<c:when test='${not empty createproject}'>
					<jsp:include page="/WEB-INF/jsp/pages/project/createproject.jsp" />
				</c:when>
				<c:when test='${not empty viewproject}'>
					<jsp:include page="/WEB-INF/jsp/pages/project/viewprojects.jsp" />
				</c:when>
				<c:when test='${not empty editproject}'>
					<jsp:include page="/WEB-INF/jsp/pages/project/editproject.jsp" />
				</c:when>
				<c:when test='${not empty assignuserstoproject}'>
					<jsp:include page="/WEB-INF/jsp/pages/project/assignuserstoproject.jsp"/>
				</c:when>
				<c:when test='${not empty removeusersfromproject}'>
					<jsp:include page="/WEB-INF/jsp/pages/project/removeusersfromproject.jsp"/>
				</c:when>
				<c:when test='${not empty editmodule }'>
					<jsp:include page="/WEB-INF/jsp/pages/module/editmodule.jsp"/>
				</c:when>
				<c:when test='${not empty updateproject}'>
					<jsp:include page="/WEB-INF/jsp/pages/project/updateproject.jsp" />
				</c:when>
				<c:when test='${not empty viewprojectsbasedonid}'>
					<jsp:include page="/WEB-INF/jsp/pages/project/viewprojectsbasedonid.jsp"/>
				</c:when>
				<c:when test='${not empty deleteproject}'>
					<jsp:include page="/WEB-INF/jsp/pages/project/deleteproject.jsp" />
				</c:when>
				<c:when test='${not empty createmodule}'>
					<jsp:include page="/WEB-INF/jsp/pages/module/createmodule.jsp" />
				</c:when>
				<c:when test='${not empty createlicense}'>
					<jsp:include page="/WEB-INF/jsp/pages/license/createlicense.jsp" />
				</c:when>
				<c:when test='${not empty addlicensestomodule}'>
					<jsp:include page="/WEB-INF/jsp/pages/licenseauth/addlicensetomodule.jsp" />
				</c:when>
				
				<c:when test='${not empty viewmodule }'>
					<jsp:include page="/WEB-INF/jsp/pages/module/viewmodule.jsp"/>
				</c:when>
				<c:when test='${not empty createteam }'>
					<jsp:include page="/WEB-INF/jsp/pages/team/createteam.jsp"/>
				</c:when>
				<c:when test='${not empty viewteam }'>
					<jsp:include page="/WEB-INF/jsp/pages/team/viewteams.jsp"/>
				</c:when>
				<c:when test='${not empty editteam }'>
					<jsp:include page="/WEB-INF/jsp/pages/team/editteam.jsp"/>
				</c:when>
				<c:when test='${not empty deleteteam }'>
					<jsp:include page="/WEB-INF/jsp/pages/team/deleteteam.jsp"/>
				</c:when>
				<c:when test='${not empty updateteam }'>
					<jsp:include page="/WEB-INF/jsp/pages/team/updateteam.jsp"/>
				</c:when>
				<c:when test='${not empty removeuserfromteam }'>
					<jsp:include page="/WEB-INF/jsp/pages/team/removeuserfromteam.jsp"/>
				</c:when>
				<c:when test='${not empty assignusertoteam}'>
					<jsp:include page="/WEB-INF/jsp/pages/team/assignusertoteam.jsp"/>
				</c:when>
				<c:when test='${not empty createlicenseauth}'>
					<jsp:include page="/WEB-INF/jsp/pages/licenseauth/createlicenseauth.jsp" />
				</c:when>
				<c:when test='${not empty createlicenseauthpermissions}'>
					<jsp:include page="/WEB-INF/jsp/pages/licenseauth/createlicenseauthpermissions.jsp" />
				</c:when>
				<c:when test='${not empty assignmodulestoproject}'>
					<jsp:include page="/WEB-INF/jsp/pages/project/assignmodulestoproject.jsp" />
				</c:when>
				<c:when test='${not empty createobjspyconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/createobjspyconfig.jsp" />
				</c:when>
				<c:when test='${not empty editobjspyconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/editobjspyconfig.jsp" />
				</c:when>
				<c:when test='${not empty viewobjspyconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/viewobjspyconfig.jsp" />
				</c:when>
				<c:when test='${not empty updateobjectspy}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/updateobjspyconfig.jsp" />
				</c:when>
				
				<c:when test='${not empty createentpspyconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/createentpspyconfig.jsp" />
				</c:when>
				<c:when test='${not empty editentpspyconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/editentpspyconfig.jsp" />
				</c:when>
				<c:when test='${not empty viewentpspyconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/viewentpspyconfig.jsp" />
				</c:when>
				<c:when test='${not empty updateentpspy}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/updateentpspyconfig.jsp" />
				</c:when>
				
				<c:when test='${not empty createmobileconfigurations}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/createmobileconfigurations.jsp" />
				</c:when>
				
				<c:when test='${not empty viewmobileconfigurations}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/viewmobileconfigurations.jsp" />
				</c:when>
				<c:when test='${not empty editmobileconfiguration}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/editmobileconfig.jsp" />
				</c:when>
				<c:when test='${not empty updatemobileconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/updatemobileconfig.jsp" />
				</c:when>
				
				<c:when test='${not empty createprlnodesconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/createprlnodesconfig.jsp" />
				</c:when>
				<c:when test='${not empty editprlNodesconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/editprlnodesconfig.jsp" />
				</c:when>
				<c:when test='${not empty viewprlnodesconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/viewprlnodesconfig.jsp" />
				</c:when>
				<c:when test='${not empty updateprlnodes}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/updateprlnodesconfig.jsp" />
				</c:when>
				
				<c:when test='${not empty createbugtrkconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/createbugtrkconfig.jsp" />
				</c:when>
				<c:when test='${not empty editbugtrkconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/editbugtrkconfig.jsp" />
				</c:when>
				<c:when test='${not empty viewbugtrkconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/viewbugtrkconfig.jsp" />
				</c:when>
				<c:when test='${not empty updatebugtrkconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/updatebugtrkconfig.jsp" />
				</c:when>
				
				<c:when test='${not empty createperformanceconfigs}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/createperformanceconfig.jsp" />
				</c:when>
				<c:when test='${not empty viewperformanceconfigs}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/viewperformanceconfig.jsp" />
				</c:when>
				<c:when test='${not empty editperformanceconfigs}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/editperformanceconfig.jsp" />
				</c:when>
				<c:when test='${not empty updateperformanceconfigs}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/updateperformanceconfig.jsp" />
				</c:when>
				
				
				<c:when test='${not empty assignroletomodule}'>
					<jsp:include page="/WEB-INF/jsp/pages/module/assignroletomodule.jsp" />
				</c:when>
				
				<c:when test='${not empty logout}'>
					<jsp:include page="/logout.jsp" />
				</c:when>
				<c:when test='${not empty otherreports}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/otherreports.jsp"/>
				</c:when>
				
				<c:when test='${not empty removeteamfromproject}'>
					<jsp:include page="/WEB-INF/jsp/pages/team/removeteamfromproject.jsp" />
				</c:when>
				<c:when test='${not empty viewotherreport}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/viewotherreport.jsp" />
				</c:when>
				<c:when test='${not empty editotherreport}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/editotherreport.jsp" />
				</c:when>
				<c:when test='${not empty assignreportstorole}'>
					<jsp:include page="/WEB-INF/jsp/pages/role/assignreportstorole.jsp" />
				</c:when>
				<c:when test='${not empty assignreportstomodule}'>
					<jsp:include page="/WEB-INF/jsp/pages/module/assignreportstomodule.jsp" />
				</c:when>
				<c:when test='${not empty assignmoduletorole}'>
					<jsp:include page="/WEB-INF/jsp/pages/module/assignmoduletorole.jsp" />
				</c:when>
				
				<c:when test='${not empty updatereports}'>
					<jsp:include page="/WEB-INF/jsp/pages/reports/updatereports.jsp" />
				</c:when>
				
				<c:when test='${not empty createconstraint}'>
					<jsp:include page="/WEB-INF/jsp/pages/constraints/createconstraint.jsp" />
				</c:when>
				<c:when test='${not empty viewconstraints}'>
					<jsp:include page="/WEB-INF/jsp/pages/constraints/viewconstraints.jsp" />
				</c:when>
				<c:when test='${not empty editconstraint}'>
					<jsp:include page="/WEB-INF/jsp/pages/constraints/editconstraint.jsp" />
				</c:when>
				<c:when test='${not empty updateconstraint}'>
					<jsp:include page="/WEB-INF/jsp/pages/constraints/updateconstraint.jsp" />
				</c:when>
				
				<c:when test='${not empty createldapconfiguration}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/createldapconfig.jsp" />
				</c:when>
				
				<c:when test='${not empty viewldapconfiglist}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/viewldapconfig.jsp" />
				</c:when>
				<c:when test='${not empty editldapconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/editldapconfig.jsp" />
				</c:when>
				<c:when test='${not empty updateldapconfig}'>
					<jsp:include page="/WEB-INF/jsp/pages/config/updateldapconfig.jsp" />
				</c:when>
				<c:when test='${not empty importusersfromldap}'>
					<jsp:include page="/WEB-INF/jsp/pages/importusers/selectldapconfig.jsp" />
				</c:when>
				
				<c:when test='${not empty errorloghome}'>
					<jsp:include page="/WEB-INF/jsp/pages/errorlogs/errorLogView.jsp" />
				</c:when>
				
				<c:when test='${not empty errorlogview}'>
					<jsp:include page="/WEB-INF/jsp/pages/errorlogs/errorLogView.jsp" />
				</c:when>
				
				<c:when test='${not empty projectList}'>
						<table align="center">
								<tr>
									<td>
										    <font size="3"><b>Projects</b></font>
									</td>
								</tr>
						</table>
				 <table id="viewProjects" class="table table-striped table-bordered table-hover">
						<thead align="center">
							<tr>
								<th align="center">Name</th>
								<th align="center">Start Date</th>
								<th align="center">End Date</th>
								<th align="center">Is Active</th>
								<th align="center">Description</th>
								<th align="center">Tenant</th>
							</tr>
					</thead>
					<tbody align="center">
						<c:if test="${not empty projectList}">
							<c:forEach items="${projectList}" var="item">
								<tr>
									<td align="center">${item.name}</td>
									<%-- <td align="center">${item.startDate}</td> --%>
									<td><fmt:formatDate value="${item.startDate}" type="date" pattern="dd-MM-yyyy" /></td>
									<td><fmt:formatDate value="${item.endDate}" type="date" pattern="dd-MM-yyyy" /></td>
									<%-- <td align="center">${item.endDate}</td> --%>
									<td align="center">${item.disabled ? 'false' : 'true'}</td>
									<td align="center">${item.description}</td>
									<td align="center">${item.tenantName}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody> 
				</table>
				<%--  <c:if test="${fn:length(projectList)>10}">
							<div id="pager" class="pagination" align="center">
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
							<script type="text/javascript">
								$(document).ready(function(){
						  			$("table#viewProjects").tablesorter({widthFixed: true, widgets: ['zebra']})
						  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
						  			updatePage();
								});
								function updatePage() {
									document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
								}
							</script>
					</c:if>	
		  --%>
	<%-- 
				<c:if test="${fn:length(projectList)>10}">
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
	
	<script src="static/js/jquery.tablesorter.min.js"></script>
	 --%>
				<table align="center">
							<tr>
								<td>
								    <font size="3"><b>Tenants</b></font>
								</td>
							</tr>
				</table>
				<table id="viewtenants" class="table table-striped table-bordered table-hover">
						<thead align="center">
							<tr>
								<th align="center">User Name</th>
								<th align="center">First Name</th>
								<th align="center">Last Name</th>
								<th align="center">Email</th>
								<th align="center">Tenant Name</th>
							</tr>
						</thead>
					<tbody align="center">
						<c:if test="${not empty tenantList}">
							<c:forEach items="${tenantList}" var="item">
								<tr>
									<td align="center">${item.userName}</td>
									<td align="center">${item.firstName}</td>
									<td align="center">${item.surName}</td>
									<td align="center">${item.emailAddress}</td>
									<td align="center">${item.tenantName}</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				
				<%--  <c:if test="${fn:length(tenantList)>10}">
							<div id="pager" class="pagination" align="center">
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
							<script type="text/javascript">
								$(document).ready(function(){
						  			$("table#viewtenants").tablesorter({widthFixed: true, widgets: ['zebra']})
						  			                               .tablesorterPager({container: $("#pager"), positionFixed:false});
						  			updatePage();
								});
								function updatePage() {
									document.getElementById('curPageNumb').innerHTML = "page "+document.getElementById('disp').value;
								}
							</script>
					</c:if>	 --%>
		 
				</c:when>
			
				<c:when test='${not empty projectToTenant}'>
						
						<table align="center">
								<tr>
									<td>
										    <font size="3"><b>Projects</b></font>
									</td>
								</tr>
						</table>
				 <table id="viewProjects" class="table table-striped table-bordered table-hover">
						<thead align="center">
							<tr>
								<th align="center">Name</th>
								<th align="center">Start Date</th>
								<th align="center">End Date</th>
								<th align="center">Description</th>
							</tr>
					</thead>
					<tbody align="center">
						<c:if test="${not empty projectToTenant}">
							<c:forEach items="${projectToTenant}" var="item">
								<tr>
									<td align="center">${item.name}</td>
									<td><fmt:formatDate value="${item.startDate}" type="date" pattern="dd-MM-yyyy" /></td>
									<td><fmt:formatDate value="${item.endDate}" type="date" pattern="dd-MM-yyyy" /></td>
									<td align="center">${item.description}</td>
								</tr>
							</c:forEach>
						</c:if>
						
					</tbody> 
				</table>
						
						
				<c:if test="${not empty usersCreatedByAppAdmin}">		
						
						<table align="center">
								<tr>
									<td>
										    <font size="3"><b>Users</b></font>
									</td>
								</tr>
						</table>
						 <table id="viewUsers" class="table table-striped table-bordered table-hover">
								<thead align="center">
									<tr>
										<th align="center">User Name</th>
										<th align="center">EmailID</th>
										<th align="center">Mobile</th>
										<th align="center">Domain</th>
										<th align="center">Experience</th>
										<th align="center">Number of Projects worked</th>
									</tr>
							</thead>
							<tbody align="center">
								<c:if test="${not empty usersCreatedByAppAdmin}">
									<c:forEach items="${usersCreatedByAppAdmin}" var="item">
										<tr>
											<td align="center">${item.userName}</td>
											<td align="center">${item.emailAddress}</td>
											<td align="center">${item.mobile}</td>
											<td align="center">${item.domain}</td>
											<td align="center">${item.experience}</td>
											<td align="center">${item.noOfProjectsWorked}</td>
										</tr>
									</c:forEach>
								</c:if>
								
							</tbody> 
						</table>
				</c:if>
				</c:when>
				
			
			</c:choose>
			
		<%-- 	<c:if test='${not empty userListCreatedByAppAdmin}'>
						<table align="center">
								<tr>
									<td>
										    <font size="3"><b>Users</b></font>
									</td>
								</tr>
						</table>
			<table id="viewUsers" class="table table-striped table-bordered table-hover">
						<thead align="center">
							<tr>
								<th align="center">User Name</th>
								<th align="center">EmailID</th>
								<th align="center">Mobile</th>
								<th align="center">Domain</th>
								<th align="center">Experience</th>
								<th align="center">Number of Projects worked</th>
							</tr>
					</thead>
					<tbody align="center">
						<c:if test="${not empty userListCreatedByAppAdmin}">
							<c:forEach items="${userListCreatedByAppAdmin}" var="item">
								<tr>
									<td align="center">${item.userName}</td>
									<td align="center">${item.emailAddress}</td>
									<td align="center">${item.mobile}</td>
									<td align="center">${item.domain}</td>
									<td align="center">${item.experience}</td>
									<td align="center">${item.noOfProjectsWorked}</td>
								</tr>
							</c:forEach>
						</c:if>
						
					</tbody> 
				</table>
			</c:if>
		 --%>
		 
		 <c:if test="${fn:length(usersCreatedByAppAdmin)>10}">
	<div id="pager" class="pagination" align="center">
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
	<script src="static/js/jquery.tablesorter.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
  			$("table#viewUsers").tablesorter({widthFixed: true, widgets: ['zebra']})
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
		 
		 
			</div>
			
			
		<!-- body -->
	<!-- Footer -->
	<jsp:include page="/WEB-INF/jsp/template/footer.jsp" />
</div>
</body>
</html>