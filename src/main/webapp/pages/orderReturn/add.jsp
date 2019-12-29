<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/17/18
  Time: 2:07 PM
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
            <div class="col-lg-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title text-center">Sales Return</h3>
                        <div class="pull-right">
                            <c:if test="${invoice ne null}">
                                <span>Order No. <b>#<a href="${pageContext.request.contextPath}/order/sale/${order.orderId}"> ${order.orderNo}</a></b></span>&nbsp;
                                <span class="pull-right">Invoice No. <b><a href="${pageContext.request.contextPath}/invoice/${invoice.invoiceId}">#${invoice.invoiceNo}</a></b></span></c:if>
                            <c:if test="${invoice eq null}">Order No. <b>#${order.orderNo}</b></c:if>
                            <br>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="row invoice-info">
                            <div class="col-sm-4 invoice-col">
                                <address>
                                    <strong>Buyer Details</strong><br>
                                    ${order.clientInfo.name}<br>

                                    <c:if test="${order.clientInfo.companyName ne null and order.clientInfo.companyName ne ''}">
                                        ${order.clientInfo.companyName}<br>
                                    </c:if>

                                    ${order.clientInfo.street}<br>
                                    Mobile: ${order.clientInfo.mobileNumber}<br>

                                    <c:if test="${order.clientInfo.companyName ne null and order.clientInfo.companyName ne ''}">
                                        Contact: ${order.clientInfo.contact}<br>
                                    </c:if>

                                    <c:if test="${order.clientInfo.email ne null and order.clientInfo.email ne ''}">
                                        Email: ${order.clientInfo.email}<br>
                                    </c:if>

                                    <c:if test="${order.clientInfo.cityInfoDTO ne null}">
                                        City: ${order.clientInfo.cityInfoDTO.cityName}<br>
                                    </c:if>

                                </address>
                            </div>
                            <div class="col-sm-4 invoice-col"></div>
                            <div class="col-sm-4 invoice-col">
                                <address>
                                    <strong>Shipping Address</strong><br>
                                    ${order.deliveryAddress}<br>
                                    Phone: ${order.clientInfo.mobileNumber}<br>
                                </address>
                            </div>
                        </div>

                        <form action="${pageContext.request.contextPath}/orderreturn/save" method="post" modelAttribute="orderReturn">

                            <input type="hidden" name="orderInfoId" value="${order.orderId}">

                            <div class="row invoice-info">
                                <div class="col-sm-3 invoice-col">
                                    <b>Order Date: </b><fmt:formatDate pattern="MMM dd, yyyy" value="${order.orderDate}"/>

                                    <div class="form-group">
                                        <label>Return Date:</label>
                                        <div class='input-group date' style="position: relative;">
                                            <div class="input-group-addon">
                                                <i class="fa fa-calendar"></i>
                                            </div>
                                            <input type="text" class="form-control datepicker" onkeypress="return false;" onkeyup="return false;" name="returnDate" placeholder="Return Date"/>
                                        </div>
                                        <p class="form-error">${itemError.returnDate}</p>
                                    </div>
                                </div>
                                <div class="col-sm-6 invoice-col">&nbsp;</div>
                                <div class="col-sm-3 invoice-col">
                                    <b>Delivery Date: </b><fmt:formatDate pattern="MMM dd, yyyy" value="${order.deliveryDate}"/>
                                </div>
                            </div>
                            <!-- /.row -->
                            <div class="row">
                                <div class="col-sm-12"><p class="form-error">${orderReturnError.error}</p></div>
                            </div>
                            <!-- Table row -->
                            <div class="row">
                                <div class="col-lg-12 table-responsive">
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>SN</th>
                                            <th>Return</th>
                                            <th>Item</th>
                                            <th>Item Lot</th>
                                            <th>Quantity</th>
                                            <th>Return Quantity</th>
                                            <th>Rate</th>
                                            <th>Discount(%)</th>
                                            <th>Subtotal</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <c:forEach items="${orderItemList}" var="orderItem" varStatus="i">
                                            <tr>
                                                <td>${i.index + 1}</td>
                                                <td><input type="checkbox" name="orderItemInfoIdList" value="${orderItem.orderItemInfoId}" class="returnOrder" /></td>
                                                <td>${orderItem.itemInfoDTO.productInfo.name}-${orderItem.itemInfoDTO.tagInfo.name}</td>
                                                <td>${orderItem.itemInfoDTO.lotInfo.lot}</td>
                                                <td>${orderItem.quantityAfterReturn} &nbsp; ${orderItem.itemInfoDTO.productInfo.unitInfo.code}</td>
                                                <td><input type="number" name="returnQuantityList" class="form-control returnQuantity" placeholder="Return Quantity" min="1" max="${orderItem.quantityAfterReturn}" disabled></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderItem.rate}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderItem.discount}"/></td>
                                                <td><fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${orderItem.amount}"/></td>
                                            </tr>
                                        </c:forEach>

                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th colspan="8" ><p class="pull-right">Net Total</p></th>
                                            <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.totalAmount}"/></td>
                                        </tr>

                                        <tr>
                                            <th colspan="8"><p class="pull-right">Tax (<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.tax}"/>%)</p></th>
                                            <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.totalAmount * order.tax / 100}"/></td>
                                        </tr>

                                        <tr>
                                            <th colspan="8"><p class="pull-right">Grand Total:</p></th>
                                            <td>Rs. <fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="true" value="${order.grandTotal}"/></td>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.col -->
                            </div>
                            <!-- /.row -->

                            <div class="row">
                                <!-- accepted payments column -->
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label for="description">Description:</label><br/>
                                        <textarea class="form-control" placeholder="write something.." name="note"
                                                  id="description" rows="3" cols="30"></textarea>
                                        <p class="form-error">${orderReturnError.note}</p>
                                    </div>
                                </div>
                            </div>
                            <!-- /.row -->
                            <div class="row">
                                <div class="col-md-6 pull-right">
                                    <button type="submit" class="btn btn-primary btn-flat btn-sm pull-right"><i class="fa fa-save"></i>&nbsp;Save changes</button>
                                </div>
                            </div>


                        </form>

                    </div>
                </div>
            </div>
        </div>
    </section>
    <!-- /.content -->
    <div class="clearfix"></div>
</div>
<!-- /.content-wrapper -->
<%@include file="/pages/parts/footer.jsp" %>

<script>
    $(document).ready( function () {

        $(document).on("change" , ".returnOrder" , function () {

            if($(this).is(":checked")) {
               console.log("you checked : " + $(this).val() + " max: " +  $(this).closest("tr").find("td:eq(5)").find("input").attr("max"));
                $(this).closest("tr").find("td:eq(5)").find("input").prop("disabled" , false);
            } else {
                console.log("you unchecked : " + $(this).val());
                $(this).closest("tr").find("td:eq(5)").find("input").prop("disabled" , true);
            }
        });
    });
</script>

