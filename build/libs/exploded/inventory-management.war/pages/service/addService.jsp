<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="/pages/common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Service</li>
        </ol>
    </div><!--/.row-->

    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">Add Service</div>
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/app/addservice" method="POST"
                          modelAttribute="productDto"
                          enctype="multipart/form-data" id="registerform" novalidate="novalidate" class="col-md-8">

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Service Name * </label>
                            <input type="text" id="serviceName" class="form-control" name="serviceName" required/>
                            <p class="error">${error.serviceName}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Service Type * </label>
                            <input type="text" id="serviceType" class="form-control double" name="serviceType"
                                   required/>
                            <p class="error">${error.serviceType}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Service Price * </label>
                            <input type="number" id="servicePrice" step="any" onkeypress="return (event.charCode >= 48 && event.charCode <= 57) ||
   event.charCode == 46 || event.charCode == 0 " class="form-control double" name="servicePrice" required/>
                            <p class="error">${error.servicePrice}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Service Description* </label>
                            <textarea rows="4" id="description" class="form-control" name="description"
                                      required></textarea>
                            <p class="error">${error.description}</p>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Add Service" class="btn btn-primary">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#js-example-responsive").select2();


        $("#registerform").validate({

            // Specify the validation rules
            rules: {
                serviceName: {
                    required: true,
                    maxlength: 50
                },
                serviceType: {
                    required: true,
                    maxlength: 50
                },
                servicePrice: {
                    required: true,
                    min: 0,
                    max: 2147483646
                },
                description: {
                    required: true,
                    maxlength: 20000
                }
            },
            // Specify the validation error messages
            messages: {


                serviceName: {
                    required: "Please provide a serviceName",
                    maxlength: "Your serviceName must be less than 50"
                },
                serviceType: {
                    required: "Please provide a serviceType",
                    maxlength: "Your serviceType must be less than 50"
                },
                servicePrice: {
                    required: "Please provide a servicePrice",
                    min: "Your servicePrice must be more than 0",
                    max: "Your servicePrice must be less than 2147483646"
                },

                description: {
                    required: "Please provide a description",
                    maxlength: "Your description must be less than 20000"
                }


            },

            submitHandler: function (form) {
                form.submit();
            }
        });
    });
</script>


<%@include file="../common/footer.jsp" %>