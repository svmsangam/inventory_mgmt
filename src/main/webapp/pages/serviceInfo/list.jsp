<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/25/18
  Time: 9:09 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <h3 class="box-title">Sevice List</h3>
                        <div class="box-tools">
                            <a href="${pageContext.request.contextPath}/service/add" class="btn btn-info btn-sm btn-flat pull-right"><span class="glyphicon glyphicon-plus-sign"></span> Add
                            </a>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <div class="table-responsive">
                            <table id="table2" class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>SN</th>
                                    <th>Title</th>
                                    <th>Total Store</th>
                                    <th>Total Order</th>
                                    <th>Validity</th>
                                    <th>Rate</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="serviceInfo" items="${serviceList}" varStatus="i">
                                    <tr>
                                        <td>${i.index + 1}</td>
                                        <td>${serviceInfo.title}</td>
                                        <td>${serviceInfo.totalStore}</td>
                                        <td>${serviceInfo.totalOrder}</td>
                                        <td>${serviceInfo.expireDays} days</td>
                                        <td>${serviceInfo.rate}</td>

                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
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
                    <h4 class="modal-title">Add Tag</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal">
                        <div class="box-body">
                            <div class="form-group">
                                <label class="control-label">Name</label>
                                <input type="text" class="form-control" name="name" placeholder="Name">
                                <p class="error">${error.name}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Code</label>
                                <input type="text" class="form-control" name="code" placeholder="Code">
                                <p class="error">${error.code}</p>
                            </div>

                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger pull-left" data-dismiss="modal">Close</button>
                    <button type="submit" pagecontext="${pageContext.request.contextPath}"
                            url="${pageContext.request.contextPath}/tag/save"  class="btn btn-primary">Save changes</button>
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
                    <h4 class="modal-title">Edit Tag</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/tag/edit"
                          modelAttribute="tagInfoDto">
                        <input type="hidden" name="tagId" value="${tagInfo.tagId}"/>
                        <div class="box-body">

                            <div class="form-group">
                                <label class="control-label">Name</label>
                                <input type="text" class="form-control" placeholder="Name" value="" required>
                                <p class="error">${error.name}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Code</label>
                                <input type="text" class="form-control" name="code" value="" placeholder="Code">
                                <p class="error">${error.code}</p>
                            </div>

                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger pull-left" data-dismiss="modal">Close</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" url="${pageContext.request.contextPath}/tag/update" class="btn btn-primary savetag">Save changes</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
</div>

<%@include file="/pages/parts/footer.jsp" %>

