/**
 * Created by dhiraj on 8/12/17.
 */

// user service start

function UserService() {
    var userRequest;

    return {

        list: [],

        save: function (user, url, pagecontext) {

            var that = new UserService();

            if (userRequest !== undefined) {
                userRequest.abort();
            }

            userRequest = $.ajax({
                type: "POST",
                url: url,
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                data: user,
                dataType: 'json',
                timeout: 100000,
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
            $("#userType").prop('selectedIndex',0);
            $("#storeId").prop('selectedIndex',0);
        },

        successMsg: function (msg) {
            $.notify({
                icon: 'glyphicon glyphicon-ok',
                title: '<strong>Success!</strong>',
                message: msg
            });
        },

        errorMsg: function (msg) {
            $.notify({
                title: '<strong>warnning!</strong>',
                message: msg
            }, {
                type: 'danger'
            });
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
                row += "<td><a href='" + pagecontext + "/user/manage?userId=" + data.userId + "' class='btn btn-xs bg-purple margin'><i class='fa fa-cogs'></i> Manage</a></td>";
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
            $.notify({
                icon: 'glyphicon glyphicon-ok',
                title: '<strong>Success!</strong>',
                message: msg
            });
        },

        errorMsg: function (msg) {
            $.notify({
                title: '<strong>warnning!</strong>',
                message: msg
            }, {
                type: 'danger'
            });
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
            row += "<td><button type='button' class='btn btn-info btn-sm  btn-flat viewStoreInfo' url='" + pagecontext + "/store/show/" + data.storeId + "' data-toggle='modal' data-target='#modal-view'><span class='glyphicon glyphicon-eye-open'></span>View</button>";
            row += "<button type='button' class='btn btn-warning btn-sm  btn-flat' data-toggle='modal' data-target='#modal-edit'><span class='glyphicon glyphicon-edit'></span>Edit</button></td>";
            row += "</tr>";

            $("#myData").prepend(row);

            $("tbody > tr").each(function (index) {
                $(this).find("td:eq(0)").text(index + 1);
            })
        }
    };
}

// store service end
