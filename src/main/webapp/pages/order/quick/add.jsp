<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/6/18
  Time: 6:44 PM
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
                        <h3 class="box-title">Quick Sales Order</h3>
                        <div class="pull-right">Order No. #${orderNo}<p class="form-error">${orderError.orderNo}</p><p class="form-error">${orderError.error}</p></div>
                    </div>
                    <form action="${pageContext.request.contextPath}/order/sale/quick" method="post" modelAttribute="order">

                        <%@include file="/pages/order/form.jsp" %>

                        <div class="box-footer">
                            <div class="row">
                                <div class="col-md-6">
                                    <strong>shortcuts for  addItem</strong><br>
                                    <small>
                                        ctrl + 'z'<br>
                                        enter or tab keydown on discount
                                    </small>
                                </div>

                                <div class="col-md-6">
                                    <button type="submit" class="btn btn-primary btn-flat btn-sm pull-right">Cotinue</button>
                                </div>
                            </div>
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

    var max = 1;
    var count = 1;

    function addRow() {

        var row = "<tr class='border-bottom itemTable' >";
        row += "<td><select class='form-control item' name='' url='${pageContext.request.contextPath}/item/show'><option value=''>select item</option><c:forEach items="${itemList}" var="item"><option value='${item.itemId}'>${item.productInfo.name}-${item.tagInfo.name}</option></c:forEach> </select></td>";
        row += "<td><input type='number' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control form-control-sm quantity' onkeyup='calculate(amountUpdate);'  name='' placeholder='enter quantity' required/></td>";
        row += "<td><input type='number' class='form-control form-control-sm' name='' required readonly/></td>";
        row += "<td><input type='number' step='any' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control form-control-sm discount' onkeyup='calculate(amountUpdate);' name='' placeholder='enter discount percent'  required /></td>";
        row += "<td class='text-right'>Rs.<span>0</span></div>";
        row += "<td><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></td>";
        row += "</tr>";
        $("#customFields").prepend(row);
        $(".item").select2();
        count++;
        max ++;
        updateName();
    }

    $(document).ready(function () {

// for dynamically add or remove row
        $("#add_row").click(function () {
            //alert(count);
            if (max === 10){
                alert("max 10");
                return;
            }

            addRow();

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

    (function( $ ) {
        var $doc = $( document );
        $doc.ready( function(){
            $doc.on( 'keydown', function( e ){
                if ( ! $( e.target ).is( ':input' ) ) {

// props rauchg for pointing out e.shiftKey
                    if ( 90 === e.which && e.ctrlKey ) {
// `shift` and `w` are pressed. Do something.

                        if (max === 10){
                            alert("max 10");
                            return;
                        }

                        addRow();
                    }
                }

                else if ( $( e.target ).is( '.discount' ) ) {

// props rauchg for pointing out e.shiftKey
                    if ( 13 === e.which || e.which === 9) {
// `shift` and `w` are pressed. Do something.

                        if (max === 10){
                            alert("max 10");
                            return;
                        }

                        addRow();
                    }
                }

            });
        });

    })( jQuery );

</script>




