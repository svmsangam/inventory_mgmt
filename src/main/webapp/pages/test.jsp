<%--
  Created by IntelliJ IDEA.
  User: dhiraj
  Date: 1/18/18
  Time: 9:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>fire base</title>
    <!-- jQuery 3 -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>

</head>
<body>
<h1>fire base test</h1>
<script src="https://www.gstatic.com/firebasejs/4.8.2/firebase.js"></script>
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

    // Retrieve Firebase Messaging object.
    const messaging = firebase.messaging();

    // Request For Notification Permission
    messaging.requestPermission()
        .then(function() {
            console.log('Notification permission granted.');

            // Request For Instance ID token.
            messaging.getToken()
                .then(function(currentToken) {
                    if (currentToken) {
                        $.post("/setwebtoken",{token: currentToken}, function(data, status){
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

    // Callback fired if Instance ID token is updated.
    messaging.onTokenRefresh(function() {
        messaging.getToken()
            .then(function(refreshedToken) {
                console.log('Refreshed Token :' + refreshedToken);
                $.post("/setwebtoken",{token: currentToken}, function(data, status){
                    console.log("Token updated successfully.");
                });
            })
            .catch(function(err) {
                console.log('Unable to retrieve refreshed token ', err);
                showToken('Unable to retrieve refreshed token ', err);
            });
    });

    messaging.onMessage(function(payload) {
        console.log("Message received. ", payload);
        angular.element('#tmkmobilebankingController').scope().getNotification();
    });

</script>
</body>
</html>
