//Initialize Firebase
const firebaseConfig = {
  apiKey: "AIzaSyA8DkXNqmFfTcjS5nSmyD3D1BpRX5e_svA",
  authDomain: "book-locator-6798d.firebaseapp.com",
  databaseURL: "https://book-locator-6798d-default-rtdb.firebaseio.com",
  projectId: "book-locator-6798d",
  storageBucket: "book-locator-6798d.appspot.com",
  messagingSenderId: "361858524797",
  appId: "1:361858524797:web:57e74614b432acec093314",
  measurementId: "G-CR3W1FV7SC"
};
  firebase.initializeApp(firebaseConfig);


  const auth = firebase.auth();

  firebase.auth().onAuthStateChanged((user) => {
    if (user) {
      
     //Clear form
     document.getElementById('loginForm').reset();
    }else{
           
    }
  });

 //Sign in with email and password
  function signIn(event){
    event.preventDefault();
    
    const email = document.getElementById('emailSignIn').value;
    const password = document.getElementById('passwordSignIn').value;
    
    firebase.auth().signInWithEmailAndPassword(email,password).catch(function (error) {
        console.log("Error signing in!", error.message);
        alert(error.message);
    }).then(function (user) {
       if(user){
         window.location.href="adminPage.html";
         alert("You are now logged in!");
       }  
    });
  }


  // prevent going back 
  function preventBack(){
    window.history.forward();
  }
   preventBack();


 