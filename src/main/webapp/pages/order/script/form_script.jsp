<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/11/18
  Time: 9:07 PM
  To change this template use File | Settings | File Templates.
--%>

<script>
    $(document).ready(function () {

        $(".choose1").select2({
            ajax: {
                url: '${pageContext.request.contextPath}/client/customer/search',
                dataType: 'json',
                headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                delay: 250,
                type: 'GET',
                data: function (params) {
                    return {
                        term: params.term, // search term
                        /* page: params.page*/
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    var arr = []
                    $.each(data.detail, function (index, value) {

                        if (value.companyName === null || "" === value.companyName) {

                            arr.push({
                                id: value.clientId,
                                text: value.name + ' - ' + value.mobileNumber
                            })
                        } else {
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
            escapeMarkup: function (markup) {
                return markup;
            },
            minimumInputLength: 1,
            placeholder: "Search Customer by Name & Mobile No"
        });
    });
</script>

<script type="text/javascript">

    var max = 1;
    var count = 1;

    $(document).ready(function () {
        select2ORItem($(".itemQrSearch"));

        /*  $(document).on("change", ".itemQrSearch", function () {
              addQrItem($(this));
          });*/


        $(document).on("click", ".calculation", function () {
            calculate(amountUpdate);
        });

        $("#customFields").on('click', '.remCF', function () {
            $(this).parent().parent().remove();
            max--;
            updateName();
            calculate(amountUpdate);
        });
    });


    function updateName() {
        $("tr").each(function (index) {
            index = index - 1;
            $(this).find("td:eq(0) > input").attr("name", "").attr("name", "orderItemInfoDTOList[" + index + "].itemInfoId");
            $(this).find("td:eq(1) > input").attr("name", "").attr("name", "orderItemInfoDTOList[" + index + "].quantity");
            $(this).find("td:eq(2) > input").attr("name", "").attr("name", "orderItemInfoDTOList[" + index + "].rate");
            $(this).find("td:eq(3) > input").attr("name", "").attr("name", "orderItemInfoDTOList[" + index + "].discount");
        })
    }

</script>


<script>

    function select2ORItem(that) {

        $(that).select2({
            closeOnSelect: false,
            ajax: {
                url: '${pageContext.request.contextPath}/item/search',
                dataType: 'json',
                headers: {'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')},
                delay: 250,
                type: 'GET',
                data: function (params) {
                    return {
                        term: params.term // search term
                        /* page: params.page*/
                    };
                },
                processResults: function (data, params) {
                    console.log(params.term);
                    params.page = params.page || 1;
                    var arr = []
                    if (data.detail.length === 1 && params.term === data.detail[0].productCode) {
                        addRowOnQrItem(new ItemDetails(data.detail[0].itemId, data.detail[0].productName + ' - ' + data.detail[0].itemName, "${pageContext.request.contextPath}/item/show", data.detail[0].sellingPrice));
                        $.notify({
                            icon: 'glyphicon glyphicon-ok',
                            title: '&nbsp;<strong>Product Added Successfully!</strong><br>',
                            message: "<strong>" + data.detail[0].productName + ' - ' + data.detail[0].itemName + "</strong> <br> <strong>Rate : </strong>" + data.detail[0].sellingPrice + "<br><strong>In Stock : </strong>" + data.detail[0].instock
                        }, {
                            type: 'success',
                            delay: 1
                        });
                    } else {
                        $.each(data.detail, function (index, value) {

                            arr.push({
                                id: value.itemId + "|" + value.sellingPrice,
                                text: value.productName + ' - ' + value.itemName + "<input type='hidden' class='qr_item_rate' value='" + value.sellingPrice + "'/>",
                                rate: value.sellingPrice
                            })
                        })
                    }
                    return {
                        results: arr/*,
                         pagination: {
                         more: (params.page * 1) < 2
                         }*/
                    };
                },
                cache: true
            },
            initSelection: function (element, callback) {

            },
            escapeMarkup: function (markup) {
                return markup;
            },
            minimumInputLength: 1,
            placeholder: "Search item by Name & code"
        }).on('change', function () {
            addQrItem($(this));

        });
    }

    function addQrItem(self) {

        that = $(".itemQrSearch :selected");
        var itemIdRateArr = that.val().split("|");
        addRowOnQrItem(new ItemDetails(itemIdRateArr[0], that.text(), "${pageContext.request.contextPath}/item/show", itemIdRateArr[1]));

    }

    function clearSelect2(self) {
        $(".select2-search__field").val("");
        self.empty();
        $(".select2-results__option").remove();
    }

    //we need call back here
    function addRowOnQrItem(itemModal) {

        console.log(itemModal);
        updateQuantityForSameItemId(itemModal);
    }

    function addRowItem(itemModal) {

        var row = "<tr class='border-bottom itemTable' id='" + itemModal.itemId + "'>";
        row += "<td><p>" + itemModal.name + "</p><input type='hidden' class='itemId' name='' value='" + itemModal.itemId + "'/></td>";
        row += "<td><input type='number' onkeypress='return validate(event);' pattern='[0-9]{5}' class='form-control form-control-sm quantity' onkeyup='calculate(amountUpdate);'  name='' placeholder='enter quantity' value='1' required/></td>";
        row += "<td><input type='number' class='form-control form-control-sm' name='' value='" + itemModal.rate + "' required readonly/></td>";
        row += "<td><input type='number' step='any' onkeypress='return validate(event);' pattern='[0-9]{5}' value='0' class='form-control form-control-sm discount' onkeyup='calculate(amountUpdate);' name='' placeholder='enter discount percent'  required /></td>";
        row += "<td class='text-right'>Rs.<span>0</span></div>";
        row += "<td><a href='javascript:void(0);' class='remCF'><i class='glyphicon glyphicon-remove text-danger'></i></a></td>";
        row += "</tr>";

        $("#customFields").prepend(row);

        updateName();
        calculate(amountUpdate);
        clearSelect2($(".itemQrSearch"));
    }

    function updateQuantityForSameItemId(itemModal) {
        var itemId = itemModal.itemId;
        var previousItem = $("#itemTable > tbody  > tr#" + itemId);
        console.log(previousItem);

        if (previousItem.length === 0) {
            addRowItem(itemModal);
        } else {
            var prevQuantity = previousItem.find("td:eq(1) > input").val();
            previousItem.find("td:eq(1) > input").val((parseInt(prevQuantity)) + 1);

            calculate(amountUpdate);
            clearSelect2($(".itemQrSearch"));
        }
    }

    function ItemDetails(itemId, name, showUrl, rate) {
        return {
            itemId: itemId,
            name: name,
            showUrl: showUrl,
            rate: rate
        }
    }

</script>


