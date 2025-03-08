import { createApp, reactive, onMounted} from 'vue';
import AppHeader from './components/AppHeader.vue';
import Chart from './components/Chart.vue'; // Import Chart.vue

const app = createApp({
    data() {
        return {
            user: reactive({ username: null, isAdmin: false }),
            boards: reactive([]),
            postsByType: reactive({ qna: [], community: [] })
        }
    },
    components: {
        AppHeader,
        Chart, // Register the Chart component globally
    },
    methods: {
        async fetchUserInfo() {
            try {
                let response = await fetch('/api/login/info');
                let data = await response.json();
                if (data.status === 200) {
                    this.user.username = data.body.username;
                    this.user.isAdmin = (data.body.role || "").includes('ROLE_ADMIN');
                }
            } catch (error) {
                console.error("사용자 정보를 불러오는 중 오류 발생:", error);
            }
        },
        async fetchBoardList() {
            try {
                let response = await fetch('/api/board/list');
                let data = await response.json();
                this.boards.length = 0;
                this.boards.push(...data.body.boards);
            } catch (error) {
                console.error("게시판 목록을 불러오는 중 오류 발생:", error);
            }
        },
        async fetchPosts(type) {
            try {
                const url = '/api/post/list?type=' + type + '&page=1';
                const response = await fetch(url);
                const data = await response.json();

                if (!response.ok) {
                    throw new Error('Failed to fetch ' + type + ' posts');
                }

                this.postsByType[type] = data.body.posts;
            } catch (error) {
                console.error(type + ' 게시글 데이터를 불러오는 중 오류 발생:', error);
            }
        },
        openLoginModal() { this.$refs.loginModal?.show(); },
        openUserInfoModal() { this.$refs.userInfoModal?.show(); },
        openBoardModal() { this.$refs.boardModal?.show(); },
    },
    async mounted() {
        await Promise.all([
            this.fetchUserInfo(),
            this.fetchBoardList(),
            this.fetchPosts('qna'),
            this.fetchPosts('community')
        ]);
    },
});

//전역 컴포넌트 등록
app.component('AppHeader', AppHeader);
app.component('login-modal', {
    template: `<div>로그인 모달</div>`,
    methods: { show() { console.log("로그인 모달 열림"); } }
});
app.component('user-info-modal', {
    props: ['user'],
    template: `<div>회원 정보 모달</div>`,
    methods: { show() { console.log("회원정보 모달 열림"); } }
});
app.component('add-board-modal', {
    template: `<div>게시판 생성 모달</div>`,
    methods: { show() { console.log("게시판 생성 모달 열림"); } }
});

app.mount("#app");