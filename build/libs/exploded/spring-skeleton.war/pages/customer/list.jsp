<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="../common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Customer</li>
            <li></li>
        </ol>
    </div>
    <!--/.row-->
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
                    <div class="panel-heading">
                        Customer List <a
                            href="${pageContext.request.contextPath}/clientInfo/add"
                            style="float: right; margin-bottom: 8px;">
                        <button class="btn btn-primary btn-sm">Add New Customer</button>
                    </a>
                    </div>
                    <div class="panel-body">
                        <table class="table">
                            <thead>
                            <tr>
                                <th><c:choose><c:when test="${sort == 'id'}"><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=id&direction=${direction}">s.n.</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=id&direction=desc">s.n.</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort == 'alias'}"><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=alias&direction=${direction}">Alias</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=alias&direction=desc">Alias</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort == 'name'}"><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=name&direction=${direction}">Name</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=name&direction=desc">Name</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort == 'address'}"><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=address&direction=${direction}">Address</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=address&direction=desc">Address</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort == 'contact'}"><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=contact&direction=${direction}">Contact</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=contact&direction=desc">Contact</a></c:otherwise></c:choose>
                                </th>
                                <th><c:choose><c:when test="${sort == 'email'}"><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=email&direction=${direction}">Email</a></c:when><c:otherwise><a
                                        href="${pageContext.request.contextPath}/clientInfo/list?pageNo=${currentpage}&sort=email&direction=desc">Email</a></c:otherwise></c:choose>
                                </th>
                                <th>Account Number</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="clientInfo" items="${customerList}" varStatus="i">
                                <tr>
                                    <td>${i.index+1}</td>
                                    <td>${clientInfo.alias}</td>
                                    <td>${clientInfo.name}</td>
                                    <td>${clientInfo.address}</td>
                                    <td>${clientInfo.contact}</td>
                                    <td>${clientInfo.email}</td>
                                    <td id="account${i.index+1}" class="account"><c:if
                                            test="${clientInfo.accountHeadDTO ne null}">${clientInfo.accountHeadDTO.accountNumber}</c:if><c:if
                                            test="${clientInfo.accountHeadDTO eq null}"><a href="javascript:void(0);"
                                                                                           class="btn btn-success generate"
                                                                                           associateId="${clientInfo.customerid}"
                                                                                           clientType="CUSTOMER"
                                                                                           url="${pageContext.request.contextPath}/account/add"
                                                                                           result="account${i.index+1}">generate</a></c:if>
                                    </td>

                                    <td><a
                                            href="${pageContext.request.contextPath}/clientInfo/edit?customerId=${clientInfo.customerid}"
                                            class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
                                        Edit </a> <a
                                            href="${pageContext.request.contextPath}/clientInfo/delete?customerId=${clientInfo.customerid}"
                                            class="btn btn-danger btn-xs"
                                            onclick="return confirm('Are you sure, you want to DELETE?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>


                    <c:if test="${fn:length(pagelist) gt 1}">

                        <style>
                            .mypgactive {
                                background: #a51234 !important;
                                cursor: pointer !important;
                                color: white !important;
                            }
                        </style>

                        <div class="pagination-block col-md-10  pull-left">
                            <ul
                                    class="pagination pagination-sm no-margin pagingclass col-md-10">
                                <li><c:if test="${currentpage > 1}">
                                    <a href="/app/oldItem?pageNo=${currentpage-1}"
                                       class="pn prev mypgactive">Prev</a>
                                </c:if></li>

                                <c:forEach var="pagelist" items="${pagelist}">
                                    <li><c:choose>

                                        <c:when test="${pagelist == currentpage}">

                                            <span class="active">${pagelist}</span>

                                        </c:when>
                                        <c:otherwise>

                                            <a href="/app/oldItem?pageNo=${pagelist}" class="mypgactive">${pagelist}</a>

                                        </c:otherwise>

                                    </c:choose></li>
                                </c:forEach>
                                <li><c:if test="${currentpage + 1 <= lastpage}">
                                    <a href="/app/oldItem?pageNo=${currentpage+1}"
                                       class="pn next mypgactive">Next</a>
                                </c:if></li>
                            </ul>
                        </div>

                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>

<script>
    $(document).ready(function () {
        $(".generate").click(function () {

            console.log("generating");
            var url = $(this).attr("url");
            var associateId = $(this).attr("associateId");
            var clientType = $(this).attr("clientType");
            var result = $(this).attr("result");

            console.log("td " + result);
            generateAccount(url, associateId, clientType, $(this), result);

        })
    })
</script>