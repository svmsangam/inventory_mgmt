<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	<form role="search">
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Search">
		</div>
	</form>
	<ul class="nav menu">
		<li><a href="${pageContext.request.contextPath}/">
				<i class="fa fa-arrow-left fa-lg" aria-hidden="true"></i>&nbsp; Back To Dashboard
		</a></li>
		
		<li><a href="${pageContext.request.contextPath}/app/service">
		<i class="fa fa-handshake-o fa-lg" aria-hidden="true"></i>&nbsp; Service</a></li>
		
		<li><a href="${pageContext.request.contextPath}/app/productInfo">
		<i class="fa fa-tagInfo fa-lg" aria-hidden="true"></i>&nbsp; Product</a></li>
		
		<%--<li><a href="${pageContext.request.contextPath}/app/oldItem">
		<i class="fa fa-list-alt fa-lg" aria-hidden="true"></i>&nbsp; Items </a></li>
		--%>
		<li><a href="${pageContext.request.contextPath}/app/vendor">
		<i class="fa fa-male fa-lg" aria-hidden="true"></i>&nbsp; Vendors</a></li>
		
		<li><a href="${pageContext.request.contextPath}/app/clientInfo">
		<i class="fa fa-id-badge fa-lg" aria-hidden="true"></i>&nbsp; Customers</a></li>
		
		<li><a href="${pageContext.request.contextPath}/app/categoryInfo">
		<i class="fa fa-folder fa-lg" aria-hidden="true"></i>&nbsp; Category Type</a></li>
		
		<li><a href="${pageContext.request.contextPath}/cityInfo">
		<i class="fa fa-map-marker fa-lg" aria-hidden="true"></i>&nbsp; City</a></li>
		
		<li><a href="${pageContext.request.contextPath}/stateInfo">
		<i class="fa fa-map fa-lg" aria-hidden="true"></i>&nbsp; State</a></li>
		
		<li><a href="${pageContext.request.contextPath}/countryInfo">
		<i class="fa fa-globe fa-lg" aria-hidden="true"></i>&nbsp; Country</a></li>
		
	</ul>

</div>

<%@include file="../common/footer.jsp"%>