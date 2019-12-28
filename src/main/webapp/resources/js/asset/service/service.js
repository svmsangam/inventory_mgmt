/**
 * Created by dhiraj on 8/12/17.
 */

function successNotify(messege) {
    $.notify({
        icon: 'glyphicon glyphicon-ok',
        title: '&nbsp;<strong>Success!</strong><br>',
        message: messege
    }, {
        type: 'success',
        delay: 1
    });
}

function dangerNotify(messege) {
    $.notify({
        icon: 'glyphicon glyphicon-warning-sign',
        title: '&nbsp;<strong>Alert!</strong><br>',
        message: messege
    }, {
        type: 'danger',
        delay: 1
    });
}

function infoNotify(messege) {
    $.notify({
        icon: 'glyphicon glyphicon-info-sign',
        title: '&nbsp;<strong>Product Added Successfully!</strong><br>',
        message: messege
    }, {
        type: 'info',
        delay: 10

    });
}

var opts = {
    lines: 13 // The number of lines to draw
    , length: 2 // The length of each line
    , width: 16 // The line thickness
    , radius: 42 // The radius of the inner circle
    , scale: 1.25 // Scales overall size of the spinner
    , corners: 1 // Corner roundness (0..1)
    , color: '#000' // #rgb or #rrggbb or array of colors
    , opacity: 0 // Opacity of the lines
    , rotate: 21 // The rotation offset
    , direction: 1 // 1: clockwise, -1: counterclockwise
    , speed: 1 // Rounds per second
    , trail: 69 // Afterglow percentage
    , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
    , zIndex: 2e9 // The z-index (defaults to 2000000000)
    , className: 'spinner' // The CSS class to assign to the spinner
    , top: '50%' // Top position relative to parent
    , left: '50%' // Left position relative to parent
    , shadow: false // Whether to render a shadow
    , hwaccel: false // Whether to use hardware acceleration
    , position: 'absolute' // Element positioning
}


function startLoading() {

    document.getElementById("myNavSpinner").style.width = "100%";
    document.getElementById("myNavSpinner").style.height = "100%";

    var target = document.getElementById('foo');

    var spinner = new Spinner(opts);

    spinner.spin(target);

    return spinner;
}


function stopLoading(spinner) {
    var target = document.getElementById('foo');
    spinner.stop(target);
    document.getElementById("myNavSpinner").style.width = "0%";
    document.getElementById("myNavSpinner").style.height = "0%";
}

//spinner

// user service start

