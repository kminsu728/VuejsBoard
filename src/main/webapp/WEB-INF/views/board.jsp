<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mskim.demo.web.board.Post" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("type") %> 게시판</title>
</head>
<body>

<header class="header">
    <jsp:include page="header.jsp" />
</header>

<main class="body">
    <div class="board-content">
        <h2><%= request.getAttribute("type") %> 게시판</h2>
        <ul>
            <%
                // 서버에서 넘겨준 게시글 리스트 가져오기
                List<Post> posts = (List<Post>) request.getAttribute("posts");

                if (posts != null && !posts.isEmpty()) {
                    for (Post post : posts) {
            %>
            <li>
                <a href="/board/post?id=<%= post.getId() %>&type=<%= request.getAttribute("type") %>">
                    <%= post.getTitle() %> - <%= post.getAuthor() %>
                </a>
            </li>
            <%
                }
            } else {
            %>
            <li>게시글이 없습니다.</li>
            <%
                }
            %>
        </ul>

        <div class="pagination">
            <a href="/board?type=<%= request.getAttribute("type") %>&page=1">첫 페이지</a>
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
            <a href="/board?type=<%= request.getAttribute("type") %>&page=<%= currentPage - 1 %>">이전</a>
            <% } %>
            <a href="/board?type=<%= request.getAttribute("type") %>&page=<%= currentPage + 1 %>">다음</a>
        </div>
    </div>
</main>

<footer class="footer fixed-bottom">
    <jsp:include page="footer.jsp" />
</footer>

</body>
</html>
