<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 12/19/17
  Time: 10:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/pages/parts/header.jsp" %>
<%@include file="/pages/parts/sidebar.jsp" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

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


    <section class="content ">
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

        <div class="box-body box box-primary">

            <div class="row">
                <div class="col-md-12">
                    <ul class="nav nav-tabs" role="tablist">
                        <li role="presentation" class="active"><a href="#home" aria-controls="home"
                                                                  role="tab" data-toggle="tab">Home</a>
                        </li>
                       <%-- <li role="presentation"><a href="#user" aria-controls="user" role="tab"
                                                   data-toggle="tab">Profile</a></li>
                        <li role="presentation"><a href="#store" aria-controls="store" role="tab"
                                                   data-toggle="tab">store</a></li>--%>
                    </ul>

                    <div class="tab-content">

                        <div role="tabpanel" class="tab-pane active" id="home">
                            <%@include file="/pages/employee/show/home.jsp" %>
                        </div>

                        <div role="tabpanel" class="tab-pane" id="user">
                            jujnjn
                        </div>

                        <div role="tabpanel" class="tab-pane" id="store">
                            <table class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th>SN</th>
                                    <th>Name</th>
                                    <th>Contact</th>
                                    <th>Email</th>
                                    <th>City</th>
                                    <th>Street</th>
                                    <th>Status</th>
                                </tr>
                                </thead>
                                <tbody id="myData">
                                <%--<c:forEach var="store" items="${storeList}" varStatus="i">--%>
                                    <tr>
                                        <td>${i.index + 1}</td>
                                        <td>${store.name}</td>
                                        <td>${store.contact}</td>
                                        <td>${store.email}</td>
                                        <td>${store.cityName}</td>
                                        <td>${store.street}</td>
                                        <td>
                                            <c:if test="${store.status eq 'ACTIVE'}"><span class="label label-success">Active</span></c:if>
                                            <c:if test="${store.status ne 'ACTIVE'}"><span class="label label-danger">Deactive</span></c:if>
                                        </td>
                                    </tr>
                               <%-- </c:forEach>--%>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.box-body -->

    </section>

</div>

<%@include file="/pages/parts/footer.jsp" %>


