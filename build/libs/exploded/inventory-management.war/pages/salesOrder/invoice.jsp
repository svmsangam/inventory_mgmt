<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/pages/common/header.jsp"%>
<%@include file="/pages/common/businessownernavbar.jsp"%>
<%@include file="/pages/common/businessownersidebar.jsp"%>

<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
<div class="row">
	<ol class="breadcrumb">
		<li><a href="\"><svg class="glyph stroked home">
					<use xlink:href="#stroked-home"></use></svg></a></li>
		<li class="active">Invoice</li>
	</ol>
</div>

<c:if test="${not empty message}">
<div class="alert alert-info alert-dismissable">
<a href="#" class="close" data-dismiss="alert" aria-label="close">x</a>
	<strong>${message}</strong>
</div>
</c:if>

<div class="row">
<div class="col-md-12">
<div class="panel panel-default">
<div class="panel-heading">
	<div class="col-md-offset-5">Add Invoice
	<div class="pull-right">Invoice No. #${invoiceNo}</div>
	</div>
</div>
<div class="panel-body">

<div class="col-md-4">		
<label>Invoice Type: </label><br>
<span>by Order </span><input type="radio" class="or" name="gender" value="sale" checked/>&nbsp;&nbsp;&nbsp;
<span>by Customer </span><input type="radio" class="or" name="gender" value="clientInfo"/>
</div>			
<form action="${pageContext.request.contextPath}/app/addinvoice"
method="POST" modelAttribute="invoiceDto" enctype="multipart/form-data"
class="col-md-12" id="registerform" novalidate="novalidate">

<div class="col-md-3" id="inputDiv">
	<input type="hidden" name= "oldItemIdList" value="0">
	<input type="hidden" name= "quantity" value="0">
	
	<div class="salediv">
	<label class="lable">sales Order</label>
	<select  class="form-control sale select2" id="sale" name="salesOrderId" >
		<option	value="0">select order</option>
		<c:forEach var="salesOrder" oldItems="${salesOrderList}">
			<option value="${salesOrder.orderId}">${salesOrder.orderNo}</option>
		</c:forEach>
	</select>
	<p class="error">${error.salesOrder}</p>
	</div>
</div>

	<div class="customerdiv col-md-3">
	<label class="lable">Customer</label>
	<select  class="form-control select2 clientInfo"  name="customerId" disabled="disabled">
		<option	value="0">select clientInfo</option>
		<c:forEach var="clientInfo" oldItems="${customerList}">
			<option value="${clientInfo.customerId}" class="cs">${clientInfo.uniqueName}</option>
		</c:forEach>
	</select>
	</div>

<div class="col-md-3" id="cn">
	<label class="lable">Customer &nbsp;&nbsp;&nbsp;</label>
	<div id="customerName"></div> 
	<input id="clientInfo" type="hidden" name="customerId" value="0"/>
</div>

<div class="col-md-3" id="inputDiv">
	<label class="lable">Invoice Date </label>
	<input type="text" id="invoiceDate" name="invoiceDate" value="${invoiceInfo.invoiceDate }" class="form-control datepicker" placeholder="invoiceInfo date" required />
	<p class="error">${error.invoiceDate}</p>
</div>

<div class="col-md-3" id="inputDiv">
	<label class="lable">Due Date </label> 
	<input type="text" class="form-control" id="datepicker2" name="dueDate" value="${invoiceInfo.dueDate }" placeholder="due date" required />
	<p class="error">${error.dueDate}</p>
</div>

<div id="inputDiv">
<a id="" myid="add_row" class="btn btn-xs btn-primary pull-left idclass"><span class="glyphicon glyphicon-plus"></span>Add Item</a>
<p class="error">${error.oldItemList}</p>
	<table class="table table-responsive table-striped table-bordered"
		id="customFields" >
		<thead class="thead-inverse">
			<tr>
				<th>Item</th>
				<th>Quantity</th>
				<th>Rate</th>
				<th>Tax</th>
				<th>Amount</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<tr class="auto"><td style="display: none;"><input type="hidden" name="oldItemIdList" value="" /><input type="hidden" name="quantity" value="" /></td></tr>
		</tbody>
	</table>
</div>
<div class="row">
<div class="col-md-4" id="inputDiv">
	<label class="lable">Notes </label>
	<textarea rows="4" id="input" placeholder="write something.." class="form-control" name="notes" value="${invoiceInfo.notes}"></textarea>
</div>
<div class="col-md-4"></div>
<div class="col-md-2">
<label class="lable">Discount (%)</label> <input type="number"
		step="any"
		onkeypress="return (event.charCode >= 48 && event.charCode <= 57) ||  
		event.charCode == 46 || event.charCode == 0 "
		id="discount" class="form-control double" name="discount"
		onKeyup="update_amounts();" value="${invoiceInfo.discount}" required />
	<p class="error">${error.discount}</p>
