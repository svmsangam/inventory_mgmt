<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${user.userType == 'ADMIN'}">
        <%@include file="../common/settings.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="../common/businessOwnerSettings.jsp" %>
    </c:otherwise>
</c:choose>


<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">State</li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <div class="col-sm-8">
        <a href="${pageContext.request.contextPath}/addstate">
            <button class="btn btn-primary btn-sm pull-right">Add State</button>
        </a>
    </div>
    <br/><br/>

    <div class="col-sm-8">
        <table class="table table-bordered table-stripped">
            <thead>
            <tr>
                <th>State Name</th>
                <th>Country Name</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="stateInfo" items="${stateList}">
                <tr>
                    <td>${stateInfo.stateName}</td>
                    <td>${stateInfo.countryName}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/editstate?id=${stateInfo.stateId}">
                            <button class="btn btn-info btn-xs">Edit</button>
                        </a>
                        <a href="${pageContext.request.contextPath}/removestate?id=${stateInfo.stateId}">
                            <button class="btn btn-danger btn-xs"
                                    onclick="return confirm('Are you sure, you want to DELETE?')">Delete
                            </button>
                        </a>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>


</div>	