function UserService() {
    var userRequest;

    return {

        list: [],

        save: function (user, url, pagecontext) {

            var that = new UserService();

            userRequest = $.ajax({
                type: "POST",
                url: url,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: user,
                dataType: 'json',
                timeout: 100000,
                beforeSend: function () {
                    // setting a timeout
                    if (userRequest !== undefined) {
                        userRequest.abort();
                    }
                },
                success: function (data) {

                    var result = data.detail;

                    var msg = data.message;

                    if (data.status === 'Success') {

                        that.setDataToDOM(result, pagecontext);
                        that.successMsg(msg);
                        $(".closeAdd").click();
                    }

                    if (data.status === 'Failure') {
                        that.errorMsg(msg);
                        $(".closeAdd").click();
                    }

                    if (data.status === 'Validation Failed') {

                        that.errorOnForm(msg);
                        that.setError(result);
                    }
                }
            });

        },

        getById: function (userId) {

        },

        changeStore: function (storeId, url, that) {
            var sppiner;
            userRequest = $.ajax({
                type: "GET",
                url: url,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: {storeId: storeId},
                dataType: 'json',
                timeout: 30000,
                tryCount: 0,
                retryLimit: 3,
                beforeSend: function () {
                    // setting a timeout
                    if (userRequest !== undefined) {
                        userRequest.abort();
                    }

                    sppiner = startLoading();
                },
                success: function (data) {

                    var result = data.detail;

                    var msg = data.message;

                    if (data.status === 'Success') {

                        that.prop("disabled", true);
                        $('#table2 input[type=checkbox]').each(function () {
                            console.log($(this).val() + "-" + (this.checked ? "checked" : "not checked"));

                            if ((!!this.checked)) {
                                if (storeId !== $(this).val()) {
                                    $(this).prop("disabled", false).click();

                                }
                            }
                        });
                        stopLoading(sppiner);
                    }

                    if (data.status === 'Failure') {
                        stopLoading(sppiner);
                        window.location.reload();
                    }

                    if (data.status === 'Validation Failed') {
                        stopLoading(sppiner);
                        window.location.reload();
                    }
                },
                error: function (xhr, textStatus, errorThrown) {

                    console.log(xhr + " " + textStatus + " " + errorThrown);

                    if (textStatus == 'timeout') {

                        this.tryCount++;

                        if (this.tryCount <= this.retryLimit) {
                            //try again
                            $.ajax(this);
                            return;
                        } else {
                            //cancel request
                            stopLoading(sppiner);
                            window.location.reload();

                            return;
                        }

                    }

                    if (xhr.status == 500) {
                        //handle error
                        stopLoading(sppiner);
                        window.location.reload();
                    } else {
                        //handle error
                        stopLoading(sppiner);
                        window.location.reload();
                    }
                }
            });
        },

        setError: function (error) {
            $(".inventoryuser").text(error.username);
            $(".userpassword").text(error.password);
            $(".userrepassword").text(error.repassword);
            $(".userType").text(error.userType);
            $(".storeId").text(error.storeId);
        },

        clearError: function () {
            $(".inventoryuser").text("");
            $(".userpassword").text("");
            $(".userrepassword").text("");
            $(".userType").text("");
            $(".errorModel").text("");
            $(".storeId").text("");
            $(".addError").removeClass("hide").removeClass("show").addClass("hide");
        },

        clearForm: function () {
            $("#inventoryuser").val("");
            $("#userpassword").val("");
            $("#userrepassword").val("");
            $("#userType").prop('selectedIndex', 0);
            $("#storeId").prop('selectedIndex', 0);
        },

        successMsg: function (msg) {
            successNotify(msg)
        },

        errorMsg: function (msg) {
            dangerNotify(msg)
        },

        errorOnForm: function (error) {
            $(".addError").removeClass("hide").removeClass("show").addClass("show");
            $(".errorModel").text(error);
        },

        setDataToDOM: function (data, pagecontext) {
            var row = "<tr>";
            row += "<td>0</td>";
            row += "<td>" + data.inventoryuser + "</td>";
            row += "<td>" + data.storeName + "</td>";
            row += "<td>" + data.userType + "</td>";
            row += "<td>";
            if (data.enable === true) {
                row += "<span class='label label-success'>Activated</span>";
            } else {
                row += "<span class='label label-danger'>Deactivated</span>";
            }

            row += "</td>";

            row += "<td>";
            if (data.enable === false) {

                row += "<a href='" + pagecontext + "/user/updateenable?userId=" + data.userId + "' onclick='return confirm('Are you sure you want to Activate?')'><span class='label label-success'>Activate ?</span></a>";

            } else {
                row += "<a href='" + pagecontext + "/user/updateenable?userId=" + data.userId + "' onclick='return confirm('Are you sure you want to Deactivate?')'><span class='label label-danger'>Deactivate ?</span></a>";
            }

            row += "</td>";

            if (data.userType === "USER") {
                row += "<td><a href='" + pagecontext + "/user/manage?userId=" + data.userId + "' class='btn btn-xs bg-purple'><i class='fa fa-cogs'></i> Manage</a></td>";
            }

            row += "</tr>";

            $("#myData").prepend(row);

            $("tbody > tr").each(function (index) {
                $(this).find("td:eq(0)").text(index + 1);
            })
        }
    };
}

// user service end


// store service start

