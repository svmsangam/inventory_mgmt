<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<input type="hidden" value="${pageContext.request.contextPath}" id ="page">
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


        <div id="contentPDF">
            <!-- title row -->
        <div class="row">
            <div class="col-xs-12">
                <h2 class="page-header">
                    <%--<i class="fa fa-globe"></i>--%> Invoice <span id="inv">#${invoice.invoiceNo}</span>
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
            <div class="col-sm-4 invoice-col no-print">
                <%--<b>Invoice #${invoice.invoiceNo}</b><br>
                <br>--%>
                <b>Order ID:</b> <a href="${pageContext.request.contextPath}/order/sale/${invoice.orderInfo.orderId}">#${invoice.orderInfo.orderNo}</a><br>
                <c:if test="${invoice.receivableAmount gt 0}">
                    <b>Payment Due:</b> <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.receivableAmount}"/><br>
                </c:if>

                <b>Account:</b> <a href="${pageContext.request.contextPath}/paymentinfo/add?invoiceId=${invoice.invoiceId}">${invoice.orderInfo.clientInfo.accountNo}</a>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <!-- Table row -->
        <div class="col-md-12">
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
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
                <div class="col-xs-6 no-print">
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
                            <th style="width:50%">Net Total:</th>
                            <td>Rs.<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.orderInfo.totalAmount}"/></td>
                        </tr>
                        <tr>
                            <th>Tax (<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.orderInfo.tax}"/>%)</th>
                            <td>Rs.<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.orderInfo.totalAmount * invoice.orderInfo.tax /100}"/></td>
                        </tr>
                        <tr>
                            <th>Grand Total:</th>
                            <td>Rs.<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.totalAmount}"/></td>
                        </tr>
                        <tr>
                            <td>Entered By :</td>
                            <td>${invoice.createdByName}</td>
                        </tr>
                    </table>
                </div>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
        </div>
        <!-- this row will not appear when printing -->
        <div class="row no-print" id="editor">
            <div class="col-xs-12">
                <a href="${pageContext.request.contextPath}/invoice/print?invoiceId=${invoice.invoiceId}" class="btn btn-default" target="_blank"><i class="fa fa-print"></i> Print</a>

                    <a href="${pageContext.request.contextPath}/paymentinfo/add?invoiceId=${invoice.invoiceId}" class="btn btn-success pull-right"><i class="fa fa-credit-card"></i> Proceed To Payment </a>

                <a href="${pageContext.request.contextPath}/invoice/pdf?invoiceId=${invoice.invoiceId}" class="btn btn-primary pull-right" target="_blank" style="margin-right: 5px;">
                    <i class="fa fa-download"></i> Generate PDF
                </a>
            </div>
        </div>

        <c:if test="${fn:length(paymentList) gt 0}">
            <div class="row">
                <div class="col-md-12">
                    <div class="row"><div class="col-md-12"><h4>Payment Details</h4></div> </div>
                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Method</th>
                                    <th>Remarks</th>
                                    <th>Cheque Date</th>
                                    <th>Exp-Withdrawable Date</th>
                                    <th>Bank Name</th>
                                    <th>Bank Account</th>
                                    <th>Action</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${paymentList}" var="paymentInfo">

                                    <tr>
                                        <td><fmt:formatDate pattern="MMM dd, yyyy" value="${paymentInfo.paymentDate}"/></td>
                                        <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${paymentInfo.receivedPayment.amount}"/></td>
                                        <td>${paymentInfo.receivedPayment.paymentMethod}</td>
                                        <td>${paymentInfo.remark}</td>
                                        <td><fmt:formatDate pattern="MMM dd, yyyy" value="${paymentInfo.receivedPayment.chequeDate}"/></td>

                                        <td><fmt:formatDate pattern="MMM dd, yyyy" value="${paymentInfo.receivedPayment.commitedDateOfCheque}"/></td>
                                        <td>${paymentInfo.receivedPayment.bankOfCheque}</td>
                                        <td>${paymentInfo.receivedPayment.bankAccountNumber}</td>
                                        <th>
                                            <c:if test="${paymentInfo.receivedPayment.paymentMethod eq 'CHEQUE'}">
                                                <c:choose>
                                                    <c:when test="${paymentInfo.receivedPayment.status eq 'INACTIVE'}"><a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/paymentinfo/chuque/collect?paymentId=${paymentInfo.paymentInfoId}">Is Collected ?</a></c:when>
                                                    <c:otherwise><label class="label label-success">collected</label> </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </th>
                                    </tr>

                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${fn:length(logger) gt 0}">
            <div class="row" style="margin-top: 10px;">

            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-6">
                        <h4 class="text-center">Printed Logs</h4>
                        <table class="table">
                            <thead>
                            <tr>
                                <th>S.N.</th>
                                <th>Username</th>
                                <th>Log</th>
                                <th>Date</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach varStatus="i" items="${logger}" var="log">
                                <tr>
                                    <td>${i.index + 1}</td>
                                    <td>${log.username}</td>
                                    <td>${log.log}</td>
                                    <td><fmt:formatDate type = "both" dateStyle = "medium" timeStyle = "medium" value = "${log.date}" /></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
    </section>
    <!-- /.content -->
    <div class="clearfix"></div>

</div>
<%@include file="/pages/parts/footer.jsp" %>