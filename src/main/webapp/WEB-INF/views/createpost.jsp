<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("type") %> 글 작성</title>
</head>
<body>

<header class="header">
    <jsp:include page="header.jsp" />
</header>

<main class="body">
    <div class="createpost-content">
        <h2><%= request.getParameter("type") %> 게시판 - 글 작성</h2>

        <form action="/board/createpost" method="post">
            <input type="hidden" name="type" value="<%= request.getParameter("type") %>">

            <div class="mb-3">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="author">작성자</label>
                <input type="text" id="author" name="author" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="content">내용</label>
                <textarea id="content" name="content" class="form-control" rows="5" required></textarea>
            </div>

            <button type="submit" class="btn btn-primary">등록</button>
            <a href="/board?type=<%= request.getParameter("type") %>" class="btn btn-secondary">취소</a>
        </form>
    </div>
</main>

<footer class="footer fixed-bottom">
    <jsp:include page="footer.jsp" />
</footer>

</body>
</html>
