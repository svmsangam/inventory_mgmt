<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/24/17
  Time: 9:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

    <!-- Main content -->
    <section class="content">

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissable">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                <strong>${message}</strong>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissable">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                <strong>${error}</strong>
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Add Item</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/item/saveitem" method="post" modelAttribute="item">
                        <div class="box-body">

                            <div class="form-group">
                                <label class="control-label">Product</label>
                                <select name="productId" class="form-control select2">
                                    <option value="">select product</option>
                                    <c:forEach items="${productList}" var="product">
                                        <c:choose>
                                            <c:when test="${product.productId eq item.productId}">
                                                <option value="${product.productId}" selected>${product.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${product.productId}">${product.name}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                                <p class="form-error">${itemError.productId}</p>
                            </div>

                            <jsp:include page="addform.jsp"/>

                        </div>
                        <!-- /.box-body -->
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
                <!-- /.box -->
            </div>
        </div>

    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<div class="modal fade" id="modal-addtag">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add New Tag</h4>
            </div>
            <div class="modal-body">
                <div class="box-body">

                    <div class="form-group">
                        <label class="control-label">Name</label>
                        <input type="text" id="tagName" class="form-control addTagFormClear" name="name"
                               placeholder="Name">
                        <p class="form-error tagFormError" id="tagNameError"></p>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Code</label>
                        <input type="text" id="tagCode" class="form-control addTagFormClear" name="code"
                               placeholder="code">
                        <p class="form-error tagFormError" id="tagCodeError"></p>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-danger pull-left closeAdd" data-dismiss="modal">Close
                    </button>
                    <button type="button" class="btn btn-primary btn-sm  btn-flat pull-right addNewTag"
                            url="${pageContext.request.contextPath}/api/tag/save"><span
                            class="glyphicon glyphicon-save"></span>
                        Save Changes
                    </button>
                </div>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


<%@include file="/pages/parts/footer.jsp" %>

<%@include file="/pages/item/script.jsp" %>

<script src="${pageContext.request.contextPath}/resources/js/asset/app/numberValidation.js"></script>


