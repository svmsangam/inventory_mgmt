<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 7/6/17
  Time: 2:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <input class="context" type="hidden" value="${pageContext.request.contextPath}">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Invoice</li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">Generate Invoie</div>
                <div class="panel-body">
                    <div id="generate" class="show">
                        <form action="${pageContext.request.contextPath}/invoiceInfo/generate" method="POST"
                              modelAttribute="productInfo"
                              enctype="multipart/form-data" class="col-lg-8">


                            <div class="form-group">
                                <label class="lable">order * </label>
                                <select required="required" class="choose1 form-control"
                                        url="${pageContext.request.contextPath}/order/searchsale" name="orderId">

                                </select>
                            </div>

                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="submit" value="Generate Invoice" class="btn btn-primary pull-left">
                            <input type="button" value="Custom Invoice" class="btn btn-primary pull-right Custom">
                        </form>
                    </div>
                    <div id="custom" class="hide">

                        <form method="post" action="${pageContext.request.contextPath}/invoiceInfo/custom"
                              modelAttribute="invoiceInfo" id="registerform" novalidate="novalidate">
                            <%--<input type="hidden" name="orderNo" value="${orderNo}" required/>
                            <input type="hidden" name="invoiceNo" value="${invoiceNo}" required/>--%>
                            <div class="row">
                                <div class="col-xs-6">

                                    <div class="form-group col-xs-6">
                                        <label name="orderDate">Buyer Type:</label>

                                        <select name="buyerType" class="form-control" id="buyerType">
                                            <option>select buyerType</option>
                                            <c:forEach items="${buyerTypeList}" var="buyerType">
                                                <option value="${buyerType}">${buyerType}</option>
                                            </c:forEach>
                                        </select>

                                        <p>${error.buyerType}</p>
                                    </div>

                                    <div class="form-group col-xs-6">
                                        <label name="orderDate">Buyer:</label>
                                        <div class="resultCustomer" id="customerResult"></div>
                                        <input type="text" placeholder="clientInfo name"
                                               class="searchCustomer form-control" id="customerName" required/>
                                        <a class="addCustomer btn btn-info btn-sm hide" style="margin-top: 8px;"
                                           data-toggle="modal" data-target="#myModal">add clientInfo</a>
                                        <input type="hidden" id="customerId" name="buyerId" required/>

                                        <p>${error.cutomerName}</p>
                                    </div>

                                    <div class="form-group col-xs-12">
                                        <label name="orderDate">Order Date:
                                            <input type="text" placeholder="select date" class="datepicker form-control"
                                                   id="orderDate" name="orderDate" value="${sales.orderDate}" required/>
                                        </label>
                                        <p>${error.orderDate}</p>
                                    </div>

                                    <div class="form-group col-xs-12">
                                        <label name="deliveryDate">Delivery Date:
                                            <input type="text" class="datepicker form-control" placeholder="select date"
                                                   id="deliveryDate" name="deliveryDate"
                                                   value="${sales.deliveryDate}" required/></label>
                                        <p>${error.deliveryDate}</p>
                                    </div>
                                </div>

                                <div class="col-xs-6 text-right">
                                    <div class="form-group">
                                        <label name="shipmentChannel">Shipment Channel:
                                            <select class="form-control select2" name="shipmentChannel"
                                                    id="shipmentChannel" class="select2" required="required">
                                                <c:forEach var="shipment" items="${shipmentList}">
                                                    <option value="${shipment}"
                                                            <c:if test="${sales.shipmentChannel== shipment}">selected</c:if>>${shipment}</option>
                                                </c:forEach>
                                            </select></label>
                                    </div>
                                    <div class="form-group">
                                        <label name="shipmentDate">Shipment Date:
                                            <input type="text" class="datepicker form-control" placeholder="select date"
                                                   id="shipmentDate" name="shippingDate"
                                                   value="${sales.shipmentDate}" required/></label>
                                        <p>${error.shipmentDate}</p>
                                    </div>
                                    <div class="form-group">
                                        <label>Deliver To:
                                            <input class="form-control searchTextField" value="" type="text"
                                                   id="searchTextField" name="deliveredTo" placeholder="Enter Address"
                                                   required/></label>
                                    </div>
                                </div>
                            </div>

                            <p>${error.itemCheck}</p>
                            <div id="inputDiv">


                                <div id="add_row" class="btn btn-xs btn-primary pull-left"><span
                                        class="glyphicon glyphicon-plus"></span> Add Item
                                </div>

                                <div class="clearfix"></div>
                                <br>


                                <%-- STATIC SEARCH DIV--%>

                                <div class="row table-header table-row">
                                    <div class="col-md-3">Item</div>
                                    <div class="col-md-2">Quantity</div>
                                    <div class="col-md-2">Rate</div>
                                    <div class="col-md-2">Discount (%)</div>
                                    <div class="col-md-2">Amount</div>
                                    <div class="col-md-1"></div>
                                </div>

                                <div id="customFields">


                                </div>

                                <%--END SEARCH DIV--%>

                            </div>


                            <div class="input-group pull-right">
                                <label class="lable">Tax(%) </label> <input type="number" value="0.0" id="tax"
                                                                            step="any"
                                                                            onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46 || event.charCode == 0 "
                                                                            class="form-control" onKeyup="calculate();"
                                                                            name="tax" required/>

                                <br>
                                <label class="lable">Total Cost </label> <input type="number" value="0.0"
                                                                                id="total" step="any"
                                                                                onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46 || event.charCode == 0 "
                                                                                id="total" class="form-control"
                                                                                name="totalCost" readOnly
                                                                                required/>
                            </div>


                            <div class="col-lg-4">
                                <label for="description">Description:</label><br/>
                                <textarea class="form-control" placeholder="write something.." name="description"
                                          id="description" rows="3" cols="30" required>${sales.notes}</textarea>
                            </div>

                            <div class="clearfix"></div>

                            <div class="col-lg-4 pull-left">
                                <div class="invoiceInfo-details-block">
                                    <span class="title text-uppercase">Payment</span>
                                    <div class="form-group">
                                        <label>Paid Amount * </label>

                                        <input type="number" name="bidAmount" class="form-control" required/>

                                        <label>Payment Method * </label>
                                        <select class="form-control" name="paymentMethod" required>
                                            <c:forEach items="${paymentMethodList}" var="method">
                                                <c:if test="${method ne 'NON'}">
                                                    <option value="${method}">${method}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label>Payment Remark * </label>
                                        <input type="text" name="bidRemark" class="form-control" required/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-4 pull-right" style="margin-top: 5px;">

                                <div class="invoiceInfo-details-block ">
                                    <span class="title text-uppercase">Refund</span>
                                    <div class="form-group">
                                        <label>Return Amount </label>
                                        <input type="text" name="returnAmount" class="form-control"/>
                                    </div>


                                    <div class="form-group">
                                        <label>Return Method </label>
                                        <select class="form-control" name="returnType" required>
                                            <c:forEach items="${paymentMethodList}" var="method">
                                                <c:if test="${method ne 'NON'}">
                                                    <option value="${method}">${method}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label>Return Remark </label>
                                        <input type="text" name="returnRemark" class="form-control"/>
                                    </div>
                                </div>
                            </div>


                            <div class="box-footer col-lg-12">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="submit" class="btn btn-primary btn-block" value="CONFIRM"/>
                            </div>
                        </form>


                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Add Customer</h4>
                </div>
                <div class="modal-body customerData">
                    <div class="form-group">
                        <label class="lable">Name *</label>
                        <input type="text" class="form-control" id="name" required/>

                        <p class="error name"></p>
                    </div>

                    <div class="form-group">
                        <label class="lable">Contact *</label>
                        <input type="text" class="form-control" id="contact" required/>

                        <p class="error contact"></p>
                    </div>

                    <div class="form-group">
                        <label class="lable">Address *</label>
                        <input type="text" class="form-control" id="address" required/>

                        <p class="error address"></p>
                    </div>

                    <div class="form-group">
                        <label class="lable">Email *</label>
                        <input type="text" class="form-control" id="email" required/>

                        <p class="error email"></p>
                    </div>

                    <div class="form-group">
                        <label class="lable">Alias *</label>
                        <input type="text" class="form-control" id="alias" required/>

                        <p class="error alias"></p>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" url="${pageContext.request.contextPath}/clientInfo/savecustomer"
                            class="savecustomer btn btn-success pull-left">Save
                    </button>
                    <button type="button" class="closeme btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>


