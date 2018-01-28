/**
 * Created by dhiraj on 1/21/18.
 */
var socket = null;
var stompClient = null;

function connectToSocket(secretKey) {

    if (checkConnection() === false) {

        console.log("new connection");

        console.log(Cache-Control);

        socket = new SockJS("/webSocket");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {

            console.log('Connected: ' + frame + " : " + secretKey);
            stompClient.subscribe('/topic/notification/' + secretKey, function (messageOutput) {
                /*stompClient.send("/app/isContactOnline/", {}, JSON.stringify({
                    'checker' : messageOutput.body
                }));*/
                console.log(messageOutput.body);
                setNotification(messageOutput.body);
                notifyMe(messageOutput.body);
            });

            localStorage['socket'] = JSON.stringify(stompClient);

        });

    } else {
        console.log("already connected");
    }

}

function checkConnection() {

    if (stompClient === undefined){
        return false;
    } else {

        if (stompClient === null){
            return false;
        }
        else if (stompClient.connected === true) {
            return true;
        } else {
            return false;
        }
    }
}

function sendMessage(secretKey) {
    var text = "ping";
    console.log("sending");
    stompClient.send("/app/isContactOnline/", {}, JSON.stringify({
        'checker' : JSON.parse(greeting.body).content
    }));
}



// request permission on page load
document.addEventListener('DOMContentLoaded', function () {
    if (!Notification) {
        alert('Desktop notifications not available in your browser. Try Chrom.');
        return;
    }

    if (Notification.permission !== "granted")
        Notification.requestPermission();
});

function notifyMe(msg) {
    if (Notification.permission !== "granted") {
        Notification.requestPermission();

    } else {
     var notification = new Notification('Inventory Notification', {
     icon: 'http://localhost:8081/resources/images/logo.png',
     body: msg
     });

     notification.onclick = function () {
     window.open("http://localhost:8081/");
     };

     }

}

function setNotification(msg) {

    console.log(msg);

    var body = msg;

    var url = "http://localhost:8081/";

    var row = "<li>";

    if(url !== undefined){
        if(url !== null){
            row += "<a href='" +url+ "'>";
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

