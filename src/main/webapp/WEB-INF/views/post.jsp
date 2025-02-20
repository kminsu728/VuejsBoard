<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.mskim.demo.web.board.Post" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <%
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) ? auth.getName() : null;
        Post post = (Post) request.getAttribute("post");
    %>
    <title><%= post.getTitle() %></title>
</head>
<body>

<header class="header">
    <jsp:include page="header.jsp" />
</header>

<main class="body">
    <div class="post-content">
        <h2><%= post.getTitle() %></h2>
        <p><strong>작성자:</strong> <%= post.getAuthor() %></p>
        <p><strong>내용:</strong> <%= post.getContent() %></p>
        <p><strong>작성일:</strong> <%= post.getDate() %></p>
        <p><strong>조회수:</strong> <%= post.getViews() %></p>
        <%--<a href="/board/deletepost?type=<%=post.getType()%>%>&id=<%= post.getId()%>">글 작성</a>--%>
        <form action="/board/deletepost" method="post">
            <input type="hidden" name="id" value="<%= post.getId() %>">
            <input type="hidden" name="type" value="<%= post.getType() %>">
            <input type="hidden" name="author" value="<%= post.getAuthor() %>">
            <button type="submit">삭제</button>
        </form>
    </div>
</main>

<footer class="footer fixed-bottom">
    <jsp:include page="footer.jsp" />
</footer>

</body>
</html>
