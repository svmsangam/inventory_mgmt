<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/11/18
  Time: 9:07 PM
  To change this template use File | Settings | File Templates.
--%>

<script>
    $(document).ready(function() {

        $(".choose1").select2({
            ajax: {
                url: '${pageContext.request.contextPath}/client/customer/search',
                dataType: 'json',
                headers : {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                delay: 250,
                type: 'GET',
                data: function (params) {
                    return {
                        term: params.term, // search term
                        /* page: params.page*/
                    };
                },
                processResults: function (data , params) {
                    params.page = params.page || 1;
                    var arr = []
                    $.each(data.detail, function (index, value) {

                        if(value.companyName === null || "" === value.companyName) {

                            arr.push({
                                id: value.clientId,
                                text: value.name + ' - ' + value.mobileNumber
                            })
                        }else {
                            arr.push({
                                id: value.clientId,
                                text: value.companyName + ' - ' + value.mobileNumber
                            })
                        }
                    })



                    return {
                        results: arr/*,
                         pagination: {
                         more: (params.page * 1) < 2
                         }*/
                    };
                },
                cache: true
            },
            escapeMarkup: function (markup) { return markup; },
            minimumInputLength: 1,
            placeholder: "Search Customer by Name & Mobile No"
        });
    });
</script>

<script type="text/javascript">

    var max = 1;
    var count = 1;

    function addRow() {

        var row = "<tr class='border-bottom itemTable' >";
        row += "<td><select class='choose2 form-control item' name='' url='${pageContext.request.contextPath}/item/show'></select></td>";
        row += "<td><input type='number' onkeypress='return validate(event);' pattern='[0-9]{5}' class='form-control form-control-sm quantity' onkeyup='calculate(amountUpdate);'  name='' placeholder='enter quantity' required/></td>";
        row += "<td><input type='number' class='form-control form-control-sm' name='' required readonly/></td>";
        row += "<td><input type='number' step='any' onkeypress='return validate(event);' pattern='[0-9]{5}' value='0' class='form-control form-control-sm discount' onkeyup='calculate(amountUpdate);' name='' placeholder='enter discount percent'  required /></td>";
        row += "<td class='text-right'>Rs.<span>0</span></div>";
        row += "<td><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></td>";
        row += "</tr>";
        $("#customFields").prepend(row);
        /*$(".item").select2();*/
        select2Item($(".item"));
        count++;
        max ++;
        updateName();
    }

    $(document).ready(function () {
        select2ORItem($(".itemQrSearch"));

        $(document).on("change" , ".itemQrSearch" , function () {
           addQrItem($(this));
        });

// for dynamically add or remove row
        $("#add_row").click(function () {
            //alert(count);
            if (max === 20){
                alert("max 10");
                return;
            }

            addRow();

        });
        $("#customFields").on('click', '.remCF', function () {
            $(this).parent().parent().remove();
            max--;
            updateName();
        });
    });


    function updateName() {
        $("tr").each(function (index) {
            index = index -1;
            $(this).find("td:eq(0) > select").attr("name" , "").attr("name" , "orderItemInfoDTOList["+index+"].itemInfoId");
            $(this).find("td:eq(1) > input").attr("name" , "").attr("name" , "orderItemInfoDTOList["+index+"].quantity");
            $(this).find("td:eq(2) > input").attr("name" , "").attr("name" , "orderItemInfoDTOList["+index+"].rate");
            $(this).find("td:eq(3) > input").attr("name" , "").attr("name" , "orderItemInfoDTOList["+index+"].discount");
        })
    }

    (function( $ ) {
        var $doc = $( document );
        $doc.ready( function(){
            $doc.on( 'keydown', function( e ){
                if ( ! $( e.target ).is( ':input' ) ) {

// props rauchg for pointing out e.shiftKey
                    if ( 90 === e.which && e.ctrlKey ) {
// `shift` and `w` are pressed. Do something.

                        if (max === 10){
                            alert("max 10");
                            return;
                        }

                        addRow();
                    }
                }

                else if ( $( e.target ).is( '.discount' ) ) {

// props rauchg for pointing out e.shiftKey
                    if ( 13 === e.which || e.which === 9) {
// `shift` and `w` are pressed. Do something.

                        if (max === 10){
                            alert("max 10");
                            return;
                        }

                        addRow();
                    }
                }

            });
        });

    })( jQuery );

</script>


<script>

    function select2Item(that) {

            $(that).select2({
                ajax: {
                    url: '${pageContext.request.contextPath}/item/search',
                    dataType: 'json',
                    headers : {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                    delay: 250,
                    type: 'GET',
                    data: function (params) {
                        return {
                            term: params.term, // search term
                            /* page: params.page*/
                        };
                    },
                    processResults: function (data , params) {
                        params.page = params.page || 1;
                        var arr = []
                        $.each(data.detail, function (index, value) {

                            arr.push({
                                id: value.itemId,
                                text: value.productName + ' - ' + value.itemName
                            })
                        })



                        return {
                            results: arr/*,
                         pagination: {
                         more: (params.page * 1) < 2
                         }*/
                        };
                    },
                    cache: true
                },
                escapeMarkup: function (markup) { return markup; },
                minimumInputLength: 1,
                placeholder: "Search item by Name & code"
            });
        }

    function select2ORItem(that) {

        $(that).select2({
            closeOnSelect: false,
            ajax: {
                url: '${pageContext.request.contextPath}/item/search',
                dataType: 'json',
                headers : {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                delay: 250,
                type: 'GET',
                data: function (params) {
                    return {
                        term: params.term // search term
                        /* page: params.page*/
                    };
                },
                processResults: function (data , params) {
                    params.page = params.page || 1;
                    var arr = []
                    $.each(data.detail, function (index, value) {

                        arr.push({
                            id: value.itemId,
                            text: value.productName + ' - ' + value.itemName
                        })
                    })

                    return {
                        results: arr/*,
                         pagination: {
                         more: (params.page * 1) < 2
                         }*/
                    };
                },
                cache: true
            },
            initSelection: function(element, callback) {

            },
            escapeMarkup: function (markup) { return markup; },
            minimumInputLength: 1,
            placeholder: "Search item by Name & code"
        });
    }

        function addQrItem(self) {

            that = $(".itemQrSearch :selected");
            addRowOnQrItem(new ItemDetails(that.val() , that.text() , "${pageContext.request.contextPath}/item/show"));

        }

        function getRateOnQr(itemModal , self) {
        console.log($('.table').find("tbody > tr:eq(0)").find("td:eq(0)").find("select").val());
            var orderService = new OrderInfoService();
            orderService.getItemById(itemModal.itemId, itemModal.showUrl , $('.table').find("tbody > tr:eq(0)").find("td:eq(0)").find("select"));
            //select2ORItem($(".itemQrSearch"));
            clearSelect2($(".itemQrSearch"));
        }

        function clearSelect2(self) {
            $(".select2-search__field").val("");
            self.empty();
            $(".select2-results__option").remove();
        }

    function addRowOnQrItem(itemModal) {

        var row = "<tr class='border-bottom itemTable' >";
        row += "<td><select class='choose2 form-control item' name='' url='${pageContext.request.contextPath}/item/show'>" +
                "<option selected value='"+itemModal.itemId+"'>" +itemModal.name + "</option>" +
            "</select></td>";
        row += "<td><input type='number' onkeypress='return validate(event);' pattern='[0-9]{5}' class='form-control form-control-sm quantity' on onkeyup='calculate(amountUpdate);'  name='' placeholder='enter quantity' required/></td>";
        row += "<td><input type='number' class='form-control form-control-sm' name='' required readonly/></td>";
        row += "<td><input type='number' step='any' onkeypress='return validate(event);' pattern='[0-9]{5}' value='0' class='form-control form-control-sm discount' onkeyup='calculate(amountUpdate);' name='' placeholder='enter discount percent'  required /></td>";
        row += "<td class='text-right'>Rs.<span>0</span></div>";
        row += "<td><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></td>";
        row += "</tr>";
        $("#customFields").prepend(row);
        /*$(".item").select2();*/
        select2Item($(".item"));
        count++;
        max ++;
        updateName();
        getRateOnQr(itemModal , self);
    }

    function ItemDetails(itemId , name , showUrl) {
        return {
            itemId : itemId,
            name : name,
            showUrl : showUrl
        }
    }

</script>


