<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/29/17
  Time: 10:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Main content -->
    <section class="invoice">
        <!-- title row -->
        <div class="row">
            <div class="col-xs-12">
                <h2 class="page-header">
                    <c:if test="${invoice ne null}"><p class="text-center"><span class="pull-left">Order No. <b>#${order.orderNo}</b></span> <span class="pull-right">Invoice No. <b><a href="${pageContext.request.contextPath}/invoice/${invoice.invoiceId}">#${invoice.invoiceNo}</a></b></span></p></c:if>
                    <c:if test="${invoice eq null}"><p class="text-center">Order No. <b>#${order.orderNo}</b></p></c:if>
                </h2>
            </div>
            <!-- /.col -->
        </div>
        <!-- info row -->
        <div class="row invoice-info">
            <div class="col-sm-4 invoice-col">
                <address>
                    <strong>Buyer Details</strong><br>
                    ${order.clientInfo.name}<br>

                    <c:if test="${order.clientInfo.companyName ne null and order.clientInfo.companyName ne ''}">
                        ${order.clientInfo.companyName}<br>
                    </c:if>

                    ${order.clientInfo.street}<br>
                    Mobile: ${order.clientInfo.mobileNumber}<br>

                    <c:if test="${order.clientInfo.companyName ne null and order.clientInfo.companyName ne ''}">
                        Contact: ${order.clientInfo.contact}<br>
                    </c:if>

                    <c:if test="${order.clientInfo.email ne null and order.clientInfo.email ne ''}">
                        Email: ${order.clientInfo.email}<br>
                    </c:if>

                    <c:if test="${order.clientInfo.cityInfoDTO ne null}">
                        City: ${order.clientInfo.cityInfoDTO.cityName}<br>
                    </c:if>

                </address>
            </div>
            <div class="col-sm-4 invoice-col">
                <%--<strong>Status</strong>--%>
                <div class="btn-group" data-toggle="buttons">

                    <c:if test="${order.saleTrack eq 'PENDDING'}">
                        <label class="btn btn-primary active">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" value="PENDDING" data-id="${order.orderId}" autocomplete="off" checked> Pending
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" value="ACCEPTED" data-id="${order.orderId}" autocomplete="off"> Accept
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" value="CANCEL" data-id="${order.orderId}" autocomplete="off"> Cancel
                        </label>
                    </c:if>

                    <c:if test="${order.saleTrack eq 'ACCEPTED'}">
                        <label class="btn btn-primary active">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" data-id="${order.orderId}" value="ACCEPTED" autocomplete="off" checked> Accepted
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" data-id="${order.orderId}" value="PACKED" autocomplete="off"> Packed
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" data-id="${order.orderId}" value="CANCEL" autocomplete="off"> Cancel
                        </label>
                    </c:if>

                    <c:if test="${order.saleTrack eq 'PACKED'}">
                        <label class="btn btn-primary active">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" data-id="${order.orderId}" value="PACKED" autocomplete="off" checked> Packed
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" data-id="${order.orderId}" value="SHIPPED" autocomplete="off"> shipped
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" data-id="${order.orderId}" value="CANCEL" autocomplete="off"> Cancel
                        </label>
                    </c:if>

                    <c:if test="${order.saleTrack eq 'SHIPPED'}">
                        <label class="btn btn-primary active">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" data-id="${order.orderId}" value="SHIPPED" autocomplete="off" checked> shipped
                        </label>
                        <label class="btn btn-primary">
                            <input type="radio" name="saleTrack" url="${pageContext.request.contextPath}/order/sale/track/update" data-id="${order.orderId}" value="DELIVERED" autocomplete="off"> Delivered
                        </label>
                    </c:if>

                    <c:if test="${order.saleTrack eq 'DELIVERED'}">
                        <label class="btn btn-success active">
                            <input type="radio" autocomplete="off" checked> Delivered
                        </label>
                    </c:if>

                    <c:if test="${order.saleTrack eq 'CANCEL'}">
                        <label class="btn btn-danger active">
                            <input type="radio" autocomplete="off" checked> Canceled
                        </label>
                    </c:if>

                </div>
            </div>
            <div class="col-sm-4 invoice-col">
                <address>
                    <strong>Shipping Address</strong><br>
                    ${order.deliveryAddress}<br>
                    Phone: ${order.clientInfo.mobileNumber}<br>
                </address>
            </div>
        </div>
        <div class="row invoice-info">
            <div class="col-sm-3 invoice-col">
                <b>Order Date: </b><fmt:formatDate pattern="MMM dd, yyyy" value="${order.orderDate}"/>
            </div>
            <div class="col-sm-6 invoice-col">&nbsp;</div>
            <div class="col-sm-3 invoice-col">
                <b>Delivery Date: </b><fmt:formatDate pattern="MMM dd, yyyy" value="${order.deliveryDate}"/>
            </div>
        </div>
        <!-- /.row -->
        <div class="col-sm-12">&nbsp;</div>
        <!-- Table row -->
        <div class="row">
            <div class="col-xs-12 table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>SN</th>
                        <th>Item</th>
                        <th>Item Lot</th>
                        <th>Quantity</th>
                        <th>Rate</th>
                        <th>Discount(%)</th>
                        <th>Subtotal</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${orderItemList}" var="orderItem" varStatus="i">
                        <tr>
                            <td>${i.index + 1}</td>
                            <td>${orderItem.itemInfoDTO.productInfo.name}-${orderItem.itemInfoDTO.tagInfo.name}</td>
                            <td>${orderItem.itemInfoDTO.lotInfo.lot}</td>
                            <td>${orderItem.quantity} &nbsp; ${orderItem.itemInfoDTO.productInfo.unitInfo.code}</td>
                            <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderItem.rate}"/></td>
                            <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderItem.discount}"/></td>
                            <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderItem.amount}"/></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <div class="row">
            <!-- accepted payments column -->
            <div class="col-xs-4">
                <p class="lead">Description</p>
                <p class="text-muted well well-sm no-shadow" style="margin-top: 10px;">
                    ${order.description}
                </p>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">&nbsp;</div>
            <div class="col-lg-4">
                <div class="table-responsive">
                    <table class="table">
                        <tr>
                            <th>Tax (<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="tru" value="${order.tax}"/>%)</th>
                            <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="tru" value="${order.grandTotal - order.grandTotal * order.tax / 100}"/></td>
                        </tr>
                        <tr>
                            <th>Total:</th>
                            <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="tru" value="${order.grandTotal}"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

    </section>
    <!-- /.content -->
    <div class="clearfix"></div>
</div>
<!-- /.content-wrapper -->
<%@include file="/pages/parts/footer.jsp" %>
