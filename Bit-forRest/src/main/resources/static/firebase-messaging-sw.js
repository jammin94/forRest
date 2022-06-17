importScripts('https://www.gstatic.com/firebasejs/8.6.5/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.6.5/firebase-messaging.js');



// Initialize the Firebase app in the service worker by passing in
// your app's Firebase config object.
// https://firebase.google.com/docs/web/setup#config-object
var config = {
    		    apiKey: "AIzaSyAhJBes0hcVA79lL_GRWETlB_tJb37eWQk",
    		    authDomain: "bit-project-runrunfunfun.firebaseapp.com",
    		    databaseURL: 'https://bit-project-runrunfunfun.firebaseapp.com',
    		    projectId: "bit-project-runrunfunfun",
    		    storageBucket: "bit-project-runrunfunfun.appspot.com",
    		    messagingSenderId: "983824607774",
    		    appId: "1:983824607774:web:839fd7b2b2c2f37f555c0f",
    		    measurementId: "G-DF7YL14NV8"
    		  };


firebase.initializeApp(config);

// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.

  const messaging = firebase.messaging();

  // [START messaging_on_background_message]
  	messaging.onBackgroundMessage((payload) => {
    console.log('[firebase-messaging-sw.js] Received background message ', payload);
    // Customize notification here
    const notificationTitle = 'Background Message Title';
    const notificationOptions = {
      body: 'forrest에서 새로운 알림이 도착했습니다',
      icon: '/images/header/bearlogo.png'
    };
  
    self.registration.showNotification(notificationTitle,
      notificationOptions);
  });
