<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/13/17
  Time: 10:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${not empty pageContext.request.userPrincipal}">

    <%--<c:if test="${pageContext.request.isUserInRole('system')}">
        <%@include file="/pages/parts/system-sidebar.jsp" %>
    </c:if>

    <c:if test="${pageContext.request.isUserInRole('SUPERADMIN')}">
        <%@include file="/pages/parts/superadmin-sidebar.jsp" %>
    </c:if>--%>

    <sec:authorize access="hasRole('SYSTEM')">
        <%@include file="/pages/parts/system-sidebar.jsp" %>
    </sec:authorize>

    <sec:authorize access="hasRole('SUPERADMIN')">
        <%@include file="/pages/parts/superadmin-sidebar.jsp" %>
    </sec:authorize>

</c:if>


