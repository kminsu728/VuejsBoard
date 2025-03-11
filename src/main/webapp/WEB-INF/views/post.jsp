<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="com.mskim.demo.rest.post.Post" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>

    <%
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) ? auth.getName() : null;
        boolean isAdmin = false;
        if(auth.getPrincipal() != null && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            isAdmin = userDetails.getAuthorities().toString().contains("ROLE_ADMIN");
        }
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

<main class="container mt-5">
    <div class="card">
        <div class="card-body">
            <h2 class="card-title mb-4"><%= post.getTitle() %></h2>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <p class="text-muted mb-1"><strong>작성자:</strong> <%= post.getAuthor() %></p>
                    <p class="text-muted mb-1"><strong>작성일:</strong> <%= post.getDate() %></p>
                </div>
                <div class="col-md-6 text-md-end">
                    <p class="text-muted mb-1"><strong>조회수:</strong> <%= post.getViews() %></p>
                </div>
            </div>

            <div class="card mb-4">
                <div class="card-body">
                    <%= post.getContent() %>
                </div>
            </div>

            <% if (username != null && username.equals(post.getAuthor()) || isAdmin == true) { %>
                <form action="/post/delete" method="post" onsubmit="return confirmDelete()" class="mb-4">
                    <input type="hidden" name="id" value="<%= post.getId() %>">
                    <input type="hidden" name="type" value="<%= post.getType() %>">
                    <input type="hidden" name="author" value="<%= post.getAuthor() %>">
                    <button type="submit" class="btn btn-danger">삭제</button>
                </form>
            <% } %>

            <hr>

            <%
            if (username != null) {
            %>
            <div class="card mb-4">
                <div class="card-body">
                    <h3 class="card-title h5 mb-3">댓글 작성</h3>
                    <div class="mb-3">
                        <input type="text" id="comment-author" class="form-control" 
                               placeholder="작성자" value="<%=username%>" readonly required>
                    </div>
                    <div class="mb-3">
                        <textarea id="comment-content" class="form-control" 
                                placeholder="댓글을 입력하세요" rows="3" required></textarea>
                    </div>
                    <button onclick="submitComment()" class="btn btn-primary">등록</button>
                </div>
            </div>
            <% } else { %>
            <div class="card mb-4">
                <div class="card-body text-center">
                    <p class="mb-0">
                        댓글을 작성하려면 로그인이 필요합니다.
                    </p>
                </div>
            </div>
            <% } %>

            <div class="card">
                <div class="card-body">
                    <h3 class="card-title h5 mb-3">댓글 목록</h3>
                    <ul id="comment-list" class="list-unstyled">
                    </ul>

                    <nav class="mt-4">
                        <ul class="pagination justify-content-center">
                            <li class="page-item">
                                <button class="page-link" onclick="loadComments(1)">첫 페이지</button>
                            </li>
                            <li class="page-item">
                                <button class="page-link" onclick="prevPage()">이전</button>
                            </li>
                            <li class="page-item">
                                <button class="page-link" onclick="nextPage()">다음</button>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</main>

<script>
    $(document).ready(function() {
        $('#comment-content').summernote({
            height: 100,  // 에디터 높이
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

<footer class="footer mt-5">
    <jsp:include page="footer.jsp" />
</footer>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

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
                    commentList.innerHTML = '<li class="text-center text-muted">댓글이 없습니다.</li>';
                } else {
                    data.body.forEach(comment => {
                        let li = document.createElement("li");
                        li.className = 'mb-3 pb-3 border-bottom';
                        li.innerHTML = '<div class="d-flex justify-content-between align-items-start">' +
                            '<div><strong>' + comment.author + '</strong>' +
                            '<p class="mb-1">' + comment.content + '</p>' +
                            '<small class="text-muted">' + comment.date + '</small>' +
                            '</div>' +
                            '<button onclick="deleteComment(\'' + comment.id + '\', \'' + comment.author + '\')" ' +
                            'class="btn btn-sm btn-secondary">삭제</button>' +
                            '</div>';
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

        fetch("/api/comment/add", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: params.toString()
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data.status == 200) {
                    document.getElementById("comment-author").value = "<%=username%>";
                    //document.getElementById("comment-content").value = "";
                    $('#comment-content').summernote('reset');
                    loadComments(currentPage);
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
                    alert('댓글 삭제 권한이 없습니다.');
                }
            })
            .catch(error => {
                alert('댓글 삭제에 실패했습니다.');
            });
    }
</script>

</body>
</html>
