<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Sales</li>
        </ol>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="col-md-offset-5">Add Sales
                        <div class="pull-right">Order No. #${sales.orderNo}</div>
                    </div>
                </div>
                <div class="panel-body">

                    <form method="post" action="${pageContext.request.contextPath}/app/addsalesorder"
                          modelAttribute="sales" id="registerform" novalidate="novalidate">
                        <input type="hidden" name="orderNo" value="${sales.orderNo}" required/>

                        <div class="row">
                            <div class="col-xs-6">
                                <div class="">
                                    <label name="vendorDto">Customer:
                                        <select class="form-control" name="customerId" id="customerId" class="select2"
                                                required="required">
                                            <c:forEach var="clientInfo" oldItems="${customerList}">
                                                <option value="${clientInfo.customerId}"
                                                        <c:if test="${sales.customerId == clientInfo.customerId}">selected</c:if>>${clientInfo.uniqueName}</option>
                                            </c:forEach>
                                        </select>
                                    </label>
                                </div>
                                <div class="">
                                    <label name="orderDate">Order Date:
                                        <input type="text" placeholder="select date" class="datepicker form-control"
                                               id="orderDate" name="orderDate" value="${sales.orderDate}" required/>
                                    </label>
                                    <p>${error.orderDate}</p>
                                </div>

                                <div class="">
                                    <label name="deliveryDate">Delivery Date:
                                        <input type="text" class="datepicker form-control" placeholder="select date"
                                               id="deliveryDate" name="deliveryDate"
                                               value="${sales.deliveryDate}" required/></label>
                                    <p>${error.deliveryDate}</p>
                                </div>
                            </div>
                            <div class="col-xs-0">
                            </div>
                            <div class="col-xs-6 text-right">
                                <div class="">
                                    <label name="shipmentChannel">Shipment Channel:
                                        <select class="form-control" name="shipmentChannel" id="shipmentChannel"
                                                class="select2" required="required">
                                            <c:forEach var="shipment" oldItems="${shipmentList}">
                                                <option value="${shipment}"
                                                        <c:if test="${sales.shipmentChannel== shipment}">selected</c:if>>${shipment}</option>
                                            </c:forEach>
                                        </select></label>
                                </div>
                                <div class="">
                                    <label name="shipmentDate">Shipment Date:
                                        <input type="text" class="datepicker form-control" placeholder="select date"
                                               id="shipmentDate" name="shipmentDate"
                                               value="${sales.shipmentDate}" required/></label>
                                    <p>${error.shipmentDate}</p>
                                </div>
                                <div class="">
                                    <label>Deliver To:
                                        <input class="form-control" type="text" id="deliveryCity" name="deliveryCity"
                                               required placeholder="Enter Address"/></label>
                                </div>
                            </div>
                        </div>

                        <p>${error.itemCheck}</p>
                        <div id="inputDiv">
                            <div id="add_row" class="btn btn-xs btn-primary pull-left"><span
                                    class="glyphicon glyphicon-plus"></span> Add Item
                            </div>
                            <table class="table table-responsive table-striped table-bordered"
                                   id="customFields">
                                <thead class="thead-inverse">
                                <tr>
                                    <th>Item</th>
                                    <th>Quantity</th>
                                    <th>Rate</th>
                                    <th>Tax</th>
                                    <th>Amount</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <input type="hidden" name="oldItemList" value="0"/>
                                <input type="hidden" name="quantity" value="0"/>
                                <c:if test="${fn:length(sales.oldItemDtoList) gt 0}">

                                    <c:forEach var="checkItem" oldItems="${sales.oldItemDtoList}" varStatus="loop">
                                        <tr>
                                            <td>
                                                <select id="js-example-responsive${loop.index}"
                                                        class="form-control input-md  mytest" arrt="${loop.index}"
                                                        style="width: 100%" name="oldItemList" required>
                                                    <c:forEach var="oldItem" oldItems="${oldItemList}">
                                                        <c:set var="valid" scope="page" value="no"/>
                                                        <c:if test="${checkItem.id eq oldItem.id}">
                                                            <c:set var="valid" scope="page" value="yes"/>
                                                        </c:if>
                                                        <option value="${oldItem.id}"
                                                                <c:if test="${valid eq 'yes' }">selected</c:if>>${oldItem.itemName}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td><input type="number" id="input"
                                                       onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                                       pattern="[0-9]{5}" class="form-control qty"
                                                       onKeyup="update_amounts();"
                                                       value="${purchaseOrder.quantities[loop.index]}" name="quantity"
                                                       placeholder="enter quantity" required/></td>
                                            <td><input type="number" step="any" id="rate${loop.index}"
                                                       onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                                       pattern="[0-9]{5}" class="form-control rate"
                                                       value="${checkItem.rate}" name="rate" required readOnly/></td>
                                            <td><input type="number" step="any" id="tax${loop.index}"
                                                       onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                                       pattern="[0-9]{5}" class="form-control tax"
                                                       value="${checkItem.tax}" name="tax" required readOnly/></td>
                                            <td><span id="amount" class="amount">0</span></td>
                                            <td><a href="javascript:void(0);" class="remCF"><span
                                                    class="btn btn-danger btn-xs glyphicon glyphicon-remove"></span></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>

                        <div class="input-group pull-right">
                            <label class="lable">Total Cost </label> <input type="number" value="0.0"
                                                                            id="total" step="any"
                                                                            onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46 || event.charCode == 0 "
                                                                            id="total" class="form-control"
                                                                            name="totalCost" readOnly
                                                                            required/>
                        </div>


                        <div class="col-lg-4">
                            <label name="notes">Notes:</label><br/>
                            <textarea class="form-control" placeholder="write something.." name="notes" id="notes"
                                      rows="3" cols="30" required>${sales.notes}</textarea>
                        </div>

                        <div class="box-footer col-lg-12">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="submit" class="btn btn-primary pull-right" value="CONFIRM"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
