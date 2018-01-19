<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/19/18
  Time: 8:05 PM
  To change this template use File | Settings | File Templates.
--%>
<!-- jQuery 3 -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
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



    // Retrieve Firebase Messaging object.
    const messaging = firebase.messaging();

    console.log(messaging);

    navigator.serviceWorker.register('/resources/firebase-messaging-sw.js')
        .then(function (registration){
            messaging.useServiceWorker(registration);

            messaging.requestPermission()
                .then(function() {
                    console.log('Notification permission granted.');

                    // Request For Instance ID token.
                    messaging.getToken()
                        .then(function(currentToken) {
                            if (currentToken) {
                                $.post("/notification/updateToken",{token: currentToken}, function(data, status){
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
                $.post("/notification/updateToken",{token: currentToken}, function(data, status){
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


    function setNotification(payload) {

        var body = payload.notification.body;

        var row = "<li>";

        row += "<a href='#'>";

        row += "" + body;

        row += " </a></li>";

        $(".notification").prepend(row);
    }

</script>

