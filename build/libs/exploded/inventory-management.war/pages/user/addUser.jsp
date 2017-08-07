<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/common/header.jsp" %>
<%@include file="/pages/common/businessownernavbar.jsp" %>
<%@include file="/pages/common/businessownersidebar.jsp" %>
<script type="text/javascript" src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/landing/js/select2.full.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/select2.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">User</li>
        </ol>
    </div><!--/.row-->

    <div class="row">
        <div class="col-lg-6">
            <div class="panel panel-default">
                <div class="panel-heading">Add User</div>
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/app/addbusinessowneruser" method="POST"
                          modelAttribute="user"
                          enctype="multipart/form-data" id="registerform" novalidate="novalidate">

                        <p class="error">${error.username}</p>
                        <div class="input-group" id="inputDiv">
                            <span class="input-group-addon">User Name</span>
                            <input type="text" value="${user.username }" id="username" class="form-control"
                                   name="username" required/>
                        </div>

                        <p class="error">${error.password}</p>
                        <div class="input-group" id="inputDiv">
                            <span class="input-group-addon">Password</span>
                            <input type="password" id="password" class="form-control" name="password" required/>
                        </div>

                        <p class="error">${error.repassword}</p>
                        <div class="input-group" id="inputDiv">
                            <span class="input-group-addon">Re-enter Password</span>
                            <input type="password" id="repassword" class="form-control" name="repassword" required/>
                            <br/>
                        </div>

                        <p class="error">${error.timeZone}</p>
                        <div class="input-group" id="inputDiv">
                            <span class="input-group-addon">Time Zone</span>
                            <script type="text/javascript">
                                $(document).ready(function () {
                                    $(".select2").select2();

                                });
                            </script>
                            <select id="timeZone" class="form-control select2" style="width: 100%"
                                    id="js-example-basic-single"
                                    name="timeZone" required="required">
                                <c:forEach var="timeZone" items="${timeZoneList}">
                                    <option value="${timeZone}">${timeZone}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <p class="error">${error.userType}</p>
                        <div class="input-group" id="inputDiv">
                            <span class="input-group-addon">User Type</span>
                            <select id="userType" class="form-control select2" style="width: 100%"
                                    id="js-example-basic-single"
                                    name="userType" required>
                                <c:forEach var="userType" items="${userTypeList}">
                                    <c:if test="${userType ne 'BUSINESSOWNER'}">
                                        <c:if test="${userType ne 'ADMIN'}">
                                            <option value="${userType}">${userType}</option>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <br/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Add User" class="btn btn-primary pull-right">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/pages/common/footer.jsp" %>
<script type="text/javascript">
    $(document).ready(function () {
        $("#js-example-responsive").select2();
        $("#registerform").validate({
            // Specify the validation rules
            rules: {
                username: {
                    required: true,
                    maxlength: 50
                },
                password: {
                    required: true,
                    maxlength: 50
                },
                repassword: {
                    required: true,
                    maxlength: 50
                }
            },
            // Specify the validation error messages
            messages: {
                username: {
                    required: "Please provide username",
                    maxlength: "Your username must be less than 50"
                },
                password: {
                    required: "Please enter password",
                    maxlength: "Your password must be less than 50"
                },
                repassword: {
                    required: "Please Re-enter password",
                    maxlength: "Your lastName must be less than 50"
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    });
</script>
	