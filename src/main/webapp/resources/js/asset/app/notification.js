/**
 * Created by dhiraj on 1/21/18.
 */
var socket = null;
var stompClient = null;

function connect(secretKey) {
    socket = new SockJS("/webSocket");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {

        console.log('Connected: ' + frame + " : " + secretKey);
        stompClient.subscribe('/topic/notification/'+secretKey, function(messageOutput) {

            console.log(JSON.parse(messageOutput.body));
        });

    });

}
