<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/29/18
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<form action="${pageContext.request.contextPath}/product/filter" method="GET">
    <div class="well well-sm">
        <div class="row">

            <div class="col-md-4">
                <div class="form-group">
                    <label>Product Name</label>
                    <input class="form-control" name="name" type="text" value="${filterDTO.name}"/>
                    <p class="form-error"></p>
                </div>
            </div>
			<div class="col-md-4">
                <div class="form-group">
                    <label>Trending</label>
                    <select class="choose2 form-control" name="trendingLevel">

                        <option value="">select trending</option>

                        <c:forEach items="${trendingtList}" var="trending">
                            <c:choose>
                                <c:when test="${filterDTO.trendingLevel eq trending}">
                                    <option value="${trending}" selected>${trending}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${trending}">${trending}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <p class="form-error"></p>
                </div>
            </div>

            <div class="col-md-4">
                <div class="form-group">
                    <label>Sub Category</label>
                    <select class="select2 form-control" name="subCategoryId">

                        <option value="">select sub category</option>

                        <c:forEach items="${subcategoryList}" var="subcategory">
                            <c:choose>
                                <c:when test="${filterDTO.subCategoryId eq subcategory.categoryId}">
                                    <option value="${subcategory.categoryId}" selected>${subcategory.categoryName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${subcategory.categoryId}">${subcategory.categoryName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <p class="form-error"></p>
                </div>
            </div>
        </div>
       <%--  <div class="row">
        <div class="col-md-4">
            <div class="form-group">
                <label>InStock Greater Than</label>
                <input type="number"  class="form-control" name="greaterThanInStock" placeholder="InStock Greater Than" value='<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.greaterThanInStock}"/>'>
            </div>
        </div>
		
			<div class="col-md-4">
                <div class="form-group">
                    <label>InStock Less Than</label>
                    <input type="number" class="form-control" name="lessThanInStock" placeholder="InStock Less Than" value='<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.lessThanInStock}"/>'>
                </div>
            </div>
            
		</div> --%>
        <div class="row">
            <div class="col-md-4 pull-right" >
                <button type="submit" class="btn btn-success btn-flat btn-block">Filter!</button>
            </div>
        </div>

    </div>
</form>
