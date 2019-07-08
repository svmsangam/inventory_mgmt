<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/7/18
  Time: 2:18 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-md-12">
        <table class="table table-bordered table-hover table-striped">
            <thead>
            <tr>
                <th>SN</th>
                <th>Username</th>
                <th>Store</th>
                <th>UserType</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody id="myData">
            <c:forEach var="user" items="${userList}" varStatus="i">
                <tr>
                    <td>${i.index + 1}</td>
                    <td>${user.inventoryuser}</td>
                    <td>${user.storeName}</td>
                    <td>${user.userType}</td>
                    <td>
                        <c:if test="${user.enable eq true}">
                            <span class="label label-success">Activated</span>
                        </c:if>
                        <c:if test="${user.enable eq false}">
                            <span class="label label-danger">Deactivated</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
