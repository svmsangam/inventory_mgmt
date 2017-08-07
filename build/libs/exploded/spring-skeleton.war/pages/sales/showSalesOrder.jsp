<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/pages/common/header.jsp" %>
<%@include file="/pages/common/businessownernavbar.jsp" %>
<%@include file="/pages/common/businessownersidebar.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Sales</li>

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
                <div class="panel-heading">Order No. #${sales.orderNo}</div>
                <div class="panel-body">
                    <div class="container-wrapper " id="DivIdToPrint">

                        <div class="row">
                            <div class="col-xs-12">
                                <div class="col-xs-6">
                                    <address>
                                        <span>Dates:</span>
                                        <strong>Order</strong>: ${sales.orderDate} &nbsp;
                                        <strong>Shipment</strong>: ${sales.shipmentDate} &nbsp;
                                        <strong>Delivery</strong>: ${sales.deliveryDate}<br>
                                        <br><strong>Shipment Channel</strong>: ${sales.shipmentChannel}<br>
                                        <br><strong>Delivery City</strong>: ${sales.deliveryCity}
                                        <br><strong>Customer Name</strong>: ${sales.customerName}
                                    </address>
                                </div>
                                <div class="col-xs-6">
                                    <input type="hidden" id="salesId" value="${sales.orderId}">

                                    <span style="color: blue;" class="pull-left">Track Index</span>
                                    <table class="table" style="border-bottom-color: 2px blue;">
                                        <thead class="thead-inverse">
                                        <tr>
                                            <th>Pending</th>
                                            <th style="color: #f2bf07;">Packed</th>
                                            <th style="color: #0612f1;">Shipped</th>
                                            <th style="color: #08cc06;">Delivered</th>
                                            <th style="color: red;">Cancel</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>

                                            <td>
                                                <c:if test="${sales.track eq 'PENDING'}">
                                                    <input type="radio" class="track" name="track" value="Pending"
                                                           checked="checked"/>

                                                </c:if>
                                                <c:if test="${sales.track ne 'PENDING'}">
                                                    <input type="radio" class="track" name="track" value="Pending"/>
                                                </c:if>
                                            </td>

                                            <td>
                                                <c:if test="${sales.track eq 'PACKED'}">
                                                    <input type="radio" class="track" name="track" value="Packed"
                                                           checked="checked"/>

                                                </c:if>
                                                <c:if test="${sales.track ne 'PACKED'}">
                                                    <input type="radio" class="track" name="track" value="Packed"/>
                                                </c:if>
                                            </td>

                                            <td>
                                                <c:if test="${sales.track eq 'SHIPPED'}">
                                                    <input type="radio" class="track" name="track" value="Shipped"
                                                           checked="checked"/>
                                                </c:if>
                                                <c:if test="${sales.track ne 'SHIPPED'}">
                                                    <input type="radio" class="track" name="track" value="Shipped"/>
                                                </c:if>
                                            </td>

                                            <td>
                                                <c:if test="${sales.track eq 'DELIVERED'}">
                                                    <input type="radio" class="track" name="track" value="Delivered"
                                                           checked="checked"/>
                                                </c:if>
                                                <c:if test="${sales.track ne 'DELIVERED'}">
                                                    <input type="radio" class="track" name="track" value="Delivered"/>
                                                </c:if>
                                            </td>

                                            <td>
                                                <c:if test="${sales.track eq 'CANCEL'}">
                                                    <input type="radio" class="track" name="track" value="Cancelled"
                                                           checked="checked"/>
                                                </c:if>
                                                <c:if test="${sales.track ne 'CANCEL'}">
                                                    <input type="radio" class="track" name="track" value="Cancelled"/>
                                                </c:if>
                                            </td>

                                        </tr>
                                        </tbody>
                                    </table>

                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <table class="table table-responsive table-striped table-bordered">
                                    <thead class="thead-inverse">
                                    <tr>
                                        <th>Item Name</th>
                                        <th>Rate</th>
                                        <th>Quantity</th>
                                        <th>Tax</th>
                                        <th>Amount</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="oldItem" varStatus="i" oldItems="${sales.oldItemDtoList}">
                                        <tr>
                                            <td>${oldItem.itemName}</td>
                                            <td>${oldItem.rate}</td>
                                            <td>${oldItem.quantity}</td>
                                            <td>${oldItem.tax}</td>
                                            <td>${(oldItem.rate * oldItem.quantity) + (oldItem.rate * oldItem.quantity) * (oldItem.tax/100)}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <td colspan="4"><label class="lable" style="float: right;">Total Amount</label>
                                        </td>
                                        <td style="text-align:center"><span id="quantity"
                                                                            class="quantity">${sales.totalCost}</span>
                                        </td>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
<script type="text/javascript">
    $(document).ready(function () {
        $("#print").click(function () {
            var invoiceNo = document.getElementById('invoiceNo').innerHTML;
            var divToPrint = document.getElementById('DivIdToPrint');

            var newWin = window.open('', 'Print-Window');

            newWin.document.open();

            newWin.document.write('<html><head><title>invoiceInfo ' + invoiceNo + '</title><link rel="stylesheet" type="text/css" href="../resources/landing/css/bootstrap.min.css"><link rel="stylesheet" type="text/css" href="../resources/landing/css/styles.css"></head><body onload="window.print()">' + divToPrint.innerHTML + '</body></html>');

            newWin.document.close();

            setTimeout(function () {
                newWin.close();
            }, 500);
        });
    });

</script>


<script type="text/javascript">
    $(document).ready(function () {
        var oldTrack;
        $('.track').each(function () {
            if ($(this).is(':checked')) {
                oldTrack = $(this).val();
            }
        });
        $(".track").click(function () {
            if (confirm("Are you sure to change the status from " + oldTrack + " to " + $(this).val() + " ?")) {
                oldTrack;
                return true;
            } else {
                return false;
            }
        });


        $(document).on('change', '.track', function () {
            var track = $(this).val();
            var orderId = $('#salesId').val();
            if (orderId == null) {
                return;
            }
            //oldTrack = track;
            console.log("track changed to " + track + " of order id having " + orderId);
            $.ajax({
                type: "GET",
                url: "changeTrackStatus",
                contentType: "application/json",
                data: {orderId: orderId, trackStatus: track},
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                    if (data.status == 'Success') {

                        oldTrack = track;
                        //location.reload(true);
                    } else {
                        alert("failed to change");
                        location.reload(true);
                    }
                }
            });

        });
    });

</script>