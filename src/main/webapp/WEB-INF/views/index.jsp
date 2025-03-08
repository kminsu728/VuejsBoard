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
    <style>
    </style>
</head>
<body>

<div id="app">
    <app-header :user="user" :boards="boards"
                @open-login-modal="openLoginModal"
                @open-user-info-modal="openUserInfoModal"
                @open-board-modal="openBoardModal"></app-header>
    <main class="container mt-5">
        <div class="row">
            <div v-for="(posts, type) in postsByType" :key="type" class="col-md-6 mb-4">
                <div class="card">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <h5 class="mb-0">{{ type === 'qna' ? 'QNA 게시판' : '자유게시판' }}</h5>
                        <a :href="'/board?type=' + type" class="btn btn-sm btn-primary">더보기</a>
                    </div>
                    <div class="card-body">
                        <ul class="list-group list-group-flush">
                            <li v-for="post in posts" :key="post.id" class="list-group-item d-flex justify-content-between align-items-center">
                                <a :href="'/post?id=' + post.id + '&type=' + type"
                                   class="text-decoration-none text-dark text-truncate"
                                   style="max-width: 70%;">
                                    {{ post.title }}
                                </a>
                                <small class="text-muted">{{ post.author }}</small>
                            </li>
                            <li v-if="posts.length === 0" class="list-group-item text-center">게시글이 없습니다.</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <Chart chartId="chart1" :chartName="'인기 게시판'" :url="'/api/stat/post-by-type'"></Chart>
        <Chart chartId="chart2" :chartName="'유저 활동력'" :url="'/api/stat/post-by-author'"></Chart>
    </main>
    <!-- Vue 모달 컴포넌트 -->
    <login-modal ref="loginModal"></login-modal>
    <user-info-modal ref="userInfoModal" :user="user"></user-info-modal>
    <add-board-modal ref="boardModal"></add-board-modal>
</div>

<footer class="footer mt-5">
    <jsp:include page="footer.jsp"/>
</footer>

<script type="module" src="/dist/main.js"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>