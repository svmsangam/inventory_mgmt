<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/24/17
  Time: 7:11 PM
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
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Add Vendor</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/vendor/save" method="post"
                          modelAttribute="vendor">
                        <div class="box-body">

                            <div class="form-group">
                                <label class="control-label">Company Name</label>
                                <input type="text" class="form-control" value="${vendor.companyName}" name="companyName"
                                       placeholder="Company Name">
                                <p class="form-error">${vendorError.companyName}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Propriator Name</label>
                                <input type="text" class="form-control" value="${vendor.name}" name="name"
                                       placeholder="Propriator Name">
                                <p class="form-error">${vendorError.name}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Contact</label>
                                <input type="text" class="form-control" value="${vendor.contact}" name="contact"
                                       placeholder="contact">
                                <p class="form-error">${vendorError.contact}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Mobile Number</label>
                                <input type="text" class="form-control" value="${vendor.mobileNumber}"
                                       name="mobileNumber" placeholder="Mobile Number">
                                <p class="form-error">${vendorError.mobileNumber}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Email Address</label>
                                <input type="email" class="form-control" value="${vendor.email}" name="email"
                                       placeholder="email address">
                                <p class="form-error">${vendorError.email}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">City</label>
                                <select name="cityId" class="form-control select2" id="cityId">
                                    <option value="">select city</option>
                                    <c:forEach items="${cityList}" var="city">
                                        <c:choose>
                                            <c:when test="${city.cityId eq vendor.cityId}">
                                                <option value="${city.cityId}" selected>${city.cityName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${city.cityId}">${city.cityName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                                <p class="form-error">${vendorError.name}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Street</label>
                                <input type="text" class="form-control" value="${vendor.street}" name="street"
                                       placeholder="street">
                                <p class="form-error">${vendorError.street}</p>
                            </div>

                            <!-- /.box-body -->
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Save changes</button>
                            </div>

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
        $('.select2').select2()
    })
</script>


