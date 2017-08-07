<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
	<form role="search">
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Search">
		</div>
	</form>
	<ul class="nav menu">
		<%-- <li><a href="${pageContext.request.contextPath}/"> <i
				class="fa fa-tachometer fa-lg" aria-hidden="true"></i>&nbsp;
				Dashboard
		</a></li> --%>

			<li><a
					href="${pageContext.request.contextPath}/ledger/list"> <i
					class="fa fa-shopping-bag fa-lg" aria-hidden="true"></i>&nbsp;
				Ledger
			</a></li>

		<li><a
			href="${pageContext.request.contextPath}/productInfo/list"> <i
				class="fa fa-shopping-bag fa-lg" aria-hidden="true"></i>&nbsp;
				products
		</a></li>

		<li><a href="${pageContext.request.contextPath}/order/list/saleOrder">
				<i class="fa fa-calendar-check-o fa-lg" aria-hidden="true"></i>&nbsp;
				Sales Orders
		</a></li>

		<li><a href="${pageContext.request.contextPath}/order/addsale">
			<i class="fa fa-calendar-check-o fa-lg" aria-hidden="true"></i>&nbsp;
			Create Sales Order
		</a></li>

		<li><a href="${pageContext.request.contextPath}/order/list/purchaseorder"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp; Purchase Orders
		</a></li>

		<li><a href="${pageContext.request.contextPath}/order/addpurchase"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp; Create Purchase Order
		</a></li>

		<li><a href="${pageContext.request.contextPath}/invoiceInfo/list"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp; Invoice
		</a></li>
		
		
		<li><a href="${pageContext.request.contextPath}/clientInfo/list"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp; Customers
		</a></li>

		<li><a href="${pageContext.request.contextPath}/app/users"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp; Users
		</a></li>
		
		<li><a href="${pageContext.request.contextPath}/vendor/list"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp; Vendors
		</a></li>

		<li><a href="${pageContext.request.contextPath}/categoryInfo/list"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp;Categories
		</a></li>

		<li><a
			href="${pageContext.request.contextPath}/subCategoryInfo/list"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp;Subcategories
		</a></li>

		<li><a
				href="${pageContext.request.contextPath}/unitInfo/list"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp;Units
		</a></li>

		<li><a
				href="${pageContext.request.contextPath}/tagInfo/list"> <i
				class="fa fa-list-ol fa-lg" aria-hidden="true"></i>&nbsp;Tags
		</a></li>

	</ul>
</div>