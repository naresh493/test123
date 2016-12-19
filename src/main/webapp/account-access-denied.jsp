<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.infotree.qliktest.admin.common.AppInfo"%>
<%@ page session="true" %>
<%
	out.println("in index jsp");
	String error = null;
	if(request.getParameter("error") != null){
		error = request.getParameter("error");
		out.println("error == " + error);
	}
	out.println("in account access denied jsp");
	//TODO: remove this  when pre-authentication solution is in place
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
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }
      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
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
  
  <c:out value="${sessionScope.SPRING_SECURITY_403_EXCEPTION}" />
  
  <c:out value="${showLoginForm}" />
  
  <%-- <c:out value="${sessionScope.SPRING_SECURITY_LAST_EXCEPTION}" />
  
  <c:out value="${sessionScope}" />
  
  <c:if test="${fn:contains(sessionScope.SPRING_SECURITY_403_EXCEPTION, 'AccessDeniedException')}">
		<p>You do not have permission to perform this operation.</p>
		<p>If you feel that you should have access to this function, please contact you local Visit Reporting administrator or <a href="mailto:Support.vwg@vwg.co.uk?Subject=Visit Reporting Access Denied&Body=Hi,%0D%0A%0D%0AI believe that I should have access to do the following in Visit Reporting..%0D%0A%0D%0AWho should I contact to get this access?">Support.vwg@vwg.co.uk</a> if you do not know who your local administrator is.</p>
		<p>Click <a href="javascript:history.go(-1)">here</a> to navigate back to the page you were on previously.</p>
	</c:if>

	<c:if test="${! showLoginForm and empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION}">
		<p>Click <a href="<c:url value="/content/login" />">here</a> for main page.</p>
		<p>If this page is unavailable please contact Support.</p>
	</c:if>

	<c:if test="${fn:contains(sessionScope.SPRING_SECURITY_LAST_EXCEPTION, 'AuthenticationCredentialsNotFoundException')}">
		<p>The system is currently un-available due to a technical failure.</p>
		<p>Please try again later or contact Support if the problem persists.</p>
	</c:if>
	<c:if test="${fn:contains(sessionScope.SPRING_SECURITY_LAST_EXCEPTION, 'AuthenticationServiceException')}">
		<p>The system is currently un-available due to a technical failure.</p>
		<p>Please try again later or contact Support if the problem persists.</p>
	</c:if>
	
	<c:if test="${fn:contains(sessionScope.SPRING_SECURITY_LAST_EXCEPTION, 'UsernameNotFoundException')}">
		<p>Your details could not be found in the system.</p>
		<p>Please contact the system administrator to request access.</p>
	</c:if>
	
	<c:if test="${fn:contains(sessionScope.SPRING_SECURITY_403_EXCEPTION, 'UsernameNotFoundException')}">
		<p>You do not have permission to perform this operation.</p>
		<p>If you feel that you should have access to this function, please contact you local Visit Reporting administrator or <a href="mailto:Support.vwg@vwg.co.uk?Subject=Visit Reporting Access Denied&Body=Hi,%0D%0A%0D%0AI believe that I should have access to do the following in Visit Reporting..%0D%0A%0D%0AWho should I contact to get this access?">Support.vwg@vwg.co.uk</a> if you do not know who your local administrator is.</p>
		<p>Click <a href="javascript:history.go(-1)">here</a> to navigate back to the page you were on previously.</p>
	</c:if>

	<c:if test="${fn:contains(sessionScope.SPRING_SECURITY_LAST_EXCEPTION,'DisabledException')}">
		<p>Your account in this system has been disabled.</p>
		<p>Please contact the system administrator to request access.</p>
	</c:if>

	<!-- else -->
	<c:if test="${not empty sessionScope.SPRING_SECURITY_LAST_EXCEPTION && ! fn:contains(sessionScope.SPRING_SECURITY_LAST_EXCEPTION,'AuthenticationCredentialsNotFoundException') && ! fn:contains(sessionScope.SPRING_SECURITY_LAST_EXCEPTION,'AuthenticationServiceException') && ! fn:contains(sessionScope.SPRING_SECURITY_LAST_EXCEPTION,'UsernameNotFoundException') && ! fn:contains(sessionScope.SPRING_SECURITY_LAST_EXCEPTION,'DisabledException')}">
		<p>The system is currently un-available due to a technical failure.</p>
		<p>Please try again later or contact Support if the problem persists.</p>
	</c:if> --%>
	
  <c:if test="${showLoginForm}">
  	<%
  		out.println("removing user session");
		session.removeAttribute("userId");
		session.invalidate();
	%>
    <div class="container">
		<c:if test='${not empty param.error}'> 
			Please login to proceed.<br /><br />
        	<font color='red'> 
				Login error. <br /> 
				Reason : Invalid Username/password. 
			</font>
		</c:if>
      <form id="loginForm" class="form-signin" action="#" method="post">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <h3 class="form-signin-heading">Welcome to the QlikTest Admin Console</h3>
        	Please login to proceed.<br /><br />
        	<font color='red'> 
				Login error. <br /> 
				Reason : Invalid Username/password. 
			</font> 
        <input class="input-block-level" placeholder="userId" id="userId" type="text" name="username" value="Vijay">
        <input class="input-block-level" placeholder="password" id="password" type="password" name="password" value="12345">
        <!-- <label class="checkbox">
          <input value="remember-me" type="checkbox"> Remember me
        </label> -->
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn" type="submit" onclick="alert(document.getElementById('userId').value);alert(document.getElementById('loginForm').action);document.getElementById('loginForm').action='/QlikTestAdmin/content/login';alert(document.getElementById('loginForm').action);document.getElementById('userId').value=this.value; document.getElementById('loginForm').submit();">
        	Sign in</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button class="btn" type="reset">Reset</button>
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