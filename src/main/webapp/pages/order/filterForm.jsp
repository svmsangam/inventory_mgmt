<%--
  Created by IntelliJ IDEA.
  User: bidhee
  Date: 2/16/18
  Time: 4:38 PM
  To change this template use File | Settings | File Templates.
--%>
<form action="${pageContext.request.contextPath}/order/sale/filter" method="GET">
    <div class="well well-sm">

        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Client Name</label>
                    <select class="choose1 form-control" name="clientId"></select>
                    <p class="form-error"></p>
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label>Order Track</label>
                    <select class="choose2 form-control" name="track">

                        <option value="">select track</option>

                        <c:forEach items="${saleTrackList}" var="track">
                            <c:choose>
                                <c:when test="${filterDTO.track eq track}">
                                    <option value="${track}" selected>${track}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${track}">${track}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label>From Date:</label>
                    <div class='input-group date'>
                        <div class="input-group-addon">
                            <i class="fa fa-calendar"></i>
                        </div>

                        <input type="text" class="form-control datepicker" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.from}"/>"
                               onkeypress="return false;" onkeyup="return false;"
                               name="from" placeholder="From Date"/>
                    </div>
                    <p class="form-error"></p>
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label>To Date:</label>
                    <div class='input-group date'>
                        <div class="input-group-addon">
                            <i class="fa fa-calendar"></i>
                        </div>
                        <input type="text" class="form-control datepicker" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.to}"/>"
                               onkeypress="return false;" onkeyup="return false;"
                               name="to" placeholder="To Date"/>
                    </div>
                    <p class="form-error"></p>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Amount Greater Than</label>
                    <input type="number" class="form-control" value="<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountGt}"/>" name="amountGt" placeholder="amount greater than">
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label>Amount Less Than</label>
                    <input type="number" value="<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.amountLt}"/>" class="form-control" name="amountLt" placeholder="amount less than">
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label>Delivery Date From:</label>
                    <div class='input-group date'>
                        <div class="input-group-addon">
                            <i class="fa fa-calendar"></i>
                        </div>

                        <input type="text" class="form-control datepicker" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.dlfrom}"/>"
                               onkeypress="return false;" onkeyup="return false;"
                               name="dlfrom" placeholder="From Date"/>
                    </div>
                    <p class="form-error"></p>
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label>Delivery Date To:</label>
                    <div class='input-group date'>
                        <div class="input-group-addon">
                            <i class="fa fa-calendar"></i>
                        </div>
                        <input type="text" class="form-control datepicker" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${filterDTO.dlto}"/>"
                               onkeypress="return false;" onkeyup="return false;"
                               name="dlto" placeholder="To Date"/>
                    </div>
                    <p class="form-error"></p>
                </div>
            </div>

        </div>

        <div class="row">
            <div class="col-md-4 pull-right" >
                <button type="submit" class="btn btn-success btn-flat btn-block">Filter!</button>
            </div>
        </div>

    </div>
</form>
