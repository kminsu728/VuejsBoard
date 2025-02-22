<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="loginModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginModalLabel">로그인</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <form id="loginForm">
                    <div class="mb-3">
                        <label for="username" class="form-label">아이디</label>
                        <input type="text" class="form-control" id="username" name="username" placeholder="아이디 입력">
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="비밀번호 입력">
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-success" onclick="validateForm()">확인</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            </div>
        </div>
    </div>
</div>
<script>

    function validateForm() {
        const username = document.getElementById('username');
        const password = document.getElementById('password');


        console.log(username);
        console.log(password);
        // 유효성 검사
        if (!username.value) {
            alert("아이디를 입력하세요.");
            username.focus();
            return false;
        }

        if (!password.value) {
            alert("비밀번호를 입력하세요.");
            password.focus();
            return false;
        }

        submitLogin();
    }

    function submitLogin() {
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;

        let params = new URLSearchParams();
        params.append("username", username);
        params.append("password", password);

        fetch("/login", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: params.toString()
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (data.status == 200) {
                    window.location.reload();
                } else {
                    alert("로그인 실패: 아이디 또는 비밀번호 확인");
                }
            });
    }
</script>