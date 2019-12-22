<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 12/22/19
  Time: 10:30 PM
  To change this template use File | Settings | File Templates.
--%>

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
                        <input type="text" id="tagName" class="form-control addTagFormClear"
                               placeholder="Name">
                        <p class="form-error tagFormError" id="tagNameError"></p>
                    </div>

                    <div class="form-group">
                        <label class="control-label">Code</label>
                        <input type="text" id="tagCode" class="form-control addTagFormClear"
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
                        <input type="text" id="lotCode" class="form-control addLotFormClear"
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
