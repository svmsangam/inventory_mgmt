<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/header.jsp" %>
<style>
    #map {
        height: 350px;
        width: 680px;

    }

    #input {
        border-radius: 10px;
        -webkit-border-radius: 10px;
        -moz-border-radius: 10px;
    }

    .input {
        float: right;
        margin-left: 0px;
        margin-right: 900px;
        margin-top: 0px;

    }

    .lable {
        float: left;
    }

    #inputDiv {
        margin-top: 10px;

    }
</style>


<body>
<%@include file="../common/navbar.jsp" %>
<%@include file="../common/sidebar.jsp" %>


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
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Edit Owner Details</h1>
        </div>
    </div><!--/.row-->


    <div class="row">
        <div class="col-lg-12">
            <div class="break"></div>
            <form action="${pageContext.request.contextPath}/editProprietor?id=${storeId}" method="POST"
                  modelAttribute="proprietorDto" class="col-md-12">
                <input type="hidden" name="proprietorId" value="${proprietor.proprietorId}"/>

                <div class="input-group" id="inputDiv">
                    <label class="lable">First Name </label><br/>
                    <input type="text" name="firstName" value="${proprietor.firstName}" required/>
                    <p class="error">${error.firstName}</p>
                    <br/>
                </div>

                <div class="input-group" id="inputDiv">
                    <label class="lable">Middle Name </label><br/>
                    <input type="text" name="middleName" value="${proprietor.middleName}">
                    <p></p>
                    <br/>
                </div>

                <div class="input-group" id="inputDiv">
                    <label class="lable">Last Name </label><br/>
                    <input type="text" name="lastName" value="${proprietor.lastName}" required/>
                    <p class="error">${error.lastName}</p>
                    <br/>
                </div>

                <div class="input-group" id="inputDiv">
                    <label class="lable">Mobile No </label><br/>
                    <input type="text" name="mobileNo" value="${proprietor.mobileNo}" required/>
                    <p class="error">${error.mobileNo}</p>
                    <br/>
                </div>

                <div class="input-group" id="inputDiv">
                    <label class="lable">Email </label><br/>
                    <input type="email" name="email" value="${proprietor.email}" required/>
                    <p class="error">${error.email}</p>
                    <br/>
                </div>

                <div class="col-lg-12">
                    <div class="col-lg-4">
                        <div class="input-group" id="inputDiv">
                            <label class="lable">Temporary Address </label><br/>
                            <input type="text" class="form-control" name="temporaryAddress"
                                   value="${proprietor.temporaryAddress}" required/>
                            <p class="error">${error.temporaryAddress}</p>
                            <br/>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="input-group" id="inputDiv">
                            <label class="lable">Temporary City </label>
                            <select name="temporaryCity" class="form-control input-md" required="required">
                                <option value="">Select City</option>
                                <c:if test="${fn:length(cityList) gt 0}">
                                    <c:forEach var="cityInfo" oldItems="${cityList}">
                                        <c:choose>
                                            <c:when
                                                    test="${cityInfo.cityName == proprietor.temporaryCity}">
                                                <option value="${cityInfo.cityName}" selected>${cityInfo.cityName}
                                                    , ${cityInfo.stateName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${cityInfo.cityName}">${cityInfo.cityName}
                                                    , ${cityInfo.stateName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-lg-12">
                    <div class="col-lg-4">
                        <div class="input-group" id="inputDiv">
                            <label class="lable">Permanent Address </label><br/>
                            <input type="text" class="form-control" name="permanentAddress"
                                   value="${proprietor.permanentAddress}" required/>
                            <p class="error">${error.permanentAddress}</p>
                            <br/>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="input-group" id="inputDiv">
                            <label class="lable">Permanent City </label>
                            <select name="permanentCity" class="form-control input-md" required="required">
                                <option value="">Select City</option>
                                <c:if test="${fn:length(cityList) gt 0}">
                                    <c:forEach var="cityInfo" oldItems="${cityList}">
                                        <c:choose>
                                            <c:when
                                                    test="${cityInfo.cityName == proprietor.permanentCity}">
                                                <option value="${cityInfo.cityName}" selected>${cityInfo.cityName}
                                                    , ${cityInfo.stateName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${cityInfo.cityName}">${cityInfo.cityName}
                                                    , ${cityInfo.stateName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                    </div>
                </div>
                <br/> <br/> <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


                <div class="col-lg-12"><input type="submit" value="Update Owner Details"
                                              class="btn btn-sm btn-success"/></div>
                <br/><br/><br/>
            </form>
            </form>
        </div><!-- /.col-->
    </div><!-- /.row -->

</div><!--/.main-->

<%@include file="../common/footer.jsp" %>