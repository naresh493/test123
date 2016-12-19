<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="com.infotree.qliktest.admin.common.AppInfo"%>
<%@page session="true" %>

<%
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", -1);
	pageContext.setAttribute("showLoginForm", AppInfo.isNoPreAuth());
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>${showLoginForm ? 'QlikTest Administration' : 'Access Denied'}</title>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Le styles -->
    <link href="static/css/bootstrap-2.3.2.css" rel="stylesheet">
    <style type="text/css">
      body {
       min-height: 650px;
      }
      .form-signin {
        max-width: 330px;
        padding: 19px 29px 29px 29px;
        height:300px;
        margin:100px auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading{
        margin-bottom: 10px;
        text-align: center;
      }
      .form-signin .checkbox{
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 14px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
    </style>
   <!--  <link href="static/css/bootstrap-responsive.css" rel="stylesheet"> -->
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

    <!-- Fav and touch icons -->
	 <link rel="apple-touch-icon-precomposed" sizes="144x144" href="static/images/apple-touch-icon-144-precomposed.png" />
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="static/images/apple-touch-icon-114-precomposed.png" />
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="static/images/apple-touch-icon-72-precomposed.png" />
    <link rel="apple-touch-icon-precomposed" href="static/images/apple-touch-icon-57-precomposed.png" />
	<link rel="shortcut icon" href="static/images/favicon.png" />  </head>
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	
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

  <body>
   <c:if test="${showLoginForm}">
    <div class="container">
	<%
    request.getSession().removeAttribute("userid");
    request.getSession().invalidate();
    %>
      <form id="loginForm" class="form-signin" action="content/login" method="post">
        <h3 class="form-signin-heading" ><img src="<c:url value="/static/images/qliktest-logo.png"/>"/></h3>
        <c:if test='${not empty param.error}'> 
        	<font color='red' size="3"> 
				Invalid Username/password. 
			</font>
		</c:if>	
        <input class="input-block-level" autocomplete="off" id="userId" type="text" name="username" placeholder="User Name" required="required"/>
        <input class="input-block-level" autocomplete="off" id="password" type="password" name="password" placeholder="Password" required="required"/>
       
        <div class="loginbutton">
        <span style="float: left;"><button class="btn btn-primary" type="submit">
        	Sign in</button></span>
         <span style="float: left;"><button class="btn btn-primary" type="reset">Reset</button></span>
          <a href="content/password/forgotpassword">Forgot Password</a>
         </div>
         
      </form>
      
     </div>
	
    <!-- Placed at the end of the document so the pages load faster -->
<!-- placed at bottom so that the page loads faster -->
	<script src="static/js/jquery.js"></script>
    <script src="static/js/bootstrap-transition.js"></script>
    <script src="static/js/bootstrap-alerts.js"></script>
    <script src="static/js/bootstrap-modal.js"></script>
    <script src="static/js/bootstrap-dropdown.js"></script>
    <script src="static/js/bootstrap-scrollspy.js"></script>
    <script src="static/js/bootstrap-tabs.js"></script>
    <script src="static/js/bootstrap-tooltip.js"></script>
    <script src="static/js/bootstrap-popover.js"></script>
    <script src="static/js/bootstrap-buttons.js"></script>
    <script src="static/js/bootstrap-collapse.js"></script>
    <script src="static/js/bootstrap-carousel.js"></script>
    <script src="static/js/bootstrap-typeahead.js"></script>
    
</c:if>
</body>
</html>