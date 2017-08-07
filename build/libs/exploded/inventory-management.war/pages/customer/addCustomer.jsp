<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="../common/businessOwnerSettings.jsp" %>
<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Customer</li>
        </ol>
    </div><!--/.row-->

    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">Add Customer</div>
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/app/addcustomer" method="POST"
                          modelAttribute="customerDto"
                          enctype="multipart/form-data" id="registerform" novalidate="novalidate" class="col-md-6">

                        <div class="form-group" id="inputDiv">
                            <label class="lable">First Name * </label>
                            <input type="text" id="firstName" value="${clientInfo.firstName}" class="form-control"
                                   name="firstName" required/>
                            <p class="error">${error.firstName}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Middle Name </label>
                            <input type="text" id="middleName" value="${clientInfo.middleName}" class="form-control"
                                   name="middleName"/>
                            <p class="error">${error.middleName}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Last Name * </label>
                            <input type="text" id="lastName" value="${clientInfo.lastName}" class="form-control"
                                   name="lastName" required/>
                            <p class="error">${error.lastName}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Unique Name * </label>
                            <input type="text" id="uniqueName" value="${clientInfo.uniqueName}" class="form-control"
                                   name="uniqueName" required/>
                            <p class="error">${error.uniqueName}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">MobileNo * </label>
                            <input type="number" id="mobileNo" value="${clientInfo.mobileNo}" pattern="[0-9]{5}"
                                   class="form-control" name="mobileNo" required/>
                            <p class="error" id="error">${error.mobileNo}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Landline * </label>
                            <input type="number" id="landline" value="${clientInfo.landline}" pattern="[0-9]{5}"
                                   class="form-control" name="landline" required/>
                            <p class="error" id="error">${error.landline}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Address * </label>
                            <input type="text" id="address" value="${clientInfo.address}" class="form-control"
                                   name="address" required/>
                            <p class="error">${error.address}</p>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Add Customer" class="btn btn-sm btn-primary">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- <%@include file="/pages/common/footer.jsp"%> --%>

<script>

    $(document).ready(function () {


        $("#registerform").validate({

            // Specify the validation rules
            rules: {
                firstName: "required",
                lastName: "required",
                uniqueName: "required",
                address: "required",

                mobileNo: {
                    required: true,
                    minlength: 10,
                    maxlength: 10
                },
                landline: {
                    required: true,
                    minlength: 9,
                    maxlength: 9
                }
            },
            // Specify the validation error messages
            messages: {


                firstname: "Please enter your first name",
                lastname: "Please enter your last name",
                uniqueName: "Please enter your unique name",
                address: "Please enter your address",

                mobileNo: {
                    required: "Please provide a mobileNo",
                    minlength: "Your mobileNo must be 10 characters long",
                    maxlength: "Your mobileNo must be 10 characters long"
                },
                landline: {
                    required: "Please type your landline",
                    minlength: "Your landline must be 9 characters long",
                    maxlength: "Your landline must be 9 characters long"
                }

            },

            submitHandler: function (form) {
                form.submit();
            }
        });


    });
</script>
