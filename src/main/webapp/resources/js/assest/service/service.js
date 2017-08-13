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

                        window.location.reload();
                    }

                    if (data.status === 'Failure'){
                        alert(msg);
                    }

                    if(data.status === 'Validation Failed'){

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
        }
    };
}

// user service end
