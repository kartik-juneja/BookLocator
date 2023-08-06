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

  var title,author,barcode,department,count,rack,shelf;

  // Reference to Book shelf db
  var database = firebase.database();

  // Listen for form submit
    document.getElementById('submitBook').addEventListener('submit',submitForm);

    //Submit form
function submitForm(e){
    e.preventDefault();

    //get values
    title = getInputVal('bookTitle');
    author = getInputVal('bookAuthor');
    barcode = getInputVal('barcode');
   
    department = getInputVal('department');
    count = getInputVal('count');
    rack = getInputVal('rack')
    shelf = getInputVal('shelf');

    //Save admin data
    addBook(title,author,barcode,department,count,rack,shelf);
    console.log("Added Book");

    //Clear form
    document.getElementById('submitBook').reset();

    //reload
    window.location.reload();
}


//function to get fields values
function getInputVal(id){
    return document.getElementById(id).value;
}

//Function to add book
function addBook(title,author,barcode,department,count,rack,shelf){
database.ref('BookShelf/' + title).set({
      titleBook : title,
      authorBook : author,
      barcode : barcode,
      department : department,
      No_of_books : count,
      rackNo : rack,
      ShelfNo : shelf
      
  });
}
