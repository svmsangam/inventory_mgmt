<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>
<%@include file="../common/sidebar.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Business</li>
        </ol>
    </div><!--/.row--><br/>
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <div class="row">
        <div class="col-sm-12">
            <a href="${pageContext.request.contextPath}/addbusiness">
                <button class="btn btn-primary btn-md pull-right">Add Business</button>
            </a>
        </div>
        <br/><br/>
    </div><!--/.row-->
    <div class="row">
        <c:forEach items="${storeList}" var="storeInfo" varStatus="loop">

            <div class="col-lg-4">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">&nbsp</h3>
                        <span class="pull-right">
                <!-- Tabs -->
                <ul class="nav panel-tabs">
                    <li class="active"><a href="#business${loop.index+1}" data-toggle="tab">Business</a></li>
                    <li><a href="#users${loop.index+1}" data-toggle="tab">Users</a></li>
                    <li><a href="#location${loop.index+1}" data-toggle="tab">Location</a></li>
                </ul>
            </span>
                    </div>
                    <div class="panel-body">
                        <div class="tab-content">
                            <div class="tab-pane active" id="business${loop.index+1}"><a
                                    href="${pageContext.request.contextPath}/business/${storeInfo.storeId}">${storeInfo.storeName}</a>
                            </div>
                            <div class="tab-pane" id="users${loop.index+1}">
                                <c:forEach items="${storeInfo.userDTOs}" var="user">
                                    <a href="${pageContext.request.contextPath}/addBusinessServicePlanForBusinesOwner?userId=${user.id}">${user.username}</a>
                                </c:forEach>
                            </div>
                            <div class="tab-pane" id="location${loop.index+1}">${storeInfo.location}</div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@include file="../common/footer.jsp" %>
