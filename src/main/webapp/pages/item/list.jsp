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
            <li class="active">Item</li>
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
                    <div class="panel-heading">Item List <a href="${pageContext.request.contextPath}/productInfo/add"
                                                            style="float:right; margin-bottom:8px;">
                        <button class="btn btn-primary btn-sm">Add New Item</button>
                    </a>
                    </div>
                    <div class="panel-body table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th><c:choose><c:when test="${sort == 'id'}"><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=id&direction=${direction}">s.n.</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=id&direction=desc">s.n.</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'name'}"><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=name&direction=${direction}">Item
                                    Name</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=name&direction=desc">Item
                                    Name</a></c:otherwise></c:choose></th>
                                <th><c:choose><c:when test="${sort eq 'code'}"><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=code&direction=${direction}">Item
                                    Code</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=code&direction=desc">Item
                                    Code</a></c:otherwise></c:choose></th>
                                <th><c:choose><c:when test="${sort eq 'subCategoryInfo'}"><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=subCategoryInfo&direction=${direction}">SubCategory</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=subCategoryInfo&direction=desc">SubCategory</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'unitInfo'}"><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=unitInfo&direction=${direction}">Unit</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=unitInfo&direction=desc">Unit</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'tradition'}"><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=tradition&direction=${direction}">Tradition</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage}&sort=tradition&direction=desc">Tradition</a></c:otherwise></c:choose>
                                </th>
                                <th>Instock</th>
                                <th>Total Stock</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="productInfo" items="${itemList}" varStatus="counter">
                                <tr>
                                    <td>${counter.index + 1}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/productInfo/${productInfo.itemId}">${productInfo.name}</a>
                                    </td>
                                    <td>${productInfo.code}</td>
                                    <td>${productInfo.subcategoryName}</td>
                                    <td>${productInfo.unitName}</td>
                                    <td>${productInfo.trendingLevel}</td>
                                    <td>${productInfo.availabaleQuatity}</td>
                                    <td>${productInfo.totalQuantity}</td>

                                    <td>
                                        <a href="${pageContext.request.contextPath}/productInfo/edit?itemId=${productInfo.itemId}"
                                           class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
                                            Edit </a> <a
                                            href="${pageContext.request.contextPath}/productInfo/delete?itemId=${productInfo.itemId}"
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
                                        <a href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage-1}&sort=${sort}&direction=${directionPage}"
                                           class="pn prev mypgactive">Prev</a>
                                    </c:if></li>

                                <c:forEach var="pagelist" items="${pagelist}">
                                    <li><c:choose>

                                        <c:when test="${pagelist == currentpage}">

                                            <span class="active">${pagelist}</span>

                                        </c:when>
                                        <c:otherwise>

                                            <a href="${pageContext.request.contextPath}/productInfo/list?pageNo=${pagelist}&sort=${sort}&direction=${directionPage}"
                                               class="mypgactive">${pagelist}</a>

                                        </c:otherwise>

                                    </c:choose></li>
                                </c:forEach>
                                <li>
                                    <c:if test="${currentpage + 1 <= lastpage}">
                                        <a href="${pageContext.request.contextPath}/productInfo/list?pageNo=${currentpage+1}&sort=${sort}&direction=${directionPage}"
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
