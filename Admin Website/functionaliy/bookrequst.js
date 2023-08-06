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

function getRequest(){
    firebase.database().ref('StudentBookData/BookData').on('value',
    function(AllRecords){
        AllRecords.forEach(
        function(CurrentRecord){
            var title = CurrentRecord.val().bookTitle;
            var author = CurrentRecord.val().authorName;
            var publisher = CurrentRecord.val().publisher;
            var branch = CurrentRecord.val().branch;
            var semester = CurrentRecord.val().semester;
            addItemsToTable(title,author,publisher,branch,semester);
            console.log("Books" + title + " " + author );
            
        }
       );
    });
}

window.onload = getRequest;

function addItemsToTable(title,author,publisher,branch,semester){
    var tbody = document.getElementById('tbody');
    var trow = document.createElement('tr');
    var td1 = document.createElement('td');
    var td2 = document.createElement('td');
    var td3 = document.createElement('td');
    var td4 = document.createElement('td');
    var td5 = document.createElement('td');

    td1.innerHTML = title;
    td2.innerHTML = author;
    td3.innerHTML = publisher;
    td4.innerHTML = branch;
    td5.innerHTML = semester;

    trow.appendChild(td1);
    trow.appendChild(td2);
    trow.appendChild(td3);
    trow.appendChild(td4);
    trow.appendChild(td5);

    tbody.appendChild(trow);

    // //call the function to set the event to the new row 
    // selectRow();
    
}
