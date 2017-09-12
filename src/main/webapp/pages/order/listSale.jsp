<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <h3 class="box-title">Order List</h3>
                        <div class="box-tools">
                            <a href="" class="btn btn-info btn-sm btn-flat pull-right"><span class="glyphicon glyphicon-plus-sign"></span> New Order
                            </a>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <table id="table2" class="table table-bordered table-hover table-striped">
                            <thead>
                            <tr>
                                <th>Order No</th>
                                <th>Customer Name</th>
                                <th>Total Cost</th>
                                <th>Order Date</th>
                                <th>Delivery Date</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${orderList}" var="order">

                                <tr>
                                    <td><a href="${pageContext.request.contextPath}/order/sale/${order.orderId}">#${order.orderNo}</a></td>
                                    <td>${order.clientInfo.name}</td>
                                    <td>${order.grandTotal}</td>
                                    <td>${order.deliveryDate}</td>
                                    <td>${order.deliveryDate}</td>
                                    <td><span class="label label-success">${order.saleTrack}</span></td>
                                    <td>

                                    </td>
                                </tr>

                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="/pages/parts/footer.jsp" %>
