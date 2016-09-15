
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <base href="${base}/com/scottwseo/commons/help/" target="_blank">
    <link rel="icon" href="favicon.ico">

    <title>Dashboard Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="dashboard.css" rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><span id="appname"></span>&nbsp;<span id="tag"></span></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Dashboard</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li id="overview" class="active"><a href="overview.html">Overview <span class="sr-only">(current)</span></a></li>
                <li><a href="${base}/swagger/">Swagger</a></li>
                <li><a href="${base}/log">Log</a></li>
                <li><a href="${base}/togglz">Features</a></li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="embed-responsive embed-responsive-16by9">
                <iframe class="embed-responsive-item" name="body_iframe"></iframe>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="jquery.min.js"><\/script>')</script>
<script src="bootstrap.min.js"></script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
<script src="holder.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="ie10-viewport-bug-workaround.js"></script>

<script>

    var previous = $('#overview');

    $(document).ready(function(){

        $('.sidebar').on('click', 'a', function (e) {
            var href = $(this).attr('href');
            $('iframe').attr('src', href);
            e.preventDefault();
        })

        $('.sidebar').on('click', 'li', function (e) {
            if (previous) {
                previous.toggleClass('active');
            }
            $(this).toggleClass('active');
            previous = $(this);
        })

        // load the initial page
        $('iframe').attr('src', 'overview.html');

        $.ajax({url: '${base}/meta', success: function(json){
            $("#tag").html(json.tag);
            $("#appname").html(json.appname);
        }});
    });

</script>

</body>
</html>