function StoreService() {
    var storeRequest;

    return {

        list: [],

        save: function (store, url, pagecontext, isSave) {

            var that = new StoreService();

            if (storeRequest !== undefined) {
                storeRequest.abort();
            }

            storeRequest = $.ajax({
                type: "POST",
                url: url,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: store,
                dataType: 'json',
                timeout: 100000,
                beforeSend : function () {
                  if (storeRequest !== undefined){
                      storeRequest.abort();
                      blockUiZ(2000);
                  }
                },
                success: function (data) {

                    var result = data.detail;

                    var msg = data.message;

                    if (data.status === 'Success') {

                        if (isSave === true) {
                            that.setDataToDOM(result, pagecontext);
                            that.successMsg(msg);
                            $(".closeAdd").click();
                        } else {
                            that.successMsg(msg);
                            window.location.reload();
                        }

                        $.unblockUI();
                    }

                    if (data.status === 'Failure') {
                        that.errorMsg(msg);
                        $(".closeAdd").click();
                        $.unblockUI();
                    }

                    if (data.status === 'Validation Failed') {

                        that.errorOnForm(msg);
                        that.setError(result);
                        $.unblockUI();
                    }
                }
            });

        },

        getById: function (url) {

            var that = new StoreService();

            that.clearLoadData();

            if (storeRequest !== undefined) {
                storeRequest.abort();
            }

            storeRequest = $.ajax({
                type: "GET",
                url: url,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                dataType: 'json',
                timeout: 100000,
                beforeSend : function () {
                  if(storeRequest !== undefined){
                      storeRequest.abort();
                      blockUiZ(2000)
                  }
                },
                success: function (data) {

                    var result = data.detail;

                    var msg = data.message;

                    if (data.status === 'Success') {

                        that.loadData(result);
                    }

                    if (data.status === 'Failure') {
                        that.errorMsg(msg);
                        $(".closeShow").click();
                    }

                    $.unblockUI();
                }
            });
        },

        loadData: function (data) {

            $(".storeName").text("").text(data.name);
            $(".storeEmail").text("").text(data.email);
            $(".storeContact").text("").text(data.contact);
            $(".storeStreet").text("").text(data.street);
            $(".storeMobile").text("").text(data.mobileNumber);
            $(".storePan").text("").text(data.panNumber);
            $(".storeReg").text("").text(data.regNumber);
            $(".storeCity").text("").text(data.cityName);


            $("#emailEdit").val("").val(data.email);
            $("#panEdit").val("").val(data.panNumber);
            $("#regEdit").val("").val(data.regNumber);
            $("#contactEdit").val("").val(data.contact);
            $("#mobileEdit").val("").val(data.mobileNumber);
            $("#streetEdit").val("").val(data.street);
            $("#storeIdEdit").attr("storeId", "").attr("storeId", data.storeId);

        },

        clearLoadData: function () {
            $(".storeName").text("");
            $(".storeEmail").text("");
            $(".storeContact").text("");
            $(".storeStreet").text("");
            $(".storeMobile").text("");
            $(".storePan").text("");
            $(".storeReg").text("");
            $(".storeCity").text("");

            $("#emailEdit").val("");
            $("#panEdit").val("");
            $("#regEdit").val("");
            $("#contactEdit").val("");
            $("#mobileEdit").val("");
            $("#streetEdit").val("");
            $("#storeIdEdit").attr("storeId", "");
        },

        setError: function (error) {
            $(".name").text(error.name);
            $(".contact").text(error.contact);
            $(".email").text(error.email);
            $(".mobile").text(error.mobileNumber);
            $(".cityId").text(error.cityName);
            $(".reg").text(error.regNumber);
            $(".pan").text(error.panNumber);
            $(".street").text(error.street);
        },

        clearError: function () {
            $(".name").text("");
            $(".contact").text("");
            $(".email").text("");
            $(".mobile").text("");
            $(".cityId").text("");
            $(".reg").text("");
            $(".pan").text("");
            $(".street").text("");
            $(".errorModel").text("");
            $(".addError").removeClass("hide").removeClass("show").addClass("hide");
        },

        clearForm: function () {
            $("#name").val("");
            $("#contact").val("");
            $("#email").val("");
            $("#mobile").val("");
            $("#reg").val("");
            $("#pan").val("");
            $("#street").val("");
            /*$("#cityId").select2("val", "");*/
        },

        successMsg: function (msg) {
            successNotify(msg)
        },

        errorMsg: function (msg) {
            dangerNotify(msg)
        },

        errorOnForm: function (error) {
            $(".addError").removeClass("hide").removeClass("show").addClass("show");
            $(".errorModel").text(error);
        },

        setDataToDOM: function (data, pagecontext) {
            var row = "<tr>";
            row += "<td>0</td>";
            row += "<td>" + data.name + "</td>";
            row += "<td>" + data.contact + "</td>";
            row += "<td>" + data.email + "</td>";
            row += "<td>" + data.cityName + "</td>";
            row += "<td>" + data.street + "</td>";
            row += "<td><span class='label label-success'>Active</span></td>";
            row += "<td><button type='button' class='btn btn-info btn-sm  btn-flat viewStoreInfo' url='" + pagecontext + "/store/show/" + data.storeId + "' data-toggle='modal' data-target='#modal-view'><span class='glyphicon glyphicon-eye-open'></span>View</button></td>";
            row += "<td>new</td>";
            row += "</tr>";

            $("#myData").prepend(row);

            $("tbody > tr").each(function (index) {
                $(this).find("td:eq(0)").text(index + 1);
            })
        }
    };
}

