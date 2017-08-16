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

                        ${user.inventoryuser}

                        ${user.userType}

                        <c:if test="${store ne null}">
                            Associate with : ${store.name} contact ${store.contact} , ${store.mobileNumber} address ${store.cityName}
                        </c:if>


                            <form method="POST" action="${pageContext.request.contextPath}/user/manage?userId=${user.userId}" modelAttribute="userpermission">

                                <table class="table table-striped table-bordered">
                                    <thead>
                                    <tr>
                                        <th>Service</th>
                                        <th>Create</th>
                                        <th>View</th>
                                        <th>Update</th>
                                        <th>Delete</th>
                                    </tr>
                                    </thead>
                                    <tbody>


                                    <tr>
                                        <td>Category</td>
                                        <td><input type="checkbox" name="permissionList" value="CATEGORY_CREATE" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="CATEGORY_VIEW" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="CATEGORY_UPDATE" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="CATEGORY_DELETE" class="myiterator" /></td>
                                    </tr>

                                    <tr>
                                        <td>SubCategory</td>
                                        <td><input type="checkbox" name="permissionList" value="SUBCATEGORY_CREATE" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="SUBCATEGORY_VIEW" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="SUBCATEGORY_UPDATE" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="SUBCATEGORY_DELETE" class="myiterator"  /></td>
                                    </tr>

                                    <tr>
                                        <td>Tag</td>
                                        <td><input type="checkbox" name="permissionList" value="TAG_CREATE" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="TAG_VIEW" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="TAG_UPDATE" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="TAG_DELETE" class="myiterator" /></td>
                                    </tr>

                                    <tr>
                                        <td>Unit</td>
                                        <td><input type="checkbox" name="permissionList" value="UNIT_CREATE" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="UNIT_VIEW" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="UNIT_UPDATE" class="myiterator" /></td>
                                        <td><input type="checkbox" name="permissionList" value="UNIT_DELETE" class="myiterator" /></td>
                                    </tr>

                                    <tr>
                                        <td>Product</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PRODUCT_CREATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PRODUCT_VIEW"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PRODUCT_UPDATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PRODUCT_DELETE"  /></td>
                                    </tr>

                                    <tr>
                                        <td>Item</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="ITEM_CREATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="ITEM_VIEW"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="ITEM_UPDATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="ITEM_DELETE"  /></td>
                                    </tr>

                                    <tr>
                                        <td>Client</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="CLIENT_CREATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="CLIENT_VIEW"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="CLIENT_UPDATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="CLIENT_DELETE"  /></td>
                                    </tr>

                                    <tr>
                                        <td>Sales Order</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="SALES_ORDER_CREATE"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="SALES_ORDER_VIEW"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="SALES_ORDER_UPDATE"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="SALES_ORDER_DELETE"
                                        /></td>
                                    </tr>

                                    <tr>
                                        <td>Purchase Order</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PURCHASE_ORDER_CREATE"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PURCHASE_ORDER_VIEW"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PURCHASE_ORDER_UPDATE"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PURCHASE_ORDER_DELETE"
                                        /></td>
                                    </tr>

                                    <tr>
                                        <td>Invoice</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="INVOICE_CREATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="INVOICE_VIEW"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="INVOICE_UPDATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="INVOICE_DELETE"  /></td>
                                    </tr>

                                    <tr>
                                        <td>Payment</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PAYMENT_CREATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PAYMENT_VIEW"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PAYMENT_UPDATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PAYMENT_DELETE"  /></td>
                                    </tr>

                                    <tr>
                                        <td>Sales Return</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="SALES_ORDER_RETURN_CREATE"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="SALES_ORDER_RETURN_VIEW"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="SALES_ORDER_RETURN_UPDATE"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="SALES_ORDER_RETURN_DELETE"
                                        /></td>
                                    </tr>

                                    <tr>
                                        <td>Purchase Return</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PURCHASE_ORDER_RETURN_CREATE"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PURCHASE_ORDER_RETURN__VIEW"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PURCHASE_ORDER_RETURN__UPDATE"
                                        /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="PURCHASE_ORDER_RETURN__DELETE"
                                        /></td>
                                    </tr>

                                    <tr>
                                        <td>Report</td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="REPORT_CREATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="REPORT_VIEW"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="REPORT_UPDATE"  /></td>
                                        <td><input type="checkbox" name="permissionList"
                                                   class="myiterator" value="REPORT_DELETE"  /></td>
                                    </tr>

                                    </tbody>
                                </table>

                                <br /> <br />
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <input type="submit" value="update changes" class="btn btn-success btn-block" />
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
