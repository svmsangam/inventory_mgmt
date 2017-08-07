<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../../ecommerce/template/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-wrapper">

    <table class="table table-responsive table-sm table-striped table-bordered table-hover">

        <thead class="thead-inverse">
        <tr>
            <th>Medicine</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Expire Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="medicine" oldItems="${priceQuotation.oldItem}">
            <tr>
                <td>${medicine.medicineDto.name}</td>
                <td>${medicine.quantity}</td>
                <td>${medicine.medicineDto.sellingPrice}</td>
                <td>${medicine.medicineDto.expiryDate}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button class="btn btn-danger"> Add To Cart</button>
</div>


<%@include file="../../ecommerce/template/footer.jsp" %>