/**
 * Created by dhiraj on 8/12/17.
 */

// user service start

function UserService(){
    var userRequest;

    return {

        list : [],

        save : function(user , url){

            if(userRequest !== undefined){
                userRequest.abort();
            }

            userRequest = $.ajax({
                type: "POST",
                url: url,
                contentType : "application/json",
                data : {inventoryuser : user.inventoryuser,
                        userpassword : user.userpassword,
                        userrepassword : user.userrepassword,
                        userType:user.userType
                },
                dataType : 'json',
                timeout : 100000,
                success: function(data) {
                    if(data.status === 'Success'){

                        window.location.reload();
                    }

                    if (data.status === 'Failure'){
                        alert(data.message);
                    }

                    if(data.status === 'Validation Failed'){
                        alert(data.message);
                    }
                }
            });

        }
    };
}

// user service end
