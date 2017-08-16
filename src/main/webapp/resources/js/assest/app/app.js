/**
 * Created by dhiraj on 8/12/17.
 */

//user app start
var userService = new UserService();

$(document).ready(function(){

    $(document).on("click","#saveuser",function() {

        var user = setUserDate();

        var url = $(this).attr("url");

        var pagecontext = $(this).attr("pagecontext");

        userService.save(user , url , pagecontext);
    });

    $(document).on("click",".addUser",function() {

        userService.clearError();
        userService.clearForm();
    });

    $(document).on("click",".closeError",function() {
        $(".addError").removeClass("hide").removeClass("show").addClass("hide");
    });
});


function setUserDate() {

    var username = $("#inventoryuser").val();
    var password = $("#userpassword").val();
    var repassword = $("#userrepassword").val();
    var userType= $("#userType").val();

    if(username === undefined){
        username = "";
    }

    if (password === undefined){
        password = "";
    }

    if (repassword === undefined){
        repassword = ""
    }

    if (userType === undefined){
        userType = "";
    }

    var user = new User();

    user.inventoryuser = username;
    user.userpassword = password;
    user.userrepassword = repassword;
    user.userType = userType;

    console.log("username : " + username + " pass : " + password + " repass : " + repassword + " userType : " + userType);

    return user;
}
//user app end
