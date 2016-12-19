<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page session="false" %>
<!-- images -->
<link rel="shortcut icon" href="<c:url value="/static/images/favicon.png" />" />
<link rel="apple-touch-icon-precomposed" sizes="144x144" href="<c:url value="/static/images/apple-touch-icon-144-precomposed.png"/>" />
<link rel="apple-touch-icon-precomposed" sizes="114x114" href="<c:url value="/static/images/apple-touch-icon-114-precomposed.png"/>" />
<link rel="apple-touch-icon-precomposed" sizes="72x72" href="<c:url value="/static/images/apple-touch-icon-72-precomposed.png" />" />
<link rel="apple-touch-icon-precomposed" sizes="57x57" href="<c:url value="/static/images/apple-touch-icon-57-precomposed.png" />" />
<!-- css -->
<link href="<c:url value="/static/css/bootstrap.min.css" />" rel="stylesheet" id="bootstrap-css">
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/bootstrap-2.3.2.css" />"  />
 <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/bootstrap-responsive.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/jquery-ui-1.8.16.custom.css" />"  />
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/application.css" />"  />


    <style type="text/css">
    .dropdown-submenu {
    position: relative;
}

.dropdown-submenu>.dropdown-menu {
    top: 0;
    left: 100%;
    margin-top: -6px;
    margin-left: -1px;
    -webkit-border-radius: 0 6px 6px 6px;
    -moz-border-radius: 0 6px 6px;
    border-radius: 0 6px 6px 6px;
}

.dropdown-submenu:hover>.dropdown-menu {
    display: block;
}

.dropdown-submenu>a:after {
    display: block;
    content: " ";
    float: right;
    width: 0;
    height: 0;
    border-color: transparent;
    border-style: solid;
    border-width: 5px 0 5px 5px;
    border-left-color: #ccc;
    margin-top: 5px;
    margin-right: -10px;
}

.dropdown-submenu:hover>a:after {
    border-left-color: #fff;
}

.dropdown-submenu.pull-left {
    float: none;
}

.dropdown-submenu.pull-left>.dropdown-menu {
    left: -100%;
    margin-left: 10px;
    -webkit-border-radius: 6px 0 6px 6px;
    -moz-border-radius: 6px 0 6px 6px;
    border-radius: 6px 0 6px 6px;
}    </style>
 
<style type="text/css">
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
	body {
        padding-bottom: 40px;
        background-color: #fff;
      }
      table.tblHeader{
    	width: 98%;
    	border: 1px;
      	vertical-align: text-top;
      }
       table.tblHeader-inner{
    	width: 98%;
      	vertical-align: text-top;
      }
      table.tblFormData{
      	/* margin-left: 450px; */
    	width: 500px;
    	height: 80px;
    	border: 5px;
    	margin: 0 auto;
      }
	
    p.footer{
    	font-size: 13px;
    }
    div.row{
    	margin: 100px 20px;
    	height: 0px;
    }
    div.span3{
    	margin: 200px;
    	height: 0px;
    }
    form.well.span8{
    	align: top;
    	horizontal-align: centre;
    	height: 0px;
    	width: 1000px;
    }
    form.form-horizontal{
    	width: 1000px;
    	align: centre;
    	horizontal-align: centre;
    }
    div .footer{
    	height: 50px;
    }
	
</style>