<script type="text/javascript">
    function update_amounts() {
        var sum = 0.0;
        $('#customFields > tbody  > tr').each(function () {
            var qty = $(this).find('.qty').val();
            if (qty == null) {
                qty = 0;
            }
            var rate = $(this).find('.rate').val();
            if (rate == null) {
                rate = 0;
            }
            var tax = $(this).find('.tax').val();
            if (tax == null) {
                tax = 0;
            }
            var amount = (qty * rate);
            amount = (amount + ((amount * tax) / 100));
            sum += amount;
            $(this).find('.amount').text('' + amount.toFixed(2));
        });
        var discount = $('#discount').val();
        if (discount == null) {
            discount = 0;
        }
        // document.getElementById("total").value = (sum - ((sum * discount) / 100)).toFixed(4);
        document.getElementById("total").value = (sum - ((sum * discount) / 100));
    }

    $(document).ready(function () {
        $(".select2").select2();
        /*  update_amounts(); */
    });
    $(document).ready(function () {
        var count = 1;
// for dynamically add or remove row
        $("#add_row").click(function () {
            //alert(count);
            var row = "<tr><td><select id='js-example-responsive' class='form-control selectMe  mytest myclass" + count + "' arrt='" + count + "' style='width: 100%' name='oldItemList' ><option value='null' selected>select OldItem</option><c:forEach var='oldItem' oldItems='${oldItemList}'><option value='${oldItem.id}'>${oldItem.itemName}</option></c:forEach></select></td>";
            row += '<td><input type="number" id="quantity' + count + '" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="form-control qty" onkeyup="update_amounts();"   name="quantity" placeholder="enter quantity" required/></td>';
            row += '<td><input type="number" step="any" id="rate' + count + '" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="form-control rate rate' + count + '"  required readOnly/></td>';
            row += '<td><input type="number" step="any" id="tax' + count + '" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="form-control tax tax' + count + '"   required readOnly/></td>';
            row += '<td><span id="amount" class="amount">0</span></td><td> <a href="javascript:void(0);" class="remCF"><span class="btn btn-danger btn-xs glyphicon glyphicon-remove"></span></a></td>';
            $("#customFields").append(row);
            $(".selectMe").select2();// for select2 dropdown
            count++;
        });
        $("#customFields").on('click', '.remCF', function () {
            $(this).parent().parent().remove();
            update_amounts();
        });
    });
    $(document).ready(function () {
        $(".qty").keypress(function () {
            update_amounts();
            console.log("Handler for .keypress() called.");
        });
        $(document).on('change', '.mytest', function () {
            var id = $(this).attr('arrt');
            var itemId = $(this, "option:selected").val();
            if (itemId == null || itemId == "") {
                document.getElementById("quantity" + id).value = 0;
                document.getElementById("rate" + id).value = 0;
                document.getElementById("tax" + id).value = 0;
                update_amounts();
                return;
            }
            $.ajax({
                type: "GET",
                url: "getItem",
                contentType: "application/json",
                data: {itemId, itemId},
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                    if (data.status == 'Success') {

                        document.getElementById("rate" + id).value = data.detail.rate;
                        document.getElementById("tax" + id).value = data.detail.tax;
                        update_amounts();
                    }
                }
            });
        });

        function getPurchaseOrderValue() {
            var orderId = document.getElementById("load-values").value;
            if (orderId == null) {
                return;
            }
            $.ajax({
                type: "GET",
                url: "getPurchaseOrderItems",
                contentType: "application/json",
                data: {orderId, orderId},
                dataType: 'json',
                timeout: 100000,
                success: function (data) {
                    if (data.status == 'Success') {
                        $('#customFields > tbody  >').remove();
                        var row = '<tbody>';
                        for (i = 0; i < data.detail.oldItemDtoList.length; i++) {

                            row += '<tr><td>' + data.detail.oldItemDtoList[i].itemName + '<input type="hidden" name="oldItemList" value="0" /><input type="hidden" name="quantity" value="0" /></td>';
                            row += '<td><input type="hidden" class="qty" value="' + data.detail.oldItemDtoList[i].quantity + '"/>' + data.detail.oldItemDtoList[i].quantity + '</td>';
                            row += '<td ><input type="hidden" class="rate" value="' + data.detail.oldItemDtoList[i].rate + '"/>' + data.detail.oldItemDtoList[i].rate + '</td>';
                            row += '<td><input type="hidden" class="tax" value="' + data.detail.oldItemDtoList[i].tax + '"/>' + data.detail.oldItemDtoList[i].tax + '</td><td><span id="amount" class="amount">0</span></td><td> </td></tr>';

                            //row +='<td><input type="number" id="input" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="qty" onKeyup="update_amounts();"  value="'+data.detail.oldItemDtoList[i].quantity+'" name="quantity" placeholder="enter quantity" required/></td>';
                            //row +='<td><input type="number" step="any" id="rate0" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="rate" value="'+data.detail.oldItemDtoList[i].rate+'" name="rate" required readOnly/></td></td>';
                            //row +='<td><input type="number" step="any" id="tax0" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="tax" value="'+data.detail.oldItemDtoList[i].tax+'" name="tax"  required readOnly/></td><td><span id="amount" class="amount">0</span></td>';
                            //row +='<td> <a href="javascript:void(0);" class="remCF">Remove</a></td></tr>';
                        }
                        row += '</tbody>';

                        $("#customFields").append(row);
                        document.getElementById("vendor").value = data.detail.vendorDto.contactName;
                        update_amounts();

                    }
                }
            });
        }
    });

