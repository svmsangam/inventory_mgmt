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
            <li class="active">Order</li>
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
                    <div class="panel-body table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th><c:choose><c:when test="${sort == 'id'}"><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=id&direction=${direction}">s.n.</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=id&direction=desc">s.n.</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'orderNo'}"><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=orderNo&direction=${direction}">orderNo</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/order/list?pageNo=${currentpage}&sort=orderNo&direction=desc">orderNo</a></c:otherwise></c:choose>
                                </th>
                                <th>Buyer</th>
                                <th><c:choose><c:when test="${sort eq 'orderDate'}"><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=orderDate&direction=${direction}">orderDate</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=orderDate&direction=desc">orderDate</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'shippingDate'}"><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=shippingDate&direction=${direction}">shippingDate</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=shippingDate&direction=desc">shippingDate</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'deliveryDate'}"><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=deliveryDate&direction=${direction}">deliveryDate</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=deliveryDate&direction=desc">deliveryDate</a></c:otherwise></c:choose>
                                </th>
                                <%-- <th><c:choose><c:when test="${sort eq 'orderType'}"><a href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=orderType&direction=${direction}">orderType</a></c:when><c:otherwise><a href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=orderType&direction=desc">orderType</a></c:otherwise></c:choose></th>--%>

                                <th><c:choose><c:when test="${sort eq 'totalAmount'}"><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=totalAmount&direction=${direction}">totalAmount</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=totalAmount&direction=desc">totalAmount</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'tax'}"><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=tax&direction=${direction}">tax(%)</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=tax&direction=desc">tax</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'grandTotal'}"><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=grandTotal&direction=${direction}">grandTotal</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=grandTotal&direction=desc">grandTotal</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort eq 'shipmentChannel'}"><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=shipmentChannel&direction=${direction}">shipmentChannel</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage}&sort=shipmentChannel&direction=desc">shipmentChannel</a></c:otherwise></c:choose>
                                </th>
                                <th>track</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="order" items="${orderList}" varStatus="counter">
                                <tr>
                                    <td>${counter.index + 1}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/order/${order.orderId}">${order.orderNo}</a>
                                    </td>
                                    <td>${order.buyerName}</td>
                                    <td><fmt:formatDate pattern="MMM dd, yyyy" value="${order.orderDate}"/></td>
                                    <td><fmt:formatDate pattern="MMM dd, yyyy" value="${order.shippingDate}"/></td>
                                    <td><fmt:formatDate pattern="MMM dd, yyyy" value="${order.deliveryDate }"/></td>
                                        <%-- <td>${order.orderType}</td>--%>
                                    <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                          value="${order.totalAmount}"/></td>
                                    <td>${order.tax}</td>
                                    <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                          value="${order.grandTotal}"/></td>
                                    <td>${order.shipmentChannel}</td>
                                    <c:choose><c:when test="${order.orderType == 'Sale'}">
                                        <td><c:choose><c:when
                                                test="${order.saleTrack == 'NULL'}">Pending</c:when><c:otherwise>${order.saleTrack}</c:otherwise></c:choose></td>
                                    </c:when><c:otherwise>
                                        <td>${order.purchaseTrack}</td>
                                    </c:otherwise></c:choose>

                                    <td>
                                        <c:choose>
                                            <c:when test="${order.orderType eq 'Sale'}">
                                                <c:choose>
                                                    <c:when test="${order.saleTrack eq 'NULL'}">
                                                        <a href="${pageContext.request.contextPath}/order/edit?orderId=${order.orderId}"
                                                           class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
                                                            Edit </a>
                                                    </c:when>

                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${order.purchaseTrack eq 'ISSUED'}">
                                                        <a href="${pageContext.request.contextPath}/order/edit?orderId=${order.orderId}"
                                                           class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
                                                            Edit </a>
                                                    </c:when>

                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>


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
                                        <a href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage-1}&sort=${sort}&direction=${directionPage}"
                                           class="pn prev mypgactive">Prev</a>
                                    </c:if></li>

                                <c:forEach var="pagelist" items="${pagelist}">
                                    <li><c:choose>

                                        <c:when test="${pagelist == currentpage}">

                                            <span class="active">${pagelist}</span>

                                        </c:when>
                                        <c:otherwise>

                                            <a href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${pagelist}&sort=${sort}&direction=${directionPage}"
                                               class="mypgactive">${pagelist}</a>

                                        </c:otherwise>

                                    </c:choose></li>
                                </c:forEach>
                                <li>
                                    <c:if test="${currentpage + 1 <= lastpage}">
                                        <a href="${pageContext.request.contextPath}/order/list/saleOrder?pageNo=${currentpage+1}&sort=${sort}&direction=${directionPage}"
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
