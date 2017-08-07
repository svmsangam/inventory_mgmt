<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/common/header.jsp" %>
<%@include file="/pages/common/businessownernavbar.jsp" %>
<%@include file="/pages/common/businessownersidebar.jsp" %>

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
                <div class="panel-heading">Edit User - ${user.username}</div>
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/editbusinessowneruser" method="POST"
                          modelAttribute="user"
                          enctype="multipart/form-data">

                        <input type="hidden" name="id" value="${user.id}"/>

                        <p class="error">${error.timeZone}</p>
                        <div class="input-group">
                            <span class="input-group-addon">Time Zone</span>
                            <select id="js-example-responsive" class="form-control selectMe" name="timeZone">
                                <c:forEach var="timeZone" items="${timeZoneList}">
                                    <c:if test="${timeZone eq user.timeZone }">
                                        <option value="${timeZone}" selected>${timeZone}</option>
                                    </c:if>
                                    <c:if test="${timeZone ne user.timeZone }">
                                        <option value="${timeZone}">${timeZone}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>

                        <p class="error">${error.userType}</p>
                        <div class="input-group" id="inputDiv">
                            <span class="input-group-addon">User Type</span>
                            <select id="js-example-responsive1" class="form-control selectMe" name="userType">
                                <c:forEach var="userType" items="${userTypeList}">
                                    <c:if test="${userType eq user.userType }">
                                        <option value="${userType}" selected>${userType}</option>
                                    </c:if>
                                    <c:if test="${userType ne user.userType && userType != 'ADMIN' && userType != 'BUSINESSOWNER'}">
                                        <option value="${userType}">${userType}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>

                        <p class="error">${error.status}</p>
                        <div class="input-group" id="inputDiv">
                            <span class="input-group-addon">Status</span>
                            <select class="form-control selectMe" id="js-example-basic-single" name="userStatus">
                                <c:forEach var="status" items="${statusList}">
                                    <c:if test="${status eq user.status.toString()}">
                                        <option value="${status}" selected>${status}</option>
                                    </c:if>
                                    <c:if test="${status ne user.status.toString()}">
                                        <option value="${status}">${status}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </div>
                        <br/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Update" class="btn btn-sm btn-primary">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/pages/common/footer.jsp" %>
<script type="text/javascript">
    $(document).ready(function () {
        $(".selectMe").select2();
    });
</script>