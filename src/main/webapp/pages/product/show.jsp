<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/24/17
  Time: 10:06 PM
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

    <section class="content-header">
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <c:forEach items="${categoryList}" var="category">
                    <li class="breadcrumb-item" aria-current="page"> ${category.categoryName}</li>
                </c:forEach>
                <li class="breadcrumb-item active" aria-current="page"> ${product.name}</li>
            </ol>
        </nav>
    </section>

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
                        <h3 class="box-title text-bold">
                            ${product.name}
                        </h3>
                        <div class="box-tools">
                            <a href="${pageContext.request.contextPath}/product/add"
                               class="btn btn-info btn-sm btn-flat pull-right"><span
                                    class="glyphicon glyphicon-plus-sign"></span> Add New Product
                            </a>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row">
                            <div class="col-md-12">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th>Unit</th>
                                        <th>Trend</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>${product.unitInfo.name}</td>
                                        <td>${product.trendingLevel}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                <div class="box-tools">
                                    <a href="${pageContext.request.contextPath}/item/add?productId=${product.productId}"
                                       class="btn btn-info btn-sm btn-flat pull-left"><span
                                            class="glyphicon glyphicon-plus-sign"></span> Add New Item
                                    </a>
                                </div>
                            </div>
                        </div>

                      <div class="row">
                          <div class="col-md-12">
                              <div class="table-responsive">
                                  <table id="table1" class="table table-bordered table-hover table-striped">
                                      <thead>
                                      <tr>
                                          <th>SN</th>
                                          <th>Code</th>
                                          <th>Tag</th>
                                          <th>Lot</th>
                                          <th>Cost Price</th>
                                          <th>Selling Price</th>
                                          <th>Expire On</th>
                                          <th>Instock</th>
                                          <th>Quantity</th>
                                          <th>Total Costing</th>
                                          <th>Total Selling</th>
                                          <th>Supplier</th>
                                      </tr>
                                      </thead>
                                      <tbody>
                                      <c:forEach var="item" items="${itemList}" varStatus="i">
                                          <tr>
                                              <td>${i.index + 1}</td>
                                              <td>${item.code}</td>
                                              <td>${item.tagInfo.name}</td>
                                              <td>${item.lotInfo.lot}</td>
                                              <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                    value="${item.costPrice}"/></td>
                                              <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                    value="${item.sellingPrice}"/></td>
                                              <td><fmt:formatDate pattern="MMM dd, yyyy" value="${item.expireDate}"/></td>
                                              <td><c:choose><c:when
                                                      test="${item.threshold gt item.inStock or item.threshold eq item.inStock}"><span
                                                      class="form-error">${item.inStock}</span></c:when><c:otherwise>${item.inStock}</c:otherwise></c:choose>
                                              </td>
                                              <td>${item.quantity}</td>
                                              <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                    value="${item.totalCost}"/></td>
                                              <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                    value="${item.totalSale}"/></td>
                                              <td>${item.vendorName}</td>
                                          </tr>
                                      </c:forEach>
                                      </tbody>
                                      <tfoot>
                                      <tr>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td></td>
                                          <td><strong>Total</strong></td>
                                          <td><strong>${product.stockInfo.inStock}</strong></td>
                                          <td><strong>${product.stockInfo.quantity}</strong></td>
                                          <td><strong><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                        value="${product.totalCosting}"/></strong></td>
                                          <td><strong><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                        value="${product.totalSale}"/></strong></td>
                                      </tr>
                                      </tfoot>
                                  </table>
                              </div>
                          </div>
                      </div>

                        <div class="row">
                            <div class="col-md-12">
                                <p class="well well-sm">${product.description}</p>
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


