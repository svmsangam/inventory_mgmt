<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spr" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
 

<div class="container-wrapper">
	<div class="container">
		<c:if test="${not empty message}">
			<p class="bg-success">
				<c:out value="${message}"></c:out>
			</p>
		</c:if>
		<spr:page header1="Medicine Stock Alert Lists">
			<c:choose>
				<c:when test="${fn:length(medicineDtoList) eq 0}">
					<c:if test="${not empty message}">
						<p class="bg-success">
							<c:out value="${message}"></c:out>
						</p>
					</c:if>
				</c:when>
				<c:when test="${fn:length(medicineDtoList) gt 0}">
					<table id="medicineList"
						class="table table-responsive table-sm table-striped table-bordered table-hover">
						<thead class="thead-inverse">
							<tr>
								<th>Name</th>
								<th>Category</th>
								<th>Cost Price</th>
								<th>Selling Price</th>
								<th>Quantity</th>
								<th>Generic Name</th>
								<th>Company</th>
								<th>Manufactured Date</th>
								<th>Expire Date</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="medicine" oldItems="${medicineDtoList}">
								<tr>
									<td>${medicine.name}</td>
									<td>${medicine.categoryInfo}</td>
									<td>${medicine.costPrice}</td>
									<td>${medicine.sellingPrice}</td>
									<td>${medicine.quantity}<br />
									<c:choose>
										<c:when test="${medicine.quantity == 0}">
										
										<button class="btn btn-danger btn-sm ">OUT OF STOCK</button>
										</c:when>
										<c:otherwise>
										<button class="btn btn-info btn-sm ">ADD</button>
										</c:otherwise>
										</c:choose>
									</td>
									<td>${medicine.genericName}</td>
									<td>${medicine.company}</td>
									<td>${medicine.mfddate}</td>
									<td>${medicine.expiryDate}</td>
									<td><a href="${pageContext.request.contextPath}/editMedicine?medicineId=${medicine.id}"
										class="btn btn-info btn-xs"><i class="fa fa-pencil"></i>
											Edit </a> <a href="${pageContext.request.contextPath}/deleteMedicine?medicineId=${medicine.id}"
										class="btn btn-danger"> Delete </a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
			</c:choose>
		</spr:page>
	</div>
</div>