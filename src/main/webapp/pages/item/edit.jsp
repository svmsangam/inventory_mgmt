<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/common/header.jsp" %>
<%@include file="../common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">

    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Item Edit</li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-6">
            <div class="panel panel-default">
                <div class="panel-heading">Add Item<a href="${pageContext.request.contextPath}/productInfo/list"
                                                      style="float:right; margin-bottom:8px;">
                    <button class="btn btn-primary btn-sm">List Item</button>
                </a></div>
                <div class="panel-body">
                    <input class="context" type="hidden" value="${pageContext.request.contextPath}">
                    <form action="${pageContext.request.contextPath}/productInfo/edit" method="POST"
                          modelAttribute="productInfo"
                          enctype="multipart/form-data" id="registerform" novalidate="novalidate" class="col-lg-8">
                        <input type="hidden" name="itemId" value="${productInfo.itemId}"/>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Item Name * </label>
                            <input type="text" value="${productInfo.name }" class="form-control" name="name" required/>

                            <p class="error">${error.name}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Item Code * </label>
                            <input type="text" value="${productInfo.code }" class="form-control" name="code" required/>

                            <p class="error">${error.code}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Item CostPrice * </label>
                            <input type="number" value="${productInfo.costPrice }" class="form-control" name="costPrice"
                                   required/>

                            <p class="error">${error.costPrice}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Item SellingPrice * </label>
                            <input type="number" value="${productInfo.sellingPrice }" class="form-control"
                                   name="sellingPrice" required/>

                            <p class="error">${error.sellingPrice}</p>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Item TraditonLevel * </label>
                            <select required="required" class="choose form-control categoryInfo" name="trendingLevel">
                                <option value="">Select level</option>
                                <c:if test="${fn:length(tradionLevelList) gt 0}">
                                    <c:forEach var="level" items="${tradionLevelList}">
                                        <c:choose>
                                            <c:when test="${level eq productInfo.trendingLevel}">
                                                <option value="${level}" selected>${level}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${level}">${level}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Item Category * </label>
                            <select required="required" class="choose form-control categoryInfo">
                                <option value="">Select categoryInfo</option>
                                <c:if test="${fn:length(categoryList) gt 0}">
                                    <c:forEach var="categoryInfo" items="${categoryList}">
                                        <option value="${categoryInfo.categoryId}">${categoryInfo.categoryName}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>

                        <div class="form-group" id="subCategory">
                            <label class="lable">Item SubCategory * </label>

                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Item Unit * </label>

                            <select name="unitId" required="required" class="choose form-control">
                                <option value="">Select Unit</option>
                                <c:if test="${fn:length(unitList) gt 0}">
                                    <c:forEach var="unitInfo" items="${unitList}">
                                        <c:choose>
                                            <c:when
                                                    test="${unitInfo.unitId == productInfo.unitId}">
                                                <option value="${unitInfo.unitId}" selected>${unitInfo.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${unitInfo.unitId}">${unitInfo.name}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:if>
                            </select>

                        </div>

                        <div class="form-group" id="inputDiv">
                            <label class="lable">Item Description * </label>
                            <textarea type="text" value="${productInfo.description }" class="form-control"
                                      name="description" required></textarea>

                            <p class="error">${error.description}</p>
                        </div>

                        <div class="form-group table-responsive" id="inputDiv">

                            <div id="add_row" class="btn btn-xs btn-primary pull-left"><span
                                    class="glyphicon glyphicon-plus"></span> Add Row
                            </div>
                            <div class="table-responsive">
                            <table id="customFields" class="table">
                                <thead>
                                <th>tagInfo</th>
                                <th>quantity</th>
                                <th>threshold</th>
                                </thead>
                                <tbody>
                                <c:if test="${fn:length(productInfo.tagIdList) gt 0}">
                                    <c:forEach var="tagItem" items="${productInfo.tagIdList}">

                                        <tr>
                                            <td>
                                                <select name="tagIdList" required="required"
                                                        class="choose form-control">
                                                    <option value="">Select Tag</option>
                                                    <c:if test="${fn:length(tagList) gt 0}">
                                                        <c:forEach var="tagInfo" items="${tagList}">
                                                            <c:choose>
                                                                <c:when test="${tagItem == tagInfo.tagId}">
                                                                    <option value="${tagInfo.tagId}"
                                                                            selected>${tagInfo.tagName}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${tagInfo.tagId}">${tagInfo.tagName}</option>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </td>

                                            <td>
                                                <input type="number" value="" class="form-control" name="quantityList"
                                                       required/>
                                            </td>
                                            <td>
                                                <input type="number" value="" class="form-control" name="thresholdList"
                                                       required/>
                                            </td>

                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${fn:length(productInfo.tagIdList) eq 0}">

                                    <tr>
                                        <td>
                                            <select name="tagIdList" required="required" class="choose form-control">
                                                <option value="">Select Tag</option>
                                                <c:if test="${fn:length(tagList) gt 0}">
                                                    <c:forEach var="tagInfo" items="${tagList}">
                                                        <option value="${tagInfo.tagId}">${tagInfo.tagName}</option>
                                                    </c:forEach>
                                                </c:if>
                                            </select>

                                        </td>

                                        <td>
                                            <input type="number" value="" class="form-control quantity"
                                                   name="quantityList" required/>
                                        </td>

                                        <td>
                                            <input type="number" value="" class="form-control quantity"
                                                   name="thresholdList" required/>
                                        </td>
                                    </tr>
                                </c:if>
                                </tbody>
                            </table>
                            </div>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" value="Update Item" class="btn btn-primary pull-right">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>

<script type="text/javascript">


    $(document).ready(function () {
        var count = 1;
// for dynamically add or remove row
        $("#add_row").click(function () {
            //alert(count);
            var row = "<tr><td><select name='tagIdList' required='required' class='choose form-control'><option value=''>Select TagInfo</option><c:if test='${fn:length(tagList) gt 0}'><c:forEach var='tagInfo' items='${tagList}'><option value='${tagInfo.tagId}'>${tagInfo.tagName}</option></c:forEach></c:if></select></td>";
            row += "<td><input  type='number' class='form-control quantity'  name='quantityList' required/></td>";
            row += "<td><input  type='number' value='' class='form-control'  name='thresholdList' required/></td><td> <a href='javascript:void(0);' class='remCF'><span class='btn btn-danger btn-xs glyphicon glyphicon-remove'></span></a></td></tr>";
            $("#customFields").prepend(row);
            $(".choose").select2();// for select2 dropdown
            count++;
        });
        $("#customFields").on('click', '.remCF', function () {
            $(this).parent().parent().remove();
            update_amounts();
        });
    });

    $(document).ready(function () {
        $(".choose").select2();

    });

    $(document).ready(function () {

        $("#subCategory").hide();
        $(document).on('change', '.categoryInfo', function () {
            var categoryId = $(this).val();

            var url = $(".context").val() + "/subCategoryInfo/bycategory";
            getAllSubcategory(categoryId, url);
        });
    });
</script>

<script type="text/javascript">
    $(document).ready(function () {
        $("#js-example-responsive").select2();
        $("#registerform").validate({
            // Specify the validation rules
            rules: {
                itemName: "required",
                rate: {
                    required: true,
                    min: 0,
                    max: 2147483646
                },
                quantity: {
                    required: true,
                    min: 0,
                    max: 2147483646
                },
                tax: {
                    required: true,
                    min: 0,
                    max: 1000
                },
                threshold: {
                    required: true,
                    min: 0,
                    max: 2147483646
                }
            },
            // Specify the validation error messages
            messages: {
                firstname: "Please enter your first name",
                rate: {
                    required: "Please provide a rate",
                    min: "Your rate must be more than 0",
                    max: "Your rate must be less than 2147483646"
                },
                quantity: {
                    required: "Please type your quantity",
                    min: "Your quantity must be more than 0",
                    max: "Your quantity must be less than 2147483646"
                },
                tax: {
                    required: "Please type your tax",
                    min: "Your tax must be more than 0",
                    max: "Your tax must be less than 1000"
                },
                threshold: {
                    required: "Please type your threshold",
                    min: "Your threshold must be more than 0",
                    max: "Your threshold must be less than 2147483646"
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    });
</script>
						
