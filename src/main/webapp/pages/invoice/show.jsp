<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <section class="invoice">
        <!-- title row -->
        <div class="row">
            <div class="col-xs-12">
                <h2 class="page-header">
                    <%--<i class="fa fa-globe"></i>--%> Invoice #${invoice.invoiceNo}
                    <small class="pull-right">Date: <fmt:formatDate pattern="MMM dd, yyyy" value="${invoice.invoiceDate}"/></small>
                </h2>
            </div>
            <!-- /.col -->
        </div>
        <!-- info row -->
        <div class="row invoice-info">
            <div class="col-sm-4 invoice-col">
                From
                <address>
                    <strong>${invoice.storeInfoDTO.name}</strong><br>
                    ${invoice.storeInfoDTO.cityName},${invoice.storeInfoDTO.stateName},${invoice.storeInfoDTO.countryName}<br>
                    ${invoice.storeInfoDTO.street}<br>
                    Phone: ${invoice.storeInfoDTO.contact},${invoice.storeInfoDTO.mobileNumber}<br>
                    Pan No.:${invoice.storeInfoDTO.panNumber}<br>
                    Email: ${invoice.storeInfoDTO.email}
                </address>
            </div>
            <!-- /.col -->
            <div class="col-sm-4 invoice-col">
                To
                <address>
                    <c:if test="${invoice.orderInfo.clientInfo.companyName eq null}">
                        <strong>${invoice.orderInfo.clientInfo.name}</strong><br>
                    </c:if>

                    <c:if test="${invoice.orderInfo.clientInfo.companyName ne null}">
                        <strong>${invoice.orderInfo.clientInfo.companyName}</strong><br>
                    </c:if>

                    ${invoice.orderInfo.clientInfo.cityInfoDTO.cityName},${invoice.orderInfo.clientInfo.cityInfoDTO.stateName}, ${invoice.orderInfo.clientInfo.cityInfoDTO.countryName}<br>
                    ${invoice.orderInfo.clientInfo.street}<br>
                    Phone: ${invoice.orderInfo.clientInfo.contact}<c:if test="${invoice.orderInfo.clientInfo.mobileNumber ne null}">,${invoice.orderInfo.clientInfo.mobileNumber}</c:if><br>
                    Email: ${invoice.orderInfo.clientInfo.email}
                </address>
            </div>
            <!-- /.col -->
            <div class="col-sm-4 invoice-col">
                <%--<b>Invoice #${invoice.invoiceNo}</b><br>
                <br>--%>
                <b>Order ID:</b> <a href="${pageContext.request.contextPath}/order/sale/${invoice.orderInfo.orderId}">#${invoice.orderInfo.orderNo}</a><br>
                <c:if test="${invoice.receivableAmount gt 0}">
                    <b>Payment Due:</b> ${invoice.receivableAmount}<br>
                </c:if>

                <b>Account:</b> ${invoice.orderInfo.clientInfo.accountNo}
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <!-- Table row -->
        <div class="row">
            <div class="col-xs-12 table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Qty</th>
                        <th>Product</th>
                        <th>Serial #</th>
                        <th>Description</th>
                        <th>Subtotal</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Call of Duty</td>
                        <td>455-981-221</td>
                        <td>El snort testosterone trophy driving gloves handsome</td>
                        <td>$64.50</td>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>Need for Speed IV</td>
                        <td>247-925-726</td>
                        <td>Wes Anderson umami biodiesel</td>
                        <td>$50.00</td>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>Monsters DVD</td>
                        <td>735-845-642</td>
                        <td>Terry Richardson helvetica tousled street art master</td>
                        <td>$10.70</td>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>Grown Ups Blue Ray</td>
                        <td>422-568-642</td>
                        <td>Tousled lomo letterpress</td>
                        <td>$25.99</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <div class="row">

            <c:if test="${invoice.description ne null and '' ne invoice.description}">
                <!-- accepted payments column -->
                <div class="col-xs-6">
                    <p class="lead">Remark:</p>

                    <p class="text-muted well well-sm no-shadow" style="margin-top: 10px;">
                            ${invoice.description}
                    </p>
                </div>
            </c:if>
            <!-- /.col -->
            <div class="col-xs-3 pull-right">
                <%--<p class="lead">Amount Due 2/22/2014</p>--%>

                <div class="table-responsive">
                    <table class="table">
                        <tr>
                            <th style="width:50%">Subtotal:</th>
                            <td>Rs.<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.orderInfo.totalAmount}"/></td>
                        </tr>
                        <tr>
                            <th>Tax (<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.orderInfo.tax}"/>%)</th>
                            <td>Rs.<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.orderInfo.totalAmount * invoice.orderInfo.tax /100}"/></td>
                        </tr>
                        <tr>
                            <th>Total:</th>
                            <td>Rs.<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.totalAmount}"/></td>
                        </tr>
                    </table>
                </div>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <!-- this row will not appear when printing -->
        <div class="row no-print">
            <div class="col-xs-12">
                <a href="invoice-print.html" target="_blank" class="btn btn-default"><i class="fa fa-print"></i> Print</a>
                <button type="button" class="btn btn-success pull-right"><i class="fa fa-credit-card"></i> Submit Payment
                </button>
                <button type="button" class="btn btn-primary pull-right" style="margin-right: 5px;">
                    <i class="fa fa-download"></i> Generate PDF
                </button>
            </div>
        </div>
    </section>
    <!-- /.content -->
    <div class="clearfix"></div>
</div>
<%@include file="/pages/parts/footer.jsp" %>
