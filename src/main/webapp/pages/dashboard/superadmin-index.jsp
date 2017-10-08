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

        <div class="row">
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-aqua">
                    <div class="inner">
                        <h3>150</h3>

                        <p>Total Stock</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-cube"></i>
                    </div>
                    <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-green">
                    <div class="inner">
                        <h3>530000</h3>

                        <p>Total Sales</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-stats-bars"></i>
                    </div>
                    <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-yellow">
                    <div class="inner">
                        <h3>440000</h3>

                        <p>Total Purchase</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-bag"></i>
                    </div>
                    <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
            <div class="col-lg-3 col-xs-6">
                <!-- small box -->
                <div class="small-box bg-red">
                    <div class="inner">
                        <h3>65</h3>

                        <p>Total Users</p>
                    </div>
                    <div class="icon">
                        <i class="ion ion-person-add"></i>
                    </div>
                    <a href="#" class="small-box-footer">More info <i class="fa fa-arrow-circle-right"></i></a>
                </div>
            </div>
            <!-- ./col -->
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="box">
                    <div class="box-header with-border">
                        <h3 class="box-title">Monthly Recap Report</h3>

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
                                    <strong>Sales: 1 Jan, 2017 - 30 Dec, 2017</strong>
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
                                    <span class="progress-number"><b>160</b></span>

                                    <div class="progress sm">
                                        <div class="progress-bar progress-bar-primary" style="width: 80%"></div>
                                    </div>
                                </div>

                                <div class="progress-group">
                                    <span class="progress-text">Order Accepted</span>
                                    <span class="progress-number"><b>160</b></span>

                                    <div class="progress sm">
                                        <div class="progress-bar label-default" style="width: 80%"></div>
                                    </div>
                                </div>

                                <!-- /.progress-group -->
                                <div class="progress-group">
                                    <span class="progress-text">Order Packed</span>
                                    <span class="progress-number"><b>310</b></span>

                                    <div class="progress sm">
                                        <div class="progress-bar progress-bar-yellow" style="width: 80%"></div>
                                    </div>
                                </div>
                                <!-- /.progress-group -->
                                <div class="progress-group">
                                    <span class="progress-text">Order Shipped</span>
                                    <span class="progress-number"><b>480</b></span>

                                    <div class="progress sm">
                                        <div class="progress-bar progress-bar-aqua" style="width: 80%"></div>
                                    </div>
                                </div>
                                <!-- /.progress-group -->
                                <div class="progress-group">
                                    <span class="progress-text">Order Cancelled</span>
                                    <span class="progress-number"><b>250</b></span>

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
                                    <h5 class="description-header">$35,210.43</h5>
                                    <span class="description-text">TOTAL COLLECTION</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-3 col-xs-6">
                                <div class="description-block border-right">
                                   <%-- <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>--%>
                                    <h5 class="description-header">$10,390.90</h5>
                                    <span class="description-text">TOTAL RECIEVABLE</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-3 col-xs-6">
                                <div class="description-block border-right">
                                    <%--<span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>--%>
                                    <h5 class="description-header">$24,813.53</h5>
                                    <span class="description-text">TODAYS SALE</span>
                                </div>
                                <!-- /.description-block -->
                            </div>
                            <!-- /.col -->
                            <div class="col-sm-3 col-xs-6">
                                <div class="description-block">
                                    <%--<span class="description-percentage text-red"><i class="fa fa-caret-down"></i> 18%</span>--%>
                                    <h5 class="description-header">1200</h5>
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
                    <h3 class="box-title">Latest Orders</h3>

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
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><a href="#">OR9842</a></td>
                                <td>12 sept,2017</td>
                                <td>15 sept,2017</td>
                                <td><span class="label label-success">Shipped</span></td>
                            </tr>
                            <tr>
                                <td><a href="#">OR1848</a></td>
                                <td>12 sept,2017</td>
                                <td>15 sept,2017</td>
                                <td><span class="label label-warning">Pending</span></td>
                            </tr>
                            <tr>
                                <td><a href="#">OR7429</a></td>
                                <td>12 sept,2017</td>
                                <td>15 sept,2017</td>
                                <td><span class="label label-danger">Delivered</span></td>
                            </tr>
                            <tr>
                                <td><a href="#">OR7429</a></td>
                                <td>12 sept,2017</td>
                                <td>15 sept,2017</td>
                                <td><span class="label label-info">Processing</span></td>
                            </tr>
                            <tr>
                                <td><a href="#">OR1848</a></td>
                                <td>12 sept,2017</td>
                                <td>15 sept,2017</td>
                                <td><span class="label label-warning">Pending</span></td>
                            </tr>
                            <tr>
                                <td><a href="#">OR7429</a></td>
                                <td>12 sept,2017</td>
                                <td>15 sept,2017</td>
                                <td><span class="label label-danger">Delivered</span></td>
                            </tr>
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
                    <h3 class="box-title">Recently Added Products</h3>

                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                        </button>
                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                    </div>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <ul class="products-list product-list-in-box">
                        <li class="item">
                            <div class="product-img">
                                <img src="/resources/img/default-50x50.gif" alt="Product Image">
                            </div>
                            <div class="product-info">
                                <a href="javascript:void(0)" class="product-title">Samsung TV
                                    <span class="label label-warning pull-right">$1800</span></a>
                                <span class="product-description">
                          Samsung 32" 1080p 60Hz LED Smart HDTV.
                        </span>
                            </div>
                        </li>
                        <!-- /.item -->
                        <li class="item">
                            <div class="product-img">
                                <img src="/resources/img/default-50x50.gif" alt="Product Image">
                            </div>
                            <div class="product-info">
                                <a href="javascript:void(0)" class="product-title">Bicycle
                                    <span class="label label-info pull-right">$700</span></a>
                                <span class="product-description">
                          26" Mongoose Dolomite Men's 7-speed, Navy Blue.
                        </span>
                            </div>
                        </li>
                        <!-- /.item -->
                        <li class="item">
                            <div class="product-img">
                                <img src="/resources/img/default-50x50.gif" alt="Product Image">
                            </div>
                            <div class="product-info">
                                <a href="javascript:void(0)" class="product-title">Xbox One <span
                                        class="label label-danger pull-right">$350</span></a>
                                <span class="product-description">
                          Xbox One Console Bundle with Halo Master Chief Collection.
                        </span>
                            </div>
                        </li>
                        <!-- /.item -->
                        <li class="item">
                            <div class="product-img">
                                <img src="/resources/img/default-50x50.gif" alt="Product Image">
                            </div>
                            <div class="product-info">
                                <a href="javascript:void(0)" class="product-title">PlayStation 4
                                    <span class="label label-success pull-right">$399</span></a>
                                <span class="product-description">
                          PlayStation 4 500GB Console (PS4)
                        </span>
                            </div>
                        </li>
                        <!-- /.item -->
                    </ul>
                </div>
                <!-- /.box-body -->
                <div class="box-footer text-center">
                    <a href="${pageContext.request.contextPath}product/list" class="uppercase">View All Products</a>
                </div>
                <!-- /.box-footer -->
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
                {
                    label               : 'Electronics',
                    fillColor           : 'rgb(210, 214, 222)',
                    strokeColor         : 'rgb(210, 214, 222)',
                    pointColor          : 'rgb(210, 214, 222)',
                    pointStrokeColor    : '#c1c7d1',
                    pointHighlightFill  : '#fff',
                    pointHighlightStroke: 'rgb(220,220,220)',
                    data                : [65, 59, 80, 81, 56, 55, 40 , 50 , 30 , 20, 10, 60]
                },
                {
                    label               : 'Digital Goods',
                    fillColor           : 'rgba(60,141,188,0.9)',
                    strokeColor         : 'rgba(60,141,188,0.8)',
                    pointColor          : '#3b8bba',
                    pointStrokeColor    : 'rgba(60,141,188,1)',
                    pointHighlightFill  : '#fff',
                    pointHighlightStroke: 'rgba(60,141,188,1)',
                    data                : [28, 48, 40, 19, 86, 27, 90 , 20 , 30 , 10 , 5 , 30]
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