// store service end


// orderInfo service start

function OrderInfoService() {

    var orderInfoRequest;

    return {

        getItemById: function (itemId, url, event) {

            if (itemId === undefined) {
                return null;
            }
            var that = new OrderInfoService();

            if (orderInfoRequest !== undefined) {
                orderInfoRequest.abort();
            }

            var spinner = startLoading();

            orderInfoRequest = $.ajax({
                type: "GET",
                url: url,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: {itemId: itemId},
                dataType: 'json',
                timeout: 30000,
                tryCount: 0,
                retryLimit: 3,
                beforeSend: function () {
                    if (itemId === undefined) {
                        return null;
                    }
                },
                success: function (data) {

                    var result = data.detail;

                    var msg = data.message;

                    if (data.status === 'Success') {

                        that.setItemResult(result, event);
                        calculate(amountUpdate);

                        stopLoading(spinner);

                    } else {

                        alert(msg);
                    }
                },

                error: function (xhr, textStatus, errorThrown) {

                    console.log(xhr + " " + textStatus + " " + errorThrown);

                    if (textStatus == 'timeout') {

                        this.tryCount++;

                        if (this.tryCount <= this.retryLimit) {
                            //try again
                            $.ajax(this);
                            return;
                        } else {
                            //cancel request
                            that.cancelRequest(spinner);

                            return;
                        }

                    }

                    if (xhr.status == 500) {
                        //handle error
                    } else {
                        //handle error
                    }
                }
            });
        },

        cancelRequest: function (spinner) {
            stopLoading(spinner);
            if (orderInfoRequest !== undefined) {
                orderInfoRequest.abort();
            }

        },

        setItemResult: function (result, event) {
            $(event).parents("tr").find("td:eq(2)").find("input").val("").val(result.sellingPrice.toFixed(3));
            $(event).parents("tr").find("td:eq(1)").find("input").attr("max", "").attr("max", result.inStock);
        },


        changeSaleTrack: function (url, track, orderId) {

            var that = new OrderInfoService();

            if (orderInfoRequest !== undefined) {
                orderInfoRequest.abort();
            }

            var spinner = startLoading();

            orderInfoRequest = $.ajax({
                type: "GET",
                url: url,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: {
                    orderId: orderId,
                    track: track
                },
                dataType: 'json',
                timeout: 30000,
                tryCount: 0,
                retryLimit: 3,
                success: function (data) {

                    var result = data.detail;

                    var msg = data.message;

                    if (data.status === 'Success') {

                        window.location.reload();
                        stopLoading(spinner);


                    } else {
                        stopLoading(spinner);
                        alert(msg);
                        window.location.reload();
                    }
                },

                error: function (xhr, textStatus, errorThrown) {

                    console.log(xhr + " " + textStatus + " " + errorThrown);

                    if (textStatus == 'timeout') {

                        this.tryCount++;

                        if (this.tryCount <= this.retryLimit) {
                            //try again
                            $.ajax(this);
                            return;
                        } else {
                            //cancel request
                            that.cancelRequest(spinner);

                            return;
                        }

                    }

                    if (xhr.status === 500) {
                        stopLoading(spinner);
                        alert("internal server error cantact for support");
                    } else if (xhr.status === 404) {
                        //handle error
                        stopLoading(spinner);
                        alert("internal server error cantact for support");
                    } else {
                        //handle error
                        stopLoading(spinner);
                        alert("internal server error cantact for support");
                    }
                }
            });
        }
    };

}

