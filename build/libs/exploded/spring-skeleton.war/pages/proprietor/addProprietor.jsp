<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/form-elements.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/multistepform/assets/css/select2.css">

<!--link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/favicon.png"-->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
      href="${pageContext.request.contextPath}/resources/multistepform/assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
      href="${pageContext.request.contextPath}/resources/multistepform/ssets/ico/apple-touch-icon-57-precomposed.png">

<%@include file="../common/header.jsp" %>
<%@include file="../common/navbar.jsp" %>
<%@include file="../common/sidebar.jsp" %>


<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Home</li>
            <li>

            </li>
        </ol>
    </div><!--/.row-->
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">Add Owner Details</h1>
        </div>
    </div><!--/.row-->

    <div class="container-wrapper">

        <form action="${pageContext.request.contextPath}/addproprietor?id=${storeId}" method="POST"
              modelAttribute="proprietorDto">

            <div class="f1-steps">
                <div class="f1-progress">
                    <div class="f1-progress-line" data-now-value="16.66" data-number-of-steps="3"
                         style="width: 16.66%;"></div>
                </div>
                <div class="f1-step">
                    <div class="f1-step-icon">
                        <svg class="glyph stroked app window with content">
                            <use xlink:href="#stroked--app-window-with-content"/>
                        </svg>
                    </div>

                    <p>Business Details</p>
                </div>

                <div class="f1-step active">
                    <div class="f1-step-icon">
                        <svg class="glyph stroked male user">
                            <use xlink:href="#stroked-male-user"/>
                        </svg>
                    </div>
                    <p>Owner Details</p>
                </div>
                <div class="f1-step">
                    <div class="f1-step-icon">
                        <svg class="glyph stroked folder">
                            <use xlink:href="#stroked-folder"/>
                        </svg>
                    </div>
                    <p>Service Plan</p>
                </div>
            </div>

            <div class="col-lg-12">
                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">First Name </label>
                        <input type="text" class="form-control" name="firstName" value="${proprietor.firstName}"
                               required/>
                        <p class="error">${error.firstName}</p>
                        <br/>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Middle Name </label>
                        <input type="text" class="form-control" name="middleName" value="${proprietor.middleName}">
                        <p></p>
                        <br/>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Last Name </label>
                        <input type="text" class="form-control" name="lastName" value="${proprietor.lastName}"
                               required/>
                        <p class="error">${error.lastName}</p>
                        <br/>
                    </div>
                </div>
            </div>

            <div class="col-lg-12">
                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Mobile No </label>
                        <input type="text" class="form-control" name="mobileNo" value="${proprietor.mobileNo}"
                               required/>
                        <p class="error">${error.mobileNo}</p>
                        <br/>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Email </label>
                        <input type="text" class="form-control" name="email" value="${proprietor.email}" required/>
                        <p class="error">${error.email}</p>
                        <br/>
                    </div>
                </div>
            </div>

            <div class="col-lg-12">
                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Username </label>
                        <input type="text" class="form-control" name="username" value="${proprietor.username}"
                               required/>
                        <p class="error">${error.username}</p>
                        <br/>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Password </label>
                        <input type="password" class="form-control" name="password" value="${proprietor.password}"
                               required/>
                        <p class="error">${error.password}</p>
                        <br/>
                    </div>
                </div>
            </div>

            <div class="col-lg-12">
                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Temporary Address </label><br/>
                        <input type="text" class="form-control" name="temporaryAddress"
                               value="${proprietor.temporaryAddress}" required/>
                        <p class="error">${error.temporaryAddress}</p>
                        <br/>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Temporary City </label>
                        <select name="temporaryCity" class="form-control" required="required">
                            <option value="">Select City</option>
                            <c:if test="${fn:length(cityList) gt 0}">
                                <c:forEach var="cityInfo" items="${cityList}">
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
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Permanent Address </label><br/>
                        <input type="text" class="form-control" name="permanentAddress"
                               value="${proprietor.permanentAddress}" required/>
                        <p class="error">${error.permanentAddress}</p>
                        <br/>
                    </div>
                </div>

                <div class="col-lg-4">
                    <div class="form-group" id="inputDiv">
                        <label class="lable">Permanent City </label>
                        <select name="permanentCity" class="form-control input-md" required="required">
                            <option value="">Select City</option>
                            <c:if test="${fn:length(cityList) gt 0}">
                                <c:forEach var="cityInfo" items="${cityList}">
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
            <br/> <br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <!-- <button type="button" class="btn btn-previous">Previous</button> -->
            <button type="submit" class="btn btn-next pull-right">Next</button>


        </form>
    </div>
</div>


<%@include file="../common/footer.jsp" %>