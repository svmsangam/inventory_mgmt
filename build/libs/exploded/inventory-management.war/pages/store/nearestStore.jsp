<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

<spr:page header1="Nearest Stores">
    <div class="col-md-12">
        <c:choose>
            <c:when test="${fn:length(storeDtoList) eq 0}">
                <c:if test="${not empty message}">
                    <p class="bg-success">
                        <c:out value="${message}"></c:out>
                    </p>
                </c:if>
            </c:when>
            <c:when test="${fn:length(storeDtoList) gt 0}">
                <table id="storeList" class="table table-striped">
                    <thead>
                    <tr>
                        <th>Store Name</th>
                        <th>Owner Name</th>
                        <th>Address</th>
                        <th>Mobile Number
                        <th>Pan Number</th>
                        <th>Registration Number</th>
                        <th>LandLine Number</th>
                        <th>Latitude</th>
                        <th>Longitude</th>


                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="storeInfo" oldItems="${storeDtoList}">
                        <tr>
                            <td>${storeInfo.storeName}</td>
                            <td>${storeInfo.proprieterName}</td>
                            <td>${storeInfo.location}</td>
                            <td>${storeInfo.mobileNumber}</td>
                            <td>${storeInfo.panNumber}</td>
                            <td>${storeInfo.registrationNumber}</td>
                            <td>${storeInfo.landLine}</td>
                            <td>${storeInfo.latitude}</td>
                            <td>${storeInfo.longitude}</td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
        </c:choose>
    </div>
</spr:page>	