</div>
<%@include file="../common/footer.jsp" %>

<script>
    $(document).ready(function () {

        var url = $(".choose1").attr("url");

        $(".choose1").select2({
            ajax: {
                url: url,
                dataType: 'json',
                headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                delay: 250,
                type: 'GET',
                data: function (params) {
                    return {
                        term: params.term, // search term
                        /* page: params.page*/
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    var arr = [];
                    $.each(data.detail, function (index, value) {
                        arr.push({
                            id: value.orderId,
                            text: "( No. " + value.orderNo + " ) ( amount: " + value.grandTotal.toFixed(2) + " ) ( date: " + moment(value.orderDate).format("ll") + " ) ( " + moment(moment(value.orderDate).format("YYYYMMDD"), "YYYYMMDD").fromNow() + " )"
                        })
                    });


                    return {
                        results: arr/*,
                         pagination: {
                         more: (params.page * 1) < 2
                         }*/
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) {
                return markup;
            },
            minimumInputLength: 1,
            placeholder: "Search order"
        });
    });
</script>

<script>
    $(document).ready(function () {
        // $("#custom").hide();

        $(document).on("click", ".Custom", function () {
            console.log("hello dhiraj");
            $("#custom").removeClass("hide").addClass("show");
            $("#generate").removeClass("show").addClass("hide");
            $(".panel-heading").text("Create InvoiceInfo");

            var row = '<input type="button" value="Generate InvoiceInfo"	class="btn btn-primary pull-right generate">';
            $(".panel-heading").append(row)
        });

        $(document).on("click", ".generate", function () {
            console.log("hello dhiraj");
            $("#custom").removeClass("show").addClass("hide");
            $("#generate").removeClass("hide").addClass("show");
            $(".panel-heading").text("Generate Invoie");
            $(".generate").remove();
        });
    });
</script>


<script>

    $(document).ready(function () {

        $(".datepicker").datepicker({
            /* dateFormat: 'yy-mm-dd' */
        });
    });
</script>

<script>

    $(document).ready(function () {

        $("#clientInfo").hide();
        $(document).on('change', '#orderType', function () {

            var orderType = $(this).val();

            var buyerType;

            if (orderType === "Sale") {

                buyerType = "CUSTOMER";

            } else if (orderType === "Purchase") {

                buyerType = "VENDOR";
            } else {
                $(".autoCustomer").remove();
                $(".itemTable").remove();
                $("#clientInfo").removeClass("show").addClass("hide");
                return;

            }

            var url = $(".context").val() + "/clientInfo/" + buyerType;

            getAllCusomertByType(buyerType, url);
        });
    });
</script>

<script>

    $(document).ready(function () {

        $(document).on('click', '.closeResult', function () {
            $(".searchtable").remove();
            $(".result").hide();
        });

        $(document).on('change', '#buyerType', function () {
            $(".searchtable").remove();
            $("#customerId").val("");
            $(".addCustomer").hide();
            $(".resultCustomer").hide();
            $(".searchCustomer").val("");
        });

        $(document).on('keyup', '.search', function () {

            //console.log("you press " + evt);

            var input = $(this).val();

            if (input === undefined || input === null || "" === input) {
                $(".searchtable").remove();
                return;
            }

            console.log("you typed : " + input);

            var url = $(".context").val() + "/stock/search";

            searchStock(input, url, $(this), "Sale");
        });

        // $(".addCustomer").hide();

        $(document).on('keyup', '.searchCustomer', function () {

            //console.log("you press " + evt);

            var buyerType = $("#buyerType").val();

            if (buyerType === undefined || buyerType === null || "" === buyerType) {
                return;
            }

            $("#customerId").val("");
            $(".addCustomer").hide();
            var input = $(this).val();

            if (input === undefined || input === null || "" === input) {
                $(".searchtable").remove();
                return;
            }

            console.log("you typed : " + input);

            if (buyerType === "CUSTOMER") {
                var url = $(".context").val() + "/clientInfo/searchcustomer";
                searchCustomer(input, url, $(this), 0);
            }

            if (buyerType === "VENDOR") {
                var url = $(".context").val() + "/clientInfo/searchvendor";
                searchVendor(input, url, $(this), 0);
            }


        });


    });
</script>

<script>

    $(document).ready(function () {

        $(document).on('click', '.selectMe', function () {


            var stocckId = $(this).attr("myId");
            var rate = $(this).attr("myRate");
            var searchOn = $(this).attr("searchOn");
            var stockTag = $("#" + searchOn).attr("mystock");
            var rateTag = $("#" + searchOn).attr("myrate");


            $("#" + stockTag).val("");
            $("#" + rateTag).val("");

            $("#" + stockTag).val(stocckId);
            $("#" + rateTag).val(rate);


            $("#" + searchOn).val($(this).text());

            $("#" + searchOn).attr('disabled', 'disabled');

            $(".result").hide();

            console.log("your stockId : " + stocckId + " hiden : " + stockTag);


        });

        $(document).on('click', '.selectCustomer', function () {

            var cutomerId = $(this).attr("myid");

            $("#customerName").val($(this).text());
            $("#customerId").val(cutomerId);

            $(".searchtable").remove();

            $("#customerResult").hide();

        });

    });
</script>

<script type="text/javascript">


    $(document).ready(function () {
        var count = 1;
// for dynamically add or remove row
        $("#add_row").click(function () {
            //alert(count);

            var row = "<div class='row border-bottom table-row itemTable' >";

            row += "<div class='col-md-3'><div class='result' id='result" + count + "'></div><input type='text' result='result" + count + "' id='search" + count + "' mystock='stock" + count + "' myrate='rate" + count + "' class='search form-control form-control-sm' placeholder='search productInfo ' /><input type='hidden' id='stock" + count + "' name='itemLotIdList' required/></div>";
            row += "<div class='col-md-2'><input type='number' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control qty form-control-sm' onKeyup='calculate();'  name='quantityList' placeholder='enter quantity' required/></div>";
            row += "<div class='col-md-2'><input type='number' id='rate" + count + "' class='form-control form-control-sm rate' name='rateList' required /></div>";
            row += "<div class='col-md-2'><input type='number' step='any' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control discount form-control-sm' name='discountList' onKeyup='calculate();' placeholder='enter tax percent'  required /></div>";
            row += "<div class='col-md-2 text-right'><input type='number' class='form-control form-control-sm amount' name='amountList' value='0' disabled/></div>";
            row += "<div class='col-md-1'><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></div>";

            row += "</div>";

            $("#customFields").prepend(row);
            count++;
        });
        $("#customFields").on('click', '.remCF', function () {
            $(this).parent().parent().remove();
            calculate();
        });
    });
</script>


<script>

    function clearCustomeData() {
        $("#name").val("");
        $("#alias").val("");
        $("#contact").val("");
        $("#email").val("");
        $("#address").val("");
    }

    function clearCustomerError() {

        $(".name").text("");
        $(".alias").text("");
        $(".contact").text("");
        $(".email").text("");
        $(".address").text("");

    }

    $(document).ready(function () {

        $(".addCustomer").click(function () {

            clearCustomeData();
            clearCustomerError();

            $(".savecustomer").prop('disabled', false).text("Save");
        });

        $(".savecustomer").click(function () {

            clearCustomerError();

            var url = $(this).attr("url");

            var name = $("#name").val();
            var alias = $("#alias").val();
            var contact = $("#contact").val();
            var email = $("#email").val();
            var address = $("#address").val();


            saveCustomer(url, name, address, alias, contact, email);

        });
    });

</script>