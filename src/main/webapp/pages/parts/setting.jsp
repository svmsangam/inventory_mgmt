<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/3/18
  Time: 1:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<ul class="sidebar-menu tree" data-widget="tree">
    <li class="header">Extra Settings</li>
    <!-- Optionally, you can add icons to the links -->
    <sec:authorize access="hasRole('ROLE_SUPERADMINISTRATOR')">

        <%@include file="/pages/parts/setting/super-admin.jsp" %>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">

        <%@include file="/pages/parts/setting/admin.jsp" %>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_SYSTEM')">

        <%@include file="/pages/parts/setting/system.jsp" %>
    </sec:authorize>

    <sec:authorize access="hasAnyRole('ROLE_USER' , 'ROLE_DASHBOARD')">
        <%@include file="/pages/parts/setting/user.jsp" %>
    </sec:authorize>

</ul>
