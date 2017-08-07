<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/common/header.jsp" %>
<%@include file="../common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">

    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Tag Edit</li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-6">
            <div class="panel panel-default">
                <!--   <div class="panel-heading">TagInfo Tag</div> -->
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/tagInfo/update" method="POST"
                          modelAttribute="tagInfo"
                          enctype="multipart/form-data" id="registerform" novalidate="novalidate" class="col-lg-8">
                        <input type="hidden" name="orderId" value="${tagInfo.tagId}"/>

                        <div class="form-group" id="inputDiv">
                            <label class="lable"> Tag Name * </label>
                            <input type="text" value="${tagInfo.tagName }" class="form-control" name="tagName"
                                   required/>

                            <p class="error">${error.tagName}</p>

                            <label class="lable"> Tag Code* </label>
                            <input type="text" value="${tagInfo.code }" class="form-control" name="code" required/>

                            <p class="error">${error.code}</p>
                        </div>


                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Update Tag" class="btn btn-primary pull-right">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
