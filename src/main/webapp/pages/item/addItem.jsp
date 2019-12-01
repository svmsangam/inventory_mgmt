<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/24/17
  Time: 9:43 PM
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

        <c:if test="${not empty message}">
            <div class="alert alert-success alert-dismissable">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                <strong>${message}</strong>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissable">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
                <strong>${error}</strong>
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Add Item</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/item/saveitem" method="post" modelAttribute="item" >
                        <div class="box-body">

                            <div class="form-group">
                                <label class="control-label">Product</label>
                                <select name="productId" class="form-control select2">
                                    <option value="">select product</option>
                                    <c:forEach items="${productList}" var="product">
                                        <c:choose>
                                            <c:when test="${product.productId eq item.productId}">
                                                <option value="${product.productId}" selected>${product.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${product.productId}">${product.name}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                                <p class="form-error">${itemError.productId}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Tag</label>
                                <a href="#" class="pull-right" id="addNewTagBtn" data-toggle="modal" data-target="#modal-addtag">new tag</a>
                                <select name="tagId" class="form-control chooseTag" id="tagId">
                                    <option value="">select tag</option>
                                </select>
                                <p class="form-error">${itemError.tagId}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Lot</label>
                                <select name="lotId" class="form-control select2" id="lotId">
                                    <option value="">select lot</option>
                                    <c:forEach items="${lotList}" var="lot">
                                        <c:choose>
                                            <c:when test="${lot.lotId eq item.lotId}">
                                                <option value="${lot.lotId}" selected>${lot.lot}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${lot.lotId}">${lot.lot}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                                <p class="form-error">${itemError.lotId}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Cost Price</label>
                                <input type="number" class="form-control" value="${item.costPrice}" onkeypress="return validate(event);"  name="costPrice" placeholder="costPrice">
                                <p class="form-error">${itemError.costPrice}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Selling Price</label>
                                <input type="number" class="form-control" value="${item.sellingPrice}" onkeypress="return validate(event);"  name="sellingPrice" placeholder="sellingPrice">
                                <p class="form-error">${itemError.sellingPrice}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Quantity</label>
                                <input type="number" class="form-control" onkeypress="return validate(event);"  value="${item.quantity}" name="quantity" placeholder="quantity">
                                <p class="form-error">${itemError.quantity}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Threshold</label>
                                <input type="number" class="form-control" onkeypress="return validate(event);"  value="${item.threshold}" name="threshold" placeholder="threshold">
                                <p class="form-error">${itemError.threshold}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Expiry Date</label>
                                <div class='input-group date datepicker'>
                                    <input type="text" class="datepicker form-control" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${item.expireDate}"/>" name="expireDate" placeholder="expire on">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                                <p class="form-error">${itemError.expireDate}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Supplier</label>
                                <select name="vendorId" class="form-control choose1">

                                </select>
                                <p class="form-error"><%--${itemError.vendorId}--%></p>
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

<div class="modal fade" id="modal-addtag">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add New Tag</h4>
            </div>
            <div class="modal-body">
                <div class="box-body">

                    <div class="form-group">
                        <label class="control-label">Name</label>
                        <input type="text" id="tagName" class="form-control addTagFormClear" name="name" placeholder="Name">
                        <p class="form-error tagFormError" id="tagNameError"></p>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Code</label>
                        <input type="text" id="tagCode" class="form-control addTagFormClear" name="code" placeholder="code">
                        <p class="form-error tagFormError" id="tagCodeError"></p>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-danger pull-left closeAdd" data-dismiss="modal">Close
                    </button>
                    <button type="button" class="btn btn-primary btn-sm  btn-flat pull-right addNewTag" url="${pageContext.request.contextPath}/api/tag/save"><span class="glyphicon glyphicon-save"></span>
                        Save Changes
                    </button>
                </div>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>


<%@include file="/pages/parts/footer.jsp" %>

<script>
    $(function () {
        $('.select2').select2();
        $(".datepicker").datepicker({
        });
    })
</script>

<script>
    $(document).ready(function() {

        $(".choose1").select2({
            ajax: {
                url: '${pageContext.request.contextPath}/client/vendor/search',
                dataType: 'json',
                headers : {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                delay: 250,
                type: 'GET',
                data: function (params) {
                    return {
                        term: params.term, // search term
                        /* page: params.page*/
                    };
                },
                processResults: function (data , params) {
                    params.page = params.page || 1;
                    var arr = []
                    $.each(data.detail, function (index, value) {

                        if(value.companyName === null || "" === value.companyName) {

                            arr.push({
                                id: value.clientId,
                                text: value.name + ' - ' + value.mobileNumber
                            })
                        }else {
                            arr.push({
                                id: value.clientId,
                                text: value.companyName + ' - ' + value.mobileNumber
                            })
                        }
                    })



                    return {
                        results: arr/*,
                         pagination: {
                         more: (params.page * 1) < 2
                         }*/
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) { return markup; },
            minimumInputLength: 1,
            placeholder: "Search Vendor by Name & Mobile No"
        });
        $(".chooseTag").select2({
            ajax: {
                url: '${pageContext.request.contextPath}/api/tag/search',
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                headers : {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                delay: 250,
                type: 'GET',
                data: function (params) {
                    return {
                        term: params.term, // search term
                        /* page: params.page*/
                    };
                },
                processResults: function (data , params) {
                    params.page = params.page || 1;
                    var arr = []
                    $.each(data.detail, function (index, value) {

                        if(value.companyName === null || "" === value.companyName) {

                            arr.push({
                                id: value.clientId,
                                text: value.name + ' - ' + value.mobileNumber
                            })
                        }else {
                            arr.push({
                                id: value.clientId,
                                text: value.companyName + ' - ' + value.mobileNumber
                            })
                        }
                    })



                    return {
                        results: arr/*,
                         pagination: {
                         more: (params.page * 1) < 2
                         }*/
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) { return markup; },
            minimumInputLength: 1,
            placeholder: "Search tag by name and code"
        });

        /*add new tag start*/
        //addNewTagBtn

        $(document).on("click" , "#addNewTagBtn" , function () {
            $(".addNewTag").prop( "disabled", false );
            clearErrorData(".tagFormError");
            clearInputFormData(".addTagFormClear");

        });

        $(document).on("click" , ".addNewTag" , function () {
            var name = $("#tagName").val();
            var code = $("#tagCode").val();

            var url = $(this).attr("url");

            var tagService = new TagService();
            tagService.save(name , code , url);

        });

        /*add new tag end*/
    });

    function clearInputFormData(cls) {
        $(cls).val("");
    }

    function clearErrorData(cls) {
        $(cls).text("");
    }
</script>
<script src="${pageContext.request.contextPath}/resources/js/asset/app/numberValidation.js"></script>


