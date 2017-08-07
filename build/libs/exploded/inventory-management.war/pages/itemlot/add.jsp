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
            <li class="active">ItemLot</li>
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
                <div class="panel-heading">Add Category</div>
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/itemlot/save"
                          method="POST" modelAttribute="itemlot"
                          enctype="multipart/form-data" id="registerform"
                          novalidate="novalidate" class="col-lg-8">


                        <div class="form-group" id="inputDiv">
                            <label class="lable"> Cost price </label> <input type="text"
                                                                             value="${itemInfo.costPrice }"
                                                                             class="form-control"
                                                                             name="categoryName" required/>

                            <%-- <p class="error">${error.categoryName}</p> --%>

                            <label class="lable">Selling Price </label> <input type="text"
                                                                               value="${itemInfo.sellingPrice }"
                                                                               class="form-control"
                                                                               name="sellingPrice" required/>

                            <%-- <p class="error">${error.code}</p> --%>
                            <label class="lable"> Available Quantity</label> <input type="text"
                                                                                    value="${itemInfo.availableQuantity }"
                                                                                    class="form-control"
                                                                                    name="availableQuantity" required/>

                            <label class="lable">Expiry Date </label> <input type="text"
                                                                             value="${itemInfo.expireDate }"
                                                                             class="form-control"
                                                                             name="expireDate" required/>
                        </div>


                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/> <input type="submit"
                                                               value="Add itemInfo" class="btn btn-primary pull-right">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
