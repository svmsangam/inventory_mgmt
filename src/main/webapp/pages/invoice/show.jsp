<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<style>
    @media print {
        *,
        *:before,enter code here
        *:after {
            color: #000 !important;
            text-shadow: none !important;
            background: transparent !important;
            -webkit-box-shadow: none !important;
            box-shadow: none !important;
        }
        a,
        a:visited {
            text-decoration: underline;
        }
        a[href]:after {
            content: " (" attr(href) ")";
        }
        abbr[title]:after {
            content: " (" attr(title) ")";
        }
        a[href^="#"]:after,
        a[href^="javascript:"]:after {
            content: "";
        }
        pre,
        blockquote {
            border: 1px solid #999;

            page-break-inside: avoid;
        }
        thead {
            display: table-header-group;
        }
        tr,
        img {
            page-break-inside: avoid;
        }
        img {
            max-width: 100% !important;
        }
        p,
        h2,
        h3 {
            orphans: 3;
            widows: 3;
        }
        h2,
        h3 {
            page-break-after: avoid;
        }
        select {
            background: #fff !important;
        }
        .navbar {
            display: none;
        }
        .btn > .caret,
        .dropup > .btn > .caret {
            border-top-color: #000 !important;
        }
        .label {
            border: 1px solid #000;
        }
        .table {
            border-collapse: collapse !important;
        }
        .table td,
        .table th {
            background-color: #fff !important;
        }
        .table-bordered th,
        .table-bordered td {
            border: 1px solid #ddd !important;
        }
    }
</style>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <section class="content invoice">

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissable">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                <strong>${message}</strong>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissable">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                <strong>${error}</strong>
            </div>
        </c:if>


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
            <div class="col-lg-12 table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>SN</th>
                        <th>Item</th>
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
            <div class="col-lg-3 pull-right">
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
                <button class="btn btn-default" onclick="window.print();"><i class="fa fa-print"></i> Print</button>

                <c:if test="${invoice.receivableAmount gt 0}">
                    <button type="button" class="btn btn-success pull-right"><i class="fa fa-credit-card"></i> Proceed To Payment
                    </button>
                </c:if>

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