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

        <div class="row">
            <div class="col-md-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Add Sales Order</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/order/sale/save" method="post"
                          modelAttribute="customer">
                        <%--row start--%>
                        <div class="row">

                            <%--col start--%>
                            <div class="col-xs-6">

                                <div class="form-group col-xs-6">
                                    <label class="control-label">Buyer Name</label>
                                    <input type="text" class="form-control" value="${customer.name}" name="name"
                                           placeholder="Name">
                                    <p class="form-error">${customerError.name}</p>
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

                            <%--col end--%>


                            <%--col start--%>
                            <div class="col-xs-6 text-right">

                                <div class="form-group">
                                    <label>OrderNo #</label>
                                        <strong>1234</strong>
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
                            <%--col end--%>
                        </div>
                        <%--row end--%>
                        <div class="box-body">

                            <%--row start--%>
                            <div class="row">

                                <div id="add_row" class="form-group">
                                    <label class="btn btn-xs btn-primary">
                                        <span class="glyphicon glyphicon-plus"></span> Add Item
                                    </label>
                                </div>

                                <%--col start--%>
                                <div class="col-xs-12 margin-bottom">

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
                                    <%--col end--%>

                                <%--col start--%>
                                <div class="col-xs-6 pull-left">

                                    <div class="form-group">
                                        <label for="description">Description:</label><br/>
                                        <textarea class="form-control" placeholder="write something.." name="description"
                                                  id="description" rows="3" cols="30" required>${sales.notes}</textarea>
                                    </div>

                                </div>
                                <%--col end--%>

                                <%--col start--%>
                                <div class="col-xs-6 pull-right">

                                    <div class="form-group">
                                        <label class="lable">Tax(%) </label> <input type="number" value="0.0" id="tax" step="any"
                                                                                    onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46 || event.charCode == 0 "
                                                                                    class="form-control" onKeyup="calculate();"
                                                                                    name="tax" required/>

                                    </div>

                                    <div class="form-group">

                                        <label class="lable">Total Cost </label>
                                        <span>Rs.<strong>30000</strong></span>

                                    </div>

                                </div>
                                <%--col end--%>

                            </div>

                            <%--row end--%>

                            <!-- /.box-body -->
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Save changes</button>
                            </div>

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

<%@include file="/pages/parts/footer.jsp" %>

<script>

    $(function () {
        $(".datepicker").datepicker({
            /* dateFormat: 'yy-mm-dd' */
        });
    });

    $(document).ready(function () {

        $(".select2").select2();
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

            var row = "<div class='row border-bottom table-row itemTable' >";

            row += "<div class='col-md-3'><div class='result' id='result" + count + "'></div><input type='text' result='result" + count + "' id='search" + count + "' mystock='stock" + count + "' myrate='rate" + count + "' class='search form-control form-control-sm' placeholder='search productInfo ' /><input type='hidden' id='stock" + count + "' name='itemLotIdList' required/></div>";
            row += "<div class='col-md-2'><input type='number' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control qty form-control-sm' onKeyup='calculate();'  name='quantityList' placeholder='enter quantity' required/></div>";
            row += "<div class='col-md-2'><input type='number' id='rate" + count + "' class='form-control form-control-sm rate' name='rateList' required /></div>";
            row += "<div class='col-md-2'><input type='number' step='any' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control discount form-control-sm' name='discountList' onKeyup='calculate();' placeholder='enter tax percent'  required /></div>";
            row += "<div class='col-md-2 text-right'>Rs.<span class='amount'>77778</span></div>";
            row += "<div class='col-md-1'><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></div>";

            row += "</div>";

            $("#customFields").prepend(row);
            count++;
            max ++;
        });
        $("#customFields").on('click', '.remCF', function () {
            $(this).parent().parent().remove();
            max--;
            calculate();
        });
    });
</script>



