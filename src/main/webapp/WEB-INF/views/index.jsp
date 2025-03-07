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

    <script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>

</head>
<body>

<header class="header">
    <jsp:include page="header.jsp"/>
</header>

<main id="app" class="container mt-5">
    <div class="row">
        <!-- postsByType 객체에서 type(key)와 posts(value)를 순회 -->
        <div v-for="(posts, type) in postsByType" :key="type" class="col-md-6 mb-4">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <!-- 게시판 이름 동적으로 설정 -->
                    <h5 class="mb-0">{{ type === 'qna' ? 'QNA 게시판' : '자유게시판' }}</h5>
                    <a :href="'/board?type=' + type" class="btn btn-sm btn-primary">더보기</a>
                </div>
                <div class="card-body">
                    <ul class="list-group list-group-flush">
                        <!-- 게시글 목록 순회 -->
                        <li v-for="post in posts" :key="post.id" class="list-group-item d-flex justify-content-between align-items-center">
                            <a :href="'/post?id=' + post.id + '&type=' + type"
                               class="text-decoration-none text-dark text-truncate"
                               style="max-width: 70%;">
                                {{ post.title }}
                            </a>
                            <small class="text-muted">{{ post.author }}</small>
                        </li>
                        <!-- 게시글이 없을 경우 메시지 출력 -->
                        <li v-if="posts.length === 0" class="list-group-item text-center">게시글이 없습니다.</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div id="chart1"></div>
    <div id="chart2"></div>
</main>

<footer class="footer mt-5">
    <jsp:include page="footer.jsp"/>
</footer>

<script>
    const { createApp, reactive, ref, onMounted } = Vue;

    createApp({
        setup() {
            const postsByType = reactive({
                qna: [],
                community: []
            });
            const chart1 = ref(null);
            const chart2 = ref(null);

            // 게시글 데이터 불러오기
            const fetchPosts = async (type) => {
                try {
                    console.log('fetchPosts 호출됨, type:', type);
                    const url = '/api/post/list?type=' + type + '&page=1';
                    const response = await fetch(url);
                    const data = await response.json();

                    if (!response.ok) {
                        throw new Error('Failed to fetch ' + type + ' posts');
                    }

                    postsByType[type] = data.body.posts;
                    console.log(postsByType[type]);
                } catch (error) {
                    console.error(type + ' 게시글 데이터를 불러오는 중 오류 발생:', error);
                }
            }

            // 차트 데이터 불러오기
            const fetchChartData = async (url, chartRef, chartName) => {
                try {
                    const response = await fetch(url);
                    const data = await response.json();
                    renderChart(data, chartRef, chartName);
                } catch (error) {
                    console.error('${chartName} 데이터를 불러오는 중 오류 발생:', error);
                }
            };

            // 차트 렌더링 함수
            const renderChart = (data, chartRef, chartName) => {
                if (!chartRef.value) return;

                let xAxisData = [];
                let seriesData = {};

                data.body.forEach(item => {
                    let time = new Date(item.time).toISOString().substring(11, 16);
                    let type = item._id;

                    if (!seriesData[type]) {
                        seriesData[type] = Array(xAxisData.length).fill(0);
                    }

                    let index = xAxisData.indexOf(time);
                    if (index === -1) {
                        xAxisData.push(time);
                        index = xAxisData.length - 1;
                    }

                    seriesData[type][index] = item.count;
                });

                let option = {
                    title: { text: chartName },
                    tooltip: { trigger: 'axis' },
                    xAxis: { type: 'category', data: xAxisData },
                    yAxis: { type: 'value' },
                    series: Object.keys(seriesData).map(type => ({
                        name: type,
                        type: 'line',
                        data: seriesData[type],
                    }))
                };

                chartRef.value.setOption(option);
            };

            // Vue 컴포넌트 마운트 후 실행
            onMounted(() => {
                fetchPosts('qna');
                fetchPosts('community');

                chart1.value = echarts.init(document.getElementById('chart1'));
                chart2.value = echarts.init(document.getElementById('chart2'));

                fetchChartData('/api/stat/post-by-type', chart1, '인기 게시판');
                fetchChartData('/api/stat/post-by-author', chart2, '유저 활동력');
            });

            return { postsByType };
        }
    }).mount("#app");
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
