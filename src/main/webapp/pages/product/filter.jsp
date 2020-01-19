<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 12/17/17
  Time: 10:36 PM
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
                        <h3 class="box-title">Product Filter</h3>
                        <small style="color: #f47342;">total results : &nbsp;${totalResult}</small>
                         <div class="box-tools">
                             <div class="row">
                                 <div class="dropdown pull-right">
                                     <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown"><i class="fa fa-cloud-download"></i> Download Report
                                         <span class="caret"></span></button>
                                    <ul class="dropdown-menu">
                                         <li><a href="${pageContext.request.contextPath}/report/product/filter/pdf?page=${currentpage}&name=${filterDTO.name}&trendingLevel=${filterDTO.trendingLevel}&subCategoryId=${filterDTO.subCategoryId}&greaterThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.greaterThanInStock}"/>&lessThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.lessThanInStock}"/>" target="_blank"><i class="fa fa-file-pdf-o"></i> PDF</a></li>
                                         <li class="divider"></li>
                                         <li><a href="${pageContext.request.contextPath}/report/product/filter/xls?page=${currentpage}&name=${filterDTO.name}&trendingLevel=${filterDTO.trendingLevel}&subCategoryId=${filterDTO.subCategoryId}&greaterThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.greaterThanInStock}"/>&lessThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.lessThanInStock}"/>" target="_blank"><i class="fa fa-file-excel-o"></i> XLS</a></li>
                                     </ul>
                                 </div>
                             </div>
                         </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                        <div class="row">
                            <div class="col-md-12">
                                <%@include file="/pages/product/filterForm.jsp" %>
                            </div>
                        </div>

                      <div class="row">
							<div class="col-md-12">
								<div class="table-responsive">
									<table class="table table-bordered table-hover table-striped">
										<thead>
											<tr>
												<th>SN</th>
												<th>Name</th>
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
													<td><a
														href="${pageContext.request.contextPath}/product/${product.productId}">${product.name}</a>
													</td>
													<td>${product.trendingLevel}</td>
													<td>${product.subCategoryInfo.name}</td>
													<td>${product.stockInfo.inStock}</td>
													<td><a
														href="${pageContext.request.contextPath}/product/edit?productId=${product.productId}"
														class="btn btn-danger"><i class="fa fa-edit"></i> edit
													</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
                    </div>
                    <c:if test="${fn:length(pagelist) gt 1}">

                        <div class="row">
                            <div class="col-xs-12">
                                <nav class="pull-right">
                                    <ul class="pagination">

                                        <c:if test="${currentpage > 1}">
                                            <li class="page-item">
												<a class="page-link"
                                                   href="${pageContext.request.contextPath}/product/filter?page=${currentpage-1}&name=${filterDTO.name}&trendingLevel=${filterDTO.trendingLevel}&subCategoryId=${filterDTO.subCategoryId}&greaterThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.greaterThanInStock}"/>&lessThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.lessThanInStock}"/>">
                                                Prev</a>
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

                                                    <li class="page-item">
                                                    <a class="page-link"
                                                   href="${pageContext.request.contextPath}/product/filter?page=${pagelist}&name=${filterDTO.name}&trendingLevel=${filterDTO.trendingLevel}&subCategoryId=${filterDTO.subCategoryId}&greaterThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.greaterThanInStock}"/>&lessThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.lessThanInStock}"/>">${pagelist}</a>
                                                    </li>

                                                </c:otherwise>

                                            </c:choose>
                                        </c:forEach>

                                        <c:if test="${currentpage + 1 <= lastpage}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="${pageContext.request.contextPath}/product/filter?page=${currentpage+1}&name=${filterDTO.name}&trendingLevel=${filterDTO.trendingLevel}&subCategoryId=${filterDTO.subCategoryId}&greaterThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.greaterThanInStock}"/>&lessThanInStock=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.lessThanInStock}"/>">Next</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </nav>
                            </div>
                        </div>

                    </c:if>

                </div>
            </div>
        </div>
    </section>
</div>
<%@include file="/pages/parts/footer.jsp" %>