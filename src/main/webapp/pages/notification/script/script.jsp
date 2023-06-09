
<script>


function countNotification() {
    $.get("${pageContext.request.contextPath}/notification/count",function(data, status){
        console.log("counted successfully." + status);

        if(status === "success"){
            setCount(data);
        }

    });
}

function setCount(data) {
    $(".countNotificationHeader").text(data.message);

    $(".countNotification").text("You have " + data.message + " notifications");

    $.each(data.detail , function (i, v) {
        var row = "<li>";

        if(v.url !== undefined){
            if(v.url !== null){
                row += "<a href='${pageContext.request.contextPath}" +v.url+ "'>";
            }else{
                row += "<a href='#'>";
            }

        } else {
            row += "<a href='#'>";
        }


        row += "" + v.body;

        row += " </a></li>";

        $(".notification").prepend(row);
    })
}

function updateCount(count){

    var oldCount = $(".countNotificationHeader").text();

    var newCount = parseInt(count) + parseInt(oldCount);

    $(".countNotificationHeader").text(newCount);


    $(".countNotification").text("You have " + newCount + " notifications");
}

$(document).ready( function () {
    countNotification();// get total notification count
});

</script>

<%--
<script src="${pageContext.request.contextPath}/resources/js/firebase/firebase-app.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/firebase/firebase-messaging4.6.2.js"></script>


<script>
    // Initialize Firebase
    var config = {
        apiKey: "AIzaSyCcz5mGnYYZWBVcK3eO7MtwH0M5i1VbTxA",
        authDomain: "inventory-43117.firebaseapp.com",
        databaseURL: "https://inventory-43117.firebaseio.com",
        projectId: "inventory-43117",
        storageBucket: "inventory-43117.appspot.com",
        messagingSenderId: "118536954776"
    };
    firebase.initializeApp(config);


</script>





<script>


$(document).ready( function () {
    count();// get total notification count

    // Retrieve Firebase Messaging object.
    const messaging = firebase.messaging();

    console.log(messaging);

    navigator.serviceWorker.register('${pageContext.request.contextPath}/resources/firebase-messaging-sw.js')
        .then(function (registration){
            messaging.useServiceWorker(registration);

            messaging.requestPermission()
                .then(function() {
                    console.log('Notification permission granted.');

                    // Request For Instance ID token.
                    messaging.getToken()
                        .then(function(currentToken) {
                            if (currentToken) {
                                $.post("${pageContext.request.contextPath}/notification/updateToken",{token: currentToken}, function(data, status){
                                    console.log("Token updated successfully.");
                                });
                                console.log('Token:' + currentToken);
                            } else {
                                console.log('No Instance ID token available. Request permission to generate one.');
                            }
                        })
                        .catch(function(err) {
                            console.log('An error occurred while retrieving token. ', err);
                        });
                })
                .catch(function(err) {
                    console.log('Unable to get permission to notify.', err);
                });
        });

    // Request For Notification Permission


    // Callback fired if Instance ID token is updated.
    messaging.onTokenRefresh(function() {
        messaging.getToken()
            .then(function(refreshedToken) {
                console.log('Refreshed Token :' + refreshedToken);
                $.post("${pageContext.request.contextPath}/notification/updateToken",{token: currentToken}, function(data, status){
                    console.log("Token updated successfully.");
                });
            })
            .catch(function(err) {
                console.log('Unable to retrieve refreshed token ', err);
                showToken('Unable to retrieve refreshed token ', err);
            });
    });

    messaging.onMessage(function(payload) {
        console.log("Message received. ", payload + " hdj : " + payload.notification.body);
        setNotification(payload);
    });


});--%>


    function setNotification(payload) {

        console.log(payload);

        var body = payload.notification.body;

        var url = payload.data.url;

        var row = "<li>";

        if(url !== undefined){
            if(url !== null){
                row += "<a href='${pageContext.request.contextPath}" +url+ "'>";
            }else {
                row += "<a href='#'>";
            }
        }else {
            row += "<a href='#'>";
        }


        row += "" + body;

        row += " </a></li>";

        $(".notification").prepend(row);

        updateCount(1);
    }

    function count() {
        $.get("${pageContext.request.contextPath}/notification/count",function(data, status){
            console.log("counted successfully." + status);

            if(status === "success"){
                setCount(data);
            }

        });
    }

    function setCount(data) {
        $(".countNotificationHeader").text(data.message);

        $(".countNotification").text("You have " + data.message + " notifications");

        $.each(data.detail , function (i, v) {
            var row = "<li>";

            if(v.url !== undefined){
                if(v.url !== null){
                    row += "<a href='${pageContext.request.contextPath}" +v.url+ "'>";
                }else{
                    row += "<a href='#'>";
                }

            } else {
                row += "<a href='#'>";
            }


            row += "" + v.body;

            row += " </a></li>";

            $(".notification").prepend(row);
        })
    }

    function updateCount(count){

        var oldCount = $(".countNotificationHeader").text();

        var newCount = parseInt(count) + parseInt(oldCount);

        $(".countNotificationHeader").text(newCount);


        $(".countNotification").text("You have " + newCount + " notifications");
}


</script>

