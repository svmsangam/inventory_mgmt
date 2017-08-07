<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="../resources/css/select2.css" rel="stylesheet">
<c:choose>
    <c:when test="${user.userType == 'ADMIN'}">
        <%@include file="../common/settings.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="../common/businessOwnerSettings.jsp" %>
    </c:otherwise>
</c:choose>


<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">City</li>
        </ol>
    </div><!--/.row-->


    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">Edit City</h2>
        </div>
    </div><!--/.row-->

    <div class="col-sm-6">
        <form method="post" action="${pageContext.request.contextPath}/editcity" modelAttribute="cityDto">
            <input type="hidden" name="cityId" value="${cityInfo.cityId}" required/>
            <table>
                <tr>
                    <td><label class="lable">City Name: </label></td>
                    <td>
                        <input class="form-control" type="text" name="cityName" value="${cityInfo.cityName}" required/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><p class="error">${error.cityName}</p></td>
                </tr>

                <tr>

                    <td><label>State</label></td>
                    <td>
                        <script type="text/javascript">
                            $(document).ready(function () {
                                $(".choose").select2();

                            });
                        </script>
                        <select name="stateName" required="required" class="choose form-control">
                            <option value="">Select State</option>
                            <c:if test="${fn:length(stateList) gt 0}">
                                <c:forEach var="stateInfo" items="${stateList}">
                                    <c:choose>
                                        <c:when
                                                test="${stateInfo.stateName == cityInfo.stateName}">
                                            <option value="${stateInfo.stateName}"
                                                    selected>${stateInfo.stateName},${stateInfo.countryName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${stateInfo.stateName}">${stateInfo.stateName},${stateInfo.countryName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:if>
                        </select></td>

                </tr>
                <tr>
                    <td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></td>
                    <td><br/><input type="submit" value="Update City" class="btn btn-sm btn-success"/></td>
                </tr>
            </table>
        </form>
    </div>
</div>