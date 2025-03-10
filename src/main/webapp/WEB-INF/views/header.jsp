<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.GrantedAuthority" %>
<%@ page import="com.mskim.demo.rest.login.CustomUserDetails" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TopHeader Example</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>

    <%
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) ? auth.getName() : null;
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);

        String nickname = "";
        if(auth.getPrincipal() != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) auth.getPrincipal();
            nickname = customUserDetails.getUser().getName();
        }
    %>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom shadow-sm">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Home</a>

        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto" id="boardList">
                <!-- 여기에 동적으로 게시판 목록이 삽입됨 -->
            </ul>
        </div>

        <div class="d-flex">
            <% if (username == null) { %>
            <button class="btn btn-primary me-2" onclick="openLoginModal()">로그인</button>
            <a class="btn btn-outline-secondary" href="/signup.jsp">회원가입</a>
            <% } else { %>
            <span class="me-3 fw-bold text-dark"><%= nickname %> 님</span>
            <% if(isAdmin == true) { %>
                <button class="btn btn-primary me-2" onclick="addBoardType()">게시판 생성</button>
            <% } %>
            <button class="btn btn-primary me-2" onclick="openUserInfoModal()">회원정보</button>
            <button class="btn btn-danger" onclick="logout()">로그아웃</button>
            <% } %>
        </div>
    </div>
</nav>

<%@ include file="login-modal.jsp" %>
<%@ include file="user-info-modal.jsp" %>
<%@ include file="add-board-modal.jsp" %>

<script>
    function openLoginModal() {
        let modal = new bootstrap.Modal(document.getElementById('loginModal'));
        modal.show();
        modal._element.addEventListener('shown.bs.modal', function () {
            document.getElementById('username').focus();
        });
    }

    function addBoardType() {
        let modal = new bootstrap.Modal(document.getElementById('addBoardModal'));
        modal.show();
        modal._element.addEventListener('shown.bs.modal', function () {
            document.getElementById('boardtype').focus();
        });
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
                    document.getElementById("userId").value = data.body.userId;
                    document.getElementById("name").value = data.body.name;
                    document.getElementById("email").value = data.body.email;
                    document.getElementById("role").value = data.body.role;
                } else {
                    alert("사용자 정보를 불러오는 데 실패했습니다.");
                }
            })
            .catch(error => console.error("Error fetching user data:", error));

        modal._element.addEventListener('shown.bs.modal', function () {
            document.getElementById('name').focus();
        });
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

    document.addEventListener("DOMContentLoaded", function () {
        fetch("/api/board/list")  // 백엔드 API 호출
            .then(response => response.json())  // JSON 응답 파싱
            .then(data => {
                let boardList = document.getElementById("boardList");
                boardList.innerHTML = ""; // 기존 목록 초기화

                if (data.body && data.body.boards && Array.isArray(data.body.boards)) {
                    console.log(data.body.boards)
                    data.body.boards.forEach(board => {
                        let li = document.createElement("li");
                        li.className = 'nav-item';
                        li.innerHTML = '<a class="nav-link" href="/board?type=' +
                        board.type + '">' + board.name + '</a>';
                        boardList.appendChild(li);
                    });
                } else {
                    console.error("응답 데이터 형식이 올바르지 않습니다.");
                }
            })
            .catch(error => console.error("게시판 목록을 불러오는 중 오류 발생:", error));
    });

    // #### Websocket 관려 로직 ####
    // var socket = new SockJS('/ws');
    // var stompClient = Stomp.over(socket);
    //
    // stompClient.connect({}, function(frame) {
    //     console.log('Connected: ' + frame);
    //
    //     stompClient.subscribe('/topic/alerts', function(message) {
    //         alert(message.body); // WebSocket 메시지를 alert()으로 표시
    //     });
    // });

</script>
</body>
</html>


