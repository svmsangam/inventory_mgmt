<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@include file="../common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Home</li>
            <li>

            </li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <div class="container-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">Item/s below threshold</div>
                    <div class="panel-body">
                        <div class="table-responsive">
                        <table id="example" class="display" cellspacing="0" width="100%">

                            <thead class="thead-inverse">
                            <tr>
                                <th>s.no</th>
                                <th>Item Name</th>
                                <th>Category</th>
                                <th>Rate</th>
                                <th>Quantity</th>
                                <th>Tax</th>
                                <th>Instock</th>
                                <th>Threshold</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="oldItem" items="${oldItemList}" varStatus="counter">

                                <tr>
                                    <td>${counter.count}</td>
                                    <td>${oldItem.itemName}</td>
                                    <td>${oldItem.categoryName}</td>
                                    <td>${oldItem.rate}</td>
                                    <td>${oldItem.quantity }</td>
                                    <td>${oldItem.tax}</td>

                                    <c:choose>
                                        <c:when test="${oldItem.stock <= oldItem.threshold }">
                                            <td style="color:red;"><b>${oldItem.stock}</b></td>
                                        </c:when>

                                        <c:otherwise>
                                            <td>${oldItem.stock}</td>
                                        </c:otherwise>
                                    </c:choose>


                                    <td>${oldItem.threshold}</td>

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="../common/footer.jsp" %>
<script>
    $(document).ready(function () {
        $('#example').DataTable({
            "pagingType": "full_numbers"
        });
    });
</script>
