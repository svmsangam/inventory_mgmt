<%-- 
  - Author(s):Uttam
 - Description: side bar for sales personnel
  
  --%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	<form role="search">
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Search">
		</div>
	</form>
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
					
					<use xlink:href="#stroked-calendar"></use></svg> List Sales Orders</a></li>
					<li><a href="${pageContext.request.contextPath}/"><svg
					class="glyph stroked calendar">
					
					
		<li><a href="${pageContext.request.contextPath}/admin/tables"><svg
					class="glyph stroked table">
					<use xlink:href="#stroked-table"></use></svg> LOGOUT</a></li>
		<li role="presentation" class="divider"></li>
		<li><a href="login.html"><svg class="glyph stroked male-user">
					<use xlink:href="#stroked-male-user"></use></svg> Login Page</a></li>
	</ul>

</div>