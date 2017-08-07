<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp"%>
	
<script src="${pageContext.request.contextPath}/resources/js/chart.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/webapp/resources/css/jquery.dataTables.min.css">
<link href="${pageContext.request.contextPath}/resources/css/chartist.min.css" rel="stylesheet">

<style>
.ct-label {
    color: rgb(0, 0, 255);
    font-size: 1rem !important;
    line-height: 1;
}
.ct-series-b .ct-bar, .ct-series-b .ct-line, .ct-series-b .ct-point, .ct-series-b .ct-slice-donut {
    stroke: #0b6621 !important;
}
.ct-series-a .ct-bar, .ct-series-a .ct-line, .ct-series-a .ct-point, .ct-series-a .ct-slice-donut {
    stroke: #47ad60 !important;
}

</style>


	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
				<li class="active">Dashboard</li>
			</ol>
		</div><!--/.row-->
		
		<c:if test="${not empty message}">
			<div class="alert alert-info alert-dismissable">
    		<a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
	   			<strong>${message}</strong>
			</div>
		</c:if>
		
		<div class="row">
			<div class="col-xs-12 col-md-6 col-lg-3">
				<div class="panel panel-teal panel-widget">
					<div class="row no-padding">
						<div class="col-sm-3 col-lg-5 widget-left">
							<svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg>
						</div>
						<div class="col-sm-9 col-lg-7 widget-right">
							<div class="text-normal">Total Users</div>
							<div class="large">${fn:length(userList)}</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-md-6 col-lg-3">
				<div class="panel panel-blue panel-widget ">
					<div class="row no-padding">
						<div class="col-sm-3 col-lg-5 widget-left">
							<svg class="glyph stroked bag"><use xlink:href="#stroked-bag"></use></svg>
						</div>
						<div class="col-sm-9 col-lg-7 widget-right">
							<div class="text-normal">Total Stock</div>
							<div class="large">${totalStock}</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 col-md-6 col-lg-3">
				<div class="panel panel-orange panel-widget">
					<div class="row no-padding">
						<div class="col-sm-3 col-lg-5 widget-left">
							<svg class="glyph stroked  clipboard with paper"><use xlink:href="#stroked-clipboard-with-paper"></use></svg>
						</div>
						<div class="col-sm-9 col-lg-7 widget-right">
							<div class="text-normal">Total Purchase</div>
							<div class="large"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${totalPurchase}" /></p></div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="col-xs-12 col-md-6 col-lg-3">
				<div class="panel panel-red panel-widget">
					<div class="row no-padding">
						<div class="col-sm-3 col-lg-5 widget-left">
							<svg class="glyph stroked app-window-with-content"><use xlink:href="#stroked-app-window-with-content"></use></svg>
						</div>
						<div class="col-sm-9 col-lg-7 widget-right">
							<div class="text-normal">Total Sale</div>
							<div class="large"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${totalSale}" /></div>
						</div>
					</div>
				</div>
			</div>
		</div><!--/.row-->
		
		<div class="col-lg-9">
			<div class="panel panel-primary">
				<div class="panel-heading">
				Chart Index
					<div class="pull-right">
						
							<span style="margin-left: 10px; padding-right: 10px; padding-top: 10px;">purchase :</span>  <img src="${pageContext.request.contextPath}/resources/landing/img/purshaseIndex.PNG" alt="sale" height="42" width="42"/>
						
							<span style="margin-left: 10px; padding-right: 10px; padding-top: 5px;">sale :</span> <img src="${pageContext.request.contextPath}/resources/landing/img/salesIndex.PNG" alt="purchase" height="42" width="42" />
		
					</div>
				</div>
				<div class="panel-body">
					<div class="canvas-wrapper">
						<canvas class="main-chart" id="line-chart" height="253" width="600"></canvas>
						</div>
					</div>
				</div>
			</div>

			<div class="col-lg-3">
				<div class="panel panel-info">
					<div class="panel-heading dark-overlay"><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"></use></svg>Sales Status</div>
					<div class="panel-body">
						<p>Sales OrderRequest Pending: <span class="badge" style="background-color: teal;">${salesOrderPending}</span><span class="badge" style="background-color: teal; float: right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${salesOrderPendingAmount}" /></span></p>
						<p>Sales OrderRequest Accepted: <span class="badge" style="background-color: teal;">${salesOrderAccepted}</span><span class="badge" style="background-color: teal;float: right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${salesOrderAcceptedAmount}" /></span></p>
						<p>Sales OrderRequest Packed: <span class="badge" style="background-color: teal;">${salesOrderPacked}</span><span class="badge" style="background-color: teal;float: right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${salesOrderPackedAmount}" /></span></p>
						<p>Sales OrderRequest Shipped: <span class="badge" style="background-color: teal;">${salesOrderShipped}</span><span class="badge" style="background-color: teal;float: right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${salesOrderShippedAmount}" /></span></p>
						<p>Sales OrderRequest Delivered: <span class="badge" style="background-color: teal;">${salesOrderDelivered}</span><span class="badge" style="background-color: teal;float: right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${salesOrderDeliveredAmount}" /></span></p>
						<p>Sales OrderRequest cancelled: <span class="badge" style="background-color: teal;">${salesOrderCancelled}</span><span class="badge" style="background-color: teal;float: right"><fmt:formatNumber type = "number" maxFractionDigits = "3" value = "${salesOrderCancelledAmount}" /></span></p>
					</div>
				</div>
			</div>
		
		<div class="col-lg-3">
				<div class="panel panel-info">
					<div class="panel-heading dark-overlay"><svg class="glyph stroked clipboard with paper"><use xlink:href="#stroked-clipboard-with-paper"/></svg>Purchase Status</div>
					<div class="panel-body">
						<p>Purchase OrderRequest Issued: <span class="badge" style="background-color: teal;">${purchaseOrderIssued}</span></p>
						<p>Purchase OrderRequest Received: <span class="badge" style="background-color: teal;">${purchaseOrderReceived}</span></p>
					</div>
				</div>
			</div>
	

		
<%@include file="../common/footer.jsp"%>
	<script type="text/javascript">
	
	
	var saleAmount =  ${chartDataofsale};
	var purchaseAmount =  ${chartDataofpurchase};
	//console.log("sale : " + saleAmount + " ;;; " + saleAmount[2]);	
	var lineChartData = {
			labels : ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
			datasets : [
				{
					label: "purchase",
					fillColor : "rgba(253, 239, 227, 0.63)",
					strokeColor : "rgba(232, 89, 89, 0.64)",
					pointColor : "rgba(228, 12, 23, 0.94)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(220,220,220,1)",
					data : purchaseAmount
				},
				{
					label: "Sale",
					fillColor : "rgba(48, 164, 255, 0.2)",
					strokeColor : "rgba(48, 164, 255, 1)",
					pointColor : "rgba(48, 164, 255, 1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(48, 164, 255, 1)",
					data : saleAmount
				}
			]

		};
		

window.onload = function(){
	var chart1 = document.getElementById("line-chart").getContext("2d");
	window.myLine = new Chart(chart1).Line(lineChartData, {
		responsive: true
	});

	
};
	
	</script>