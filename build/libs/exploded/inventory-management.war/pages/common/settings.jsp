<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="../common/header.jsp" %>

<%@include file="../common/navbar.jsp" %>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	<form role="search">
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Search">
		</div>
	</form>
	<ul class="nav menu">
		<li><a href="${pageContext.request.contextPath}/dashboard">
				<i class="fa fa-arrow-left fa-lg" aria-hidden="true"></i>&nbsp; Back To Main
		</a></li>
		<li><a href="${pageContext.request.contextPath}/cityInfo">
				<i class="fa fa-map-marker fa-lg" aria-hidden="true"></i>&nbsp; City
		</a></li>
		<li><a href="${pageContext.request.contextPath}/stateInfo">
				<i class="fa fa-map fa-lg" aria-hidden="true"></i>&nbsp; State
		</a></li>
		<li><a href="${pageContext.request.contextPath}/countryInfo">
				<i class="fa fa-globe fa-lg" aria-hidden="true"></i>&nbsp; Country
		</a></li>
	</ul>

</div>



<%@include file="../common/footer.jsp"%>