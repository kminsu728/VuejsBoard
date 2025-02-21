<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="userInfoModal" tabindex="-1" aria-labelledby="userInfoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="userInfoModalLabel">로그인</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <form id="userInfoForm">
                    <div class="mb-3">
                        <label for="userId" class="form-label">아이디</label>
                        <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디 입력" readonly>
                    </div>

                    <div class="mb-3">
                        <label for="name" class="form-label">닉네임</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="닉네임 입력">
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">이메일</label>
                        <input type="text" class="form-control" id="email" name="email" placeholder="이메일 입력">
                    </div>

                    <div class="mb-3">
                        <label for="role" class="form-label">role</label>
                        <input type="text" class="form-control" id="role" name="role" placeholder="role" readonly>
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-success" onclick="submitUserInfo()">확인</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            </div>
        </div>
    </div>
</div>
<script>
    function submitUserInfo() {
        let userId = document.getElementById("userId").value;
        let name = document.getElementById("name").value;
        let email = document.getElementById("email").value;

        let params = new URLSearchParams();
        params.append("username", userId);
        params.append("name", name);
        params.append("email", email);

        fetch("/api/user/info/update", {
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
                    alert("사용자 정보 업데이트 실패!");
                }
            });
    }
</script>