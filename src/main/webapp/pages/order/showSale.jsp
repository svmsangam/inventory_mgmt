<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/29/17
  Time: 10:43 PM
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
                <h2 class="page-header">
                    <p class="text-center">Order No. <b>#${orderNo}</b></p>
                </h2>
            </div>
            <!-- /.col -->
        </div>
        <!-- info row -->
        <div class="row invoice-info">
            <div class="col-sm-4 invoice-col">
                <b>Order Date: </b><p>sample</p><br>
                <b>Delivery Date: </b><p>sample</p><br>
            </div>
            <div class="col-sm-4 invoice-col">
                <strong>Status</strong>
                <div class="form-group">
                    <label class="bg-orange-active color-palette">
                        <input type="radio" name="r1" checked> Pending
                    </label>
                    <label class="bg-aqua-active color-palette">
                        <input type="radio" name="r1" checked> Packed
                    </label>
                    <label class="bg-red-active color-palette">
                        <input type="radio" name="r1"> Delivered
                    </label>
                </div>
            </div>
            <div class="col-sm-2 invoice-col">
                <address>
                    <strong>Billing Address</strong><br>
                    795 Folsom Ave, Suite 600<br>
                    San Francisco, CA 94107<br>
                    Phone: (804) 123-5432<br>
                </address>
            </div>
            <div class="col-sm-2 invoice-col">
                <address>
                    <strong>Shipping Address</strong><br>
                    795 Folsom Ave, Suite 600<br>
                    San Francisco, CA 94107<br>
                    Phone: (555) 539-1037<br>
                </address>
            </div>
        </div>
        <!-- /.row -->

        <!-- Table row -->
        <div class="row">
            <div class="col-xs-12 table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Item</th>
                        <th>Quantity</th>
                        <th>Rate</th>
                        <th>Discount</th>
                        <th>Subtotal</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>aaaa</td>
                        <td>aa</td>
                        <td>aaa</td>
                        <td>aa</td>
                        <td>aaaa</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->

        <div class="row">
            <!-- accepted payments column -->
            <div class="col-xs-4">
                <p class="lead">Description</p>
                <p class="text-muted well well-sm no-shadow" style="margin-top: 10px;">
                    Description here..
                </p>
            </div>
            <!-- /.col -->
            <div class="col-xs-4">&nbsp;</div>
            <div class="col-xs-4">
                <div class="table-responsive">
                    <table class="table">
                        <tr>
                            <th>Discount:</th>
                            <td>$250.30</td>
                        </tr>
                        <tr>
                            <th>Tax (9.3%)</th>
                            <td>$10.34</td>
                        </tr>
                        <tr>
                            <th>Shipping:</th>
                            <td>$5.80</td>
                        </tr>
                        <tr>
                            <th>Total:</th>
                            <td>$265.24</td>
                        </tr>
                    </table>
                </div>
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
