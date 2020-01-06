<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

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
            <div class="col-xs-12">
                <div class="box box-info">

                    <div class="box-header">

                        <div class="row">
                            <div class="col-md-12"><h3 class="box-title">Report</h3></div>

                        </div>


                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Sale and Receivable Chart</h3>
                                </div>
                                <div class="well well-sm">
                                    <canvas id="pieChart1" style="height:250px"></canvas>
                                    <div class="row">
                                        <div class="col-xs-6"><span class="label-success label">&nbsp;&nbsp;</span>&nbsp;Total Sale Amount </div>
                                        <div class="col-xs-6"></div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-6"><span class="label-danger label">&nbsp;&nbsp;</span>&nbsp;Total Due Amount </div>
                                        <div class="col-xs-6"></div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Overall Sales Percentage Chart</h3>
                                </div>
                                <div class="well well-sm text-center">
                                    <input type="text" class="knob pull-right" value="${crPercentage}" data-width="267" data-height="267" data-fgColor="#932ab6" data-readonly="true">

                                    <div class="knob-label">Sales Percentage = "${crPercentage}%"</div>
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
                                                <td><a href="${pageContext.request.contextPath}/invoice/${invoice.invoiceId}">#${invoice.invoiceNo}</a></td>
                                                <td><c:if test="${invoice.fiscalYearInfo ne null}">${invoice.fiscalYearInfo.title}</c:if></td>
                                                <td>${invoice.orderInfo.clientInfo.name}</td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.totalAmount}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.receivableAmount}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy" value="${invoice.invoiceDate}"/></td>
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
                            <div class="col-md-12"><h3 class="box-title">Order List</h3></div>

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

<script>
    $(document).ready(function () {
        var drAmount = 0;
        var crAmount = 0;

        <c:if test="${account ne null}">
            drAmount = ${account.formattedDebitAmount};
            crAmount = ${account.formattedCreditAmount};
        </c:if>

        if (drAmount === undefined){
            drAmount = 0;
        }

        if (drAmount === null){
            drAmount = 0;
        }

        if (crAmount === undefined){
            crAmount = 0;
        }

        if (crAmount === null){
            crAmount = 0;
        }

        initPieChart1(drAmount , crAmount);
        initPieChart2();
    });

    function initPieChart1(drAmount , crAmount) {
        var pieChartCanvas1 = $('#pieChart1').get(0).getContext('2d');
        var pieChart1       = new Chart(pieChartCanvas1);
        var PieData1        = [
            {
                value    : drAmount,
                color    : '#f56954',
                highlight: '#f56954',
                label    : 'Due Amount'
            },
            {
                value    : crAmount,
                color    : '#00a65a',
                highlight: '#00a65a',
                label    : 'Sale Amount'
            }
        ];
        var pieOptions1     = {
            //Boolean - Whether we should show a stroke on each segment
            segmentShowStroke    : true,
            //String - The colour of each segment stroke
            segmentStrokeColor   : '#fff',
            //Number - The width of each segment stroke
            segmentStrokeWidth   : 2,
            //Number - The percentage of the chart that we cut out of the middle
            percentageInnerCutout: 50, // This is 0 for Pie charts
            //Number - Amount of animation steps
            animationSteps       : 100,
            //String - Animation easing effect
            animationEasing      : 'easeOutBounce',
            //Boolean - Whether we animate the rotation of the Doughnut
            animateRotate        : true,
            //Boolean - Whether we animate scaling the Doughnut from the centre
            animateScale         : false,
            //Boolean - whether to make the chart responsive to window resizing
            responsive           : true,
            // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
            maintainAspectRatio  : true,
            //String - A legend template
            legendTemplate       : 'Hello'
        };

        pieChart1.Doughnut(PieData1, pieOptions1)
    }

    function initPieChart2() {
        $(".knob").knob({
            change : function (value) {
             //console.log("change : " + value);
             },
             release : function (value) {
             console.log("release : " + value);
             },
             cancel : function () {
             console.log("cancel : " + this.value);
             },
            draw: function () {

                // "tron" case
                if (this.$.data('skin') == 'tron') {

                    var a = this.angle(this.cv)  // Angle
                        , sa = this.startAngle          // Previous start angle
                        , sat = this.startAngle         // Start angle
                        , ea                            // Previous end angle
                        , eat = sat + a                 // End angle
                        , r = true;

                    this.g.lineWidth = this.lineWidth;

                    this.o.cursor
                    && (sat = eat - 0.3)
                    && (eat = eat + 0.3);

                    if (this.o.displayPrevious) {
                        ea = this.startAngle + this.angle(this.value);
                        this.o.cursor
                        && (sa = ea - 0.3)
                        && (ea = ea + 0.3);
                        this.g.beginPath();
                        this.g.strokeStyle = this.previousColor;
                        this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, sa, ea, false);
                        this.g.stroke();
                    }

                    this.g.beginPath();
                    this.g.strokeStyle = r ? this.o.fgColor : this.fgColor;
                    this.g.arc(this.xy, this.xy, this.radius - this.lineWidth, sat, eat, false);
                    this.g.stroke();

                    this.g.lineWidth = 2;
                    this.g.beginPath();
                    this.g.strokeStyle = this.o.fgColor;
                    this.g.arc(this.xy, this.xy, this.radius - this.lineWidth + 1 + this.lineWidth * 2 / 3, 0, 2 * Math.PI, false);
                    this.g.stroke();

                    return false;
                }
            }
        });
        /* END JQUERY KNOB */

        //INITIALIZE SPARKLINE CHARTS
        $(".sparkline").each(function () {
            var $this = $(this);
            $this.sparkline('html', $this.data());
        });
    }
</script>