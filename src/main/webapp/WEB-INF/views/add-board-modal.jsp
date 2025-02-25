<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="addBoardModal" tabindex="-1" aria-labelledby="userInfoModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addBoardModalLabel">로그인</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <form id="addBoardForm">
                    <div class="mb-3">
                        <label for="boardtype" class="form-label">게시판 Code</label>
                        <input type="text" class="form-control" id="boardtype" name="boardtype" placeholder="게시판 명 입력">
                    </div>

                    <div class="mb-3">
                        <label for="boardname" class="form-label">게시판 명</label>
                        <input type="text" class="form-control" id="boardname" name="boardname" placeholder="게시판 명 입력">
                    </div>
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-success" onclick="addBoard()">확인</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
            </div>
        </div>
    </div>
</div>
<script>
    function addBoard() {
        let boardtype = document.getElementById("boardtype").value;
        let boardname = document.getElementById("boardname").value;

        let params = new URLSearchParams();
        params.append("type", boardtype);
        params.append("name", boardname);

        fetch("/api/board/create", {
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
                    alert("게시판 생성 실패!");
                }
            })
            .catch(error => {
                console.error('게시판 생성 실패 Exception 발생', error);
                alert("게시판 생성 실패!");
            });
    }
</script>