<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <base href="${base}/com/scottwseo/commons/help/" target="_blank">

    <script src="websocket.js"></script>
</head>

<body>

<pre id="out" class="console-output">

</pre>
<div id="spinner">
    <img alt="" src="spinner.gif">
</div>


<script>
    var hostname = window.location.hostname;
    var port = window.location.port;
    var url = 'ws://' + hostname + ':' + port + '${base}/logevent';

    var socket = new SimpleWebSocket(url);
    socket.connect();

    socket.on('newLog', function(data) {
        console.log(data);
        $('#out').append('<pre>' + data + '</pre>');
        window.scrollTo(0, document.body.scrollHeight);
    });


</script>

</body>

</html>