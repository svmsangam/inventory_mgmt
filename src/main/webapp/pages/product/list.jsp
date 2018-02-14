<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/24/17
  Time: 9:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

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
            <div class="col-xs-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Product List</h3>
                        <div class="box-tools">
                            <a href="${pageContext.request.contextPath}/item/add" class="btn btn-primary btn-sm btn-flat margin-r-5"><span class="glyphicon glyphicon-plus-sign"></span> Add Item</a>
                            <a href="${pageContext.request.contextPath}/product/add" class="btn btn-info btn-sm btn-flat pull-right"><span class="glyphicon glyphicon-plus-sign"></span> Add Product</a>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th>SN</th>
                                            <th>Name</th>
                                            <th>Code</th>
                                            <th>Trend</th>
                                            <th>Category</th>
                                            <th>Instock</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="product" items="${productList}" varStatus="i">
                                            <tr>
                                                <td>${i.index + 1}</td>
                                                <td><a href="${pageContext.request.contextPath}/product/${product.productId}">${product.name}</a></td>
                                                <td>${product.code}</td>
                                                <td>${product.trendingLevel}</td>
                                                <td>${product.subCategoryInfo.name}</td>
                                                <td>${product.stockInfo.inStock}</td>
                                                <td><a href="${pageContext.request.contextPath}/product/edit?productId=${product.productId}" class="btn btn-danger"><i class="fa fa-edit"></i> edit </a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <c:if test="${fn:length(pagelist) gt 1}">

                                    <div class="col-xs-12">
                                        <nav class="pull-right">
                                            <ul class="pagination">

                                                <c:if test="${currentpage > 1}">
                                                    <li class="page-item">

                                                        <a href="${pageContext.request.contextPath}/product/list?pageNo=${currentpage-1}"
                                                           class="page-link">Prev</a>
                                                    </li>
                                                </c:if>

                                                <c:forEach var="pagelist" items="${pagelist}">
                                                    <c:choose>
                                                        <c:when test="${pagelist == currentpage}">

                                                            <li class="page-item active">
                                                  <span class="page-link">
                                                    ${pagelist}
                                                    <span class="sr-only">(current)</span>
                                                  </span>
                                                            </li>

                                                        </c:when>
                                                        <c:otherwise>

                                                            <li class="page-item"><a class="page-link"
                                                                                     href="${pageContext.request.contextPath}/product/list?pageNo=${pagelist}">${pagelist}</a>
                                                            </li>

                                                        </c:otherwise>

                                                    </c:choose>
                                                </c:forEach>

                                                <c:if test="${currentpage + 1 <= lastpage}">
                                                    <li class="page-item">
                                                        <a class="page-link"
                                                           href="${pageContext.request.contextPath}/product/list?pageNo=${currentpage+1}">Next</a>
                                                    </li>
                                                </c:if>
                                            </ul>
                                        </nav>
                                    </div>

                                </c:if>
                            </div>
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>
    </section>
</div>

<%@include file="/pages/parts/footer.jsp" %>

