<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/25/18
  Time: 6:07 PM
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
                        <h3 class="box-title">Add Subscriber</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/subscriber/save" method="post" modelAttribute="subscriber" >
                        <div class="box-body">

                           <div class="row">

                               <div class="col-md-4 form-group">
                                   <label class="control-label">User Name *</label>
                                   <input type="text" class="form-control" name="username" placeholder="Name" required="required"/>
                                   <p class="form-error inventoryuser"></p>
                               </div>

                               <div class="col-md-4 form-group">
                                   <label class="control-label">Password *</label>
                                   <input type="password" class="form-control" name="password" id="userpassword"
                                          placeholder="password" required/>
                                   <p class="form-error userpassword"></p>
                               </div>

                               <div class="col-md-4 form-group">
                                   <label class="control-label">Service *</label>
                                   <select class="form-control" name="serviceId" required>
                                       <option value="0">select service</option>
                                       <c:forEach items="${serviceList}" var="serviceInfo">
                                           <option value="${serviceInfo.serviceId}">${serviceInfo.title}</option>
                                       </c:forEach>
                                   </select>
                                   <p class="form-error"></p>
                               </div>

                           </div>

                            <div class="row">

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Full Name *</label>
                                    <input type="text" class="form-control" name="fullName" placeholder="full name" required="required"/>
                                    <p class="form-error"></p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Contact </label>
                                    <input type="text" class="form-control" name="contact" placeholder="contact" />
                                    <p class="form-error userpassword"></p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Mobile *</label>
                                    <input type="text" class="form-control" name="mobile" placeholder="mobile" required/>
                                    <p class="form-error"></p>
                                </div>

                            </div>

                            <div class="row">

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Email *</label>
                                    <input type="email" class="form-control" name="email" placeholder="email" required="required"/>
                                    <p class="form-error"></p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">City *</label>
                                    <select class="form-control" name="cityInfoId" required>
                                        <option value="0">select city</option>
                                        <c:forEach items="${cityList}" var="city">
                                            <option value="${city.cityId}">${city.cityName}</option>
                                        </c:forEach>
                                    </select>
                                    <p class="form-error userpassword"></p>
                                </div>

                                <div class="col-md-4 form-group">
                                    <label class="control-label">Street</label>
                                    <input type="text" class="form-control" name="street" placeholder="street"/>
                                    <p class="form-error"></p>
                                </div>

                            </div>

                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
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



