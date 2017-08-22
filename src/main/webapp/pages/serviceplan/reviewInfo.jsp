<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/common/header.jsp" %>
<%@include file="/pages/common/businessownernavbar.jsp" %>
<%@include file="/pages/common/businessownersidebar.jsp" %>

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
            <li class="active">Icons</li>
        </ol>
    </div><!--/.row-->

    <div class="row">

        <div class="col-lg-12">
            <h1 class="page-header">Permissions Review</h1>
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
                    <th>User Information</th>
                </tr>

                <tr>
                    <th>UserName :</th>
                    <th>${user.username}</th>
                </tr>
                <tr>
                    <th>User Type :</th>
                    <th>${user.userType}</th>
                </tr>

            </table>
            <br/><br/><br/><br/>

            <table class="table table-striped table-bordered">
                <tr>
                    <th>Service Plans</th>
                </tr>
                <c:forEach var="service" oldItems="${permissions}">
                    <tr>
                        <td>${service.permission}</td>
                    </tr>
                </c:forEach>


            </table>
            <br/><br/>

            <button class="btn btn-success">OK</button>
            <br/><br/>

        </div><!-- /.col-->
    </div><!-- /.row -->

</div>
<!--/.main-->

<%@include file="../common/footer.jsp" %>