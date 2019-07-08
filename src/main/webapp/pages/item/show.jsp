<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 6/29/17
  Time: 12:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/businessownernavbar.jsp" %>
<%@include file="../common/businessownersidebar.jsp" %>

<style>
    .newItem {
        background: lightgray;
    }
</style>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="/">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Item</li>
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


    <div class="row">


        <div class="col-lg-6">
            <div class="panel panel-default">
                <div class="panel-heading"><a href="${pageContext.request.contextPath}/productInfo/list"
                                              style="float:left; margin-bottom:8px;margin-right: 150px;">
                    <button class="btn btn-primary btn-sm">List Item</button>
                </a><c:if test="${productInfo.name ne null}">${productInfo.name }</c:if><a
                        href="${pageContext.request.contextPath}/productInfo/add"
                        style="float:right; margin-bottom:8px;">
                    <button class="btn btn-primary btn-sm">Add New Item</button>
                </a>
                </div>
                <c:if test="${productInfo ne null}">
                    <div class="panel-body">

                            <%-- <c:if test="${productInfo.name ne null}">
                                 <div class="form-group" id="inputDiv">
                                     <label class="lable">ProductInfo Name :</label>
                                     <span class="form-control" >${productInfo.name }</span>
                                 </div>
                             </c:if>--%>

                        <c:if test="${productInfo.code ne null}">
                            <div class="form-group" id="inputDiv">
                                <label class="lable">Code :</label>
                                <span class="form-control">${productInfo.code }</span>
                            </div>
                        </c:if>

                        <c:if test="${productInfo.categoryName ne null}">
                            <div class="form-group" id="inputDiv">
                                <label class="lable">categoryInfo :</label>
                                <span class="form-control">${productInfo.categoryName }</span>
                            </div>
                        </c:if>

                        <c:if test="${productInfo.subcategoryName ne null}">
                            <div class="form-group" id="inputDiv">
                                <label class="lable">subCategoryInfo :</label>
                                <span class="form-control">${productInfo.subcategoryName }</span>
                            </div>
                        </c:if>

                        <c:if test="${productInfo.unitName ne null}">
                            <div class="form-group" id="inputDiv">
                                <label class="lable">unitInfo :</label>
                                <span class="form-control">${productInfo.unitName }</span>
                            </div>
                        </c:if>

                        <c:if test="${productInfo.trendingLevel ne null}">
                            <div class="form-group" id="inputDiv">
                                <label class="lable">TraditionLevel :</label>
                                <span class="form-control">${productInfo.trendingLevel }</span>
                            </div>
                        </c:if>

                        <a class="addItemLot btn btn-info btn-sm" style="margin-top: 8px;" data-toggle="modal"
                           data-target="#lotModal">new lotInfo</a>

                        <c:if test="${ItemLotList ne null}">
                            <div class="form-group table-responsive" id="inputDiv">
                                <c:if test="${fn:length(ItemLotList) gt 0}">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>tagInfo</th>
                                            <th>costPrice</th>
                                            <th>sellingPrice</th>
                                            <th>threshold</th>
                                            <th>avialableQuantity</th>
                                            <th>totalQuantity</th>
                                            <th>expiry date</th>
                                        </tr>
                                        </thead>
                                        <tbody id="resultItemLot">
                                        <c:forEach var="itemInfo" items="${ItemLotList}">
                                            <tr>
                                                <td>${itemInfo.tagDto.tagName}</td>
                                                <td>${itemInfo.costPrice}</td>
                                                <td>${itemInfo.sellingPrice}</td>
                                                <td>${itemInfo.threshold}</td>
                                                <c:choose><c:when
                                                        test="${itemInfo.availableQuantity <= itemInfo.threshold}">
                                                    <td style="color: red">${itemInfo.availableQuantity}</td>
                                                </c:when><c:otherwise>
                                                    <td>${itemInfo.availableQuantity}</td>
                                                </c:otherwise></c:choose>
                                                <td>${itemInfo.totalQuantity}</td>
                                                <td><fmt:formatDate pattern="MMM dd, yyyy"
                                                                    value="${itemInfo.expireDate}"/></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <th>Grand Total</th>
                                            <th></th>
                                            <th></th>
                                            <th></th>
                                            <th id="gat">${stock.availableQuantity}</th>
                                            <th id="gt">${stock.totalQuantity}</th>
                                            <th></th>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </c:if>
                            </div>
                        </c:if>

                        <c:if test="${productInfo.description ne null}">
                            <div class="form-group" id="inputDiv">
                                <label class="lable">Item description :</label>
                                <span class="form-control" style="overflow: hidden">${productInfo.description }</span>
                            </div>
                        </c:if>

                    </div>
                </c:if>
            </div>


            <%--add itemlot start--%>
            <div class="modal fade" id="lotModal" role="dialog">
                <div class="modal-dialog">


                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Add ItemLot</h4>

                            <p class="error errorMsg"></p>
                        </div>
                        <div class="modal-body customerData">
                            <div class="form-group">
                                <label class="lable">tagInfo *</label>

                                <input type="hidden" id="itemId" value="${productInfo.itemId}"/>

                                <select class="choose1 form-control" style="width: 100%;"
                                        url="${pageContext.request.contextPath}/tagInfo/search" id="tagId"></select>
                                <p class="error tagId"></p>
                            </div>

                            <div class="form-group">
                                <label class="lable">costPrice *</label>
                                <input type="text" class="form-control" id="costPrice" required/>

                                <p class="error costPrice"></p>
                            </div>

                            <div class="form-group">
                                <label class="lable">sellingPrice *</label>
                                <input type="text" class="form-control" id="sellingPrice" required/>

                                <p class="error sellingPrice"></p>
                            </div>

                            <div class="form-group">
                                <label class="lable">availableQuantity *</label>
                                <input type="text" class="form-control" id="availableQuantity" required/>

                                <p class="error availableQuantity"></p>
                            </div>

                            <div class="form-group">
                                <label class="lable">threshold *</label>
                                <input type="text" class="form-control" id="threshold" required/>

                                <p class="error threshold"></p>
                            </div>

                            <div class="form-group">
                                <label class="lable">expireDate *</label>
                                <input type="text" class="datepicker form-control" id="expireDate" required/>

                                <p class="error expireDate"></p>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" url="${pageContext.request.contextPath}/itemlot/save"
                                    class="saveItemLot btn btn-success pull-left">Save
                            </button>
                            <button type="button" class="closeme btn btn-danger" data-dismiss="modal">Close</button>
                        </div>
                    </div>

                    <%--add itemlot end--%>


                </div>

            </div>

        </div>
    </div>
    <%@include file="../common/footer.jsp" %>

    <script>

        $(function () {
            $(".datepicker").datepicker({
                /*dateFormat: 'yy-mm-dd'*/
            });
        });
    </script>


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
                        };
                    },
                    processResults: function (data, params) {
                        params.page = params.page || 1;
                        var arr = [];
                        $.each(data.detail, function (index, value) {
                            arr.push({
                                id: value.tagId,
                                text: value.tagName
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
                placeholder: "Search tagInfo"
            });
        });
    </script>

    <script>

        $(document).ready(function () {


            $(document).on('click', '.addItemLot', function () {

                clearItemLotData();
                clearItemLotError();
            });


            $(document).on('click', '.saveItemLot', function () {

                saveItemLot($(this).attr("url"), $("#itemId").val(), $("#tagId").val(), $("#costPrice").val(), $("#sellingPrice").val(), $("#availableQuantity").val(), $("#threshold").val(), $("#expireDate").val());
            });
        });

    </script>