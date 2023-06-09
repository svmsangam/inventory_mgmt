<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/13/17
  Time: 10:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- Sidebar user panel (optional) -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="${pageContext.request.contextPath}/resources/img/inventory-logo.png" class="img-circle"
                     alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${pageContext.request.userPrincipal.name}</p>
                <!-- Status -->
                <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
        </div>

        <!-- search form (Optional) -->
        <%--<form method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
              <button type="submit" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
              </button>
            </span>
            </div>
        </form>--%>
        <!-- /.search form -->

        <!-- Sidebar Menu -->
        <ul class="sidebar-menu" data-widget="tree">
            <li><a href="${pageContext.request.contextPath}/dashboard"><i class="fa fa-home"></i><span>Dashboard</span></a></li>

            <li><a href="${pageContext.request.contextPath}/order/sale/quick"><i class="glyphicon glyphicon-fast-forward"></i><span>Quick Receipt</span></a></li>

            <!-- Optionally, you can add icons to the links -->
            <li><a href="${pageContext.request.contextPath}/user/list"><i class="fa fa-users"></i> <span>User</span></a></li>

            <li><a href="${pageContext.request.contextPath}/store/list"><i class="fa fa-suitcase"></i>
                <span>Store</span></a></li>

            <li><a href="${pageContext.request.contextPath}/invoice/list"><i class="fa fa-calendar-check-o"></i> <span>Receipts</span></a></li>

            <li><a href="${pageContext.request.contextPath}/ledger/list"><i class="fa fa-columns"></i> <span>Ledger</span></a></li>

            <li><a href="${pageContext.request.contextPath}/product/list"><i class="fa fa-cubes"></i> <span>Product</span></a></li>

            <li><a href="${pageContext.request.contextPath}/item/add"><i class="fa fa-plus-square-o"></i> <span>New Item</span></a></li>

            <li class="treeview">
                <a href="#"><i class="fa fa-line-chart"></i> <span>Sales Order</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="${pageContext.request.contextPath}/order/sale/list"><i class="fa fa-list-ul"></i> <span>Order List</span></a></li>
                    <li><a href="${pageContext.request.contextPath}/order/sale/add"><i class="fa fa-plus"></i> <span>Order Add</span></a></li>
                    <li><a href="${pageContext.request.contextPath}/order/sale/inactive"><i class="fa fa-trash-o"></i> <span>Order Inactive</span></a></li>
                    <li><a href="${pageContext.request.contextPath}/orderreturn/list"><i class="fa fa-backward"></i> <span>Order Return</span></a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#"><i class="fa fa-address-book-o"></i> <span>Client</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="${pageContext.request.contextPath}/customer/list"><i class="fa fa-male"></i> <span>Customer</span></a></li>
                    <li><a href="${pageContext.request.contextPath}/vendor/list"><i class="fa fa-truck"></i> <span>Vendor</span></a></li>
                </ul>
            </li>

        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>
