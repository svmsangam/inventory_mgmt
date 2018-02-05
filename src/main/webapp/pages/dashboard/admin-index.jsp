<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/13/17
  Time: 11:28 PM
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

        <c:if test="${empty error}">
            <div class="row">
                <div class="col-lg-3 col-xs-6">
                    <!-- small box -->
                    <div class="small-box bg-aqua">
                        <div class="inner">
                            <h3>${totalStock}</h3>

                            <p>Total Stock (units)</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-cube"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/product/list" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->
                <div class="col-lg-3 col-xs-6">
                    <!-- small box -->
                    <div class="small-box bg-green">
                        <div class="inner">
                            <h3><fmt:formatNumber type="number" maxFractionDigits="2" groupingUsed="true" value="${totalSale}"/></h3>

                            <p>Total Sales (Rs)</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-stats-bars"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/invoice/list" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->
                <div class="col-lg-3 col-xs-6">
                    <!-- small box -->
                    <div class="small-box bg-yellow">
                        <div class="inner">
                            <h3>${fn:length(storeList)}</h3>

                            <p>Total Store</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-bag"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/store/list" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->
                <div class="col-lg-3 col-xs-6">
                    <!-- small box -->
                    <div class="small-box bg-red">
                        <div class="inner">
                            <h3>${totalUser}</h3>

                            <p>Total Users</p>
                        </div>
                        <div class="icon">
                            <i class="ion ion-person-add"></i>
                        </div>
                        <a href="${pageContext.request.contextPath}/user/list" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                    </div>
                </div>
                <!-- ./col -->
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">This year's Monthly Sale Report of &nbsp; ${store}</h3>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                                    <%--<div class="btn-group">
                                        <button type="button" class="btn btn-box-tool dropdown-toggle" data-toggle="dropdown">
                                            <i class="fa fa-wrench"></i></button>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>--%>
                                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="row">
                                <div class="col-md-8">
                                    <p class="text-center">
                                            <%--  <strong>1 Jan, 2017 - 30 Dec, 2017</strong>--%>
                                    </p>

                                    <div class="chart">
                                        <!-- Sales Chart Canvas -->
                                        <canvas id="salesChart" style="height: 250px;"></canvas>
                                    </div>
                                    <!-- /.chart-responsive -->
                                </div>
                                <!-- /.col -->
                                <div class="col-md-4">
                                    <p class="text-center">
                                        <strong>Sales Order Status</strong>
                                    </p>

                                    <div class="progress-group">
                                        <span class="progress-text">Order Pending</span>
                                        <span class="progress-number"><b>${totalPendingSale}</b></span>

                                        <div class="progress sm">
                                            <div class="progress-bar progress-bar-primary" style="width: 80%"></div>
                                        </div>
                                    </div>

                                    <div class="progress-group">
                                        <span class="progress-text">Order Accepted</span>
                                        <span class="progress-number"><b>${totalAcceptedSale}</b></span>

                                        <div class="progress sm">
                                            <div class="progress-bar label-default" style="width: 80%"></div>
                                        </div>
                                    </div>

                                    <!-- /.progress-group -->
                                    <div class="progress-group">
                                        <span class="progress-text">Order Packed</span>
                                        <span class="progress-number"><b>${totalPackedSale}</b></span>

                                        <div class="progress sm">
                                            <div class="progress-bar progress-bar-yellow" style="width: 80%"></div>
                                        </div>
                                    </div>
                                    <!-- /.progress-group -->
                                    <div class="progress-group">
                                        <span class="progress-text">Order Shipped</span>
                                        <span class="progress-number"><b>${totalShipedSale}</b></span>

                                        <div class="progress sm">
                                            <div class="progress-bar progress-bar-aqua" style="width: 80%"></div>
                                        </div>
                                    </div>
                                    <!-- /.progress-group -->
                                    <div class="progress-group">
                                        <span class="progress-text">Order Cancelled</span>
                                        <span class="progress-number"><b>${totalCanceledSale}</b></span>

                                        <div class="progress sm">
                                            <div class="progress-bar progress-bar-red" style="width: 80%"></div>
                                        </div>
                                    </div>
                                    <!-- /.progress-group -->
                                </div>
                                <!-- /.col -->
                            </div>
                            <!-- /.row -->
                        </div>
                        <!-- ./box-body -->
                        <div class="box-footer">
                            <div class="row">
                                <div class="col-sm-3 col-xs-6">
                                    <div class="description-block border-right">
                                            <%-- <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>--%>
                                        <h5 class="description-header">$<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${totalPayment}"/></h5>
                                        <span class="description-text">TOTAL COLLECTION</span>
                                    </div>
                                    <!-- /.description-block -->
                                </div>
                                <!-- /.col -->
                                <div class="col-sm-3 col-xs-6">
                                    <div class="description-block border-right">
                                            <%-- <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>--%>
                                        <h5 class="description-header">$<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${totalReceivable}"/></h5>
                                        <span class="description-text">TOTAL RECIEVABLE</span>
                                    </div>
                                    <!-- /.description-block -->
                                </div>
                                <!-- /.col -->
                                <div class="col-sm-3 col-xs-6">
                                    <div class="description-block border-right">
                                            <%--<span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>--%>
                                        <h5 class="description-header">$<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${toDayTotalSale}"/></h5>
                                        <span class="description-text">TODAYS SALE</span>
                                    </div>
                                    <!-- /.description-block -->
                                </div>
                                <!-- /.col -->
                                <div class="col-sm-3 col-xs-6">
                                    <div class="description-block">
                                            <%--<span class="description-percentage text-red"><i class="fa fa-caret-down"></i> 18%</span>--%>
                                        <h5 class="description-header">$<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${toDayTotalPayment}"/></h5>
                                        <span class="description-text">TODAYS COLLECTION</span>
                                    </div>
                                    <!-- /.description-block -->
                                </div>
                            </div>
                            <!-- /.row -->
                        </div>
                        <!-- /.box-footer -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

            <div class="row">
                <div class="col-md-8">
                    <!-- TABLE: LATEST ORDERS -->
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">Top Sales Orders</h3>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="table-responsive">
                                <table class="table no-margin">
                                    <thead>
                                    <tr>
                                        <th>Order ID</th>
                                        <th>Order Date</th>
                                        <th>Delivery Date</th>
                                        <th>Amount</th>
                                        <th>Status</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:if test="${fn:length(orderList) gt 0}">
                                        <c:forEach items="${orderList}" var="order">

                                            <tr>
                                                <td><a href="${pageContext.request.contextPath}/order/sale/${order.orderId}">#${order.orderNo}</a></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy" value="${order.orderDate}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy" value="${order.deliveryDate}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.grandTotal}"/></td>
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

                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer clearfix">
                            <a href="${pageContext.request.contextPath}/order/sale/add" class="btn btn-sm btn-primary btn-flat pull-left">Place New Order</a>
                            <a href="${pageContext.request.contextPath}/order/sale/list" class="btn btn-sm btn-default btn-flat pull-right">View All Orders</a>
                        </div>
                        <!-- /.box-footer -->
                    </div>
                </div>

                <div class="col-md-4">
                    <!-- PRODUCT LIST -->
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Top Receivable Invoice</h3>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                </button>
                                <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="table-responsive">
                                <table class="table no-margin">
                                    <thead>
                                    <tr>
                                        <th>Invoice No</th>
                                        <th>Amount Recievable</th>
                                        <th>Invoice Date</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${invoiceList}" var="invoice">

                                        <tr>
                                            <td><a href="${pageContext.request.contextPath}/invoice/${invoice.invoiceId}">#${invoice.invoiceNo}</a></td>
                                            <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.receivableAmount}"/></td>
                                            <td><fmt:formatDate pattern="MMM dd, yyyy" value="${invoice.invoiceDate}"/></td>
                                        </tr>

                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer text-center">
                            <a href="${pageContext.request.contextPath}/invoice/list" class="uppercase">View All Invoices</a>
                        </div>
                        <!-- /.box-footer -->
                    </div>
                    <!-- /.box -->
                </div>
            </div>
        </c:if>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@include file="/pages/parts/footer.jsp" %>
