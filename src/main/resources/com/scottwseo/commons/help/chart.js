
google.charts.load('current', {'packages': ['gauge']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var options = {
        width: 130, height: 130,
        redFrom: 90, redTo: 100,
        yellowFrom: 75, yellowTo: 90,
        minorTicks: 5
    };

    var memoryChart = new google.visualization.Gauge(document.getElementById('memory_chart_div'));
    // var activeDBChart = new google.visualization.Gauge(document.getElementById('active_db_chart_div'));
    // var dbiActive = 'io.dropwizard.db.ManagedPooledDataSource.dbi.active'

    var url = '../../../../metrics';

    var memoryGage;
    var data = google.visualization.arrayToDataTable([
        ['Label', 'Value'],
        ['Memory', 0]
    ]);

    $.getJSON(url, function (json) {
        memoryGage = (json.gauges["jvm.memory.total.used"].value / json.gauges["jvm.memory.total.max"].value) * 100;
        data.setValue(0, 1, memoryGage);
        memoryChart.draw(data, options);
    });

    setInterval(function () {
        $.getJSON(url, function (json) {
            memoryGage = (json.gauges["jvm.memory.total.used"].value / json.gauges["jvm.memory.total.max"].value) * 100;
            data.setValue(0, 1, memoryGage);
            memoryChart.draw(data, options);
        });
    }, 3000);
}

