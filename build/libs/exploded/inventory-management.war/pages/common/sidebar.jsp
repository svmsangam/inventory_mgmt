<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	<form role="search">
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Search">
		</div>
	</form>
	<ul class="nav menu">
		<li><a href="${pageContext.request.contextPath}/dashboard">
				<i class="fa fa-tachometer fa-lg" aria-hidden="true"></i>&nbsp; Dashboard
		</a></li>
		
		<li><a href="${pageContext.request.contextPath}/business">
		 		<i class="fa fa-briefcase fa-lg" aria-hidden="true"></i>&nbsp; Business
		</a></li>
		
			
			<li><a href="${pageContext.request.contextPath}/app/addsubcategory">
		 		<i class="fa fa-briefcase fa-lg" aria-hidden="true"></i>&nbsp; Subcategory test
		</a></li>
		<li><a href="${pageContext.request.contextPath}/settings">
				 <i class="fa fa-cogs fa-lg" aria-hidden="true"></i>&nbsp; Settings
		</a></li>
	</ul>

</div>