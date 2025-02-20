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

    <script>
        var currentPage = 1;
    </script>
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
        <form action="/board/deletepost" method="post" onsubmit="return confirmDelete()">
            <input type="hidden" name="id" value="<%= post.getId() %>">
            <input type="hidden" name="type" value="<%= post.getType() %>">
            <input type="hidden" name="author" value="<%= post.getAuthor() %>">
            <button type="submit">삭제</button>
        </form>
        <hr>
        <!-- 댓글 입력 폼 -->
        <h3>댓글 작성</h3>
        <input type="text" id="comment-author" placeholder="작성자" value="<%=username%>" readonly required>
        <textarea id="comment-content" placeholder="댓글을 입력하세요" required></textarea>
        <button onclick="submitComment()">등록</button>

        <hr>

        <h3>댓글 목록</h3>
        <ul id="comment-list">
            <!-- AJAX로 동적 로딩 -->
        </ul>

        <div class="pagination">
            <button onclick="loadComments(1)">첫 페이지</button>
            <button onclick="prevPage()">이전</button>
            <button onclick="nextPage()">다음</button>
        </div>
    </div>
</main>

<footer class="footer fixed-bottom">
    <jsp:include page="footer.jsp" />
</footer>



<script>
    document.addEventListener("DOMContentLoaded", function() {
        loadComments(1);
    });

    function confirmDelete() {
        return confirm("게시글을 삭제하시겠습니까?");
    }

    function prevPage() {
        if (currentPage > 1) {
            loadComments(--currentPage);
        }
    }

    function nextPage() {
        loadComments(++currentPage);
    }

    function loadComments(pageNumber) {
        let id = "<%= post.getId() %>";

        fetch('/api/comment/list?id=' + id + '&page=' + pageNumber)
            .then(response => response.json())
            .then(data => {
                let commentList = document.getElementById("comment-list");
                commentList.innerHTML = "";

                if (data.body.length === 0) {
                    commentList.innerHTML = "<li>댓글이 없습니다.</li>";
                } else {
                    data.body.forEach(comment => {
                        let li = document.createElement("li");
                        li.innerHTML = '<p><strong>' + comment.author + '</strong>: ' + comment.content + '</p>' +
                                     '<p><small>' + comment.date + '</small></p>' +
                            '<button onclick="deleteComment(\'' + comment.id + '\', \'' + comment.author + '\')">삭제</button>';
                        commentList.appendChild(li);
                    });
                }
            })
            .catch(error => console.error("Error loading comments:", error));
    }

    function submitComment() {
        let id = "<%= post.getId() %>";
        let author = document.getElementById("comment-author").value;
        let content = document.getElementById("comment-content").value;

        if (!author || !content) {
            alert("이름과 댓글 내용을 입력하세요.");
            return;
        }

        let params = new URLSearchParams();
        params.append("id", id);
        params.append("author", author);
        params.append("content", content);

        fetch("/api/comment/addcomment", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: params.toString()
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data.status == 200) {
                    document.getElementById("comment-author").value = "";
                    document.getElementById("comment-content").value = "";
                    loadComments(currentPage); // 댓글 다시 로드
                } else {
                    alert("댓글 등록 실패");
                }
            })
            .catch(error => console.error("Error adding comment:", error));
    }


    function deleteComment(commentId, author) {
        if (!confirm('댓글을 삭제하시겠습니까?')) {
            return;
        }

        let id = commentId;
        let params = new URLSearchParams();
        params.append("id", id);
        params.append("author", author);

        fetch('/api/comment/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: params.toString()
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === 200) {
                    loadComments(currentPage);
                } else {
                    alert('댓글 삭제에 실패했습니다.');
                }
            })
            .catch(error => {
                alert('댓글 삭제에 실패했습니다.');
            });
    }
</script>

</body>
</html>
