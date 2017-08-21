<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <li class="active">Invoice</li>

        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <div class="row" id="DivIdToPrint">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="col-md-offset-5">Invoice of <strong>${invoiceInfo.customerName}</strong>
                        <div class="pull-right">Invoice No. <strong id="invoiceno">#${invoiceInfo.invoiceNo}</strong>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="col-sm-4 pull-left">
                        <div class="well">
                            <h4>To</h4>
                            <p><c:if
                                    test="${invoiceInfo.customerName != null}"><strong>${invoiceInfo.customerName}</strong></c:if>
                            </p>
                            <p>phone: ${clientInfo.landline }</p>
                            <p>mobile: ${clientInfo.mobileNo }</p>
                            <p>address:${clientInfo.address }</p>
                        </div>
                    </div><!--/col-->

                    <div class="col-sm-4 pull-right text-right">
                        <div class="well">
                            <h4>Details</h4>
                            <p>Invoice Date <strong>${invoiceInfo.invoiceDate}</strong></p>
                            <p>Due Date <strong>${invoiceInfo.dueDate}</strong></p>
                            <p>Delivery Date <strong>${sales.deliveryDate}</strong></p>
                            <p>Delivery cityInfo <strong>${sales.deliveryCity}</strong></p>
                        </div>
                    </div><!--/col-->

                    <div class="row">
                        <div class="col-xs-6">
                            <address>
                                <strong>Shipment Channel:</strong><br>
                                ${sales.shipmentChannel}<br>
                                <strong>Shipment Date:</strong><br>
                                ${sales.shipmentDate}<br>
                            </address>
                        </div>
                        <%-- <div class="col-xs-6 text-right">
                            <address>
                                <strong>OrderInfo Date:</strong><br>
                                ${invoiceInfo.invoiceDate}<br><br>
                            </address>
                        </div>--%>
                    </div>

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

                        <c:forEach var="oldItem" oldItems="${oldItemList}">
                            <tr>
                                <td>${oldItem.itemName}</td>
                                <td>${oldItem.rate}</td>
                                <td>${oldItem.quantity}</td>
                                <td>${oldItem.tax}</td>
                                <td>${(oldItem.rate * oldItem.quantity) + (oldItem.rate * oldItem.quantity) * (oldItem.tax/100)}
                                    &nbsp; salesItem
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${fn:length(invoiceInfo.oldItemList) gt 0}">
                            <c:forEach var="oldItem" oldItems="${invoiceInfo.oldItemList}">
                                <tr>
                                    <td>${oldItem.itemName}</td>
                                    <td>${oldItem.rate}</td>
                                    <td>${oldItem.quantity}</td>
                                    <td>${oldItem.tax}</td>
                                    <td>${(oldItem.rate * oldItem.quantity) + (oldItem.rate * oldItem.quantity) * (oldItem.tax/100)}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="4"></td>
                            <td>
                                <label class="lable">Discount(%) </label>
                                <span id="total" class="total form-control">${invoiceInfo.discount}</span>
                            </td>
                        <tr>
                        <tr>
                            <td colspan="4"></td>
                            <td>
                                <label class="lable">Grand Total </label>
                                <span id="quantity" class="quantity form-control">${invoiceInfo.totalCost}</span>
                            </td>
                        <tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <button class="btn btn-primary pull-right" id="print"><i class="fa fa-print"></i> Print</button>
    <div id="idiv">
        <table class="table table-responsive table-striped table-bordered" id="invoiceInfo">
        </table>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
<script type="text/javascript">
    $(document).ready(function () {
        $("#print").click(function () {
            var invoiceNo = document.getElementById('invoiceno').innerHTML;
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