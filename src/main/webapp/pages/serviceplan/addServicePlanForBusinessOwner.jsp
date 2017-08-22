<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/common/header.jsp" %>
<c:choose>
    <c:when test="${pageContext.request.userPrincipal.name eq 'admin'}">
        <%@include file="/pages/common/navbar.jsp" %>
        <%@include file="/pages/common/sidebar.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="/pages/common/businessownernavbar.jsp" %>
        <%@include file="/pages/common/businessownersidebar.jsp" %>
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
            <li class="active">Business</li>
        </ol>
    </div>
    <!--/.row-->

    <div class="col-md-offset-4">
        <h4 class="page-header">Add Permissions</h4>
    </div>
    <!--/.row-->
    <div class="container-wrapper">

        <form method="POST" action="${pageContext.request.contextPath}/addBusinessServicePlanForBusinesOwner"
              modelAttribute="businessService" class="f1">

            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>Service Plan</th>
                    <th>Create</th>
                    <th>View</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>


                <tr>
                    <td>Category</td>
                    <td><input type="checkbox" name="permission" value="CATEGORY_CREATE" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="CATEGORY_VIEW" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="CATEGORY_UPDATE" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="CATEGORY_DELETE" class="myiterator"
                               disabled="disabled"/></td>
                </tr>

                <tr>
                    <td>SubCategory</td>
                    <td><input type="checkbox" name="permission" value="SUBCATEGORY_CREATE" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="SUBCATEGORY_VIEW" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="SUBCATEGORY_UPDATE" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="SUBCATEGORY_DELETE" class="myiterator"
                               disabled="disabled"/></td>
                </tr>

                <tr>
                    <td>Tag</td>
                    <td><input type="checkbox" name="permission" value="TAG_CREATE" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="TAG_VIEW" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="TAG_UPDATE" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="TAG_DELETE" class="myiterator"
                               disabled="disabled"/></td>
                </tr>

                <tr>
                    <td>Unit</td>
                    <td><input type="checkbox" name="permission" value="UNIT_CREATE" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="UNIT_VIEW" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="UNIT_UPDATE" class="myiterator"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission" value="UNIT_DELETE" class="myiterator"
                               disabled="disabled"/></td>
                </tr>


                <tr>
                    <td>Service<input type="hidden" name="userId"
                                      value="${user.id}" class="userId"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SERVICE_CREATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SERVICE_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SERVICE_UPDATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SERVICE_DELETE" disabled="disabled"/></td>
                </tr>
                <tr>
                    <td>Product</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="PRODUCT_CREATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="PRODUCT_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="PRODUCT_UPDATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="PRODUCT_DELETE" disabled="disabled"/></td>
                </tr>

                <tr>
                    <td>Item</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="ITEM_CREATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="ITEM_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="ITEM_UPDATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="ITEM_DELETE" disabled="disabled"/></td>
                </tr>
                <tr>
                    <td>Sales Order</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SALES_ORDER_CREATE"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SALES_ORDER_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SALES_ORDER_UPDATE"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SALES_ORDER_DELETE"
                               disabled="disabled"/></td>
                </tr>
                <tr>
                    <td>Invoice</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="INVOICE_CREATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="INVOICE_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="INVOICE_UPDATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="INVOICE_DELETE" disabled="disabled"/></td>
                </tr>
                <tr>
                    <td>Purchase Order</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="PURCHASE_ORDER_CREATE"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="PURCHASE_ORDER_VIEW"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="PURCHASE_ORDER_UPDATE"
                               disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="PURCHASE_ORDER_DELETE"
                               disabled="disabled"/></td>
                </tr>
                <tr>
                    <td>Bill</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="BILL_CREATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="BILL_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="BILL_UPDATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="BILL_DELETE" disabled="disabled"/></td>
                </tr>
                <tr>
                    <td>User</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="USER_CREATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="USER_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="USER_UPDATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="USER_DELETE" disabled="disabled"/></td>
                </tr>
                <tr>
                    <td>Report</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="REPORT_CREATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="REPORT_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="REPORT_UPDATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="REPORT_DELETE" disabled="disabled"/></td>
                </tr>
                <tr>
                    <td>Supplier</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SUPPLIER_CREATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SUPPLIER_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SUPPLIER_UPDATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="SUPPLIER_DELETE" disabled="disabled"/></td>
                </tr>

                <tr>
                    <td>Store</td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="STORE_CREATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="STORE_VIEW" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="STORE_UPDATE" disabled="disabled"/></td>
                    <td><input type="checkbox" name="permission"
                               class="myiterator" value="STORE_DELETE" disabled="disabled"/></td>
                </tr>

                </tbody>
            </table>

            <br/> <br/>
            <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="submit" value="Submit Plan" class="btn btn-success" /> --%>
        </form>

    </div>
    <!-- /.row -->

