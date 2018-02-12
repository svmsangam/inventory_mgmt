<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/3/18
  Time: 1:11 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
<img class="profile-user-img img-responsive img-circle" src="${pageContext.request.contextPath}/resources/img/avatar.png" alt="User profile picture">
--%>

<%--<c:if test="${profile.photo ne null}">
    <img class="profile-user-img img-responsive img-circle" src="${pageContext.request.contextPath}/employee/${profile.photo}" alt="User profile picture">
</c:if>--%>

<%--<c:if test="${profile.photo eq null}">
    <a class="btn btn-default" href="${pageContext.request.contextPath}/profile/image/upload/${profile.employeeProfileId}">upload image</a>
</c:if>--%>

<h3 class="profile-username text-center">${profile.username}</h3>
<p class="text-muted text-center">${profile.firstName} ${profile.middleName} ${profile.lastName}</p>

<div class="row">
    <div class="col-md-6">
        <hr>
            <strong><i class="fa fa-book margin-r-5"></i> Contact Details</strong>
            <p class="text-muted">${profile.mobileNumber}</p>
            <p class="text-muted">${profile.email}</p>
            <strong><i class="fa fa-book margin-r-5"></i> Emergency Contact</strong>
            <p class="text-muted">${profile.emergencyCantact}</p>
        <hr>
    </div>

    <div class="col-md-6">
        <hr>
            <strong><i class="fa fa-map-marker margin-r-5"></i> Current Address</strong>
            <p class="text-muted">${profile.temporaryAddress}, ${profile.temporaryCity.cityName}
                <br>${profile.temporaryCity.stateName}, ${profile.temporaryCity.countryName}
            </p>
        <hr>
            <strong><i class="fa fa-map-marker margin-r-5"></i> Permanent Address</strong>
            <p class="text-muted">${profile.permanentAddress}, ${profile.permanentCity.cityName}
                <br>${profile.permanentCity.stateName}, ${profile.permanentCity.countryName}
            </p>
        <hr>
    </div>
</div>

<div class="row">
    <div class="col-md-6">
        <hr>
            <strong><i class="fa fa-pencil margin-r-5"></i> Citizenship Details</strong>
            <p class="text-muted">${profile.citizenShipNo}, ${profile.citizenShipCity.cityName}</p>
        <hr>
    </div>

    <div class="col-md-6">
        <strong><i class="fa fa-file-text-o margin-r-5"></i> Created By</strong>
        <p>${profile.createdByName}</p>
    </div>
</div>

