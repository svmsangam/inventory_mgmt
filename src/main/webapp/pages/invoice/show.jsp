<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<input type="hidden" value="${pageContext.request.contextPath}" id="page">
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
                        Receipt <span id="inv">#${invoice.invoiceNo}</span>
                        <c:if test="${invoice.fiscalYearInfo ne null}"><span style="margin-left: 20%;">Fiscal year : &nbsp;${invoice.fiscalYearInfo.title}</span></c:if>
                        <small class="pull-right">Date: <fmt:formatDate pattern="MMM dd, yyyy"
                                                                        value="${invoice.invoiceDate}"/></small>
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
                        <c:if test="${invoice.orderInfo.clientInfo.companyName eq null or empty invoice.orderInfo.clientInfo.companyName}">
                            <strong>${invoice.orderInfo.clientInfo.name}</strong><br>
                        </c:if>

                        <c:if test="${invoice.orderInfo.clientInfo.companyName ne null and not empty invoice.orderInfo.clientInfo.companyName}">
                            <strong>${invoice.orderInfo.clientInfo.companyName}</strong><br>
                        </c:if>

                        <c:if test="${invoice.orderInfo.clientInfo.cityInfoDTO.cityName ne null and not empty invoice.orderInfo.clientInfo.cityInfoDTO.cityName}">
                            ${invoice.orderInfo.clientInfo.cityInfoDTO.cityName},
                        </c:if>

                        <c:if test="${invoice.orderInfo.clientInfo.cityInfoDTO.stateName ne null and not empty invoice.orderInfo.clientInfo.cityInfoDTO.stateName}">
                            ${invoice.orderInfo.clientInfo.cityInfoDTO.stateName},
                        </c:if>

                        <c:if test="${invoice.orderInfo.clientInfo.cityInfoDTO.countryName ne null and not empty invoice.orderInfo.clientInfo.cityInfoDTO.countryName}">
                            ${invoice.orderInfo.clientInfo.cityInfoDTO.countryName}<br>
                        </c:if>

                        <c:if test="${invoice.orderInfo.clientInfo.street ne null and not empty invoice.orderInfo.clientInfo.street}">
                            ${invoice.orderInfo.clientInfo.street}<br>
                        </c:if>

                        <c:if test="${invoice.orderInfo.clientInfo.pan ne null and not empty invoice.orderInfo.clientInfo.pan}">
                            Pan Number : ${invoice.orderInfo.clientInfo.pan}<br>
                        </c:if>


                        Phone:<c:if
                            test="${invoice.orderInfo.clientInfo.contact ne null and not empty invoice.orderInfo.clientInfo.contact}">${invoice.orderInfo.clientInfo.contact}</c:if>
                        <c:if test="${invoice.orderInfo.clientInfo.mobileNumber ne null and not empty invoice.orderInfo.clientInfo.mobileNumber}">,${invoice.orderInfo.clientInfo.mobileNumber}</c:if><br>
                        Email: <c:if
                            test="${invoice.orderInfo.clientInfo.email ne null and not empty invoice.orderInfo.clientInfo.email}">${invoice.orderInfo.clientInfo.email}</c:if>

                    </address>
                </div>
                <!-- /.col -->
                <div class="col-sm-4 invoice-col no-print">
                    <%--<b>Invoice #${invoice.invoiceNo}</b><br>
                    <br>--%>
                    <b>Order ID:</b> <a
                        href="${pageContext.request.contextPath}/order/sale/${invoice.orderInfo.orderId}">#${invoice.orderInfo.orderNo}</a><br>
                    <c:if test="${invoice.receivableAmount gt 0}">
                        <b>Payment Due:</b> <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true"
                                                              value="${invoice.receivableAmount}"/><br>
                    </c:if>

                    <b>Account:</b> <a
                        href="${pageContext.request.contextPath}/paymentinfo/add?invoiceId=${invoice.invoiceId}">${invoice.orderInfo.clientInfo.accountNo}</a>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
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
                                    <td>${orderItem.quantityAfterReturn}
                                        &nbsp; ${orderItem.itemInfoDTO.productInfo.unitInfo.code}</td>
                                    <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true"
                                                          value="${orderItem.rate}"/></td>
                                    <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true"
                                                          value="${orderItem.discount}"/></td>
                                    <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true"
                                                          value="${orderItem.amount}"/></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                            <tfoot>
                            <tr>
                                <th colspan="5"><p class="pull-right">Net Total:</p></th>
                                <td>Rs.<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true"
                                                         value="${invoice.orderInfo.totalAmount}"/></td>
                            </tr>
                            <tr>
                                <th colspan="5"><p class="pull-right">Tax (<fmt:formatNumber type="number"
                                                                                             maxFractionDigits="3"
                                                                                             groupingUsed="true"
                                                                                             value="${invoice.orderInfo.tax}"/>%)</p>
                                </th>
                                <td>Rs.<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true"
                                                         value="${invoice.orderInfo.totalAmount * invoice.orderInfo.tax /100}"/></td>
                            </tr>
                            <tr>
                                <th colspan="5"><p class="pull-right">Grand Total:</p></th>
                                <td>Rs.<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true"
                                                         value="${invoice.totalAmount}"/></td>
                            </tr>
                            <tr>
                                <td colspan="5"><p class="pull-right">Entered By :</p></td>
                                <td>${invoice.createdByName}</td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                    <!-- /.col -->
                </div>
            </div>

            <div class="row">

                <c:if test="${invoice.description ne null and '' ne invoice.description}">
                    <!-- accepted payments column -->
                    <div class="col-xs-12 no-print pull-left">
                        <div class="panel panel-color panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">Remarks</h3>
                            </div>
                            <div class="panel-body">
                                <p>${invoice.description}</p>
                            </div>
                        </div>
                    </div>
                </c:if>
                <!-- /.col -->
            </div>
            <!-- /.row -->

            <div class="row">
                <c:choose>
                    <c:when test="${invoice.canceled eq true}">
                        <div class="col-xs-6 no-print">

                            <div class="panel panel-color panel-danger">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Cancel Reason</h3>
                                </div>
                                <div class="panel-body">
                                    <p> ${invoice.cancelNote}</p>
                                </div>
                            </div>

                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-xs-6 no-print margin"><label class="label label-success">Active</label></div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <!-- this row will not appear when printing -->
        <div class="row no-print" id="editor">
            <div class="col-md-2 margin-b-5">
                <a href="${pageContext.request.contextPath}/invoice/print?invoiceId=${invoice.invoiceId}"
                   class="btn btn-yahoo btn-block" target="_blank"><i class="fa fa-print"></i>&nbsp;&nbsp;Print</a>

            </div>

            <div class="col-md-2 margin-b-5">
                <c:if test="${invoice.canceled eq false}">
                    <a href="${pageContext.request.contextPath}/paymentinfo/add?invoiceId=${invoice.invoiceId}"
                       class="btn btn-success btn-block"><i class="fa fa-credit-card"></i>&nbsp;&nbsp;Payment </a>

                </c:if>
            </div>

            <div class="col-md-2 margin-b-5">
                <a href="${pageContext.request.contextPath}/invoice/pdf?invoiceId=${invoice.invoiceId}"
                   class="btn btn-facebook btn-block" target="_blank">
                    <i class="fa fa-file-pdf-o"></i>&nbsp;&nbsp;Generate PDF
                </a>
            </div>

            <div class="col-md-2 margin-b-5">

                <a href="${pageContext.request.contextPath}/invoice/xls?invoiceId=${invoice.invoiceId}"
                   class="btn btn-primary btn-block" target="_blank">
                    <i class="fa fa-file-excel-o"></i>&nbsp;&nbsp;Generate Excel
                </a>
            </div>

            <c:choose>
                <c:when test="${invoice.canceled eq true}">
                    <div class="col-md-2 margin-b-5">
                        <label class="btn btn-danger">canceled</label>
                    </div>

                    <div class="col-md-2">
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="col-md-2 margin-b-5">
                        <button class="btn btn-danger btn-block" data-toggle="modal" data-target="#invoiceCancel">
                            <i class="fa fa-power-off" aria-hidden="true"></i>&nbsp;&nbsp;cancel
                        </button>
                    </div>

                    <div class="col-md-2 margin-b-5">
                        <a href="${pageContext.request.contextPath}/orderreturn/add?orderInfoId=${invoice.orderInfo.orderId}"
                           class="btn-tumblr btn btn-block"><i class="fa fa-cart-arrow-down" aria-hidden="true"></i>&nbsp;&nbsp;return order</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <c:if test="${fn:length(paymentList) gt 0}">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-12"><h4>Payment Details</h4></div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="table-responsive">
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

                                        <c:if test="${paymentInfo.receivedPayment ne null}">
                                            <tr>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${paymentInfo.paymentDate}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      groupingUsed="true"
                                                                      value="${paymentInfo.receivedPayment.amount}"/></td>
                                                <td>${paymentInfo.receivedPayment.paymentMethod}</td>
                                                <td>${paymentInfo.remark}</td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${paymentInfo.receivedPayment.chequeDate}"/></td>

                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${paymentInfo.receivedPayment.commitedDateOfCheque}"/></td>
                                                <td>${paymentInfo.receivedPayment.bankOfCheque}</td>
                                                <td>${paymentInfo.receivedPayment.bankAccountNumber}</td>
                                                <th>
                                                    <c:if test="${paymentInfo.receivedPayment.paymentMethod eq 'CHEQUE'}">
                                                        <c:choose>
                                                            <c:when test="${paymentInfo.receivedPayment.status eq 'INACTIVE'}"><a
                                                                    class="btn btn-sm btn-danger"
                                                                    href="${pageContext.request.contextPath}/paymentinfo/chuque/collect?paymentId=${paymentInfo.paymentInfoId}">Is
                                                                Collected ?</a></c:when>
                                                            <c:otherwise><label
                                                                    class="label label-success">collected</label> </c:otherwise>
                                                        </c:choose>
                                                    </c:if>
                                                </th>
                                            </tr>
                                        </c:if>

                                        <c:if test="${paymentInfo.refundPayment ne null}">
                                            <tr>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${paymentInfo.paymentDate}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      groupingUsed="true"
                                                                      value="${paymentInfo.refundPayment.amount}"/></td>
                                                <td>${paymentInfo.refundPayment.paymentMethod}</td>
                                                <td>${paymentInfo.remark}</td>

                                            </tr>
                                        </c:if>
                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>
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
                                        <td><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium"
                                                            value="${log.date}"/></td>
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

    <!-- Modal -->
    <div class="modal fade" id="invoiceCancel" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Cancel invoice</h4>
                </div>
                <form action="${pageContext.request.contextPath}/invoice/cancel" method="post">
                    <div class="modal-body">

                        <input type="hidden" name="invoiceId" value="${invoice.invoiceId}">
                        <input type="hidden" name="version" value="${invoice.version}">
                        <div class="form-group">
                            <label for="description">Reason:</label><br/>
                            <textarea class="form-control" placeholder="write something.." name="note"
                                      id="description" rows="3" cols="30" required></textarea>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-success pull-right">Save changes</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
    <!-- model end -->

</div>
<%@include file="/pages/parts/footer.jsp" %>