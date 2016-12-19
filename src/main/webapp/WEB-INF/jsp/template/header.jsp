<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
		<title>DWR Dev</title>
		<script type="text/javascript" src="/QlikTestAdmin/dwr/engine.js"></script>
		<script type="text/javascript" src="/QlikTestAdmin/dwr/interface/dwrService.js"></script>
		<script type="text/javascript" src="/QlikTestAdmin/dwr/util.js"></script>
		<script src="http://code.jquery.com/jquery-latest.js"></script>
		<script>
		function onloadmethod() {
		      dwr.engine.setActiveReverseAjax(true);
		      dwrService.sendNotifications();
		   }
			
		function reversajaxtest(msg){
			  alert(msg);
				   
		   }
		function clearBreadCrumb(){
			 sessionStorage.bcchild = '';
			 sessionStorage.bcparent = '';
		}
		function clearChildBreadCrumb(){
			localStorage.bcchild='';
		}
		</script>
		<script type="text/javascript">
        function noBack()
         {
             window.history.forward()
         }
        noBack();
        window.onload = noBack;
        window.onpageshow = function(evt) { if (evt.persisted) noBack() }
        window.onunload = function() { void (0) }
        function openModal(title,data){
			bootbox.alert("<center><h5><b><u><i>"+title+"</i></u></b></h5><p>"+data+"</p></center>");
			
		}
    </script>
		
		
		
		<!-- This below code is used for display the place holder value in IE Browser -->
		 <script type="text/javascript">
	 $(function() {
	   //Run this script only for IE
	   if (navigator.appName === "Microsoft Internet Explorer") {
	     $("input[type=text]").each(function() {
	       var p;
	      // Run this script only for input field with placeholder attribute
	       if (p = $(this).attr('placeholder')) {
	       // Input field's value attribute gets the placeholder value.
	         $(this).val(p);
	         $(this).css('color', 'gray');
	         // On selecting the field, if value is the same as placeholder, it should become blank
	         $(this).focus(function() {
	           if (p === $(this).val()) {
	             return $(this).val('');
	           }
	         });
	          // On exiting field, if value is blank, it should be assigned the value of placeholder
	         $(this).blur(function() {
	           if ($(this).val() === '') {
	             return $(this).val(p);
	           }
	         });
	       }
	     });
	     $("input[type=password]").each(function() {
	       var e_id, p;
	       if (p = $(this).attr('placeholder')) {
	         e_id = $(this).attr('id');
	         // change input type so that the text is displayed
	         document.getElementById(e_id).type = 'text';
	         $(this).val(p);
	         $(this).focus(function() {
	           // change input type so that password is not displayed
	           document.getElementById(e_id).type = 'password';
	           if (p === $(this).val()) {
	             return $(this).val('');
	           }
	         });
	         $(this).blur(function() {
	           if ($(this).val() === '') {
	             document.getElementById(e_id).type = 'text';
	             $(this).val(p);
	           }
	         });
	       }
	     });
	 	  $("textarea").each(function() {
	       var p;
	      // Run this script only for input field with placeholder attribute
	       if (p = $(this).attr('placeholder')) {
	       // Input field's value attribute gets the placeholder value.
	         $(this).val(p);
	         $(this).css('color', 'gray');
	         // On selecting the field, if value is the same as placeholder, it should become blank
	         $(this).focus(function() {
	           if (p === $(this).val()) {
	             return $(this).val('');
	           }
	         });
	          // On exiting field, if value is blank, it should be assigned the value of placeholder
	         $(this).blur(function() {
	           if ($(this).val() === '') {
	             return $(this).val(p);
	           }
	         });
	       }
	     });



	     $('form').submit(function() {
	       //Interrupt submission to blank out input fields with placeholder values
	       $("input[type=text]").each(function() {
	         if ($(this).val() === $(this).attr('placeholder')) {
	           $(this).val('');
	         }
	       });
	      $("input[type=password]").each(function() {
	         if ($(this).val() === $(this).attr('placeholder')) {
	            $(this).val('');
	         }
	       });
	 $("textarea").each(function() {
	         if ($(this).val() === $(this).attr('placeholder')) {
	            $(this).val('');
	         }
	       });
	     });
	   }
	 });
	 </script>
	
	<!-- This Above code is used for display the place holder value in IE Browser -->

		
</head>

<body onload="onloadmethod()">
<ul id="sendNotifications">
</ul>
<table class="tblHeader" style="width:99%">
	<tr>
		<td align="left">
			<a href="#"><img src="<c:url value="/static/images/qliktest-logo.png"/>" alt="QlikTest" title="QlikTest" /></a>
		</td>
		<td align="right">Hi, <font color='#01325c'><c:out value="${sessionScope.username}" /> (<c:out value="${sessionScope.roleName}"/>)</font> &nbsp;&nbsp; | 
			<font size="2"><a href="<%= request.getContextPath() %>/content/password/changepassword" onclick="insertBreadCrumb('ChangePassword');insertChildBreadCrumb('')">Change Password</a>&nbsp;&nbsp;<a onclick="clearBreadCrumb()" href="<%= request.getContextPath() %>">Sign Out</a></font>
		</td>
	</tr>
	
</table>
<center>
<div style="margin: -30px auto 10px auto; width:300px; font-size: 18px;"><b><span  style=" color:#0088cc" >QlikTest</span>
<span style="color:#01428c">Control Panel</span></b></div>
</center>
</body>
</html>