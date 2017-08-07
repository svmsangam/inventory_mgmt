<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 7/6/17
  Time: 2:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Invoice</li>
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

    <div class="panel panel-default">
        <div class="invoiceInfo-container">
            <!-- INVOICE HEADER -->
            <div class="row invoiceInfo-header">
                <div class="col-md-3 pull-left">
                    <img src="../../resources/images/logo.png" alt="">
                </div>
                <div class="col-md-7 text-center"><span class="invoiceInfo-title">INVOICE</span></div>
                <div class="col-md-2 pull-right">
                    <div class="text-right"><strong><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${invoiceInfo.invoiceDate}"/></strong></div>
                    <div class="text-right"><strong>INVOICE # ${invoiceInfo.invoiceNo}</strong></div>
                </div>
            </div>

            <!-- INVOICE HEADER END-->
            <section id="invoiceInfo-page" class="invoiceInfo-section">
                <div class="row">
                    <div class="col-md-4">
                        <div class="invoiceInfo-details-block">
                            <span class="title text-uppercase">Buyer</span>
                            <span class="name">${buyerInfo.name}</span><br>
                            <span>${buyerInfo.address}</span><br><br>
                            <span>Phone : ${buyerInfo.contact}</span><br>
                            <span>Email : ${buyerInfo.email}</span><br>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="invoiceInfo-details-block">
                            <span class="title text-uppercase">Seller</span>
                            <span class="name">${sellerInfo.name}</span><br>
                            <span>${sellerInfo.address}</span><br><br>
                            <span>Phone : ${sellerInfo.contact}</span><br>
                            <span>Email : ${sellerInfo.email}</span><br>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="invoiceInfo-details-block">
                            <span class="title text-uppercase">Order &amp; Delivery Details</span>
                            <table width="100%">
                                <tbody>
                                <tr height="30" class="border-bottom">
                                    <td width="30%">Ordered :</td>
                                    <td width="70%">
                                        <c:if test="${order.orderDate ne null}">
                                            <fmt:formatDate pattern="MMM dd, yyyy" value="${order.orderDate}"/>
                                        </c:if>
                                    </td>
                                </tr>

                                <tr height="30" class="border-bottom">
                                    <td width="30%">Shipment :</td>
                                    <td width="70%">
                                        <c:if test="${order.shippingDate ne null}">
                                            <fmt:formatDate pattern="MMM dd, yyyy" value="${order.shippingDate}"/>
                                        </c:if>
                                        <span style="margin-left: 10px;">via <strong>${order.shipmentChannel }</strong></span>


                                    </td>
                                </tr>


                                <tr height="40">
                                    <td width="30%">Delivery : <br>Address :</td>
                                    <td width="70%">
                                        <c:if test="${order.deliveryDate ne null}">
                                            <fmt:formatDate pattern="MMM dd, yyyy" value="${order.deliveryDate}"/>
                                        </c:if>
                                        <c:if test="${order.saleTrack ne null}">
                                            <strong></strong><span style="margin-left: 10px;"
                                                                   class="label label-success">${order.saleTrack}</span>
                                        </c:if>
                                        <br>
                                        ${order.deliveredTo}
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>


                <table width="100%" border="0" style="margin-top: 30px">
                    <tbody>
                    <tr class="invoiceInfo-table-heading">
                        <td class="cell-padding" width="40%" align="left">Product</td>
                        <td class="cell-padding" width="15%" align="right">Quantity</td>
                        <td class="cell-padding" width="15%" align="right">Rate</td>
                        <td class="cell-padding" width="15%" align="right">Discount</td>
                        <td class="cell-padding" width="15%" align="right">Total</td>
                    </tr>


                    <c:forEach var="orderItem" items="${orderItemList}">
                        <tr>
                            <td class="cell-padding" width="40%" align="left">${orderItem.itemLotDTO.itemDTO.name}</td>
                            <td class="cell-padding" width="15%" align="right">${orderItem.quantity}</td>
                            <td class="cell-padding" width="15%" align="right">${orderItem.rate}</td>
                            <td class="cell-padding" width="15%" align="right">${orderItem.discount}</td>
                            <td class="cell-padding" width="15%" align="right"><fmt:formatNumber type="number"
                                                                                                 maxFractionDigits="3"
                                                                                                 value="${orderItem.amount}"/></td>
                        </tr>
                    </c:forEach>

                    <tr>

                        <td class="cell-padding" width="55%" align="right" bgcolor="#f3f7fb">&nbsp;</td>
                        <td class="row-total cell-padding" width="15%" align="right"><strong>Total Amount</strong></td>
                        <td class="row-total cell-padding" width="30%" align="right"><fmt:formatNumber type="number"
                                                                                                       maxFractionDigits="3"
                                                                                                       value="${order.totalAmount}"/></td>

                    </tr>

                    <tr>

                        <td class="cell-padding" width="55%" align="right" bgcolor="#f3f7fb">&nbsp;</td>
                        <td class="row-total cell-padding" width="15%" align="right"><strong>Tax (%)</strong></td>
                        <td class="row-total cell-padding" width="30%" align="right">${order.tax}</td>

                    </tr>


                    <tr>
                        <td class="cell-padding" width="55%" align="right" bgcolor="#f3f7fb">&nbsp;</td>
                        <td class="row-total cell-padding" width="15%" align="right"><strong>Grand Total</strong></td>
                        <td class="row-total cell-padding" width="30%" align="right"><span
                                class="text-grand-total"><strong><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                                   value="${invoiceInfo.totalAmount}"/></strong></span>
                        </td>

                    </tr>


                    <tr>
                        <td class="cell-padding" width="55%" align="right" bgcolor="#f3f7fb">&nbsp;</td>
                        <td class="row-total cell-padding" width="15%" align="right"><strong>Paid Amount</strong></td>
                        <td class="row-total cell-padding" width="30%" align="right"><span
                                class="text-grand-total"><strong><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                                   value="${invoiceInfo.totalAmount - invoiceInfo.receivableAmount}"/></strong></span>
                        </td>

                    </tr>


                    <tr>
                        <td class="cell-padding" width="55%" align="right" bgcolor="#f3f7fb">&nbsp;</td>
                        <td class="row-total cell-padding" width="15%" align="right"><strong>Due Amount</strong></td>
                        <td class="row-total cell-padding" width="30%" align="right">
                                <span class="text-grand-total">
                                  <strong><fmt:formatNumber type="number" maxFractionDigits="3"
                                                            value="${invoiceInfo.receivableAmount}"/></strong>

                                  </span>
                        </td>

                    </tr>

                    <tr>
                        <td class="cell-padding" width="55%" align="right" bgcolor="#f3f7fb">&nbsp;</td>
                        <td class="row-total cell-padding" width="15%" align="right"></td>
                        <td class="row-total cell-padding" width="30%" align="right">
                            <c:if test="${invoiceInfo.receivableAmount > 0}">
                                <button class="btn btn-xs btn-success" data-toggle="modal" data-target="#lotModal">Make
                                    Payment
                                </button>
                            </c:if>

                        </td>

                    </tr>

                    </tbody>
                </table>


            </section>

            <section>
                <div class="row" style="margin-top: 30px">
                    <div class="col-md-12">
                        <div class="invoiceInfo-details-block">
                            <span class="title text-uppercase">Payment Details</span>
                            <table width="100%">
                                <thead>
                                <tr class="border-bottom" height="40">
                                    <th><strong>Date</strong></th>
                                    <th><strong>Amount</strong></th>
                                    <th><strong>Method</strong></th>
                                    <th><strong>Transaction</strong></th>
                                    <th class="text-right"><strong>Note</strong></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${fn:length(paymentDetailList) ne null}">
                                    <c:forEach items="${paymentDetailList}" var="paymentInfo">
                                        <c:if test="${paymentInfo.receivedPayment.amount ne 0.0}">
                                            <tr class="border-bottom" height="40">
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${paymentInfo.date}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      value="${paymentInfo.receivedPayment.amount}"/></td>
                                                <td>${paymentInfo.receivedPayment.paymentMethod}</td>

                                                <td>Received</td>

                                                <td class="text-right">${paymentInfo.receivedPayment.remark}</td>
                                            </tr>

                                            <c:if test="${paymentInfo.returnedPayment ne null}">
                                                <c:if test="${paymentInfo.returnedPayment.amount ne 0.0}">

                                                    <tr class="border-bottom" height="40">
                                                        <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                            value="${paymentInfo.date}"/></td>
                                                        <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                              value="${paymentInfo.returnedPayment.amount}"/></td>
                                                        <td>${paymentInfo.returnedPayment.paymentMethod}</td>

                                                        <td>Returned</td>

                                                        <td class="text-right">${paymentInfo.returnedPayment.remark}</td>
                                                    </tr>

                                                </c:if>

                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>
