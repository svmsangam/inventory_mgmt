<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/25/18
  Time: 6:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<c:set var="system" value="${false}"></c:set>
<sec:authorize access="hasRole('ROLE_SYSTEM')">
    <c:set var="system" value="${true}"></c:set>
</sec:authorize>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

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

            <div class="col-xs-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Susbcriber List</h3>
                        <div class="box-tools">
                            <a href="${pageContext.request.contextPath}/subscriber/add" class="btn btn-info btn-sm btn-flat pull-right addUser"><span
                                    class="glyphicon glyphicon-plus-sign"></span> Add
                            </a>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="table-responsive">
                            <table id="table2" class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>SN</th>
                                    <th>Username</th>
                                    <th>Store</th>
                                    <th>UserType</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                    <th>Manage</th>
                                </tr>
                                </thead>
                                <tbody id="myData">
                                <c:forEach var="user" items="${userList}" varStatus="i">
                                    <tr>
                                        <td>${i.index + 1}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${system}">
                                                    <%--<a href="${pageContext.request.contextPath}/user/store?userId=${user.userId}">${user.inventoryuser}</a>--%>
                                                    <a href="${pageContext.request.contextPath}/subscriber/show?subscriberId=${user.userId}">${user.inventoryuser}</a>

                                                </c:when>
                                                <c:otherwise>  ${user.inventoryuser}</c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${user.storeName}</td>
                                        <td>${user.userType}</td>
                                        <td><c:if test="${user.enable eq true}"><span
                                                class="label label-success">Activated</span></c:if><c:if
                                                test="${user.enable eq false}"><span
                                                class="label label-danger">Deactivated</span></c:if></td>
                                        <td>
                                            <c:if test="${user.enable eq true}"><a
                                                    href="${pageContext.request.contextPath}/user/updateenable?userId=${user.userId}"
                                                    onclick="return confirm('Are you sure you want to Deactivate?')"><span
                                                    class="label label-danger">Deactivate ?</span></a></c:if>

                                            <c:if test="${user.enable eq false}"><a
                                                    href="${pageContext.request.contextPath}/user/updateenable?userId=${user.userId}"
                                                    onclick="return confirm('Are you sure you want to Activate?')"><span
                                                    class="label label-success">Activate ?</span></a></c:if>
                                        </td>
                                        <td>
                                            <c:if test="${user.userType eq 'USER' and user.enable eq true}">
                                                <a class="btn btn-xs bg-purple" href="${pageContext.request.contextPath}/user/manage?userId=${user.userId}">
                                                    <i class="fa fa-cogs"></i> Manage
                                                </a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>
    </section>

</div>

<%@include file="/pages/parts/footer.jsp" %>