</div>
<div class="col-md-2">
	<label class="lable" >Total Cost </label>
	<input type="number" id="total" step="any" onkeypress="return (event.charCode >= 48 && event.charCode <= 57) ||  
	event.charCode == 46 || event.charCode == 0 " id="total" class="form-control" readOnly required />
</div>
</div>

	<div class="pull-right">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
		<input type="submit" value="Save Invoice" class="btn btn-primary">
	</div>
	</form>
</div>
</div>
</div>
</div>
</div>
<%@include file="../common/footer.jsp"%>

<script type="text/javascript">
						$(document).ready(function() {
							$(".select2").select2();
							
							$('.customerdiv').hide();
				    		
				    		//$('.clientInfo').prop('disabled', false);
				    		
							
				    		$(document).on('change', '.or', function(){
				    			$('.idclass').hide();
				    			
								$('.auto').each(function(){
					        		   $(this).remove();
								});
								
								$('.add').each(function(){
					        		   $(this).remove();
								});
								
							    	 
							    	if($(this).val() == 'clientInfo'){
							    	
							    		$('.clientInfo').removeAttr("disabled");
							    	
							    		$('.salediv').hide(); 
							    		$('#cn').hide(); 
							    		$('#clientInfo').prop('disabled', true);
							    		$('.sale').prop('disabled', true);
							    		$('.customerdiv').show();
							    	
							    		$('.idclass').show();
							    		$('.idclass').attr('id', $('.idclass').attr('myid'));
							    	} else {
							    	
							    		$('#cn').show();
							    		$('.customerdiv').hide();
							    		$('.salediv').show();
							    		$('#clientInfo').removeAttr("disabled");
							    		$('.sale').removeAttr("disabled");
							    		$('.clientInfo').prop('disabled', true);
							    		$('.idclass').hide();
							    		$('.idclass').attr('id','');
							
							    	}
							    	
							});
						});
					</script>
					
					<script type="text/javascript">

						var count = 1;
						
						// for dynamically add or remove row
						$(document).ready(function(){
						    $(document).on('click', '#add_row', function(){
						    	
						    	var row = "<tr class='add'><td><select id='js-example-responsive' class='form-control input-md selectMe  mytest myclass"+count+"' arrt='"+count+"' style='width: 100%' name='oldItemIdList' ><option value='null' selected>select OldItem</option><c:forEach var='oldItem' oldItems='${oldItemList}'><option value='${oldItem.id}'>${oldItem.itemName}</option></c:forEach></select></td>";
						    	row += '<td><input type="number" id="quantity'+count+'" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="qty" onKeyup="update_amounts();"   name="quantity" placeholder="enter quantity" required/></td>';
						    	row += '<td><input type="number" step="any" id="rate'+count+'" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="rate rate'+count+'"  required readOnly/></td>';
						    	row += '<td><input type="number" step="any" id="tax'+count+'" onkeypress="return event.charCode > 47 && event.charCode < 58;" pattern="[0-9]{5}" class="tax tax'+count+'"   required readOnly/></td>';
						    	row += '<td><span id="amount" class="amount">0</span></td><td> <a href="javascript:void(0);" class="remCF"><span class="btn btn-danger btn-xs glyphicon glyphicon-remove"></span></a></td>';
						        $("#customFields").append(row);
						       
									 
								$(".selectMe").select2();// for select2 dropdown
									
						        count++;
						    });
						    
						    $("#customFields").on('click','.remCF',function(){
						        $(this).parent().parent().remove();
						     
						        update_amounts();
						    });
						});

						function update_amounts()
						{	
							
							 var sum = 0.0;
						    $('#customFields > tbody  > tr').each(function() {
						        var qty = $(this).find('.qty').val();
						        if(qty == null){
						        	qty = 0;
						        }
						        var rate = $(this).find('.rate').val();
						        if(rate == null){
						        	rate = 0;
						        }
						        var tax = $(this).find('.tax').val();
						        if(tax == null){
						        	tax = 0;
						        }
						       
						        var amount = (qty*rate);
						        amount = (amount + ((amount * tax) / 100));
						        sum+=amount;
						       
						        $(this).find('.amount').text(''+amount.toFixed(2));
						    });
						    
						    var discount = $('#discount').val();
					        if(discount == null){
					        	discount = 0;
					        }
					        
					        //alert(discount);
						    document.getElementById("total").value = (sum - ((sum * discount) / 100)).toFixed(2);

}
                        $(document).ready(function(){
							// for to put rate tax of selected oldItem
							 $(document).on('change', '#js-example-responsive', function(){
								 var id = $(this).attr('arrt');
							var itemId = $(this, "option:selected").val();
							//alert("hello");
							
							if(itemId == null){
								 document.getElementById("quantity"+id).value = 0;
								 document.getElementById("rate"+id).value = 0;
					        	 document.getElementById("tax"+id).value = 0;
								 update_amounts();
								return;
							}
							//alert(itemId);
							
							 $.ajax({
							        type: "GET",
							        url: "getItem",
							        contentType : "application/json", 
							        data : {itemId, itemId},       
									dataType : 'json',  
									timeout : 100000,
							        success: function(data) {
							           if(data.status=='Success'){
							        	
							        	  document.getElementById("rate"+id).value = data.detail.rate;
							        	  document.getElementById("tax"+id).value = data.detail.tax;
							        	   update_amounts();
							           }
							        }
							    });
							
							});
							 
							 // for autopopulate of oldItem from selected salesorder
							 $(document).on('change', '.sale', function(){
						    	var salesOrderId = $(this).val();
						    	console.log("dhiraj" + salesOrderId);
						    	if(salesOrderId == null || salesOrderId == 0){
						    		$('.auto').each(function(){
						        		   $(this).remove();
						        		   var row = '<tr class="auto"><td><input type="hidden" name="oldItemIdList" value="" /><input type="hidden" name="quantity" value="" /></td></tr>';
						        		   $("#customFields").append(row);
						        		   document.getElementById("clientInfo").value = "";
						        	   });
						    		return;
								}
								//alert(salesOrderId);
								 $.ajax({
								        type: "GET",
								        url: "getSalesOrderWithId",
								        contentType : "application/json", 
								        data : {salesOrderId, salesOrderId},       
										dataType : 'json',  
										timeout : 100000,
								        success: function(data) {
								           if(data.status=='Success'){
								        	   var oldItemList = data.detail.oldItemDtoList;
								        	   var quantityList = data.detail.quantities;
								        	   //console.log(oldItemList[0].itemName);
								        	   $('.auto').each(function(){
								        		   $(this).remove();
								        	   });
								        	    for(i = 0 ; i<oldItemList.length ; i++){
								        			
								        	    	  var row = '<tr class="auto">';
										        	  row +="<td><label class='lable'>"+oldItemList[i].itemName+"</label></td>";
										        	  row +='<td>'+quantityList[i]+'<input type="hidden" class="qty" value="'+quantityList[i]+'"/></td>';
										        	  row +='<td ><input type="hidden" class="rate" value="'+oldItemList[i].rate+'"/>'+oldItemList[i].rate+'</td>';
										        	  row +='<td><input type="hidden" class="tax" value="'+oldItemList[i].tax+'"/>'+oldItemList[i].tax+'</td><td><span id="amount" class="amount">0</span></td>';
										        	  row += '</tr>';
										        	  $("#customFields").append(row); 
								        	  } 
									        	  
									        	  
									        	 
									        	   document.getElementById("clientInfo").value = data.detail.customerId;
									        	   document.getElementById("customerName").innerHTML = data.detail.customerName;
									        	   update_amounts();
									        
								           }
								        }
								        });
						    });
						});
					</script>
							
