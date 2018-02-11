<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/16/17
  Time: 8:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- iCheck -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/iCheck/square/blue.css">

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
                        <h3 class="box-title">Manage User</h3>
                        <%--<div class="box-tools">
                            <button type="button" class="btn btn-info btn-sm btn-flat pull-right addUser" data-toggle="modal" data-target="#modal-add"><span class="glyphicon glyphicon-plus-sign"></span>  Add</button>
                        </div>--%>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                            <div class="col-md-6">
                                <div class="box box-solid">
                                    <div class="box-header with-border">
                                        <h3 class="box-title">${user.inventoryuser}</h3>
                                    </div>
                                    <!-- /.box-header -->
                                    <div class="box-body">
                                        <dl>
                                            <dd>User Type: <b>${user.userType}</b></dd>
                                            <c:if test="${store ne null}">
                                                <dd>Associated With: <b>${store.name}</b></dd>
                                                <dd>Contact No.: <b>${store.contact}</b></dd>
                                                <dd>Mobile No.: <b>${store.mobileNumber}</b></dd>
                                                <dd>Address: <b>${store.cityName}</b></dd>
                                            </c:if>
                                        </dl>
                                    </div>
                                    <!-- /.box-body -->
                                </div>
                                <!-- /.box -->
                            </div>
                        <style>
                            .myiterat {
                                margin-right: 10px;
                            }
                        </style>

                        <form method="POST"
                              action="${pageContext.request.contextPath}/user/manage?userId=${user.userId}"
                              modelAttribute="userpermission">
                            <div class="table-responsive">
                            <table class="table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>Service &nbsp;<input type="checkbox" class="all" /><strong >Select All</strong></th>
                                    <th>Create</th>
                                    <th>View</th>
                                    <th>Update</th>
                                    <th>Delete</th>
                                </tr>
                                </thead>
                                <tbody>

                                <tr>
                                    <td>Category</td>
                                    <td>
                                        <div class="checkbox icheck"><label class="myiterat"><c:choose><c:when
                                                test="${fn:contains(userpermission, 'CATEGORY_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="CATEGORY_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="CATEGORY_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'CATEGORY_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="CATEGORY_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="CATEGORY_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'CATEGORY_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="CATEGORY_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="CATEGORY_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'CATEGORY_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="CATEGORY_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="CATEGORY_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>SubCategory</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SUBCATEGORY_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="SUBCATEGORY_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SUBCATEGORY_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SUBCATEGORY_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="SUBCATEGORY_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SUBCATEGORY_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SUBCATEGORY_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="SUBCATEGORY_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SUBCATEGORY_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SUBCATEGORY_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="SUBCATEGORY_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SUBCATEGORY_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Tag</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'TAG_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="TAG_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="TAG_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'TAG_VIEW')}"><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="TAG_VIEW"
                                                                                                         class="myiterator"
                                                                                                         checked/></c:when><c:otherwise><input
                                                type="checkbox" name="permissionList" value="TAG_VIEW"
                                                class="myiterator"/></c:otherwise></c:choose></label></div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'TAG_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="TAG_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="TAG_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'TAG_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="TAG_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="TAG_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Unit</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'UNIT_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="UNIT_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="UNIT_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'UNIT_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="UNIT_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="UNIT_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'UNIT_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="UNIT_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="UNIT_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'UNIT_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="UNIT_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="UNIT_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Product</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PRODUCT_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="PRODUCT_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PRODUCT_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PRODUCT_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="PRODUCT_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PRODUCT_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PRODUCT_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="PRODUCT_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PRODUCT_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PRODUCT_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="PRODUCT_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PRODUCT_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Item</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'ITEM_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="ITEM_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="ITEM_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'ITEM_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="ITEM_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="ITEM_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'ITEM_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="ITEM_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="ITEM_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'ITEM_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="ITEM_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="ITEM_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Client</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'CLIENT_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="CLIENT_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="CLIENT_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'CLIENT_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="CLIENT_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="CLIENT_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'CLIENT_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="CLIENT_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="CLIENT_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'CLIENT_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="CLIENT_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="CLIENT_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Sales Order</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SALES_ORDER_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="SALES_ORDER_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SALES_ORDER_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SALES_ORDER_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="SALES_ORDER_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SALES_ORDER_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SALES_ORDER_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="SALES_ORDER_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SALES_ORDER_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SALES_ORDER_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="SALES_ORDER_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SALES_ORDER_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Purchase Order</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PURCHASE_ORDER_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="PURCHASE_ORDER_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PURCHASE_ORDER_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PURCHASE_ORDER_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="PURCHASE_ORDER_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PURCHASE_ORDER_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PURCHASE_ORDER_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="PURCHASE_ORDER_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PURCHASE_ORDER_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PURCHASE_ORDER_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="PURCHASE_ORDER_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PURCHASE_ORDER_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Invoice</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'INVOICE_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="INVOICE_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="INVOICE_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'INVOICE_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="INVOICE_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="INVOICE_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'INVOICE_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="INVOICE_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="INVOICE_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'INVOICE_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="INVOICE_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="INVOICE_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Payment</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PAYMENT_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="PAYMENT_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PAYMENT_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PAYMENT_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="PAYMENT_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PAYMENT_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PAYMENT_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="PAYMENT_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PAYMENT_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PAYMENT_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="PAYMENT_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PAYMENT_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Sales Return</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SALES_ORDER_RETURN_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="SALES_ORDER_RETURN_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SALES_ORDER_RETURN_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SALES_ORDER_RETURN_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="SALES_ORDER_RETURN_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SALES_ORDER_RETURN_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SALES_ORDER_RETURN_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="SALES_ORDER_RETURN_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SALES_ORDER_RETURN_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'SALES_ORDER_RETURN_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="SALES_ORDER_RETURN_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="SALES_ORDER_RETURN_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Purchase Return</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PURCHASE_ORDER_RETURN_CREATE')}"><input
                                                type="checkbox" name="permissionList"
                                                value="PURCHASE_ORDER_RETURN_CREATE" class="myiterator"
                                                checked/></c:when><c:otherwise><input type="checkbox"
                                                                                      name="permissionList"
                                                                                      value="PURCHASE_ORDER_RETURN_CREATE"
                                                                                      class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PURCHASE_ORDER_RETURN_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="PURCHASE_ORDER_RETURN_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="PURCHASE_ORDER_RETURN_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PURCHASE_ORDER_RETURN_UPDATE')}"><input
                                                type="checkbox" name="permissionList"
                                                value="PURCHASE_ORDER_RETURN_UPDATE" class="myiterator"
                                                checked/></c:when><c:otherwise><input type="checkbox"
                                                                                      name="permissionList"
                                                                                      value="PURCHASE_ORDER_RETURN_UPDATE"
                                                                                      class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'PURCHASE_ORDER_RETURN_DELETE')}"><input
                                                type="checkbox" name="permissionList"
                                                value="PURCHASE_ORDER_RETURN_DELETE" class="myiterator"
                                                checked/></c:when><c:otherwise><input type="checkbox"
                                                                                      name="permissionList"
                                                                                      value="PURCHASE_ORDER_RETURN_DELETE"
                                                                                      class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                <tr>
                                    <td>Report</td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'REPORT_CREATE')}"><input
                                                type="checkbox" name="permissionList" value="REPORT_CREATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="REPORT_CREATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'REPORT_VIEW')}"><input
                                                type="checkbox" name="permissionList" value="REPORT_VIEW"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="REPORT_VIEW"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label><c:choose><c:when
                                                test="${fn:contains(userpermission, 'REPORT_UPDATE')}"><input
                                                type="checkbox" name="permissionList" value="REPORT_UPDATE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="REPORT_UPDATE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="checkbox icheck"><label style="margin-right: 10px;"><c:choose><c:when
                                                test="${fn:contains(userpermission, 'REPORT_DELETE')}"><input
                                                type="checkbox" name="permissionList" value="REPORT_DELETE"
                                                class="myiterator" checked/></c:when><c:otherwise><input type="checkbox"
                                                                                                         name="permissionList"
                                                                                                         value="REPORT_DELETE"
                                                                                                         class="myiterator"/></c:otherwise></c:choose></label>
                                        </div>
                                    </td>
                                </tr>

                                </tbody>
                            </table>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="submit" value="update changes" class="btn btn-success btn-sm pull-right"/>
                        </form>

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
   /* $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%'
        });
    });*/

    $(function () {
        var checkAll = $('input.all');
        var checkboxes = $('input.myiterator');

        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%'
        });

        checkAll.on('ifChecked ifUnchecked', function(event) {
            if (event.type == 'ifChecked') {
                checkboxes.iCheck('check');
            } else {
                checkboxes.iCheck('uncheck');
            }
        });

        checkboxes.on('ifChanged', function(event){
            if(checkboxes.filter(':checked').length == checkboxes.length) {
                checkAll.prop('checked', 'checked');
            } else {
                checkAll.removeProp('checked');
            }
            checkAll.iCheck('update');
        });
    });

    $(document).ready(function () {

        $('input').on('ifChecked', function(event){
            console.log(event.type + ' callback');
        });

        // Remove the checked state from "All" if any checkbox is unchecked
        $(document).on('click', 'uncheck' , function (event) {
            $('#check-all').iCheck('uncheck');
        });

// Make "All" checked if all checkboxes are checked
        $('.check').on('ifChecked', function (event) {
            if ($('.check').filter(':checked').length == $('.check').length) {
                $('#check-all').iCheck('check');
            }
        });

    });
</script>