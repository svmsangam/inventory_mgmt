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
                            <button type="button" class="btn btn-info btn-sm btn-flat pull-right addUser"
                                    data-toggle="modal" data-target="#modal-add"><span
                                    class="glyphicon glyphicon-plus-sign"></span> Add
                            </button>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <table id="table2" class="table table-bordered table-hover table-striped">
                            <thead>
                            <tr>
                                <th>SN</th>
                                <th>Name</th>
                                <th>Address</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody id="myData">
                            <%-- <c:forEach var="user" items="${userList}" varStatus="i">--%>
                            <tr>
                                <td>${i.index + 1}</td>
                                <td>demo</td>
                                <td>demo</td>
                                <td><span class="label label-success">Active</span></td>
                                <td>
                                    <button type="button" class="btn btn-info btn-sm  btn-flat" data-toggle="modal"
                                            data-target="#modal-view"><span class="glyphicon glyphicon-eye-open"></span>
                                        View
                                    </button>
                                    <button type="button" class="btn btn-warning btn-sm  btn-flat" data-toggle="modal"
                                            data-target="#modal-edit"><span class="glyphicon glyphicon-edit"></span>
                                        Edit
                                    </button>
                                    <button type="button" class="btn btn-danger btn-sm btn-flat"><span
                                            class="glyphicon glyphicon-minus-sign"></span> Delete
                                    </button>
                                </td>
                            </tr>
                            <%--</c:forEach>--%>
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
                    <h3 class="box-title text-center">Store Name</h3>
                    <div class="box-body">

                        <dl class="dl-horizontal">
                            <dt>Email ID</dt>
                            <dd>sample@gmail.com</dd>
                            <dt>Contact No</dt>
                            <dd>016655442</dd>
                            <dt>Street</dt>
                            <dd>Maitidevi Road</dd>
                            <dt>Mobile No</dt>
                            <dd>9876543210</dd>
                        </dl>

                        <strong><i class="fa fa-book margin-r-5"></i> PAN Number</strong>
                        <p class="text-muted">0123456789</p>
                        <hr>

                        <strong><i class="fa fa-book margin-r-5"></i> Registration Number</strong>
                        <p class="text-muted">0123456789</p>
                        <hr>

                        <strong><i class="fa fa-map-marker margin-r-5"></i> Address</strong>
                        <p class="text-muted">Kathmandu, Nepal</p>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger pull-right" data-dismiss="modal">Close</button>
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
                                    <input type="text" class="form-control" name="" id="" placeholder="Name" required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Email</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="email" required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Contact no.</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="Contact"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Mobile no</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="mobile no"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Street Address</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="street address"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Address</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="Address"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">PAN no</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="PAN no"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Regd no</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="Registration no"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger pull-left closeAdd" data-dismiss="modal">Close</button>
                        <button type="submit" id="saveuser" url="${pageContext.request.contextPath}/store/add" class="btn btn-primary">Save changes</button>
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
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Edit Store</h4>
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
                                    <input type="text" class="form-control" name="" id="" placeholder="Name" required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Email</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="email" required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Contact no.</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="Contact"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Mobile no</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="mobile no"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>

                        </div>

                        <div class="row">

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Street Address</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="street address"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>

                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Address</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="Address"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">PAN no</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="PAN no"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <div class="form-group">
                                    <label class="control-label">Regd no</label>
                                    <input type="text" class="form-control" name="" id="" placeholder="Registration no"
                                           required/>
                                    <p class="form-error"></p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger pull-left closeAdd" data-dismiss="modal">Close</button>
                        <button type="submit" id="" url="${pageContext.request.contextPath}/store/update" class="btn btn-primary">Save changes</button>
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