</div>
<!--/.main-->

<%@include file="../common/footer.jsp" %>

<script type="text/javascript">
    var userId;
    var plans;

    $(document).ready(function () {
        userId = $('.userId').val();
        $.ajax({

            type: "GET",
            url: "getPlanOfcurrentUser",
            contentType: "application/json",
            data: {storeId: userId},
            dataType: 'json',
            timeout: 100000,
            success: function (data) {

                if (data.status == 'Success') {

                    plans = data.detail;
                    for (i = 0; i < plans.length; i++) {
                        $('.myiterator').each(function () {
                            if ($(this).val() == plans[i]) {
                                $(this).removeAttr("disabled");
                            }
                        });

                    }

                }
            }
        });

        $.ajax({

            type: "GET",
            url: "getPlanOfUser",
            contentType: "application/json",
            data: {userId: userId},
            dataType: 'json',
            timeout: 100000,
            success: function (data) {

                if (data.status == 'Success') {

                    plans = data.detail;
                    for (i = 0; i < plans.length; i++) {
                        $('.myiterator').each(function () {
                            //&& $(this).is(':enabled')
                            if ($(this).val() == plans[i]) {
                                $(this).attr('checked', true);
                            }
                        });

                    }

                }
            }
        });

        $('.myiterator').click(function () {

            //$(this).attr('check', 'me');

            var plan = $(this).val();
            if ($(this).is(':checked')) {

                console.log('i am checked ' + plan);
                $.ajax({

                    type: "GET",
                    url: "addPlan",
                    contentType: "application/json",
                    data: {userId: userId, plan: plan},
                    dataType: 'json',
                    timeout: 100000,
                    statusCode: {

                        404: function () {
                            $('.myiterator').each(function () {
                                if ($(this).val() == plan) {
                                    $(this).removeAttr("checked");

                                }
                            });
                            alert("please try again 404");

                        },
                        500: function () {

                            $('.myiterator').each(function () {
                                if ($(this).val() == plan) {
                                    $(this).removeAttr("checked");

                                }
                            });

                            alert("please try again 500");

                        },
                        200: function (data) {

                            console.log("my success");
                            if (data.status != 'Success') {

                                $('.myiterator').each(function () {
                                    if ($(this).val() == plan) {
                                        $(this).removeAttr("checked");

                                    }
                                });
                                alert("please try again failed");

                            }
                            if (data.status == 'Failure') {

                                $('.myiterator').each(function () {
                                    if ($(this).val() == plan) {
                                        $(this).removeAttr("checked");

                                    }
                                });
                                alert("please try again failed : " + data.status);
                            }
                        },

                        error: function (error) {
                            $('.myiterator').each(function () {
                                if ($(this).val() == plan) {
                                    $(this).removeAttr("checked");

                                }
                            });
                            alert("please try again validation failed");
                        }
                    }


                });

            } else {

                console.log('i am unchecked ' + plan);
                $.ajax({

                    type: "GET",
                    url: "removePlan",
                    contentType: "application/json",
                    data: {userId: userId, plan: plan},
                    dataType: 'json',
                    timeout: 100000,
                    statusCode: {

                        404: function () {
                            $('.myiterator').each(function () {
                                if ($(this).val() == plan) {
                                    $(this).prop('checked', true);
                                    alert(" 404 : Resource not found");


                                }
                            });

                        },
                        500: function () {

                            $('.myiterator').each(function () {
                                if ($(this).val() == plan) {
                                    //$(this).removeAttr("checked");
                                    //$(this).attr('checked','checked');
                                    $(this).prop('checked', true);
                                    alert("500 : internal server error ");


                                }
                            });


                        },
                        200: function (data) {

                            if (data.status != 'Success') {

                                $('.myiterator').each(function () {
                                    if ($(this).val() == plan) {
                                        $(this).prop('checked', true);
                                        alert("200 : please try again");


                                    }
                                });


                            }
                        },

                        error: function (error) {
                            $('.myiterator').each(function () {
                                if ($(this).val() == plan) {
                                    $(this).prop('checked', true);
                                    alert("please try again validation failed");


                                }
                            });

                        }
                    }

                });

            }

        });

    });
</script>