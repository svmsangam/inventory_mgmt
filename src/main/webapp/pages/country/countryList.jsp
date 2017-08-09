<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            Country List
            <%--<small>Optional description</small>--%>
        </h1>
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/"><i class="fa fa-dashboard"></i> Home</a></li>
            <li><a href="javascript:void(0);">Country</a></li>
            <li class="active">List</li>
        </ol>
    </section>

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
        <div class="col-xs-6">
            <div class="box box-info">
                <div class="box-header">
                    <div class="box-tools">
                        <button type="button" class="btn btn-info btn-sm btn-flat pull-right" data-toggle="modal" data-target="#modal-add"><span class="glyphicon glyphicon-plus-sign"></span>  Add</button>
                    </div>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <table id="table2" class="table table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>SN</th>
                            <th>Name</th>
                            <th>ISO</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="country" items="${countryList}" varStatus="i">
                                <tr>
                                    <td>${i.index + 1}</td>
                                    <td>${country.countryName}</td>
                                    <td>${country.countryISO}</td>
                                    <td>
                                        <button type="button" class="btn btn-warning btn-sm  btn-flat" data-toggle="modal" data-target="#modal-edit"><span class="glyphicon glyphicon-edit"></span> Edit</button>
                                        <button type="button" class="btn btn-danger btn-sm btn-flat"><span class="glyphicon glyphicon-minus-sign"></span> Delete</button>
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

    <div class="modal fade" id="modal-add">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Add Country</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="box-body">
                        <div class="form-group">
                            <label class="control-label">Name</label>
                            <input type="text" class="form-control" name="countryName" placeholder="Name">
                            <p class="error">${error.countryName}</p>
                        </div>

                        <div class="form-group">
                            <label class="control-label">ISO</label>
                            <input type="text" class="form-control" name="countryISO" placeholder="ISO">
                            <p class="error">${error.countryISO}</p>
                        </div>

                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal">Close</button>
                <button type="submit" class="btn btn-primary">Save changes</button>
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
                    <h4 class="modal-title">Edit Country</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/editcountry" modelAttribute="countryDto">
                            <input type="hidden" name="countryId" value="${countryInfo.countryId}"/>
                        <div class="box-body">

                            <div class="form-group">
                                <label class="control-label">Name *</label>
                                <input type="text" class="form-control" placeholder="Name" value="Nepal" required>
                                <p class="error">${error.countryName}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">ISO *</label>
                                <input type="text" class="form-control" name="countryISO" value="NPL" placeholder="ISO">
                                <p class="error">${error.countryISO}</p>
                            </div>

                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger pull-left" data-dismiss="modal">Close</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-primary">Save changes</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
</div>

<%@include file="/pages/parts/footer.jsp" %>
