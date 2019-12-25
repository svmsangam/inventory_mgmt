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

        <c:if test="${not empty orderError.error}">
            <div class="alert alert-danger alert-dismissable">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                <strong>${orderError.error}</strong>
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Add Sales Order</h3>
                        <div class="pull-right">Order No. #${orderNo}<p class="form-error">${orderError.orderNo}</p>
                        </div>
                    </div>

                    <div class="box-body">
                        <div class="row">
                            <div class="col-lg-6">
                                <label>QR Scanner :</label>
                                <select class='choose2 form-control itemQrSearch' name='' url='${pageContext.request.contextPath}/item/show'></select>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group pull-right">
                                    <label>Total Cost </label>
                                    <span class="btn btn-default btn-flat calculation">Rs.&nbsp;<h2 id="total">${mytotalamount + mytotalamount * tax /100}</h2></span>
                                </div>
                            </div>
                        </div>

                        <form action="${pageContext.request.contextPath}/order/sale/save" autocomplete="off" method="post"
                              modelAttribute="order">

                            <div class="row">
                                <div class="col-lg-12">
                                    <%@include file="/pages/order/form.jsp" %>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="box-footer">
                                        <div class="row">

                                            <div class="col-md-6 pull-right">
                                                <button type="submit"
                                                        class="btn btn-primary btn-flat btn-sm pull-right">
                                                     save changes &nbsp; &nbsp;
                                                    <i class="fa fa-save fa-lg"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </form>
                        <!-- /.box -->
                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@include file="/pages/parts/footer.jsp" %>
<%@include file="/pages/order/script/form_script.jsp" %>
<script src="${pageContext.request.contextPath}/resources/js/asset/app/numberValidation.js"></script>