</div>

<%--add itemlot start--%>
<div class="modal fade" id="lotModal" role="dialog">
    <div class="modal-dialog">


        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h2 class="modal-title text-center">Make Payment</h2>

                <p class="error errorMsg"></p>
            </div>
            <form action="${pageContext.request.contextPath}/invoiceInfo/makepayment" method="POST"
                  modelAttribute="payment">
                <div class="modal-body customerData">
                    <div class="col-lg-6">
                        <div class="invoiceInfo-details-block">
                            <span class="title text-uppercase">Payment</span>
                            <div class="form-group">
                                <label>Paid Amount * </label>
                                <input type="hidden" name="invoiceId" id="incoiceId" class="form-control"
                                       value="${invoiceInfo.invoiceId}" required/>

                                <input type="text" name="bidAmount" id="bidAmount" class="form-control"
                                       value="${invoiceInfo.receivableAmount}" required/>
                                <br>
                                <label>Payment Method * </label>
                                <select class="form-control" name="paymentMethod" id="paymentMethod" required>
                                    <c:forEach items="${paymentMethodList}" var="method">
                                        <c:if test="${method ne 'NON'}">
                                            <option value="${method}">${method}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Payment Remark * </label>
                                <input type="text" name="bidRemark" id="bidRemark" class="form-control" required/>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">

                        <div class="invoiceInfo-details-block ">
                            <span class="title text-uppercase">Refund</span>
                            <div class="form-group">
                                <label>Return Amount </label>
                                <input type="text" name="returnAmount" id="returnAmount" class="form-control"/>
                            </div>


                            <div class="form-group">
                                <label>Return Method </label>
                                <select class="form-control" name="returnType" id="returnType" required>
                                    <c:forEach items="${paymentMethodList}" var="method">
                                        <c:if test="${method ne 'NON'}">
                                            <option value="${method}">${method}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Return Remark </label>
                                <input type="text" name="returnRemark" id="returnRemark" class="form-control"/>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>

                </div>
                <div class="modal-footer">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" url="${pageContext.request.contextPath}/payment/save" ss="savePayment"
                            class="btn btn-success pull-left">Save
                    </button>
                    <button type="button" class="closeme btn btn-danger" data-dismiss="modal">Close</button>
                </div>

            </form>
        </div>

        <%--add itemlot end--%>


    </div>

</div>

<%@include file="../common/footer.jsp" %>

<script>

    $(document).ready(function () {

        $(document).on("click", ".savePayment", function () {
            // alert("what .!!!");
            var url = $(this).attr("url");

            var receivedAmount = $("#bidAmount").val();
            var receivedMethod = $("#paymentMethod").val();
            var receivedRemark = $("#bidRemark").val();

            var returnedAmount = $("#returnAmount").val();
            var returnedMethod = $("#returnType").val();
            var returnedRemark = $("#returnRemark").val();

            var invoiceId = $("#incoiceId").val();

            makePayment(url, invoiceId, receivedAmount, receivedMethod, receivedRemark, returnedAmount, returnedMethod, returnedRemark);


        });
    });

</script>