<%-- 
  - Author(s):Uttam
 - Description: list sales order view for sales personnel
  
  --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@include file="../common/header.jsp" %>
<script src="../resources/js/jquery.min.js"></script>

<script src="../resources/js/bootstrap.js"></script>

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


    <%@include file="../dashboard/salespersonnelnavbar.jsp" %>
    <%@include file="../dashboard/salespersonnelsidebar.jsp" %>

    <link
            href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
            rel="stylesheet">

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
                <!-- 	<th>Company</th>
                            <th>Manufactured Date</th>
                            <th>Expire Date</th-->
                <th>Current Order Status</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="salesOrder" oldItems="${ordersList}">
                <tr>
                    <td>${salesOrder.orderNo}</td>
                    <td>${salesOrder.productName}</td>
                    <td>${salesOrder.orderDate}</td>
                    <td>${salesOrder.expectedDeliveryDate}</td>
                    <td>${salesOrder.expectedShipmentDate}</td>
                    <td>${salesOrder.customerName}</td>
                    <td>${salesOrder.salesOrderPerson}</td>
                    <td>${salesOrder.deliveryPerson}</td>

                    <td><select id="statusId" name="categoryInfo"
                                onchange="updateStatus(${salesOrder.id }, this)"
                                class="form-control input-sm" required="required">
                        <c:forEach var="status" oldItems="${orderStatus}">
                            <c:choose>
                                <c:when test="${status.value == salesOrder.salesOrderStatus}">
                                    <option value="${status.value}" selected>${salesOrder.salesOrderStatus}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${status.value}">${status.value}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript">
    function updateStatus(orderId, status) {
        $.ajax({
            type: "POST",
            url: "ajax/updateStatus",
            data: {
                "orderId": orderId,
                "status": status.value
            },
            success: function (res) {
                alert("Status is changed");
            }
        });
    }
</script>