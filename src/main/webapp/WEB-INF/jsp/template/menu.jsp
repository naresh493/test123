<%-- <%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page session="true" %>
<style>
	.select {
		background:#555;
		color:#ffff;
	}
</style>
<script type="text/javascript">
function insertBreadCrumbHome(val){
	sessionStorage.bcparent = " Home "+val;
	sessionStorage.bcparent =" ";
	sessionStorage.bcchild = " ";
	sessionStorage.bcparent = val;
}

function insertBreadCrumb(val){
	
	sessionStorage.bcparent = " Home >>" +val;
		$("#bc_child").text('');
		
}
function insertChildBreadCrumb(val){
	if(val == ''){
		sessionStorage.bcchild = ""+val;
	}else{
		sessionStorage.bcchild = " >> "+val;
	}
	
	
	
}
function clearChildBreadCrumb(){
	localStorage.bcchild='';
}
</script>	
<c:set var="pageUrl" scope="request" value="/QlikTestAdmin/content"/>
<div class="navbar-outer">
<div class="navbar">
<div class="navbar-inner">
    <div class="container">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
              <!-- FOR SYS ADMIN -->
			<c:if test="${fn:contains(sessionScope.roleName,'System Administrator')}">
               			    <li id="dropdownProjectMenu">
	                    		<a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="insertBreadCrumb('Permission');clearChildBreadCrumb()">Permission<b class="caret" ></b></a>
		                    	<ul class="dropdown-menu multi-level">
				                    			<li><a onclick="insertChildBreadCrumb('Create Permission' )"  href="<c:out value="${pageUrl}"/>/permissions/<c:out value="createpermission" />">Create Permission</a></li>
				                    			<li><a onclick="insertChildBreadCrumb('View Permissions')"  href="<c:out value="${pageUrl}"/>/permissions/<c:out value="viewpermission" />">View Permissions</a></li>
				                    			<li><a onclick="insertChildBreadCrumb('Edit Permission')" href="<c:out value="${pageUrl}"/>/permissions/<c:out value="editpermission" />">Edit Permission</a></li>
		                        </ul>
		                    </li>	
		                    <li id="dropdownProjectMenu">
	                    		<a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="insertBreadCrumb('Controls');clearChildBreadCrumb()">Controls<b class="caret" ></b></a>
		                    	<ul class="dropdown-menu multi-level">
				                    			<li><a onclick="insertChildBreadCrumb('Create Control' )"  href="<c:out value="${pageUrl}"/>/constraints/<c:out value="createconstraint" />">Create Control</a></li>
				                    			<li><a onclick="insertChildBreadCrumb('View Controls')"  href="<c:out value="${pageUrl}"/>/constraints/<c:out value="viewconstraints" />">View Controls</a></li>
				                    			<li><a onclick="insertChildBreadCrumb('Edit Control')" href="<c:out value="${pageUrl}"/>/constraints/<c:out value="editconstraint" />">Edit Control</a></li>
		                        </ul>
		                    </li>
						 <li id="dropdownLicenseMenu">
		                    	<a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="insertBreadCrumb('Packages');clearChildBreadCrumb()">Packages<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	
			                    			<li><a onclick="insertChildBreadCrumb('Create Package')" href="<c:out value="${pageUrl}"/>/module/<c:out value="createmodule" />">Create Package</a></li>
			                    			<li><a onclick="insertChildBreadCrumb('View Packages')" href="<c:out value="${pageUrl}"/>/module/<c:out value="viewmodule" />">View Packages</a></li>
			                    			<li><a onclick="insertChildBreadCrumb('Edit Package')" href="<c:out value="${pageUrl}"/>/module/<c:out value="editmodule" />">Edit Package</a></li>
		                        </ul>
		                    </li>
		                     <li id="dropdownProjectMenu">
	                    		<a class="dropdown-toggle" data-toggle="dropdown" href="#"  onclick="insertBreadCrumb('Projects');clearChildBreadCrumb()">Projects<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	
			                    			<li><a onclick="insertChildBreadCrumb('Create Project')" href="<c:out value="${pageUrl}"/>/project/<c:out value="createproject" />">Create Project</a></li>
			                    			<li><a onclick="insertChildBreadCrumb('View Projects')" href="<c:out value="${pageUrl}"/>/project/<c:out value="viewproject" />">View Projects</a></li>
			                    			<li><a  onclick="insertChildBreadCrumb('Edit Project')" href="<c:out value="${pageUrl}"/>/project/<c:out value="editproject" />">Edit Project</a></li>
			                    		
			                    	
		                        </ul>
		                    </li>	
		                    
		                    
		                     <li id="dropdownTenantMenu">
		                    	<a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="insertBreadCrumb('Tenants');clearChildBreadCrumb()">Tenants<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	
				                    			<li><a  onclick="insertChildBreadCrumb('Create Tenant')"href="<c:out value="${pageUrl}"/>/userprofile/<c:out value="createtenant" />">Create Tenant</a></li>
				                    			<li><a onclick="insertChildBreadCrumb('View Tenants')" href="<c:out value="${pageUrl}"/>/tenant/<c:out value="viewtenants" />">View Tenants</a></li>
				                    			<li><a onclick="insertChildBreadCrumb('Edit Tenant')" href="<c:out value="${pageUrl}"/>/tenant/<c:out value="edittenant" />">Edit Tenant</a></li>
				                    		
		                        </ul>
		                    </li>		
		                    <li id="dropdownGeneralMenu">
		                    	<a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="insertBreadCrumb('Roles');clearChildBreadCrumb()">Roles<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	
				                    			<li><a  onclick="insertChildBreadCrumb('Create Role')" href="<c:out value="${pageUrl}"/>/role/<c:out value="createrole" />">Create Role</a></li>
				                    		
				                    			<li><a  onclick="insertChildBreadCrumb('View Role')" href="<c:out value="${pageUrl}"/>/role/<c:out value="viewrole" />">View Role</a></li>
				                    		
				                    			<li><a  onclick="insertChildBreadCrumb('Edit Role')" href="<c:out value="${pageUrl}"/>/role/<c:out value="editrole" />">Edit Role</a></li>
				                    		
				                    			<li><a onclick="insertChildBreadCrumb('Assign Roles To Package')" href="<c:out value="${pageUrl}"/>/rolemodule/<c:out value="assignroletomodule" />">Assign Roles To Package</a></li>
				                    			
		                        </ul>
							</li>
							 <li id="dropdownProjectMenu">
	                    		<a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="insertBreadCrumb('Reports');clearChildBreadCrumb()">Reports<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	
				                    			<li><a onclick="insertChildBreadCrumb('Packages')"  href="<c:out value="${pageUrl}"/>/reports/<c:out value="modulereports" />">Packages</a></li>
				                    		
				                    			<li><a  onclick="insertChildBreadCrumb('Tenants')"  href="<c:out value="${pageUrl}"/>/reports/<c:out value="tenantsreports" />">Tenants</a></li>
				                    		
				                    			<li><a onclick="insertChildBreadCrumb('Audit Trail')"  href="<c:out value="${pageUrl}"/>/logs/<c:out value="viewalllogs" />">Audit Trail</a></li>
				                    			
				                    			<li><a onclick="insertChildBreadCrumb('Error Logs')"  href="<c:out value="${pageUrl}"/>/errorlogs/<c:out value="getErrorLog" />">Error Logs</a></li>
				                    			
				                    			<li><a  onclick="insertChildBreadCrumb('Projects') " href="<c:out value="${pageUrl}"/>/reports/<c:out value="projectsreports" />">Projects</a></li>
				                    			
				                    			<li><a onclick="insertChildBreadCrumb('Other Reports') " href="<c:out value="${pageUrl}"/>/reports/<c:out value="otherreports" />">Other Reports</a></li>
				                    			
		                        </ul>
		                    </li>
		              <li id="dropdownconfigurationmenu">
	                    		<a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="insertBreadCrumb('Configurations')">Configurations<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	
				                    			 <li  class="dropdown-submenu">
								                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" >Web Object Editor</a>
								                    <ul class="dropdown-menu">
								                       <li><a onclick="insertChildBreadCrumb('Web Object Editor>>Create') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="createobjspy" />">Create</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Web Object Editor>>Edit') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="editobjspy" />">Edit</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Web Object Editor>>View') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="viewobjspy" />">View</a></li>
								                    </ul>
								                  </li>
				                    		
				                    		<li class="dropdown-submenu">
								                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Bug Tracking</a>
								                     <ul class="dropdown-menu">
								                      <li><a onclick="insertChildBreadCrumb('Bug Tracking>>Create') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="createbugtrk" />">Create</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Bug Tracking>>Edit') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="editbugtrk" />">Edit</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Bug Tracking>>View') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="viewbugtrk" />">View</a></li>
								                      
								                    </ul>
								                  </li>
				                    		
				                    		
				                    		<li class="dropdown-submenu">
								                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Parallel Execution Nodes</a>
								                      <ul class="dropdown-menu">
								                      <li><a onclick="insertChildBreadCrumb('Parallel Execution Nodes>>Create') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="createprlnodes" />">Create</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Parallel Execution Nodes>>Edit') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="editprlnodes" />">Edit</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Parallel Execution Nodes>>View') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="viewprlnodes" />">View</a></li>
								                    </ul>  
								                  </li>
				                    		
				                    			<li class="dropdown-submenu">
								                    <a tabindex="-1" href="#">Enterprise Configurations</a>
								                     <ul class="dropdown-menu">
								                      <li><a onclick="insertChildBreadCrumb('Enterprise Configurations>>Create') "  href="<c:out value="${pageUrl}"/>/configuration/<c:out value="createentpspy" />">Create</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Enterprise Configurations>>Edit') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="editentpspy" />">Edit</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Enterprise Configurations>>View') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="viewentpspy" />">View</a></li>
								                    </ul> 
								                  </li>
								                  <li class="dropdown-submenu">
								                    <a tabindex="-1" href="#">Mobile Configurations</a>
								                     <ul class="dropdown-menu">
								                      <li><a onclick="insertChildBreadCrumb('Mobile Configurations>>Create') "  href="<c:out value="${pageUrl}"/>/configuration/<c:out value="createmobileconfig" />">Create</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Mobile Configurations>>Edit') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="editmobileconfig" />">Edit</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Mobile Configurations>>View') " href="<c:out value="${pageUrl}"/>/configuration/<c:out value="viewmobileconfigs" />">View</a></li>
								                    </ul> 
								                  </li>
								                  <li class="dropdown-submenu">
								                    <a tabindex="-1" href="#">Performance Testing Configuration</a>
								                     <ul class="dropdown-menu">
								                      <li><a onclick="insertChildBreadCrumb('Performance Testing Configuration>>Create') "  href="<c:out value="${pageUrl}"/>/configuration/<c:out value="createperformanceconfigs" />">Create</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Performance Testing Configuration>>Edit') "  href="<c:out value="${pageUrl}"/>/configuration/<c:out value="editperformanceconfigs" />">Edit</a></li>
								                      <li><a onclick="insertChildBreadCrumb('Performance Testing Configuration>>View') "  href="<c:out value="${pageUrl}"/>/configuration/<c:out value="viewperformanceconfigs" />">View</a></li>
								                    </ul> 
								                  </li>
								                  <li class="dropdown-submenu">
								                    <a tabindex="-1" href="#">LDAP Configuration</a>
								                     <ul class="dropdown-menu">
								                      <li><a onclick="insertChildBreadCrumb('LDAP Configuration>>Create') "  href="<c:out value="${pageUrl}"/>/ldapconfiguration/<c:out value="createldapconfig" />">Create</a></li>
								                      <li><a onclick="insertChildBreadCrumb('LDAP Configuration>>Edit') "  href="<c:out value="${pageUrl}"/>/ldapconfiguration/<c:out value="editldapconfig" />">Edit</a></li>
								                      <li><a onclick="insertChildBreadCrumb('LDAP Configuration>>View') "  href="<c:out value="${pageUrl}"/>/ldapconfiguration/<c:out value="viewldapconfig" />">View</a></li>
								                    </ul> 
								                  </li>
								                  
				                    		
		                        </ul>	
		                    </li>
		                    
		             </c:if>
		            <!-- FOR APP ADMIN -->
					<c:if test="${ fn:contains(sessionScope.roleName,'Application Administrator') }"> 
		             <li id="dropdownUserMenu">
		                    	<a class="dropdown-toggle" data-toggle="dropdown" href="#" onclick="insertBreadCrumb('Users')">Users<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	<c:forEach items="${sessionScope.userList}" var="item">
			                    		<c:choose>
			                    			<c:when test="${item.equals('create_user')}">
				                    			<li><a onclick="insertChildBreadCrumb('Create User') " href="<c:out value="${pageUrl}"/>/userprofile/<c:out value="createuser" />">Create User</a></li>
				                    		</c:when>
				                    		<c:when test="${item.equals('import_users')}">
				                    			<li><a onclick="insertChildBreadCrumb('Import Users') " href="<c:out value="${pageUrl}"/>/import/<c:out value="importusersfromldap" />">Import Users From Ldap</a></li>
				                    		</c:when>
				                    		<c:when test="${item.equals('view_users')}">
				                    			<li><a  onclick="insertChildBreadCrumb('View Users') " href="<c:out value="${pageUrl}"/>/userprofile/<c:out value="viewuser" />">View Users</a></li>
				                    		</c:when>
				                    		<c:when test="${item.equals('edit_user')}">
				                    			<li><a onclick="insertChildBreadCrumb('Edit User') " href="<c:out value="${pageUrl}"/>/userprofile/<c:out value="edituser" />">Edit User</a></li>
				                    		</c:when>
			                    		</c:choose>
			                    	</c:forEach>
		                        </ul>
		                    </li>
		                    <li id="dropdownProjectMenu">
	                    		<a class="dropdown-toggle" data-toggle="dropdown" href="#"  onclick="insertBreadCrumb('Projects')">Projects<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	<c:forEach items="${sessionScope.projectsList}" var="item">
										<c:choose>
				                    		<c:when test="${item.equals('view_projects')}">
				                    			<li><a onclick="insertChildBreadCrumb('View Projects') " href="<c:out value="${pageUrl}"/>/project/<c:out value="getprojectsbyid" />">View Projects</a></li>
				                    		</c:when>
				                    		<c:when test="${item.equals('assign_users_to_project')}">
				                    			<li><a onclick="insertChildBreadCrumb('Assign Users To Project') " href="<c:out value="${pageUrl}"/>/userproject/<c:out value="assignuserstoproject" />">Assign Users To Project</a></li>
				                    		</c:when>
				                    		<c:when test="${item.equals('remove_users_from_projects')}">
				                    			<li><a onclick="insertChildBreadCrumb('Edit User') " href="<c:out value="${pageUrl}"/>/userproject/<c:out value="removeusersfromproject" />">Remove Users From Project</a></li>
				                    		</c:when>
				                    		<c:when test="${item.equals('view_modules')}">
				                    			<li><a onclick="insertChildBreadCrumb('View Packages') " href="<c:out value="${pageUrl}"/>/module/<c:out value="viewpackages" />">View Packages</a></li>
				                    		</c:when>
				                    		
									</c:choose>
			                    	</c:forEach>
		                        </ul>
		                    </li>
		                   
		                    <li id="dropdownTeamsMenu">
		                    	<a class="dropdown-toggle" data-toggle="dropdown" href="#"  onclick="insertBreadCrumb('Teams')" >Teams<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	<c:forEach items="${sessionScope.teamList}" var="item">
										<c:choose>
				                    		<c:when test="${item.equals('create_team')}">
				                    			<li><a onclick="insertChildBreadCrumb('Create Team') " href="<c:out value="${pageUrl}"/>/team/<c:out value="createteam" />">Create Team</a></li>
				                    		</c:when>
				                    		<c:when test="${item.equals('view_teams')}">
				                    			<li><a onclick="insertChildBreadCrumb('View Teams') " href="<c:out value="${pageUrl}"/>/team/<c:out value="viewteam" />">View Teams</a></li>
				                    		</c:when>
				                    		<c:when test="${item.equals('edit_team')}">
				                    			<li><a onclick="insertChildBreadCrumb('Edit Team') " href="<c:out value="${pageUrl}"/>/team/<c:out value="editteam" />">Edit Team</a></li>
				                    		</c:when>
				                    		
				                    		<c:when test="${item.equals('assign_users_to_team')}">
				                    			<li><a onclick="insertChildBreadCrumb('Assign Users To Team') " href="<c:out value="${pageUrl}"/>/userteam/<c:out value="assignuserstoteam" />">Assign Users To Team</a></li>
				                    		</c:when>
				                    		
				                    		<c:when test="${item.equals('assign_teams_to_project')}">
				                    			<li><a onclick="insertChildBreadCrumb('Assign Team To Project') " href="<c:out value="${pageUrl}"/>/teamproject/<c:out value="assignprojectstoteam" />">Assign Team To Project</a></li>
				                    		</c:when>
				                    		
				                    		
				                    		
									</c:choose>
			                    	</c:forEach>
		                        </ul>
		                    </li>		
		                    <li id="dropdownTeamsMenu">
		                    	<a class="dropdown-toggle" data-toggle="dropdown"href="#"  onclick="insertBreadCrumb('Roles')" >Roles<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
		                    		<c:forEach items="${sessionScope.roleList}" var="item">
		                    			<c:choose>
		                    				<c:when test="${item.equals('view_roles')}">
		                    					<li><a onclick="insertChildBreadCrumb('View Roles') "  href="<c:out value="${pageUrl}"/>/role/<c:out value="viewrolesappadmin" />">View Roles</a></li>
		                    				</c:when>
		                    				<c:when test="${item.equals('assign_roles_to_user')}">
		                    					<li><a onclick="insertChildBreadCrumb('Assign Roles To User') "  href="<c:out value="${pageUrl}"/>/userrole/<c:out value="addroletouser" />">Assign Roles To User</a></li>
		                    				</c:when>
		                    				<c:when test="${item.equals('assign_roles_to_team')}">
		                    					<li><a onclick="insertChildBreadCrumb('Assign Roles To Team') "  href="<c:out value="${pageUrl}"/>/userrole/<c:out value="addroletoteam" />">Assign Roles To Team</a></li>
		                    				</c:when>
		                    				<c:when test="${item.equals('assign_reports_to_role')}">
		                    					<li><a onclick="insertChildBreadCrumb('Assign Reports To Role') "  href="<c:out value="${pageUrl}"/>/role/<c:out value="assignreportstorole" />">Assign Reports To Role</a></li>
		                    				</c:when>
		                    				
		                    			</c:choose>
		                    		</c:forEach>
		                    	</ul>
		                    </li>	
		                    <li id="dropdownProjectMenu">
	                    		<a class="dropdown-toggle" data-toggle="dropdown" href="#"  onclick="insertBreadCrumb('Reports')" >Reports<b class="caret"></b></a>
		                    	<ul class="dropdown-menu multi-level">
			                    	<c:forEach items="${sessionScope.appAdminReports}" var="item">
			                    	<c:choose>
			                    			<c:when test="${item.equals('view_users_report')}">
				                    			<li><a onclick="insertChildBreadCrumb('Users') "  href="<c:out value="${pageUrl}"/>/reports/<c:out value="userreports" />">Users</a></li>
				                    		</c:when>
				                    			<c:when test="${item.equals('view_teams_reports')}">
				                    			<li><a onclick="insertChildBreadCrumb('Teams') "  href="<c:out value="${pageUrl}"/>/reports/<c:out value="teamsreports" />">Teams</a></li>
				                    		</c:when>
				                    		
			                    			<c:when test="${item.equals('view_audit_logs')}">
				                    			<li><a onclick="insertChildBreadCrumb('Audit Trail') "  href="<c:out value="${pageUrl}"/>/logs/<c:out value="viewlogs" />">Audit Trail</a></li>
				                    		</c:when>
				                    			
				                    		
				                    		
				                    </c:choose>
										
			                    	</c:forEach>
			                    	
		                        </ul>
		                    </li>	
		                    			
						</c:if>
						
            </ul>
        </div><!--/.nav-collapse -->
       
    </div>
     
    </div>
   
</div>
 
</div>
	 &nbsp;&nbsp;<span  id="bc_parent"></span><span id="bc_child"></span>
 
    <%@include file="/WEB-INF/jsp/template/js.jsp" %>
  