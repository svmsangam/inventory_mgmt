/*
/!**
 * Created by dhiraj on 1/21/18.
 *!/
var socket = null;
var stompClient = null;

function connectToSocket(secretKey) {

   var checker = checkConnection(secretKey);

    console.log("check connection : " + checker);


    if (checker === false) {

        console.log("new connection");

        socket = new SockJS("/webSocket");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {

            console.log('Connected: ' + frame + " : " + secretKey);
            stompClient.subscribe('/topic/notification/' + secretKey, function (messageOutput) {
                stompClient.send("/app/isContactOnline/", {}, JSON.stringify({
                    'checker' : messageOutput.body
                }));
                console.log(messageOutput.body);
                setNotification(messageOutput.body);
                notifyMe(messageOutput.body);
            });

        });

    } else {
        console.log("already connected");
    }

}

function checkConnection(secretKey) {

    if (checkStocket(secretKey) === true){
        return true;
    }else {
        return false;
    }

    /!*if (stompClient === undefined){
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
    }*!/
}

function checkStocket(secretKey) {

    if(typeof(Storage) !== "undefined") {

        var storageChecker = localStorage.getItem(secretKey);

        console.log("local storage : " + storageChecker  );

        if (storageChecker === undefined || storageChecker === null){

            localStorage.setItem(secretKey, true);

            sessionStorage.myKey = secretKey;

            console.log("return : " + false );

            return false;

        } else if (storageChecker === "true"){

            console.log("return 1 : " + true );
            sessionStorage.myKey = secretKey;

            return true;

        }else {
            //sessionStorage.clear();
            sessionStorage.myKey = secretKey;
            console.log("return 2 : " + false );

            localStorage.setItem(secretKey, true);

            console.log("storage initialized : " + localStorage.getItem(secretKey)  );

            return false;
        }
    } else {

        return false;
        //document.getElementById("result").innerHTML = "Sorry, your browser does not support web storage...";
    }
}

//remove key for local storage while close browser

window.onbeforeunload = function (event) {
    var message = 'Important: Please click on \'Save\' button to leave this page.';
    if (typeof event === 'undefined') {
        event = window.event;
    }
    if (event) {
        localStorage.removeItem(sessionStorage.myKey);
        window.onbeforeunload = null;
    }
    window.onbeforeunload = null;
};

$(function () {
    $("a").click(function () {
        window.onbeforeunload = null;
    });
    $(".btn").click(function () {
        window.onbeforeunload = null;
    });
});


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

*/
