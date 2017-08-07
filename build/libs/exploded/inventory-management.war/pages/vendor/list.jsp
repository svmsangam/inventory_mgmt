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
            <li class="active">Vendor</li>
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
                    <div class="panel-heading">Vendor List <a href="${pageContext.request.contextPath}/vendor/add"
                                                              style="float:right; margin-bottom:8px;">
                        <button class="btn btn-primary btn-sm">Add New Vendor</button>
                    </a>
                    </div>
                    <div class="panel-body table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th><c:choose><c:when test="${sort == 'id'}"><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=id&direction=${direction}">s.n.</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=id&direction=desc">s.n.</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'storeName'}"><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=storeName&direction=${direction}">storeName</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=storeName&direction=desc">storeName</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'location'}"><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=location&direction=${direction}">location</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=location&direction=desc">location</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'MobileNumber'}"><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=MobileNumber&direction=${direction}">mobileNumber</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=MobileNumber&direction=desc">mobileNumber</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'landLine'}"><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=landLine&direction=${direction}">landLine</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage}&sort=landLine&direction=desc">landLine</a></c:otherwise></c:choose>
                                </th>
                                <th>Account Number</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="storeInfo" items="${storeList}" varStatus="i">
                                <tr>
                                    <td>${i.index + 1}</td>
                                    <td>${storeInfo.storeName}</td>
                                    <td>${storeInfo.location}</td>
                                    <td>${storeInfo.mobileNumber}</td>
                                    <td>${storeInfo.landLine}</td>
                                    <td id="account${i.index+1}" class="account"><c:if
                                            test="${storeInfo.accountHeadDTO ne null}">${storeInfo.accountHeadDTO.accountNumber}</c:if><c:if
                                            test="${storeInfo.accountHeadDTO eq null}"><a href="javascript:void(0);"
                                                                                          class="btn btn-success generate"
                                                                                          associateId="${storeInfo.storeId}"
                                                                                          clientType="STORE"
                                                                                          url="${pageContext.request.contextPath}/account/add"
                                                                                          result="account${i.index+1}">generate</a></c:if>
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
                                        <a href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage-1}&sort=${sort}&direction=${directionPage}"
                                           class="pn prev mypgactive">Prev</a>
                                    </c:if></li>

                                <c:forEach var="pagelist" items="${pagelist}">
                                    <li><c:choose>

                                        <c:when test="${pagelist == currentpage}">

                                            <span class="active">${pagelist}</span>

                                        </c:when>
                                        <c:otherwise>

                                            <a href="${pageContext.request.contextPath}/vendor/list?pageNo=${pagelist}&sort=${sort}&direction=${directionPage}"
                                               class="mypgactive">${pagelist}</a>

                                        </c:otherwise>

                                    </c:choose></li>
                                </c:forEach>
                                <li>
                                    <c:if test="${currentpage + 1 <= lastpage}">
                                        <a href="${pageContext.request.contextPath}/vendor/list?pageNo=${currentpage+1}&sort=${sort}&direction=${directionPage}"
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

<script>
    $(document).ready(function () {
        $(".generate").click(function () {

            console.log("generating");
            var url = $(this).attr("url");
            var associateId = $(this).attr("associateId");
            var clientType = $(this).attr("clientType");
            var result = $(this).attr("result");

            console.log("td " + result);
            generateAccount(url, associateId, clientType, $(this), result);

        })
    })
</script>