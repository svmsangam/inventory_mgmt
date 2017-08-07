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
            <li class="active">State</li>
        </ol>
    </div><!--/.row-->


    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">Edit State</h2>
        </div>
    </div><!--/.row-->

    <div class="col-sm-6">
        <form method="post" action="${pageContext.request.contextPath}/editstate" modelAttribute="stateDto">
            <input type="hidden" name="stateId" value="${stateInfo.stateId}" required/>
            <table>
                <tr>
                    <td><label class="lable">State Name </label><br/></td>
                    <td>
                        <input class="form-control" type="text" name="stateName" value="${stateInfo.stateName}"
                               required/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><p class="error">${error.stateName}</p></td>
                </tr>
                <tr>

                    <td><label>Country</label></td>
                    <td>
                        <script type="text/javascript">
                            $(document).ready(function () {
                                $(".choose").select2();

                            });
                        </script>
                        <select name="countryName" required="required" class="choose form-control">
                            <option value="">Select Country</option>
                            <c:if test="${fn:length(countryList) gt 0}">
                                <c:forEach var="countryInfo" oldItems="${countryList}">
                                    <c:choose>
                                        <c:when
                                                test="${countryInfo.name == stateInfo.countryName}">
                                            <option value="${countryInfo.name}" selected>${countryInfo.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${countryInfo.name}">${countryInfo.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:if>
                        </select></td>

                </tr>
                <tr>
                    <td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></td>
                    <td><br/><input type="submit" value="Update State" class="btn btn-sm btn-success"/></td>

                </tr>
            </table>

            </table>
        </form>
    </div>
</div>