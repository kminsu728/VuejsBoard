<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TopHeader Example</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue@3.3.4/dist/vue.global.js"></script>
</head>
<body>

<div id="app">
    <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom shadow-sm">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Home</a>

            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto">
                    <li v-for="board in boards" :key="board.type" class="nav-item">
                        <a class="nav-link" :href="'/board?type=' + board.type">{{ board.name }}</a>
                    </li>
                </ul>
            </div>

            <div class="d-flex">
                <template v-if="!user.username">
                    <button class="btn btn-primary me-2" @click="openLoginModal">로그인</button>
                    <a class="btn btn-outline-secondary" href="/signup.jsp">회원가입</a>
                </template>
                <template v-else>
                    <span class="me-3 fw-bold text-dark">{{ user.username }} 님</span>
                    <button v-if="user.isAdmin" class="btn btn-primary me-2" @click="openBoardModal">게시판 생성</button>
                    <button class="btn btn-primary me-2" @click="openUserInfoModal">회원정보</button>
                    <button class="btn btn-danger" @click="logout">로그아웃</button>
                </template>
            </div>
        </div>
    </nav>

    <!-- Vue 모달 컴포넌트 -->
    <login-modal ref="loginModal"></login-modal>
    <user-info-modal ref="userInfoModal" :user="user"></user-info-modal>
    <add-board-modal ref="boardModal"></add-board-modal>
</div>

<script>
    const app = Vue.createApp({
        data() {
            return {
                user: { username: null, isAdmin: false },
                boards: []
            };
        },
        methods: {
            async fetchUserInfo() {
                try {
                    let response = await fetch('/api/login/info');
                    let data = await response.json();
                    if (data.status === 200) {
                        this.user = {
                            username: data.body.username,
                            isAdmin: (data.body.role || "").includes('ROLE_ADMIN')
                        };
                    }
                } catch (error) {
                    console.error("사용자 정보를 불러오는 중 오류 발생:", error);
                }
            },
            async fetchBoardList() {
                try {
                    let response = await fetch('/api/board/list');
                    let data = await response.json();
                    this.boards = data.body.boards || [];
                } catch (error) {
                    console.error("게시판 목록을 불러오는 중 오류 발생:", error);
                }
            },
            openLoginModal() { this.$refs.loginModal?.show(); },
            openUserInfoModal() { this.$refs.userInfoModal?.show(); },
            openBoardModal() { this.$refs.boardModal?.show(); },
            async logout() {
                await fetch('/logout', { method: 'POST' });
                location.reload();
            }
        },
        mounted() {
            this.fetchUserInfo();
            this.fetchBoardList();
        }
    });

    app.mount('#app');
</script>

</body>
</html>
