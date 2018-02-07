<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/7/18
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<style>
    .nav-tabs {
        border-bottom: 2px solid #DDD;
    }

    .nav-tabs > li.active > a, .nav-tabs > li.active > a:focus, .nav-tabs > li.active > a:hover {
        border-width: 0;
    }

    .nav-tabs > li > a {
        border: none;
        color: #666;
    }

    .nav-tabs > li.active > a, .nav-tabs > li > a:hover {
        border: none;
        color: #4285F4 !important;
        background: transparent;
    }

    .nav-tabs > li > a::after {
        content: "";
        background: #4285F4;
        height: 2px;
        position: absolute;
        width: 100%;
        left: 0px;
        bottom: -1px;
        transition: all 250ms ease 0s;
        transform: scale(0);
    }

    .nav-tabs > li.active > a::after, .nav-tabs > li:hover > a::after {
        transform: scale(1);
    }

    .tab-nav > li > a::after {
        background: #21527d none repeat scroll 0% 0%;
        color: #fff;
    }

    .tab-pane {
        padding: 15px 0;
    }

    .tab-content {
        padding: 20px
    }

    .card {
        background: #FFF none repeat scroll 0% 0%;
        box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.3);
        margin-bottom: 30px;
    }
</style>

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
                        <h3 class="box-title">Subscriber Service Renew</h3>
                        <div class="box-tools">
                            <a href="${pageContext.request.contextPath}/subscriber/service/renew?subscriber=${subscriber.subscriberId}"
                               class="btn btn-success btn-sm btn-flat pull-right">
                                <span class="glyphicon glyphicon-stats"></span> &nbsp;Renew
                            </a>
                        </div>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">

                        <div class="row">
                            <div class="col-md-12">
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active"><a href="#home" aria-controls="home"
                                                                              role="tab"
                                                                              data-toggle="tab">Subscriber</a>
                                    </li>

                                    <li role="presentation"><a href="#store" aria-controls="store" role="tab"
                                                               data-toggle="tab">store</a></li>
                                </ul>

                                <div class="tab-content">

                                    <div role="tabpanel" class="tab-pane active" id="home">
                                        <%@include file="/pages/subscriber/renew/home.jsp" %>
                                    </div>

                                    <div role="tabpanel" class="tab-pane" id="store">
                                        <%@include file="/pages/subscriber/renew/store.jsp" %>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <form action="${pageContext.request.contextPath}/subscriber/service/renew" method="post"
                                  modelAttribute="subscriber">

                                <input type="hidden" name="subscriberId" value="${subscriber.subscriberId}">
                                <div class="box-body">
                                    <div class="col-md-6">
                                        <label class="control-label">Service *</label>
                                        <select class="form-control" name="serviceId" required>
                                            <option value="0">select service</option>
                                            <c:forEach items="${serviceList}" var="serviceInfo">
                                                <option value="${serviceInfo.serviceId}">${serviceInfo.title}</option>
                                            </c:forEach>
                                        </select>
                                        <p class="form-error"></p>
                                    </div>
                                </div>

                                <div class="box-footer">
                                    <button type="submit" class="btn btn-primary">Save changes</button>
                                </div>
                            </form>
                        </div>

                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
        </div>
    </section>
</div>

<%@include file="/pages/parts/footer.jsp" %>


