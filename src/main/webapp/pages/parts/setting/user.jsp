<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/3/18
  Time: 1:48 PM
  To change this template use File | Settings | File Templates.
--%>

<sec:authorize ifAnyGranted=" ROLE_USER , SUBCATEGORY_VIEW">
    <li><a href="${pageContext.request.contextPath}/subcategory/list"><i class="fa fa-th-large"></i> <span>Category</span></a></li>
</sec:authorize>

<sec:authorize ifAnyGranted=" ROLE_USER , UNIT_VIEW">
    <li><a href="${pageContext.request.contextPath}/unit/list"><i class="fa fa-square"></i><span>Unit</span></a></li>
</sec:authorize>

<sec:authorize ifAnyGranted=" ROLE_USER , TAG_VIEW">
    <li><a href="${pageContext.request.contextPath}/tag/list"><i class="fa fa-tags"></i><span>Tag</span></a></li>
</sec:authorize>


    <li><a href="${pageContext.request.contextPath}/country/list"><i class="fa fa-globe"></i> <span>Country</span></a></li>

    <li><a href="${pageContext.request.contextPath}/state/list"><i class="fa fa-map"></i><span>State</span></a></li>

    <li><a href="${pageContext.request.contextPath}/city/list"><i class="fa fa-map-marker"></i><span>City</span></a></li>

