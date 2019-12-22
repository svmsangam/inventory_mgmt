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
    <label class="control-label">Tag</label>
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
    <label class="control-label">Lot</label>
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
    <label class="control-label">Cost Price</label>
    <input type="number" onkeypress="return validate(event);" class="form-control"
           value="${item.costPrice}" name="costPrice" placeholder="costPrice">
    <p class="form-error">${itemError.costPrice}</p>
</div>

<div class="form-group">
    <label class="control-label">Selling Price</label>
    <input type="number" onkeypress="return validate(event);" class="form-control"
           value="${item.sellingPrice}" name="sellingPrice" placeholder="sellingPrice">
    <p class="form-error">${itemError.sellingPrice}</p>
</div>

<div class="form-group">
    <label class="control-label">Quantity</label>
    <input type="number" onkeypress="return validate(event);" class="form-control"
           value="${item.quantity}" name="quantity" placeholder="quantity">
    <p class="form-error">${itemError.quantity}</p>
</div>

<div class="form-group">
    <label class="control-label">Threshold</label>
    <input type="number" onkeypress="return validate(event);" class="form-control"
           value="${item.threshold}" name="threshold" placeholder="threshold">
    <p class="form-error">${itemError.threshold}</p>
</div>

<div class="form-group">
    <label class="control-label">Expiry Date</label>
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


<%--tag add modal start--%>
<div class="modal fade" id="modal-addtag">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add New Tag</h4>
            </div>
            <div class="modal-body">
                <div class="box-body">

                    <div class="form-group">
                        <label class="control-label">Name</label>
                        <input type="text" id="tagName" class="form-control addTagFormClear" name="name"
                               placeholder="Name">
                        <p class="form-error tagFormError" id="tagNameError"></p>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Code</label>
                        <input type="text" id="tagCode" class="form-control addTagFormClear" name="code"
                               placeholder="code">
                        <p class="form-error tagFormError" id="tagCodeError"></p>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-danger pull-left closeAdd" data-dismiss="modal">Close
                    </button>
                    <button type="button" class="btn btn-primary btn-sm  btn-flat pull-right addNewTag"
                            url="${pageContext.request.contextPath}/api/tag/save"><span
                            class="glyphicon glyphicon-save"></span>
                        Save Changes
                    </button>
                </div>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<%--tag add modal end--%>

<%--lot add modal start--%>
<div class="modal fade" id="modal-addlot">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add New Lot</h4>
            </div>
            <div class="modal-body">
                <div class="box-body">

                    <div class="form-group">
                        <label class="control-label">Name</label>
                        <input type="text" id="lotName" class="form-control addLotFormClear" name="name"
                               placeholder="Name">
                        <p class="form-error lotFormError" id="lotNameError"></p>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Code</label>
                        <input type="text" id="lotCode" class="form-control addLotFormClear" name="code"
                               placeholder="code">
                        <p class="form-error lotFormError" id="lotCodeError"></p>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-danger pull-left closeLotAdd" data-dismiss="modal">Close
                    </button>
                    <button type="button" class="btn btn-primary btn-sm  btn-flat pull-right addNewLot"
                            url="${pageContext.request.contextPath}/api/lot/save"><span
                            class="glyphicon glyphicon-save"></span>
                        Save Changes
                    </button>
                </div>
            </div>

        </div>
    </div>
</div>
<%--lot add modal end--%>

<!-- add Supplier Modal start -->
<div id="modal-addSupplier" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Add New Supplier</h4>
            </div>
            <div class="modal-body">

                <div class="alert hidden alert-danger alert-dismissible addSupplierError">
                    <button type="button" class="close closeError" data-dismiss="alert" aria-hidden="true">&times;
                    </button>
                    <p class="supplierErrorModel"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Company Name *</label>
                    <input type="text" id="suppliercompanyName" class="form-control addSupplierFormClear" name="companyName"
                           placeholder="Company Name">
                    <p class="form-error supplierFormError supplierCompanyNameError"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Proprietor Name</label>
                    <input type="text" id="suppliername" class="form-control addSupplierFormClear" name="name"
                           placeholder="Name">
                    <p class="form-error supplierFormError supplierNameError"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Contact</label>
                    <input type="text" id="suppliercontact" class="form-control addSupplierFormClear" name="contact"
                           placeholder="contact">
                    <p class="form-error supplierFormError supplierContactError"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Mobile Number</label>
                    <input type="text" class="form-control addSupplierFormClear"
                           id="suppliermobileNumber" name="mobileNumber" placeholder="Mobile Number">
                    <p class="form-error supplierFormError supplierMobileError"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Email Address</label>
                    <input type="email" class="form-control addSupplierFormClear" id="supplieremail" name="email"
                           placeholder="email address">
                    <p class="form-error supplierFormError supplierEmailError"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">City</label>
                    <select name="cityId" class="form-control select2" id="suppliercityId">
                        <option value="">select city</option>
                        <c:forEach items="${cityList}" var="city">

                            <option value="${city.cityId}">${city.cityName}</option>

                        </c:forEach>
                    </select>
                    <p class="form-error supplierFormError supplierCityError"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Street</label>
                    <input type="text" class="form-control addSupplierFormClear" id="supplierstreet" name="street"
                           placeholder="street">
                    <p class="form-error supplierFormError supplierStreetError"></p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="addNewSupplier" pagecontext="${pageContext.request.contextPath}"
                        url="${pageContext.request.contextPath}/client/vendor/save" class="btn btn-primary pull-left addNewSupplier">
                    save changes
                </button>
                <button type="button" class="btn btn-danger pull-right closeSupplierAdd" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>
<!-- add Supplier Modal end -->