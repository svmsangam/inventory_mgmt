<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <li class="active">Review</li>
        </ol>
    </div><!--/.row-->
    <div class="col-lg-4">
        <c:if test="${not empty message}">
            <h3 class="bg-success"><c:out value="${message}"></c:out></h3>
        </c:if>
    </div>
    <input type="hidden" id="storeId" value="${storeInfo.storeId}"/>
    <div class="col-xs-12">
        <div class="panel-primary">
            <div class="panel-heading">Business Information</div>
            <div class="col-xs-3">
                <div class="panel panel-body">Name: ${storeInfo.storeName}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">PAN no: ${storeInfo.panNumber}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">Reg no: ${storeInfo.registrationNumber}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">Mobile no: ${storeInfo.mobileNumber}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">Phone: ${storeInfo.landLine}</div>
            </div>
        </div>
    </div>

    <div class="col-xs-12">
        <div class="panel-primary">
            <div class="panel-heading">Owner Details</div>
            <div class="col-xs-3">
                <div class="panel panel-body">First Name: ${storeInfo.proprieterDto.firstName}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">Middle Name: ${storeInfo.proprieterDto.middleName}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">Last Name: ${storeInfo.proprieterDto.lastName}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">Email: ${storeInfo.proprieterDto.email}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">Mobile no: ${storeInfo.proprieterDto.mobileNo}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">Temporary Address: ${storeInfo.proprieterDto.temporaryAddress}</div>
            </div>
            <div class="col-xs-3">
                <div class="panel panel-body">Permanent Address: ${storeInfo.proprieterDto.permanentAddress}</div>
            </div>
        </div>
    </div>

    <div class="col-lg-12">
        <div class="panel-primary">
            <div class="panel-heading">Service Plans</div>
            <div class="col-xs-12">
                <c:forEach var="plan" oldItems="${permissions}">
                    <div class="col-xs-3">
                        <div class="panel panel-body">${plan}</div>
                    </div>
                </c:forEach>
            </div>

        </div>
    </div>

    <a href="${pageContext.request.contextPath}/business">
        <button class="btn btn-primary pull-right">Back To List</button>
    </a>
    <a href="${pageContext.request.contextPath}/editbusiness?storeId=${storeInfo.storeId}" class="btn btn-success">Edit
        Business Details</a>
    <a href="${pageContext.request.contextPath}/deletebusiness?storeId=${storeInfo.storeId}" class="btn btn-danger"
       onclick="return confirm('Are you sure, you want to DELETE?')">Delete</a>
    <br/><br/>


</div>
<!--/.main-->
<%@include file="../common/footer.jsp" %>