// orderInfo service end

//tag service start

function TagService() {
    var tagRequest;

    return {
        save: function (name, code, url) {
            var self = new TagService();
            if (self.validation(name , code)){
                tagRequest = $.ajax({
                    type: "POST",
                    url: url,
                    contentType: "application/x-www-form-urlencoded;charset=utf-8",
                    data: {"name" : name , "code" : code},
                    dataType: 'json',
                    timeout: 100000,
                    beforeSend: function () {
                        if (tagRequest !== undefined) {
                            tagRequest.abort();
                        }

                        $(".addNewTag").prop( "disabled", true );
                        blockUiZ(2001);

                    },
                    success: function (data) {

                        $.unblockUI();
                        $(".addNewTag").prop( "disabled", false );
                        var result = data.detail;

                        var msg = data.message;

                        if (data.status === 'Success') {

                            $(".closeAdd").click();
                            //that.setDataToDOM(result, pagecontext);
                            self.successMsg(msg);

                        }

                        if (data.status === 'Failure') {
                            self.errorMsg(msg);
                            $(".closeAdd").click();
                        }

                        if (data.status === 'Validation Failed') {
                            self.setError(result.name , result.code);
                        }
                    },

                    error: function (xhr, textStatus, errorThrown) {

                        console.log(xhr + " " + textStatus + " " + errorThrown);

                        if (textStatus == 'timeout') {

                            this.tryCount++;

                            if (this.tryCount <= this.retryLimit) {
                                //try again
                                if (tagRequest !== undefined){
                                    tagRequest.abort();
                                }
                                $.unblockUI();
                                self.errorMsg("Poor connection. Tag may be saved. Please check you internet");
                                return;
                            } else {
                                //cancel request
                                if (tagRequest !== undefined){
                                    tagRequest.abort();
                                }
                                self.errorMsg("Poor connection. Tag may be saved. Please check you internet");
                                $.unblockUI();

                                return;
                            }

                        }

                        if (xhr.status === 500) {
                            $(".addNewTag").prop( "disabled", false );
                            $(".closeAdd").click();
                            $.unblockUI();
                            self.errorMsg("internal server error cantact for support");
                        } else if (xhr.status === 404) {
                            $(".addNewTag").prop( "disabled", false );
                            //handle error
                            $(".closeAdd").click();
                            $.unblockUI();
                            self.errorMsg("internal server error cantact for support");
                        } else {
                            //handle error
                            $(".addNewTag").prop( "disabled", false );
                            $(".closeAdd").click();
                            $.unblockUI();
                            self.errorMsg("internal server error cantact for support");
                        }
                    }
                });
            }

        },

        errorMsg: function (msg) {
            $.notify({
                title: '<strong>warnning!</strong>',
                message: msg
            }, {
                type: 'danger'
            });
        },

        successMsg: function (msg) {
            successNotify(msg)
        },

        validation: function (name, code) {
            var self = new TagService();

            if (name === undefined) {
                self.setError("please enter tag name", "");
                return false;
            } else if (name === null) {
                self.setError("please enter tag name", "");
                return false;
            } else if (name.trim() === "") {
                self.setError("please enter tag name", "");
                return false;
            } else {
                if (code === undefined) {
                    self.setError("", "please enter tag code");
                    return false;
                } else if (code === null) {
                    self.setError("", "please enter tag code");
                    return false;
                } else if (code.trim() === "") {
                    self.setError("", "please enter tag code");
                    return false;
                }
            }

            return true;
        },

        setError: function (nameError, codeError) {
            $("#tagNameError").text(nameError);
            $("#tagCodeError").text(codeError);
        },

        clearInputFormData: function () {
            $(".addTagFormClear").val("");
        },

        clearErrorData: function () {
            $(".tagFormError").text("");
        }
    }
}

