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
        row += "<td><select class='form-control item' name='' url='${pageContext.request.contextPath}/item/show'><option value=''>select item</option><c:forEach items="${itemList}" var="item"><option value='${item.itemId}'>${item.productInfo.name}-${item.tagInfo.name}</option></c:forEach> </select></td>";
        row += "<td><input type='number' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control form-control-sm quantity' onkeyup='calculate(amountUpdate);'  name='' placeholder='enter quantity' required/></td>";
        row += "<td><input type='number' class='form-control form-control-sm' name='' required readonly/></td>";
        row += "<td><input type='number' step='any' onkeypress='return event.charCode > 47 && event.charCode < 58;' pattern='[0-9]{5}' class='form-control form-control-sm discount' onkeyup='calculate(amountUpdate);' name='' placeholder='enter discount percent'  required /></td>";
        row += "<td class='text-right'>Rs.<span>0</span></div>";
        row += "<td><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></td>";
        row += "</tr>";
        $("#customFields").prepend(row);
        $(".item").select2();
        count++;
        max ++;
        updateName();
    }

    $(document).ready(function () {

// for dynamically add or remove row
        $("#add_row").click(function () {
            //alert(count);
            if (max === 10){
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

