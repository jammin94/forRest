const firebaseModule = (function () {
    async function init() {
        // Your web app's Firebase configuration
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', function() {
                navigator.serviceWorker.register('/firebase-messaging-sw.js')
                    .then(registration => {
                        var firebaseConfig = {
				    		    apiKey: "AIzaSyAhJBes0hcVA79lL_GRWETlB_tJb37eWQk",
				    		    authDomain: "bit-project-runrunfunfun.firebaseapp.com",
				    		    databaseURL: 'https://bit-project-runrunfunfun.firebaseapp.com',
				    		    projectId: "bit-project-runrunfunfun",
				    		    storageBucket: "bit-project-runrunfunfun.appspot.com",
				    		    messagingSenderId: "983824607774",
				    		    appId: "1:983824607774:web:839fd7b2b2c2f37f555c0f",
				    		    measurementId: "G-DF7YL14NV8"
				    		  };
                        // Initialize Firebase
                        firebase.initializeApp(firebaseConfig);


                        // Show Notificaiton Dialog
                        const messaging = firebase.messaging();
                        messaging.requestPermission()
                        .then(function() {
						    console.log("Have permission");
					        console.log(messaging.getToken());
                            return messaging.getToken();
                        })
                        .then(async function(token) {
                            await fetch('/token/register', { method: 'post', body: token })
                            messaging.onMessage(payload => {
								console.log('Message received. ', payload);
                                const title = payload.data.title;
                                const options = {
                                    body : payload.data.content
                                }
                                navigator.serviceWorker.ready.then(registration => {
                                    registration.showNotification(title, options);
                                })
                            })
                        })
                        .catch(function(err) {
                            console.log("Error Occured");
                        })
                    })
            })
        }
    }      

    return {
        init: function () {
            init()
        }
    }
})()

firebaseModule.init()