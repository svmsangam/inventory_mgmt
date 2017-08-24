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

        <div class="row">
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Add Product</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/product/save" method="post" modelAttribute="product" >
                        <div class="box-body">

                            <div class="form-group">
                                <label class="control-label">SubCategory</label>
                                <select name="subCategoryId" class="form-control select2" id="subcategoryId">
                                    <option value="">select subcategory</option>
                                    <c:forEach items="${subcategoryList}" var="subcategory">
                                        <c:choose>
                                            <c:when test="${subcategory.subCategoryId eq product.subCategoryId}">
                                                <option value="${subcategory.subCategoryId}" selected>${subcategory.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${subcategory.subCategoryId}">${subcategory.name}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                                <p class="form-error">${productError.subCategoryId}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Name</label>
                                <input type="text" class="form-control" value="${product.name}" name="name" placeholder="Name">
                                <p class="form-error">${productError.name}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Code</label>
                                <input type="text" class="form-control" value="${product.code}" name="code" placeholder="code">
                                <p class="form-error">${productError.code}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Trend</label>
                                <select name="trendingLevel" class="form-control select2" id="trendingLevel">
                                    <option value="">select trend</option>
                                    <c:forEach items="${trendingtList}" var="trend">
                                        <c:choose>
                                            <c:when test="${trend eq product.trendingLevel}">
                                                <option value="${trend}" selected>${trend}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${trend}">${trend}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                                <p class="form-error">${productError.trendingLevel}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Unit</label>
                                <select name="unitId" class="form-control select2" id="unitId">
                                    <option value="">select unit</option>
                                    <c:forEach items="${unitList}" var="unit">
                                        <c:choose>
                                            <c:when test="${unit.unitId eq product.unitId}">
                                                <option value="${unit.unitId}" selected>${unit.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${unit.unitId}">${unit.name}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                                <p class="form-error">${productError.unitId}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Description</label>
                                <input type="text" class="form-control" value="${product.description}" name="description" placeholder="code">
                                <p class="form-error">${productError.description}</p>
                            </div>

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

<%@include file="/pages/parts/footer.jsp" %>

<script>
    $(function () {
        $('.select2').select2()
    })
</script>



