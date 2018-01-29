<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/29/18
  Time: 10:20 AM
  To change this template use File | Settings | File Templates.
--%>
<form action="${pageContext.request.contextPath}/invoice/filter" method="GET">
    <div class="well well-sm">

        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Fiscal Year</label>
                    <select class="choose2 form-control" name="fiscalYearId">

                        <option value="">select fiscal year</option>

                        <c:forEach items="${fiscalYearList}" var="fiscalYear">
                            <c:choose>
                                <c:when test="${filterDTO.fiscalYearId eq fiscalYear.fiscalYearInfoId}">
                                    <option value="${fiscalYear.fiscalYearInfoId}" selected>${fiscalYear.title}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${fiscalYear.fiscalYearInfoId}">${fiscalYear.title}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <p class="form-error"></p>
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label>Client Name</label>
                    <select class="choose1 form-control" name="clientId"></select>
                    <p class="form-error"></p>
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
                        <%--<input type="text" class="form-control datepicker"
                               onkeypress="return false;" onkeyup="return false;"
                               value="<fmt:formatDate pattern="MM/dd/yyyy" value=""/>"
                               name="from" placeholder="From Date"/>--%>
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
                    <label>Receivable Greater Than</label>
                    <input type="number" value="<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableGt}"/>" class="form-control" name="receivableGt" placeholder="receivable greater than">
                </div>
            </div>

            <div class="col-md-3">
                <div class="form-group">
                    <label>Receivable Less Than</label>
                    <input type="number" value="<fmt:formatNumber type="number" maxFractionDigits="3" groupingUsed="false" value="${filterDTO.receivableLt}"/>" name="receivableLt" class="form-control" placeholder="receivable less than">
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
