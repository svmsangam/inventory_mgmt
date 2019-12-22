<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 12/22/19
  Time: 8:19 PM
  To change this template use File | Settings | File Templates.
--%>
<script>
    $(function () {
        $('.select2').select2();
        $(".datepicker").datepicker({});
    })
</script>

<script>
    $(document).ready(function () {

        $(".choose1").select2({
            ajax: {
                url: '${pageContext.request.contextPath}/client/vendor/search',
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
            placeholder: "Search Vendor by Name & Mobile No"
        });

        $(".chooseTag").select2({
            ajax: {
                url: '${pageContext.request.contextPath}/api/tag/search',
                dataType: 'json',
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
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

                        arr.push({
                            id: value.tagId,
                            text: value.name + ' - ' + value.code
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
            escapeMarkup: function (markup) {
                return markup;
            },
            minimumInputLength: 1,
            placeholder: "Search tag by name and code"
        });

        /*add new tag start*/
        //addNewTagBtn

        $(document).on("click", "#addNewTagBtn", function () {
            $(".addNewTag").prop("disabled", false);
            clearErrorData(".tagFormError");
            clearInputFormData(".addTagFormClear");

        });

        $(document).on("click", ".addNewTag", function () {
            var name = $("#tagName").val();
            var code = $("#tagCode").val();

            var url = $(this).attr("url");

            var tagService = new TagService();
            tagService.save(name, code, url);

        });

        /*add new tag end*/


        $(document).on("click", "#addNewSupplierBtn", function () {
            $(".addNewSupplier").prop("disabled", false);
            clearErrorData(".supplierFormError");
            clearInputFormData(".addSupplierFormClear");

        });

        $(document).on("click", ".addNewSupplier", function () {
            var suppliercompanyName = $("#suppliercompanyName").val();
            var suppliername = $("#suppliername").val();
            var suppliercontact = $("#suppliercontact").val();
            var suppliermobileNumber = $("#suppliermobileNumber").val();
            var supplieremail = $("#supplieremail").val();
            var suppliercityId = $("#suppliercityId").val();
            var supplierstreet = $("#supplierstreet").val();

            var url = $(this).attr("url");

            console.log("clityid : " + suppliercityId);
            var vendorService = new VendorService();
            vendorService.save(suppliercompanyName, suppliername, suppliercontact, suppliermobileNumber, supplieremail, suppliercityId, supplierstreet, url);

        });

    });

    function clearInputFormData(cls) {
        $(cls).val("");
    }

    function clearErrorData(cls) {
        $(cls).text("");
    }
</script>

