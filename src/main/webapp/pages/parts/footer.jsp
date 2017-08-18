<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- Main Footer -->
<footer class="main-footer">
    <!-- To the right -->
    <div class="pull-right hidden-xs">
        Anything you want
    </div>
    <!-- Default to the left -->
    <strong>Copyright &copy; 2017 <a href="#">Company</a>.</strong> All rights reserved.
</footer>

<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
        <li class="active"><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
        <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <!-- Home tab content -->
        <div class="tab-pane active" id="control-sidebar-home-tab">
            <h3 class="control-sidebar-heading">Recent Activity</h3>
            <ul class="control-sidebar-menu">

                <li>
                    <a href="javascript:void(0);">
                        <i class="menu-icon fa fa-birthday-cake bg-red"></i>

                        <div class="menu-info">
                            <h4 class="control-sidebar-subheading">ToDays Sale</h4>

                            <p>Rs 12,000,000</p>
                        </div>
                    </a>
                </li>

                <li>
                    <a href="javascript:void(0);">
                        <i class="menu-icon fa fa-birthday-cake bg-red"></i>

                        <div class="menu-info">
                            <h4 class="control-sidebar-subheading">ToDays Collection</h4>

                            <p>Rs 10,000,000</p>
                        </div>
                    </a>
                </li>

            </ul>
            <!-- /.control-sidebar-menu -->

            <h3 class="control-sidebar-heading">Tasks Progress</h3>
            <ul class="control-sidebar-menu">

                <sec:authorize access="hasRole('ROLE_SUPERADMINISTRATOR,ROLE_AUTHENTICATED')" >

                <li class="active"><a href="${pageContext.request.contextPath}/user/list"><i class="fa fa-link"></i> <span>Sales Order Pending <span class="badge">26</span></span></a></li>

                <li><a href="${pageContext.request.contextPath}/user/list"><i class="fa fa-link"></i> <span>Sales Order Accepted <span class="badge">6</span></span></a></li>

                <li><a href="${pageContext.request.contextPath}/user/list"><i class="fa fa-link"></i> <span>Sales Order Packed <span class="badge">14</span></span></a></li>

                <li><a href="${pageContext.request.contextPath}/user/list"><i class="fa fa-link"></i> <span>Sales Order Shipped <span class="badge">10</span></span></a></li>

                <li><a href="${pageContext.request.contextPath}/user/list"><i class="fa fa-link"></i> <span>Purchase Order Pending <span class="badge">3</span></span></a></li>

                <li><a href="${pageContext.request.contextPath}/user/list"><i class="fa fa-link"></i> <span>Purchase Order Issued <span class="badge">1</span></span></a></li>
</sec:authorize>
            </ul>
            <!-- /.control-sidebar-menu -->

        </div>
        <!-- /.tab-pane -->
        <!-- Stats tab content -->
<%--
        <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
--%>
        <!-- /.tab-pane -->
        <!-- Settings tab content -->
        <div class="tab-pane" id="control-sidebar-settings-tab">
            <ul class="sidebar-menu tree" data-widget="tree">
                <li class="header">Extra Settings</li>
                <!-- Optionally, you can add icons to the links -->
                <sec:authorize access="hasRole('ROLE_SUPERADMINISTRATOR,ROLE_AUTHENTICATED')" >

                <li class="active"><a href="${pageContext.request.contextPath}/state/list"><i class="fa fa-link"></i> <span>Employee</span></a></li>

                <li><a href="${pageContext.request.contextPath}/country/list"><i class="fa fa-link"></i> <span>Country</span></a></li>

                <li><a href="${pageContext.request.contextPath}/state/list"><i class="fa fa-link"></i> <span>State</span></a></li>

                <li><a href="${pageContext.request.contextPath}/city/list"><i class="fa fa-link"></i> <span>City</span></a></li>

                <li><a href="${pageContext.request.contextPath}/state/list"><i class="fa fa-link"></i> <span>Category</span></a></li>

                <li><a href="${pageContext.request.contextPath}/city/list"><i class="fa fa-link"></i> <span>SubCategory</span></a></li>

                <li><a href="${pageContext.request.contextPath}/state/list"><i class="fa fa-link"></i> <span>Unit</span></a></li>

                <li><a href="${pageContext.request.contextPath}/city/list"><i class="fa fa-link"></i> <span>Tag</span></a></li>

                <li><a href="${pageContext.request.contextPath}/state/list"><i class="fa fa-link"></i> <span>Lot</span></a></li>

            <%--<li class="treeview">
                    <a href="#"><i class="fa fa-link"></i> <span>Product Accesories</span>
                        <span class="pull-right-container">
                            <i class="fa fa-angle-left pull-right"></i>
                        </span>
                    </a>
                    <ul class="treeview-menu" style="display: none;">
                        <li><a href="#">Category</a></li>
                        <li><a href="#">SubCategory</a></li>
                        <li><a href="#">Unit</a></li>
                        <li><a href="#">Tag</a></li>
                        <li><a href="#">Lot</a></li>
                    </ul>
                </li>--%>
</sec:authorize>
            </ul>
        </div>
        <!-- /.tab-pane -->
    </div>
</aside>
<!-- /.control-sidebar -->
<!-- Add the sidebar's background. This div must be placed
immediately after the control sidebar -->
<div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIAjaxD JS SCRIPTS -->

<!-- jQuery 3 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<%--select2 dropdown--%>
<script src="${pageContext.request.contextPath}/resources/js/select2.full.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<!-- DataTables -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${pageContext.request.contextPath}/resources/js/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/resources/js/adminlte.min.js"></script>

<%--notification--%>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-notify.min.js"></script>
<%--js model--%>
<script src="${pageContext.request.contextPath}/resources/js/assest/model/model.js"></script>
<%--js service--%>
<script src="${pageContext.request.contextPath}/resources/js/assest/service/service.js"></script>
<%--js app--%>
<script src="${pageContext.request.contextPath}/resources/js/assest/app/app.js"></script>

<!-- page script -->
<script>
    $(function () {
        $('#table1').DataTable()
        $('#table2').DataTable({
            'paging'      : true,
            'lengthChange': false,
            'searching'   : true,
            'ordering'    : true,
            'info'        : true,
            'autoWidth'   : false
        })
    })
</script>
</body>
</html>