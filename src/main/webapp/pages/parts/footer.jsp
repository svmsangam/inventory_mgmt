<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Main Footer -->
<footer class="main-footer">
    <!-- To the right -->
    <div class="pull-right hidden-xs">
        Contact Us:
        9849019834/9841891093
    </div>
    <!-- Default to the left -->
    <strong>Copyright &copy; <%=new java.text.SimpleDateFormat("yyyy").format(new java.util.Date())%> <a href="#">Company</a>.</strong> All rights reserved.
</footer>

<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
   <%-- <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
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

                <sec:authorize access="hasRole('ROLE_SUPERADMINISTRATOR,ROLE_AUTHENTICATED')">

                    <li class="active"><a href="${pageContext.request.contextPath}/user/listSale"><i class="fa fa-link"></i>
                        <span>Sales Order Pending <span class="badge">26</span></span></a></li>

                    <li><a href="${pageContext.request.contextPath}/user/listSale"><i class="fa fa-link"></i> <span>Sales Order Accepted <span
                            class="badge">6</span></span></a></li>

                    <li><a href="${pageContext.request.contextPath}/user/listSale"><i class="fa fa-link"></i> <span>Sales Order Packed <span
                            class="badge">14</span></span></a></li>

                    <li><a href="${pageContext.request.contextPath}/user/listSale"><i class="fa fa-link"></i> <span>Sales Order Shipped <span
                            class="badge">10</span></span></a></li>

                    <li><a href="${pageContext.request.contextPath}/user/listSale"><i class="fa fa-link"></i> <span>Purchase Order Pending <span
                            class="badge">3</span></span></a></li>

                    <li><a href="${pageContext.request.contextPath}/user/listSale"><i class="fa fa-link"></i> <span>Purchase Order Issued <span
                            class="badge">1</span></span></a></li>
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

        <%@include file="/pages/parts/setting.jsp" %>

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
<!-- Bootstrap 3.3.7 -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<--select2 dropdown-->
<script src="${pageContext.request.contextPath}/resources/js/select2.full.min.js"></script>
<!-- bootstrap datepicker -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-datepicker.min.js"></script>
<!-- DataTables -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.dataTables.min.js"></script>

<%--
<script src="${pageContext.request.contextPath}/resources/js/dataTables.bootstrap.min.js"></script>
--%>
<!-- SlimScroll -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="${pageContext.request.contextPath}/resources/js/fastclick.js"></script>
<!-- iCheck 1.0.1 -->
<script src="${pageContext.request.contextPath}/resources/js/icheck.min.js"></script>
<!-- AdminLTE App -->
<script src="${pageContext.request.contextPath}/resources/js/adminlte.min.js"></script>
<!-- ChartJS -->
<script src="${pageContext.request.contextPath}/resources/js/Chart.js"></script>
<%--notification--%>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap-notify.min.js"></script>
<%--js spinner--%>
<script src="${pageContext.request.contextPath}/resources/js/spin.min.js"></script>
<%--js pdf--%>
<script src="${pageContext.request.contextPath}/resources/js/jspdf.min.js"></script>
<%--js model--%>
<script src="${pageContext.request.contextPath}/resources/js/asset/model/model.js"></script>
<%--js service--%>
<script src="${pageContext.request.contextPath}/resources/js/asset/service/service.js"></script>
<%--js app--%>
<script src="${pageContext.request.contextPath}/resources/js/asset/app/app.js"></script>
<%--js bock ui loading spinner--%>
<script src="${pageContext.request.contextPath}/resources/js/jquery.blockUI.min.js"></script>

<script>

    function blockUi() {
        blockUiZ(10001);
    }

    function blockUiZ(zindex) {
        $.blockUI({
            //theme: true,
            baseZ: zindex,
            css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#000000',
                '-webkit-border-radius': '5px',
                '-moz-border-radius': '5px',
                opacity: .4,
                color: '#fff',
                'font-size': 'large',
                textAlign:	'center',
                border:		'1px solid #0c0c0c',
                cursor:		'wait'
            },
            message: 'loading...'

        })
    }

    $(function () {
        //Initialize Select2 Elements
        $('.select2').select2();

        //iCheck for checkbox and radio inputs
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
            checkboxClass: 'icheckbox_minimal-blue',
            radioClass: 'iradio_minimal-blue'
        });

        $('.datepicker').datepicker({
            autoclose: true,
            todayHighlight: true
        })
    })
</script>

<!-- page script -->
<script>
    $(function () {
        $('#table1').DataTable({
            'paging': false,
            'lengthChange': false,
            'searching': false,
            'ordering': true,
            'info': true,
            'autoWidth': false
        });

        $('#table2').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': true,
            'ordering': true,
            'info': true,
            'autoWidth': false
        })
    })
</script>

<%--for sidebar active--%>
<script>
    /** add active class and stay opened when selected */
    var url = window.location;

    // for sidebar menu entirely but not cover treeview
    $('ul.sidebar-menu a').filter(function() {
        return this.href == url;
    }).parent().siblings().removeClass('active').end().addClass('active');

    // for treeview
    $('ul.treeview-menu a').filter(function() {
        return this.href == url;
    }).parentsUntil(".sidebar-menu > .treeview-menu").siblings().removeClass('active').end().addClass('active');

</script>

<sec:authorize access="hasRole('ROLE_SUPERADMINISTRATOR')">
    <%--<%@include file="/pages/notification/script/script.jsp" %>--%>
    <%--js notification--%>
    <script src="${pageContext.request.contextPath}/resources/js/asset/app/notification.js"></script>
    <%--js socket--%>
    <script src="${pageContext.request.contextPath}/resources/js/socket/sockjs.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/socket/stomp.min.js"></script>
<input type="hidden" id="key" value="${pageContext.request.userPrincipal.name}">
    <script>
        $(document).ready(function () {

            var key = $("#key").val();

            try {
                connectToSocket(key);
            }catch (e){
                console.log(e);
            }
        })
    </script>
</sec:authorize>

</body>
</html>