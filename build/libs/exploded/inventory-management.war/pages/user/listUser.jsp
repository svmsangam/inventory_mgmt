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
            <li class="active">Users</li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert bg-success alert-dismissable" role="alert">
            <svg class="glyph stroked checkmark">
                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#stroked-checkmark"></use>
            </svg>
                ${message}
            <a href="#" class="close" data-dismiss="alert" aria-label="close">
                <span class="glyphicon glyphicon-remove"></span>
            </a>
        </div>
    </c:if>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">User List <a
                        href="${pageContext.request.contextPath}/app/addbusinessowneruser">
                    <button class="btn btn-sm btn-primary pull-right">Add New User</button>
                </a>
                </div>
                <div class="panel-body">
                    <table id="example" class="cell-border" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>Username</th>
                            <th>User Type</th>
                            <th>Time Zone</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.username}</td>
                                <td>${user.userType}</td>
                                <td>${user.timeZone}</td>
                                <td>${user.status}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/app/editbusinessowneruser?userId=${user.id}"
                                       class="btn btn-info btn-xs">
                                        <i class="fa fa-pencil"></i> Edit </a>
                                    <a href="${pageContext.request.contextPath}/addBusinessServicePlanForBusinesOwner?userId=${user.id}"
                                       class="btn btn-warning btn-xs">
                                        <i class="fa fa-pencil"></i> Permissions </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="../common/footer.jsp" %>
<script>
    $(document).ready(function () {
        $('#example').DataTable({
            "pagingType": "full_numbers"
        });
    });
</script>