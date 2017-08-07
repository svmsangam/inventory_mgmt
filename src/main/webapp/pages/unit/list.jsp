<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 6/29/17
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="../common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">unitInfo</li>
            <li>

            </li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <div class="container-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">Unit List <a href="${pageContext.request.contextPath}/unitInfo/add"
                                                            style="float:right; margin-bottom:8px;">
                        <button class="btn btn-primary btn-sm">Add New Unit</button>
                    </a>
                    </div>
                    <div class="panel-body table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>s.no</th>
                                <th>Unit Name</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="unitInfo" items="${unitList}" varStatus="counter">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>${unitInfo.name}</td>

                                    <td>
                                        <a href="${pageContext.request.contextPath}/unitInfo/edit?unitInfo=${unitInfo.unitId}"
                                           class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
                                            Edit </a> <a
                                            href="${pageContext.request.contextPath}/unitInfo/delete?unitInfo=${unitInfo.unitId}"
                                            class="btn btn-danger btn-xs"
                                            onclick="return confirm('Are you sure, you want to DELETE?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>


                    <c:if test="${fn:length(pagelist) gt 1}">

                        <style>
                            .mypgactive {
                                background: #a51234 !important;
                                cursor: pointer !important;
                                color: white !important;
                            }
                        </style>

                        <div class="pagination-block col-md-10  pull-left">
                            <ul class="pagination pagination-sm no-margin pagingclass col-md-10">
                                <li>
                                    <c:if test="${currentpage > 1}">
                                        <a href="/app/oldItem?pageNo=${currentpage-1}"
                                           class="pn prev mypgactive">Prev</a>
                                    </c:if></li>

                                <c:forEach var="pagelist" items="${pagelist}">
                                    <li><c:choose>

                                        <c:when test="${pagelist == currentpage}">

                                            <span class="active">${pagelist}</span>

                                        </c:when>
                                        <c:otherwise>

                                            <a href="/app/oldItem?pageNo=${pagelist}" class="mypgactive">${pagelist}</a>

                                        </c:otherwise>

                                    </c:choose></li>
                                </c:forEach>
                                <li>
                                    <c:if test="${currentpage + 1 <= lastpage}">
                                        <a href="/app/oldItem?pageNo=${currentpage+1}"
                                           class="pn next mypgactive">Next</a>
                                    </c:if></li>
                            </ul>
                        </div>

                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>

