
var socket = $.atmosphere;
var request = { url: 'http://localhost:9030/cyclops/notify/test',
                     contentType : "application/json",
                     logLevel : 'debug',
                     transport : 'websocket',
                     fallbackTransport: 'long-polling'};

request.onOpen = function(response) {
    console.log("Opened conmection")
}

request.onMessage = function(response) {
    try {
        //console.log("Received message")
        //console.log(response.responseBody)
        var event = JSON.parse(response.responseBody);
        var message = event.message;
        $("#tail-text").prepend(message+"<br>")
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
