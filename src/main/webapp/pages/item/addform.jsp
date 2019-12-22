<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 12/22/19
  Time: 6:04 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    .fa-2x {
        font-size: 20px !important;
    }
    .input-group .input-group-addon {
        border-radius: 0;
        border-color: #d2d6de;
        background-color: #eee;
    }
</style>

<div class="form-group">
    <label class="control-label">Code *</label>
    <input type="text" class="form-control" value="${item.code}" name="code" placeholder="code">
    <p class="form-error">${itemError.code}</p>
</div>

<div class="form-group">
    <label class="control-label">Tag *</label>
    <div class='input-group'>
        <select name="tagId" class="form-control chooseTag" id="tagId">
            <option value="">select tag</option>
        </select>
        <span class="input-group-addon">
             <a href="#" class="pull-right" id="addNewTagBtn" data-toggle="modal"
                data-target="#modal-addtag" title="click here to add new tag">
                 <i class="fa fa-plus-circle fa-2x darkGray" aria-hidden="true"></i>
             </a>
        </span>
    </div>
    <p class="form-error">${itemError.tagId}</p>
</div>

<div class="form-group">
    <label class="control-label">Lot *</label>
    <%--TODO add new lot here--%>
    <%--<a href="#" class="pull-right" id="addNewLotBtn" data-toggle="modal"
       data-target="#modal-addlot" title="click to add new lot"><i class="fa fa-plus-circle fa-2x darkGray" aria-hidden="true"></i></a>--%>
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
    <label class="control-label">Cost Price *</label>
    <input type="number" onkeypress="return validate(event);" class="form-control"
           value="${item.costPrice}" name="costPrice" placeholder="costPrice">
    <p class="form-error">${itemError.costPrice}</p>
</div>

<div class="form-group">
    <label class="control-label">Selling Price *</label>
    <input type="number" onkeypress="return validate(event);" class="form-control"
           value="${item.sellingPrice}" name="sellingPrice" placeholder="sellingPrice">
    <p class="form-error">${itemError.sellingPrice}</p>
</div>

<div class="form-group">
    <label class="control-label">Quantity *</label>
    <input type="number" onkeypress="return validate(event);" class="form-control"
           value="${item.quantity}" name="quantity" placeholder="quantity">
    <p class="form-error">${itemError.quantity}</p>
</div>

<div class="form-group">
    <label class="control-label">Threshold *</label>
    <input type="number" onkeypress="return validate(event);" class="form-control"
           value="${item.threshold}" name="threshold" placeholder="threshold">
    <p class="form-error">${itemError.threshold}</p>
</div>

<div class="form-group">
    <label class="control-label">Expiry Date *</label>
    <div class='input-group date datepicker'>
        <input type="text" class="datepicker form-control" onkeypress="return false;"
               onkeyup="return false;"
               value="<fmt:formatDate pattern="MM/dd/yyyy" value="${item.expireDate}"/>"
               name="expireDate" placeholder="expire on">
        <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
    </div>
    <p class="form-error">${itemError.expireDate}</p>
</div>

<div class="form-group">
    <label class="control-label">Supplier</label>
    <div class='input-group'>
        <select name="vendorId" class="form-control choose1">

        </select>
        <span class="input-group-addon">
             <a href="#" class="pull-right" id="addNewSupplierBtn" data-toggle="modal"
                data-target="#modal-addSupplier" title="click here to add new Supplier">
                 <i class="fa fa-plus-circle fa-2x darkGray" aria-hidden="true"></i>
             </a>
        </span>
    </div>
    <p class="form-error"><%--${itemError.vendorId}--%></p>
</div>