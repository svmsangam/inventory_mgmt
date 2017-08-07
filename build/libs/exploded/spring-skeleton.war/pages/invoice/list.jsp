<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 7/25/17
  Time: 2:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Invoice</li>
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
                    <div class="panel-heading">Invoice List <a href="${pageContext.request.contextPath}/invoiceInfo/add"
                                                               style="float:right; margin-bottom:8px;">
                        <button class="btn btn-primary btn-sm">Add New Invoice</button>
                    </a>
                    </div>
                    <div class="panel-body table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th><c:choose><c:when test="${sort == 'id'}"><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=id&direction=${direction}">s.n.</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=id&direction=desc">s.n.</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'invoiceNo'}"><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=invoiceNo&direction=${direction}">invoiceNo</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=invoiceNo&direction=desc">invoiceNo</a></c:otherwise></c:choose>
                                </th>
                                <th>Customer Name</th>
                                <th><c:choose><c:when test="${sort eq 'invoiceDate'}"><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=invoiceDate&direction=${direction}">invoiceDate</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=invoiceDate&direction=desc">invoiceDate</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'totalAmount'}"><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=totalAmount&direction=${direction}">totalAmount</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=totalAmount&direction=desc">totalAmount</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'receivableAmount'}"><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=receivableAmount&direction=${direction}">receivableAmount</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage}&sort=receivableAmount&direction=desc">receivableAmount</a></c:otherwise></c:choose>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="invoiceInfo" items="${invoiceList}" varStatus="counter">
                                <tr>
                                    <td>${counter.index + 1}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/invoiceInfo/${invoiceInfo.invoiceId}">${invoiceInfo.invoiceNo}</a>
                                    </td>
                                    <td>${invoiceInfo.orderRequestDTO.buyerName}</td>
                                    <td><fmt:formatDate pattern="MMM dd, yyyy" value="${invoiceInfo.invoiceDate}"/></td>
                                    <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                          value="${invoiceInfo.totalAmount}"/></td>
                                    <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                          value="${invoiceInfo.receivableAmount}"/></td>
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
                                        <a href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage-1}&sort=${sort}&direction=${directionPage}"
                                           class="pn prev mypgactive">Prev</a>
                                    </c:if></li>

                                <c:forEach var="pagelist" items="${pagelist}">
                                    <li><c:choose>

                                        <c:when test="${pagelist == currentpage}">

                                            <span class="active">${pagelist}</span>

                                        </c:when>
                                        <c:otherwise>

                                            <a href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${pagelist}&sort=${sort}&direction=${directionPage}"
                                               class="mypgactive">${pagelist}</a>

                                        </c:otherwise>

                                    </c:choose></li>
                                </c:forEach>
                                <li>
                                    <c:if test="${currentpage + 1 <= lastpage}">
                                        <a href="${pageContext.request.contextPath}/invoiceInfo/list?pageNo=${currentpage+1}&sort=${sort}&direction=${directionPage}"
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
