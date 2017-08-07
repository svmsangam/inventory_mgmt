<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 6/29/17
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="../common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">unitInfo</li>
            <li>

            </li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>


    <div class="row">

        <c:if test="${unitInfo ne null}">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-heading">Show Unit<a href="${pageContext.request.contextPath}/unitInfo/add"
                                                           style="float:right; margin-bottom:8px;">
                        <button class="btn btn-primary btn-sm">Add New Unit</button>
                    </a>
                    </div>
                    <div class="panel-body">

                        <c:if test="${unitInfo.name ne null}">
                            <div class="form-group" id="inputDiv">
                                <label class="lable">Unit Name :</label>
                                <span class="form-control">${unitInfo.name }</span>
                            </div>
                        </c:if>

                        <c:if test="${unitInfo.code ne null}">
                            <div class="form-group" id="inputDiv">
                                <label class="lable">Unit Code :</label>
                                <span class="form-control">${unitInfo.code }</span>
                            </div>
                        </c:if>

                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div>
<%@include file="../common/footer.jsp" %>

