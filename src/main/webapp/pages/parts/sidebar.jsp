<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/13/17
  Time: 10:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%--<sec:authentication property="principal.authorities"/>--%>
<%--<sec:authentication property="principal.username" />--%>

<sec:authorize access="hasRole('ROLE_SUPERADMINISTRATOR,ROLE_AUTHENTICATED')">
    <%@include file="/pages/parts/superadmin-sidebar.jsp" %>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMINISTRATOR,ROLE_AUTHENTICATED')">
    <%@include file="/pages/parts/admin-sidebar.jsp" %>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_USER,ROLE_AUTHENTICATED')">
    <%@include file="/pages/parts/user-sidebar.jsp" %>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_SYSTEM,ROLE_AUTHENTICATED')">
    <%@include file="/pages/parts/system-sidebar.jsp" %>
</sec:authorize>


