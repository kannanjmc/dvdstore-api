var SimpleWebSocket = function (url) {

    var socket = new WebSocket(url);

    var callbacks = {};

    this.connect = function () {
    }

    this.on = function (event, callback) {
        callbacks[event] = callbacks[event] || [];
        callbacks[event].push(callback);
        return this;
    };


    this.send = function (event, data) {
        checkConnection();

        var json = JSON.stringify({event: event, data: data});
        socket.send(json);
        return this;
    };

    // dispatch to the right handlers
    socket.onmessage = function (evt) {
        // console.log('evt = ', evt);
        if (evt.data) {
            var json = JSON.parse(evt.data);
            dispatch(json.event, json.data);
        }
        else {
            console.log('*** error ***');
        }
    };

    socket.onclose = function () {
        checkConnection();
        dispatch('close', null)
    }

    socket.onopen = function () {
        checkConnection();
        dispatch('open', null)
    }

    var dispatch = function (event, arg) {
        var callback = callbacks[event];

        // no callbacks for this event
        if (typeof callback == 'undefined') {
            console.log('callback = ' + callback + ' event = ' + event);
            return;
        }

        for (var i = 0; i < callback.length; i++) {
            callback[i](arg);
        }
    }

    function checkConnection() {
        if (!socket) {
            console.log('You must call connect() first');
            return false;
        }
    }
};