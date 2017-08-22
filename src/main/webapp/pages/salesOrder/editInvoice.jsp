<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<%@include file="/pages/common/businessownernavbar.jsp" %>
<%@include file="/pages/common/businessownersidebar.jsp" %>
<%@include file="/pages/common/header.jsp" %>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="../resources/landing/js/select2.full.js"></script>
<script>
    $(function () {

        $("#datepicker, #datepicker2").datepicker({

            dateFormat: 'yy-mm-dd'

        });
    });
</script>
<link
        href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
        rel="stylesheet">

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="\">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Home</li>
        </ol>
    </div>
    <!--/.row-->

    <div class="row">

        <div class="col-lg-12">
            <h1 class="page-header">Edit Invoice</h1>
        </div>
    </div>
    <!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <div class="row">
        <div class="col-md-12">
            <div class="break"></div>
            <form action="${pageContext.request.contextPath}/app/addinvoice"
                  method="POST" modelAttribute="invoiceDto" enctype="multipart/form-data"
                  class="col-md-12">


                <div class="input-group" id="inputDiv">
                    <label class="lable">Invoice No * </label><input type="text"
                                                                     id="input" class="form-control input-md"
                                                                     value="${invoiceInfo.invoiceNo}" required
                                                                     readOnly/>
                    <br/>
                    <p class="error">${error.invoiceNo}</p>
                    <br/>
                </div>

                <input type="hidden" name="oldItemIdList" value="0">
                <input type="hidden" name="quantity" value="0">
                <input type="hidden" name="invoiceId" value="${invoiceInfo.invoiceId}">

                <div class="salediv">
                    <span>${invoive.customerName}</span>
                    <p class="error">${error.salesOrder}</p>

                </div>

                <div class="input-group" id="cn">
                    <label class="lable">Customer * </label></br>
                    <label class="lable " id="customerName">${invoiceInfo.customerName}</label>
                    <br/>
                    <p class="error"></p>
                    <br/>
                </div>

                <div class="input-group" id="inputDiv">
                    <label class="lable">Date * </label> <input type="text"
                                                                id="datepicker" name="invoiceDate"
                                                                value="${invoiceInfo.invoiceDate }"
                                                                required/> <br/>
                    <p class="error">${error.invoiceDate}</p>
                    <br/>
                </div>

                <div class="input-group" id="inputDiv">
                    <label class="lable">Due Date * </label> <input type="text"
                                                                    id="datepicker2" name="dueDate"
                                                                    value="${invoiceInfo.dueDate }"
                                                                    required/> <br/>
                    <p class="error">${error.dueDate}</p>
                    <br/>
                </div>


                <script type="text/javascript">


                    var count = 1;


                    // for dynamically add or remove row
                    $(document).ready(function () {
                        $("#add_row").click(function () {


                            var row = "<tr><td><select id='js-example-responsive' class='form-control input-md selectMe  mytest myclass" + count + "' arrt='" + count + "' style='width: 100%' name='oldItemIdList' ><option value='null' selected>select OldItem</option><c:forEach var='oldItem' oldItems='${oldItemList}'><option value='${oldItem.id}'>${oldItem.itemName}</option></c:forEach></select></td>";
                            row += '<td><input type="number" id="quantity' + count + '" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="qty" onKeyup="update_amounts();"   name="quantity" placeholder="enter quantity" required/></td>';
                            row += '<td><input type="number" step="any" id="rate' + count + '" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="rate rate' + count + '"  required readOnly/></td>';
                            row += '<td><input type="number" step="any" id="tax' + count + '" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="tax tax' + count + '"   required readOnly/></td>';
                            row += '<td><span id="amount" class="amount">0</span></td><td> <a href="javascript:void(0);" class="remCF">Remove</a></td>';
                            $("#customFields").append(row);


                            $(".myclass" + count).select2();// for select2 dropdown

                            count++;
                        });

                        $("#customFields").on('click', '.remCF', function () {
                            $(this).parent().parent().remove();

                            update_amounts();
                        });
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

                        //alert(discount);
                        document.getElementById("total").value = (sum - ((sum * discount) / 100)).toFixed(2);

                    }
                    $(document).ready(function () {
                        // for to put rate tax of selected oldItem
                        $(document).on('change', '#js-example-responsive', function () {
                            var id = $(this).attr('arrt');
                            var itemId = $(this, "option:selected").val();
                            //alert("hello");

                            if (itemId == null) {
                                document.getElementById("quantity" + id).value = 0;
                                document.getElementById("rate" + id).value = 0;
                                document.getElementById("tax" + id).value = 0;
                                update_amounts();
                                return;
                            }
                            //alert(itemId);

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


                        // for autopopulate of oldItem from selected salesorder
                        $(document).on('change', '.sale', function () {
                            var salesOrderId = $(this).val();
                            console.log("dhiraj" + salesOrderId);
                            if (salesOrderId == null || salesOrderId == 0) {
                                $('.auto').each(function () {
                                    $(this).remove();
                                    var row = '<tr class="auto"><td><input type="hidden" name="oldItemIdList" value="" /><input type="hidden" name="quantity" value="" /></td></tr>';
                                    $("#customFields").append(row);
                                    document.getElementById("clientInfo").value = "";
                                });
                                return;
                            }
                            //alert(salesOrderId);
                            $.ajax({
                                type: "GET",
                                url: "getSalesOrderWithId",
                                contentType: "application/json",
                                data: {salesOrderId, salesOrderId},
                                dataType: 'json',
                                timeout: 100000,
                                success: function (data) {
                                    if (data.status == 'Success') {
                                        var oldItemList = data.detail.oldItemDtoList;
                                        var quantityList = data.detail.quantities;
                                        //console.log(oldItemList[0].itemName);
                                        $('.auto').each(function () {
                                            $(this).remove();
                                        });
                                        for (i = 0; i < oldItemList.length; i++) {

                                            var row = '<tr class="auto">';
                                            row += '<td><label class="lable">' + oldItemList[i].itemName + '</label></td>';
                                            row += '<td>' + quantityList[i] + '<input type="hidden" class="qty" value="' + quantityList[i] + '"/></td>';
                                            row += '<td ><input type="hidden" class="rate" value="' + oldItemList[i].rate + '"/>' + oldItemList[i].rate + '</td>';
                                            row += '<td><input type="hidden" class="tax" value="' + oldItemList[i].tax + '"/>' + oldItemList[i].tax + '</td><td><span id="amount" class="amount">0</span></td>';
                                            row += '</tr>';
                                            $("#customFields").append(row);
                                        }


                                        document.getElementById("clientInfo").value = data.detail.customerId;
                                        document.getElementById("customerName").innerHTML = data.detail.customerName;
                                        update_amounts();

                                    }
                                }
                            });
                        });
                    });


                </script>

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
                        <c:if test="${fn:length(invoiceInfo.salesDto) gt 0}">

                            <c:forEach var="oldItem" oldItems="${invoiceInfo.salesDto.oldItemDtoList}" varStatus="loop">
                                <tr>
                                    <td><span>${oldItem.itemName}</span><input type="hidden" name="oldItemList"
                                                                               value="${oldItem.id}" required readOnly/>
                                    </td>
                                    <td><input type="number" id="input"
                                               onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                               pattern="[0-9]{5}" class="qty" onKeyup="update_amounts();"
                                               value="${oldItem.quantity}" name="quantity" readOnly required/></td>
                                    <td><input type="number" step="any"
                                               onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                               pattern="[0-9]{5}" class="rate" value="${oldItem.rate}" name="rate"
                                               required readOnly/></td>
                                    <td><input type="number" step="any"
                                               onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                               pattern="[0-9]{5}" class="tax" value="${oldItem.tax}" name="tax" required
                                               readOnly/></td>
                                    <td><span id="amount" class="amount">0</span></td>
                                    <td><a href="javascript:void(0);" class="remCF">Remove</a></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${fn:length(invoiceInfo.oldItemList) gt 0}">

                            <c:forEach var="oldItem" oldItems="${invoiceInfo.oldItemList}" varStatus="loop">
                                <tr>
                                    <td><span>${oldItem.itemName}</span><input type="hidden" name="oldItemList"
                                                                               value="${oldItem.id}" required readOnly/>
                                    </td>
                                    <td><input type="number" id="input"
                                               onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                               pattern="[0-9]{5}" class="qty" onKeyup="update_amounts();"
                                               value="${oldItem.quantity}" name="quantity" placeholder="enter quantity"
                                               required/></td>
                                    <td><input type="number" step="any"
                                               onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                               pattern="[0-9]{5}" class="rate" value="${oldItem.rate}" name="rate"
                                               required readOnly/></td>
                                    <td><input type="number" step="any"
                                               onkeypress="return event.charCode > 47 && event.charCode < 58;"
                                               pattern="[0-9]{5}" class="tax" value="${oldItem.tax}" name="tax" required
                                               readOnly/></td>
                                    <td><span id="amount" class="amount">0</span></td>
                                    <td><a href="javascript:void(0);" class="remCF">Remove</a></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                    <a id="add_row" class="btn btn-default pull-left">Add Item</a>
                </div>
                <div class="input-group" id="inputDivRightTop">
                    <label class="lable">discount * </label> <input type="number"
                                                                    step="any"
                                                                    onkeypress="return (event.charCode >= 48 && event.charCode <= 57) ||
   event.charCode == 46 || event.charCode == 0 "
                                                                    id="discount" class="form-control input-md double"
                                                                    name="discount"
                                                                    onKeyup="update_amounts();"
                                                                    value="${invoiceInfo.discount}" required/> <br/>
                    <p class="error">${error.discount}</p>
                    <br/>
                </div>

                <div class="input-group" id="inputDivRightBottom">
                    <label class="lable">totalCost * </label> <input type="number"
                                                                     id="total" step="any"
                                                                     onkeypress="return (event.charCode >= 48 && event.charCode <= 57) ||
   event.charCode == 46 || event.charCode == 0 "
                                                                     id="total" class="form-control input-md"
                                                                     value="${invoiceInfo.totalCost}" readOnly
                                                                     required/> <br/> <br/>
                </div>


                <div class="input-group" id="inputDiv">
                    <label class="lable">notes* </label>
                    <textarea rows="4" cols="50" id="input"
                              class="form-control input-md" name="notes" value="${invoiceInfo.notes}"
                              required></textarea>
                    <br/>
                    <p class="error">${error.notes}</p>
                    <br/>
                </div>

                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/> <input type="submit" value="upadte Invoice"
                                                       class="btn btn-sm btn-success">

            </form>
        </div>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
<style>
    .input {
        float: right;
        margin-left: 0px;
        margin-right: 900px;
        margin-top: 0px;
    }

    .lable {
        float: left;
    }

    #inputDiv {
        margin-top: 10px;
    }

    #inputDivRightTop {
        margin-top: 10px;
        float: right;
        margin-right: 10px;
    }

    #inputDivRightBottom {
        margin-top: 10px;
        float: left;
        margin-left: 790px;
    }
</style>

