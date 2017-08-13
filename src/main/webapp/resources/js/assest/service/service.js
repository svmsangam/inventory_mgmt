/**
 * Created by dhiraj on 8/12/17.
 */

// user service start

function UserService(){
    var userRequest;

    return {

        list : [],

        save : function(user , url){

            var that = new UserService();

            if(userRequest !== undefined){
                userRequest.abort();
            }

            userRequest = $.ajax({
                type: "POST",
                url: url,
                contentType : "application/x-www-form-urlencoded;charset=utf-8",
                data : user,
                dataType : 'json',
                timeout : 100000,
                success: function(data) {

                    var result = data.detail;

                    var msg = data.message;

                    if(data.status === 'Success'){

                        that.setDataToDOM(result);
                        that.successMsg(msg);
                        $(".closeAdd").click();
                    }

                    if (data.status === 'Failure'){
                        that.errorMsg(msg);
                    }

                    if(data.status === 'Validation Failed'){

                        that.errorOnForm(msg);
                        that.setError(result);
                    }
                }
            });

        },

        setError : function (error) {
            $(".inventoryuser").text(error.username);
            $(".userpassword").text(error.password);
            $(".userrepassword").text(error.repassword);
            $(".userType").text(error.userType);
        },

        clearError : function () {
            $(".inventoryuser").text("");
            $(".userpassword").text("");
            $(".userrepassword").text("");
            $(".userType").text("");
            $(".errorModel").text("");
            $(".addError").removeClass("hide").removeClass("show").addClass("hide");
        },

        clearForm : function () {
            $("#inventoryuser").val("");
            $("#userpassword").val("");
            $("#userrepassword").val("");
            /*$("#userType").val("");*/
        },

        successMsg : function (msg) {
            $.notify({
                icon: 'glyphicon glyphicon-ok',
                title: '<strong>Success!</strong>',
                message: msg
            });
        },

        errorMsg : function (msg) {
            $.notify({
                title: '<strong>warnning!</strong>',
                message: msg
            },{
                type: 'danger'
            });
        },

        errorOnForm: function (error) {
            $(".addError").removeClass("hide").removeClass("show").addClass("show");
            $(".errorModel").text(error);
        },

        setDataToDOM : function (data) {
            var row = "<tr>";
            row += "<td>new</td>";
            row += "<td>"+data.inventoryuser+"</td>";
            row += "<td>"+data.userType+"</td>";
            row += "</tr>";

            $("#myData").prepend(row);
        }
    };
}

// user service end