<script>
    $(function () {

        'use strict';

        /* ChartJS
         * -------
         * Here we will create a few charts using ChartJS
         */

        // -----------------------
        // - MONTHLY SALES CHART -
        // -----------------------

        // Get context with jQuery - using jQuery's .get() method.
        var salesChartCanvas = $('#salesChart').get(0).getContext('2d');
        // This will get the first returned node in the jQuery collection.
        var salesChart       = new Chart(salesChartCanvas);

        var salesChartData = {
            labels  : ['January', 'February', 'March', 'April', 'May', 'June', 'July' , 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'],
            datasets: [
                /* {
                 label               : 'Electronics',
                 fillColor           : 'rgb(210, 214, 222)',
                 strokeColor         : 'rgb(210, 214, 222)',
                 pointColor          : 'rgb(210, 214, 222)',
                 pointStrokeColor    : '#c1c7d1',
                 pointHighlightFill  : '#fff',
                 pointHighlightStroke: 'rgb(220,220,220)',
                 data                : [65, 59, 80, 81, 56, 55, 40 , 50 , 30 , 20, 10, 60]
                 },*/
                {
                    label               : 'Digital Goods',
                    fillColor           : 'rgba(60,141,188,0.9)',
                    strokeColor         : 'rgba(60,141,188,0.8)',
                    pointColor          : '#3b8bba',
                    pointStrokeColor    : 'rgba(60,141,188,1)',
                    pointHighlightFill  : '#fff',
                    pointHighlightStroke: 'rgba(60,141,188,1)',
                    data                : ${cartSaleDate}
                }
            ]
        };

        var salesChartOptions = {
            // Boolean - If we should show the scale at all
            showScale               : true,
            // Boolean - Whether grid lines are shown across the chart
            scaleShowGridLines      : false,
            // String - Colour of the grid lines
            scaleGridLineColor      : 'rgba(0,0,0,.05)',
            // Number - Width of the grid lines
            scaleGridLineWidth      : 1,
            // Boolean - Whether to show horizontal lines (except X axis)
            scaleShowHorizontalLines: true,
            // Boolean - Whether to show vertical lines (except Y axis)
            scaleShowVerticalLines  : true,
            // Boolean - Whether the line is curved between points
            bezierCurve             : true,
            // Number - Tension of the bezier curve between points
            bezierCurveTension      : 0.3,
            // Boolean - Whether to show a dot for each point
            pointDot                : true,
            // Number - Radius of each point dot in pixels
            pointDotRadius          : 4,
            // Number - Pixel width of point dot stroke
            pointDotStrokeWidth     : 1,
            // Number - amount extra to add to the radius to cater for hit detection outside the drawn point
            pointHitDetectionRadius : 20,
            // Boolean - Whether to show a stroke for datasets
            datasetStroke           : true,
            // Number - Pixel width of dataset stroke
            datasetStrokeWidth      : 2,
            // Boolean - Whether to fill the dataset with a color
            datasetFill             : true,
            // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
            maintainAspectRatio     : true,
            // Boolean - whether to make the chart responsive to window resizing
            responsive              : true
        };

        // Create the line chart
        salesChart.Line(salesChartData, salesChartOptions);

        // ---------------------------
        // - END MONTHLY SALES CHART -
        // ---------------------------


    });

</script>
