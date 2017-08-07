<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/businessOwnerSettings.jsp" %>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
    <div class="row">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">Category</li>
        </ol>
    </div><!--/.row-->
    <c:if test="${not empty message}">
        <div class="alert alert-info alert-dismissable">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
            <strong>${message}</strong>
        </div>
    </c:if>

    <div class="row">
        <div class="col-lg-8">

            <div class="break"></div>

            <a href="${pageContext.request.contextPath}/app/addcategory">
                <button class="btn btn-primary btn-sm pull-right">Add Category</button>
            </a>
            <div class="break"></div>
            <div>
                <table class="table table-bordered table-stripped">
                    <thead>
                    <tr>
                        <th>Category Name</th>
                        <th>Code</th>
                        <th>Description</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="categoryInfo" items="${categoryList}">
                        <tr>
                            <td>${categoryInfo.categoryName}</td>
                            <td>${categoryInfo.code}</td>
                            <td>${categoryInfo.description}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/app/editcategory?id=${categoryInfo.categoryId}">
                                    <button class="btn btn-info btn-xs">Edit</button>
                                </a>
                                <a href="${pageContext.request.contextPath}/app/deletecategory?id=${categoryInfo.categoryId}">
                                    <button class="btn btn-danger btn-xs"
                                            onclick="return confirm('Are you sure, you want to DELETE?')">Delete
                                    </button>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>


        </div><!-- /.col-->
    </div><!-- /.row -->

</div>
<!--/.main-->
	
