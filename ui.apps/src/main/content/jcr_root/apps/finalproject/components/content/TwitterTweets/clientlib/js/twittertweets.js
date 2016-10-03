//var tweetsclass = 'a_'+Math.random();


/*arr = tweetsclass.split('.');
var tweetsclass1 = arr[arr.length-1];
alert("aftertweetsclass"+tweetsclass1);


alert(tweetsclass);

 var databak = "";
 var cnt = 0;
*/

$(document).ready(function() {

//    $(function() {



var divt = $("div[id^='header']");
$.each( divt, function( key, value ) {
  //alert( key + ": " + value );
   // alert(value.id);
    var id = value.id;
   var tmp = id.substr ( id.indexOf ( 'header' ) + 6 ); //alert(tmp);


var tcol = $('#tcolor'+tmp).val();
    //alert('tcolor'+tcolor);
     var  ss = $('#searchval'+tmp).val();
     var tt = $('#tweetsnum'+tmp).val();

    // var tbgcolor = $('#tcolor'+tmp).val();

    //alert("ss"+ss);
    //alert("tt"+tt);



     // if ( xaxis == 'undefined' ) xaxis =4;
	 // if ( yaxis == 'undefined' ) yaxis =4;


loadLatestTweet(ss,tt,tmp,tcol);

   /* var sum = 0;
var arr = [ 1, 2, 3, 4, 5 ];
  //  var arr = [ 1 ];

        if (ss == null)
        {
            ss = "dwaynehale";
            tt = 50;
        }

$.each( arr, function( index, value ){
    alert("fasfdasfasfsafsa("+ss+","+tt+","+tmp+");");

    var divt = $("div[id^='header']");
$.each( divt, function( key, value ) {
  alert( key + ": " + value );
    alert(value.id);
    var id = value.id;
   var tmp = id.substr ( id.indexOf ( 'header' ) + 6 ); //alert(tmp);


     var  ss = $('#searchval'+tmp).val();
     var tt = $('#tweetsnum'+tmp).val();



setInterval("loadLatestTweet("+ss+","+tt+","+tmp+");", 20000);
});

});
*/


});





    var cval =  $('#classval').val();
    //alert(cval);
   // alert("searchval["+cval+"]");
    var  searchval = $("#searchval"+cval).val();
    var tweetsnum = $("#tweetsnum"+cval).val();


//alert("searchval~~~" + searchval);
//alert("tweetsnum~~~" + tweetsnum);


    //$('#jstwitter').addClass(tweetsclass1);

    //$('#jstwitter').attr('id','jstwitter'+tweetsclass1);

    /*
     $('#searchval').attr('id','searchval'+tweetsclass1);
     $('#tweetsnum').attr('id','tweetsnum'+tweetsclass1);
    */


var sum = 0;
//var arr = [ 1, 2, 3, 4, 5 ];
    var arr = [ 1 ];

        if (searchval == null)
        {
            searchval = "dwaynehale";
            tweetsnum = 50;
        }

/*

$.each( arr, function( index, value ){

setInterval("loadMoreTweets()", 16000);
});

    */






	 //loadLatestTweet(searchval,tweetsnum,cval);
}); 

function loadMoreTweets(cval){
//alert("loadmoretweets"+searchval+"~~~"+tweetsnum );

     // var cval =  $('#classval').val();

 var  search = $('#searchval'+cval).val();
 var tweets = $('#tweetsnum'+cval).val();


    //alert(search);
    //alert(tweets);

  //   var a =  parseInt($('#tweetsnum').val()) ;
     var a =  parseInt(tweets) ;
     var b =  parseInt(10) ;
    tweets = a+b;
    loadLatestTweet(search,tweets,cval);
};

//Twitter Parsers
String.prototype.parseURL = function() {
	return this.replace(/[A-Za-z]+:\/\/[A-Za-z0-9-_]+\.[A-Za-z0-9-_:%&~\?\/.=]+/g, function(url) {
		return url.link(url);
	});
};


String.prototype.parseUsername = function() {
	return this.replace(/[@]+[A-Za-z0-9-_]+/g, function(u) {
		var username = u.replace("@","")
		return u.link("http://twitter.com/"+username);
	});
};
String.prototype.parseHashtag = function() {
	return this.replace(/[#]+[A-Za-z0-9-_]+/g, function(t) {
		var tag = t.replace("#","%23")
		return t.link("http://search.twitter.com/search?q="+tag);
	});
};
function parseDate(str) {
    var v=str.split(' ');
    return new Date(Date.parse(v[1]+" "+v[2]+", "+v[5]+" "+v[3]+" UTC"));
} 

function loadLatestTweet(searchval,tweetsnum,cval,tcol){
//alert('from loadlatest'+tcol);

//tcol ="#9A3225";

    //alert(searchval+tweetsnum+cval);

	//cnt = cnt + 1;
    //alert("call loadLatesttweet count ="+cnt);

   // var numTweets = 1;
   // var _url = '/bin/TwitterTweetsServlet?search='+searchval+'&count='+tweetsnum;
    
    var _url = '/bin/NewTwitterTweetsServlet?search='+searchval+'&count='+tweetsnum;

    //alert("url"+_url);

    $("."+cval).empty();

    $.getJSON(_url,function(data){


      /*  if (cnt > 2) {
            data = "E";
        } */

       // alert("data: "+data);

/*        $.trim(data);

        if (data == "ERROR")
        {
           // alert(data);

            data = databak ;

            //alert("after E=" +data);
        }

        */

  //alert("databak: "+databak);

/*        if (data != "ERROR") {
          databak = data;
        }

*/

    for(var i = 0; i< data.length; i++){
    	
    	 if (parseInt(data[0].code) == 34)
         {


             tweet = '<div id="uppercase bold" class="white">'+data[i].message+'</div>'
             $("."+cval).append('<p>'+tweet+'</p>');

         }

    	 else {

            var image = data[i].user.profile_image_url; //alert(image);

            var name =  "@"+data[i].user.name;

            var tweetStart = '<li><div class="avatar row"><img src="'+image+'"/><div class="hover"><div class="icon-twitter"></div></div></div>';
            var tweet = '<div class="bubble-container"><div class="bubble " style="background-color:'+tcol+';"><div class="retweet"><div class="icon-retweet"></div></div><p >'+name+'</p><br>'+data[i].text +'<div class="over-bubble"><div class="icon-mail-reply action"></div><div class="icon-retweet action"></div><div class="icon-star"></div></div></div><div class="arrow"></div></div></li>';

        //alert("tweet:::"+tweet);
            var created = parseDate(data[i].created_at);

        //alert("date:::"+created);
            var createdDate = created.getDate()+'-'+(created.getMonth()+1)+'-'+created.getFullYear()+' at '+created.getHours()+':'+created.getMinutes();
            tweet = tweet.parseURL().parseUsername().parseHashtag();
            tweet += '<div class="tweeter-info"><div id="uppercase bold"><a class="white" href="https://twitter.com/#!/CypressNorth" target="_blank" class="black bold"></a></div><div class="left"><a style="color:#FFFFFF;" href="https://twitter.com/#!/CypressNorth/status/'+data[i].id_str+'">'+createdDate+'</a></div></div></div>'
            $("."+cval).append('<p>'+tweetStart+tweet+'</p>');
    	 }
        //alert(tweet);



        }
    });
}