</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css">
<!-- <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
    $(function () {
        $(".datepicker").datepicker({
            /* dateFormat: 'yy-mm-dd' */
        });
    });
</script>

<script type="text/javascript">
    $(document).ready(function () {
        $("#js-example-responsive").select2();


        $("#registerform").validate({

            // Specify the validation rules
            rules: {


                deliveryCity: {
                    required: true,
                    maxlength: 100
                },
                shipmentChannel: {
                    required: true

                },
                orderDate: {
                    required: true,
                    maxlength: 10,
                    minlength: 10
                },
                shipmentDate: {
                    required: true,
                    maxlength: 10,
                    minlength: 10
                },
                deliveryDate: {
                    required: true,
                    maxlength: 10,
                    minlength: 10
                },
                notes: {
                    required: false,
                    maxlength: 2000

                }
            },
            // Specify the validation error messages
            messages: {


                deliveryCity: {
                    required: "Please provide a deliveryCity",
                    maxlength: "Your deliveryCity must be less than 100"
                },

                shipmentChannel: {
                    required: "Please provide a shipmentChannel"

                },

                orderDate: {
                    required: "Please provide a orderDate",
                    maxlength: "not a valid date",
                    minlength: "not a valid date"
                },

                shipmentDate: {
                    required: "Please provide a shipmentDate",
                    maxlength: "not a valid date",
                    minlength: "not a valid date"
                },
                deliveryDate: {
                    required: "Please provide a deliveryDate",
                    maxlength: "not a valid date",
                    minlength: "not a valid date"
                },
                notes: {
                    required: "Please provide a address",
                    maxlength: "Your deliveryCity must be less than 1000"

                }
            },

            submitHandler: function (form) {
                form.submit();
            }
        });
    });
</script>
	
	
