<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TopHeader Example</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

    <%
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) ? auth.getName() : null;
    %>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom shadow-sm">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Home</a>

        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/board?type=qna">QnA 게시판</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/board?type=community">자유게시판</a>
                </li>
            </ul>
        </div>

        <div class="d-flex">
            <% if (username == null) { %>
            <button class="btn btn-primary me-2" onclick="openLoginModal()">로그인</button>
            <a class="btn btn-outline-secondary" href="/signup.jsp">회원가입</a>
            <% } else { %>
            <span class="me-3 fw-bold text-dark"><%= username %> 님</span>
            <button class="btn btn-primary me-2" onclick="openUserInfoModal()">회원정보</button>
            <button class="btn btn-danger" onclick="logout()">로그아웃</button>
            <% } %>
        </div>
    </div>
</nav>

<%@ include file="login-modal.jsp" %>
<%@ include file="user-info-modal.jsp" %>

<script>
    function openLoginModal() {
        let modal = new bootstrap.Modal(document.getElementById('loginModal'));
        modal.show();
    }

    function openUserInfoModal() {
        let modal = new bootstrap.Modal(document.getElementById('userInfoModal'));
        modal.show();

        let username = "<%= username %>";  // 서버에서 전달된 username

        // 사용자 정보를 가져오는 API 호출
        fetch("/api/user/info?username=" + username, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data.status == 200) {
                    console.log(data.body.userId);
                    console.log(data.body.name);
                    console.log(data.body.email);
                    console.log(data.body.role);
                    document.getElementById("username").value = username;
                    document.getElementById("name").value = data.body.name;
                    document.getElementById("email").value = data.body.email;
                    document.getElementById("role").value = data.body.role;
                } else {
                    alert("사용자 정보를 불러오는 데 실패했습니다.");
                }
            })
            .catch(error => console.error("Error fetching user data:", error));
    }

    function logout() {
        fetch("/logout", { method: "POST" })
            .then(response => response.json())
            .then(data => {
                if (data.status == 200) {
                    window.location.reload();
                }
            });
    }
</script>
</body>
</html>


