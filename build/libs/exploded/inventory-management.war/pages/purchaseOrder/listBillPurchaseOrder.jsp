<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp"%>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="#"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
				<li class="active">Bill</li>
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
					<div class="panel-heading">Bill Of Purchase Order<a href="${pageContext.request.contextPath}/app/addbillpurchaseorder"><button class="btn btn-primary btn-sm pull-right">Create New Order</button></a>
					</div>
					<div class="panel-body">
					<table id="example" class="cell-border" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>Bill No</th>
								<th>Vendor</th>
								<th>Total Cost</th>
								<th>Bill Date</th>
								<th>Due Date</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach oldItems="${billPurchaseOrderList}" var="bill">
								<tr>
									<td>${bill.billNo}</td>
									<td>${bill.vendorDto.contactName}</td>
									<td>${bill.totalCost}</td>
									<td>${bill.billDate}</td>
									<td>${bill.dueDate}</td>
									<td>
									<a href="${pageContext.request.contextPath}/app/editbillpurchaseorder?id=${bill.billId}">
									<button class="btn btn-info btn-xs">Edit</button></a>
									<a href="${pageContext.request.contextPath}/app/removebillpurchaseorder?id=${bill.billId}">
									<button class="btn btn-danger btn-xs" onclick="return confirm('Are you sure, you want to DELETE?')">Delete</button></a>
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
	
<%@include file="../common/footer.jsp"%>

<script>
	$(document).ready(function() {
	    $('#example').DataTable( {
	        "pagingType": "full_numbers"
	    } );
	} );
	</script>	