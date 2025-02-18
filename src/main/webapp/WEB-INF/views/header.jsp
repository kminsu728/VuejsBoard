<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mskim.demo.rest.user.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TopHeader Example</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<%
    User loggedInUser = (User) session.getAttribute("user");
%>
<header>
    <div class="top-header">
        <div class="container">
            <div class="logo" onclick="loadContent('home.jsp')">
                <span>HOME</span>
            </div>
            <nav>
                <ul>
                    <li onclick="loadContent('board_1')"><a href="#">QnA</a></li>
                    <li onclick="loadContent('board_2')"><a href="#">Community</a></li>
                </ul>
            </nav>
            <div class="auth-section">
                <% if (loggedInUser == null) { %>
                <button onclick="openLoginPopup()">로그인</button>
                <button onclick="location.href='register.jsp'">회원가입</button>
                <% } else { %>
                <span><%= loggedInUser.getName() %>님 환영합니다!</span>
                <button onclick="logout()">로그아웃</button>
                <% } %>
            </div>
        </div>
    </div>
</header>


<!-- 로그인 팝업 -->
<div id="loginPopup" class="popup">
    <div class="popup-content">
        <span class="close" onclick="closeLoginPopup()">&times;</span>
        <h2>로그인</h2>
        <form id="loginForm" method="post" action="/auth/login">
            <label>아이디:</label>
            <input type="text" name="username" required>
            <label>비밀번호:</label>
            <input type="password" name="password" required>
            <button type="submit">로그인</button>
        </form>
    </div>
</div>

<style>
    .popup { display: none; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%);
        background: white; padding: 20px; border-radius: 5px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); }
    .popup-content { text-align: center; }
    .close { position: absolute; top: 10px; right: 10px; cursor: pointer; font-size: 20px; }
</style>

<script>
    function openLoginPopup() {
        document.getElementById("loginPopup").style.display = "block";
    }
    function closeLoginPopup() {
        document.getElementById("loginPopup").style.display = "none";
    }
    function logout() {
        fetch('/auth/logout', { method: 'GET' })
            .then(() => location.reload());
    }
</script>

</body>
</html>


