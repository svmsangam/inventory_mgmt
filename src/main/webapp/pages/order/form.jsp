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
                    <label>Customer Name</label><a href="" class="pull-right"> Create a New Customer</a>
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
                        <input type="text" class="form-control datepicker" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${item.deliveryDate}"/>" name="deliveryDate" placeholder="Delivery Date"/>
                    </div>
                    <p class="form-error">${itemError.deliveryDate}</p>
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
                        <input type="text" class="form-control datepicker" onkeypress="return false;" onkeyup="return false;" value="<fmt:formatDate pattern="MM/dd/yyyy" value="${item.orderDate}"/>" name="orderDate" placeholder="Order Date"/>
                    </div>
                    <p class="form-error">${itemError.orderDate}</p>
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
                        <input class="form-control" value="" type="text" id="searchTextField" name="deliveryAddress" placeholder="Enter Address" required/>
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
                <input type="number" value="0.0" id="tax" step="any"
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

