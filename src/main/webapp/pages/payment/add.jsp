<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 10/10/17
  Time: 10:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/24/17
  Time: 9:43 PM
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
    <section class="content">

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

        <div class="row">
            <div class="col-md-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Make Payment</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                    <div class="col-sm-6 invoice-col">
                        Client
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
                    <div class="col-sm-6 invoice-col no-print">
                        <%--<b>Invoice #${invoice.invoiceNo}</b><br>
                        <br>--%>
                        <b>Order ID:</b> <a href="${pageContext.request.contextPath}/order/sale/${invoice.orderInfo.orderId}">#${invoice.orderInfo.orderNo}</a><br>
                            <b>Invoice ID:</b> <a href="${pageContext.request.contextPath}/invoice/${invoice.invoiceId}">#${invoice.invoiceNo}</a><br>
                        <c:if test="${invoice.receivableAmount gt 0}">
                            <b>Payment Due:</b> <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.receivableAmount}"/><br>
                        </c:if>
                            <b>Total Amount:</b> <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.totalAmount}"/><br>

                        <b>Account:</b> ${invoice.orderInfo.clientInfo.accountNo}<br>

                        <b>Invoice Date:</b> <fmt:formatDate pattern="MMM dd, yyyy" value="${invoice.invoiceDate}"/>
                    </div>

                        <c:if test="${fn:length(paymentList) gt 0}">
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
                        </c:if>
                    </div>

                    <div class="clearfix"></div>

                    <form action="${pageContext.request.contextPath}/paymentinfo/save" method="post" modelAttribute="payment" >
                        <div class="box-body">

                            <input type="hidden" name="invoiceInfoId" value="${invoice.invoiceId}"/>

                            <input type="hidden" name="invoiceVersion" value="${invoice.version}"/>

                            <div class="form-group">
                                <label class="control-label">Amount</label>
                                <input type="text" onkeypress="return event.charCode >= 46 && event.charCode !== 47 && event.charCode < 58;" class="form-control" value="<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${paymentInfo.receivedPayment.amount}"/>" name="receivedPayment.amount" placeholder="amount">
                                <p class="form-error">${paymentError.amount}</p>
                                <p class="form-error">${paymentError.invoice}</p>
                            </div>

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

                            <div class="form-group">
                                <label class="control-label">Remarks</label>
                                <input type="text" class="form-control" value="${paymentInfo.remark}" name="remark" placeholder="remark">
                                <p class="form-error">${paymentError.remark}</p>
                            </div>


                            <c:choose>
                                <c:when test="${paymentInfo.receivedPayment ne null}">

                                    <c:if test="${paymentInfo.receivedPayment.paymentMethod eq 'CHEQUE'}">
                                            <div class="form-group cheque">
                                                <label class="control-label">Cheque Date</label>
                                                <div class='input-group date'>
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                <input type="text" class="form-control datepicker" onkeyup="return false;" onkeypress="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${paymentInfo.receivedPayment.chequeDate}"/>" name="receivedPayment.chequeDate" placeholder="select date">
                                                <p class="form-error">${paymentError.chequeDate}</p>
                                            </div>

                                            <div class="form-group cheque">
                                                <label class="control-label">Commited Date Of Cheque</label>
                                                <div class='input-group date'>
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                <input type="text" class="form-control datepicker" onkeyup="return false;" onkeypress="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${paymentInfo.receivedPayment.commitedDateOfCheque}"/>" name="receivedPayment.commitedDateOfCheque" placeholder="select date">
                                                <p class="form-error">${paymentError.commitedDateOfCheque}</p>
                                            </div>

                                            <div class="form-group cheque">
                                                <label class="control-label">Bank Name</label>
                                                <input type="text" class="form-control" value="${paymentInfo.receivedPayment.bankOfCheque}" name="receivedPayment.bankOfCheque" placeholder="Bank Name">
                                                <p class="form-error">${paymentError.bankOfCheque}</p>
                                            </div>

                                            <div class="form-group cheque">
                                                <label class="control-label">Bank Account Number</label>
                                                <input type="text" class="form-control" value="${paymentInfo.receivedPayment.bankAccountNumber}" name="receivedPayment.bankAccountNumber" placeholder="Bank Account Number">
                                                <p class="form-error">${paymentError.bankAccountNumber}</p>
                                            </div>
                                    </c:if>
                                </c:when>

                                <c:otherwise>
                                    <div class="form-group cheque hidden">
                                        <label class="control-label">Cheque Date</label>
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" class="form-control datepicker" onkeyup="return false;" onkeypress="return false;" name="receivedPayment.chequeDate" placeholder="select date">
                                        <p class="form-error">${paymentError.chequeDate}</p>
                                    </div>

                                    <div class="form-group cheque hidden">
                                        <label class="control-label">Commited Date Of Cheque</label>
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" class="form-control datepicker" onkeyup="return false;" onkeypress="return false;" name="receivedPayment.commitedDateOfCheque" placeholder="select date">
                                        <p class="form-error">${paymentError.commitedDateOfCheque}</p>
                                    </div>

                                    <div class="form-group cheque hidden">
                                        <label class="control-label">Bank Name</label>
                                        <input type="text" class="form-control" name="receivedPayment.bankOfCheque" placeholder="Bank Name">
                                        <p class="form-error">${paymentError.bankOfCheque}</p>
                                    </div>

                                    <div class="form-group cheque hidden">
                                        <label class="control-label">Bank Account Number</label>
                                        <input type="text" class="form-control" name="receivedPayment.bankAccountNumber" placeholder="Bank Account Number">
                                        <p class="form-error">${paymentError.bankAccountNumber}</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>

                        </div>
                        <!-- /.box-body -->
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
                <!-- /.box -->
            </div>
        </div>

    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@include file="/pages/parts/footer.jsp" %>

<script>
    $(function () {
        $('.select2').select2()
    })

</script>




