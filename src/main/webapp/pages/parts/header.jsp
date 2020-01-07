<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Inventory</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ionicons.min.css">
    <!-- bootstrap datepicker -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap-datepicker.min.css">
    <!-- iCheck for checkboxes and radio inputs -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/blue.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
    <%--select 2--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/select2.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/fancy.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/AdminLTE.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/skin-blue.min.css">
    <%--custom--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/inventory.css">

    <style>
        .darkGray{
            color: #89898c !important;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>

    <![endif]-->

    <!-- Google Font -->
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
   <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition skin-blue sidebar-mini fixed">

<%--for loading spinner start--%>
<div id="myNavSpinner" class="overlay">
    <span id="foo" class="foo"></span>
</div>

<%--for loading spinner end--%>

<div class="wrapper" style="overflow: hidden">

    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>I</b></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>Inventory</b></span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <sec:authorize access="hasRole('ROLE_SUPERADMINISTRATOR')">
                        <!-- Notifications Menu -->
                        <li class="dropdown notifications-menu">
                            <!-- Menu toggle button -->
                            <a href="#" class="dropdown-toggle notifications-a" data-toggle="dropdown">
                                <i class="fa fa-bell-o"></i>
                                <span class="label label-warning countNotificationHeader"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li class="header countNotification"></li>
                                <li>
                                    <!-- Inner Menu: contains the notifications -->
                                    <ul class="menu notification">

                                        <!-- end notification -->
                                    </ul>
                                </li>
                                <li class="footer"><a href="#">View all</a></li>
                            </ul>
                        </li>
                    </sec:authorize>
                    <!-- User Account Menu -->
                    <li class="dropdown user user-menu">
                        <!-- Menu Toggle Button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <!-- The user image in the navbar-->
                            <img src="${pageContext.request.contextPath}/resources/img/inventory-logo.png"
                                 class="user-image" alt="User Image">
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                            <span class="hidden-xs">${pageContext.request.userPrincipal.name}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                            <li class="user-header">
                                <img src="${pageContext.request.contextPath}/resources/img/inventory-logo.png"
                                     class="img-circle" alt="User Image">

                                <p>
                                    <sec:authentication property="principal.username"/> -

                                    <sec:authorize access="hasRole('ROLE_SYSTEM')">
                                        System
                                    </sec:authorize>

                                    <sec:authorize access="hasRole('ROLE_SUPERADMINISTRATOR')">
                                        Super Administrator
                                    </sec:authorize>

                                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                        Administrator
                                    </sec:authorize>

                                    <sec:authorize access="hasAnyRole('ROLE_USER , ROLE_DASHBOARD')">
                                        User
                                    </sec:authorize>

                                    <%--<small>Member since Nov. 2012</small>--%>
                                </p>
                            </li>
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="${pageContext.request.contextPath}/user/changepassword" class="btn btn-default btn-flat">Change Password</a>
                                </div>
                                <div class="pull-right">
                                    <a href="${pageContext.request.contextPath}/logout"
                                       class="btn btn-default btn-flat">Sign out</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <!-- Control Sidebar Toggle Button -->
                    <li>
                        <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
