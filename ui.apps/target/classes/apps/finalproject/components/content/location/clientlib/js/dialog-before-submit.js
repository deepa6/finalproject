(function ($, $document) {
    "use strict";

$document.on("dialog-success", function() {
        $(window).adaptTo("foundation-ui").alert("Save", "Dialog content saved, event [dialog-success]");



       alert("latitude"+$form.find("[name='./jcr:latitude']").val());

       alert("longitude"+$form.find("[name='./jcr:longitude']").val());


    //alert( rad + "~~" + add + "~~~" + lat +"~~~" + lon);

    });


    $document.on("dialog-closed", function() {
       $(window).adaptTo("foundation-ui").alert("Close", "Dialog closed, event [dialog-closed]");

    });




})($, $(document));