<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/custom.css" rel="stylesheet"> --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/notification.js"></script>
<!-- <spr:page header1="View Prescription"> -->
    <div ng-app="notifyapp" ng-controller="notifyCtrl">
        <div class="row">
            <div class="col-md-6">
                <div ng-repeat="orderDto in prescriptionList">
                    <img src="${pageContext.request.contextPath}/resources/images/prescriptions/{{orderDto.prescription.document.id}}.png"
                         alt="file" style="width:100%; height=150px"/>
                </div>
            </div>
            <div class="col-md-6">
                <div ng-controller="MedicineList as ml">
                    <form method="POST" modelAttribute="orderResponseDto">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Medicine Name</th>
                                <th>Price</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="row in ml.rows">
                                <td>
                                    <input type="text" ng-model="orderResponse.medicineName" ng-readonly="row.readonly"
                                           ng-disabled="row.readonly" on-focus="!row.readonly"/>
                                </td>
                                <td>
                                    <input type="text" ng-model="orderResponse.priceQuotation"
                                           ng-readonly="row.readonly" ng-disabled="row.readonly"/>
                                </td>
                                <td>
                                    <button ng-click="ml.editRow($index)">{{row.readonly ? "Edit" : "Save" }}</button>
                                    <button ng-click="ml.removeRow($index)">Remove</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <input type="hidden" ng-model="orderResponse.id" value="${orderDto.id}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="submit" ng-click="loadOrder()" value="SEND" class="btn btn-sm btn-success">
                    </form>
                    <br/>
                    <input type="button" value="Add New" ng-click="ml.addNewRow('','')"/>
                </div>
            </div>
        </div>
    </div>
    <!-- </spr:page> -->