<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/13/17
  Time: 10:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--    <sec:authorize access="hasRole('ROLE_SUPERADMIN')">
        <%@include file="/pages/dashboard/superadmin-index.jsp" %>
    </sec:authorize>--%>

    <sec:authorize access="hasRole('ROLE_SUPERADMINISTRATOR,ROLE_AUTHENTICATED')" >
        <%@include file="/pages/dashboard/superadmin-index.jsp" %>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR,ROLE_AUTHENTICATED')" >
        <%@include file="/pages/dashboard/admin-index.jsp" %>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_USER,ROLE_AUTHENTICATED')" >
        <%@include file="/pages/dashboard/user-index.jsp" %>
    </sec:authorize>

    <sec:authorize access="hasRole('ROLE_SYSTEM,ROLE_AUTHENTICATED')" >
        <%@include file="/pages/dashboard/system-index.jsp" %>
    </sec:authorize>
