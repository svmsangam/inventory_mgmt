<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 6/29/17
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
            <li class="active">Add Customer</li>
        </ol>
    </div>
    <!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-6">
            <div class="panel panel-default">
                <div class="panel-heading">Add Customer</div>
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/clientInfo/save"
                          method="POST" modelAttribute="clientInfo"
                          enctype="multipart/form-data" id="registerform"
                          novalidate="novalidate" class="col-lg-8">


                        <div class="form-group">
                            <label class="lable">Name *</label>
                            <input type="text" value="${clientInfo.name }" class="form-control" name="name" required/>

                            <p class="error">${error.name}</p>
                        </div>

                        <div class="form-group">
                            <label class="lable">Contact *</label>
                            <input type="text" value="${clientInfo.contact }" class="form-control" name="contact"
                                   required/>

                            <p class="error">${error.contact}</p>
                        </div>

                        <div class="form-group">
                            <label class="lable">Address *</label>
                            <input type="text" value="${clientInfo.address }" class="form-control" name="address"
                                   required/>

                            <p class="error">${error.address}</p>
                        </div>

                        <div class="form-group">
                            <label class="lable">Email *</label>
                            <input type="text" value="${clientInfo.email }" class="form-control" name="email" required/>

                            <p class="error">${error.email}</p>
                        </div>

                        <div class="form-group">
                            <label class="lable">Alias *</label>
                            <input type="text" value="${clientInfo.alias }" class="form-control" name="alias" required/>

                            <p class="error">${error.alias}</p>
                        </div>


                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/> <input type="submit"
                                                               value="Add Customer" class="btn btn-primary pull-right">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
