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



document.getElementById('issueBook').addEventListener('submit',getBookData);



function getBookData(e){
    e.preventDefault();
    firebase.database().ref('BookShelf').on('value',
    function(AllRecords){
        AllRecords.forEach(
            function(CurrentRecord){
                var barcode = CurrentRecord.val().barcode;
                var title = CurrentRecord.val().titleBook;
                var author = CurrentRecord.val().authorBook;
                if(barcode==getInputVal('issuebarcode') || title == getInputVal("issuebookName")){
                    addItemsToIssueTable(barcode,title,author);
                    console.log("Book found" + title);
                }
            }
        )
    }
    )
}

function addItemsToIssueTable(barcode,title,author){
      var tbody = document.getElementById('issueBody');
      var trow = document.createElement('tr');
      var td1 = document.createElement('td');
      var td2 = document.createElement('td');
      var td3 = document.createElement('td');
      

      td1.innerHTML = barcode;
      td2.innerHTML = title;
      td3.innerHTML = author;
      

      trow.appendChild(td1);
      trow.appendChild(td2);
      trow.appendChild(td3);
      

      tbody.appendChild(trow);

      //call the function to set the event to the new row 
      selectRow();
      



  }

  
  var rowIndex,table = document.getElementById("issueTable");
  function selectRow(){
     
    for(var i = 1; i < table.rows.length; i++)
      {
          table.rows[i].onclick = function(){
              
          }
      }

      //show the modal
      //when table field is clicked
      document.getElementById('issueTable').addEventListener('click',function(){
          document.querySelector('#issuemodal').style.display = 'flex';
      });

  }  



  function getInputVal(id){
    return document.getElementById(id).value;
}