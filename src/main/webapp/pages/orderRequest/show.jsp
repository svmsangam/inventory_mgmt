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
            <li class="active">Order</li>
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
                <div class="panel-heading">
                    <a href="${pageContext.request.contextPath}/order/list"
                       style="float:left; margin-bottom:8px;margin-right: 150px;">
                        <button class="btn btn-primary btn-sm">List Order</button>
                    </a>
                    <c:if test="${order.saleTrack eq 'DELIVERED'}">
                        <button class="btn btn-primary pull-right" id="print"><i class="fa fa-print"></i> Print Invoice
                        </button>
                    </c:if>

                </div>
                <div id="DivIdToPrint">
                    <div class="text-center">
                        <strong id="invoiceno">
                            ORDER ID #<c:if test="${order.orderNo ne null}">${order.orderNo }</c:if>
                        </strong>
                    </div>

                    <c:if test="${order ne null}">
                        <div class="col-xs-12">&nbsp;</div>
                        <div class="col-xs-6">

                            <div class="col-xs-10">
                                <div class="span4 well">

                                    <span class="title text-uppercase"><strong>Buyer</strong></span><br>
                                    <span class="name">${buyerInfo.name}</span><br>
                                    <span>${buyerInfo.address}</span><br><br>
                                    <span>Phone : ${buyerInfo.contact}</span><br>
                                    <span>Email : ${buyerInfo.email}</span><br>

                                </div>
                                <div class="span4 well">

                                    <span class="title text-uppercase"><strong>Seller</strong></span><br>
                                    <span class="name">${sellerInfo.name}</span><br>
                                    <span>${sellerInfo.address}</span><br><br>
                                    <span>Phone : ${sellerInfo.contact}</span><br>
                                    <span>Email : ${sellerInfo.email}</span><br>

                                </div>
                            </div>

                            <div class="col-xs-10">

                            </div>

                        </div>
                        <div class="col-xs-5 pull-right">
                            <input type="hidden" id="orderId" value="${order.orderId}">

                            <span class="col-xs-offset-5 no-print hidden-print"><strong>Track Index</strong></span>
                            <div class="table-responsive">
                            <table class="table no-print hidden-print" style="border-bottom-color: 2px blue;">
                                <thead class="thead-inverse">
                                <c:choose>
                                    <c:when test="${order.orderType eq 'Sale'}">
                                        <tr>

                                            <c:if test="${order.saleTrack eq 'NULL'}">
                                                <th>Pending</th>
                                                <th>Accepted</th>
                                                <th style="color: red;">Cancel</th>
                                            </c:if>


                                            <c:if test="${order.saleTrack eq 'ACCEPTED'}">
                                                <th>Accepted</th>
                                                <th style="color: #f2bf07;">Packed</th>
                                            </c:if>

                                            <c:if test="${order.saleTrack eq 'PACKED'}">
                                                <th style="color: #f2bf07;">Packed</th>
                                                <th style="color: #0612f1;">Shipped</th>
                                            </c:if>

                                            <c:if test="${order.saleTrack eq 'SHIPPED'}">
                                                <th style="color: #0612f1;">Shipped</th>
                                                <th style="color: #08cc06;">Delivered</th>
                                            </c:if>

                                            <c:if test="${order.saleTrack eq 'DELIVERED'}">
                                                <th style="color: #08cc06;">Delivered</th>
                                            </c:if>

                                            <c:if test="${order.saleTrack eq 'CANCEL'}">
                                                <th style="color: red;">Cancelled</th>
                                            </c:if>

                                        </tr>
                                    </c:when>

                                    <c:otherwise>
                                        <tr>
                                            <th style="color: #0612f1;">Issued</th>
                                            <th style="color: #08cc06;">Received</th>
                                            <th style="color: red;">Cancelled</th>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>

                                </thead>
                                <tbody>


                                <c:choose>
                                    <c:when test="${order.orderType eq 'Sale'}">
                                        <tr>

                                            <c:if test="${order.saleTrack eq 'NULL'}">
                                                <td><input type="radio" class="track" name="saleTrack" value="NULL"
                                                           mycheck="yes" checked="checked"/></td>
                                                <td><input type="radio" class="track" name="saleTrack"
                                                           url="${pageContext.request.contextPath}/order/saletrack"
                                                           value="ACCEPTED"/></td>
                                                <td><input type="radio" class="track" name="saleTrack"
                                                           url="${pageContext.request.contextPath}/order/saletrack"
                                                           value="CANCEL"/></td>
                                            </c:if>


                                            <c:if test="${order.saleTrack eq 'ACCEPTED'}">
                                                <td><input type="radio" class="track" name="saleTrack" value="ACCEPTED"
                                                           mycheck="yes" checked="checked"/></td>
                                                <td><input type="radio" class="track"
                                                           url="${pageContext.request.contextPath}/order/saletrack"
                                                           name="saleTrack" value="PACKED"/></td>
                                            </c:if>

                                            <c:if test="${order.saleTrack eq 'PACKED'}">
                                                <td><input type="radio" class="track" name="saleTrack" value="PACKED"
                                                           mycheck="yes" checked="checked"/></td>
                                                <td><input type="radio" class="track"
                                                           url="${pageContext.request.contextPath}/order/saletrack"
                                                           name="saleTrack" value="SHIPPED"/></td>
                                            </c:if>

                                            <c:if test="${order.saleTrack eq 'SHIPPED'}">
                                                <td><input type="radio" class="track" name="saleTrack" value="SHIPPED"
                                                           mycheck="yes" checked="checked"/></td>
                                                <td><input type="radio" class="track"
                                                           url="${pageContext.request.contextPath}/order/saletrack"
                                                           name="saleTrack" value="DELIVERED"/></td>
                                            </c:if>

                                            <c:if test="${order.saleTrack eq 'DELIVERED'}">
                                                <td style="color: #08cc06;">Delivered</td>
                                            </c:if>

                                            <c:if test="${order.saleTrack eq 'CANCEL'}">
                                                <td style="color: red;">Cancelled</td>
                                            </c:if>

                                        </tr>
                                    </c:when>

                                    <c:otherwise>

                                        <td>
                                            <c:if test="${order.purchaseTrack eq 'ISSUED'}">
                                                <input type="radio" class="track" name="purchaseTrack" value="ISSUED"
                                                       mycheck="yes" checked="checked"/>
                                            </c:if>
                                            <c:if test="${order.purchaseTrack ne 'ISSUED'}">
                                                <input type="radio"
                                                       url="${pageContext.request.contextPath}/order/purchasetrack"
                                                       class="track" name="purchaseTrack" value="ISSUED"/>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${order.purchaseTrack eq 'RECEIVED'}">
                                                <input type="radio" class="track" name="purchaseTrack" mycheck="yes"
                                                       value="RECEIVED" checked="checked"/>
                                            </c:if>
                                            <c:if test="${order.purchaseTrack ne 'RECEIVED'}">
                                                <input type="radio" class="track"
                                                       url="${pageContext.request.contextPath}/order/purchasetrack"
                                                       name="purchaseTrack" value="RECEIVED"/>
                                            </c:if>
                                        </td>

                                        <td>

                                            <c:if test="${order.purchaseTrack eq 'CANCELLED'}">
                                                <input type="radio" class="track" name="purchaseTrack" mycheck="yes"
                                                       value="CANCELLED" checked="checked"/>
                                            </c:if>
                                            <c:if test="${order.purchaseTrack ne 'CANCELLED'}">
                                                <input type="radio" class="track"
                                                       url="${pageContext.request.contextPath}/order/purchasetrack"
                                                       name="purchaseTrack" value="CANCELLED"/>
                                            </c:if>
                                        </td>


                                    </c:otherwise>
                                </c:choose>


                                </tbody>
                            </table>
                            </div>

                            <div class="well col-xs-12">
                                <span class="text-center "><strong>Delivery Details</strong></span><br>
                                <div class="table-responsive">
                                <table class="center-block" style="margin-top: 10px;">
                                    <tbody>
                                    <tr>
                                        <td><c:if test="${order.deliveredTo ne null}">
                                            <strong>Delivery City: </strong>${order.deliveredTo}
                                        </c:if></td>
                                    </tr>
                                    <tr>
                                        <td><c:if test="${order.shipmentChannel ne null}">
                                            <strong>Shipment Channel: </strong>${order.shipmentChannel }
                                        </c:if></td>
                                    </tr>
                                    </tbody>
                                </table>
                                </div>
                                <br>

                                <table class="center-block">
                                    <tbody>
                                    <tr>
                                        <td><strong>Order: </strong></td>
                                        <td>
                                            <c:if test="${order.orderDate ne null}">
                                                <fmt:formatDate pattern="MMM dd, yyyy" value="${order.orderDate}"/>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><strong>Shipment: </strong></td>
                                        <td><c:if test="${order.shippingDate ne null}">
                                            <fmt:formatDate pattern="MMM dd, yyyy" value="${order.shippingDate}"/>
                                        </c:if></td>
                                    </tr>
                                    <tr>
                                        <td><strong>Delivery: </strong></td>
                                        <td><c:if test="${order.deliveryDate ne null}">
                                            <fmt:formatDate pattern="MMM dd, yyyy" value="${order.deliveryDate}"/>
                                        </c:if></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                        </div>

                        <div class="container">
                            <c:if test="${orderItemList ne null}">
                                <div class="form-group table-responsive" id="inputDiv">
                                    <c:if test="${fn:length(orderItemList) gt 0}">
                                        <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                            <tr>
                                                <th>itemName</th>
                                                <th>quantity</th>
                                                <th>rate</th>
                                                <th>discount(%)</th>
                                                <th>total</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="orderItem" items="${orderItemList}">
                                                <tr>
                                                    <td>${orderItem.itemLotDTO.itemDTO.name}</td>
                                                    <td>${orderItem.quantity}</td>
                                                    <td>${orderItem.rate}</td>
                                                    <td>${orderItem.discount}</td>
                                                    <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                          value="${orderItem.amount}"/></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                            <tfoot>
                                            <tr>
                                                <th>Total Amount</th>
                                                <th></th>
                                                <th></th>
                                                <th></th>
                                                <th><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      value="${order.totalAmount}"/></th>
                                            </tr>
                                            <tr>
                                                <th>Tax(%)</th>
                                                <th></th>
                                                <th></th>
                                                <th></th>
                                                <th>${order.tax}</th>
                                            </tr>
                                            <tr>
                                                <th>Grand Total</th>
                                                <th></th>
                                                <th></th>
                                                <th></th>
                                                <th><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      value="${order.grandTotal}"/></th>
                                            </tr>
                                            </tfoot>
                                        </table>
                                        </div>
                                    </c:if>
                                </div>
                            </c:if>
                        </div>
                        <div class="panel-body no-print hidden-print" id="note">
                            <c:if test="${order.description ne null}">
                                <div class="col-xs-4 pull-right">
                                    <label class="pull-right">Order description: </label>
                                    <textarea rows="3" class="form-control" readonly>${order.description }</textarea>
                                </div>
                            </c:if>
                        </div>
                    </c:if>

                </div>
            </div>
        </div>

    </div>
