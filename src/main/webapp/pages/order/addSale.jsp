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
                        <div class="pull-right">Order No. #1234</div>
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

                                <div class="form-group col-xs-7">
                                    <label class="control-label">Order Date</label>
                                    <div class='input-group date datepicker'>
                                        <input type="text" class="datepicker form-control" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${item.expireDate}"/>" name="expireDate" placeholder="expire on">
                                        <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                    </div>
                                    <p class="form-error">${itemError.expireDate}</p>
                                </div>

                            </div>

                            <%--col end--%>


                            <%--col start--%>
                            <div class="col-xs-6 text-right">

                                <div class="form-group margin">
                                    <label class="control-label">Delivery Date:</label>
                                    <div class='input-group date datepicker'>
                                       <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                        <input type="text" class="datepicker form-control" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${item.expireDate}"/>" name="expireDate" placeholder="expire on">

                                    </div>
                                    <p class="form-error">${itemError.expireDate}</p>
                                </div>

                                <div class="form-group margin">
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

                                <div class="form-group margin">
                                    <label class="btn btn-xs btn-primary" id="add_row">
                                        <span class="glyphicon glyphicon-plus"></span> Add Item
                                    </label>
                                </div>

                                <%--col start--%>
                                <div class="col-xs-12 margin">

                                        <table class="table">
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

            var row = "<tr class='border-bottom itemTable' >";

            row += "<td><select class='select2 form-control'><option>select item</option><option>mattress</option><option>carpet</option></select></td>";
            row += "<td><input type='number' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control qty form-control-sm' onKeyup='calculate();'  name='quantityList' placeholder='enter quantity' required/></td>";
            row += "<td><input type='number' id='rate" + count + "' class='form-control form-control-sm rate' name='rateList' required /></td>";
            row += "<td><input type='number' step='any' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control discount form-control-sm' name='discountList' onKeyup='calculate();' placeholder='enter tax percent'  required /></td>";
            row += "<td class='text-right'>Rs.<span class='amount'>77778</span></div>";
            row += "<td><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></td>";

            row += "</tr>";

            $("#customFields").prepend(row);
            $(".select2").select2();
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



