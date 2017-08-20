<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

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

            <div class="col-xs-12">
                <div class="box box-info">
                    <div class="box-header">
                        <h3 class="box-title">Store List</h3>
                        <div class="box-tools">
                            <button type="button" class="btn btn-info btn-sm btn-flat pull-right addStore"
                                    data-toggle="modal" data-target="#modal-add"><span
                                    class="glyphicon glyphicon-plus-sign"></span> Add
                            </button>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                        <div class="box-body">
                        <table id="table2" class="table table-bordered table-hover table-striped">
                            <thead>
                            <tr>
                                <th>SN</th>
                                <th>Name</th>
                                <th>Contact</th>
                                <th>Email</th>
                                <th>City</th>
                                <th>Street</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody id="myData">
                            <c:forEach var="store" items="${storeList}" varStatus="i">
                                <tr>
                                    <td>${i.index + 1}</td>
                                    <td><a href="javascript:void(0);" url="${pageContext.request.contextPath}/store/show/${store.storeId}" class="viewStoreInfo" data-toggle="modal"
                                                data-target="#modal-view">
                                            ${store.name}
                                    </a></td>
                                    <td>${store.contact}</td>
                                    <td>${store.email}</td>
                                    <td>${store.cityName}</td>
                                    <td>${store.street}</td>
                                    <td><c:if test="${store.status eq 'ACTIVE'}"><span class="label label-success">Active</span></c:if><c:if test="${store.status ne 'ACTIVE'}"><span class="label label-danger">Deactive</span></c:if></td>
                                    <td>
                                        <button type="button" url="${pageContext.request.contextPath}/store/show/${store.storeId}" class="btn btn-info btn-sm  btn-flat viewStoreInfo" data-toggle="modal"
                                                data-target="#modal-view"><span class="glyphicon glyphicon-eye-open"></span>
                                            View
                                        </button>
                                       <%-- <button type="button" class="btn btn-warning btn-sm  btn-flat" data-toggle="modal"
                                                data-target="#modal-edit"><span class="glyphicon glyphicon-edit"></span>
                                            Edit
                                        </button>--%>
                                        <%--<button type="button" class="btn btn-danger btn-sm btn-flat"><span
                                                class="glyphicon glyphicon-minus-sign"></span> Delete
                                        </button>--%>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>
    </section>

    <div class="modal fade" id="modal-view">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Store Detail View</h4>
                </div>
                <div class="modal-body">
                    <h3 class="box-title text-center storeName"></h3>
                    <div class="box-body">

                        <dl class="dl-horizontal">
                            <dt >Email ID</dt>
                            <dd class="storeEmail"></dd>
                            <dt>Contact No</dt>
                            <dd class="storeContact"></dd>
                            <dt>Street</dt>
                            <dd class="storeStreet"></dd>
                            <dt>Mobile No</dt>
                            <dd class="storeMobile"></dd>
                        </dl>

                        <strong><i class="fa fa-book margin-r-5"></i> PAN Number</strong>
                        <p class="text-muted storePan"></p>
                        <hr>

                        <strong><i class="fa fa-book margin-r-5"></i> Registration Number</strong>
                        <p class="text-muted storeReg"></p>
                        <hr>

                        <strong><i class="fa fa-map-marker margin-r-5"></i> City</strong>
                        <p class="text-muted storeCity"></p>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger pull-left closeShow" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-warning btn-sm  btn-flat pull-right edit" data-dismiss="modal" data-toggle="modal"
                                data-target="#modal-edit"><span class="glyphicon glyphicon-edit"></span>
                            Edit
                        </button>
                    </div>
                </div>

            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->

    <div class="modal fade" id="modal-add">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Add Store</h4>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger alert-dismissible addError">
                        <button type="button" class="close closeError" data-dismiss="alert" aria-hidden="true">&times;
                        </button>
                        <p class="errorModel"></p>
                    </div>
                    <div class="box-body">

                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Name</label>
                                    <input type="text" class="form-control" name="name" id="name" placeholder="store name" required/>
                                    <p class="form-error name"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Email</label>
                                    <input type="text" class="form-control" name="email" id="email" placeholder="email" required/>
                                    <p class="form-error email"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Contact no.</label>
                                    <input type="text" class="form-control" name="contact" id="contact" placeholder="Contact"
                                           required/>
                                    <p class="form-error contact"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Mobile no</label>
                                    <input type="text" class="form-control" name="mobile" id="mobile" placeholder="mobile no"
                                           required/>
                                    <p class="form-error mobile"></p>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Street Address</label>
                                    <input type="text" class="form-control" name="street" id="street" placeholder="street address"
                                           required/>
                                    <p class="form-error street"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">City</label>
                                    <select name="cityId" class="form-control select2" id="cityId">
                                        <option value="">select city</option>
                                        <c:forEach items="${cityList}" var="city">
                                            <option value="${city.cityId}">${city.cityName}</option>
                                        </c:forEach>
                                    </select>
                                    <p class="form-error cityId"></p>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">PAN no</label>
                                    <input type="text" class="form-control" name="pan" id="pan" placeholder="PAN no"
                                           required/>
                                    <p class="form-error pan"></p>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Regd no</label>
                                    <input type="text" class="form-control" name="reg" id="reg" placeholder="Registration no"
                                           required/>
                                    <p class="form-error reg"></p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger pull-left closeAdd" data-dismiss="modal">Close</button>
                        <button type="submit" pagecontext="${pageContext.request.contextPath}" url="${pageContext.request.contextPath}/store/save" class="btn btn-primary savestore">Save changes</button>
                    </div>
                </div>

            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->

    <div class="modal fade" id="modal-edit">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close pull-left" data-dismiss="modal" data-toggle="modal"
                            data-target="#modal-view" aria-label="Close">
                        <span aria-hidden="true"><</span></button>
                    <h4 class="pull-left title-center">Edit Store</h4>
                    <button type="button" class="close pull-right" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body">
                    <div class="alert alert-danger alert-dismissible addError">
                        <button type="button" class="close closeError" data-dismiss="alert" aria-hidden="true">&times;
                        </button>
                        <p class="errorModel"></p>
                    </div>
                    <div class="box-body">

                        <div class="row">
                           <%-- <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Name</label>
                                    <input type="text" class="form-control" name="name" id="nameEdit" placeholder="Name" required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>--%>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Email</label>
                                    <input type="text" class="form-control" name="" id="emailEdit" placeholder="email" required/>
                                    <p class="form-error email name"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Contact no.</label>
                                    <input type="text" class="form-control" name="" id="contactEdit" placeholder="Contact"
                                           required/>
                                    <p class="form-error contact"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Mobile no</label>
                                    <input type="text" class="form-control" name="" id="mobileEdit" placeholder="mobile no"
                                           required/>
                                    <p class="form-error mobile"></p>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Street Address</label>
                                    <input type="text" class="form-control" name="" id="streetEdit" placeholder="street address"
                                           required/>
                                    <p class="form-error street"></p>
                                </div>
                            </div>

                            <%--<div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">City</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="Address"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>--%>
                        </div>

                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">PAN no</label>
                                    <input type="text" class="form-control" name="" id="panEdit" placeholder="PAN no"
                                           required/>
                                    <p class="form-error pan"></p>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Regd no</label>
                                    <input type="text" class="form-control" name="" id="regEdit" placeholder="Registration no"
                                           required/>
                                    <p class="form-error reg"></p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger pull-left closeAdd" data-dismiss="modal">Close</button>
                        <button type="submit" id="storeIdEdit" url="${pageContext.request.contextPath}/store/update" class="btn btn-primary updatestore">Save changes</button>
                    </div>
                </div>

            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->

</div>
<%--content-wrapper--%>
<%@include file="/pages/parts/footer.jsp" %>
<script>
    $(function () {
        $('.select2').select2()
    })
</script>