</div>
<%@include file="../common/footer.jsp" %>

<script>
    $(document).ready(
        function () {

            $('input:radio').click(
                function () {
                    var oldTrackEvent;
                    $('input:radio').each(function () {

                        if ($(this).attr('mycheck') == 'yes') {
                            oldTrackEvent = $(this);
                        }

                    });

                    var oldTrack = "";
                    if (oldTrackEvent.val() === 'NULL') {
                        oldTrack = "Pending";
                    } else {
                        oldTrack = oldTrackEvent.val();
                    }

                    if (confirm("Are you sure to change the status from " + oldTrack + " to " + $(this).val() + " ?")) {
                        oldTrackEvent.attr('mycheck', 'no');
                        $(this).attr('mycheck', 'yes');
                        return true;

                    } else {
                        return false;
                    }
                });

            $('input:radio[name="saleTrack"]').change(
                function () {
                    var track = $(this).val();
                    var url = $(this).attr("url");
                    var orderId = $("#orderId").val();

                    changeTrack(url, orderId, track);
                });
        });
</script>


<script type="text/javascript">
    $(document).ready(function () {


        $("#print").click(function () {


            /*$("#DivIdToPrint").print({
             globalStyles: true,
             mediaPrint: false,
             stylesheet: null,
             noPrintSelector: ".no-print",
             iframe: true,
             append: null,
             prepend: null,
             manuallyCopyFormValues: true,
             deferred: $.Deferred(),
             timeout: 750,
             title: null,
             doctype: '<!doctype html>'
             });*/


            var invoiceNo = document.getElementById('invoiceno').innerHTML;
            var divToPrint = document.getElementById('DivIdToPrint');

            var newWin = window.open('', 'Print-Window');

            newWin.document.open();

            var page = '<html><head><title>invoiceInfo ' + invoiceNo + '</title><style>@page { margin: 5px 5px 5px 5px; }</style><link rel="stylesheet" type="text/css" href="../resources/landing/css/bootstrap.min.css"><link rel="stylesheet" type="text/css" href="../resources/landing/css/styles.css"></head><body onload="window.print()" ><div class="row">';
            page += '<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main"><div class="col-lg-12"><div class="panel panel-default">' + divToPrint.innerHTML + '</body></html> </div></div></div></div>';

            newWin.document.write(page);

            newWin.document.close();

            setTimeout(function () {
                newWin.close();
            }, 1000);
        });
    });


</script>