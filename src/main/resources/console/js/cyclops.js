var socketManager = {
    socket: $.atmosphere,
    currentConnection: null,
    check: function(event) {
        var regexp = $("#regex").val()
        if ( regexp == null || regexp == undefined || regexp.trim().length === 0 ) {
            return true
        }
        reg = regexp.trim()
        if(reg.test(event.message)) {
            return true
        }
        return false
    },
    subscribe: function(topic) {
        this.unsubscribe()
        var request = {
                    url: '/cyclops/notify/' + topic,
                    contentType : "application/json",
                    logLevel : 'debug',
                    transport : 'websocket'
        };

        request.onOpen = function(response) {
            console.log("Opened connection")
        }

        request.onMessage = function(response) {
            try {
                console.log(response)
                var event = JSON.parse(response.responseBody);
                if(true == socketManager.check(event)) {
                    $('#data tbody').prepend('<tr><td><font face="courier" color="red">'+event.source
                                    +'&gt;</font></td><td><font face="courier">'+event.message+'</font></td></tr>');
                }
            } catch (e) {
                console.log(e);
                return;
            }
        }

        request.onError = function(response) {
            console.log("ERROR");
            console.log(response);
        }
        this.currentConnection = this.socket.subscribe(request)
    },
    unsubscribe: function() {
        if(this.currentConnection != null) {
            this.socket.unsubscribe()
            this.currentConnection = null
        }
    }
}

function refreshTopics() {
    $.ajax({
        type: 'GET',
        url: "/cyclops/info/topics",
        async: true
    }).done(function(data) {
        $.each(data, function(key,value) {
            $('#topics').append("<option value=\""+value+"\">"+value+"</option>");
        });
        $('.selectpicker').selectpicker('refresh');
    });
    var topic = $("#topics").val()
    if(topic) {
        console.log('Subscribing to: ' + topic)
        socketManager.subscribe(topic)
    }
}

$('.selectpicker').selectpicker();

//Event handlers
$(document).ready(function(){
    refreshTopics();
});

$("#topics").change(function () {
    $("#data tbody tr").remove()
    socketManager.unsubscribe()
    var topic = $("#topics").val()
    if(topic) {
        console.log('Subscribing to: ' + topic)
        socketManager.subscribe(topic)
    }
});