<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 8/24/17
  Time: 9:43 PM
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
            <div class="col-md-6">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Add Item</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/item/save" method="post" modelAttribute="item" >
                        <div class="box-body">

                            <input type="hidden" name="productId" value="${product}"/>
                            <div class="form-group">
                                <label class="control-label">Tag</label>
                                <select name="tagId" class="form-control select2" id="tagId">
                                    <option value="">select tag</option>
                                    <c:forEach items="${tagList}" var="tag">
                                        <c:choose>
                                            <c:when test="${tag.tagId eq item.tagId}">
                                                <option value="${tag.tagId}" selected>${tag.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${tag.tagId}">${tag.name}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                                <p class="form-error">${itemError.tagId}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Lot</label>
                                <select name="lotId" class="form-control select2" id="lotId">
                                    <option value="">select lot</option>
                                    <c:forEach items="${lotList}" var="lot">
                                        <c:choose>
                                            <c:when test="${lot.lotId eq item.lotId}">
                                                <option value="${lot.lotId}" selected>${lot.lot}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${lot.lotId}">${lot.lot}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                                <p class="form-error">${itemError.lotId}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Cost Price</label>
                                <input type="number" class="form-control" value="${item.costPrice}" name="costPrice" placeholder="costPrice">
                                <p class="form-error">${itemError.costPrice}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Selling Price</label>
                                <input type="number" class="form-control" value="${item.sellingPrice}" name="sellingPrice" placeholder="sellingPrice">
                                <p class="form-error">${itemError.sellingPrice}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Quantity</label>
                                <input type="number" class="form-control" value="${item.quantity}" name="quantity" placeholder="quantity">
                                <p class="form-error">${itemError.quantity}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Threshold</label>
                                <input type="number" class="form-control" value="${item.threshold}" name="threshold" placeholder="threshold">
                                <p class="form-error">${itemError.threshold}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Expiry Date</label>
                                <div class='input-group date datepicker'>
                                    <input type="text" class="datepicker form-control" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${item.expireDate}"/>" name="expireDate" placeholder="expire on">
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                                <p class="form-error">${itemError.expireDate}</p>
                            </div>

                        </div>
                        <!-- /.box-body -->
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Save changes</button>
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
        $('.select2').select2();
        $(".datepicker").datepicker({
        });
    })
</script>



