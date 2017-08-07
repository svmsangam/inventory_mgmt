<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Sales</li>
        </ol>
    </div>

    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <!--/.row-->

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">List Sales Order<a
                        href="${pageContext.request.contextPath}/app/addsalesorder">
                    <button class="btn btn-primary btn-sm pull-right">Create New Order</button>
                </a></div>
                <div class="panel-body">
                    <table id="example" class="cell-border" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>Order No</th>
                            <th>Customer Name</th>
                            <th>Total Cost</th>
                            <th>Order Date</th>
                            <th>Shipment Date</th>
                            <th>Delivery Date</th>
                            <th>Track</th>
                            <th>Action</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach oldItems="${salesList}" var="sales">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/app/showSalesOrder?saleOrderId=${sales.orderId}">${sales.orderNo}</a>
                                </td>
                                <td>${sales.customerName}</td>
                                <td>${sales.totalCost}</td>
                                <td>${sales.orderDate}</td>
                                <td>${sales.shipmentDate}</td>
                                <td>${sales.deliveryDate}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${sales.track eq 'SHIPPED'}">
                                            <span class="label label-primary">Shipped</span>
                                        </c:when>
                                        <c:when test="${sales.track eq 'DELIVERED'}">
                                            <span class="label label-success">Delivered</span>
                                        </c:when>
                                        <c:when test="${sales.track eq 'PACKED'}">
                                            <span class="label label-yellow">Packed</span>
                                        </c:when>
                                        <c:when test="${sales.track eq 'PENDING'}">
                                            <span class="label label-default">Pending</span>
                                        </c:when>
                                        <c:when test="${sales.track eq 'CANCEL'}">
                                            <span class="label label-danger">Cancelled</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td><c:if test="${sales.track eq 'PENDING'}"><a
                                        href="${pageContext.request.contextPath}/app/editsalesorder?id=${sales.orderId}">
                                    <button class="btn btn-success btn-xs">Edit</button>
                                </a></c:if> <a
                                        href="${pageContext.request.contextPath}/app/removesalesorder?saleId=${sales.orderId}">
                                    <button class="btn btn-danger btn-xs"
                                            onclick="return confirm('Are you sure, you want to DELETE?')">Delete
                                    </button>
                                </a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div><!--/.row-->
</div>

<%@include file="../common/footer.jsp" %>
<script>
    $(document).ready(function () {
        $('#example').DataTable({
            "pagingType": "full_numbers"
        });
    });
</script>