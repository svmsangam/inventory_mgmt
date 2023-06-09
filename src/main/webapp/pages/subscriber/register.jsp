<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/2/18
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Inventory | Registration</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- jQuery 3 -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <!-- Bootstrap 3.3.7 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/AdminLTE.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/iCheck/square/blue.css">

    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">

    <%--select 2--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/select2.min.css">

    <style>
        .form-error{
            color: red;
        }
    </style>
    <script src='https://www.google.com/recaptcha/api.js'></script>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>

    <![endif]-->

    <!-- Google Font -->
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition login-page">
<div class="login-box" style="margin-top: 0; margin-bottom: 5px;">
    <div class="login-logo">
        <a href="${pageContext.request.contextPath}/"><b>Inventory</b>Management</a>
    </div>
</div>
<!-- /.login-box -->


<div class="container">
    <section class="content">

            <c:if test="${not empty message}">
                <div class="alert alert-success alert-dismissable">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                    <strong>${message}</strong>
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissable">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                    <strong>${error}</strong>
                </div>
            </c:if>

        <div class="row">
            <div class="col-md-12">

                <div class="box box-info">
                    <div class="box-header with-border">
                        <h3 class="box-title">Free Registration for 1 month</h3>

                    </div>
                    <form id="registerForm" action="${pageContext.request.contextPath}/subscriber/register" method="post" modelAttribute="subscriber">
                        <div class="box-body">

                            <div class="row">

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Email *</label>
                                    <input type="email"  value="${subscriber.email}" class="form-control" name="email" placeholder="email"
                                           required="required"/>
                                    <p class="form-error">${subscriberError.email}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Password *</label>
                                    <input type="password" class="form-control" name="password" id="userpassword"
                                           placeholder="password" required/>
                                    <p class="form-error">${subscriberError.password}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Comfirm Password *</label>
                                    <input type="password" class="form-control" name="repassword"
                                           placeholder="password" required/>
                                    <p class="form-error">${subscriberError.repassword}</p>
                                </div>

                            </div>

                            <div class="row">

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Full Name *</label>
                                    <input type="text" class="form-control" value="${subscriber.fullName}" name="fullName" placeholder="full name"
                                           required="required"/>
                                    <p class="form-error">${subscriberError.fullName}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Contact </label>
                                    <input type="text" class="form-control" value="${subscriber.contact}" name="contact" placeholder="contact"/>
                                    <p class="form-error">${subscriberError.contact}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Mobile *</label>
                                    <input type="text" class="form-control" value="${subscriber.mobile}" name="mobile" placeholder="mobile"
                                           required/>
                                    <p class="form-error">${subscriberError.mobile}</p>
                                </div>

                            </div>

                            <div class="row">


                                <div class="col-md-4 form-group">
                                    <label class="control-label">City *</label>
                                    <select class="form-control selectMe" name="cityInfoId" style="width: 100%;" required>
                                        <option value="0">select city</option>
                                        <c:forEach items="${cityList}" var="city">
                                            <option value="${city.cityId}">${city.cityName}</option>
                                        </c:forEach>
                                    </select>
                                    <p class="form-error">${subscriberError.cityName}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Street</label>
                                    <input type="text" value="${subscriber.street}" class="form-control" name="street" placeholder="street"/>
                                    <p class="form-error">${subscriberError.street}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <div class="g-recaptcha" data-callback="capcha_filled" data-expired-callback="capcha_expired" data-sitekey="6LeG-kMUAAAAACApsSOmYiulgmDIMHn6aMqaKeUM"></div>
                                </div>

                            </div>

                            <div class="row">
                                <div class="col-md-6">
                                    <div class="checkbox icheck">
                                        <label class="">
                                            <div class="icheckbox_square-blue" style="position: relative;" aria-checked="false" aria-disabled="false"><input style="position: absolute; top: -20%; left: -20%; display: block; width: 140%; height: 140%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;" type="checkbox" required><ins class="iCheck-helper" style="position: absolute; top: -20%; left: -20%; display: block; width: 140%; height: 140%; margin: 0px; padding: 0px; background: rgb(255, 255, 255) none repeat scroll 0% 0%; border: 0px none; opacity: 0;"></ins></div> I agree to the <a href="#">terms</a>
                                        </label>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="box-footer">
                            <button type="submit" class="btn btn-primary"><i class="fa fa-registered"></i> &nbsp;Register</button>
                            <a href="${pageContext.request.contextPath}/login" class="btn btn-default pull-right"><i class="fa fa-sign-in"></i> &nbsp;already have username signin</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </section>
</div>

<!-- Bootstrap 3.3.7 -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<!-- iCheck -->
<script src="${pageContext.request.contextPath}/resources/plugins/iCheck/icheck.min.js"></script>

<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<!--select2 dropdown-->
<script src="${pageContext.request.contextPath}/resources/js/select2.full.min.js"></script>

<script>
    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });
</script>

<script>
    var allowSubmit = false;

    var msg = "proof you are not a robot";

    function capcha_filled () {
        allowSubmit = true;
    }

    function capcha_expired () {
        allowSubmit = false;
        msg = "captcha is expired , click again on I'm not a robot"
    }

    function check_if_capcha_is_filled (e) {
        if(allowSubmit) return true;
        e.preventDefault();
        swal(msg);
    }
    
    $(document).ready(function () {

        $(".selectMe").select2();

        $("#registerForm").submit(function(e){
            check_if_capcha_is_filled (e);
        });
    })
</script>
</body>
</html>

