<%--
  Created by IntelliJ IDEA.
  User: Shashwat
  Date: 1/13/2018
  Time: 8:29 PM
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
                        <h3 class="box-title">Edit Subcategory</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/category/update" method="post" modelAttribute="subCategory" >
                        <div class="box-body">

                                <div class="form-group">
                                    <label class="control-label">Category</label>
                                    <select name="categoryId" class="form-control select2" id="cityId">
                                        <option value="">select category</option>
                                        <c:forEach items="${categoryList}" var="category">
                                            <c:if test="${category.categoryId ne subcategory.subCategoryId}">
                                                <c:choose>
                                                    <c:when test="${category.categoryId eq subcategory.subCategoryId}">
                                                        <option value="${category.categoryId}" selected>${category.categoryName}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${category.categoryId}">${category.categoryName}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <p class="form-error">${subcategoryError.categoryId}</p>
                                </div>
                            <div class="form-group">
                                <label>Subcategory Name</label>
                                <input type="hidden" name="subCategoryId" value="${subcategory.subCategoryId}" />
                                <input type="text" name="name" class="form-control input-sm" value="${subcategory.name}">
                                <p class="error">${subcategoryError.name}</p>
                            </div>
                            <div class="form-group">
                                <label>Code</label> <input type="text" name="code"
                                                           class="form-control input-sm" required="required"
                                                           value="${subcategory.code}">
                                <p class="error">${subcategoryError.code}</p>
                            </div>
                                <div class="form-group">
                                    <label>Description</label> <input type="text" name="description"
                                                               class="form-control input-sm" required="required"
                                                               value="${subcategory.description}">
                                    <p class="error">${subcategoryError.description}</p>
                                </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Edit</button>
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