//tag service end

//Vendor service start

function VendorService() {
    var supplierRequest;

    return {
        save: function (companyName , name, contact , mobile , email , city , street, url) {
            var self = new VendorService();
            if (self.validation(companyName)){
                supplierRequest = $.ajax({
                    type: "POST",
                    url: url,
                    contentType: "application/x-www-form-urlencoded;charset=utf-8",
                    data: {
                        "companyName" : companyName ,
                        "name" : name,
                        "contact" : contact ,
                        "mobileNumber" : mobile ,
                        "email" : email ,
                        "cityId" : city ,
                        "street" : street
                    },
                    dataType: 'json',
                    timeout: 100000,
                    beforeSend: function () {
                        if (supplierRequest !== undefined) {
                            supplierRequest.abort();
                        }

                        $(".addNewSupplier").prop( "disabled", true );
                        blockUiZ(2001);

                    },
                    success: function (data) {

                        $.unblockUI();
                        $(".addNewSupplier").prop( "disabled", false );
                        var result = data.detail;

                        var msg = data.message;

                        if (data.status === 'Success') {

                            $(".closeSupplierAdd").click();
                            self.successMsg(msg);

                        }

                        if (data.status === 'Failure') {
                            self.errorMsg(msg);
                            $(".closeAdd").click();
                        }

                        if (data.status === 'Validation Failed') {
                            self.setError(result.companyName , result.name , result.contact, result.mobileNumber , result.email , result.cityId, result.street);
                        }
                    },

                    error: function (xhr, textStatus, errorThrown) {

                        console.log(xhr + " " + textStatus + " " + errorThrown);

                        if (textStatus == 'timeout') {

                            this.tryCount++;

                            if (this.tryCount <= this.retryLimit) {
                                //try again
                                self.errorMsg("Poor connection. Supplier may be saved. Please check you internet.");
                                $.unblockUI();
                                return;
                            } else {
                                //cancel request
                                if (tagRequest !== undefined){
                                    tagRequest.abort();
                                }
                                self.errorMsg("Poor connection. Supplier may be saved. Please check you internet.");
                                $.unblockUI();

                                return;
                            }

                        }

                        if (xhr.status === 500) {
                            $(".addNewSupplier").prop( "disabled", false );
                            $(".closeSupplierAdd").click();
                            $.unblockUI();
                            self.errorMsg("internal server error cantact for support");
                        } else if (xhr.status === 404) {
                            $(".addNewSupplier").prop( "disabled", false );
                            //handle error
                            $(".closeSupplierAdd").click();
                            $.unblockUI();
                            self.errorMsg("internal server error cantact for support");
                        } else {
                            //handle error
                            $(".addNewSupplier").prop( "disabled", false );
                            $(".closeSupplierAdd").click();
                            $.unblockUI();
                            self.errorMsg("internal server error cantact for support");
                        }
                    }
                });
            }

        },

        errorMsg: function (msg) {
            $.notify({
                title: '<strong>warnning!</strong>',
                message: msg
            }, {
                type: 'danger'
            });
        },

        successMsg: function (msg) {
            successNotify(msg)
        },

        validation: function (companyName) {
            var self = new VendorService();

            if (companyName === undefined) {
                self.setError("please enter company name", "" , "" , "" , "" , "" , "");
                return false;
            } else if (companyName === null) {
                self.setError("please enter company name", "" , "" , "" , "" , "" , "");
                return false;
            } else if (companyName.trim() === "") {
                self.setError("please enter company name", "" , "" , "" , "" , "" , "");
                return false;
            }

            return true;
        },

        setError: function (companyNameError , nameError, contactError , mobileError , emailError , cityError , streetError) {
            $(".supplierCompanyNameError").text(companyNameError);
            $(".supplierNameError").text(nameError);
            $(".supplierContactError").text(contactError);
            $(".supplierMobileError").text(mobileError);
            $(".supplierEmailError").text(emailError);
            $(".supplierCityError").text(cityError);
            $(".supplierStreetError").text(streetError);
        },

        clearInputFormData: function () {
            $(".addSupplierFormClear").val("");
        },

        clearErrorData: function () {
            $(".supplierFormError").text("");
        }
    }
}

