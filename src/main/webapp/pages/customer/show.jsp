<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<style>
    .fancy-grid-cell-inner {
        cursor: default;
        font-size: small !important;
    }
</style>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

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
            <div class="col-xs-12">
                <div class="box box-info">

                    <div class="box-header">

                        <div class="row">
                            <div class="col-md-6"><h3 class="box-title">Customer Details</h3></div>


                            <div class="col-md-6">

                                <div class="box-tools">
                                    <a href="${pageContext.request.contextPath}/customer/add"
                                       class="btn btn-info btn-sm btn-flat pull-right"><span
                                            class="glyphicon glyphicon-plus-sign"></span> Add
                                    </a>

                                    <a href="${pageContext.request.contextPath}/customer/list"
                                       class="btn btn-primary btn-sm btn-flat pull-right margin-r-5"><span
                                            class="glyphicon glyphicon-list"></span> List
                                    </a>
                                </div>

                            </div>
                        </div>


                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-4">

                                <div class="well well-sm">
                                    <label>Name : </label><br>
                                    ${customer.name}
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="well well-sm">
                                    <label>Company Name : </label><br>
                                    ${customer.companyName}
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="well well-sm">
                                    <label>Account No : </label><br>
                                    ${customer.accountNo}
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4">

                                <div class="well well-sm">
                                    <label>Mobile No : </label><br>
                                    ${customer.mobileNumber}
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="well well-sm">
                                    <label>Contact No : </label><br>
                                    ${customer.contact}
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="well well-sm">
                                    <label>Email : </label><br>
                                    ${customer.email}
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-4">

                                <div class="well well-sm">
                                    <label>PAN No : </label><br>
                                    ${customer.pan}
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="well well-sm">
                                    <label>City : </label><br>
                                    ${customer.cityInfoDTO.cityName}
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="well well-sm">
                                    <label>Street : </label><br>
                                    ${customer.street}
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>

        <div class="row">
            <div class="col-xs-6">
                <div class="box box-info">
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div id="container"
                                     style="height: 400px; max-width: 600px; margin: 0 auto; border: 1px #9cc2cb"></div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>

            <div class="col-xs-6">
                <div class="box box-info">
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div id="container1"
                                     style="height: 400px; max-width: 600px; margin: 0 auto; border: 1px #9cc2cb"></div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>

        <div class="row">
            <div class="col-xs-12">
                <div class="box box-info">

                    <div class="box-header">

                        <div class="row">
                            <div class="col-md-12"><h3 class="box-title">Receivable Receipt List</h3></div>

                        </div>


                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="table-responsive">
                                    <table class="table datatable2 table-bordered table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th>Receipt No</th>
                                            <th>Fiscal Year</th>
                                            <th>Customer Name</th>
                                            <th>Total Amount</th>
                                            <th>Amount Recievable</th>
                                            <th>Receipt Date</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach items="${invoiceList}" var="invoice">

                                            <tr>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/invoice/${invoice.invoiceId}">#${invoice.invoiceNo}</a>
                                                </td>
                                                <td><c:if
                                                        test="${invoice.fiscalYearInfo ne null}">${invoice.fiscalYearInfo.title}</c:if></td>
                                                <td>${invoice.orderInfo.clientInfo.name}</td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      groupingUsed="true"
                                                                      value="${invoice.totalAmount}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      groupingUsed="true"
                                                                      value="${invoice.receivableAmount}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${invoice.invoiceDate}"/></td>
                                            </tr>

                                        </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>

        <div class="row">
            <div class="col-xs-12">
                <div class="box box-info">
                    <div class="box-header">
                        <div class="row">
                            <div class="col-md-12"><h3 class="box-title">Sales Order List</h3></div>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="table-responsive">
                                    <table class="table datatable2 table-bordered table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th>Order No</th>
                                            <th>Customer Name</th>
                                            <th>Total Cost</th>
                                            <th>Order Date</th>
                                            <th>Delivery Date</th>
                                            <th>Status</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach items="${orderList}" var="order">
                                            <tr>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/order/sale/${order.orderId}">#${order.orderNo}</a>
                                                </td>
                                                <td>${order.clientInfo.name}</td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      groupingUsed="true"
                                                                      value="${order.grandTotal}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${order.orderDate}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${order.deliveryDate}"/></td>
                                                <td>
                                                    <c:if test="${order.saleTrack eq 'PENDDING'}">
                                                        <span class="label label-primary">Pending</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'ACCEPTED'}">
                                                        <span class="label label-default">Accepted</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'PACKED'}">
                                                        <span class="label label-warning">Packed</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'SHIPPED'}">
                                                        <span class="label label-info">Shipped</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'DELIVERED'}">
                                                        <span class="label label-success">Delivered</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'CANCEL'}">
                                                        <span class="label label-danger">Canceled</span>
                                                    </c:if>

                                                </td>
                                            </tr>
                                        </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>

    </section>


