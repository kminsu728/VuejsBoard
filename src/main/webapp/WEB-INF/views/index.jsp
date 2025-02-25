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
</main>

<footer class="footer mt-5">
    <jsp:include page="footer.jsp" />
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
