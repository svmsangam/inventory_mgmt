/**
 * Created by dhiraj on 8/12/17.
 */

// user service start

function UserService(){
    var userRequest;

    return {

        list : [],

        save : function(user , url , pagecontext){

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

                        that.setDataToDOM(result , pagecontext);
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

        setDataToDOM : function (data , pagecontext) {
            var row = "<tr>";
            row += "<td>0</td>";
            row += "<td>"+data.inventoryuser+"</td>";
            row += "<td>"+data.userType+"</td>";
            row += "<td>";
            if(data.enable === true){
                row += "<span class='label label-success'>Activated</span>";
                if(data.userType === "USER"){
                    row += "<a href='"+pagecontext+"/user/manage?userId="+data.userId+"'><span class='label label-primary label-manage'>Manage</span></a>";
                }
            }else {
                row += "<span class='label label-danger'>Deactivated</span>";
            }

            row += "</td>";

            row += "<td>";
            if(data.enable === false){

                row += "<a href='"+pagecontext+"/user/updateenable?userId="+data.userId+"' onclick='return confirm('Are you sure you want to Activate?')'><span class='label label-success'>Activate ?</span></a>";

            }else {
                row += "<a href='"+pagecontext+"/user/updateenable?userId="+data.userId+"' onclick='return confirm('Are you sure you want to Deactivate?')'><span class='label label-danger'>Deactivate ?</span></a>";
            }

            row += "</td>";
            row += "</tr>";

            $("#myData").prepend(row);

            $("tr").each(function(index) {
                $(this).find("td:eq(0)").text(index);
            })
        }
    };
}

// user service end
