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


//Reference admin data collection
var data = firebase.database().ref('adminData');


// Listen for form submit
document.getElementById('form').addEventListener('submit',submitForm);

//Submit form
function submitForm(e){
    e.preventDefault();

    //get values
    var fname = getInputVal('firstName');
    var lname = getInputVal('lastName');
    var email = getInputVal('email');
    var pass = getInputVal('password');

    //Save admin data
    saveAdminData(fname,lname,email,pass);

    //Show alert
    document.querySelector('.alert').style.display = 'block';

    //Hide alert 
    setTimeout(function(){
        document.querySelector('.alert').style.display = 'none';
    },2000);

    //Clear form
    document.getElementById('form').reset();
}


//function to get form values
function getInputVal(id){
    return document.getElementById(id).value;
}

//Function to save the admin data 
function saveAdminData(fname,lname,email,pass){
  var newAdmin = data.push();
  newAdmin.set({
      fname: fname,
      lname: lname,
      email: email,
      pass:  pass
  });
}