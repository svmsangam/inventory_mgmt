<%-- 
  - Author(s):Uttam
 - Description: side bar for delivery personnel
  
  --%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	<form role="search">
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Search">
		</div>
	</form>
	<%-- <ul class="nav menu">
		<li><a href="${pageContext.request.contextPath}/admin/dashboard">
				<svg class="glyph stroked dashboard-dial">
					<use xlink:href="#stroked-dashboard-dial"></use>
				</svg> Dashboard
		</a></li>
		<li class="parent"><a href="${pageContext.request.contextPath}/addStore"> <span data-toggle="collapse"
				href="#add-transaction"> <svg
						class="glyph stroked chevron-down">
						<use xlink:href="#stroked-chevron-down"></use>
				</svg>
			</span> Business --%>
		</a>
			<ul class="children collapse" id="">
				<li><a class=""
					href="${pageContext.request.contextPath}/addBusinesstype"> <svg
							class="glyph stroked table">
							<use xlink:href="#stroked-table">
							</use></svg> Create Transaction
				</a></li>
				<li><a class=""
					href="${pageContext.request.contextPath}/uploadTransactionFile">
						<svg class="glyph stroked table">
							<use xlink:href="#stroked-table"></use></svg> profile
				</a></li>
			</ul></li>
		<li><a href="${pageContext.request.contextPath}/salesOrder"><svg
					class="glyph stroked calendar">
					
					<use xlink:href="#stroked-calendar"></use></svg> List & Edit Sales Orders</a></li>
					<li><a href="${pageContext.request.contextPath}/"><svg
					class="glyph stroked calendar">
					
					
		<li><a href="${pageContext.request.contextPath}/admin/tables"><svg
					class="glyph stroked table">
					<use xlink:href="#stroked-table"></use></svg> LOGOUT</a></li>
		<%-- <li><a href="${pageContext.request.contextPath}/admin/forms"><svg
					class="glyph stroked pencil">
					<use xlink:href="#stroked-pencil"></use></svg> Forms</a></li>
		<li><a href="${pageContext.request.contextPath}/admin/panels"><svg
					class="glyph stroked app-window">
					<use xlink:href="#stroked-app-window"></use></svg> Alerts &amp; Panels</a></li>
		<li><a href="${pageContext.request.contextPath}/admin/icons"><svg
					class="glyph stroked star">
					<use xlink:href="#stroked-star"></use></svg> Icons</a></li>
		<li class="parent "><a href="#"> <span data-toggle="collapse"
				href="#sub-oldItem-1"><svg class="glyph stroked chevron-down">
						<use xlink:href="#stroked-chevron-down"></use></svg></span> Dropdown
		</a>
			<ul class="children collapse" id="sub-oldItem-1">
				<li><a class="" href="#"> <svg
							class="glyph stroked chevron-right">
							<use xlink:href="#stroked-chevron-right"></use></svg> Sub OldItem 1
				</a></li>
				<li><a class="" href="#"> <svg
							class="glyph stroked chevron-right">
							<use xlink:href="#stroked-chevron-right"></use></svg> Sub OldItem 2
				</a></li>
				<li><a class="" href="#"> <svg
							class="glyph stroked chevron-right">
							<use xlink:href="#stroked-chevron-right"></use></svg> Sub OldItem 3
				</a></li>
			</ul></li> --%>
		<li role="presentation" class="divider"></li>
		<li><a href="login.html"><svg class="glyph stroked male-user">
					<use xlink:href="#stroked-male-user"></use></svg> Login Page</a></li>
	</ul>

</div>