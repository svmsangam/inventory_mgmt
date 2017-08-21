<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/common/header.jsp" %>
<%@include file="/pages/common/businessownernavbar.jsp" %>
<%@include file="/pages/common/businessownersidebar.jsp" %>


<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Bill</li>
        </ol>
    </div><!--/.row-->

    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="col-md-offset-5">New Bill
                        <div class="pull-right">Bill No. #${billPurchaseOrder.billNo}</div>
                    </div>
                </div>
                <div class="panel-body">

                    <form method="post" action="${pageContext.request.contextPath}/app/editbillpurchaseorder"
                          modelAttribute="billDto" enctype="multipart/form-data" id="registerform"
                          novalidate="novalidate">
                        <input type="hidden" name="billId" value="${billPurchaseOrder.billId}"/>
                        <input type="hidden" name="billNo" value="${billPurchaseOrder.billNo}"/>

                        <div class="row">
                            <div class="col-lg-4">
                                <label name="purchaseOrderDto">Order No:</label>

                                <select name="purchaseOrderDto.orderId" onChange="getPurchaseOrderValue();"
                                        id="load-values" class="form-control select2" required>

                                    <c:forEach var="purchaseOrder" oldItems="${purchaseOrderList}">
                                        <option value="${purchaseOrder.orderId}"
                                                <c:if test="${billPurchaseOrder.purchaseOrderDto.orderId == purchaseOrder.orderId}">selected</c:if>>${purchaseOrder.orderNo}</option>
                                    </c:forEach>
                                    <option value="0"
                                            <c:if test="${billPurchaseOrder.purchaseOrderDto.orderId eq 0}">selected="selected"</c:if>>
                                        Select Order No
                                    </option>
                                </select>
                            </div>

                            <div class="col-lg-4">
                                <label name="vendorDto">Vendor: </label>
                                <input type="text" id="vendor" class="form-control " name="vendorDto.contactName"
                                       value="${billPurchaseOrder.vendorDto.contactName}" required/>
                                <p class="error">${error.contactName}</p>
                            </div>

                            <div class="col-lg-4">
                                <label name="billDate">Bill Date:</label>
                                <input type="text" id="billDate" class="form-control datepicker" name="billDate"
                                       value="${billPurchaseOrder.billDate}" required/>
                                <p>${error.billDate}</p>
                            </div>

                            <div class="col-lg-4">
                                <label name="dueDate">Due Date:</label>
                                <input type="text" id="dueDate" class="form-control datepicker" name="dueDate"
                                       value="${billPurchaseOrder.dueDate}" required/>
                                <p>${error.dueDate}</p>
                            </div>
                        </div>

                        <p class="error">${error.itemCheck}</p>
                        <div id="inputDiv">
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
                                <c:if test="${fn:length(billPurchaseOrder.oldItemDtoList) gt 0}">

                                    <c:forEach var="checkItem" oldItems="${billPurchaseOrder.oldItemDtoList}"
                                               varStatus="loop">
                                        <tr>
                                            <td>
                                                <select id="js-example-responsive${loop.index}"
                                                        class="form-control mytest" arrt="${loop.index}"
                                                        style="width: 100%" name="oldItemList" required>
                                                    <option value="" selected>Select Item</option>
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
                                                       value="${billPurchaseOrder.quantities[loop.index]}"
                                                       name="quantity" placeholder="enter quantity" required/></td>
                                            <td><input type="text" step="any" id="rate${loop.index}"
                                                       onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                                       pattern="[0-9]{5}" class="form-control rate"
                                                       value="${checkItem.rate}" name="rate" required readOnly/></td>
                                            <td><input type="text" step="any" id="tax${loop.index}"
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
                                <c:if test="${fn:length(billPurchaseOrder.purchaseOrderDto.oldItemDtoList) gt 0}">
                                    <c:forEach oldItems="${billPurchaseOrder.purchaseOrderDto.oldItemDtoList}"
                                               var="purchaseOrderItems" varStatus="loop">
                                        <tr>
                                            <td>${purchaseOrderItems.itemName}</td>
                                            <td>${purchaseOrderItems.quantity}<input type="hidden" class="qty"
                                                                                     value="${billPurchaseOrder.purchaseOrderDto.quantities[loop.index]}"/>
                                            </td>
                                            <td>${purchaseOrderItems.rate}<input type="hidden" class="rate"
                                                                                 value="${purchaseOrderItems.rate}"/>
                                            </td>
                                            <td>${purchaseOrderItems.tax}<input type="hidden" class="tax"
                                                                                value="${purchaseOrderItems.tax}"/></td>
                                            <td><span id="amount" class="amount">0</span></td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                            <a id="add_row" class="btn btn-default pull-left">Add Item</a>
                        </div>

                        <div class="form-group pull-right">
                            <label class="lable">Total Cost </label> <input type="text"
                                                                            value="${billPurchaseOrder.totalCost}"
                                                                            step="any"
                                                                            onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46 || event.charCode == 0 "
                                                                            id="totalCost" class="form-control"
                                                                            name="totalCost" readOnly
                                                                            required/>
                        </div>

                        <div class="col-md-4">
                            <label name="notes">Notes:</label><br/>
                            <textarea name="notes" id="notes" rows="4"
                                      class="form-control">${billPurchaseOrder.notes}</textarea>
                        </div>

                        <div class="col-lg-12">
                            <input type="submit" class="btn btn-primary pull-right" value="Update"/>
                        </div>
                    </form>
                    <div>
                        <%-- <img src="/images/billImageFile/${billPurchaseOrder.billId}.png" alt="" />  --%>
                        <%-- <img src="file://E:/billImage/${billPurchaseOrder.billId}.png" alt=""/> --%>
                    </div>
                </div><!-- /.col-->
            </div><!-- /.row -->
        </div><!--/.main-->
        <%@include file="../common/footer.jsp" %>
        <script type="text/javascript">
            $(document).ready(function () {
                update_amounts();
                var orderId = document.getElementById("load-values").value;
                if (orderId == 0) {
                    $('#add_row').show();
                } else {
                    $('#add_row').hide();
                }
            });
            var count = 1;
            // for dynamically add or remove row
            $("#add_row").click(function () {
                //alert(count);
                var row = "<tr class='add'><td><select id='js-example-responsive' class='form-control input-md selectMe  mytest myclass" + count + "' arrt='" + count + "' style='width: 100%' name='oldItemList' ><option value='null' selected>select OldItem</option><c:forEach var='oldItem' oldItems='${oldItemList}'><option value='${oldItem.id}'>${oldItem.itemName}</option></c:forEach></select></td>";
                //var row = '<tr><td><select id="js-example-responsive'+count+'" class="form-control input-md  mytest" arrt="'+count+'" style="width: 100%" name="oldItemList" required><option value="" selected>Select OldItem</option><c:forEach var="oldItem" oldItems="${oldItemList}"><option value="${oldItem.id}">${oldItem.itemName}</option></c:forEach></select></td>';
                row += '<td><input type="number" id="quantity' + count + '" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="qty" onKeyup="update_amounts();"   name="quantity" placeholder="enter quantity" required/></td>';
                row += '<td><input type="text" step="any" id="rate' + count + '" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="rate rate' + count + '"  required readOnly/></td>';
                row += '<td><input type="text" step="any" id="tax' + count + '" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="tax tax' + count + '"   required readOnly/></td>';
                row += '<td><span id="amount" class="amount">0</span></td><td> <a href="javascript:void(0);" class="remCF"><span class="btn btn-danger btn-xs glyphicon glyphicon-remove"></span></a></td>';
                $("#customFields").append(row);
                $(".selectMe").select2();// for select2 dropdown
                count++;
            });
            $("#customFields").on('click', '.remCF', function () {
                $(this).parent().parent().remove();
                update_amounts();
            });
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
                document.getElementById("totalCost").value = (sum - ((sum * discount) / 100)).toFixed(2);
            }
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
                if (orderId == 0) {
                    $('#customFields > tbody  >').remove();
                    $('#add_row').show();
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
                            $('.auto').each(function () {
                                $(this).remove();
                            });
                            $('#customFields > tbody  >').remove();
                            var quantityList = data.detail.quantities;
                            for (i = 0; i < data.detail.oldItemDtoList.length; i++) {

                                var row = '<tr class="auto"><td>' + data.detail.oldItemDtoList[i].itemName + '<input type="hidden" name="oldItemList" value="0" /><input type="hidden" name="quantity" value="0" /></td>';
                                row += '<td><input type="hidden" class="qty" value="' + quantityList[i] + '"/>' + quantityList[i] + '</td>';
                                row += '<td ><input type="hidden" class="rate" value="' + data.detail.oldItemDtoList[i].rate + '"/>' + data.detail.oldItemDtoList[i].rate + '</td>';
                                row += '<td><input type="hidden" class="tax" value="' + data.detail.oldItemDtoList[i].tax + '"/>' + data.detail.oldItemDtoList[i].tax + '</td><td><span id="amount" class="amount">0</span></td><td> </td></tr>';
                                $("#customFields").append(row);
                                //row +='<td><input type="number" id="input" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="qty" onKeyup="update_amounts();"  value="'+data.detail.oldItemDtoList[i].quantity+'" name="quantity" placeholder="enter quantity" required/></td>';
                                //row +='<td><input type="number" step="any" id="rate0" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="rate" value="'+data.detail.oldItemDtoList[i].rate+'" name="rate" required readOnly/></td></td>';
                                //row +='<td><input type="number" step="any" id="tax0" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="tax" value="'+data.detail.oldItemDtoList[i].tax+'" name="tax"  required readOnly/></td><td><span id="amount" class="amount">0</span></td>';
                                //row +='<td> <a href="javascript:void(0);" class="remCF">Remove</a></td></tr>';
                            }

                            document.getElementById("vendor").value = data.detail.vendorDto.contactName;
                            update_amounts();
                            $('#add_row').hide();
                        }
                    }
                });
            }
        </script>
        <script>
            $(function () {
                $(".datepicker").datepicker({});
            });
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $(".chooseMe").select2();
            });
        </script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#registerform").validate({
                    // Specify the validation rules
                    rules: {
                        billDate: {
                            required: true,
                            maxlength: 10,
                            minlength: 10
                        },
                        dueDate: {
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
                        invoiceNo: {
                            required: "Please provide a shipmentChannel"
                        },
                        billDate: {
                            required: "Please provide a billDate",
                            maxlength: "not a valid date",
                            minlength: "not a valid date"
                        },
                        dueDate: {
                            required: "Please provide a shipmentDate",
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