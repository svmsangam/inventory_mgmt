<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/6/18
  Time: 6:45 PM
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
                <div class="box box-primary box-solid" style="border: none;">
                    <div class="box-header with-border">
                        <h3 class="box-title text-center">Payment Section</h3>
                        <!-- /.box-tools -->
                    </div>
                </div>

                <h2 class="page-header">
                    <c:if test="${order ne null}"><span>Order No. <b>#${order.orderNo}</b></span></c:if>
                </h2>
            </div>
            <!-- /.col -->
        </div>
        <!-- info row -->
        <div class="row invoice-info">
            <div class="col-md-6 invoice-col">
                <address>
                    <strong>Buyer Details</strong><br>

                    <c:if test="${order.clientInfo.companyName ne null and order.clientInfo.companyName ne ''}">
                        ${order.clientInfo.companyName}<br>
                    </c:if>

                    ${order.clientInfo.name}<br>

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
            <div class="col-md-6 invoice-col">
                <address>
                    <strong>Shipping Address</strong><br>
                    ${order.deliveryAddress}<br>
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
            <div class="col-md-12 table-responsive">
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
                    <tfoot>
                    <tr>
                        <th colspan="6"><p class="pull-right">Net Total</p></th>
                        <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.totalAmount}"/></td>
                    </tr>

                    <tr>
                        <th colspan="6"><p class="pull-right">Tax (<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.tax}"/>%)</p></th>
                        <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.totalAmount * order.tax / 100}"/></td>
                    </tr>

                    <tr>
                        <th colspan="6"><p class="pull-right">Grand Total:</p></th>
                        <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.grandTotal}"/></td>
                    </tr>
                    </tfoot>
                </table>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <div class="row">
            <!-- accepted payments column -->
            <div class="col-lg-6 pull-left">
                <label>Description</label>
                <p class="well well-sm no-shadow" style="margin-top: 5px;">
                    ${order.description}
                </p>
            </div>
        </div>
        <!-- /.row -->

        <%--payment row start--%>
        <div class="box box-success box-solid">
            <div class="box-header with-border">
                <h3 class="box-title">Payment Form</h3>
                <!-- /.box-tools -->
            </div>
            <!-- /.box-header -->
            <div class="box-body">
                <div class="row">
                    <div class="col-md-12">
                        <form action="${pageContext.request.contextPath}/order/sale/quick/confirm" method="post" modelAttribute="payment" >

                            <input type="hidden" name="orderInfoId" value="${order.orderId}"/>

                            <div class="col-md-12">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label">Payment Method</label>
                                        <select name="receivedPayment.paymentMethod" class="form-control select2" id="paymentMethod">
                                            <c:forEach items="${paymentMethodList}" var="paymentMethod">
                                                <c:choose>
                                                    <c:when test="${paymentMethod eq paymentInfo.receivedPayment.paymentMethod}">
                                                        <option value="${paymentMethod}" selected>${paymentMethod}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${paymentMethod}">${paymentMethod}</option>
                                                    </c:otherwise>
                                                </c:choose>

                                            </c:forEach>
                                        </select>
                                        <p class="form-error">${paymentError.paymentMethod}</p>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label">Amount</label>
                                        <input type="text" onkeypress="return event.charCode >= 46 && event.charCode !== 47 && event.charCode < 58;" class="form-control" value="<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${paymentInfo.receivedPayment.amount}"/>" name="receivedPayment.amount" placeholder="amount">
                                        <p class="form-error">${paymentError.amount}</p>
                                        <p class="form-error">${paymentError.invoice}</p>
                                    </div>
                                </div>

                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="control-label">Remarks</label>
                                        <input type="text" class="form-control" value="${paymentInfo.remark}" name="remark" placeholder="remark">
                                        <p class="form-error">${paymentError.remark}</p>
                                    </div>
                                </div>
                            </div>

                            <c:choose>
                                <c:when test="${paymentInfo.receivedPayment.paymentMethod eq 'CHEQUE'}">
                                    <div class="col-md-12">
                                        <div class="col-md-4">
                                            <div class="form-group cheque">
                                                <label>Cheque Date</label>
                                                <div class='input-group date'>
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="text" class="form-control datepicker" onkeyup="return false;" onkeypress="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${paymentInfo.receivedPayment.chequeDate}"/>" name="receivedPayment.chequeDate" placeholder="select date">
                                                </div>
                                                <p class="form-error">${paymentError.chequeDate}</p>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="form-group cheque">
                                                <label>Commited Date Of Cheque</label>
                                                <div class='input-group date'>
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="text" class="form-control datepicker" onkeyup="return false;" onkeypress="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${paymentInfo.receivedPayment.commitedDateOfCheque}"/>" name="receivedPayment.commitedDateOfCheque" placeholder="select date">
                                                </div>
                                                <p class="form-error">${paymentError.commitedDateOfCheque}</p>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="form-group cheque">
                                                <label>Bank Name</label>
                                                <input type="text" class="form-control" value="${paymentInfo.receivedPayment.bankOfCheque}" name="receivedPayment.bankOfCheque" placeholder="Bank Name">
                                                <p class="form-error">${paymentError.bankOfCheque}</p>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="form-group cheque">
                                                <label>Bank Account Number</label>
                                                <input type="text" class="form-control" value="${paymentInfo.receivedPayment.bankAccountNumber}" name="receivedPayment.bankAccountNumber" placeholder="Bank Account Number">
                                                <p class="form-error">${paymentError.bankAccountNumber}</p>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>

                                <c:otherwise>
                                    <div class="col-md-12">
                                        <div class="col-md-2">
                                            <div class="form-group cheque hidden">
                                                <label>Cheque Date</label>
                                                <div class="input-group">
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="text" class="form-control datepicker" onkeyup="return false;" onkeypress="return false;" name="receivedPayment.chequeDate" placeholder="select date">
                                                </div>
                                                <p class="form-error">${paymentError.chequeDate}</p>
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group cheque hidden">
                                                <label>Commited Date</label>
                                                <div class="input-group">
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="text" class="form-control datepicker" onkeyup="return false;" onkeypress="return false;" name="receivedPayment.commitedDateOfCheque" placeholder="select date">
                                                </div>
                                                <p class="form-error">${paymentError.commitedDateOfCheque}</p>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="form-group cheque hidden">
                                                <label>Bank Name</label>
                                                <input type="text" class="form-control" name="receivedPayment.bankOfCheque" placeholder="Bank Name">
                                                <p class="form-error">${paymentError.bankOfCheque}</p>
                                            </div>
                                        </div>
                                        <div class="col-md-5">
                                            <div class="form-group cheque hidden">
                                                <label>Bank Account Number</label>
                                                <input type="text" class="form-control" name="receivedPayment.bankAccountNumber" placeholder="Bank Account Number">
                                                <p class="form-error">${paymentError.bankAccountNumber}</p>
                                            </div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                            <!-- /.box-body -->
                            <div class="modal-footer" style="padding: 5px;">
                                <a href="${pageContext.request.contextPath}/order/sale/cancel?orderId=${order.orderId}" class="btn btn-danger pull-left"><i class="fa fa-trash-o"></i> &nbsp;Cancel Order</a>
                                <button type="submit" class="btn btn-primary"><i class="fa fa-save"></i> &nbsp;Confirm Order</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
            <!-- /.box-body -->
        </div>
        <%--payment row end--%>
    </section>
    <!-- /.content -->
    <div class="clearfix"></div>
</div>
<!-- /.content-wrapper -->
<%@include file="/pages/parts/footer.jsp" %>
