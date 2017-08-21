<%-- 
  - Author(s):Uttam
 list sales order view
  - Description: 
  --%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp" %>
<script
        src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link
        href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
        rel="stylesheet">
<script>
    function myFunction() {
        // Declare variables
        var input, filter, table, tr, td, i;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");

        // Loop through all table rows, and hide those who don't match the search query
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[0];
            if (td) {
                if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%-- <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#sidebar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"><span>TMK INVENTORY</span>Admin</a>
			<ul class="user-menu">
				<li class="dropdown pull-right"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"><svg
							class="glyph stroked male-user">
							<use xlink:href="#stroked-male-user"></use></svg> User <span
						class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="#"><svg class="glyph stroked male-user">
									<use xlink:href="#stroked-male-user"></use></svg> Profile</a></li>
						<li><a href="#"><svg class="glyph stroked gear">
									<use xlink:href="#stroked-gear"></use></svg> Settings</a></li>
						<li><a href="${pageContext.request.contextPath}/home/logout"><svg
									class="glyph stroked cancel">
									<use xlink:href="#stroked-cancel"></use></svg> Logout</a></li>
					</ul></li>
			</ul>
		</div>

	</div>
	<!-- /.container-fluid -->
</nav> --%>
<div class="container-wrapper">
    <div class="container">
        <c:if test="${not empty message}">
            <p class="bg-success">
                <c:out value="${message}"></c:out>
            </p>
        </c:if>
        <c:choose>
            <c:when test="${fn:length(salesOrderDtoList) eq 0}">
                <c:if test="${not empty message}">
                    <p class="bg-success">
                        <c:out value="${message}"></c:out>
                    </p>
                </c:if>
            </c:when>
            <c:when test="${fn:length(salesOrderDtoList) gt 0}">
                <input type="text" id="myInput" class="form-control"
                       onkeyup="myFunction()" placeholder="Search for salesOrder...">
                <table id="myTable"
                       class="table table-responsive table-sm table-striped table-bordered table-hover">
                    <thead class="thead-inverse">
                    <tr>
                        <th>Order #</th>
                        <th>Product Name</th>
                        <th>Order date</th>
                        <th>Expected Delivery Date</th>
                        <th>Expected Shipment Date</th>
                        <th>Customer Name</th>
                        <th>Sales Order Person</th>
                        <th>Delivery Person</th>
                        <th>order no</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="salesOrder" oldItems="${salesOrderDtoList}">
                        <tr>
                            <td>${salesOrder.orderNo}</td>
                            <td>${salesOrder.productName}</td>
                            <td>${salesOrder.orderDate}</td>
                            <td>${salesOrder.expectedDeliveryDate}</td>
                            <td>${salesOrder.expectedShipmentDate}</td>
                            <td>${salesOrder.customerName}</td>
                            <td>${salesOrder.salesOrderPerson}</td>
                            <td>${salesOrder.deliveryPerson}</td>
                            <td>${salesOrder.orderNo}</td>

                            <td><a
                                    href="${pageContext.request.contextPath}/app/editSalesOrder?salesOrderId=${salesOrder.id}"
                                    class="btn btn-info btn-xs"><i
                                    class="fa fa-pencil"></i> <%-- 	Edit </a> <a href="${pageContext.request.contextPath}/deleteSalesOrder?salesOrderId=${salesOrder.id}" --%>
                                class="btn btn-danger"> Delete </a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
        </c:choose>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
