var socket = $.atmosphere;
var request = { url: '/cyclops/notify/test',
                     contentType : "application/json",
                     logLevel : 'debug',
                     transport : 'websocket'};

request.onOpen = function(response) {
    console.log("Opened conmection")
}

request.onMessage = function(response) {
    try {
        //console.log("Received message")
        //console.log(response.responseBody)
        var event = JSON.parse(response.responseBody);
        var message = event.message;
        $("#tail-text").prepend('<font face="courier"><font color="red">'+event.source+'&gt;</font>&nbsp;'+message+'</font><br>')
    } catch (e) {
        //console.log('This does not look like a valid JSON: ', response.responseBody);
        return;
    }
}

request.onError = function(response) {
    console.log("ERROR");
    console.log(response);
}

var subSocket = socket.subscribe(request);
/*var socket = io.connect('http://localhost:9030/cyclops/notify/');
  socket.on('test', function (data) {
    console.log(data);
    //socket.emit('my other event', { my: 'data' });
  });*/

$('.selectpicker').selectpicker();

$(document).ready(function(){
      $.ajax({
        type: 'GET',
        url: "/cyclops/info/topics",
        async: true
      }).done(function(data) {
          //var items = $('#apps').children().clone();
          $.each(data, function(key,value) {
            $('#topics').append("<option>"+value+"</option>");
          });
          $('.selectpicker').selectpicker('refresh');
        });
      });