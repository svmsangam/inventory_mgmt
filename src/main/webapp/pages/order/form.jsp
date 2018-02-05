<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/6/18
  Time: 6:46 PM
  To change this template use File | Settings | File Templates.
--%>

<div class="box-body">
    <div class="row">
        <div class="col-md-4">
            <div class="form-group">
                <input type="hidden" name="orderNo" value="${orderNo}"/>
                <label>Customer Name</label><a href="#" class="pull-right addClient" data-toggle="modal"
                                               data-target="#addCustomerModal"> Create a New Customer</a>
                <select class="choose1 form-control" name="clientId"></select>
                <p class="form-error">${orderError.clientInfo}</p>
            </div>
        </div>
        <div class="col-md-4">&nbsp;</div>
        <div class="col-md-4">
            <div class="form-group">
                <label>Delivery Date:</label>
                <div class='input-group date'>
                    <div class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                    </div>
                    <input type="text" class="form-control datepicker" onkeypress="return false;"
                           onkeyup="return false;"
                           value="<fmt:formatDate pattern="MM/dd/yyyy" value="${order.deliveryDate}"/>"
                           name="deliveryDate" placeholder="Delivery Date"/>
                </div>
                <p class="form-error">${orderError.deliveryDate}</p>
            </div>
        </div>
        <%--</div>

        <div class="row">--%>
        <div class="col-md-4">
            <div class="form-group">
                <label>Order Date:</label>
                <div class='input-group date' style="position: relative;">
                    <div class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                    </div>
                    <input type="text" class="form-control datepicker" onkeypress="return false;"
                           onkeyup="return false;"
                           value="<fmt:formatDate pattern="MM/dd/yyyy" value="${order.orderDate}"/>" name="orderDate"
                           placeholder="Order Date"/>
                </div>
                <p class="form-error">${orderError.orderDate}</p>
            </div>
        </div>
        <div class="col-md-4">&nbsp;</div>
        <div class="col-md-4">
            <div class="form-group">
                <label>Deliver To:</label>
                <div class="input-group">
                    <div class="input-group-addon">
                        <i class="fa fa-map-marker"></i>
                    </div>
                    <input class="form-control" value="${order.deliveryAddress}" type="text" id="searchTextField" name="deliveryAddress"
                           placeholder="Enter Address" required/>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-12">
        <div class="form-group">
            <button class="btn btn-xs btn-flat btn-primary pull-right" type="button" id="add_row">
                <span class="glyphicon glyphicon-plus"></span> Add Item
            </button>
        </div>
        <div>
            <table class="table table-bordered table-hover table-striped">
                <thead>
                <tr>
                    <th>item</th>
                    <th>quantity</th>
                    <th>rate</th>
                    <th>discount(%)</th>
                    <th>total</th>
                    <th></th>
                </tr>
                </thead>

                <tbody id="customFields">

                    <c:if test="${order.orderItemInfoDTOList ne null}">
                        <c:set var="mytotalamount" value="0"></c:set>
                        <c:forEach varStatus="i" var="orderItem" items="${order.orderItemInfoDTOList}">
                            <c:set var="myamount" value="${orderItem.quantity * orderItem.rate}"></c:set>
                            <c:set var="mydisamount" value="${myamount - myamount * discount / 100}"></c:set>
                            <c:set var="mytotalamount" value="${mytotalamount + mydisamount}"></c:set>
                            <c:set var="rate" value="0"></c:set>
                            <tr class="border-bottom itemTable">
                                <td>
                                    <select class='form-control item' name="orderItemInfoDTOList[${i}].itemInfoId" url="${pageContext.request.contextPath}/item/show">
                                        <option value="">select item</option>
                                        <c:forEach items="${itemList}" var="item">
                                            <c:choose>
                                                <c:when test="${item.itemId eq orderItem.itemInfoId}">
                                                    <option value="${item.itemId}" selected>${item.productInfo.name}-${item.tagInfo.name}</option>
                                                    <c:set var="rate" value="${item.sellingPrice}"></c:set>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${item.itemId}">${item.productInfo.name}-${item.tagInfo.name}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input onkeypress="return event.charCode > 47 &amp;&amp; event.charCode < 58;" pattern="[0-9]{5}" class="form-control form-control-sm quantity" onkeyup="calculate(amountUpdate);" name="orderItemInfoDTOList[${i}].quantity" value="${orderItem.quantity}" placeholder="enter quantity" required="" type="number"></td>
                                <td><input class="form-control form-control-sm" value="${rate}" name="orderItemInfoDTOList[${i}].rate" required="required" readonly="readonly" type="number"></td>
                                <td><input step="any" onkeypress="return event.charCode > 47 &amp;&amp; event.charCode < 58;" pattern="[0-9]{5}" value="${orderItem.discount}" class="form-control form-control-sm discount" onkeyup="calculate(amountUpdate);" name="orderItemInfoDTOList[${i}].discount" placeholder="enter discount percent" required="required" type="number"></td>
                                <td class="text-right">Rs.<span>${mydisamount}</span></td>
                                <td><a href="javascript:void(0);" class="remCF"><i class="glyphicon glyphicon-remove text-danger"></i></a></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <div class="col-md-4">
        <div class="form-group">
            <label for="description">Description:</label><br/>
            <textarea class="form-control" placeholder="write something.." name="description"
                      id="description" rows="3" cols="30" required>${order.description}</textarea>
            <p class="form-error">${orderError.description}</p>
        </div>
    </div>
    <div class="col-md-6">&nbsp;</div>
    <div class="col-md-2">
        <div class="form-group">
            <label class="lable">Tax(%) </label>
            <input type="number" value="${tax}" id="tax" step="any"
                   onkeypress="return (event.charCode >= 48 && event.charCode <= 57) || event.charCode == 46 || event.charCode == 0 "
                   class="form-control"
                   onkeyup='calculate(amountUpdate);'
                   name="tax" required/>
        </div>
        <div class="form-group">
            <label>Total Cost </label>
            <span>Rs.<strong id="total">0</strong></span>
        </div>
    </div>