//Vendor service end

//clientInfo service start

function ClientInfoService() {

    var clientInfoRequest;

    return {

        list: [],

        save: function (client, url, pagecontext) {

            var that = new ClientInfoService();

            console.log(client.pan);

            if (clientInfoRequest !== undefined) {
                clientInfoRequest.abort();
            }

            clientInfoRequest = $.ajax({
                type: "POST",
                url: url,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: client,
                dataType: 'json',
                timeout: 100000,
                success: function (data) {

                    var result = data.detail;

                    var msg = data.message;

                    if (data.status === 'Success') {

                        //that.setDataToDOM(result, pagecontext);
                        that.successMsg(msg);
                        $(".closeAdd").click();

                    }

                    if (data.status === 'Failure') {
                        that.errorMsg(msg);
                        $(".closeAdd").click();
                    }

                    if (data.status === 'Validation Failed') {

                        that.errorOnForm(msg);
                        that.setError(result);
                    }
                }
            });

        },

        getById: function (url) {

            var that = new ClientInfoService();

            that.clearLoadData();

            if (clientInfoRequest !== undefined) {
                clientInfoRequest.abort();
            }

            clientInfoRequest = $.ajax({
                type: "GET",
                url: url,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                dataType: 'json',
                timeout: 100000,
                success: function (data) {

                    var result = data.detail;

                    var msg = data.message;

                    if (data.status === 'Success') {

                        that.loadData(result);
                    }

                    if (data.status === 'Failure') {
                        that.errorMsg(msg);
                        $(".closeShow").click();
                    }
                }
            });
        },

        loadData: function (data) {

            $(".name").text("").text(data.name);
            $(".companyName").text("").text(data.companyName);
            $(".contact").text("").text(data.contact);
            $(".mobileNumber").text("").text(data.mobileNumber);
            $(".email").text("").text(data.email);
            $(".street").text("").text(data.street);
            $(".pan").text("").text(data.pan);

        },

        clearLoadData: function () {
            $(".name").text("");
            $(".companyName").text("");
            $(".contact").text("");
            $(".mobileNumber").text("");
            $(".email").text("");
            $(".street").text("");
            $(".pan").text("");

            $("#nameEdit").val("");
            $("#companyNameEdit").val("");
            $("#contactEdit").val("");
            $("#mobileNumberEdit").val("");
            $("#emailEdit").val("");
            $("#streetEdit").val("");
            $("#pan").val("");
        },

        setError: function (error) {
            $(".name").text(error.name);
            $(".contact").text(error.contact);
            $(".email").text(error.email);
            $(".companyName").text(error.companyName);
            $(".mobileNumber").text(error.mobileNumber);
            $(".street").text(error.street);
            $(".cityId").text(error.cityId);

        },

        clearError: function () {
            $(".name").text("");
            $(".contact").text("");
            $(".email").text("");
            $(".companyName").text("");
            $(".mobileNumber").text("");
            $(".street").text("");
            $(".cityId").text("");
            $(".errorModel").text("");
            $(".addError").removeClass("hidden").removeClass("show").addClass("hidden");
        },

        clearForm: function () {
            $("#name").val("");
            $("#contact").val("");
            $("#email").val("");
            $("#mobileNumber").val("");
            $("#street").val("");
            $("#companyName").val("");
            $("#pan").val("");
            /*$("#cityId").select2("val", "");*/
        },

        successMsg: function (msg) {
            successNotify(msg)
        },

        errorMsg: function (msg) {
            dangerNotify(msg)
        },

        errorOnForm: function (error) {
            $(".addError").removeClass("hidden").removeClass("show").addClass("show");
            $(".errorModel").text(error);
        },

        setDataToDOM: function (data, pagecontext) {

        }
    };
}

//clientInfo service end