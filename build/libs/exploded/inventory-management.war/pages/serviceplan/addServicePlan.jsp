<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- CSS -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/form-elements.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/select2.css">

        <!--link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/favicon.png"-->
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/resources/multistepform/ssets/ico/apple-touch-icon-57-precomposed.png">

    <%@include file="../common/header.jsp" %>
	<%@include file="../common/navbar.jsp" %>
	<%@include file="../common/sidebar.jsp"%>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main" >
<div class="row">
	<ol class="breadcrumb">
		<li><a href="/"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg></a></li>
		<li class="active">Home</li>
			<li>
		
		</li>
	</ol>
</div><!--/.row-->
<div class="row">
	<div class="col-sm-9">
		<h1 class="page-header">Add Service  Plan</h1>
	</div>
</div><!--/.row-->
						
<!--  	<div class="top-content">
            <div class="container select2-container">
                 -->
<!-- <div class="col-sm-10 col-sm-offset-1 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3 form-box"> -->
<div class="container-wrapper"> 
<form method="POST" action="${pageContext.request.contextPath}/addBusinessServicePlan?storeId=${storeId}" modelAttribute="businessService">
      		<div class="f1-steps">
      			<div class="f1-progress">
      			    <div class="f1-progress-line" data-now-value="16.66" data-number-of-steps="2" style="width: 16.66%;"></div>
      			</div>
      			<div class="f1-step">
      				<div class="f1-step-icon"><svg class="glyph stroked chevron right"><use xlink:href="#stroked-chevron-right"/></svg></div>

      				<h3>Business Details</h3>
      			</div>
      			<div class="f1-step ">
      				<div class="f1-step-icon"><svg class="glyph stroked download"><use xlink:href="#stroked-download"/></svg></div>
      				<h3>Owner Details</h3>
      			</div>
      			<div class="f1-step active">
      				<div class="f1-step-icon"><svg class="glyph stroked download"><use xlink:href="#stroked-download"/></svg></div>
      				<h3>Service Plan</h3>
      			</div>
			
<table class="table table-striped table-bordered">
	<thead>
		<tr>
		<th>Service Plan</th>
		<th>Create</th>
		<th>View</th>
		<th>Update</th>
		<th>Delete</th>
		</tr>
	</thead>
	<tbody>

	<tr>
		<td>Category</td>
		<td><input type="checkbox" name="permission" value="CATEGORY_CREATE"/></td>
		<td><input type="checkbox" name="permission" value="CATEGORY_VIEW"/></td>
		<td><input type="checkbox" name="permission" value="CATEGORY_UPDATE"/></td>
		<td><input type="checkbox" name="permission" value="CATEGORY_DELETE"/></td>
	</tr>

	<tr>
		<td>SubCategory</td>
		<td><input type="checkbox" name="permission" value="SUBCATEGORY_CREATE"/></td>
		<td><input type="checkbox" name="permission" value="SUBCATEGORY_VIEW"/></td>
		<td><input type="checkbox" name="permission" value="SUBCATEGORY_UPDATE"/></td>
		<td><input type="checkbox" name="permission" value="SUBCATEGORY_DELETE"/></td>
	</tr>

	<tr>
		<td>Tag</td>
		<td><input type="checkbox" name="permission" value="TAG_CREATE"/></td>
		<td><input type="checkbox" name="permission" value="TAG_VIEW"/></td>
		<td><input type="checkbox" name="permission" value="TAG_UPDATE"/></td>
		<td><input type="checkbox" name="permission" value="TAG_DELETE"/></td>
	</tr>

	<tr>
		<td>Unit</td>
		<td><input type="checkbox" name="permission" value="UNIT_CREATE"/></td>
		<td><input type="checkbox" name="permission" value="UNIT_VIEW"/></td>
		<td><input type="checkbox" name="permission" value="UNIT_UPDATE"/></td>
		<td><input type="checkbox" name="permission" value="UNIT_DELETE"/></td>
	</tr>

		<tr>
			<td>Service</td>
			<td><input type="checkbox" name="permission" value="SERVICE_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="SERVICE_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="SERVICE_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="SERVICE_DELETE"/></td>
		</tr>
		<tr>
			<td>Product</td>
			<td><input type="checkbox" name="permission" value="PRODUCT_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="PRODUCT_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="PRODUCT_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="PRODUCT_DELETE"/></td>
		</tr>
		<tr>
			<td>Item</td>
			<td><input type="checkbox" name="permission" value="ITEM_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="ITEM_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="ITEM_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="ITEM_DELETE"/></td>
		</tr>
		<tr>
			<td>Sales Order</td>
			<td><input type="checkbox" name="permission" value="SALES_ORDER_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="SALES_ORDER_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="SALES_ORDER_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="SALES_ORDER_DELETE"/></td>
		</tr>
		<tr>
			<td>Invoice</td>
			<td><input type="checkbox" name="permission" value="INVOICE_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="INVOICE_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="INVOICE_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="INVOICE_DELETE"/></td>
		</tr>
		<tr>
			<td>Purchase Order</td>
			<td><input type="checkbox" name="permission" value="PURCHASE_ORDER_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="PURCHASE_ORDER_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="PURCHASE_ORDER_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="PURCHASE_ORDER_DELETE"/></td>
		</tr>
		<tr>
			<td>Bill</td>
			<td><input type="checkbox" name="permission" value="BILL_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="BILL_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="BILL_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="BILL_DELETE"/></td>
		</tr>
		<tr>
			<td>User</td>
			<td><input type="checkbox" name="permission" value="USER_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="USER_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="USER_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="USER_DELETE"/></td>
		</tr>
		<tr>
			<td>Report</td>
			<td><input type="checkbox" name="permission" value="REPORT_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="REPORT_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="REPORT_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="REPORT_DELETE"/></td>
		</tr>
		<tr>
			<td>Supplier</td>
			<td><input type="checkbox" name="permission" value="SUPPLIER_CREATE"/></td>
			<td><input type="checkbox" name="permission" value="SUPPLIER_VIEW"/></td>
			<td><input type="checkbox" name="permission" value="SUPPLIER_UPDATE"/></td>
			<td><input type="checkbox" name="permission" value="SUPPLIER_DELETE"/></td>
		</tr>

	<tr>
		<td>Store</td>
		<td><input type="checkbox" name="permission" value="STORE_CREATE"/></td>
		<td><input type="checkbox" name="permission" value="STORE_VIEW"/></td>
		<td><input type="checkbox" name="permission" value="STORE_UPDATE"/></td>
		<td><input type="checkbox" name="permission" value="STORE_DELETE"/></td>
	</tr>
		
	</tbody>
</table>
			
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
<button type="submit" class="btn btn-next pull-right">Finish</button>

</form>
			
</div><!-- /.row -->
	
</div><!--/.main-->

<%@include file="../common/footer.jsp"%>