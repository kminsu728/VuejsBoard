<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= request.getAttribute("type") %> 글 작성</title>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>

    <%
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) ? auth.getName() : null;
    %>
</head>
<body>

<header class="header">
    <jsp:include page="header.jsp" />
</header>

<main class="body">
    <div class="createpost-content">
        <h2><%= request.getParameter("type") %> 게시판 - 글 작성</h2>

        <form action="/post/create" method="post">
            <input type="hidden" name="type" value="<%= request.getParameter("type") %>">

            <div class="mb-3">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" class="form-control" required>
            </div>

            <div class="mb-3">
                <label for="author">작성자</label>
                <input type="text" id="author" name="author" class="form-control" value="<%= username %>" readonly required>
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

<script>
    $(document).ready(function() {
        $('#content').summernote({
            height: 300,  // 에디터 높이
            placeholder: '여기에 내용을 입력하세요...',
            tabsize: 2,
            toolbar: [
                ['style', ['bold', 'italic', 'underline', 'clear']],
                ['font', ['strikethrough', 'superscript', 'subscript']],
                ['para', ['ul', 'ol', 'paragraph']],
                ['insert', ['link', 'picture', 'video']],
                ['view', ['fullscreen', 'codeview']]
            ]
        });
    });
</script>

<footer class="footer fixed-bottom">
    <jsp:include page="footer.jsp" />
</footer>

</body>
</html>
