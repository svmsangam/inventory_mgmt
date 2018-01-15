<%--
  Created by IntelliJ IDEA.
  User: Shashwat
  Date: 1/11/2018
  Time: 9:00 PM
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
                        <h3 class="box-title">Edit Category</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/category/update" method="post" modelAttribute="category" >
                        <div class="box-body">
                            <%--<div class="form-group">
                                <input type="hidden" name="categoryId" value="${category.categoryId}" />
                            </div>--%>
                            <div class="form-group">
                                <label>Category Name</label><input type="hidden" name="categoryId" value="${category.categoryId}" /> <input type="text" name="name"
                                                                  class="form-control input-sm" value="${category.name}">
                                <p class="error">${error.name}</p>
                            </div>
                            <div class="form-group">
                                <label>Code</label> <input type="text" name="code"
                                                                 class="form-control input-sm" required="required"
                                                                 value="${category.code}">
                                <p class="error">${error.code}</p>
                            </div>
                            <div class="form-group">
                                <label>Description </label> <input type="text" name="description"
                                                               class="form-control input-sm" required="required"
                                                               value="${category.description}">
                                <p class="error">${error.description}</p>
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


