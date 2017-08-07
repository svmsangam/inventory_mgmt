<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../common/header.jsp"%>
<%@include file="../common/businessownernavbar.jsp"%>
<%@include file="../common/businessownersidebar.jsp"%>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#"><svg class="glyph stroked home">
                <use xlink:href="#stroked-home"></use></svg></a></li>
            <li class="active">Order</li>
        </ol>
    </div>

    <input class="context" type="hidden" value="${pageContext.request.contextPath}">


    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="col-md-offset-5">Add Order
                        <div class="pull-right">Order No. #${orderNo}</div>
                    </div>
                </div>
                <div class="panel-body">

                    <form method="post" action="${pageContext.request.contextPath}/order/save" modelAttribute="orderInfo" id="registerform" novalidate="novalidate">
                        <input type="hidden" name="orderNo" value="${orderNo}" required/>

                        <div class="row">
                            <div class="col-xs-6">

                                <div class="form-group col-xs-12">
                                    <label name="vendorDto">Order Type:
                                        <select class="form-control select2" name="orderType" id = "orderType" class="select2" required="required">

                                            <option selected>select order type</option>
                                            <option value="Sale" >Sale</option>
                                            <option value="Purchase" >Purchase</option>

                                        </select>
                                    </label>
                                </div>

                                <div class="form-group col-xs-6" id="clientInfo"></div>

                                <div class="form-group col-xs-12">
                                    <label name="orderDate">Order Date:
                                        <input type="text" placeholder="select date" class="datepicker form-control" id="orderDate" name="orderDate" value="${sales.orderDate}" required />
                                    </label>
                                    <p>${error.orderDate}</p>
                                </div>

                                <div class="form-group col-xs-12">
                                    <label name="deliveryDate">Delivery Date:
                                        <input type="text" class="datepicker form-control" placeholder="select date" id="deliveryDate" name="deliveryDate"
                                               value="${sales.deliveryDate}" required /></label>
                                    <p>${error.deliveryDate}</p>
                                </div>
                            </div>

                            <div class="col-xs-6 text-right">
                                <div class="form-group">
                                    <label name="shipmentChannel">Shipment Channel:
                                        <select class="form-control select2" name="shipmentChannel" id="shipmentChannel" class="select2" required="required">
                                            <c:forEach var="shipment" items="${shipmentList}">
                                                <option value="${shipment}"
                                                        <c:if test="${sales.shipmentChannel== shipment}">selected</c:if>>${shipment}</option>
                                            </c:forEach>
                                        </select></label>
                                </div>
                                <div class="form-group">
                                    <label name="shipmentDate">Shipment Date:
                                        <input type="text" class="datepicker form-control" placeholder="select date" id="shipmentDate" name="shippingDate"
                                               value="${sales.shipmentDate}" required /></label>
                                    <p>${error.shipmentDate}</p>
                                </div>
                                <div class="form-group">
                                    <label>Deliver To:
                                        <input class="form-control" type="text" id="deliveryCity" name="deliveredTo" required placeholder="Enter Address"/></label>
                                </div>
                            </div>
                        </div>

                        <p>${error.itemCheck}</p>
                        <div id="inputDiv">




                            <div id="add_row" class="btn btn-xs btn-primary pull-left"><span class="glyphicon glyphicon-plus"></span> Add Item</div>

                            <div class="clearfix"></div>
                            <br>


                            <%-- STATIC SEARCH DIV--%>

                            <div class="row table-header table-row">
                                <div class="col-md-3">Item</div>
                                <div class="col-md-2">Quantity</div>
                                <div class="col-md-2">Rate</div>
                                <div class="col-md-2">Tax (%)</div>
                                <div class="col-md-2">Amount</div>
                                <div class="col-md-1"></div>
                            </div>

                            <div id="customFields">


                            </div>

                        <%--END SEARCH DIV--%>

                        </div>

                        <div class="input-group pull-right">
                            <label class="lable">Total Cost </label> <input type="number" value="0.0"
                                                                            id="total" step="any"
                                                                            onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46 || event.charCode == 0 "
                                                                            id="total" class="form-control" name="totalCost" readOnly
                                                                            required />
                        </div>


                        <div class="col-lg-4">
                            <label for="description">Description:</label><br />
                            <textarea class="form-control" placeholder="write something.." name="description" id="description" rows="3" cols="30" required>${sales.notes}</textarea>
                        </div>

                        <div class="box-footer col-lg-12">
                            <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
                            <input type="submit" class="btn btn-primary pull-right" value="CONFIRM" />
                        </div>
                    </form>


                </div>

            </div>
        </div>

    </div>
