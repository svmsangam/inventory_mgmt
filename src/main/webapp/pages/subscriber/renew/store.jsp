<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 2/7/18
  Time: 2:25 PM
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
                <th>Name</th>
                <th>Contact</th>
                <th>Email</th>
                <th>City</th>
                <th>Street</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody id="myData">
            <c:forEach var="store" items="${storeList}" varStatus="i">
                <tr>
                    <td>${i.index + 1}</td>
                    <td>${store.name}</td>
                    <td>${store.contact}</td>
                    <td>${store.email}</td>
                    <td>${store.cityName}</td>
                    <td>${store.street}</td>
                    <td><c:if test="${store.status eq 'ACTIVE'}"><span class="label label-success">Active</span></c:if>
                        <c:if test="${store.status ne 'ACTIVE'}"><span class="label label-danger">Deactive</span></c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
