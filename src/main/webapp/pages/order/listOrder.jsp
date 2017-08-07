<%-- 
  - Author(s):Uttam
 - Description: list sales order view
  
  --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp" %>
<script type="text/javascript"
        src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="../resources/landing/js/select2.full.js"></script>
<link href="../resources/css/select2.css" rel="stylesheet">

<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Icons</li>
        </ol>
    </div>
    <!--/.row-->

    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Sales order</h1>
        </div>
    </div>
    <!--/.row-->

    <div class="container-wrapper">
        <a href="${pageContext.request.contextPath}/addSalesOrder"
           style="float: left;"">
        <button class="btn btn-primary btn-md">Create
            New Order
        </button>
        </a>
        <div class="container">
            <table
                    class="table table-responsive table-sm table-striped table-bordered table-hover">
                <thead class="thead-inverse">
                <tr>
                    <th>Order #</th>
                    <th>productInfo name</th>
                    <th>Order date</th>
                    <th>Expected Delivery Date</th>
                    <th>Expected Shipment Date</th>
                    <th>Customer Name</th>
                    <th>Sales Order Person</th>
                    <th>Delivery Person</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="salesOrder" items="${ordersList}">
                    <tr>
                        <td>${salesOrder.orderNo}</td>
                        <td>${salesOrder.productName}</td>
                        <td>${salesOrder.orderDate}</td>
                        <td>${salesOrder.expectedDeliveryDate}</td>
                        <td>${salesOrder.expectedShipmentDate}</td>
                        <td>${salesOrder.customerName}</td>
                        <td>${salesOrder.salesOrderPerson}</td>
                        <td>${salesOrder.deliveryPerson}</td>

                        <td><a
                                href="${pageContext.request.contextPath}/editSalesOrder?salesOrderId=${salesOrder.id}"
                                class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
                            Edit </a> <a
                                href="${pageContext.request.contextPath}/deleteSalesOrder?salesOrderId=${salesOrder.id}"
                                class="btn btn-danger btn-xs"> Delete </a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <%-- <!--/.row-->
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Add and View oldItems List</h1>
        </div>
    </div>
    <!--/.row-->

    <div class="container-wrapper">
        <a href="${pageContext.request.contextPath}/addItem"
            style="float: left; margin-bottom: 8px; margin-left: 18px;"><button
                class="btn btn-primary btn-md">Add New OldItem</button></a>

        <div class="container">
            <table class="table table-responsive table-striped table-bordered">
                <thead class="thead-inverse">
                    <tr>
                        <th>itemName</th>
                        <th>categoryType</th>
                        <th>rate</th>
                        <th>quantity</th>
                        <th>tax</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="oldItem" oldItems="${oldItemList}">
                        <tr>

                            <td>${oldItem.itemName}</td>
                            <td>${oldItem.categoryType}</td>
                            <td>${oldItem.rate}</td>
                            <td>${oldItem.quantity}</td>
                            <td>${oldItem.tax}</td>

                            <td><a
                                href="${pageContext.request.contextPath}/editItem?itemId=${oldItem.id}"
                                class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
                                    Edit </a> <a
                                href="${pageContext.request.contextPath}/deleteItem?itemId=${oldItem.id}"
                                class="btn btn-danger btn-xs"> Delete </a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div> --%>

    <!-- addoldItemstems  -->
		
		