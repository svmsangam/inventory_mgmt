<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/common/header.jsp" %>
<%@include file="/pages/common/businessownernavbar.jsp" %>
<%@include file="/pages/common/businessownersidebar.jsp" %>


<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Home</li>

        </ol>
    </div><!--/.row-->

    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>
    <div class="container-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">Invoice List <a href="${pageContext.request.contextPath}/app/addinvoice">
                        <button class="btn btn-primary btn-sm pull-right">Create New Invoice</button>
                    </a>
                    </div>
                    <div class="panel-body">
                        <table id="example" class="cell-border" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th>Invoice No</th>
                                <th>Customer Name</th>
                                <th>Discount</th>
                                <th>Total Amount</th>
                                <th>Invoice Date</th>
                                <th>Due Date</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="invoiceInfo" oldItems="${invoiceList}">
                                <tr>
                                    <td>${invoiceInfo.invoiceNo}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/app/showinvoice?invoiceId=${invoiceInfo.invoiceId}">${invoiceInfo.customerName}</a>
                                    </td>
                                    <td>${invoiceInfo.discount}</td>
                                    <td>${invoiceInfo.totalCost}</td>
                                    <td>${invoiceInfo.invoiceDate}</td>
                                    <td>${invoiceInfo.dueDate}</td>
                                    <td><%-- <a href="${pageContext.request.contextPath}/app/editinvoice?invoiceId=${invoiceInfo.invoiceId}"
										class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
											Edit </a> --%> <a
                                            href="${pageContext.request.contextPath}/app/deleteinvoice?invoiceId=${invoiceInfo.invoiceId}"
                                            class="btn btn-danger btn-xs"
                                            onclick="return confirm('Are you sure, you want to DELETE?')"> Delete </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div><!--/.row-->
    </div>
</div>
<%@include file="../common/footer.jsp" %>
<script>
    $(document).ready(function () {
        $('#example').DataTable({
            "pagingType": "full_numbers"
        });
    });
</script>	