var socketManager = {
    socket: $.atmosphere,
    currentConnection: null,
    maxRowCount: 1000,
    currentRowCount: 0,
    check: function(event) {
        var regexp = $("#regex").val()
        if ( regexp == null || regexp == undefined || regexp.trim().length === 0 ) {
            return true
        }
        reg = new RegExp(regexp.trim(), "i")
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
                    socketManager.currentRowCount = socketManager.currentRowCount + 1
                    if(socketManager.currentRowCount > socketManager.maxRowCount ) {
                        socketManager.currentRowCount = socketManager.maxRowCount;
                        $('#data tbody tr:last').remove()
                    }
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
        socketManager.currentRowCount = 0
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
        var i = 0;
        $.each(data, function(key,value) {
            if(i==0) {
                $('#topics').append("<option value=\""+value+"\" selected>"+value+"</option>");
            }
            else {
                $('#topics').append("<option value=\""+value+"\">"+value+"</option>");
            }
        });
        $('.selectpicker').selectpicker('refresh');
        var topic = $("#topics").val()
        if(topic) {
            console.log('Subscribing to: ' + topic)
            socketManager.subscribe(topic)
        }
    });
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