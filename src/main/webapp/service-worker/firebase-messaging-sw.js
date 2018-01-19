/**
 * Created by dhiraj on 1/19/18.
 */

importScripts('https://www.gstatic.com/firebasejs/3.9.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/3.9.0/firebase-messaging.js');

var config = {
    messagingSenderId : "118536954776"
};
firebase.initializeApp(config);

const messaging = firebase.messaging();

self.addEventListener('notificationclick', function(event) {
    event.notification.close();
    return clients.openWindow('/notification');
});

messaging.setBackgroundMessageHandler(function(payload) {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
    // Customize notification here
    const notificationTitle = payload.data.title;
    const notificationOptions = {
        body: payload.data.body,
        icon: 'http://139.59.30.219:8080/resources/img/inventory-logo.png'
    };

    return self.registration.showNotification(notificationTitle,
        notificationOptions);
});