</div>


<!-- add customer Modal start -->
<div id="addCustomerModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Add New Customer</h4>
            </div>
            <div class="modal-body">

                <div class="alert hidden alert-danger alert-dismissible addError">
                    <button type="button" class="close closeError" data-dismiss="alert" aria-hidden="true">&times;
                    </button>
                    <p class="errorModel"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Name</label>
                    <input type="text" id="name" class="form-control" name="name"
                           placeholder="Name">
                    <p class="form-error name"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Company Name</label>
                    <input type="text" id="companyName" class="form-control" name="companyName"
                           placeholder="Company Name">
                    <p class="form-error companyName"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Contact</label>
                    <input type="text" id="contact" class="form-control" name="contact"
                           placeholder="contact">
                    <p class="form-error contact"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Mobile Number</label>
                    <input type="text" class="form-control"
                           id="mobileNumber" name="mobileNumber" placeholder="Mobile Number">
                    <p class="form-error mobileNumber"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Email Address</label>
                    <input type="email" class="form-control" id="email" name="email"
                           placeholder="email address">
                    <p class="form-error email"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Pan Number</label>
                    <input type="text" class="form-control" id="pan" name="pan" placeholder="pan number">
                    <p class="form-error pan"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">City</label>
                    <select name="cityId" class="form-control select2" id="cityId">
                        <option value="">select city</option>
                        <c:forEach items="${cityList}" var="city">

                            <option value="${city.cityId}">${city.cityName}</option>

                        </c:forEach>
                    </select>
                    <p class="form-error cityId"></p>
                </div>

                <div class="form-group">
                    <label class="control-label">Street</label>
                    <input type="text" class="form-control" id="street" name="street"
                           placeholder="street">
                    <p class="form-error street"></p>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="savecustomer" pagecontext="${pageContext.request.contextPath}"
                        url="${pageContext.request.contextPath}/client/customer/save" class="btn btn-primary pull-left">
                    save changes
                </button>
                <button type="button" class="btn btn-danger pull-right closeAdd" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>

<!-- add customer Modal end -->