<script type="text/javascript">
$(document).ready(function() {
	
	$("#registerform").validate({
	    
        // Specify the validation rules
        rules: {
        	
        
        customerId: { required: true },
      	
      	invoiceNo: { required: true },
      	
    	salesOrderId:{required: true},
    	
    	discount: {
    		  required: true,
    		  max: 100,
    		  min: 0
    	},
    	invoiceDate: {
  		  required: true,
  		  maxlength: 10,
  		  minlength: 10
  	},
  	dueDate: {
		  required: true,
		  maxlength: 10,
		  minlength: 10
	},
	notes: {
		  required: true,
		  maxlength: 2000
		
	}
        },
        // Specify the validation error messages
        messages: {
        	


        	discount: {
        		required: "Please provide a discount",
        		 max: "Your discount must be less than 100",
        		 min: "Your discount must be more than 0"
        	},

        	customerId: {
        		required: "Please provide a shipmentChannel"
        		 
        	},
        	
        	salesOrderId: {
        		required: "Please provide a shipmentChannel"
        		 
        	},
        	invoiceNo: {
        		required: "Please provide a shipmentChannel"
        		 
        	},

        	invoiceDate: {
        		required: "Please provide a orderDate",
        		 maxlength: "not a valid date",
        		 minlength: "not a valid date"
        	},

        	dueDate: {
        		required: "Please provide a shipmentDate",
       		 maxlength: "not a valid date",
       		 minlength: "not a valid date"
        	},
        	
        	notes: {
        		required: "Please provide a Note",
        		 maxlength: "Your Notes must be less than 1000"
        		 
        	}
        },
        
        submitHandler: function(form) {
            form.submit();
        }
    });
});
</script>

	<script>
    $(function(){
         
    	$(".datepicker, #datepicker2").datepicker({
    		
    		/* dateFormat: 'yy-mm-dd' */
    		
    	});
    });
</script>
					
<style>
.input {
	float: right;
	margin-left: 0px;
	margin-right: 900px;
	margin-top: 0px;
}

.lable {
	float: left;
}

#inputDiv {
	margin-top: 10px;
}

#inputDivRightTop {
	margin-top: 10px;
	float: right;
	margin-right: 10px;
}

#inputDivRightBottom {
	margin-top: 10px;
	float: left;
	margin-left: 790px;
}
</style>

