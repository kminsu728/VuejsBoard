<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mskim.demo.web.board.Post" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("type") %> 게시판</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <%
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) ? auth.getName() : null;
    %>
</head>
<body>

<header class="header">
    <jsp:include page="header.jsp" />
</header>

<main class="container mt-5">
    <div class="row">
        <div class="col-12">
            <h2 class="mb-4"><%= request.getAttribute("type") %> 게시판</h2>
            
            <div class="d-flex justify-content-end mb-3">

                <% if (username != null) { %>
                <a href="/board/createpost?type=<%= request.getAttribute("type") %>" 
                   class="btn btn-primary">글 작성</a>
                   <% } else { %>
                    <button type="button" 
                            class="btn btn-secondary" 
                            onclick="alert('로그인이 필요한 서비스입니다.')"
                            >글 작성</button>
                <% } %>
            </div>

            <table class="table table-hover">
                <thead class="table-light">
                    <tr>
<%--                        <th scope="col" width="10%">번호</th>--%>
                        <th scope="col" width="50%">제목</th>
                        <th scope="col" width="10%">작성자</th>
                        <th scope="col" width="20%">작성일</th>
                        <th scope="col" width="20%">조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Post> posts = (List<Post>) request.getAttribute("posts");
                        if (posts != null && !posts.isEmpty()) {
                            for (Post post : posts) {
                    %>
                    <tr>
<%--                        <td><%= post.getId() %></td>--%>
                        <td>
                            <a href="/board/post?id=<%= post.getId() %>&type=<%= request.getAttribute("type") %>"
                               class="text-decoration-none text-dark">
                                <%= post.getTitle() %>
                            </a>
                        </td>
                        <td><%= post.getAuthor() %></td>
                        <td><%= post.getDate() %></td>
                        <td><%= post.getViews() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-center">게시글이 없습니다.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <nav aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">
                    <li class="page-item">
                        <a class="page-link" href="/board?type=<%= request.getAttribute("type") %>&page=1">처음</a>
                    </li>
                    <%
                        int currentPage = 1;
                        if (request.getParameter("page") != null) {
                            try {
                                currentPage = Integer.parseInt(request.getParameter("page"));
                            } catch (NumberFormatException e) {
                                currentPage = 1;
                            }
                        }

                        if (currentPage > 1) {
                    %>
                    <li class="page-item">
                        <a class="page-link" href="/board?type=<%= request.getAttribute("type") %>&page=<%= currentPage - 1 %>">이전</a>
                    </li>
                    <% } %>
                    <li class="page-item">
                        <a class="page-link" href="/board?type=<%= request.getAttribute("type") %>&page=<%= currentPage + 1 %>">다음</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</main>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<footer class="footer fixed-bottom">
    <jsp:include page="footer.jsp" />
</footer>

</body>
</html>
