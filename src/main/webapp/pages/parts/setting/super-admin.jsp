<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/3/18
  Time: 1:42 PM
  To change this template use File | Settings | File Templates.
--%>

    <li><a href="${pageContext.request.contextPath}/country/list"><i class="fa fa-globe"></i> <span>Country</span></a></li>

    <li><a href="${pageContext.request.contextPath}/state/list"><i class="fa fa-map"></i><span>State</span></a></li>

    <li><a href="${pageContext.request.contextPath}/city/list"><i class="fa fa-map-marker"></i><span>City</span></a></li>

    <li><a href="${pageContext.request.contextPath}/category/list"><i class="fa fa-columns"></i> <span>Category</span></a></li>

    <li><a href="${pageContext.request.contextPath}/subcategory/list"><i class="fa fa-th-large"></i> <span>SubCategory</span></a></li>

    <li><a href="${pageContext.request.contextPath}/unit/list"><i class="fa fa-square"></i><span>Unit</span></a></li>

    <li><a href="${pageContext.request.contextPath}/tag/list"><i class="fa fa-tags"></i><span>Tag</span></a></li>

    <li><a href="${pageContext.request.contextPath}/fiscalyear/list"><i class="fa fa-calendar" aria-hidden="true"></i><span>Fiscal Year</span></a></li>

    <li class="treeview">
            <a href="#"><i class="fa fa-book"></i> <span>Employee</span>
                <span class="pull-right-container">
                    <i class="fa fa-angle-left pull-right"></i>
                </span>
            </a>
            <ul class="treeview-menu" style="display: none;">
                <li><a href="${pageContext.request.contextPath}/profile/list"><i class="fa fa-list"></i> List</a></li>
                <li><a href="${pageContext.request.contextPath}/qulification/list"><i class="fa fa-list"></i>Qualification</a></li>
                <li><a href="${pageContext.request.contextPath}/designation/list"><i class="fa fa-list"></i> Designation</a></li>
            </ul>
        </li>
