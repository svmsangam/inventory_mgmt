<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <li class="active">Country</li>
        </ol>
    </div><!--/.row-->


    <div class="row">
        <div class="col-lg-12">
            <h2 class="page-header">Edit Country</h2>
        </div>
    </div><!--/.row-->

    <div class="col-sm-6">
        <form method="post" action="${pageContext.request.contextPath}/editcountry" modelAttribute="countryDto">
            <input type="hidden" name="countryId" value="${countryInfo.countryId}" required/>
            <table>
                <tr>
                    <td><label class="lable">Country Name </label><br/></td>
                    <td>
                        <input class="form-control" type="text" name="name" value="${countryInfo.name}" required/>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><p class="error">${error.countryName}</p></td>
                </tr>
                <tr>
                    <td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></td>
                    <td><input type="submit" value="Update Country" class="btn btn-sm btn-success"></td>
                </tr>
            </table>

            </table>
        </form>
    </div>
</div>