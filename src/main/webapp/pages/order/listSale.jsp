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
                        <h3 class="box-title">Order List</h3>
                        <div class="box-tools">
                            <button class="btn btn-success btn-sm btn-flat margin-r-5 filter" data-toggle="collapse"
                                    data-target="#filter"><span class="glyphicon glyphicon-filter"></span> filter
                            </button>

                            <a href="${pageContext.request.contextPath}/order/sale/add"
                               class="btn btn-info btn-sm btn-flat pull-right"><span
                                    class="glyphicon glyphicon-plus-sign"></span> New Order</a>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                        <div class="row collapse" id="filter">
                            <div class="col-md-12">
                                <%@include file="/pages/order/filterForm.jsp" %>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-lg-12">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover table-striped">
                                        <thead>
                                        <tr>
                                            <th>Order No</th>
                                            <th>Customer Name</th>
                                            <th>Total Cost</th>
                                            <th>Order Date</th>
                                            <th>Delivery Date</th>
                                            <th>Status</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach items="${orderList}" var="order">

                                            <tr>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/order/sale/${order.orderId}">#${order.orderNo}</a>
                                                </td>
                                                <td>${order.clientInfo.name}</td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      groupingUsed="true"
                                                                      value="${order.grandTotal}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${order.orderDate}"/></td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${order.deliveryDate}"/></td>
                                                <td>
                                                        <%--label css for track  status--%>
                                                        <%--<span class="label label-primary">Pending</span>
                                                        <span class="label label-teal">Accepted</span>
                                                        <span class="label label-warning">Packed</span>
                                                        <span class="label label-info">Shipped</span>
                                                        <span class="label label-success">Delivered</span>
                                                        <span class="label label-gray">Canceled</span>--%>

                                                    <c:if test="${order.saleTrack eq 'PENDDING'}">
                                                        <span class="label label-primary">Pending</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'ACCEPTED'}">
                                                        <span class="label label-default">Accepted</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'PACKED'}">
                                                        <span class="label label-warning">Packed</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'SHIPPED'}">
                                                        <span class="label label-info">Shipped</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'DELIVERED'}">
                                                        <span class="label label-success">Delivered</span>
                                                    </c:if>

                                                    <c:if test="${order.saleTrack eq 'CANCEL'}">
                                                        <span class="label label-danger">Canceled</span>
                                                    </c:if>

                                                </td>
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

                                                <a href="${pageContext.request.contextPath}/order/sale/list?pageNo=${currentpage-1}"
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
                                                                             href="${pageContext.request.contextPath}/order/sale/list?pageNo=${pagelist}">${pagelist}</a>
                                                    </li>

                                                </c:otherwise>

                                            </c:choose>
                                        </c:forEach>

                                        <c:if test="${currentpage + 1 <= lastpage}">
                                            <li class="page-item">
                                                <a class="page-link"
                                                   href="${pageContext.request.contextPath}/order/sale/list?pageNo=${currentpage+1}">Next</a>
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


<script>
    $(document).ready(function () {

        $(".choose1").select2({
            ajax: {
                url: '${pageContext.request.contextPath}/client/customer/search',
                dataType: 'json',
                headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                delay: 250,
                type: 'GET',
                data: function (params) {
                    return {
                        term: params.term, // search term
                        /* page: params.page*/
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    var arr = []
                    $.each(data.detail, function (index, value) {

                        if (value.companyName === null || "" === value.companyName) {

                            arr.push({
                                id: value.clientId,
                                text: value.name + ' - ' + value.mobileNumber
                            })
                        } else {
                            arr.push({
                                id: value.clientId,
                                text: value.companyName + ' - ' + value.mobileNumber
                            })
                        }
                    })


                    return {
                        results: arr/*,
                         pagination: {
                         more: (params.page * 1) < 2
                         }*/
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) {
                return markup;
            },
            minimumInputLength: 1,
            placeholder: "Search Customer by Name or Mobile No"
        });
    });
</script>
