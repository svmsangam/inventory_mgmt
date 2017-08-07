<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>
<%@include file="../common/sidebar.jsp" %>

<script src="../resources/landing/js/select2.full.js"></script>
<link href="../resources/css/select2.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Store</li>
        </ol>
    </div><!--/.row-->

    <div class="row">

        <div class="col-lg-12">
            <h1 class="page-header">Business Details</h1>
        </div>
    </div><!--/.row-->


    <div class="row">
        <div class="col-lg-12">


            <table class="table table-striped table-bordered">
                <tr>
                    <th>Store Information</th>
                </tr>
                <tr>
                    <th>Store Name :</th>
                    <th>${storeInfo.storeName}</th>
                </tr>
                <tr>
                    <th>Pan Number :</th>
                    <th>${storeInfo.panNumber}</th>
                </tr>
                <tr>
                    <th>Registration Number :</th>
                    <th>${storeInfo.registrationNumber}</th>
                </tr>
                <tr>
                    <th>Mobile Number :</th>
                    <th>${storeInfo.mobileNumber}</th>
                </tr>
                <tr>
                    <th>LandLine :</th>
                    <th>${storeInfo.landLine}</th>
                </tr>

                <tr>
                    <th>AccountNo :</th>
                    <c:if test="${storeInfo.accountHeadDTO ne null}">
                        <th>${storeInfo.accountHeadDTO.accountNumber}</th>
                    </c:if>
                    <c:if test="${storeInfo.accountHeadDTO eq null}">
                        <th id="generateTd"><a href="#" url="${pageContext.request.contextPath}/account/add"
                                               myid="${storeInfo.storeId}" id="generate">generate</a></th>
                    </c:if>
                </tr>
            </table>
            <br/><br/><br/><br/>

            <table class="table table-striped table-bordered">
                <tr>
                    <th>Owner Information</th>
                </tr>
                <tr>
                    <th>First Name :</th>
                    <th>${storeInfo.proprieterDto.firstName}</th>
                </tr>
                <tr>
                    <th>Middle Name :</th>
                    <th>${storeInfo.proprieterDto.middleName}</th>
                </tr>
                <tr>
                    <th>Last Name :</th>
                    <th>${storeInfo.proprieterDto.lastName}</th>
                </tr>
                <tr>
                    <th>Mobile No :</th>
                    <th>${storeInfo.proprieterDto.mobileNo}</th>
                </tr>
                <tr>
                    <th>Email :</th>
                    <th>${storeInfo.proprieterDto.email}</th>
                </tr>
                <tr>
                    <th>Temporary Address:</th>
                    <th>${storeInfo.proprieterDto.temporaryAddress}</th>
                </tr>
                <tr>
                    <th>Permanent Address :</th>
                    <th>${storeInfo.proprieterDto.permanentAddress}</th>
                </tr>
            </table>
            <br/><br/><br/><br/>

            <table class="table table-striped table-bordered">
                <tr>
                    <th>Service Plans</th>
                    <th>
                        <a href="${pageContext.request.contextPath}/addBusinessServicePlanForSore?storeId=${storeInfo.storeId}"
                           class=" btn btn-sm">reassign</a></th>
                </tr>
                <c:forEach var="service" items="${permissions}" varStatus="i">
                    <tr class="col-xs-3" style="margin-bottom: 10px;">
                        <td>${i.index + 1}</td>
                        <td>${service}</td>
                    </tr>
                </c:forEach>


            </table>
        </div><!-- /.col-->
    </div><!-- /.row -->

</div>
<!--/.main-->generate

<%@include file="../common/footer.jsp" %>

<script>
    $(document).ready(function () {
        $(document).on("click", "#generate", function () {
            var url = $(this).attr("url");
            var myid = $(this).attr("myid");

            generateAccountForAdmin(url, myid, "STORE");

        });
    });
</script>