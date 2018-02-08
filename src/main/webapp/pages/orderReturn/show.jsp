<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/8/18
  Time: 3:42 PM
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
    <section class="invoice">
        <!-- title row -->
        <div class="row">
            <div class="col-xs-12">
                <h2 class="page-header">show Sales Return</h2>

            </div>
            <!-- /.col -->
        </div>
        <!-- info row -->
        <div class="row invoice-info">
            <div class="col-sm-4 invoice-col">
                <address>
                    <strong>Buyer Details</strong><br>
                    ${orderReturn.clientInfoName}<br>

                    <c:if test="${orderReturn.clientInfoCompanyName ne null and orderReturn.clientInfoCompanyName ne ''}">
                        ${orderReturn.clientInfoCompanyName}<br>
                    </c:if>
                </address>
            </div>
        </div>

        <div class="row invoice-info">
            <div class="col-sm-3 invoice-col">
                <b>Order Return Date: </b><fmt:formatDate pattern="MMM dd, yyyy" value="${orderReturn.returnDate}"/>
            </div>
        </div>
        <!-- /.row -->
        <div class="col-sm-12">&nbsp;</div>
        <!-- Table row -->
        <div class="row">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>SN</th>
                    <th>Item</th>
                    <th>Quantity</th>
                    <th>Rate</th>
                    <th>Discount(%)</th>
                    <th>Subtotal</th>
                </tr>
                </thead>
                <tbody>

                <c:set var="netTotal" value="${0}"></c:set>
                <c:forEach items="${orderReturnItemList}" var="orderItem" varStatus="i">
                    <c:set var="netTotal" value="${netTotal + orderItem.totalAmount}"></c:set>
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>${orderItem.itemName}</td>
                        <td>${orderItem.quantity}</td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderItem.rate}"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderItem.discount}"/></td>
                        <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderItem.totalAmount}"/></td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <div class="row">
            <!-- accepted payments column -->
            <div class="col-xs-4">

                <c:if test="${orderReturn.description ne null}">
                    <div class="panel panel-color panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Remarks</h3>
                        </div>
                        <div class="panel-body">
                            <p>${orderReturn.description}</p>
                        </div>
                    </div>
                </c:if>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">&nbsp;</div>
            <div class="col-lg-4">
                <table class="table">
                    <tr>
                        <th>Net Total</th>
                        <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${netTotal}"/></td>
                    </tr>

                    <tr>
                        <th>Tax (<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderReturn.tax}"/>%)</th>
                        <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${netTotal * orderReturn.tax / 100}"/></td>
                    </tr>

                    <tr>
                        <th>Grand Total:</th>
                        <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderReturn.totalAmount}"/></td>
                    </tr>
                </table>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

    </section>
    <!-- /.content -->
    <div class="clearfix"></div>
</div>
<!-- /.content-wrapper -->
<%@include file="/pages/parts/footer.jsp" %>

