<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/24/17
  Time: 7:11 PM
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
                        <h3 class="box-title">Add Category</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/category/save" method="post" modelAttribute="subcategory" >
                        <div class="box-body">

                            <div class="form-group">
                                <label class="control-label">Category</label>
                                <select name="categoryId" class="form-control select2" id="cityId">
                                    <option value="">select category</option>
                                    <c:forEach items="${categoryList}" var="category">
                                        <c:choose>
                                            <c:when test="${category.categoryId eq subcategory.categoryId}">
                                                <option value="${category.categoryId}" selected>${category.categoryName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${category.categoryId}">${category.categoryName}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                                <p class="form-error">${subcategoryError.categoryId}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Name</label>
                                <input type="text" class="form-control" value="${subcategory.name}" name="name" placeholder="Name">
                                <p class="form-error">${subcategoryError.name}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Code</label>
                                <input type="text" class="form-control" value="${subcategory.code}" name="code" placeholder="code">
                                <p class="form-error">${subcategoryError.code}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Description</label>
                                <input type="text" class="form-control" value="${subcategory.description}" name="description" placeholder="description">
                                <p class="form-error">${subcategoryError.description}</p>
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