</div>
<%@include file="../common/footer.jsp"%>
<script>
    $(function(){
        $(".datepicker").datepicker({
            /* dateFormat: 'yy-mm-dd' */
        });
    });

    $(document).ready(function() {
        $(".select2").select2();
    });
</script>

<script>

    $(document).ready(function(){

        $("#clientInfo").hide();
        $( document ).on('change','#orderType', function() {

            var orderType = $(this).val();

            var buyerType;

            if (orderType === "Sale"){

                buyerType = "CUSTOMER";

            } else if (orderType === "Purchase"){

                buyerType = "VENDOR";
            } else {
                $(".autoCustomer").remove();
                $(".itemTable").remove();
                $("#clientInfo").hide();
                return;

            }

            var url = $(".context").val() + "/clientInfo/" + buyerType;

            getAllCusomertByType(buyerType , url);
        });
    });
</script>

<script>

    $(document).ready(function(){

        $( document ).on('keyup','.search', function() {

            //console.log("you press " + evt);
            var orderType = $("#orderType").val();

            if (!(orderType === "Sale" || orderType === "Purchase")){

                $.notify({
                    title: '<strong>Heads up!</strong>',
                    message: 'Please select the order type first'
                },{
                    type: 'danger'
                });

                return;
            }

            var input = $(this).val();

            if (input === undefined || input === null || "" === input){
                $(".searchtable").remove();
                return;
            }

            console.log("you typed : " + input);

            var url = $(".context").val() + "/stock/search";

            searchStock(input , url , $(this));
        });
    });
</script>

<script>

    $(document).ready(function(){

        $( document ).on('click','.selectMe', function() {


            var stocckId = $(this).attr("myId");
            var rate = $(this).attr("myRate");
            var searchOn = $(this).attr("searchOn");
            var stockTag = $("#" + searchOn).attr("mystock");
            var rateTag = $("#" + searchOn).attr("myrate");


            $("#"+stockTag).val("");
            $("#"+rateTag).val("");

            $("#"+stockTag).val(stocckId);
            $("#"+rateTag).val(rate);


            $("#"+searchOn).val($(this).text());

            $("#"+searchOn).attr('disabled','disabled');

            $(".result").hide();

            console.log("your stockId : " + stocckId + " hiden : " + stockTag);


        });
    });
</script>

<script type="text/javascript">


    $(document).ready(function(){
        var count = 1;
// for dynamically add or remove row
        $("#add_row").click(function(){
            //alert(count);

            var row = "<div class='row border-bottom table-row itemTable' >";

            row += "<div class='col-md-3'><div class='result' id='result" +count+ "'></div><input type='text' result='result" +count+ "' id='search" +count+ "' mystock='stock" +count+  "' myrate='rate" +count+ "' class='search form-control form-control-sm' placeholder='search productInfo ' /><input type='hidden' id='stock" +count+ "' name='stockIdList' required/></div>";
            row += "<div class='col-md-2'><input type='number' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control qty form-control-sm' onKeyup='calculate();'  name='quantityList' placeholder='enter quantity' required/></div>";
            row += "<div class='col-md-2'><input type='number' id='rate" +count+ "' class='form-control form-control-sm rate' name='rateList' required disabled/></div>";
            row += "<div class='col-md-2'><input type='number' step='any' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control tax form-control-sm' name='taxList' onKeyup='calculate();' placeholder='enter tax percent'  required /></div>";
            row += "<div class='col-md-2 text-right'><input type='number' class='form-control form-control-sm amount' name='amountList' value='0' disabled/></div>";
            row += "<div class='col-md-1'><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></div>";

            row += "</div>";

            $("#customFields").prepend(row);
            count++;
        });
        $("#customFields").on('click','.remCF',function(){
            $(this).parent().parent().remove();
            calculate();
        });
    });
</script>

<script>





</script>
