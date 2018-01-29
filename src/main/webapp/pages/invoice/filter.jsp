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
                        <h3 class="box-title">Invoice Filter</h3>
                        <small style="color: #f47342;">total results : &nbsp;${totalResult}</small>
                         <div class="box-tools">
                             <div class="row">
                                 <div class="dropdown pull-right">
                                     <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown"><i class="fa fa-cloud-download"></i> Download Report
                                         <span class="caret"></span></button>
                                     <ul class="dropdown-menu">
                                         <li><a href="${pageContext.request.contextPath}/report/ledger/filter/pdf?pageNo=${currentpage}&from=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.from}"/>&to=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.to}"/>&fiscalYearId=${filterDTO.fiscalYearId}&clientId=${filterDTO.clientId}&amountGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountGt}"/>&amountLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountLt}"/>&receivableGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableGt}"/>&receivableLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableLt}"/>"><i class="fa fa-file-pdf-o"></i> PDF</a></li>
                                         <li class="divider"></li>
                                         <li><a href="${pageContext.request.contextPath}/report/ledger/filter/xls?pageNo=${currentpage}&from=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.from}"/>&to=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.to}"/>&fiscalYearId=${filterDTO.fiscalYearId}&clientId=${filterDTO.clientId}&amountGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountGt}"/>&amountLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountLt}"/>&receivableGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableGt}"/>&receivableLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableLt}"/>"><i class="fa fa-file-excel-o"></i> XLS</a></li>
                                     </ul>
                                 </div>
                             </div>
                         </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                        <div class="row">
                            <div class="col-md-12">
                                <%@include file="/pages/invoice/filterForm.jsp" %>
                            </div>
                        </div>

                        <div class="table-responsive">
                            <table class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>Invoice No</th>
                                    <th>Fiscal Year</th>
                                    <th>Customer Name</th>
                                    <th>Total Amount</th>
                                    <th>Amount Recievable</th>
                                    <th>Invoice Date</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${invoiceList}" var="invoice">

                                    <tr>
                                        <td><a href="${pageContext.request.contextPath}/invoice/${invoice.invoiceId}">#${invoice.invoiceNo}</a></td>
                                        <td><c:if test="${invoice.fiscalYearInfo ne null}">${invoice.fiscalYearInfo.title}</c:if></td>
                                        <td>${invoice.orderInfo.clientInfo.name}</td>
                                        <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.totalAmount}"/></td>
                                        <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${invoice.receivableAmount}"/></td>
                                        <td><fmt:formatDate pattern="MMM dd, yyyy" value="${invoice.invoiceDate}"/></td>
                                    </tr>

                                </c:forEach>

                                </tbody>
                            </table>
                        </div>
                    </div>

                    <c:if test="${fn:length(pagelist) gt 1}">

                        <div class="col-xs-12">
                            <nav class="pull-right">
                                <ul class="pagination">

                                    <c:if test="${currentpage > 1}">
                                        <li class="page-item">

                                            <a href="${pageContext.request.contextPath}/invoice/filter?pageNo=${currentpage-1}&from=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.from}"/>&to=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.to}"/>&fiscalYearId=${filterDTO.fiscalYearId}&clientId=${filterDTO.clientId}&amountGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountGt}"/>&amountLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountLt}"/>&receivableGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableGt}"/>&receivableLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableLt}"/>"
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
                                                                         href="${pageContext.request.contextPath}/invoice/filter?pageNo=${pagelist}&from=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.from}"/>&to=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.to}"/>&fiscalYearId=${filterDTO.fiscalYearId}&clientId=${filterDTO.clientId}&amountGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountGt}"/>&amountLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountLt}"/>&receivableGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableGt}"/>&receivableLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableLt}"/>">${pagelist}</a>
                                                </li>

                                            </c:otherwise>

                                        </c:choose>
                                    </c:forEach>

                                    <c:if test="${currentpage + 1 <= lastpage}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/invoice/filter?pageNo=${currentpage+1}&from=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.from}"/>&to=<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.to}"/>&fiscalYearId=${filterDTO.fiscalYearId}&clientId=${filterDTO.clientId}&amountGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountGt}"/>&amountLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountLt}"/>&receivableGt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableGt}"/>&receivableLt=<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableLt}"/>">Next</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
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