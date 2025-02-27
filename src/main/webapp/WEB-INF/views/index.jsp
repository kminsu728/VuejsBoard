<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mskim.demo.web.post.Post" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>홈페이지</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js"></script>
    <style>
        #chart1 { width: 100%; height: 200px; }
        #chart2 { width: 100%; height: 200px; }
    </style>
</head>
<body>

<header class="header">
    <jsp:include page="header.jsp" />
</header>

<main class="container mt-5">
    <div class="row">
        <%
            Map<String, List<Post>> postsByType = (Map<String, List<Post>>) request.getAttribute("posts");
            for (Map.Entry<String, List<Post>> entry : postsByType.entrySet()) {
                String type = entry.getKey();
                List<Post> posts = entry.getValue();
        %>
        <div class="col-md-6 mb-4">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">
                        <%= type.equals("qna") ? "QNA 게시판" : "자유게시판" %>
                    </h5>
                    <a href="/board?type=<%= type %>" class="btn btn-sm btn-primary">더보기</a>
                </div>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <%
                            if (posts != null && !posts.isEmpty()) {
                                for (Post post : posts) {
                        %>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <a href="/post?id=<%= post.getId() %>&type=<%= type %>"
                               class="text-decoration-none text-dark text-truncate"
                               style="max-width: 70%;">
                                <%= post.getTitle() %>
                            </a>
                            <small class="text-muted"><%= post.getAuthor() %></small>
                        </li>
                        <%
                            }
                        } else {
                        %>
                        <li class="list-group-item text-center">게시글이 없습니다.</li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>

    <div id="chart1"></div>
    <div id="chart2"></div>
</main>

<footer class="footer mt-5">
    <jsp:include page="footer.jsp" />
</footer>

<script>
    var myChart1 = echarts.init(document.getElementById('chart1'));
    var myChart2 = echarts.init(document.getElementById('chart2'));

    fetch('/api/stat/post-by-type')
        .then(response => response.json())
        .then(data => {

            var chartName = '인기 게시판'
            chart(data, myChart1, chartName);
        })
        .catch(error => console.error('Error fetching data:', error));

    fetch('/api/stat/post-by-author')
        .then(response => response.json())
        .then(data => {
            var chartName = '유저 활동력'
            chart(data, myChart2, chartName);
        })
        .catch(error => console.error('Error fetching data:', error));


    function chart(data, chart, chartName) {
        let now = new Date();
        let oneHourAgo = new Date(now.getTime() - 60 * 60 * 1000);

        oneHourAgo.setMinutes(Math.floor(oneHourAgo.getMinutes() / 10) * 10, 0, 0);

        let xAxisData = [];
        for (let time = oneHourAgo; time <= now; time.setMinutes(time.getMinutes() + 10)) {
            xAxisData.push(new Date(time).toISOString().substring(11, 16)); // HH:mm 형식으로 변환
        }

        let seriesData = {};
        let dataBody = data.body;

        dataBody.sort((a, b) => new Date(a.time) - new Date(b.time));

        dataBody.forEach(item => {
            let itemTime = new Date(item.time);
            let type = item._id;

            if (!seriesData[type]) {
                seriesData[type] = Array(xAxisData.length).fill(0);
            }

            let roundedTime = new Date(
                itemTime.getFullYear(),
                itemTime.getMonth(),
                itemTime.getDate(),
                itemTime.getHours(),
                Math.floor(itemTime.getMinutes() / 10) * 10
            );

            let roundedTimeStr = roundedTime.toISOString().substring(11, 16);
            let index = xAxisData.indexOf(roundedTimeStr);

            if (index !== -1) {
                seriesData[type][index] += item.count;
            }
        });

        var option = {
            title: {text: chartName},
            tooltip: {
                trigger: 'axis',
                formatter: function (params) {
                    let tooltipText = ``;
                    params.forEach(param => {
                        let typeName = param.seriesName;
                        let count = param.data;
                        tooltipText += '<span style="color:' + param.color + ';">' + typeName + ': ' + count + ' 개</span><br/>'; // 원하는 형식으로 표시
                    });

                    return tooltipText;
                }
            },
            xAxis: {type: 'category', data: xAxisData},
            yAxis: {type: 'value'},
            series: Object.keys(seriesData).map(type => ({
                name: type,
                type: 'line',
                data: seriesData[type],
            }))
        };

        chart.setOption(option);
    }
</script>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
