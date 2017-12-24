<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 12/24/17
  Time: 9:37 PM
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
                        <h3 class="box-title">Add Fiscal Year</h3>
                    </div>
                    <!-- /.box-header -->
                    <form action="${pageContext.request.contextPath}/fiscalyear/save" method="post" modelAttribute="fiscalYearInfo" >
                        <div class="box-body">

                            <div class="form-group">
                                <label class="control-label">Title</label>
                                <input type="text" class="form-control" minlength="5" maxlength="15" value="${fiscalyear.title}" name="title" placeholder="display name" required>
                                <p class="form-error">${fiscalYearError.title}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Openning Date</label>
                                <div class='input-group date datepicker'>
                                    <input type="text" class="datepicker form-control" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${fiscalYear.opennigDate}"/>" name="opennigDate" placeholder="Opennig Date" required>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                                <p class="form-error">${fiscalYearError.opennigDate}</p>
                            </div>

                            <div class="form-group">
                                <label class="control-label">Clossing Date</label>
                                <div class='input-group date datepicker'>
                                    <input type="text" class="datepicker form-control" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${fiscalYear.closingDate}"/>" name="closingDate" placeholder="Closing Date" required>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                                <p class="form-error">${fiscalYearError.expireDate}</p>
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


