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

    <div class="row">
        <div class="col-lg-5">
            <div class="panel panel-default">
                <div class="panel-heading">Edit Category</div>
                <div class="panel-body">
                    <form method="post" action="${pageContext.request.contextPath}/app/editcategory"
                          modelAttribute="categoryDto">
                        <input type="hidden" name="categoryId" value="${categoryInfo.categoryId}" required/>
                        <div class="form-group">
                            <label name="categoryName">Category Name:</label>
                            <input class="form-control" type="text" name="categoryName"
                                   value="${categoryInfo.categoryName}" required/>
                            <p class="error">${error.categoryName}</p>
                        </div>

                        <div class="form-group">
                            <label>Code:</label>
                            <input class="form-control" type="text" name="code" value="${categoryInfo.code}" required/>
                            <p class="error">${error.code}</p>
                        </div>

                        <div class="form-group">
                            <label name="description">Description:</label>
                            <textarea class="form-control" name="description"
                                      rows="4">${categoryInfo.description}</textarea>
                            <p class="error">${error.description}</p>
                        </div>

                        <div class="pull-left">
                            <input type="submit" class="btn btn-primary" value="Update Category Type"/>
                        </div>
                    </form>
                </div>
            </div><!-- /.col-->
        </div><!-- /.row -->
    </div>
</div>
<!--/.main-->
	
