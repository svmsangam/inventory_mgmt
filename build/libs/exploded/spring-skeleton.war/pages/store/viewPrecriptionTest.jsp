<%@ taglib prefix="spr" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>


<script>
    function addMecicine(id, orderId) {
        medicineListDto = [];
        $.ajax({
            url: "getMedicine",
            data: {
                id: id,
                orderId: orderId
            },
            cache: false,
            type: "GET",
            success: function (data) {
                medicineListDto = data;
                //alert(JSON.stringify(medicineListDto))
                var table = document.getElementById("selectedItems");
                var row = table.insertRow(1);
                var name = row.insertCell(0);
                var price = row.insertCell(1);
                var expireDate = row.insertCell(2);

                name.innerHTML = medicineListDto.name;
                price.innerHTML = medicineListDto.sellingPrice;
                expireDate.innerHTML = medicineListDto.expiryDate;
            },

        });
    }


    function myFunction() {
        var input, filter, table, tr, td, i;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");

        // Loop through all table rows, and hide those who don't match the search query
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[0];
            if (td) {
                if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }


    }
</script>
<spr:page header1="View Prescription">
    <div>
        <div class="row">
            <div class="col-md-7">

                <img
                        src="${pageContext.request.contextPath}/images/prescriptions/${orderDto.prescription.document.id}.png"
                        alt="file" style="width: 100%;"/>

            </div>


            <div class="col-md-5">

                <input type="text" id="myInput" class="form-control"
                       onkeyup="myFunction()" placeholder="Search for medicine...">
                <table id="myTable"
                       class="table table-responsive table-sm table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>Medicine Name</th>
                        <th>Price</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="medicine" oldItems="${medicineListDto}">
                        <tr>
                            <td class="medicineName">${medicine.name}</td>
                            <td>${medicine.sellingPrice}</td>
                            <td>
                                <button class="btn btn-primary add-row " id="add"
                                        onclick="addMecicine(${medicine.id},${orderDto.id})">ADD Medicine
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
            <br/> <br/>
            <div>
                <p>Selected Items</p>
                <br/>
                <table class="table table-responsive table-sm table-striped table-bordered table-hover"
                       id="selectedItems">

                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Expire Date</th>
                        <th>Quantity</th>
                    </tr>
                    </thead>

                    <tbody>

                    </tbody>
                </table>
                <a href="addPriceQuotation?orderId=${orderDto.id}" class="btn btn-primary">Send Response</a>
            </div>


                <%-- <form method="POST" action="/addOrderResponse" modelAttribute="addOrderResponse" >

                                Medicine  Name: <input type="text" name=medicineName> <br /> <br />
                                Price Quotation : <input type="text" name="priceQuotation"> <br />
                                <br />
                                    <input type="hidden" name="id" value="${orderDto.id}">
                                     <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
                                      <input type="submit" value="SEND" class="btn btn-sm btn-success">
                              </form> --%>
        </div>

    </div>
    </div>
</spr:page>