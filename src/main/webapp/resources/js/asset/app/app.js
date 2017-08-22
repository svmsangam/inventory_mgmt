/**
 * Created by dhiraj on 8/12/17.
 */

//user app start
var userService = new UserService();

$(document).ready(function () {

    $(document).on("click", "#saveuser", function () {

        var user = setUserData();

        var url = $(this).attr("url");

        var pagecontext = $(this).attr("pagecontext");

        userService.save(user, url, pagecontext);
    });


    $(document).on("click", ".addUser", function () {

        userService.clearError();
        userService.clearForm();
    });

    $(document).on("click", ".closeError", function () {
        $(".addError").removeClass("hide").removeClass("show").addClass("hide");
    });
});


function setUserData() {

    var username = $("#inventoryuser").val();
    var password = $("#userpassword").val();
    var repassword = $("#userrepassword").val();
    var userType = $("#userType").val();
    var storeId = $("#storeId").val();

    if (username === undefined) {
        username = "";
    }

    if (password === undefined) {
        password = "";
    }

    if (repassword === undefined) {
        repassword = ""
    }

    if (userType === undefined) {
        userType = "";
    }

    if (storeId === undefined) {
        storeId = 0;
    }

    var user = new User();

    user.inventoryuser = username;
    user.userpassword = password;
    user.userrepassword = repassword;
    user.userType = userType;
    user.storeId = storeId;

    console.log("username : " + username + " pass : " + password + " repass : " + repassword + " userType : " + userType);

    return user;
}
//user app end


//store app start
var storeService = new StoreService();

$(document).ready(function () {

    $(document).on("click", ".savestore", function () {

        var store = setStoreData();

        var url = $(this).attr("url");

        var pagecontext = $(this).attr("pagecontext");

        storeService.save(store, url, pagecontext, true);
    });

    $(document).on("click", ".edit", function () {
        userService.clearError();
        userService.clearForm();
    });

    $(document).on("click", ".updatestore", function () {

        var store = setStoreDataOnUpdate();

        var url = $(this).attr("url");

        storeService.save(store, url, "nn", false);
    });

    $(document).on("click", ".addStore", function () {

        storeService.clearError();
        storeService.clearForm();
    });

    $(document).on("click", ".viewStoreInfo", function () {

        var url = $(this).attr("url");

        console.log("url " + url);

        storeService.getById(url);
    });

    $(document).on("click", ".closeError", function () {
        $(".addError").removeClass("hide").removeClass("show").addClass("hide");
    });
});


function setStoreData() {

    var name = $("#name").val();
    var contact = $("#contact").val();
    var email = $("#email").val();
    var mobile = $("#mobile").val();
    var street = $("#street").val();
    var reg = $("#reg").val();
    var pan = $("#pan").val();
    var cityId = $("#cityId").val();

    if (name === undefined) {
        name = "";
    }

    if (contact === undefined) {
        contact = "";
    }

    if (mobile === undefined) {
        mobile = ""
    }

    if (email === undefined) {
        email = "";
    }

    if (street === undefined) {
        street = "";
    }

    if (reg === undefined) {
        reg = "";
    }

    if (cityId === undefined) {
        cityId = 0
    }

    if (pan === undefined) {
        pan = "";
    }

    var store = new Store();

    store.name = name;
    store.contact = contact;
    store.mobileNumber = mobile;
    store.email = email;
    store.street = street;
    store.regNumber = reg;
    store.panNumber = pan;
    store.cityId = cityId;

    return store;
}


function setStoreDataOnUpdate() {

    var storeId = $("#storeIdEdit").attr("storeId");
    var contact = $("#contactEdit").val();
    var email = $("#emailEdit").val();
    var mobile = $("#mobileEdit").val();
    var street = $("#streetEdit").val();
    var reg = $("#regEdit").val();
    var pan = $("#panEdit").val();

    if (storeId === undefined) {
        storeId = 0;
    }

    if (contact === undefined) {
        contact = "";
    }

    if (mobile === undefined) {
        mobile = ""
    }

    if (email === undefined) {
        email = "";
    }

    if (street === undefined) {
        street = "";
    }

    if (reg === undefined) {
        reg = "";
    }

    if (pan === undefined) {
        pan = "";
    }

    var store = new Store();

    store.storeId = storeId;
    store.contact = contact;
    store.mobileNumber = mobile;
    store.email = email;
    store.street = street;
    store.regNumber = reg;
    store.panNumber = pan;

    return store;
}
//store app end

