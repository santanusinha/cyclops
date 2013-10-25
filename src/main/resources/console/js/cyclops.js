var graphDataManager = {
    maxData: 60,
    data: null,
    lastUpdateTime: 0,
    currentIndex: 0,
    lastIndex: 0,
    currStart: 0,
    reset: function() {
        this.lastUpdateTime = new Date().getTime();
        this.currentTime = 0
        this.data = [this.maxData]
        for(var i = 0; i < this.maxData; i++) {
            this.data[i] = 0
        }
    },
    isFull: function() {
        return (this.currIndex + 1) % this.maxData === this.curStart
    },
    incrementEventCount: function() {
        var currentTime = new Date().getTime();
        if(currentTime < (this.lastUpdateTime + 60000)) {
            this.data[this.currentIndex] = this.data[this.currentIndex] + 1;
        }
        else {
            if( this.isFull() ) {
                this.currStart = (this.currStart + 1) % this.maxData
            }
            this.currentIndex = (this.currentIndex + 1) % this.maxData;
            //for(var i = (this.lastIndex + 1) % this.maxData; i != this.currentIndex; i = ((i + 1) % this.maxData)) {
            //    this.data[i] = 0;
            //  }
            this.lastIndex = this.currentIndex
            this.data[this.currentIndex] = 1;
            this.lastUpdateTime = currentTime

        }
    },
    start: function() {
        return this.currStart;
    },
    listData: function() {
        var results = [this.maxData]
        var startIndex = this.start()
        for(var i = 0; i < this.maxData; i++) {
            results[i] = [i, this.data[startIndex]]
            startIndex = (startIndex + 1) % this.maxData
        }
        return results
    }
}

var socketManager = {
    socket: $.atmosphere,
    currentConnection: null,
    maxRowCount: 1000,

    currentRowCount: 0,
    checkHost: function(event) {
        if(!$("#apply-host-regex").hasClass("active")) {
            return true;
        }
        var regexp = $("#host-regex").val()
        if ( regexp == null || regexp == undefined || regexp.trim().length === 0 ) {
           return true
        }
        reg = new RegExp(regexp.trim(), "i")
        if(reg.test(event.source)) {
           return true
        }
        return false
    },
    check: function(event) {
        if(!$("#apply-regex").hasClass("active")) {
            return true;
        }
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
                if(true == socketManager.checkHost(event) && true == socketManager.check(event)) {
                    socketManager.currentRowCount = socketManager.currentRowCount + 1
                    if(socketManager.currentRowCount > socketManager.maxRowCount ) {
                        socketManager.currentRowCount = socketManager.maxRowCount;
                        $('#data tbody tr:last').remove()
                    }
                    $('#data tbody').prepend('<tr><td><font face="courier" color="red">'+event.source
                                    +'&gt;</font></td><td><font face="courier">'+event.message+'</font></td></tr>');
                    graphDataManager.incrementEventCount()
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


function startListening() {
    var topic = $("#topics").val()
    if(topic) {
        $("#data tbody tr").remove()
        graphDataManager.reset();
        console.log('Subscribing to: ' + topic)
        socketManager.subscribe(topic)
    }
}

function stopListening(clear) {
    if(true == clear) {
        $("#data tbody tr").remove()
        graphDataManager.reset();
    }
    socketManager.unsubscribe()
}

function refreshTopics() {
    $.ajax({
        type: 'GET',
        url: "/cyclops/info/topics",
        async: true
    }).done(function(data) {
        $("#topics").find('option').remove()
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
        startListening()
    });
}

$('.selectpicker').selectpicker();
$("#refresh-topics").click(refreshTopics)

//Event handlers
$(document).ready(function(){
    refreshTopics();
    graphDataManager.reset();
    setInterval(function(){
        var data = graphDataManager.listData();
        //console.log(data)
        var options = {
                    series: { curvedLines: { active: true }},
                    color: "#c8c8c8",
        			lines: {
        				show: true,
        				fill: false,
        				fillColor: "#272b30",
        				lineColor: "#c8c8c8"
        			},
        			xaxis: {
        			    show: false,
        			    color: "#c8c8c8",
        			    ticks: null,
        			    tickLength: 0,
                        tickFormatter: function() {
                            return " ";
                        },
                        min: 0,
                        max: 60

        			},
        			yaxis: {
        			    show: true,
                        min: 0
        			},
                    grid: {
                        borderWidth: 0,
                        show: true
                    }
        		};
        $.plot("#event-histogram",  [{color: "#c8c8c8", data: data, curvedLines: {apply: true}}], options);
    }, 3000);
});

$("#topics").change(function () {
    stopListening(true)
    startListening()
});

$('#stream-toggle').on('switch-change', function (e, data) {
    if(true == data.value) {
        startListening()
    }
    else {
        stopListening(false)
    }
})