<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/25/17
  Time: 8:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Data Not Found 400</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/AdminLTE.css">
</head>
<body style="background-color: #ecf0f5">
<div class="error-page">
    <div class="login-box-body">

        <h2 class="headline text-yellow"> 400</h2>

        <div class="error-content">
            <h3><i class="fa fa-warning text-yellow"></i> Oops! Data not found.</h3>

            <p>
                We could not find the data you were looking for.<br>
                Meanwhile, you may return to<br>
            </p>
            <a href="${pageContext.request.contextPath}/" class="btn btn-info btn-sm"><i class="fa fa-home"></i> HOMEPAGE</a>.
        </div>
        <!-- /.error-content -->
    </div>
    <!-- /.error-page -->
</div>
<!-- jQuery 3 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/resources/js/adminlte.min.js"></script>

</body>
</html>
