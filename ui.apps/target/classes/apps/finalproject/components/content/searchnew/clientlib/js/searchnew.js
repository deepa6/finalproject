
$( window ).load(function() {


	serviceServletCall ();

 var table = null;

   $('.submit').click(function() {


  if(table != null){ 

serviceServletCall ();

       }

    var failure = function(err) {
             alert("Unable to retrive data "+err);
   };




serviceServletCall ();
  // location.reload();

  });  // end submit


      function serviceServletCall () {

           var val1=$('#v1').val();


//alert("after test");

  table = $('#example').dataTable( {
      "destroy": true,
  "ajax": {
       "type": "POST",
       "url": "/bin/SearchNewServiceString",
      "contentType": 'application/json; charset=utf-8',
       "data":{"value1" : val1},
 	   "dataSrc": ""

  } ,

 "columns": [
            { "data": "value"},
            { "data": "text"} ]



} ); 
          return true;

  }





} );