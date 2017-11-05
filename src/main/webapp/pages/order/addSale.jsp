<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/29/17
  Time: 10:43 PM
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
            <div class="col-md-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Add Sales Order</h3>
                        <div class="pull-right">Order No. #${orderNo}<p class="form-error">${orderError.orderNo}</p><p class="form-error">${orderError.error}</p></div>
                    </div>
                    <form action="${pageContext.request.contextPath}/order/sale/save" method="post" modelAttribute="order">
                        <div class="box-body">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <input type="hidden" name="orderNo" value="${orderNo}"/>
                                    <label>Customer Name</label><a href="" class="pull-right"> Create a New Customer</a>
                                    <select class="choose1 form-control" name="clientId"></select>
                                    <p class="form-error">${orderError.clientInfo}</p>
                                </div>
                            </div>
                            <div class="col-md-4">&nbsp;</div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Delivery Date:</label>
                                    <div class='input-group date'>
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" class="form-control datepicker" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${item.deliveryDate}"/>" name="deliveryDate" placeholder="Delivery Date"/>
                                    </div>
                                    <p class="form-error">${itemError.deliveryDate}</p>
                                </div>
                            </div>
                        <%--</div>

                        <div class="row">--%>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Order Date:</label>
                                    <div class='input-group date' style="position: relative;">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" class="form-control datepicker" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${item.orderDate}"/>" name="orderDate" placeholder="Order Date"/>
                                    </div>
                                    <p class="form-error">${itemError.orderDate}</p>
                                </div>
                            </div>
                            <div class="col-md-4">&nbsp;</div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label>Deliver To:</label>
                                    <div class="input-group">
                                        <div class="input-group-addon">
                                            <i class="fa fa-map-marker"></i>
                                        </div>
                                        <input class="form-control" value="" type="text" id="searchTextField" name="deliveryAddress" placeholder="Enter Address" required/>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-12">
                            <div class="form-group">
                                <button class="btn btn-xs btn-flat btn-primary pull-right" type="button" id="add_row">
                                    <span class="glyphicon glyphicon-plus"></span> Add Item
                                </button>
                            </div>
                            <div class="table-responsive">
                            <table class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>item</th>
                                    <th>quantity</th>
                                    <th>rate</th>
                                    <th>discount(%)</th>
                                    <th>total</th>
                                    <th></th>
                                </tr>
                                </thead>

                                <tbody id="customFields">

                                </tbody>
                            </table>
                            </div>
                        </div>

                        <div class="col-md-4">
                        <div class="form-group">
                            <label for="description">Description:</label><br/>
                            <textarea class="form-control" placeholder="write something.." name="description"
                                      id="description" rows="3" cols="30" required>${order.description}</textarea>
                            <p class="form-error">${orderError.description}</p>
                        </div>
                        </div>
                            <div class="col-md-6">&nbsp;</div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <label class="lable">Tax(%) </label>
                                <input type="number" value="0.0" id="tax" step="any"
                                                                            onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46 || event.charCode == 0 "
                                                                            class="form-control"
                                       onkeyup='calculate(amountUpdate);'
                                                                            name="tax" required/>
                            </div>
                            <div class="form-group">
                                <label>Total Cost </label>
                                <span>Rs.<strong id="total">0</strong></span>
                            </div>
                        </div>

                        </div>
                        <div class="box-footer">
                            <button type="submit" class="btn btn-primary btn-flat btn-sm pull-right">Save changes</button>
                        </div>
                    </form>
                <!-- /.box -->
            </div>
        </div>
        </div>
    </section>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<%@include file="/pages/parts/footer.jsp" %>

<script>
    $(document).ready(function() {

        $(".choose1").select2({
            ajax: {
                url: '${pageContext.request.contextPath}/client/customer/search',
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

                        if(value.companyName === null) {

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
            placeholder: "Search Customer by Name & Mobile No"
        });
    });
</script>

<script type="text/javascript">
    $(document).ready(function () {
        var max = 1;
        var count = 1;
// for dynamically add or remove row
        $("#add_row").click(function () {
            //alert(count);
            if (max === 10){
                alert("max 10");
                return;
            }
            var row = "<tr class='border-bottom itemTable' >";
            row += "<td><select class='form-control item' name='' url='${pageContext.request.contextPath}/item/show'><option value=''>select item</option><c:forEach items="${itemList}" var="item"><option value='${item.itemId}'>${item.productInfo.name}-${item.tagInfo.name}</option></c:forEach> </select></td>";
            row += "<td><input type='number' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control form-control-sm' onkeyup='calculate(amountUpdate);'  name='' placeholder='enter quantity' required/></td>";
            row += "<td><input type='number' class='form-control form-control-sm' name='' required readonly/></td>";
            row += "<td><input type='number' step='any' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control form-control-sm' onkeyup='calculate(amountUpdate);' name='' placeholder='enter discount percent'  required /></td>";
            row += "<td class='text-right'>Rs.<span>0</span></div>";
            row += "<td><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></td>";
            row += "</tr>";
            $("#customFields").prepend(row);
            $(".item").select2();
            count++;
            max ++;
            updateName();
        });
        $("#customFields").on('click', '.remCF', function () {
            $(this).parent().parent().remove();
            max--;
            updateName();
        });
    });


    function updateName() {
        $("tr").each(function (index) {
            index = index -1;
            $(this).find("td:eq(0) > select").attr("name" , "").attr("name" , "orderItemInfoDTOList["+index+"].itemInfoId");
            $(this).find("td:eq(1) > input").attr("name" , "").attr("name" , "orderItemInfoDTOList["+index+"].quantity");
            $(this).find("td:eq(2) > input").attr("name" , "").attr("name" , "orderItemInfoDTOList["+index+"].rate");
            $(this).find("td:eq(3) > input").attr("name" , "").attr("name" , "orderItemInfoDTOList["+index+"].discount");
        })
    }

</script>



