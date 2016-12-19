<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="com.infotree.qliktest.admin.common.AppInfo"%>

<!DOCTYPE html>
<html lang="en"><head>
<%
	//out.println("in new error jsp");
	//TODO: remove this  when pre-authentication solution is in place
	pageContext.setAttribute("showLoginForm", AppInfo.isNoPreAuth());

%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <title>Qlik Test Administration</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Le styles -->
    <link href="static/css/bootstrap-2.3.2.css" rel="stylesheet">
    <style type="text/css">
      body {
       
      }
      .form-signin {
         max-width: 300px;
        padding: 19px 29px 29px;
        margin: 50px auto 20px;
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
    <link href="static/css/bootstrap-responsive.css" rel="stylesheet">
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

  <body>
    <div class="container">
      <form class="form-signin" action="login" method="POST">
        <h3 class="form-signin-heading">Welcome to the QlikTest Admin Console</h3>
			<c:if test='${not empty error}'> 
				<font color='red'> 
					Login error. <br /> 
					<c:out value="${error}" /> 
				</font> 
			</c:if>
        <input class="input-block-level" placeholder="username" name="username" type="text" value="">
        <input class="input-block-level" placeholder="password" name="password" type="password" value="">
        <!-- <label class="checkbox">
          <input value="remember-me" type="checkbox"> Remember me
        </label> -->
        
        <span style='float: left;'><button class="btn" type="submit">Sign in</button></span>
        <span style='float: right;'><button class="btn" type="reset">Reset</button></span>
      </form>
    </div> <!-- /container -->

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
</body>
</html>