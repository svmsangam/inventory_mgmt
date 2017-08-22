<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp" %>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Purchase Order</li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>
    <div class="container-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">Purchase Orders <a
                            href="${pageContext.request.contextPath}/app/addpurchaseorder">
                        <button class="btn btn-primary btn-sm pull-right">Create New Order</button>
                    </a>
                    </div>
                    <div class="panel-body">

                        <table id="example" class="cell-border" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th>Order No</th>
                                <th>Vendor</th>
                                <th>Total Cost</th>
                                <th>Order Date</th>
                                <th>Delivery Date</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach oldItems="${purchaseOrderList}" var="purchaseOrder">
                                <tr>
                                    <td>${purchaseOrder.orderNo} </td>
                                    <td>${purchaseOrder.vendorDto.contactName}</td>
                                    <td>${purchaseOrder.totalCost}</td>
                                    <td>${purchaseOrder.orderDate}</td>
                                    <td>${purchaseOrder.deliveryDate}</td>
                                    <td>${purchaseOrder.purchaseOrderStatus}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${purchaseOrder.purchaseOrderStatus eq 'Received'}">
                                                <span class="label label-success label-sm">Received</span>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${pageContext.request.contextPath}/app/editpurchaseorder?id=${purchaseOrder.orderId}">
                                                    <button class="btn btn-info btn-xs">Edit</button>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/app/removepurchaseorder?id=${purchaseOrder.orderId}">
                                                    <button class="btn btn-danger btn-xs"
                                                            onclick="return confirm('Are you sure, you want to DELETE?')">
                                                        Delete
                                                    </button>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div><!-- /.col-->
            </div><!-- /.row -->

        </div><!--/.main-->
    </div>
</div>
<%@include file="../common/footer.jsp" %>
<script>
    $(document).ready(function () {
        $('#example').DataTable({
            "pagingType": "full_numbers"
        });
    });
</script>