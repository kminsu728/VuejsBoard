<template>
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
          <button class="btn btn-primary me-2" @click="$emit('open-login-modal')">로그인</button>
          <a class="btn btn-outline-secondary" href="/signup.jsp">회원가입</a>
        </template>
        <template v-else>
          <span class="me-3 fw-bold text-dark">{{ user.username }} 님</span>
          <button v-if="user.isAdmin" class="btn btn-primary me-2" @click="$emit('open-board-modal')">게시판 생성</button>
          <button class="btn btn-primary me-2" @click="$emit('open-user-info-modal')">회원정보</button>
          <button class="btn btn-danger" @click="logout">로그아웃</button>
        </template>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  props: {
    user: {
      type: Object,
      required: true,
      default: () => ({ username: null, isAdmin: false }),
    },
    boards: {
      type: Array,
      required: true,
      default: () => [],
    },
  },
  methods: {
    async logout() {
      await fetch('/logout', { method: 'POST' });
      location.reload();
    },
  },
};
</script>