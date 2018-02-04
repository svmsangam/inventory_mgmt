<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 12/25/17
  Time: 8:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

    <!-- Main content -->
    <section class="content">

        <div class="row">
            <div class="col-md-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Add Employee</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/profile/save" method="post" modelAttribute="employee" >
                        <div class="box-body">

                            <div class="row">
                                <div class="col-md-4 form-group">
                                    <label class="control-label">First Name</label>
                                    <input type="text" class="form-control" value="${profile.firstName}" name="firstName" placeholder="first name" required>
                                    <p class="form-error">${profileError.firstName}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Middle Name</label>
                                    <input type="text" class="form-control" value="${profile.middleName}" name="middleName" placeholder="middle name">
                                    <p class="form-error">${profileError.middleName}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Last Name</label>
                                    <input type="text" class="form-control" value="${profile.lastName}" name="lastName" placeholder="last name" required>
                                    <p class="form-error">${profileError.lastName}</p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <label class="control-label">Permanent Address</label>
                                    <input type="text" class="form-control" value="${profile.permanentAddress}" name="permanentAddress" placeholder="permanent address" required>
                                    <p class="form-error">${profileError.permanentAddress}</p>
                                </div>

                                <div class="col-md-6 form-group">
                                    <label class="control-label">Permanent City</label>
                                    <select name="permanentCityId" class="form-control select2" id="lotId" required>
                                        <option value="" selected>select city</option>
                                        <c:forEach items="${cityList}" var="city">
                                            <c:choose>
                                                <c:when test="${city.cityId eq profile.permanentCityId}">
                                                    <option value="${city.cityId}" selected>${city.cityName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${city.cityId}">${city.cityName}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                    </select>
                                    <p class="form-error">${profileError.permanentCityId}</p>
                                </div>

                            </div>

                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <label class="control-label">Temporary Address</label>
                                    <input type="text" class="form-control" value="${profile.temporaryAddress}" name="temporaryAddress" placeholder="temporary address" required>
                                    <p class="form-error">${profileError.temporaryAddress}</p>
                                </div>

                                <div class="col-md-6 form-group">
                                    <label class="control-label">Temporary City</label>
                                    <select name="temporaryCityId" class="form-control select2" id="temporaryCityId">
                                        <option value="" selected>select city</option>
                                        <c:forEach items="${cityList}" var="city">
                                            <c:choose>
                                                <c:when test="${city.cityId eq profile.temporaryCityId}">
                                                    <option value="${city.cityId}" selected>${city.cityName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${city.cityId}">${city.cityName}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                    </select>
                                    <p class="form-error">${profileError.temporaryCityId}</p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-6 form-group">
                                    <label class="control-label">Citizenship No</label>
                                    <input type="text" class="form-control" value="${profile.citizenShipNo}" name="citizenShipNo" placeholder="citizenship no" required>
                                    <p class="form-error">${profileError.citizenShipNo}</p>
                                </div>

                                <div class="col-md-6 form-group">
                                    <label class="control-label">Citizenship Issued From</label>
                                    <select name="citizenShipCityId" class="form-control select2" id="citizenShipCityId">
                                        <option value="" selected>select city</option>
                                        <c:forEach items="${cityList}" var="city">
                                            <c:choose>
                                                <c:when test="${city.cityId eq profile.citizenShipCityId}">
                                                    <option value="${city.cityId}" selected>${city.cityName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${city.cityId}">${city.cityName}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                    </select>
                                    <p class="form-error">${profileError.citizenShipCityId}</p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4 form-group">
                                    <label class="control-label">Mobile No</label>
                                    <input type="text" class="form-control" value="${profile.mobileNumber}" name="mobileNumber" placeholder="mobile no" required>
                                    <p class="form-error">${profileError.mobileNumber}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Emergency Cantact No</label>
                                    <input type="text" class="form-control" value="${profile.emergencyCantact}" name="emergencyCantact" placeholder="emergency cantact no" required>
                                    <p class="form-error">${profileError.emergencyCantact}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Email</label>
                                    <input type="email" class="form-control" value="${profile.email}" name="email" placeholder="email" required>
                                    <p class="form-error">${profileError.email}</p>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-4 form-group">
                                    <label class="control-label">Designation</label>
                                    <select name="designationId" class="form-control select2">
                                        <option value="" selected>select designation</option>
                                        <c:forEach items="${designationList}" var="designation">
                                            <c:choose>
                                                <c:when test="${designation.designationId eq profile.designationId}">
                                                    <option value="${designation.designationId}" selected>${designation.code}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${designation.designationId}">${designation.code}</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                    </select>
                                    <p class="form-error">${profileError.designationId}</p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Status</label>
                                    <select name="employeeStatus" class="form-control select2" id="employeeStatus">
                                        <option value="" selected>select status</option>
                                        <c:forEach items="${employeeStatusList}" var="employeeStatus">
                                            
                                            <c:if test="${employeeStatus eq 'PENDING' or employeeStatus eq 'CONFIRM'}">
                                                <c:choose>
                                                    <c:when test="${employeeStatus eq profile.employeeStatus}">
                                                        <option value="${employeeStatus}" selected>${employeeStatus}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${employeeStatus}">${employeeStatus}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <p class="form-error">${profileError.employeeStatus}</p>
                                </div>

                                <div class="col-md-4 form-group joindate hidden">
                                    <label class="control-label">Join Date</label>
                                    <div class='input-group date datepicker'>
                                        <input type="text" class="datepicker form-control" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${profile.joinDate}"/>" name="joinDate" placeholder="joined on">
                                        <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                    </div>
                                    <p class="form-error">${profileError.joinDate}</p>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </form>
                </div>
                <!-- /.box -->
            </div>
        </div>

    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@include file="/pages/parts/footer.jsp" %>

<script>
    $(function () {
        $('.select2').select2();
        $(".datepicker").datepicker({
        });
    });
    
    $(document).ready(function () {
        $(document).on("change" , "#employeeStatus" , function () {

            console.log("you select : " + $(this).val());

            if($(this).val() === "CONFIRM"){
                $(".joindate").val("");
                $(".joindate").removeClass("hidden").removeClass("show").addClass("show");
            } else{
                $(".joindate").val("");
                $(".joindate").removeClass("hidden").removeClass("show").addClass("hidden");
            }

        })
    })
</script>



