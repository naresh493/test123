<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script type="text/javascript" src="<c:url value="/static/js/jquery.js" />"></script> 
<script type="text/javascript" src="<c:url value="/static/js/jquery.1.7.0.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/bootstrap.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/jquery.tablesorter.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/jquery-ui-1.8.16.custom.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/jquery.tablesorter.pager.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/bootstrap-dropdown.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/bootstrap-twipsy.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/bootstrap-scrollspy.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/bootstrap-alerts.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/bootstrap-modal.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/bootstrap-popover.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/bootstrap-tabs.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/bootstrap-buttons.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/angular-1.0.0rc3.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/static/js/underscore-min.js" />"></script>

   
    <script type="text/javascript">
    $( document ).ready(function() {
        var defaultCSS = document.getElementById('bootstrap-css');
        function changeCSS(css){
            if(css) $('head > link').filter(':first').replaceWith('<link rel="stylesheet" href="'+ css +'" type="text/css" />'); 
            else $('head > link').filter(':first').replaceWith(defaultCSS); 
        }
   });
    </script>
        
         
<script type="text/javascript">
   	$(document).ready(function () {
           $('#moduleDiv').hide();
           $('#licenseDiv').hide();
           $('#trCreateModule').show();
           $('#trCreateProject').show();
           $('#trCreateLicense').show();
           $('#trCreateLicenseAuth').show();
		   $('#btnCreate').show();
           $('#trEditModule').hide();
           $('#trDeleteModule').hide();
           $('#trEditProject').hide();
           $('#trDeleteProject').hide();
           $('#trEditLicense').hide();
           $('#trDeleteLicense').hide();
           $('#trEditLicenseAuth').hide();
           $('#trDeleteLicenseAuth').hide();
           $('#trEditPermission').hide();
           $('#trDeletePermission').hide();
           $('#btnEdit').hide();
           $('#btnDelete').hide();
           $('#trModule').hide();
           $('#trUsers').hide();
           $('#trPerms').hide();
           $('#trviewusers').hide();
           $('#normal').show();
           $('#create').hide();
           $('#view').hide();
           $('#edit').hide();
           $('#trVersion').hide();
           
    });
   
 	/* function showLdap(){
   		$('#trLdap').hide();
   		$('#trDisplayResult').hide();
   		//$('#tblcreateNewUser').show();
   		$('#usercret').show();
   		$('#assignRoleUser').show();
   		
   		
   	} */
 	/* function checkLdap(){
   	   	if(document.URL.indexOf('createuser') != -1){
	   	   	var request = $.ajax({
				url: '/QlikTestAdmin/content/userprofile/checkldap',
				type: "get",
				data: {},
				dataType:'html'
			});
			request.done(function(response, textStatus, jqXHR){
				if(response == 0){
					$('#trLdap').show();
					$('#tblcreateNewUser').hide();
				}else{
					$('#trLdap').hide();
					$('#tblcreateNewUser').show();
				}
			});
   	   	}
   	}

   	function importUsersFromLdap(){
   		
   		$('#usercret').hide();
   	 	//$('#signup').attr("action", "/QlikTestAdmin/content/userprofile/importfromldap");
   		var request = $.ajax({
			url: '/QlikTestAdmin/content/userprofile/importfromldap',
			type: "post",
			data: {},
			dataType:'html'
		});
		request.done(function(response, textStatus, jqXHR){
			//$('#tblcreateNewUser').hide();
			$('#btnImportFromLdap').attr('disabled','disabled');
			$('#trDisplayResult').show();
			$('#trDisplayResult').html(response+'<br/><br/>');
			$('#trDisplayResult').css('color','green').css('font-weight','bold').css('text-align','center');
		});
   	} */
   	
   	
   	function CreateDefaultUser(action){
   		
   	   	if(action == 'getpermlist'){
	   	   	$('#signup').attr("action", "/QlikTestAdmin/content/userprofile");
			var request = $.ajax({
				url: 'getpermforrole',
				type: "get",
				data: {roleId: $('#projectList').val()+''},
				dataType:'html'
			});
			request.done(function(response, textStatus, jqXHR){
				$('#trPermissions').show();
				$('#permList').html(response+'<br/><br/>');
			});
   	   	}else if(action == 'savedefaultuser'){
   	   		$('#signup').attr("action", "/QlikTestAdmin/content/userprofile/savedefaultuser");
   	   	}
   	}

    function populateRoleDropDown(type){
    	var request = $.ajax({
			url: 'getroles',
			type: "get",
			data: {type: type},
			dataType:'html'
		});
		request.done(function(response, textStatus, jqXHR){
			if(type=='create'){
				$('#selectedRoleCreate').html(response+'<br/>');
			}else if(type=='edit'){
				$('#selectedRoleEdit').html(response+'<br/>');
			}else if(type=='delete'){
				$('#selectedRoleDelete').html(response+'<br/>');
			}
		});
    }
    
    
   	function getPermissions(type){
    	if(type=='create'){
    		$('#signup').attr("action", "/QlikTestAdmin/content/role");
       		var request = $.ajax({
    			url: 'getpermissions',
    			type: "get",
    			data: {},
    			dataType:'html'
    		});
    		request.done(function(response, textStatus, jqXHR){
				$('#trSelectExistingPermissions').show();
				$('#createPermissionsList').html(response+'<br/>');
				$('#tblEditRole').hide();
				$('#tblDeleteRole').hide();
				$('#btnSubmit').val('Create');
    		});
		}else if(type=='edit'){
			$('#tblEditRole').show();
			if($('#selectedRoleEdit').val() == ''){
				$('#trEditPermissions').hide();
				$('#trEditSelectExistingPermissions').hide();
				$('#trEditNewPermission').hide();
				$('#trOr').hide();
			} else {
				var request = $.ajax({
					url: '/QlikTestAdmin/content/userprofile/getpermforrole',
					type: "get",
					data: {roleId: $('#selectedRoleEdit').val()+''},
					dataType:'html'
				});
				var request = $.ajax({
					url: '/QlikTestAdmin/content/userprofile/assignreportstorole',
					type: "get",
					data: {roleId: $('#selectedRoleEdit').val()+''},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trEditPermissions').show();
					$('#permList').html(response+'<br/>');
				});
	       		var request = $.ajax({
	    			url: '/QlikTestAdmin/content/role/getpermissionsnotinroleid',
	    			type: "get",
	    			data: {roleId: $('#selectedRoleEdit').val()+''},
	    			dataType:'html'
	    		});
	    		request.done(function(response, textStatus, jqXHR){
					$('#trEditSelectExistingPermissions').show();
					$('#trEditNewPermission').show();
					$('#trOr').show();
					$('#editPermissionsList').html(response+'<br/>');
					$('#btnSubmit').val('Update');
	    		});
			}
		}else if(type=='delete'){
			if($('#selectedRoleDelete').val() == ''){
				$('#trDeletePermissions').hide();
			}else{
				var request = $.ajax({
					url: '/QlikTestAdmin/content/userprofile/getpermforrole',
					type: "get",
					data: {roleId: $('#selectedRoleDelete').val()+''},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trDeletePermissions').show();
					$('#deletePermissionsList').html(response+'<br/>');
					$('#btnSubmit').val('Delete');
				});
			}
		}
    }

   	
   
    
    
    function getAssignedPermissions(path){
		 if(path == 'module'){
				var request = $.ajax({
					url: 'getpermissionsbylicense',
					type: "get",
					data: {licenseId: $('#idModule').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trPerm td:nth-child(1) #to').html(response);
					
				});
		 }
	}
    
    
    function getAvailablePermissions(path){
		 if(path == 'module'){
				var request = $.ajax({
					url: 'getapermissionsbylicense',
					type: "get",
					data: {licenseId: $('#idModule').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#traPermissions td:nth-child(1) #from').html(response);
					
				});
		 }
	}
    
    function getAssignedLicensesToProject(path){
		 if(path == 'projectmodule'){
				var request = $.ajax({
					url: 'getprojectbylicense',
					type: "get",
					data: {projectId: $('#idProject').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trPerm td:nth-child(1) #to').html(response);
					
				});
		 }
	}
    
    
    function getAvailableLicensesToProject(path){
		 if(path == 'projectmodule'){
				var request = $.ajax({
					url: 'getaprojectbylicense',
					type: "get",
					data: {projectId: $('#idProject').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#traPermissions td:nth-child(1) #from').html(response);
					
				});
		 }
	}
   
   
  
  
   function getRoleBasedPermissions(path){
	 		 if(path == 'rolepermissions'){
				var request = $.ajax({
					url: 'getprojectbypermissionrole',
					type: "get",
					data: {roleId: $('#idRole').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
				$('#trPerm td:nth-child(1) #to').html(response);
					
				});
		 }
	}
 
   function getRoleAssignBasedPermissions(path){
		 if(path == 'rolepermissions'){
			var request = $.ajax({
				url: 'getprojectbypermissionrole1',
				type: "get",
				data: {roleId: $('#idRole').val()},
				dataType:'html'
			});
			request.done(function(response, textStatus, jqXHR){
			$('#traPermissions td:nth-child(1) #from').html(response);
				
			});
	 }
}

   function getTenantBasedProject(path){
	 	 if(path == 'tenant'){
			var request = $.ajax({
				url: 'getprojectbytenant',
				type: "get",
				data: {tenantId: $('#tenantList').val()},
				dataType:'html'
			});
				request.done(function(response, textStatus, jqXHR){
				$('#trProjects td:nth-child(1) #from').html(response);
				
			});
	 }
}
   function getTenantNotInBasedProject(path){
	  	 if(path == 'tenant'){
			var request = $.ajax({
				url: 'getprojectnotbytenant',
				type: "get",
				data: {tenantId: $('#tenantList').val()},
				dataType:'html'
			});
			request.done(function(response, textStatus, jqXHR){
			$('#trAProjects td:nth-child(1) #to').html(response);
				
			});
	 }
}
   
   function getRoleBasedLicenses(path){
	 	 if(path == 'rolemodule'){
			var request = $.ajax({
				url: 'getrolebylicense',
				type: "get",
				data: {moduleId: $('#idModule').val()},
				dataType:'html'
			});
				request.done(function(response, textStatus, jqXHR){
				$('#trPerm td:nth-child(1) #to').html(response);
				
			});
	 }
}
   function getRoleBasedAvalableLicenses(path){
	 	 if(path == 'rolemodule'){
			var request = $.ajax({
				url: 'getroleavalablelicense',
				type: "get",
				data: {moduleId: $('#idModule').val()},
				dataType:'html'
			});
			request.done(function(response, textStatus, jqXHR){
					$('#traPermissions td:nth-child(1) #from').html(response);
				
			});
	 }
}
    
    function ManageTeams(path){
  		 if(path == 'team'){
				var request = $.ajax({
					url: 'getusersdata',
					type: "get",
					data: {teamid: $('#idTeam').val(),uArray:$('#idUsers').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					
					$('#trUsers').show();
					$('#trUsers td:nth-child(2) #idUsers').html(response);
				});
				hide($('#idUsers').val());
		 }
	}
    
    
    
    
    function getAssignedRolesToReport(path){
 		 if(path == 'role'){
 				var request = $.ajax({
					url: 'getreportsdata',
					type: "get",
					data: {roleId: $('#idRole').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trReports td:nth-child(1) #from').html(response);
				});
		 }
	}
   
    function getAvailableRolesToReport(path){
		 if(path == 'role'){
				var request = $.ajax({
					url: 'getreportsdata1',
					type: "get",
					data: {roleId: $('#idRole').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#traReports td:nth-child(1) #to').html(response);
				});
		 }
	}
  
    
    
   
    function getReportModule(path){
		 if(path == 'module'){
				var request = $.ajax({
					url: 'getmodulereportsdata',
					type: "get",
					data: {moduleId: $('#idModule').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trReports td:nth-child(1) #from').html(response);
				});
		 }
	}
 
    function getReportModule1(path){
		 if(path == 'module'){
				var request = $.ajax({
					url: 'getmodulereportsdata1',
					type: "get",
					data: {moduleId: $('#idModule').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#traReports td:nth-child(1) #to').html(response);
				});
		 }
	}
    
    
    function getRoleModule(path){
		 if(path == 'module'){
				var request = $.ajax({
					url: 'getmoduleroledata',
					type: "get",
					data: {moduleId: $('#idModule').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trReports td:nth-child(1) #idReport').html(response);
				});
		 }
	}

   function getRoleModule1(path){
		 if(path == 'module'){
				var request = $.ajax({
					url: 'getmoduleroledata1',
					type: "get",
					data: {moduleId: $('#idModule').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#Reports td:nth-child(1) #idReport1').html(response);
				});
		 }
	}
   
   
   
   <!-- For assigning the users to the project -->
   function getAssignedUsers(path){
		 if(path == 'userproject'){
				var request = $.ajax({
					url: 'getassignedusers',
					type: "get",
					data: {projectId: $('#idProject').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trAsUsers td:nth-child(1) #to').html(response);
				});
		 }
	}
   
   
   function getAvailableUsers(path){
		 if(path == 'userproject'){
				var request = $.ajax({
					url: 'getavailableusers',
					type: "get",
					data: {projectId: $('#idProject').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#traUsers td:nth-child(1) #from').html(response);
				});
		 }
	}
   
   
   <!-- For assigning the users to the team -->
   
   
   
   
   function getAssignedTeamUsers(path){
		 if(path == 'userteam'){
				var request = $.ajax({
					url: 'getassignedteamusers',
					type: "get",
					data: {teamId: $('#idTeam').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trtAsUsers td:nth-child(1) #to').html(response);
				});
		 }
	}
 
 
 function getAvailableTeamUsers(path){
		 if(path == 'userteam'){
				var request = $.ajax({
					url: 'getavailableteamusers',
					type: "get",
					data: {teamId: $('#idTeam').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trtaUsers td:nth-child(1) #from').html(response);
				});
		 }
	}
   
   <!-- For assigning the projects to the team -->
   
   
   function getAssignedProjects(path){
		 if(path == 'teamproject'){
				var request = $.ajax({
					url: 'getassignedteamprojects',
					type: "get",
					data: {teamId: $('#idTeam').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trtaProjects td:nth-child(1) #to').html(response);
				});
		 }
	}


function getAvailableProjects(path){
		 if(path == 'teamproject'){
				var request = $.ajax({
					url: 'getavailableteamprojects',
					type: "get",
					data: {teamId: $('#idTeam').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trtProjects td:nth-child(1) #from').html(response);
				});
		 }
	}
	
	
   <!-- for assigning the roles to the user -->
   function getAssignedRoles(path){
		 if(path == 'userrole'){
				var request = $.ajax({
					url: 'getassignedroles',
					type: "get",
					data: {userId: $('#idUser').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trRoles td:nth-child(1) #to').html(response);
				});
		 }
	}


function getAvailableRoles(path){
		 if(path == 'userrole'){
				var request = $.ajax({
					url: 'getavailableroles',
					type: "get",
					data: {userId: $('#idUser').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#traRoles td:nth-child(1) #from').html(response);
				});
		 }
	}
	
	
	
	<!-- For assigning the roles to the team -->
	function getAssignedRolesToTeam(path){
		 if(path == 'teamrole'){
				var request = $.ajax({
					url: 'getassignedrolestoteam',
					type: "get",
					data: {teamId: $('#idTeam').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#trRoles td:nth-child(1) #to').html(response);
				});
		 }
	}


function getAvailableRolesToTeam(path){
		 if(path == 'teamrole'){
				var request = $.ajax({
					url: 'getavailablerolestoteam',
					type: "get",
					data: {teamId: $('#idTeam').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					$('#traRoles td:nth-child(1) #from').html(response);
				});
		 }
	}
	
	
	
	
   function ViewOtherReports(path){
	   var check = document.getElementById('ch1').value;
	   alert(" check box value "+check);
		 if(path == 'reports'){
				var request = $.ajax({
					url: 'editotherreports',
					type: "get",
					data: {reportId: $('#idReport').val()},
					dataType:'html'
					
				});alert( $('#idReport').val());
				var check = document.getElementById('ch1').value;
				request.done(function(response, textStatus, jqXHR){
					
				});
		 }
	}
   
    function ManageLicenseAuth(path){
    	if(path == 'licenseauth'){
			var request = $.ajax({
				url: 'getpermissionsdata',
				type: "get",
				data: {licenseAuthId: $('#idLicenseAuth').val()},
			dataType:'html'
			});
			request.done(function(response, textStatus, jqXHR){
				
				$('#trPerms').show();
				$('#trPerms td:nth-child(2) #idPermissions').html(response);
			});
			hide($('#idPermissions').val());
	 }
    }
    
function selectAllValues(path){
    	
    	$("#to option").prop("selected", "selected");
    	$("#toreports option").prop("selected", "selected");
    	$("#signup").submit();
    	
}

    function ManageRemoveUsers(path){
 		 if(path == 'project'){
				var request = $.ajax({
					url: 'getremoveusersdata',
					type: "get",
					data: {projectid: $('#idProject').val(),uArray:$('#idUsers').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					
					$('#trUsers').show();
					$('#trUsers td:nth-child(2) #idUsers').html(response);
				});
				hide($('#idRoles').val());
		 }
	}
    function ManageRemoveTeams(path){
		 if(path == 'team'){
				var request = $.ajax({
					url: 'getTeamsdata',
					type: "get",
					data: {projectId: $('#idProject').val()},
					dataType:'html'
				});
				request.done(function(response, textStatus, jqXHR){
					
					$('#trTeams').show();
					$('#trTeams td:nth-child(2) #idTeams').html(response);
				});
				hide($('#idTeams').val());
		 }
	}
  
   
    
    function populateLicenseName(){
    	var data = document.getElementById('licenseName').value;
    	alert(data);
    	$('#trLicenseName').show();
		$("#licenseName").attr("value",$("#trChkBoxLicense input[type='radio']:checked").val());
	}
	
	function populateModuleName(){
		
		$('#trModuleName').show();
		$("#moduleName").attr("value",$("#trChkBoxModule input[type='radio']:checked").val());
	}

	function populateProjectName(){
		$("#projectName").attr("value",$("#trChkBoxProject input[type='radio']:checked").val());
	}

	function populateLicenseAuthName(){
		$('#trLicenseAuthName').show();
		$("#licenseAuthName").attr("value",$("#trChkBoxLicenseAuth input[type='radio']:checked").val());
	}
	
	function populatePermission(){
		
		$("#permissionName").attr("value",$("#trChkBoxPermission input[type='radio']:checked").val());
	}
	
	
   	function enableCreateRole(){
		$('#trSelectExistingPermissions').css('display','block');
		$('#trAddNewPermission').css('display','block');
		$("#btnSubmit").css('display','block');
		$("#tblCreateRole").addClass("tblFormData");
		$('#trBtnAddPermissions').html('');
	}

	function doValidationForCreateRole(){
		return false;
	}

	function showSubMenu(){
		$('#ul.nav li.dropdown:hover li.dropdown-submenu ul.dropdown-menu > li').css('display','block');
		$('#ul.nav li.dropdown:hover li.dropdown-submenu ul.dropdown-menu > li').attr('display','block');
	}

	function showLicense1(name) {
		 $('#licenseDiv').show();
		 $('#trLicenseAuth').hide();
	}
	
	function showLicense2(name) {
		$('#licenseDiv').show();
		$('#trLicenseAuth').show();
	}

	function showLicense3(name) {
		$('#licenseDiv').show();
		if($("#chkBoxSecurityTesting").is(':checked')){
			 $('#divSecurityWrite').show();
			 $('#divSecurityExecute').show();
			 $('#trTestResultsPerformance').show();
		 }else{
			 if($("#chkBoxFunctionalTesting").is(':checked')){
				 $('#trTestResultsFunctional').show();
			 }else{
				 $('#trTestResultsFunctional').hide();
			 }
			 if($("#chkBoxPerformanceTesting").is(':checked')){
				 $('#trTestResultsPerformance').show();
			 }else{
				 $('#trTestResultsPerformance').hide();
			 }
			 $('#divSecurityWrite').hide();
			 $('#divSecurityExecute').hide();
			 if($("#chkBoxPerformanceTesting").is(':checked')){
				 $('#trTestResultsPerformance').show();
			 }else{
				 $('#trTestResultsPerformance').hide();
			 }
		 }

		if((!$("#chkBoxFunctionalTesting").is(':checked') && !$("#chkBoxPerformanceTesting").is(':checked')
	    	     && !$("#chkBoxSecurityTesting").is(':checked')) || $("#projectName").val()=='' ){
	    	   $('#licenseDiv').hide();
	    	   $('#chkBoxWrite').attr("checked",false);
	    	   $('#chkBoxExecute').attr("checked",false);
	    	   $('#chkBoxlicenseTestResultsFunctional').attr("checked",false);
	    	   $('#chkBoxlicenseTestResultsPerformance').attr("checked",false);
	   	 }
	}

	function showWrite(){
		$('#trWrite').show();
		if($("#chkBoxWrite").is(':checked')){
			if($("#chkBoxFunctionalTesting").is(':checked')){
				$('#divFunctionalWrite').show();
			}else{
				$('#divFunctionalWrite').hide();
			}
			if($("#chkBoxPerformanceTesting").is(':checked')){
				$('#divPerformanceWrite').show();
			}else{
				$('#divPerformanceWrite').hide();
				}
			if($("#chkBoxSecurityTesting").is(':checked')){
				$('#divSecurityWrite').show();
			}else{
				$('#divSecurityWrite').hide();
			}
		}else{
			$('#trWrite').hide();
		}

		if((!$("#chkBoxFunctionalTesting").is(':checked') && !$("#chkBoxPerformanceTesting").is(':checked')
	    	     && !$("#chkBoxSecurityTesting").is(':checked')) || $("#projectName").val()=='' ){
	    	   $('#licenseDiv').hide();
	   	 }
	}

	function showExecute(){
		$('#trExecuteInner').show();
		if($("#chkBoxExecute").is(':checked')){
			if($("#chkBoxFunctionalTesting").is(':checked')){
				$('#divFunctionalExecute').show();
			}else{
				$('#divFunctionalExecute').hide();
			}
			if($("#chkBoxPerformanceTesting").is(':checked')){
				$('#divPerformanceExecute').show();
			}else{
				$('#divPerformanceExecute').hide();
				}
			if($("#chkBoxSecurityTesting").is(':checked')){
				$('#divSecurityExecute').show();
			}else{
				$('#divSecurityExecute').hide();
			}
		}else{
			$('#trExecuteInner').hide();
		}

		if(!$("#chkBoxFunctionalTesting").is(':checked') && !$("#chkBoxPerformanceTesting").is(':checked')
	    	     && !$("#chkBoxSecurityTesting").is(':checked')){
	    	   $('#licenseDiv').hide();
	   	 }
	}

	function clickWriteAutomationTestCase(){
		$('#writeAutomationTestCaseOptions').show();		
	}

	function clickWriteManualTestCase(){
		$('#writeManualTestCaseOptions').show();		
	}
	
	function createDialog(title,text) {
		
		$('#maindiv').append("<div id='myAlert' class='dialog' title='"+title+"'><p>"+text+"</p></div>");
	  $("#myAlert").dialog({
	        resizable: true,
	        height:140,
	        modal: true,
	        overlay: { opacity: 0.5, background: 'red'}
	        
	    });
	
	} 

	function DeleteConformation(){
		
		var answer=confirm("Users from team will also removed!!!!Are you sure you want to continue");
		if (answer==true)
		  {
		    return true;
		  }
		else
		  {
		    return false;
		  }
	}
	function CheckRole(){
		var ans=document.getElementById("deleteId");
		alert(ans);
	}
	
	function checkEmail(path) {

		 var emailID = path;
		   atpos = emailID.indexOf("@");
		   dotpos = emailID.lastIndexOf(".");
		   if (atpos < 1 || ( dotpos - atpos < 2 )) 
		   {
		       alert("Please enter correct email ID")
		       document.myForm.EMail.focus() ;
		       return false;
		   }
		   return( true );
	 }
	function checkSpaces(path){
		var iChars ="^[a-zA-Z0-9]*$";
		var pName = path;
		if(pName.trim()=='')
		{
		alert("Spaces are Not Allowed");
		document.getElementById('projectName').value="";
		return false;
		}
	
	if((pName.match("^[a-zA-Z0-9]*$")) == null || (pName.match("^[a-zA-Z0-9]*$")) == "null" || (pName.match("^[a-zA-Z0-9]*$")) == "NULL"){
		alert("Special Characters are Not Allowed");
		 document.getElementById('projectName').value="";
		return false;
		}
	}
	function checkFormSpaces(){
		var prname = document.getElementById("projectName").value;
		if(prname.trim() == ''){
			alert("Spaces & Special Characters Not Allowed");
			return false;
		}
		else{
			return true;
		}
	}
	/*  function savetenant(path){
		alert("2");
		var request = $.ajax({
			url: '/QlikTestAdmin/content/userprofile/saveDefaultUserFromProject',
			type: "POST",
			data:'tenantname='+ $("#tenantname").val()+'&companyname='+$("#companyname").val()+'&displayname='+$("#displayname").val()+'&firstname='+$("#firstname").val()+'&lastname='+$("#lastname").val()+'&username='+$("#username").val()+'&emailid='+$("#emailid").val()+'&landline='+$("#landline").val()+'&mobile='+$("#mobile").val(),
			dataType:'html'
		});
		request.done(function(response, textStatus, jqXHR){
			$('#trTenants td:nth-child(2) #tenantList').html(response);
			});
		} */
	 
	 
		 function savetenantforpopup(path){
		
		var request = $.ajax({
			url: '/QlikTestAdmin/content/userprofile/saveDefaultUserFromProject',
			type: "POST",
			data:'tenantname='+ $("#tenantname").val()+'&companyname='+$("#companyname").val()+'&displayname='+$("#displayname").val()+'&firstname='+$("#firstname").val()+'&lastname='+$("#lastname").val()+'&username='+$("#username").val()+'&emailid='+$("#emailid").val()+'&landline='+$("#landline").val()+'&mobile='+$("#mobile").val(),
			dataType:'html'
		});
		
		request.done(function(response, textStatus, jqXHR){
			$('#trTenants td:nth-child(2) #tenantList').html(response);		
			var split = response.split(':');
			$('#tenantss').text(split[0]);
			
		});
		
			
           
		 } 

	 function checkFormForSpaces(form)
	  {
	    // validation fails if the input is blank
	    if(form.inputfield.value == '') {
	      alert("Spaces Not Allowed");
	      form.inputfield.focus();
	      return false;
	    }

	    // regular expression to match only alphanumeric characters and spaces
	    var re = /^[\w ]+$/;

	    // validation fails if the input doesn't match our regular expression
	    if(!re.test(form.inputfield.value)) {
	      alert("Special Characters Not Allowed");
	      form.inputfield.focus();
	      return false;
	    }

	    // validation was successful
	    return true;
	  }
	 
	
	 if (sessionStorage.bcparent) {
		 sessionStorage.bcparent = sessionStorage.bcparent;
		} else {
			sessionStorage.bcparent = "";
		}
	  $("#bc_parent").text(sessionStorage.bcparent);
		
	  if (sessionStorage.bcchild) {
		  sessionStorage.bcchild = sessionStorage.bcchild;
		} else {
			sessionStorage.bcchild = "";
		}
	  $("#bc_child").text(sessionStorage.bcchild);
	  
	
</script>