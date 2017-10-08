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
        <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
                <input type="text" name="q" class="form-control" placeholder="Search...">
                <span class="input-group-btn">
              <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
              </button>
            </span>
            </div>
        </form>
        <!-- /.search form -->

        <!-- Sidebar Menu -->
        <ul class="sidebar-menu" data-widget="tree">
            <li><a href="${pageContext.request.contextPath}/dashboard"><i class="fa fa-home"></i><span>Dashboard</span></a></li>
            <!-- Optionally, you can add icons to the links -->
            <li class="active"><a href="${pageContext.request.contextPath}/user/list"><i class="fa fa-link"></i> <span>User</span></a>
            </li>

            <li><a href="${pageContext.request.contextPath}/invoice/list"><i class="fa fa-calendar"></i> <span>Invoice</span></a></li>

            <li><a href="#"><i class="fa fa-link"></i> <span>Ledger</span></a></li>

            <li><a href="#"><i class="fa fa-link"></i> <span>Product</span></a></li>

            <li class="treeview">
                <a href="#"><i class="fa fa-link"></i> <span>Sales Order</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#">Order List</a></li>
                    <li><a href="#">Order Add</a></li>
                    <li><a href="#">Return List</a></li>
                    <li><a href="#">Return Add</a></li>

                </ul>
            </li>

            <li class="treeview">
                <a href="#"><i class="fa fa-link"></i> <span>Purchase Order</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#">Order List</a></li>
                    <li><a href="#">Order Add</a></li>
                    <li><a href="#">Return List</a></li>
                    <li><a href="#">Return Add</a></li>
                </ul>
            </li>

            <li class="treeview">
                <a href="#"><i class="fa fa-link"></i> <span>Client</span>
                    <span class="pull-right-container">
                <i class="fa fa-angle-left pull-right"></i>
              </span>
                </a>
                <ul class="treeview-menu">
                    <li><a href="#">Customer</a></li>
                    <li><a href="#">Vendor</a></li>
                </ul>
            </li>

        </ul>
        <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
</aside>