</div>

<%@include file="/pages/parts/footer.jsp" %>

<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>

<script>
    var drAmount = 0;
    var crAmount = 0;

    <c:if test="${account ne null}">
    drAmount = <fmt:formatNumber type="number" maxFractionDigits="2"
                                                                      groupingUsed="false"
                                                                      value="${account.formattedCreditAmount - account.formattedDebitAmount}"/>;
    crAmount = ${account.formattedCreditAmount};
    </c:if>

    if (drAmount === undefined) {
        drAmount = 0;
    }

    if (drAmount === null) {
        drAmount = 0;
    }

    if (crAmount === undefined) {
        crAmount = 0;
    }

    if (crAmount === null) {
        crAmount = 0;
    }

    hichartPie1(crAmount, drAmount);
    hichartPie2(${crPercentage}, crAmount);

    function hichartPie1(saleAmount, dueAmount) {
        // Build the chart
        Highcharts.chart('container', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            exporting: {
                buttons: {
                    contextButton: {
                        menuItems: [{
                            textKey: 'printChart',
                            onclick: function () {
                                this.print();
                            }
                        },
                            {
                                separator: true
                            }, {
                                textKey: 'downloadPNG',
                                onclick: function () {
                                    this.exportChart();
                                }
                            }, {
                                textKey: 'downloadJPEG',
                                onclick: function () {
                                    this.exportChart({
                                        type: 'image/jpeg'
                                    });
                                }
                            }, {
                                textKey: 'downloadPDF',
                                onclick: function () {
                                    this.exportChart({
                                        type: 'application/pdf'
                                    });
                                }
                            }, {
                                textKey: 'downloadSVG',
                                onclick: function () {
                                    this.exportChart({
                                        type: 'image/svg+xml'
                                    });
                                }
                            }]
                    }
                }
            },
            title: {
                text: 'Paid and Receivable Ratio (' + crAmount + ')'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.y:.1f}</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: 'Amount',
                colorByPoint: true,
                data: [{
                    name: 'Paid',
                    y: saleAmount - dueAmount,
                    selected: true,
                    color: '#28a745'
                }, {
                    name: 'Receivable',
                    y: dueAmount,
                    color: '#dc3545'
                }]
            }]
        });
    }

    function hichartPie2(totalSaleAmount, clientSaleAmount) {
        // Build the chart
        Highcharts.chart('container1', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            exporting: {
                buttons: {
                    contextButton: {
                        menuItems: [{
                            textKey: 'printChart',
                            onclick: function () {
                                this.print();
                            }
                        },
                            {
                                separator: true
                            }, {
                                textKey: 'downloadPNG',
                                onclick: function () {
                                    this.exportChart();
                                }
                            }, {
                                textKey: 'downloadJPEG',
                                onclick: function () {
                                    this.exportChart({
                                        type: 'image/jpeg'
                                    });
                                }
                            }, {
                                textKey: 'downloadPDF',
                                onclick: function () {
                                    this.exportChart({
                                        type: 'application/pdf'
                                    });
                                }
                            }, {
                                textKey: 'downloadSVG',
                                onclick: function () {
                                    this.exportChart({
                                        type: 'image/svg+xml'
                                    });
                                }
                            }]
                    }
                }
            },
            title: {
                text: "Sales ratio with other customer"
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: 'Sale',
                colorByPoint: true,
                data: [{
                    name: 'Other Customer',
                    y: totalSaleAmount - clientSaleAmount,
                    selected: true,
                    color: '#327da8'
                }, {
                    name: 'This Customer',
                    y: clientSaleAmount,
                    color: '#dbbf32'
                }]
            }]
        });
    }
</script>