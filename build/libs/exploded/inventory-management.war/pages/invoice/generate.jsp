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

<%@include file="../common/businessOwnerSettings.jsp" %>

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

    <div class="row">
        <div class="col-lg-12">


            <div class="panel panel-default">
                <%--<div class="panel-heading">
                    headings here...
                </div>--%>

                <div id="DivIdToPrint">

                    <!-- NEW INVOICE -->

                    <c:if test="${order ne null}">
                        <%--
                                                <form action="${pageContext.request.contextPath}/invoiceInfo/save" method="post" modelAttribute="invoiceInfo" id="registerform" novalidate="novalidate">
                        --%>
                        <form action="${pageContext.request.contextPath}/invoiceInfo/save" method="post"
                              modelAttribute="invoiceInfo">

                            <div class="invoiceInfo-container">
                                <div class="row invoiceInfo-header">
                                    <div class="col-md-3 pull-left">
                                        <img src="../../resources/images/logo.png" alt="">
                                    </div>
                                    <div class="col-md-7 text-center"><span class="invoiceInfo-title">INVOICE</span>
                                    </div>
                                    <div class="col-md-2 pull-right">
                                        <div><input type="text" name="invoiceDate"
                                                    class="invoiceInfo-date datepicker form-control text-right"
                                                    placeholder="Select Date" required></div>
                                        <div class="text-right"><strong>INVOICE #<c:if
                                                test="${invoiceNo ne null}">${invoiceNo }</c:if></strong></div>
                                    </div>
                                </div>

                                <!-- CONFIRM PAYMENT -->
                                <div id="page-confirm" class="hide">

                                    <div class="col-md-12"><h3 class="text-primary">Confirm Payment</h3><br></div>


                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="invoiceInfo-details-block">
                                                <span class="title text-uppercase">Invoice Summary</span>
                                                <table width="100%">
                                                    <tbody>
                                                    <tr class="border-bottom" height="40">
                                                        <td width="40%"><strong>Total Amount</strong></td>
                                                        <td width="60%">Rs. <fmt:formatNumber type="number"
                                                                                              maxFractionDigits="3"
                                                                                              value="${order.totalAmount}"/></td>
                                                    </tr>
                                                    <tr class="border-bottom" height="40">
                                                        <td width="40%"><strong>Tax</strong></td>
                                                        <td width="60%">${order.tax}%</td>
                                                    </tr>
                                                    <tr height="40">
                                                        <td width="40%"><strong>Grand Total</strong></td>
                                                        <td width="60%">Rs. <fmt:formatNumber type="number"
                                                                                              maxFractionDigits="3"
                                                                                              value="${order.grandTotal}"/></td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="invoiceInfo-details-block">
                                                <span class="title text-uppercase">Payment</span>
                                                <div class="form-group">
                                                    <label>Paid Amount * </label>
                                                    <input type="hidden" name="orderId" value="${order.orderId}">
                                                    <input type="hidden" name="actualAmount"
                                                           value="${order.grandTotal}"/>
                                                    <input type="hidden" name="invoiceNo" value="${invoiceNo}"/>
                                                    <input type="number" name="bidAmount" class="form-control"
                                                           required/>
                                                </div>

                                                <div class="form-group">
                                                    <label>Payment Method * </label>
                                                    <select class="form-control" name="paymentMethod" required>
                                                        <c:forEach items="${paymentMethodList}" var="method">
                                                            <c:if test="${method ne 'NON'}">
                                                                <option value="${method}">${method}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="form-group">
                                                    <label>Payment Remark * </label>
                                                    <input type="text" name="bidRemark" class="form-control" required/>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="invoiceInfo-details-block">
                                                <span class="title text-uppercase">Refund</span>
                                                <div class="form-group">
                                                    <label>Return Amount </label>
                                                    <input type="text" name="returnAmount" class="form-control"/>
                                                </div>


                                                <div class="form-group">
                                                    <label>Return Method </label>
                                                    <select class="form-control" name="returnType" required>
                                                        <c:forEach items="${paymentMethodList}" var="method">
                                                            <c:if test="${method ne 'NON'}">
                                                                <option value="${method}">${method}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="form-group">
                                                    <label>Return Remark </label>
                                                    <input type="text" name="returnRemark" class="form-control"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>


                                <!-- END -->

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
                                                                <fmt:formatDate pattern="MMM dd, yyyy"
                                                                                value="${order.orderDate}"/>
                                                            </c:if>
                                                        </td>
                                                    </tr>

                                                    <tr height="30" class="border-bottom">
                                                        <td width="30%">Shipment :</td>
                                                        <td width="70%">
                                                            <c:if test="${order.shippingDate ne null}">
                                                                <fmt:formatDate pattern="MMM dd, yyyy"
                                                                                value="${order.shippingDate}"/>
                                                            </c:if>
                                                            <span style="margin-left: 10px;">via <strong>${order.shipmentChannel }</strong></span>


                                                        </td>
                                                    </tr>


                                                    <tr height="40">
                                                        <td width="30%">Delivery : <br>Address :</td>
                                                        <td width="70%">
                                                            <c:if test="${order.deliveryDate ne null}">
                                                                <fmt:formatDate pattern="MMM dd, yyyy"
                                                                                value="${order.deliveryDate}"/>
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
                                                <td class="cell-padding" width="40%"
                                                    align="left">${orderItem.itemLotDTO.itemDTO.name}</td>
                                                <td class="cell-padding" width="15%"
                                                    align="right">${orderItem.quantity}</td>
                                                <td class="cell-padding" width="15%"
                                                    align="right">${orderItem.rate}</td>
                                                <td class="cell-padding" width="15%"
                                                    align="right">${orderItem.discount}</td>
                                                <td class="cell-padding" width="15%" align="right"><fmt:formatNumber
                                                        type="number" maxFractionDigits="3"
                                                        value="${orderItem.amount}"/></td>
                                            </tr>
                                        </c:forEach>

                                        <tr>

                                            <td class="cell-padding" width="55%" align="right" bgcolor="#f3f7fb">
                                                &nbsp;
                                            </td>
                                            <td class="row-total cell-padding" width="15%" align="right"><strong>Total
                                                Amount</strong></td>
                                            <td class="row-total cell-padding" width="30%" align="right">
                                                <fmt:formatNumber type="number" maxFractionDigits="3"
                                                                  value="${order.totalAmount}"/></td>

                                        </tr>

                                        <tr>

                                            <td class="cell-padding" width="55%" align="right" bgcolor="#f3f7fb">
                                                &nbsp;
                                            </td>
                                            <td class="row-total cell-padding" width="15%" align="right"><strong>Tax
                                                (%)</strong></td>
                                            <td class="row-total cell-padding" width="30%"
                                                align="right">${order.tax}</td>

                                        </tr>


                                        <tr>
                                            <td class="cell-padding" width="55%" align="right" bgcolor="#f3f7fb">
                                                &nbsp;
                                            </td>
                                            <td class="row-total cell-padding" width="15%" align="right"><strong>Grand
                                                Total</strong></td>
                                            <td class="row-total cell-padding" width="30%" align="right"><span
                                                    class="text-grand-total"><strong><fmt:formatNumber type="number"
                                                                                                       maxFractionDigits="3"
                                                                                                       value="${order.grandTotal}"/></strong></span>
                                            </td>

                                        </tr>


                                        </tbody>
                                    </table>


                                </section>

                                <div class="clearfix"></div>


                                <div class="row" style="margin-top: 20px">

                                    <div class="col-md-2 pull-right">
                                        <button type="button" class="btn-proceed btn btn-primary btn-block"
                                                style="margin-top: 4px;">Proceed to Payment
                                        </button>

                                        <button type="submit" class="btn-confirm hide btn btn-success btn-block"
                                                style="margin-top: -1px;">Confirm Payment
                                        </button>
                                    </div>

                                    <div class="col-md-2 pull-right">

                                        <button type="button" class="btn-back hide btn btn-default btn-block">Back
                                        </button>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <button type="submit" class="btn-save btn btn-primary btn-block">Save Invoice
                                        </button>


                                    </div>

                                </div>


                                <!-- PAYMENT FINAL -->


                            </div>

                        </form>
                    </c:if>

                </div>

                <!-- END OF NEW INVOICE -->


                <%@include file="../common/footer.jsp" %>

                <script>
                    $(".btn-proceed").click(function () {

                        $("#invoiceInfo-page").removeClass("show");
                        $("#invoiceInfo-page").addClass("hide");


                        $("#page-confirm").removeClass("hide");
                        $("#page-confirm").addClass("show");

                        $(".btn-proceed").removeClass("show");
                        $(".btn-proceed").addClass("hide");

                        $(".btn-confirm").removeClass("hide");
                        $(".btn-confirm").addClass("show");

                        $(".btn-save").removeClass("show");
                        $(".btn-save").addClass("hide");

                        $(".btn-back").removeClass("hide");
                        $(".btn-back").addClass("show");


                    });


                    $(".btn-back").click(function () {

                        $("#invoiceInfo-page").removeClass("hide");
                        $("#invoiceInfo-page").addClass("show");

                        $(".btn-back").removeClass("show");
                        $(".btn-back").addClass("hide");

                        $("#page-confirm").removeClass("show");
                        $("#page-confirm").addClass("hide");

                        $(".btn-confirm").removeClass("show");
                        $(".btn-confirm").addClass("hide");

                        $(".btn-proceed").removeClass("hide");
                        $(".btn-proceed").addClass("show");

                        $(".btn-save").removeClass("hide");
                        $(".btn-save").addClass("show");


                    });

                </script>

                <script>

                    $(function () {
                        $(".datepicker").datepicker({
                            /* dateFormat: 'yy-mm-dd' */
                        });
                    });


                </script>